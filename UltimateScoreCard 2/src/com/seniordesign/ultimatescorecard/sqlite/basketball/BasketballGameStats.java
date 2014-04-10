package com.seniordesign.ultimatescorecard.sqlite.basketball;

import java.io.Serializable;

public class BasketballGameStats implements Serializable {
	private static final long serialVersionUID = 600722506563740291L;
	long p_id;
	long g_id;
	int pts;
	int fgm;
	int fga;
	int fgm3;
	int fga3;
	int ftm;
	int fta;
	int oreb;
	int dreb;
    int ast;
    int stl;
    int blk;
    int to;
    int pf;
    int tech;
    int flagrant;
    
    // constructors
    public BasketballGameStats() {
    }
 
    public BasketballGameStats(long p_id, long g_id) {
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
    
    public void setpts(int pts) {
        this.pts = pts;
    }
    
    public void setfgm(int fgm) {
        this.fgm = fgm;
    }
    
    public void setfga(int fga) {
        this.fga = fga;
    }
    
    public void setfgm3(int fgm3) {
        this.fgm3 = fgm3;
    }
    
    public void setfga3(int fga3) {
        this.fga3 = fga3;
    }
    
    public void setftm(int ftm) {
        this.ftm = ftm;
    }
    
    public void setfta(int fta) {
        this.fta = fta;
    }
    
    public void setoreb(int oreb) {
        this.oreb = oreb;
    }
    
    public void setdreb(int dreb) {
        this.dreb = dreb;
    }
    
    public void setast(int ast) {
        this.ast = ast;
    }
    
    public void setstl(int stl) {
        this.stl = stl;
    }
    
    public void setblk(int blk) {
        this.blk = blk;
    }
    
    public void setto(int to) {
        this.to = to;
    }
 
    public void setpf(int pf) {
        this.pf = pf;
    }
    
    public void settech(int tech) {
        this.tech = tech;
    }
    
    public void setflagrant(int flagrant) {
        this.flagrant = flagrant;
    }
 
    // getters
    public long getpid() {
        return this.p_id;
    }
    
    public long getgid() {
        return this.g_id;
    }
    
    public int getpts() {
        return this.pts;
    }
    
    public int getfgm() {
        return this.fgm;
    }
    
    public int getfga() {
        return this.fga;
    }
    
    public int getfgm3() {
        return this.fgm3;
    }
    
    public int getfga3() {
        return this.fga3;
    }
    
    public int getftm() {
        return this.ftm;
    }
    
    public int getfta() {
        return this.fta;
    }
    
    public int getoreb() {
        return this.oreb;
    }
    
    public int getdreb() {
        return this.dreb;
    }
    
    public int getast() {
        return this.ast;
    }
    
    public int getstl() {
        return this.stl;
    }
    
    public int getblk() {
        return this.blk;
    }
    
    public int getto() {
        return this.to;
    }
    
    public int getpf() {
        return this.pf;
    }
    
    public int gettech() {
        return this.tech;
    }
    
    public int getflagrant() {
        return this.flagrant;
    }
    
	public double getfgpercent(){
		if(fga > 0){
			return (double) fgm / fga;
		}
		else{
			return 0.0;
		}
	}
	
	public double getfg3percent(){
		if(fga3 > 0){
			return (double) fgm3 / fga3;
		}
		else{
			return 0.0;
		}
	}
	
	public double getftpercent(){
		if(fta > 0){
			return (double) ftm / fta;
		}
		else{
			return 0.0;
		}
	}
}
