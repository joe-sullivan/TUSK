package com.seniordesign.ultimatescorecard.sqlite.football;

public class FootballGameStatsPunting {
	long p_id;
	long g_id;
	int punts;
	int yds;
	int in20;
	int tbs;
	
	// constructors
    public FootballGameStatsPunting() {
    }
 
    public FootballGameStatsPunting(long p_id, long g_id) {
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
    
    public void setpunts(int punts) {
        this.punts = punts;
    }
    
    public void setyds(int yds) {
        this.yds = yds;
    }
    
    public void setin20(int in20) {
        this.in20 = in20;
    }
    
    public void settbs(int tbs) {
        this.tbs = tbs;
    }

    // getters
    public long getpid() {
        return this.p_id;
    }
    
    public long getgid() {
        return this.g_id;
    }
    
    public int getpunts() {
    	return punts;
    }
    
    public int getyds() {
    	return yds;
    }
    
    public int getin20() {
    	return in20;
    }
    
    public int gettbs() {
    	return tbs;
    }
}
