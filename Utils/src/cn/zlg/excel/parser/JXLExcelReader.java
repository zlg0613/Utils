package cn.zlg.excel.parser;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import cn.zlg.excel.parser.bean.ExcelCell;
import cn.zlg.excel.parser.bean.ExcelConfig;
import cn.zlg.excel.parser.bean.ExcelRow;

public class JXLExcelReader implements ExcelReader {

	@Override
	public List<ExcelRow> readRows(ExcelConfig ec) {
			if(ec.getStartRow()<0){
				ec.setStartRow(0);
			}
			Workbook wb;
			try {
				wb = Workbook.getWorkbook(new File(ec.getFile()));
				Sheet sheet = wb.getSheet(ec.getSheet());
				int rows = sheet.getRows();
				if(ec.getEndRow()<0||ec.getEndRow()>rows){
					ec.setEndRow(rows);
				}
				if(ec.getStartRow()>ec.getEndRow()){
					throw new IllegalArgumentException(String.format("startRow=%d > endRow=%",ec.getStartRow(),ec.getEndRow()));
				}
				List<ExcelRow> list = new ArrayList<ExcelRow>();
				for(int i=ec.getStartRow();i<ec.getEndRow();i++){
					ExcelRow er = new ExcelRow();
					er.setRowNo(i);
					Cell[] row = sheet.getRow(i);
					ExcelCell[] cells = new ExcelCell[ec.getMappedColumns().length];
					System.out.println(i);
					for(int j=0;j<ec.getMappedColumns().length;j++){
						int targetColumn = ec.getMappedColumns()[j];
						cells[j] = convert(row[targetColumn]);
					}
					er.setCells(cells);
					list.add(er);
				}
				wb.close();
				return list;
			} catch (BiffException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
	}

	private ExcelCell convert(Cell cell) {
		if(cell==null){
			return null;
		}
		ExcelCell ec = new ExcelCell();
		ec.setColumn(cell.getColumn());
		ec.setRow(cell.getRow());
		ec.setContent(cell.getContents());
		if(cell.getCellFormat()!=null){
			Color c = new Color(cell.getCellFormat().getBackgroundColour().getValue());
			ec.setBackgroundColor(c);
		}
		return ec;
	}

}
