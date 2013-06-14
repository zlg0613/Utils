package cn.zlg.excel.parser;

import java.util.List;

import cn.zlg.excel.parser.bean.ExcelConfig;
import cn.zlg.excel.parser.bean.ExcelRow;

public interface ExcelReader {

	public List<ExcelRow> readRows(ExcelConfig ec);
}
