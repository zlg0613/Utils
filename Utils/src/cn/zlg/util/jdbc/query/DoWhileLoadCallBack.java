package cn.zlg.util.jdbc.query;

import java.sql.ResultSet;

public interface DoWhileLoadCallBack {

	public void doSomething(ResultSet rs,int rowNum);
}
