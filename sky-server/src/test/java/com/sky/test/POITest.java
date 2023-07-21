package com.sky.test;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

/**
 * ClassName: POITest
 * Package: com.sky.test
 * Description: 使用POI操作Excel文件
 *
 * @Author: Jane
 * @Create: 2023/7/21 - 19:27
 * @version: v1.0
 */
public class POITest {

    /**
     * 通过POI创建Excel文件并且写入文件内容
     */
    public static void write() throws Exception{
        // 在内存中创建一个Excel文件
        XSSFWorkbook excel = new XSSFWorkbook();
        // 在Excel文件中创建一个Sheet页
        XSSFSheet sheet = excel.createSheet("info");
        // 在Sheet中创建行对象，rownum编号从0开始
        XSSFRow row = sheet.createRow(1);
        // 创建单元格并且写入文件内容
        row.createCell(1).setCellValue("姓名");
        row.createCell(2).setCellValue("城市");

        // 创建一个新行
        row = sheet.createRow(2);
        row.createCell(1).setCellValue("Jane");
        row.createCell(2).setCellValue("深圳");

        row = sheet.createRow(3);
        row.createCell(1).setCellValue("Yoyo");
        row.createCell(2).setCellValue("深圳");

        // 通过输出流将内存中的Excel文件写入到磁盘
        FileOutputStream out = new FileOutputStream(new File("E:\\info.xlsx"));
        excel.write(out);

        // 关闭资源
        out.close();
        excel.close();
    }

    /**
     * 通过POI读取Excel文件中的内容
     * @throws Exception
     */
    public static void read() throws Exception{

        // 输入流对象，通过它读取已有的Excel文件
        InputStream in = new FileInputStream(new File("E:\\info.xlsx"));

        // 读取磁盘上已经存在的Excel文件
        XSSFWorkbook excel = new XSSFWorkbook(in);
        // 读取Excel文件中的第一个Sheet页
        XSSFSheet sheet = excel.getSheetAt(0);

        // 获取Sheet中最后一行的行号(从0开始)
        int lastRowNum = sheet.getLastRowNum();

        for (int i = 1; i <= lastRowNum; i++) {
            // 获得某一行
            XSSFRow row = sheet.getRow(i);
            // 获得单元格对象
            String cellValue1 = row.getCell(1).getStringCellValue();
            String cellValue2 = row.getCell(2).getStringCellValue();
            System.out.println(cellValue1 + " " + cellValue2);
        }

        // 关闭资源
        in.close();
        excel.close();

    }

    public static void main(String[] args) throws Exception {
        write();
        read();
    }
}
