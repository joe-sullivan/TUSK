package com.seniordesign.ultimatescorecard.sqlite.helper;

import java.io.Serializable;

public class ShotChartCoords implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4638578020745992118L;
	long shot_id;
	long g_id;
	long p_id;
	long t_id;
    int x;
    int y;
    String made;
 
    // constructors
    public ShotChartCoords() {
    }
 
    public ShotChartCoords(long g_id, long p_id, long t_id, int x, int y, String made) {
        this.g_id = g_id;
        this.p_id = p_id;
        this.t_id = t_id;
        this.x = x;
        this.y = y;
        this.made = made;
    }
 
    public ShotChartCoords(long shot_id, long g_id, long p_id, long t_id, int x, int y, String made) {
        this.shot_id = shot_id;
    	this.g_id = g_id;
        this.p_id = p_id;
        this.t_id = t_id;
        this.x = x;
        this.y = y;
        this.made = made;
    }
 
    // setters
    public void setshotid(long shot_id) {
        this.shot_id = shot_id;
    }
    
    public void setgid(long g_id) {
        this.g_id = g_id;
    }
    
    public void setpid(long p_id) {
        this.p_id = p_id;
    }
    
    public void settid(long t_id) {
        this.t_id = t_id;
    }
    
    public void setx(int x) {
        this.x = x;
    }
    
    public void sety(int y) {
        this.y = y;
    }
    
    public void setmade(String made) {
        this.made= made;
    }
 
    // getters
    public long getshotid() {
        return this.shot_id;
    }
    
    public long getgid() {
        return this.g_id;
    }
    
    public long getpid() {
        return this.p_id;
    }
    
    public long gettid() {
        return this.t_id;
    }
 
    public int getx() {
        return this.x;
    }
    
    public int gety() {
        return this.y;
    }
    
    public String getmade() {
        return this.made;
    }
}
