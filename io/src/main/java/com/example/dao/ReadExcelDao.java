package com.example.dao;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.example.common.Common;
import com.example.domain.Student;
import com.example.util.ExamineUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * 读取Excel源文件
 */
public class ReadExcelDao {
    public List<Student> readXls() throws IOException {
        InputStream is = new FileInputStream(Common.EXCEL_SAVE_PATH);
        XSSFWorkbook xssfWorkbook = new XSSFWorkbook(is);
        Student student = null;
        List<Student> list = new ArrayList<Student>();

        //循环工作表Sheet
        for (int numSheet = 0; numSheet < xssfWorkbook.getNumberOfSheets(); numSheet++) {
            Sheet sheet = xssfWorkbook.getSheetAt(numSheet);
            if (sheet == null) {
                continue;
            }

            //循环行Row
            for (int rowNum = 1; rowNum <= sheet.getLastRowNum(); rowNum++) {
                Row row = sheet.getRow(rowNum);
                if (row != null) {
                    student = new Student();
                    Cell no = row.getCell(0);
                    Cell name = row.getCell(1);
                    Cell age = row.getCell(2);
                    Cell score = row.getCell(3);
                    student.setNo(ExamineUtil.examine(getValue(no)));
                    student.setName(ExamineUtil.examine(getValue(name)));
                    student.setAge(ExamineUtil.examine(getValue(age)));
                    student.setScore(Float.valueOf(ExamineUtil.examine(getValue(score))));
                    list.add(student);
                }
            }
        }
        return list;
    }

    private String getValue(Cell cell) {
        if (cell.getCellType() == cell.CELL_TYPE_BOOLEAN) {
            // 返回布尔类型的值
            return String.valueOf(cell.getBooleanCellValue());
        } else if (cell.getCellType() == cell.CELL_TYPE_NUMERIC) {
            // 返回数值类型的值
            return String.valueOf(cell.getNumericCellValue());
        } else {
            // 返回字符串类型的值
            return String.valueOf(cell.getStringCellValue());
        }
    }
}
