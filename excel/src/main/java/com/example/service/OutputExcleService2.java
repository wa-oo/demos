package com.example.service;

import org.apache.poi.xssf.usermodel.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.example.common.Common.EXCEL_OUTPUT_PATH1;

/**
 * Excle模板输出
 */
@Controller
public class OutputExcleService2 {

    @PostMapping("/getTable")
    public void outputExcel(String response) throws IOException {

        //创建Excel文件
        XSSFWorkbook wb = new XSSFWorkbook();

        //创建一个sheet页   取名test
        XSSFSheet sheet= wb.createSheet("test");

        //样式
        XSSFCellStyle cellStyle = wb.createCellStyle();

        //设置sheet长度和宽度
        sheet.setDefaultRowHeightInPoints(20);
        sheet.setDefaultColumnWidth(20);

        //创建一行,添加表头
        XSSFRow row1=sheet.createRow(0);

        List list = getTable(response.toString());
        for (int i = 0; i < list.size(); i++) {
            row1.createCell(i).setCellValue(String.valueOf(list.get(i)));
        }
        //字体样式
        XSSFFont font = wb.createFont();
        font.setFontName("宋体");//设置字体
        font.setFontHeightInPoints((short) 20);//设置字体大小
        font.setBold(true);//粗体显示
        cellStyle.setFont(font);

        //指定文件的名称和地址
        FileOutputStream output=new FileOutputStream(EXCEL_OUTPUT_PATH1);

        //输出
        wb.write(output);
        output.flush();
    }

    public List getTable(String string){
        List list = new ArrayList();

        if (string.equals("1")) {
            //students表
            list.add("姓名");
            list.add("性别");
            list.add("身份证号");
            list.add("民族");
            list.add("政治面貌");
            list.add("单位");
            list.add("职务");
            list.add("学历");
            list.add("电话");
            list.add("邮箱");
        } else if (string.equals("2")) {
            //teacher表
            list.add("姓名");
            list.add("性别");
            list.add("身份证号");
            list.add("电话");
            list.add("学历");
        } else if (string.equals("3")) {
            //course表
            list.add("课程名称");
            list.add("学时");
            list.add("简介");
        } else if (string.equals("4")) {
            //classroom表
            list.add("教室编码");
            list.add("教室名称");
            list.add("座位数");
            list.add("地址");
        } else if (string.equals("5")) {
            //room表
            list.add("房间号");
            list.add("地址");
            list.add("床位数");
        }

        return list;
    }
}
