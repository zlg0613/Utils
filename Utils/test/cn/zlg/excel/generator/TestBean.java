package cn.zlg.excel.generator;

import cn.zlg.excel.parser.annotation.ExcelColumn;
import cn.zlg.excel.parser.annotation.ExcelMapperBean;

@ExcelMapperBean
public class TestBean {

	@ExcelColumn(value=0,title="ID")
	private int id;
	
	@ExcelColumn(value=1,title="年龄")
	private int age;
	@ExcelColumn(value=3,title="名字")
	private String name;
	@ExcelColumn(value=6,title="薪水")
	private double salary;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getSalary() {
		return salary;
	}
	public void setSalary(double salary) {
		this.salary = salary;
	}
	
	
}
