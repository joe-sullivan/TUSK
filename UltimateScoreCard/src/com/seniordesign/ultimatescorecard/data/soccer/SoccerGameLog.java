package com.seniordesign.ultimatescorecard.data.soccer;

import java.util.ArrayList;
import com.seniordesign.ultimatescorecard.data.GameLog;

public class SoccerGameLog extends GameLog{
	private static final long serialVersionUID = 5936447302962843647L;

	public SoccerGameLog(){
		_gameLog = new ArrayList<String>();
	}
	
	public void shootsAndScores(String scorer, String assist){
		if(assist.equals("")){
			_gameLog.add("Goal by "+scorer+". (Unassisted)");
		}
		else{
			_gameLog.add("Goal by "+scorer+". (Assisted by: "+assist+")");
		}
	}
	
	public void shootsAndMisses(String shooter, String goalie){
		if(goalie.equals("")){
			_gameLog.add("Shot missed by "+shooter+".");
		}
		else{
			_gameLog.add("Shot by "+shooter+", saved by " +goalie+".");
		}
	}
	
	public void penaltyCard(String player, boolean red){
		if(red){
			_gameLog.add("Red Card: "+player);
		}
		else{
			_gameLog.add("Yellow Card: "+player);
		}
	}
}
