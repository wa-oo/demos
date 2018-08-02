package com.example.controller;

import org.artofsolving.jodconverter.OfficeDocumentConverter;
import org.artofsolving.jodconverter.office.DefaultOfficeManagerConfiguration;
import org.artofsolving.jodconverter.office.OfficeManager;

import java.io.File;

/**
 * 文件转换
 */
public class FileConverter {

    private static final String OpenOffice_HOME = "C:\\Program Files (x86)\\OpenOffice 4\\program";
    private static String inputFilePath = "D:/work/马克思传播工程接口汇总.xlsx";

    public static void main(String[] args) {
        word2pdf(inputFilePath);
    }

    public static void word2pdf(String inputFilePath) {
        DefaultOfficeManagerConfiguration config = new DefaultOfficeManagerConfiguration();

        String officeHome = OpenOffice_HOME;
        config.setOfficeHome(officeHome);

        OfficeManager officeManager = config.buildOfficeManager();
        officeManager.start();

        OfficeDocumentConverter converter = new OfficeDocumentConverter(officeManager);
        String outputFilePath = getOutputFilePath(inputFilePath);
        File inputFile = new File(inputFilePath);
        if (inputFile.exists()) {// 找不到源文件, 则返回
            File outputFile = new File(outputFilePath);
            if (!outputFile.getParentFile().exists()) { // 假如目标路径不存在, 则新建该路径
                outputFile.getParentFile().mkdirs();
            }
            converter.convert(inputFile, outputFile);
        }

        officeManager.stop();
    }

    public static String getOutputFilePath(String inputFilePath) {

        String outputFilePath = inputFilePath.substring(0, inputFilePath.indexOf("."))+".pdf";

        return outputFilePath;
    }

//	public static String getOfficeHome() {  
//    String osName = System.getProperty("os.name");  
//    if (Pattern.matches("Linux.*", osName)) {  
//        return "/opt/openoffice.org3";  
//    } else if (Pattern.matches("Windows.*", osName)) {  
//        return "D:/Program Files/OpenOffice.org 3";  
//    } else if (Pattern.matches("Mac.*", osName)) {  
//        return "/Application/OpenOffice.org.app/Contents";  
//    }  
//    return null;  
//}  

}
