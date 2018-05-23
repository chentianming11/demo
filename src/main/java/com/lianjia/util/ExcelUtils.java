package com.lianjia.util;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.*;
import java.util.function.Function;

/**
 * Excel工具类
 * Created by chen on 2018/5/23.
 */
public class ExcelUtils {

    /**
     * 分页查询,生成SXSSFWorkbook
     * @param pageSize
     * @param sheetName
     * @param head
     * @param columnNameList
     * @param function
     * @return
     */
    public static SXSSFWorkbook createSXSSFWorkbookByPageQuery(Integer pageSize, String sheetName, String[] head, String[] columnNameList, Function<Integer ,List<Map<String, Object>>> function){
        SXSSFWorkbook workbook = new SXSSFWorkbook();
        Sheet detailSheet = workbook.createSheet(sheetName + DateFormatUtils.format(new Date(), "yyyyMMdd") );
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
                    if (Objects.isNull(o)){
                        o = "";
                    }
                    if (o instanceof Date){
                        o = DateFormatUtils.format((Date) o, "yyyy/MM/dd HH:mm:ss");
                    }
                    list.add(String.valueOf(o));
                }
                ExcelUtils.sheetAppendRows(detailSheet, list.toArray(new String[list.size()]));
            });

        }while (!CollectionUtils.isEmpty(result) && result.size() == pageSize);
        return workbook;
    }

    /**
     * 向excel的sheet中追加一行记录
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
