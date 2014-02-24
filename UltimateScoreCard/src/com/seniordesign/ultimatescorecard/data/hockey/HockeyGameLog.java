package com.seniordesign.ultimatescorecard.data.hockey;

import java.util.ArrayList;

import com.seniordesign.ultimatescorecard.data.GameLog;

public class HockeyGameLog extends GameLog{
	private static final long serialVersionUID = -8980053401644889731L;

	public HockeyGameLog(){
		_gameLog = new ArrayList<String>();
	}
	
	public void shootsAndScores(String scorer, String assist1, String assist2){
		if(assist1.equals("") && assist2.equals("")){
			_gameLog.add("Goal by "+scorer+". (Unassisted)");
		}
		else if(assist2.equals("")){
			_gameLog.add("Goal by "+scorer+". (Assisted by: "+assist1+")");
		}
		else{
			_gameLog.add("Goal by "+scorer+". (Assisted by: "+assist1+", "+assist2+")");
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
	
	public void penaltyShot(boolean goal, String shooter, String goalie){
		if(goalie.equals("")){
			_gameLog.add("Penalty: "+shooter+" scores.");
		}
		else{
			_gameLog.add("Penalty: "+goalie+" saves.");
		}
	}
	
	public void penalty(String player, String penalty){
		_gameLog.add("Penalty for "+player+". ("+penalty+")");
	}
}
