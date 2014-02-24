package com.seniordesign.ultimatescorecard.data;

import java.io.Serializable;
import java.util.ArrayList;

public class GameLog implements Serializable{
	private static final long serialVersionUID = 2437099151521151044L;
	protected ArrayList<String> _gameLog;
	protected int _logSize = 0;
	protected String _thePlay;
	protected String _timeStamp;	
	
	public void removeFromLog(int items){
		for(int i=0; i<items; i++){
			_gameLog.remove(_gameLog.size()-1);
		}
	}
	
	public ArrayList<String> getGameLog(){
		return _gameLog;
	}
	
	public void setLogSize(){
		_logSize = _gameLog.size();
	}
	
	public int getLogSize(){
		return _logSize;
	}
	
	public void recordActivity(String time){
		if(time.equals("Restart Clock")){
			_gameLog.add(_thePlay+ ".");
		}
		else{
			_timeStamp = time;
			_gameLog.add("(" + _timeStamp + ")" + _thePlay+ ".");
		}
	}
}
