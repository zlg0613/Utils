package cn.zlg.util;

public class Timer {
	private long startTime;
	private long endTime;
	
	public Timer(){
		this.start();
	}
	public void start(){
		this.startTime = System.currentTimeMillis();
	}
	
	public void end(){
		this.endTime = System.currentTimeMillis();
	}

	public long time(){
		return (endTime-startTime);
	}
	public long getStartTime() {
		return startTime;
	}
	public long getEndTime() {
		return endTime;
	}
}
