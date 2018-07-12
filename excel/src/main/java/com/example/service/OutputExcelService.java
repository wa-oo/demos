package com.example.service;

import com.example.dao.OutputExcelDao;
import com.example.domain.Student;
import org.apache.poi.xssf.usermodel.*;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * 输出Excel文件
 */
public class OutputExcelService {

    public void outputExcel() throws IOException {
        XSSFWorkbook wb = new XSSFWorkbook();

        XSSFSheet sheet= wb.createSheet("test");
        sheet.setDefaultRowHeightInPoints(20);
        sheet.setDefaultColumnWidth(20);

        XSSFCellStyle cellStyle = wb.createCellStyle();

        XSSFFont fontStyle=wb.createFont();
        fontStyle.setFontName("宋体");
        cellStyle.setFont(fontStyle);

        XSSFRow row1=sheet.createRow(0);
        row1.createCell(0).setCellValue("id");
        row1.createCell(1).setCellValue("no");
        row1.createCell(2).setCellValue("name");
        row1.createCell(3).setCellValue("age");
        row1.createCell(4).setCellValue("score");

        List<Student> list = OutputExcelDao.getIo();

        for (int i = 0; i < list.size(); i++) {

            Student student = list.get(i);

            XSSFRow row=sheet.createRow(i+1);
            row.createCell(0).setCellValue(student.getId());
            row.createCell(1).setCellValue(student.getNo());
            row.createCell(2).setCellValue(student.getName());
            row.createCell(3).setCellValue(student.getAge());
            row.createCell(4).setCellValue(student.getScore());
        }

        FileOutputStream output=new FileOutputStream("d:\\student.xlsx");

        wb.write(output);
        output.flush();
    }

}
