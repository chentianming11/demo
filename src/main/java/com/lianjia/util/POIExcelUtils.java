package com.lianjia.util;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.text.DecimalFormat;
import java.util.*;
import java.util.function.Function;

/**
 * Excel工具类
 *
 * @author chenTianMing
 */
public class POIExcelUtils {

    public static final String dateFormatPattern = "yyyy-MM-dd HH:ss:mm";

    /**
     * 读取一个excel到List<T> 中
     * @param head excel第一行中的列名，顺序必须和excel中的一致
     * @param keyList 实体属性键的映射list，与 head 一一对应。
     * @return
     */
    public static <T> List<T> readExcelToList(File file, String[] head, String[] keyList, Class<T> clz) throws IOException, InvalidFormatException, InstantiationException, IllegalAccessException {
        // 根据excel文件创建workbook，能自动根据excel版本创建相应的workbook
        Workbook workbook = WorkbookFactory.create(file);
        return readExcelToListFromWorkbook(head, keyList, clz, workbook);

    }

    /**
     * @param head excel第一行中的列名，顺序必须和excel中的一致
     * @param keyList 实体属性键的映射list，与 head 一一对应。
     * 读取一个excel到List<T> 中
     */
    public static <T> List<T> readExcelToList(MultipartFile file, String[] head, String[] keyList, Class<T> clz) throws IOException, InvalidFormatException, IllegalAccessException, InstantiationException {
        return readExcelToList(file.getInputStream() ,head, keyList, clz);

    }

    /**
     * @param head excel第一行中的列名，顺序必须和excel中的一致
     * @param keyList 实体属性键的映射list，与 head 一一对应。
     * 读取一个excel到List<T> 中
     */
    public static <T> List<T> readExcelToList(InputStream inputStream, String[] head, String[] keyList, Class<T> clz) throws IOException, InvalidFormatException, InstantiationException, IllegalAccessException {
        // 根据excel文件创建workbook，能自动根据excel版本创建相应的workbook
        Workbook workbook = WorkbookFactory.create(inputStream);
        return readExcelToListFromWorkbook(head, keyList, clz, workbook);

    }

    /**
     * @param head excel第一行中的列名，顺序必须和excel中的一致
     * @param keyList map的键映射list，与 head 一一对应。
     * 读取一个excel到List<Map<String,Object>> 中
     */
    public static List<Map<String, Object>> readExcelToList(File file, String[] head, String[] keyList) throws IOException, InvalidFormatException {

        // 根据excel文件创建workbook，能自动根据excel版本创建相应的workbook
        Workbook workbook = WorkbookFactory.create(file);
        return readExcelToListFromWorkbook(head, keyList, workbook);

    }

    /**
     * @param head excel第一行中的列名，顺序必须和excel中的一致
     * @param keyList map的键映射list，与 head 一一对应。
     * 读取一个excel到List<Map<String,Object>> 中
     */
    public static List<Map<String, Object>> readExcelToList(MultipartFile file, String[] head, String[] keyList) throws IOException, InvalidFormatException {

        // 根据excel文件创建workbook，能自动根据excel版本创建相应的workbook
        return readExcelToList(file.getInputStream() ,head, keyList);
    }

    /**
     * @param head excel第一行中的列名，顺序必须和excel中的一致
     * @param keyList map的键映射list，与 head 一一对应。
     * 读取一个excel到List<Map<String,Object>> 中
     */
    public static List<Map<String, Object>> readExcelToList(InputStream inputStream, String[] head, String[] keyList) throws IOException, InvalidFormatException {

        // 根据excel文件创建workbook，能自动根据excel版本创建相应的workbook
        Workbook workbook = WorkbookFactory.create(inputStream);
        return readExcelToListFromWorkbook(head, keyList, workbook);

    }

