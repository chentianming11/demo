package com.study.demo.util;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ReflectionUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.*;
import java.util.function.Function;

/**
 * @author 陈添明
 * @date 2018/10/11
 */

public abstract class ExcelUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExcelUtils.class);

    public static final String DATE_FORMAT_PATTERN = "yyyy-MM-dd HH:mm:ss";

    public static class Mapping {
        private List<String> keys = new ArrayList<>();
        private List<String> values = new ArrayList<>();

        private Mapping(){}

        public static Mapping newInstance(){
            return new Mapping();
        }

        /**
         * 新增一组 表头-字段名 映射
         * @param key excel表头导入名称
         * @param value 实体字段名称
         * @return
         */
        public Mapping put(String key, String value) {
            if (Is.empty(key) || Is.empty(value)) {
                return this;
            }
            keys.add(key);
            values.add(value);
            return this;
        }

        public List<String> getKeys() {
            return keys;
        }

        public List<String> getValues() {
            return values;
        }
    }

    public static <T> List<T> toList(File file, Mapping mapping, Class<T> clz) {
        // 根据excel文件创建workbook，能自动根据excel版本创建相应的workbook
        Workbook workbook = null;
        try {
            workbook = WorkbookFactory.create(file);
        } catch (Exception e) {
            throw new RuntimeException("创建workbook异常", e);
        }
        return toList(workbook,mapping,clz);

    }


    /**
     *   读取一个excel到List<T> 中
     */
    public static <T> List<T> toList(InputStream inputStream, Mapping mapping, Class<T> clz)  {
        // 根据excel文件创建workbook，能自动根据excel版本创建相应的workbook
        Workbook workbook = null;
        try {
            workbook = WorkbookFactory.create(inputStream);
        } catch (Exception e) {
            throw new RuntimeException("创建workbook异常", e);
        }
        return toList(workbook, mapping,clz);

    }

    /**
     *   读取一个excel到List<Map<String,Object>> 中
     */
    public static List<Map<String, Object>> toList(File file, Mapping mapping) {

        // 根据excel文件创建workbook，能自动根据excel版本创建相应的workbook
        Workbook workbook = null;
        try {
            workbook = WorkbookFactory.create(file);
        } catch (Exception e) {
            throw new RuntimeException("创建workbook异常", e);
        }
        return toList(workbook, mapping);

    }


    /**
     *   读取一个excel到List<Map<String,Object>> 中
     */
    public static List<Map<String, Object>> toList(InputStream inputStream, Mapping mapping) {

        // 根据excel文件创建workbook，能自动根据excel版本创建相应的workbook
        Workbook workbook = null;
        try {
            workbook = WorkbookFactory.create(inputStream);
        } catch (Exception e) {
            throw new RuntimeException("创建workbook异常", e);
        }
        return toList(workbook, mapping);

    }

    private static <T> List<T> toList( Workbook workbook, Mapping mapping, Class<T> clz) {
        // 获取第一个sheet
        Sheet sheet = workbook.getSheetAt(0);
        Map<Integer, String> indexFieldMapping = getIndexFieldMapping(mapping, sheet);
        int lastRowNum = sheet.getLastRowNum();
        LOGGER.info("总共加载" + lastRowNum + "行数据！");
        List<T> result = new ArrayList<>();
        try {
            // 根据有效列，读取有效列中的内容
            for (Row row : sheet) {
                // 跳过第一行标题
                if (row.getRowNum() == 0) {
                    continue;
                }
                T  t = clz.newInstance();
                // 读取内容
                indexFieldMapping.keySet().forEach((columnIndex) -> {
                    String fieldStr= indexFieldMapping.get(columnIndex);
                    Cell cell = row.getCell(columnIndex);
                    Object cellValue = getCellValue(cell);

                        Field field = ReflectionUtils.findField(clz, fieldStr);
                        if (field != null) {

                            Class<?> type = field.getType();
                            if (!Objects.equals(type, cellValue.getClass())) {
                                // 类型不一致，进行类型转换
                                cellValue = transferType(cellValue, type);
                            }
                            try {
                                PropertyUtils.setProperty(t, fieldStr, cellValue);
                            } catch (Exception e) {
                                throw new RuntimeException("设置属性异常，属性名：" + fieldStr + "; 属性值：" + cellValue, e);
                            }
                        }

                });
                result.add(t);
            }
        } catch (Exception e) {
            throw new RuntimeException("读取excel失败", e);
        }
        return result;
    }

    /**
     * 类型转换
     * @param cellValue
     * @param type
     * @return
     * @throws ParseException
     */
    private static Object transferType(Object cellValue, Class<?> type) {
        if (cellValue instanceof Date) {
            // Date 转其他类型
            if (Objects.equals(type, String.class)) {
                cellValue = DateFormatUtils.format(((Date) cellValue), DATE_FORMAT_PATTERN);
            }
        } else if (cellValue instanceof String) {
            if (Is.empty(cellValue)){
                return null;
            }
            String cellValueStr = (String) cellValue;
            // String 转其他类型
            if (Objects.equals(type, Date.class)) {
                try {
                    cellValue = DateUtils.parseDate(cellValueStr, DATE_FORMAT_PATTERN);
                } catch (ParseException e) {
                    throw new RuntimeException("日期转异常", e);
                }
            } else if (Objects.equals(type, Integer.class)) {
                cellValue = Integer.valueOf((cellValueStr));
            } else if (Objects.equals(type, Long.class)) {
                cellValue = Long.valueOf((cellValueStr));
            } else if (Objects.equals(type, Float.class)) {
                cellValue = Float.valueOf((cellValueStr));
            } else if (Objects.equals(type, Short.class)) {
                cellValue = Short.valueOf((cellValueStr));
            } else if (Objects.equals(type, Byte.class)) {
                cellValue = Byte.valueOf((cellValueStr));
            } else if (Objects.equals(type, Double.class)) {
                cellValue = Double.valueOf((cellValueStr));
            } else if (Objects.equals(type, BigDecimal.class)){
                cellValue = new BigDecimal(cellValueStr);
            }
        }
        return cellValue;
    }


    /**
     * @param workbook 工作簿
     * @param mapping  表头和字段的映射关系
     * @return
     */
    private static List<Map<String, Object>> toList(Workbook workbook, Mapping mapping) {
        // 获取第一个sheet
        Sheet sheet = workbook.getSheetAt(0);
        Map<Integer, String> indexFiledMapping = getIndexFieldMapping(mapping, sheet);
        int lastRowNum = sheet.getLastRowNum();
        LOGGER.info("总共加载" + lastRowNum + "行数据！");
        List<Map<String, Object>> result = new ArrayList<>();
        // 根据有效列，读取有效列中的内容
        for (Row row : sheet) {
            // 跳过第一行标题
            if (row.getRowNum() == 0 || row == null) {
                continue;
            }
            Map<String, Object> rowData = new HashMap<>();
            // 读取内容
            indexFiledMapping.keySet().forEach(columnIndex -> {
                String filed = indexFiledMapping.get(columnIndex);
                Cell cell = row.getCell(columnIndex);
                Object cellValue = getCellValue(cell);
                rowData.putIfAbsent(filed, cellValue);
            });
            result.add(rowData);
        }
        return result;
    }

    /**
     * 获取索引-字段映射
     * @param mapping 映射关系
     * @param sheet 表头sheet
     * @return
     */
    private static Map<Integer, String> getIndexFieldMapping(Mapping mapping, Sheet sheet) {
        // 索引-字段映射
        Map<Integer, String> indexFiledMapping = new HashedMap();
        List<String> headList = mapping.getKeys();
        List<String> fieldList = mapping.getValues();
        // 读取表头 确定有效列
        Row headRow = sheet.getRow(0);

        for (int i = 0; i < headList.size(); i++) {
            Integer index = findIndexOnRow(headRow, headList.get(i));
            if (index != null) {
                indexFiledMapping.put(index, fieldList.get(i));
                LOGGER.debug("索引index：{}; 对应字段名称：{}", index, fieldList.get(i));
            }
        }
        return indexFiledMapping;
    }


    private static Integer findIndexOnRow(Row headRow, String key) {
        for (Cell cell : headRow) {
            if (Is.equals(key, cell.getStringCellValue())){
                return cell.getColumnIndex();
            }
        }
        return null;
    }

    /**
     * 从cell中读取cellValue
     *
     * @param cell
     * @return
     */
    private static Object getCellValue(Cell cell) {
        // 根据cell内容格式 -- 分别读取其内容
        Object value ;
        if (Objects.isNull(cell)) {
            return "";
        }
        switch (cell.getCellType()) {
            case Cell.CELL_TYPE_NUMERIC: {
                if (DateUtil.isCellDateFormatted(cell)) {
                    // 日期
                    value = cell.getDateCellValue();
                } else {
                    DecimalFormat decimalFormat = new DecimalFormat("###################.##");
                    value = decimalFormat.format(cell.getNumericCellValue());
                }
                break;
            }
            case Cell.CELL_TYPE_STRING: {
                value = cell.getStringCellValue();
                break;
            }
            case Cell.CELL_TYPE_BLANK: {
                // 空白
                value = "";
                break;
            }
            case Cell.CELL_TYPE_FORMULA: {
                // 公式
                value = cell.getRichStringCellValue().getString();
                break;
            }
            default: {
                value = cell.getStringCellValue();
            }
        }
        return value;
    }

    /**
     * 分页查询,生成SXSSFWorkbook
     *
     * @param pageSize
     * @param mapping  表头和字段名映射关系
     * @param function
     * @return
     */
    public static SXSSFWorkbook createSXSSFWorkbook(Integer pageSize, Mapping mapping, Function<Integer, List<Map>> function) {
        SXSSFWorkbook workbook = new SXSSFWorkbook();
        Sheet detailSheet = workbook.createSheet("sheet1");
        // 表头
        List<String> headList = mapping.getKeys();
        // 字段
        List<String> filedList = mapping.getValues();

        ExcelUtils.sheetAppendRows(detailSheet, headList);
        Integer pageNum = 1;
        List<Map> result;
        do {
            result = function.apply(pageNum);
            pageNum++;
            result.forEach(item -> {
                List<String> sheetData = new ArrayList<>();
                for (int i = 0; i < filedList.size(); i++) {
                    detailSheet.setColumnWidth(i, 10 * 512);
                    Object o = item.get(filedList.get(i));
                    if (Objects.isNull(o)) {
                        o = "";
                    }
                    if (o instanceof Date) {
                        o = DateFormatUtils.format((Date) o, DATE_FORMAT_PATTERN);
                    }
                    sheetData.add(String.valueOf(o));
                }
                ExcelUtils.sheetAppendRows(detailSheet, sheetData);
            });

        } while (!CollectionUtils.isEmpty(result) && result.size() == pageSize);
        return workbook;
    }

    /**
     * 向excel的sheet中追加一行记录
     *
     * @param sheet
     * @param data
     */
    public static void sheetAppendRows(Sheet sheet, List<String> data) {
        Row row = sheet.createRow(sheet.getPhysicalNumberOfRows());
        for (int i = 0; i < data.size(); i++) {
            row.createCell(i).setCellValue(data.get(i) == null ? "" : data.get(i));
        }
    }

    /**
     * 导出excel
     */
    public static void export(HttpServletResponse response, SXSSFWorkbook workbook, String fileName) {
        try {
            DownloadUtils.downloadExcel(response, workbook, fileName + DateFormatUtils.format(new Date(), "yyyyMMdd") + ".xlsx");
        } catch (Exception e) {
            throw new RuntimeException("导出失败");
        }
    }
}
