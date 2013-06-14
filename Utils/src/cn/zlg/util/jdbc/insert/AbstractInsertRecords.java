package cn.zlg.util.jdbc.insert;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public abstract class AbstractInsertRecords implements InsertRecords
{

	protected  int batchSize = 50;
	
	protected int count;
	
	
	protected String sql;
	
	
	public int getBatchSize()
	{
		return batchSize;
	}

	public void setBatchSize(int batchSize)
	{
		this.batchSize = batchSize;
	}

	public abstract void close();

	public abstract void flush();
}
