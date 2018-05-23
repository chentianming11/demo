package com.lianjia.util;

import org.apache.poi.util.IOUtils;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.net.URLEncoder;

/**
 * 下载工具类
 * Created by chen on 2018/5/23.
 */
public class DownloadUtils {


    /**
     * 下载excel
     * @param response
     * @param workbook
     * @param fileName
     * @throws Exception
     */
    public static void downloadExcel(HttpServletResponse response, SXSSFWorkbook workbook, String fileName) throws Exception {
        OutputStream outputStream = null;
        try {
            response.setContentType("application/x-download");
            response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
            outputStream = response.getOutputStream();
            workbook.write(outputStream);
            workbook.dispose();
            outputStream.flush();
        } finally {
            IOUtils.closeQuietly(outputStream);
        }
    }
}