    private static <T> List<T> readExcelToListFromWorkbook(String[] head, String[] keyList, Class<T> clz, Workbook workbook) throws IllegalAccessException, InstantiationException {
        // 获取第一个sheet
        Sheet sheet = workbook.getSheetAt(0);
        // 需要读取的列索引集合
        Set<Integer> columnIndexList = new LinkedHashSet<>();
        // 读取第一行标题内容 -- 确定有效列
        Row firstRow = sheet.getRow(0);
        for (Cell cell : firstRow) {
            if (Arrays.asList(head).contains(cell.getStringCellValue())) {
                int columnIndex = cell.getColumnIndex();
                columnIndexList.add(columnIndex);
            }
        }
        List<T> result = new ArrayList<>();
        // 根据有效列，读取有效列中的内容
        for (Row row : sheet) {
            // 跳过第一行标题
            if (row.getRowNum() == 0) {
                continue;
            }
            LinkedList<String> keyLinkedList = new LinkedList<>(Arrays.asList(keyList));
            T t = clz.newInstance();
            // 读取内容
            columnIndexList.forEach((Integer columnIndex) -> {
                Cell cell = row.getCell(columnIndex);
                Object cellValue = getCellValue(cell);
                try {
                    String filedName = keyLinkedList.pollFirst();
                    Field field = ReflectionUtils.findField(clz, filedName);
                    Class<?> type = field.getType();
                    if (!Objects.equals(type, cellValue.getClass())) {
                        // 类型不一致，进行类型转换
                        if (cellValue instanceof Date){
                            // Date 转其他类型
                            if (Objects.equals(type, String.class)){
                                cellValue =  DateFormatUtils.format(((Date) cellValue),dateFormatPattern);
                            }
                        }else if (cellValue instanceof String){
                            // String 转其他类型
                            if (Objects.equals(type, Date.class)){
                                cellValue = DateUtils.parseDate((String)cellValue, dateFormatPattern);
                            }else if (Objects.equals(type, Integer.class)){
                                cellValue = Integer.valueOf(((String)cellValue));
                            }else if (Objects.equals(type, Long.class)){
                                cellValue =  Long.valueOf(((String)cellValue));
                            }else if (Objects.equals(type, Float.class)){
                                cellValue =  Float.valueOf(((String)cellValue));
                            }else if (Objects.equals(type, Short.class)){
                                cellValue =  Short.valueOf(((String)cellValue));
                            }else if (Objects.equals(type, Byte.class)){
                                cellValue =  Byte.valueOf(((String)cellValue));
                            }else if (Objects.equals(type, Double.class)){
                                cellValue =  Double.valueOf(((String)cellValue));
                            }
                        }
                    }
                    PropertyUtils.setProperty(t,filedName,cellValue);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            result.add(t);
        }
        return result;
    }




    private static List<Map<String, Object>> readExcelToListFromWorkbook(String[] head, String[] keyList, Workbook workbook) {
        // 获取第一个sheet
        Sheet sheet = workbook.getSheetAt(0);
        // 需要读取的列索引集合
        Set<Integer> columnIndexList = new LinkedHashSet<>();
        // 读取第一行标题内容 -- 确定有效列
        Row firstRow = sheet.getRow(0);
        for (Cell cell : firstRow) {
            if (Arrays.asList(head).contains(cell.getStringCellValue())) {
                int columnIndex = cell.getColumnIndex();
                columnIndexList.add(columnIndex);
            }
        }
        List<Map<String, Object>> result = new ArrayList<>();
        // 根据有效列，读取有效列中的内容
        for (Row row : sheet) {
            // 跳过第一行标题
            if (row.getRowNum() == 0 || row == null) {
                continue;
            }
            LinkedList<String> keyLinkedList = new LinkedList<>(Arrays.asList(keyList));
            Map<String, Object> map = new HashMap<>();
            // 读取内容
            columnIndexList.forEach(columnIndex -> {
                Cell cell = row.getCell(columnIndex);
                Object cellValue = getCellValue(cell);
                map.putIfAbsent(keyLinkedList.pollFirst(), cellValue);
            });
            result.add(map);
        }
        return result;
    }

    /**
     * 从cell中读取cellValue
     *
     * @param cell
     * @return
     */
    private static Object getCellValue(Cell cell) {
        // 根据cell内容格式 -- 分别读取其内容
        Object value = null;
        if (Objects.isNull(cell)){
            return "";
        }
        switch (cell.getCellType()) {
            case Cell.CELL_TYPE_NUMERIC: {
                if (DateUtil.isCellDateFormatted(cell)) {
                    // 日期
                    value = cell.getDateCellValue();
                }else {
                    DecimalFormat decimalFormat = new DecimalFormat();
                    value = decimalFormat.format(cell.getNumericCellValue());
                }
                break;
            }
            case Cell.CELL_TYPE_STRING : {
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
     * @param pageSize
     * @param head
     * @param columnNameList
     * @param function
     * @return
     */
    public static SXSSFWorkbook createSXSSFWorkbookByPageQuery(Integer pageSize, String[] head, String[] columnNameList, Function<Integer, List<Map<String, Object>>> function) {
        SXSSFWorkbook workbook = new SXSSFWorkbook();
        Sheet detailSheet = workbook.createSheet("sheet1");
        POIExcelUtils.sheetAppendRows(detailSheet, head);
        Integer pageNum = 1;
        List<Map<String, Object>> result;
        do {
            result = function.apply(pageNum);
            pageNum++;
            result.forEach(item -> {
                List<String> list = new ArrayList<>();
                for (int i = 0; i < columnNameList.length; i++) {
                    detailSheet.setColumnWidth(i, 10 * 512);
                    Object o = item.get(columnNameList[i]);
                    if (Objects.isNull(o)) {
                        o = "";
                    }
                    if (o instanceof Date) {
                        o = DateFormatUtils.format((Date) o, dateFormatPattern);
                    }
                    list.add(String.valueOf(o));
                }
                POIExcelUtils.sheetAppendRows(detailSheet, list.toArray(new String[list.size()]));
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
    public static void sheetAppendRows(Sheet sheet, String[] data) {
        Row row = sheet.createRow(sheet.getPhysicalNumberOfRows());
        for (int i = 0; i < data.length; i++) {
            row.createCell(i).setCellValue(data[i] == null ? "" : data[i]);
        }
    }

    /**
     * 导出excel
     */
    public static void export(HttpServletResponse response, SXSSFWorkbook workbook, String fileName) throws Exception {
        DownloadUtils.downloadExcel(response, workbook, fileName + DateFormatUtils.format(new Date(), "yyyyMMdd") + ".xlsx");
    }
}


