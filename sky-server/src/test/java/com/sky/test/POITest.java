package com.sky.test;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;

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
        FileOutputStream out = new FileOutputStream(new File("D:\\info.xlsx"));
        excel.write(out);

        // 关闭资源
        out.close();
        excel.close();
    }

    public static void main(String[] args) throws Exception {
        write();
    }
}
