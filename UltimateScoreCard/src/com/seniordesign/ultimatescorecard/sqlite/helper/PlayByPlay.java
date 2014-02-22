package com.seniordesign.ultimatescorecard.sqlite.helper;

import java.io.Serializable;

public class PlayByPlay implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6664362784320016803L;
	long a_id;
	long g_id;
	String action;
    String time;
    String period;
    int home_score;
    int away_score;
 
    // constructors
    public PlayByPlay() {
    }
 
    public PlayByPlay(long g_id, String action, String time, String period, int home_score, int away_score) {
        this.g_id = g_id;
        this.action = action;
        this.time = time;
        this.period = period;
        this.home_score = home_score;
        this.away_score = away_score;
    }
 
    public PlayByPlay(long a_id, long g_id, String action, String time, String period, int home_score, int away_score) {
        this.a_id = a_id;
    	this.g_id = g_id;
        this.action = action;
        this.time = time;
        this.period = period;
        this.home_score = home_score;
        this.away_score = away_score;
    }
 
    // setters
    public void setaid(long a_id) {
        this.a_id = a_id;
    }
    
    public void setgid(long g_id) {
        this.g_id = g_id;
    }
    
    public void setaction(String action) {
        this.action = action;
    }
    
    public void settime(String time) {
        this.time = time;
    }
    
    public void setperiod(String period) {
        this.period = period;
    }
    
    public void sethomescore(int home_score) {
        this.home_score = home_score;
    }
    
    public void setawayscore(int away_score) {
        this.away_score = away_score;
    }
 
    // getters
    public long getaid() {
        return this.a_id;
    }
    
    public long getgid() {
        return this.g_id;
    }
    
    public String getaction() {
        return this.action;
    }
    
    public String gettime() {
        return this.time;
    }
    
    public String getperiod() {
        return this.period;
    }
    
    public int gethomescore() {
        return this.home_score;
    }
    
    public int getawayscore() {
        return this.away_score;
    }
    
    
}
