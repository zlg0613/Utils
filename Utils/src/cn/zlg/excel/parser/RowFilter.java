package cn.zlg.excel.parser;

import cn.zlg.excel.parser.bean.ExcelCell;

public interface RowFilter {

	public boolean accept(ExcelCell[] cells,int rowNum);
}
