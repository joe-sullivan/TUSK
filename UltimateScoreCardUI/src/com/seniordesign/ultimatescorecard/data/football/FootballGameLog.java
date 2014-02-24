package com.seniordesign.ultimatescorecard.data.football;

import java.util.ArrayList;

import com.seniordesign.ultimatescorecard.data.GameLog;

import android.util.Log;

public class FootballGameLog extends GameLog{
	private static final long serialVersionUID = 8654269066086835286L;
	private String[] _players = new String[2];
	private String _eventType;
	
	public FootballGameLog(){
		_gameLog = new ArrayList<String>();
	}
	
	public void setPlayer1(String player, String event){
		_players[0] = player;
		_eventType = event;
	}
	public void setPlayer2(String player){
		_players[1] = player;
	}
	
	public void formRecord(int yards, String direction){
		if(_eventType.equals("passing")){
			_thePlay = _players[0]+ " passes " +direction+ " to " +_players[1]+ " for gain of " +yards+ " yards.";
		}
		else if (_eventType.equals("rushing")){
			_thePlay = _players[0]+ " rushes " +direction+ " for gain of " +yards+ " yards.";
		}
		else if (_eventType.equals("returning")){
			_thePlay = yards + " yards return for " +_players[0];
		}
		Log.e("The Play", _thePlay);
	}
}
