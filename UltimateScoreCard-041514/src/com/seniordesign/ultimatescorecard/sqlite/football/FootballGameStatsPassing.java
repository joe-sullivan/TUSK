package com.seniordesign.ultimatescorecard.sqlite.football;

public class FootballGameStatsPassing {
	long p_id;
	long g_id;
	int cmp;
	int att;
	int yds;
	int tds;
	int ints;
	int twopc;
	
	// constructors
    public FootballGameStatsPassing() {
    }
 
    public FootballGameStatsPassing(long p_id, long g_id) {
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
    
    public void setcmp(int cmp) {
        this.cmp = cmp;
    }
    
    public void setatt(int att) {
        this.att = att;
    }
    
    public void setyds(int yds) {
        this.yds = yds;
    }
    
    public void settds(int tds) {
        this.tds = tds;
    }
    
    public void setints(int ints) {
        this.ints = ints;
    }
    
    public void settwopc(int twopc) {
        this.twopc = twopc;
    }
    
    // getters
    public long getpid() {
        return this.p_id;
    }
    
    public long getgid() {
        return this.g_id;
    }
    
    public int getcmp() {
    	return cmp;
    }
    
    public int getatt() {
    	return att;
    }
    
    public int getyds() {
    	return yds;
    }
    
    public int gettds() {
    	return tds;
    }
    
    public int getints() {
    	return ints;
    }
    
    public int gettwopc() {
    	return twopc;
    }
}
