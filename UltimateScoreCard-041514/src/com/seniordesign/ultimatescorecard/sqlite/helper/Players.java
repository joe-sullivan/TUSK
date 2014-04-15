package com.seniordesign.ultimatescorecard.sqlite.helper;

import java.io.Serializable;

public class Players implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3055625701304433457L;
	protected long p_id;
	protected long t_id;
	protected String p_name;
    protected int p_num;
 
    // constructors
    public Players() {
    }
 
    public Players(String p_name, int p_num) {
        this.p_name = p_name;
        this.p_num = p_num;
    }
    
    public Players(long t_id, String p_name, int p_num) {
        this.t_id = t_id;
        this.p_name = p_name;
        this.p_num = p_num;
    }
 
    public Players(long p_id, long t_id, String p_name, int p_num) {
        this.p_id = p_id;
        this.t_id = t_id;
        this.p_name = p_name;
        this.p_num = p_num;
    }
 
    // setters
    public void setpid(long p_id) {
        this.p_id = p_id;
    }
    
    public void settid(long t_id) {
        this.t_id = t_id;
    }
 
    public void setpname(String p_name) {
        this.p_name = p_name;
    }
 
    public void setpnum(int p_num) {
        this.p_num = p_num;
    }

 
    // getters
    public long getpid() {
        return this.p_id;
    }
    
    public long gettid() {
        return this.t_id;
    }
 
    public String getpname() {
        return this.p_name;
    }
 
    public int getpnum() {
        return this.p_num;
    }
    
}
