package com.seniordesign.ultimatescorecard.sqlite.football;

public class FootballGameStatsFumbles {
	long p_id;
	long g_id;
	int fmb;
	int fmblost;
	int ff;
	int fr;
	
	// constructors
    public FootballGameStatsFumbles() {
    }
 
    public FootballGameStatsFumbles(long p_id, long g_id) {
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
    
    public void setfmb(int fmb) {
        this.fmb = fmb;
    }
    
    public void setfmblost(int fmblost) {
        this.fmblost = fmblost;
    }
    
    public void setff(int ff) {
        this.ff = ff;
    }
    
    public void setfr(int fr) {
        this.fr = fr;
    }
    
    // getters
    public long getpid() {
        return this.p_id;
    }
    
    public long getgid() {
        return this.g_id;
    }
    
    public int getfmb() {
    	return fmb;
    }
    
    public int getfmblost() {
    	return fmblost;
    }
    
    public int getff() {
    	return ff;
    }
    
    public int getfr() {
    	return fr;
    }
}
