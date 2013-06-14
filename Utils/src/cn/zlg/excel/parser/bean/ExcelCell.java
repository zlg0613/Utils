package cn.zlg.excel.parser.bean;

import java.awt.Color;

public class ExcelCell {

	private int row;
	private int column;
	private String content;
	private Color backgroundColor;
	public int getRow() {
		return row;
	}
	public void setRow(int row) {
		this.row = row;
	}
	public int getColumn() {
		return column;
	}
	public void setColumn(int column) {
		this.column = column;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Color getBackgroundColor() {
		return backgroundColor;
	}
	public void setBackgroundColor(Color backgroundColor) {
		this.backgroundColor = backgroundColor;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ExcelCell [content=");
		builder.append(content);
		builder.append("]");
		return builder.toString();
	}
	
}
