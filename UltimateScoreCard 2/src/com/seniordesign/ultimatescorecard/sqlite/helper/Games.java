package com.seniordesign.ultimatescorecard.sqlite.helper;

import java.io.Serializable;

public class Games implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3260781852604994461L;
	long g_id;
	long home_id;
    long away_id;
    String date;
 
    // constructors
    public Games() {
    }
 
    public Games(long home_id, long away_id, String date) {
        this.home_id = home_id;
        this.away_id = away_id;
        this.date = date;
    }
 
    public Games(long g_id, long home_id, long away_id, String date) {
        this.g_id = g_id;
    	this.home_id = home_id;
        this.away_id = away_id;
        this.date = date;
    }
 
    // setters
    public void setgid(long g_id) {
        this.g_id = g_id;
    }
    
    public void sethomeid(long home_id) {
        this.home_id = home_id;
    }
    
    public void setawayid(long away_id) {
        this.away_id = away_id;
    }
 
    public void setDate(String date) {
        this.date = date;
    }
 
    // getters
    public long getgid() {
        return this.g_id;
    }
    
    public long gethomeid() {
        return this.home_id;
    }
    
    public long getawayid() {
        return this.away_id;
    }
    
    public String getDate() {
        return this.date;
    }	
}
