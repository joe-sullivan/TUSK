package com.seniordesign.ultimatescorecard.sqlite.football;

public class FootballGameStatsReturns {
	long p_id;
	long g_id;
	int krt;
	int kyds;
	int ktds;
	int prt;
	int pyds;
	int ptds;
	
	// constructors
    public FootballGameStatsReturns() {
    }
 
    public FootballGameStatsReturns(long p_id, long g_id) {
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
    
    public void setkrt(int krt) {
        this.krt = krt;
    }
    
    public void setkyds(int kyds) {
        this.kyds = kyds;
    }
    
    public void setktds(int ktds) {
        this.ktds = ktds;
    }
    
    public void setprt(int prt) {
        this.prt = prt;
    }
    
    public void setpyds(int pyds) {
        this.pyds = pyds;
    }
    
    public void setptds(int ptds) {
        this.ptds = ptds;
    }
    
    // getters
    public long getpid() {
        return this.p_id;
    }
    
    public long getgid() {
        return this.g_id;
    }
    
    public int getkrt() {
    	return krt;
    }
    
    public int getkyds() {
    	return kyds;
    }
    
    public int getktds() {
    	return ktds;
    }
    
    public int getprt() {
    	return prt;
    }
    
    public int getpyds() {
    	return pyds;
    }
    
    public int getptds() {
    	return ptds;
    }
}
