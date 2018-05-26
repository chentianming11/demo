package com.lianjia.util;

import com.lianjia.test.Person;
import lombok.SneakyThrows;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.*;
import java.util.function.Function;

/**
 * Excel工具类
 *
 * @author chenTianMing
 */
public class ExcelUtils {

    public static final String dateFormatPattern = "yyyy-MM-dd HH:ss:mm";

    public static void main(String[] args) {

        String[] head = {"姓名", "年龄", "生日"};
        String[] keyList = {"name", "age", "birthday"};
        File file = new File("D:/test.xlsx");
        List<Map<String, Object>> list = ExcelUtils.readExcelToList(file, head, keyList);

        List<Person> personList = ExcelUtils.readExcelToList(file, head, keyList, Person.class);
        System.out.println(list);
        System.out.println(personList);
    }

    /**
     * 读取一个excel到List<T> 中
     */
    @SneakyThrows
    public static <T> List<T> readExcelToList(File file, String[] head, String[] keyList, Class<T> clz) {
        // 根据excel文件创建workbook，能自动根据excel版本创建相应的workbook
        Workbook workbook = WorkbookFactory.create(file);
        return readExcelToListFromWorkbook(head, keyList, clz, workbook);

    }

    /**
     * 读取一个excel到List<T> 中
     */
    @SneakyThrows
    public static <T> List<T> readExcelToList(MultipartFile file, String[] head, String[] keyList, Class<T> clz) {
        return readExcelToList(file.getInputStream() ,head, keyList, clz);

    }

    /**
     * 读取一个excel到List<T> 中
     */
    @SneakyThrows
    public static <T> List<T> readExcelToList(InputStream inputStream, String[] head, String[] keyList, Class<T> clz) {
        // 根据excel文件创建workbook，能自动根据excel版本创建相应的workbook
        Workbook workbook = WorkbookFactory.create(inputStream);
        return readExcelToListFromWorkbook(head, keyList, clz, workbook);

    }

    /**
     * 读取一个excel到List<Map<String,Object>> 中
     */
    @SneakyThrows
    public static List<Map<String, Object>> readExcelToList(File file, String[] head, String[] keyList) {

        // 根据excel文件创建workbook，能自动根据excel版本创建相应的workbook
        Workbook workbook = WorkbookFactory.create(file);
        return readExcelToListFromWorkbook(head, keyList, workbook);

    }

    /**
     * 读取一个excel到List<Map<String,Object>> 中
     */
    @SneakyThrows
    public static List<Map<String, Object>> readExcelToList(MultipartFile file, String[] head, String[] keyList) {

        // 根据excel文件创建workbook，能自动根据excel版本创建相应的workbook
        return readExcelToList(file.getInputStream() ,head, keyList);
    }

    /**
     * 读取一个excel到List<Map<String,Object>> 中
     */
    @SneakyThrows
    public static List<Map<String, Object>> readExcelToList(InputStream inputStream, String[] head, String[] keyList) {

        // 根据excel文件创建workbook，能自动根据excel版本创建相应的workbook
        Workbook workbook = WorkbookFactory.create(inputStream);
        return readExcelToListFromWorkbook(head, keyList, workbook);

    }

    @SneakyThrows
    private static <T> List<T> readExcelToListFromWorkbook(String[] head, String[] keyList, Class<T> clz, Workbook workbook){
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
                        if (cellValue instanceof Double){
                            // Double 转 其他数值类型
                            if (Objects.equals(type, Integer.class)){
                                cellValue = ((Double) cellValue).intValue();
                            }else if (Objects.equals(type, Long.class)){
                                cellValue =  ((Double) cellValue).longValue();
                            }else if (Objects.equals(type, Float.class)){
                                cellValue =  ((Double) cellValue).floatValue();
                            }else if (Objects.equals(type, Short.class)){
                                cellValue =  ((Double) cellValue).shortValue();
                            }else if (Objects.equals(type, Byte.class)){
                                cellValue =  ((Double) cellValue).byteValue();
                            }
                        }else if (cellValue instanceof Date){
                            // Date 转其他类型
                            if (Objects.equals(type, String.class)){
                                cellValue =  DateFormatUtils.format(((Date) cellValue),dateFormatPattern);
                            }
                        }else if (cellValue instanceof String){
                            // String 转其他类型
                            if (Objects.equals(type, Date.class)){
                                cellValue = DateUtils.parseDate((String)cellValue, dateFormatPattern);
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




    @SneakyThrows
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
                    value = cell.getNumericCellValue();
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

            case Cell.CELL_TYPE_BOOLEAN: {
                // 布尔
                value = cell.getBooleanCellValue();
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
        ExcelUtils.sheetAppendRows(detailSheet, head);
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
                ExcelUtils.sheetAppendRows(detailSheet, list.toArray(new String[list.size()]));
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


