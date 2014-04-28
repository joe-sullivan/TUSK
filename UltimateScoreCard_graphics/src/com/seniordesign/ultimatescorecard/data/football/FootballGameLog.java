package com.seniordesign.ultimatescorecard.data.football;

import com.seniordesign.ultimatescorecard.data.GameLog;
import com.seniordesign.ultimatescorecard.sqlite.football.FootballDatabaseHelper;
import com.seniordesign.ultimatescorecard.sqlite.helper.PlayByPlay;

import android.util.Log;

public class FootballGameLog extends GameLog{
	private static final long serialVersionUID = 8654269066086835286L;
	private String[] _players = new String[2];
	
	public FootballGameLog(){
	}
	
	public void setPlayer1(String player){
		_players[0] = player;
	}
	public void setPlayer2(String player){
		_players[1] = player;
	}
	
	public void formRecord(String eventType, int yards, String direction){
		if(eventType.equals("passing")){
			_thePlay = _players[0]+ " passes " +direction+ " to " +_players[1]+ " for gain of " +yards+ " yards.";
		}
		else if (eventType.equals("rushing")){
			_thePlay = _players[0]+ " rushes " +direction+ " for gain of " +yards+ " yards.";
		}
		else {
			_thePlay = "";
		}
		Log.e("The Play", _thePlay);
	}
		
	public void formRecord(String eventType, int yards){
		if (eventType.equals("kick return")){
			_thePlay = "Kickoff returned " + yards + " yards by " +_players[0]+".";
		}
		else if (eventType.equals("punt return")){
			_thePlay = "Punt returned " + yards + " yards by " +_players[0]+".";
		}
		else if (eventType.equals("qb sack")){
			_thePlay = _players[1] +" sacked by " + _players[0] + " for a lost of "+ yards + " yards.";
		}
		else if (eventType.equals("int return")){
			_thePlay = "Interception returned" + yards + " yards by " +_players[0]+".";
		}
		else if (eventType.equals("fum return")){
			_thePlay = "Fumbled returned" + yards + " yards by " +_players[0]+".";
		}
		else if (eventType.equals("field goal made")){
			_thePlay = yards + " yards FG made by " +_players[0]+".";
		}
		else if (eventType.equals("field goal miss")){
			_thePlay = yards + " yards FG missed by " +_players[0]+".";
		}
		else{
			_thePlay = "";
		}
		Log.e("The Play", _thePlay);
	}
	
	public void formRecord(String eventType){
		if (eventType.equals("interception")){
			_thePlay = "Interception by "+_players[0];
		}
		else if(eventType.equals("fumble lost")){
			_thePlay = "Fumble lost by "+_players[0] + " recoved by "+_players[1]+".";
		}
		else{
			_thePlay = "";
		}
		Log.e("The Play", _thePlay);
	}
	
	public void recordActivity(String time){
		if(time.equals("Restart Clock")){
			PlayByPlay pbp = new PlayByPlay(g_id, _thePlay + ".", time, null, 0, 0);
			((FootballDatabaseHelper) _db).createPlayByPlay(pbp);
		}
		else{
			_timeStamp = time;
			PlayByPlay pbp = new PlayByPlay(g_id,"(" + _timeStamp + ")" + _thePlay + ".", time, null, 0, 0);
			((FootballDatabaseHelper) _db).createPlayByPlay(pbp);
		}
	}
}
