package com.seniordesign.ultimatescorecard.data.football;

import java.io.Serializable;

import com.seniordesign.ultimatescorecard.sqlite.basketball.BasketballDatabaseHelper;
import com.seniordesign.ultimatescorecard.sqlite.football.FootballDatabaseHelper;
import com.seniordesign.ultimatescorecard.sqlite.helper.Players;

public class FootballPlayer extends Players implements Serializable{
	private static final long serialVersionUID = 8138078955965364135L;
	private String _name;
	private long g_id;
	private int _completion = 0;
	private int _attempt = 0;
	private int _passYards = 0;
	private int _passTD = 0;
	private int _interception = 0;
	private int _sacked = 0;
	private int _rushAtt = 0;
	private int _rushYds = 0;
	private int _rushTD = 0;
	private int _fumbled = 0;
	private int _fumbleLost = 0;
	private int _recCth = 0;
	private int _recYds = 0;
	private int _recTD = 0;
	private int _sacks = 0;
	private int _interceptions = 0;
	private int _forcedFumble = 0;
	private int _tackle = 0;
	private FootballDatabaseHelper db;
	private boolean home;
		
	public FootballPlayer(){
		super();
	}
	
	public FootballPlayer(long g_id, String p_name, int p_num){
		super(g_id, p_name, p_num);
	}
	
	public void setgid(long g_id){
		this.g_id = g_id;
		
		if(db.getGame(g_id).gethomeid()==t_id){
			home=true;
		}
		else{
			home=false;
		}
	}
	
	public void setdb(FootballDatabaseHelper db){
		this.db = db;
	}
	public String getName(){
		return _name;
	}
	public int getCompletion() {
		return _completion;
	}
	public int getAttempt() {
		return _attempt;
	}
	public int getPassYards() {
		return _passYards;
	}
	public int getPassTD() {
		return _passTD;
	}
	public int getInterception() {
		return _interception;
	}
	public int getSacked() {
		return _sacked;
	}
	public int getRushAtt() {
		return _rushAtt;
	}
	public int getRushYds() {
		return _rushYds;
	}
	public int getRushTD() {
		return _rushTD;
	}
	public int getFumbled() {
		return _fumbled;
	}
	public int getFumbleLost() {
		return _fumbleLost;
	}
	public int getRecAtt() {
		return _recCth;
	}
	public int getRecYds() {
		return _recYds;
	}
	public int getRecTD() {
		return _recTD;
	}
	public int getSacks() {
		return _sacks;
	}
	public int getInterceptions() {
		return _interceptions;
	}
	public int getForcedFumble() {
		return _forcedFumble;
	}
	public int getTackle() {
		return _tackle;
	}
	public void passCompleted(){
		_completion++;
	}
	public void passAttempted() {
		_attempt++;;
	}
	public void passGained(int passYards) {
		_passYards += passYards;
	}
	public void passTD() {
		_passTD++;
	}
	public void passIntercepted() {
		_interception++;
	}
	public void sacked() {
		_sacked++;
	}
	public void rushAttempted() {
		_rushAtt++;
	}
	public void rushGained(int rushYds) {
		_rushYds = rushYds;
	}
	public void rushTD() {
		_rushTD++;
	}
	public void fumbled() {
		_fumbled++;
	}
	public void fumbleLost() {
		_fumbleLost++;
	}
	public void recCaught() {
		_recCth++;
	}
	public void recGained(int recYds) {
		_recYds = recYds;
	}
	public void recTD() {
		_recTD++;
	}
	public void sacks() {
		_sacks++;
	}
	public void interception() {
		_interceptions++;
	}
	public void forcedFumble() {
		_forcedFumble++;
	}
	public void tackled() {
		_tackle++;
	}

}
	