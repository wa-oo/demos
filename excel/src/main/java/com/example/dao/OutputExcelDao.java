package com.example.dao;

import com.example.domain.Student;
import com.example.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import static com.example.common.Common.SELECT_STUDENT_ALL_SQL;

/**
 * 获取数据库信息
 */
public class OutputExcelDao {

    public static List<Student> getIo(){

        List<Student> list = new ArrayList<Student>();

        try {

            ResultSet rs = DBUtil.selectAll(SELECT_STUDENT_ALL_SQL);

            while (rs.next()) {
                Student student = new Student();
                student.setId(Integer.valueOf(rs.getString(1)));
                student.setNo(rs.getString(2));
                student.setName(rs.getString(3));
                student.setAge(rs.getString(4));
                student.setScore(Float.parseFloat(rs.getString(5)));
                list.add(student);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
