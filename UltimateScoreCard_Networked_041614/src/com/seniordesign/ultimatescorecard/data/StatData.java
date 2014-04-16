package com.seniordesign.ultimatescorecard.data;

public class StatData {
	private long _pid, _gid;
	private int _value;
	private String _stat;
	
	public StatData(long gid, long pid, String stat, int value){
		_gid = gid;
		_pid = pid;
		_value = value;
		_stat = stat;
	}
	
	public long getgid(){
		return _gid;
	}
	
	public long getpid(){
		return _pid;
	}
	
	public int getvalue(){
		return _value;
	}
	
	public String getstat(){
		return _stat;
	}
	
	public void setvalue(int value){
		_value = value;
	}
}
