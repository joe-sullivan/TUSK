package com.seniordesign.ultimatescorecard.sqlite.football;

public class FootballGameStatsKicking {
	long p_id;
	long g_id;
	int xpm;
	int xpa;
	int fgm;
	int fga;
	
	// constructors
    public FootballGameStatsKicking() {
    }
 
    public FootballGameStatsKicking(long p_id, long g_id) {
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
    
    public void setxpm(int xpm) {
        this.xpm = xpm;
    }
    
    public void setxpa(int xpa) {
        this.xpa = xpa;
    }
    
    public void setfgm(int fgm) {
        this.fgm = fgm;
    }
    
    public void setfga(int fga) {
        this.fga = fga;
    }
    
    // getters
    public long getpid() {
        return this.p_id;
    }
    
    public long getgid() {
        return this.g_id;
    }
    
    public int getxpm() {
    	return xpm;
    }
    
    public int getxpa() {
    	return xpa;
    }
    
    public int getfgm() {
    	return fgm;
    }
    
    public int getfga() {
    	return fga;
    }
}
