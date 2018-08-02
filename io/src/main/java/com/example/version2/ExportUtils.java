package com.example.version2;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.HorizontalAlignment;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.List;

public class ExportUtils {
	  private static void exportExcel(ExcelEntity excelEntity, OutputStream outStream)
	    throws Exception
	  {
	    HSSFWorkbook wb = new HSSFWorkbook();
	    HSSFSheet sheet = wb.createSheet(excelEntity.getSheetName());
	    HSSFRow row = sheet.createRow(0);
	    String[] colNames = excelEntity.getColumnNames();
	    for (int i = 0; i < colNames.length; i++) {
	      row.createCell(i).setCellValue(colNames[i]);
	    }
	    try {
	      wb.write(outStream);
	      outStream.flush();
	      outStream.close();
	    } catch (IOException e) {
	      if (outStream != null) {
	        outStream.close();
	      }
	      e.printStackTrace();
	    }
	  }

	  /*private static int getCellIndex(ExcelEntity excelEntity, int i)
	  {
	    int cellIndex = i;

	    if (excelEntity.isAddRowNum()) {
	      cellIndex = i;
	    }
	    return cellIndex;
	  }*/

	  public static void exportView(HttpServletResponse response, String docType, String fileName, String sheetName, List<?> list, String[] columns, String[] propertyNames)
	    throws Exception
	  {
	    initView(response, docType, fileName, sheetName, list, columns, propertyNames, true);
	  }

	  public static void exportView(HttpServletResponse response, String docType, String fileName, String sheetName, List<?> list, String[] columns, String[] propertyNames, boolean isAddRowNum)
	    throws Exception
	  {
	    initView(response, docType, fileName, sheetName, list, columns, propertyNames, isAddRowNum);
	  }

	  private static void initView(HttpServletResponse response, String docType, String fileName, String sheetName, List<?> list, String[] columns, String[] propertyNames, boolean isAddRowNum)
	    throws Exception
	  {
	    response.reset();
	    fileName = URLEncoder.encode(fileName, "UTF-8");

	    OutputStream outStream = response.getOutputStream();

	    String contentType = getContentType(docType);
	    response.setContentType(contentType);
//	    response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
	    response.setHeader("Content-Disposition", "attachment;filename="  
                .concat(String.valueOf(URLEncoder.encode(fileName, "UTF-8"))));  
	    response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "*");
        response.setHeader("Access-Control-Max-Age", "1728000");
//        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Headers", "X-PINGOTHER, Origin, X-Requested-With, Content-Type, Accept,token,userId");
	    if (("xls".equals(docType)) || ("xlsx".equals(docType))) {
	      ExcelEntity entity = new ExcelEntity();
	      entity.setColumnNames(columns);
	      entity.setPropertyNames(propertyNames);
	      entity.setResultList(list);
	      entity.setSheetName(sheetName);
	      entity.setAddRowNum(isAddRowNum);
	      exportExcel(entity, outStream);
	    }
	  }

	  private static String getContentType(String docType)
	  {
	    String contentType = null;
	    if ("pdf".equals(docType))
	      contentType = "application/pdf";
	    else if ("html".equals(docType))
	      contentType = "text/html";
	    else if ("doc".equals(docType))
	      contentType = "application/msword";
	    else if ("docx".equals(docType))
	      contentType = "application/vnd.openxmlformats-officedocument.wordprocessingml.document";
	    else if ("xls".equals(docType))
	      contentType = "application/octet-stream;charset=UTF-8";
	    else if ("xlsx".equals(docType))
	      contentType = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
	    else if ("xml".equals(docType))
	      contentType = "text/xml";
	    else if ("xlsm".equals(docType)) {
	      contentType = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
	    }
	    return contentType;
	  }

	  public void createNormalHead(String headString, int colSum, String sheetName)
	  {
	    HSSFWorkbook wb = new HSSFWorkbook();
	    HSSFSheet sheet = wb.createSheet(sheetName);
	    HSSFRow row = sheet.createRow(0);

	    HSSFCell cell = row.createCell(0);
	    row.setHeight((short) 400);

	    cell.setCellType(1);
	    cell.setCellValue(new HSSFRichTextString(headString));

	    HSSFCellStyle cellStyle = wb.createCellStyle();

	    // cellStyle.setAlignment((short) 2);
	    // cellStyle.setAlignment((short) 1);

	    cellStyle.setAlignment(HorizontalAlignment.forInt((short) 2));
	    cellStyle.setAlignment(HorizontalAlignment.forInt((short) 1));
	    cellStyle.setWrapText(true);

	    HSSFFont font = wb.createFont();
	    //font.setBoldweight((short) 700);
	    font.setFontName("宋体");
	    font.setFontHeight((short) 300);
	    cellStyle.setFont(font);

	    cell.setCellStyle(cellStyle);
	  }
}
