package com.seniordesign.ultimatescorecard.sqlite.helper;

import java.io.Serializable;

public class Teams implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4152772242438315795L;
	long t_id;
	String t_name;
	String abbv;
	String c_name;
    String sport;
 
    // constructors
    public Teams() {
    }
 
    public Teams(String t_name, String abbv, String c_name, String sport) {
        this.t_name = t_name;
        this.abbv = abbv;
        this.c_name = c_name;
        this.sport = sport;
    }
 
    public Teams(long t_id, String t_name, String abbv, String c_name, String sport) {
        this.t_id = t_id;
    	this.t_name = t_name;
    	this.abbv = abbv;
    	this.c_name = c_name;
        this.sport = sport;
    }
 
    // setters
    public void settid(long t_id) {
        this.t_id = t_id;
    }
    
    public void settname(String t_name) {
        this.t_name = t_name;
    }
    
    public void setabbv(String abbv) {
        this.abbv = abbv;
    }
    
    public void setcname(String c_name) {
        this.c_name = c_name;
    }
 
    public void setsport(String sport) {
        this.sport = sport;
    }
 
    // getters
    public long gettid() {
        return this.t_id;
    }
    
    public String gettname() {
        return this.t_name;
    }
    
    public String getabbv() {
        return this.abbv;
    }
    
    public String getcname() {
        return this.c_name;
    }
 
    public String getSport() {
        return this.sport;
    }
}
