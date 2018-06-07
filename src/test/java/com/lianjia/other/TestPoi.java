package com.lianjia.other;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.*;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by chenTianMing on 2018/5/24.
 */
public class TestPoi {

    /**
     * 测试poi读取excel文件
     */
    @Test
    public void testReadExcel() throws IOException, InvalidFormatException {
        File file = new File("D:/test2.xls");
        XSSFWorkbook workbook = new XSSFWorkbook(file);
        // 读取第一个sheet
        XSSFSheet sheet = workbook.getSheetAt(0);

        // 读取标题
        String[] head = {"姓名", "年龄", "电话", "生日"};
        XSSFRow row = sheet.getRow(0);
        short lastCellNum = row.getLastCellNum();
        List<String> headList = new ArrayList<>();
        Set<Integer> validColumnSet = new LinkedHashSet<>();
        for (int j = 0; j < lastCellNum; j++) {
            String stringCellValue = row.getCell(j).getStringCellValue();
            if (Arrays.asList(head).contains(stringCellValue)){
                System.out.println(stringCellValue);
                // 表示该列数据有效，记录列数
                validColumnSet.add(j);
            }
        }

        // 读取有效列的内容
        int lastRowNum = sheet.getLastRowNum();
        for (int i = 1; i < lastRowNum; i++) {
            XSSFRow sheetRow = sheet.getRow(i);
            validColumnSet.forEach((Integer validColumnNum) -> {
                XSSFCell cell = sheetRow.getCell(validColumnNum);
                switch (cell.getCellType()) {
                    case Cell.CELL_TYPE_NUMERIC : {
                        double numericCellValue = cell.getNumericCellValue();
                        Date dateCellValue = cell.getDateCellValue();
                        System.out.println(numericCellValue);
                        System.out.println(dateCellValue);
                        return;
                    }

                    case Cell.CELL_TYPE_STRING:{
                        String stringCellValue = cell.getStringCellValue();
                        System.out.println(stringCellValue);
                        return;
                    }

                    default:{

                    }


                }


            });
        }

//        int lastRowNum = sheet.getLastRowNum();
//        for (int i = 0; i < lastRowNum; i++) {
//            XSSFRow row = sheet.getRow(i);
//            short lastCellNum = row.getLastCellNum();
//            for (int j = 0; j < lastCellNum; j++) {
//                String cellValue = row.getCell(j).getStringCellValue();
//                System.out.println(cellValue);
//            }
//        }


    }

    @Test
    public void test3(){
        String s = "asdf  afasdfdsf  sadfas sdfd a";
        System.out.println(s.replaceAll("\\s+", ""));
        String s2 = "sdfas-dsfds--dadf-dff";
        System.out.println(s2.replaceAll("-*", ""));
    }
}
