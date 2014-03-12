package com.seniordesign.ultimatescorecard.data.basketball;

import com.seniordesign.ultimatescorecard.data.GameLog;
import com.seniordesign.ultimatescorecard.sqlite.basketball.BasketballDatabaseHelper;
import com.seniordesign.ultimatescorecard.sqlite.helper.PlayByPlay;

public class BasketballGameLog extends GameLog {;
	private static final long serialVersionUID = 1439589895554308837L;
	
	public BasketballGameLog(){
	}
	
	public void shooting(int value, boolean madeMiss, String player){
		if(madeMiss){
			if(value == 3){
				_thePlay = "Three Point made by "+ player;
			}
			else{
				_thePlay = "Two Point made by "+ player;
			}
		}
		else{
			if(value == 3){
				_thePlay = "Three Point missed by "+ player;
			}
			else{
				_thePlay = "Two Point missed by "+ player;
			}
		}
	}
	public void rebounding(String player){
		if(_thePlay.equals(null)){
			_thePlay = "Rebounded by "+ player;
		}
		else{
			_thePlay += " rebounded by "+ player;
		}
	}
	
	public void assisting(String player){
		_thePlay += " assisted by "+ player;
	}
	
	public void stealing (String stlBy, String stlFrom){
		_thePlay = "Stolen by " + stlBy + " from "+ stlFrom;
	}
	
	public void blocking (String blkBy, String blkAgainst){
		_thePlay = blkAgainst + " blocked by " + blkBy ;
	}
	
	public void foulCommitted (String player, int foulType){
		switch(foulType){
		case 1:
			_thePlay = "Personal Foul committed by " + player;
			break;
		case 2:
			_thePlay = "Technical Foul committed by " + player;
			break;
		case 3:
			_thePlay = "Flagrant Foul committed by " + player;
			break;
		default:
			_thePlay = "Foul committed by " + player;
			break;
		}
	}
	
	public void turnover(boolean type, String player, String option){
		if(type){
			_thePlay = "Unforced turnover by "+ player + " ("+ option + ")";
		}
		else{
			_thePlay = "Team turnover";
		}
	}
	
	public void freeThrow(boolean madeMiss, String player){
		if(madeMiss){
			_thePlay = player + " made Free Throw";
		}
		else{
			_thePlay = player + " missed Free Throw";
		}
	}
	
	public void recordActivity(String time){
		if(time.equals("Restart Clock")){
			PlayByPlay pbp = new PlayByPlay(g_id, _thePlay + ".", time, null, 0, 0);
			((BasketballDatabaseHelper) _db).createPlayByPlay(pbp);
		}
		else{
			_timeStamp = time;
			PlayByPlay pbp = new PlayByPlay(g_id,"(" + _timeStamp + ")" + _thePlay + ".", time, null, 0, 0);
			((BasketballDatabaseHelper) _db).createPlayByPlay(pbp);
		}
	}
	
}
