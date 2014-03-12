package com.seniordesign.ultimatescorecard.data.soccer;

import com.seniordesign.ultimatescorecard.data.GameLog;
import com.seniordesign.ultimatescorecard.sqlite.helper.PlayByPlay;
import com.seniordesign.ultimatescorecard.sqlite.soccer.SoccerDatabaseHelper;

public class SoccerGameLog extends GameLog{
	private static final long serialVersionUID = 5936447302962843647L;
	private String _thePlay;
	
	public SoccerGameLog(){
	}
	
	public void shootsAndScores(String scorer, String assist, String time){
		if(assist.equals("")){
			_thePlay = "Goal by "+scorer+". (Unassisted)";
		}
		else{
			_thePlay = "Goal by "+scorer+". (Assisted by: "+assist+")";
		}
		recordActivity(time);
	}
	
	public void shootsAndMisses(String shooter, String goalie, String time){
		if(goalie.equals("")){
			_thePlay = "Shot missed by "+shooter+".";
		}
		else{
			_thePlay = "Shot on goal by "+shooter+", saved by " +goalie+".";
		}
		recordActivity(time);
	}
	
	public void penaltyCard(String player, boolean red, String time){
		if(red){
			_thePlay = "Red Card: "+player;
		}
		else{
			_thePlay = "Yellow Card: "+player;
		}
		recordActivity(time);

	}
	
	public void recordActivity(String time){
		if(time.equals("Restart Clock")){
			PlayByPlay pbp = new PlayByPlay(g_id, _thePlay + ".", time, null, 0, 0);
			((SoccerDatabaseHelper) _db).createPlayByPlay(pbp);
		}
		else{
			_timeStamp = time;
			PlayByPlay pbp = new PlayByPlay(g_id,"(" + _timeStamp + ")" + _thePlay + ".", time, null, 0, 0);
			((SoccerDatabaseHelper) _db).createPlayByPlay(pbp);
		}
	}
}
