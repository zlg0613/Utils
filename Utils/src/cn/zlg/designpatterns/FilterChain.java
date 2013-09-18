package cn.zlg.designpatterns;

import java.util.List;

import cn.zlg.util.New;

public class FilterChain {

	private List<Filter> filters = New.arraylist();
	private int currentIndex = 0;
	
	public void init(){
		for(Filter f:filters){
			f.init();
		}
	}
	public void addFilter(Filter f){
		filters.add(f);
	}
	
	public Filter remove(int i){
		if(i<filters.size()){
			return filters.remove(i);
		}
		return null;
	}
	public boolean remove(Filter f){
		return filters.remove(f);
	}
	
	public void doFilter(Object args){
		if(currentIndex < filters.size()){
			Filter f = filters.get(currentIndex);
			currentIndex ++;
			f.filter(this, args);
		}
	}
	public void resetPointer(){
		currentIndex = 0;
	}
}
