package com.example.service;

import com.example.common.Common;
import com.example.domain.Student;
import com.example.util.DBUtil;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/**
 * 导入Excel文件
 */
public class SaveDataDBService {

    public void save() throws IOException, SQLException {
        ReadExcelService xlsMain = new ReadExcelService();
        Student student = null;
        List<Student> list = xlsMain.readXls();

        for (int i = 0; i < list.size(); i++) {
            student = list.get(i);
            List l = DBUtil.selectOne(Common.SELECT_STUDENT_SQL + "'%" + student.getName() + "%'", student);
            if (!l.contains(1)) {
                DBUtil.insert(Common.INSERT_STUDENT_SQL, student);
            } else {
                System.out.println("已经存在 : No. = " + student.getNo() + " , Name = " + student.getName() + ", Age = " + student.getAge() + ", 已丢弃!");
            }
        }
    }
}
