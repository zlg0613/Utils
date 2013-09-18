package cn.zlg.designpatterns;


public interface Filter {
	
	public void init();
	
	public void filter(FilterChain fc,Object args);

}
