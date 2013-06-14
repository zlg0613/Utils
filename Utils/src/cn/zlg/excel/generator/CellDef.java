package cn.zlg.excel.generator;

import org.apache.poi.hssf.usermodel.HSSFCell;

public class CellDef {

	private String columnTile;
	private HSSFCell cell;
	
	public int getRow() {
		return cell.getRowIndex();
	}
	
	public int getColumn() {
		return cell.getColumnIndex();
	}
	
	public String getColumnTile() {
		return columnTile;
	}
	
	public void setColumnTile(String columnTile) {
		this.columnTile = columnTile;
	}
	
	public HSSFCell getCell() {
		return cell;
	}
	
	public void setCell(HSSFCell cell) {
		this.cell = cell;
	}
	
}
