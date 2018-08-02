package com.Preview;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 * 网页预览文件
 */
@Controller
public class Preview {
    @RequestMapping(value = "/getFile", method=GET)
    public HttpServletResponse getFile(HttpServletRequest request, HttpServletResponse response) {

        String path = "D:/work/马克思传播工程接口汇总.pdf";

        try {
            File file = new File(path);
            String filename = file.getName();

            InputStream fis = new BufferedInputStream(new FileInputStream(file));
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            fis.close();
            response.reset();
            // 设置response的Header
            response.setHeader("Content-Disposition", "inline;filename=" .concat(String.valueOf(URLEncoder.encode(filename, "UTF-8"))));
            response.setHeader("Content-Length", "" + file.length());
            OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
            //设置预览文件格式
            response.setContentType("application/pdf");

            toClient.write(buffer);
            toClient.flush();
            toClient.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return response;
    }

    @RequestMapping(value = "getImage", method=GET)
    public HttpServletResponse getImage(String path,HttpServletRequest request, HttpServletResponse response) {
        try {
            File file = new File("D:/work/头像.jpg");
            String filename = file.getName();

            InputStream fis = new BufferedInputStream(new FileInputStream(file));
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            fis.close();

            response.reset();
            // 设置response的Header
            response.addHeader("Content-Length", "" + file.length());
            OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
            response.setContentType("image/jpg");
            toClient.write(buffer);
            toClient.flush();
            toClient.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return response;
    }
}
