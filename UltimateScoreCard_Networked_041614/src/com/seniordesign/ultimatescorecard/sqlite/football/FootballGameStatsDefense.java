package com.seniordesign.ultimatescorecard.sqlite.football;

public class FootballGameStatsDefense {
	long p_id;
	long g_id;
	int tackles;
	int tfl;
	int sacks;
	int ints;
	int tds;
	
	// constructors
    public FootballGameStatsDefense() {
    }
 
    public FootballGameStatsDefense(long p_id, long g_id) {
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
    
    public void settackles(int tackles) {
        this.tackles = tackles;
    }
    
    public void settfl(int tfl) {
        this.tfl = tfl;
    }
    
    public void setsacks(int sacks) {
        this.sacks = sacks;
    }
    
    public void setints(int ints) {
        this.ints = ints;
    }
    
    public void settds(int tds) {
        this.tds = tds;
    }

    
    // getters
    public long getpid() {
        return this.p_id;
    }
    
    public long getgid() {
        return this.g_id;
    }
    
    public int gettackles() {
    	return tackles;
    }
    
    public int gettfl() {
    	return tfl;
    }
    
    public int getsacks() {
    	return sacks;
    }
    
    public int getints() {
    	return ints;
    }
    
    public int gettds() {
    	return tds;
    }
}
