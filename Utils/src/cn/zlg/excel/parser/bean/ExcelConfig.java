package cn.zlg.excel.parser.bean;

import java.util.Arrays;

import cn.zlg.excel.parser.ExcelOutputType;

public class ExcelConfig {

	private String file;
	private int startRow;
	private int endRow;
	private int sheet;
	private int[] mappedColumns;
	private ExcelOutputType outputType;
	public String getFile() {
		return file;
	}
	public void setFile(String file) {
		this.file = file;
	}
	public int getStartRow() {
		return startRow;
	}
	public void setStartRow(int startRow) {
		this.startRow = startRow;
	}
	public int getEndRow() {
		return endRow;
	}
	public void setEndRow(int endRow) {
		this.endRow = endRow;
	}
	public int getSheet() {
		return sheet;
	}
	public void setSheet(int sheet) {
		this.sheet = sheet;
	}
	
	public ExcelOutputType getOutputType() {
		return outputType;
	}
	public void setOutputType(ExcelOutputType outputType) {
		this.outputType = outputType;
	}
	public int[] getMappedColumns() {
		return mappedColumns;
	}
	public void setMappedColumns(Integer[] integers) {
		this.mappedColumns = new int[integers.length];
		for(int i=0;i<integers.length;i++){
			this.mappedColumns[i] = integers[i];
		}
	}
	public void setMappedColumns(int[] mappedColumns) {
		this.mappedColumns = mappedColumns;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ExcelConfig [file=");
		builder.append(file);
		builder.append(", startRow=");
		builder.append(startRow);
		builder.append(", endRow=");
		builder.append(endRow);
		builder.append(", sheet=");
		builder.append(sheet);
		builder.append(", mappedColumns=");
		builder.append(Arrays.toString(mappedColumns));
		builder.append(", outputType=");
		builder.append(outputType);
		builder.append("]");
		return builder.toString();
	}
	
}
