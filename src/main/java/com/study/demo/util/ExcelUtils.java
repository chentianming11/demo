package com.study.demo.util;

import com.study.demo.exception.AppException;
import jk.excel.parse.Excel;
import jk.excel.parse.ExcelParseFactory;
import jk.excel.parse.Mapping;
import jk.excel.parse.ParseInfo;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.*;
import java.util.function.Function;

/**
 * @author 陈添明
 * @date 2018/10/11
 */
public class ExcelUtils {
    public static final String DATE_FORMAT_PATTERN = "yyyy-MM-dd HH:mm:ss";

    private static final Logger LOGGER = LoggerFactory.getLogger(ExcelUtils.class);

    /**
     * 导入excel到List<Map>
     *
     * @param file
     * @param mappingList
     * @param startLine
     * @return
     */
    public static List<Map> importExcelForMap(File file, List<Mapping> mappingList, int startLine) {
        try {
            ParseInfo info = new ParseInfo(file, mappingList, startLine);
            Excel excel = ExcelParseFactory.getExcelParse(info);
            List<Map> data = excel.parseToMapList();
            return data;
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw new AppException("excel导入失败");
        }
    }

    public static List<Map> importExcelForMap(MultipartFile multipartFile, List<Mapping> mappingList, int startLine) {
        String fileNmae = multipartFile.getOriginalFilename();
        File file = new File(fileNmae);
        try {
            InputStream is = multipartFile.getInputStream();
            OutputStream os = new FileOutputStream(file);
            int bytesRead = 0;
            byte[] buffer = new byte[8192];
            while ((bytesRead = is.read(buffer, 0, 8192)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            os.close();
            is.close();
            ParseInfo info = new ParseInfo(file, mappingList, startLine);
            Excel excel = ExcelParseFactory.getExcelParse(info);
            List<Map> data = excel.parseToMapList();
            return data;
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw new AppException("excel导入失败");
        }finally {
            if(file.exists()){
                file.delete();
            }
        }
    }



    /**
     * 导入excel到List<T>
     *
     * @param file
     * @param mappingList
     * @param startLine
     * @param t
     * @param <T>
     * @return
     */
    public static <T> List<T> importExcelForList(File file, List<Mapping> mappingList, int startLine, Class<T> t) {
        try {
            ParseInfo info = new ParseInfo(file, mappingList, startLine);
            Excel excel = ExcelParseFactory.getExcelParse(info);
            List<T> data = excel.parseToList(t);
            return data;
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw new AppException("excel导入失败");
        }
    }

    public static <T> List<T> importExcelForList(MultipartFile multipartFile, List<Mapping> mappingList, int startLine, Class<T> t) {
        String fileNmae = multipartFile.getOriginalFilename();
        File file = new File(fileNmae);
        try {
            InputStream is = multipartFile.getInputStream();
            OutputStream os = new FileOutputStream(file);
            int bytesRead = 0;
            byte[] buffer = new byte[8192];
            while ((bytesRead = is.read(buffer, 0, 8192)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            os.close();
            is.close();
            return importExcelForList(file,mappingList,startLine,t);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw new AppException("excel导入失败");
        } finally {
            if(file.exists()){
                file.delete();
            }
        }

    }

    /**
     * 分页查询,生成SXSSFWorkbook
     *
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
                        o = DateFormatUtils.format((Date) o, DATE_FORMAT_PATTERN);
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
        org.apache.poi.ss.usermodel.Row row = sheet.createRow(sheet.getPhysicalNumberOfRows());
        for (int i = 0; i < data.length; i++) {
            row.createCell(i).setCellValue(data[i] == null ? "" : data[i]);
        }
    }

    /**
     * 导出excel
     */
    public static void export(HttpServletResponse response, SXSSFWorkbook workbook, String fileName) {
        try {
            DownloadUtils.downloadExcel(response, workbook, fileName + DateFormatUtils.format(new Date(), "yyyyMMdd") + ".xlsx");
        } catch (Exception e) {
            throw new AppException("导出失败");

        }
    }
}
