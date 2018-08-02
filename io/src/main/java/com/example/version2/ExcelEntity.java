package com.example.version2;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;

import java.util.List;

public class ExcelEntity {
	 private String sheetName;
	  private String[] columnNames;
	  private String[] propertyNames;
	  private String[] cLabels;
	  private boolean isAddRowNum;
	  private int rpp = 200;

	  private HSSFCellStyle style = null;
	  private List resultList;

	  public String getSheetName()
	  {
	    return this.sheetName;
	  }

	  public void setSheetName(String sheetName) {
	    this.sheetName = sheetName;
	  }

	  public String[] getColumnNames() {
	    return this.columnNames;
	  }

	  public void setColumnNames(String[] columnNames) {
	    this.columnNames = columnNames;
	  }

	  public String[] getPropertyNames() {
	    return this.propertyNames;
	  }

	  public void setPropertyNames(String[] propertyNames) {
	    this.propertyNames = propertyNames;
	  }

	  public String[] getCLabels() {
	    return this.cLabels;
	  }

	  public void setCLabels(String[] labels) {
	    this.cLabels = labels;
	  }

	  public int getRpp() {
	    return this.rpp;
	  }

	  public void setRpp(int rpp) {
	    this.rpp = rpp;
	  }

	  public HSSFCellStyle getStyle() {
	    return this.style;
	  }

	  public void setStyle(HSSFCellStyle style) {
	    this.style = style;
	  }

	  public boolean isAddRowNum() {
	    return this.isAddRowNum;
	  }

	  public void setAddRowNum(boolean isAddRowNum) {
	    this.isAddRowNum = isAddRowNum;
	  }

	  public List getResultList()
	  {
	    return this.resultList;
	  }

	  public void setResultList(List resultList)
	  {
	    this.resultList = resultList;
	  }

}
