package com.seniordesign.ultimatescorecard.data.hockey;

import com.seniordesign.ultimatescorecard.data.GameLog;
import com.seniordesign.ultimatescorecard.sqlite.helper.PlayByPlay;
import com.seniordesign.ultimatescorecard.sqlite.hockey.HockeyDatabaseHelper;

public class HockeyGameLog extends GameLog{
	private static final long serialVersionUID = -8980053401644889731L;
	private String _thePlay;
	
	public HockeyGameLog(){
	}
	
	public void shootsAndScores(String scorer, String assist1, String assist2, String time){
		if(assist1.equals("") && assist2.equals("")){
			_thePlay = "Goal by "+scorer+". (Unassisted)";
		}
		else if(assist2.equals("")){
			_thePlay = "Goal by "+scorer+". (Assisted by: "+assist1+")";
		}
		else{
			_thePlay = "Goal by "+scorer+". (Assisted by: "+assist1+", "+assist2+")";
		}
		recordActivity(time);
	}
	
	public void shootsAndMisses(String shooter, String goalie, String time){
		if(goalie.equals("")){
			_thePlay = "Shot missed by "+shooter+".";
		}
		else{
			_thePlay = "Shot by "+shooter+", saved by " +goalie+".";
		}
		recordActivity(time);
	}
	
	public void penaltyShot(boolean goal, String shooter, String goalie, String time){
		if(goalie.equals("")){
			_thePlay = "Penalty: "+shooter+" scores.";
		}
		else{
			_thePlay = "Penalty: "+goalie+" saves.";
		}
		recordActivity(time);
	}
	
	public void penalty(String player, String penalty, String time){
		_thePlay = "Penalty for "+player+". ("+penalty+")";
		recordActivity(time);
	}
	
	public void recordActivity(String time){
		if(time.equals("Restart Clock")){
			PlayByPlay pbp = new PlayByPlay(g_id, _thePlay + ".", time, null, 0, 0);
			((HockeyDatabaseHelper) _db).createPlayByPlay(pbp);
		}
		else{
			_timeStamp = time;
			PlayByPlay pbp = new PlayByPlay(g_id,"(" + _timeStamp + ")" + _thePlay + ".", time, null, 0, 0);
			((HockeyDatabaseHelper) _db).createPlayByPlay(pbp);
		}
	}
}
