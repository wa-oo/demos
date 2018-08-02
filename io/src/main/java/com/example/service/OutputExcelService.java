package com.example.service;

import com.example.dao.OutputExcelDao;
import com.example.domain.Student;
import org.apache.poi.xssf.usermodel.*;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import static com.example.common.Common.EXCEL_OUTPUT_PATH;

/**
 * 输出Excel文件
 */
public class OutputExcelService {

    public void outputExcel() throws IOException {

        //创建Excel文件
        XSSFWorkbook wb = new XSSFWorkbook();

        //创建一个sheet页   取名test
        XSSFSheet sheet= wb.createSheet("test");

        //设置长度和宽度
        sheet.setDefaultRowHeightInPoints(20);
        sheet.setDefaultColumnWidth(20);

        //样式
        XSSFCellStyle cellStyle = wb.createCellStyle();

        //字体样式
        XSSFFont fontStyle=wb.createFont();
        fontStyle.setFontName("宋体");
        cellStyle.setFont(fontStyle);

        //创建一行,添加表头
        XSSFRow row1=sheet.createRow(0);
        row1.createCell(0).setCellValue("id");
        row1.createCell(1).setCellValue("no");
        row1.createCell(2).setCellValue("name");
        row1.createCell(3).setCellValue("age");
        row1.createCell(4).setCellValue("score");

        //获取所有信息
        List<Student> list = OutputExcelDao.getIo();

        for (int i = 0; i < list.size(); i++) {

            Student student = list.get(i);

            //循环添加数据
            XSSFRow row=sheet.createRow(i+1);
            row.createCell(0).setCellValue(student.getId());
            row.createCell(1).setCellValue(student.getNo());
            row.createCell(2).setCellValue(student.getName());
            row.createCell(3).setCellValue(student.getAge());
            row.createCell(4).setCellValue(student.getScore());
        }

        //指定文件的名称和地址
        FileOutputStream output=new FileOutputStream(EXCEL_OUTPUT_PATH);

        //输出
        wb.write(output);
        output.flush();
    }

}
