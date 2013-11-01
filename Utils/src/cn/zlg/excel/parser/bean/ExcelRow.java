package cn.zlg.excel.parser.bean;

import java.awt.Color;

public class ExcelRow {

	private int rowNo;
	private Color backgroundColor;
	private ExcelCell[] cells;
	public int getRowNo() {
		return rowNo;
	}
	public void setRowNo(int rowNo) {
		this.rowNo = rowNo;
	}
	public Color getBackgroundColor() {
		return backgroundColor;
	}
	public void setBackgroundColor(Color backgroundColor) {
		this.backgroundColor = backgroundColor;
	}
	public ExcelCell[] getCells() {
		return cells;
	}
	public ExcelCell getCell(int col) {
		if(col>0&&col<cells.length){
			return cells[col];
		}
		return null;
	}
	public void setCells(ExcelCell[] cells) {
		this.cells = cells;
	}
	
}
