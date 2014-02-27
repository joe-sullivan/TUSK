package com.seniordesign.ultimatescorecard.sqlite.hockey;

public class HockeyGameStats {
	long p_id;
	long g_id;
	int shots;
	int sog; //shots on goal
	int goals;
	int ast;
	int pen_minor;
	int pen_major;
	int pen_misconduct;
	int saves;
	int goals_allowed;
	
    // constructors
    public HockeyGameStats() {
    }
 
    public HockeyGameStats(long p_id, long g_id) {
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
    
    public void setpenminor(int pen_minor) {
        this.pen_minor = pen_minor;
    }
    
    public void setpenmajor(int pen_major) {
        this.pen_major = pen_major;
    }
    
    public void setpenmisconduct(int pen_misconduct) {
        this.pen_misconduct = pen_misconduct;
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
    
    public int getshots() {
        return this.shots;
    }
    
    public int getsog() {
        return this.sog;
    }
    
    public int getgoals() {
        return this.goals;
    }
    
    public int getast() {
        return this.ast;
    }
    
    public int getpenminor() {
        return this.pen_minor;
    }
    
    public int getpenmajor() {
        return this.pen_major;
    }
    
    public int getpenmisconduct() {
        return this.pen_misconduct;
    }
    
    public int getsaves() {
        return this.saves;
    }
    
    public int getgoalsallowed() {
        return this.goals_allowed;
    }
    
    public int getpenminutes(){
    	return 2*pen_minor + 5*pen_major + 10*pen_misconduct;
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
