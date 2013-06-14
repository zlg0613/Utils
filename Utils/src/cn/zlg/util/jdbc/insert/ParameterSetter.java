package cn.zlg.util.jdbc.insert;

import java.sql.PreparedStatement;

public interface ParameterSetter<T> {

	public void setParameter(T t,PreparedStatement pst);
}
