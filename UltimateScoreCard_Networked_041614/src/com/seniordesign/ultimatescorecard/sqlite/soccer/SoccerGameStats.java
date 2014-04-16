package com.seniordesign.ultimatescorecard.sqlite.soccer;

public class SoccerGameStats {
	long p_id;
	long g_id;
	int shots;
	int sog; //shots on goal
	int goals;
	int ast;
	int fouls;
	int pka; //penalty kick attempts
	int pkg; //penalty kick goals
	int offside;
	int ycard;
	int rcard;
	int save_opps;
	int saves;
	int goals_allowed;
	
    // constructors
    public SoccerGameStats() {
    }
 
    public SoccerGameStats(long p_id, long g_id) {
        this.p_id = p_id;
        this.g_id = g_id;
    }
 
    // setters
    public void setpid(long p_id) {
        this.p_id = p_id;
    }
    
    public void setgid(long g_id) {
        this.g_id = g_id;
    }
    
    public void setshots(int shots) {
        this.shots = shots;
    }
    
    public void setsog(int sog) {
        this.sog = sog;
    }
    
    public void setgoals(int goals) {
        this.goals = goals;
    }
    
    public void setast(int ast) {
        this.ast = ast;
    }
    
    public void setfouls(int fouls) {
        this.fouls = fouls;
    }
    
    public void setpka(int pka) {
        this.pka = pka;
    }
    
    public void setpkg(int pkg) {
        this.pkg = pkg;
    }
    
    public void setoffside(int offside) {
        this.offside = offside;
    }
    
    public void setycard(int ycard) {
        this.ycard = ycard;
    }
    
    public void setrcard(int rcard) {
        this.rcard = rcard;
    }
    
    public void setsave_opps(int save_opps) {
        this.save_opps = save_opps;
    }
    
    public void setsaves(int saves) {
        this.saves = saves;
    }
    
    public void setgoalsallowed(int goals_allowed) {
        this.goals_allowed = goals_allowed;
    }
 
    // getters
    public long getpid() {
        return this.p_id;
    }
    
    public long getgid() {
        return this.g_id;
    }
    
    public long getshots() {
        return this.shots;
    }
    
    public long getsog() {
        return this.sog;
    }
    
    public long getgoals() {
        return this.goals;
    }
    
    public long getast() {
        return this.ast;
    }
    
    public long getfouls() {
        return this.fouls;
    }
    
    public long getpka() {
        return this.pka;
    }
    
    public long getpkg() {
        return this.pkg;
    }
    
    public long getoffside() {
        return this.offside;
    }
    
    public long getycard() {
        return this.ycard;
    }
    
    public long getrcard() {
        return this.rcard;
    }
    
    public long getsaveopps() {
        return this.save_opps;
    }
    
    public long getsaves() {
        return this.saves;
    }
    
    public long getgoalsallowed() {
        return this.goals_allowed;
    }
    
    public double getsavepercent(){
    	if((saves+goals_allowed)>0){
    		return saves/(saves+goals_allowed);
    	}
    	else{
    		return 0.0;
    	}
    }
}
