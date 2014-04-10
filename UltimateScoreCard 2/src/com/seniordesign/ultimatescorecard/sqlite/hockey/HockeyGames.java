package com.seniordesign.ultimatescorecard.sqlite.hockey;

import com.seniordesign.ultimatescorecard.sqlite.helper.Games;

public class HockeyGames extends Games{

	private static final long serialVersionUID = 1106760649533960977L;
	int home_shots;
	int home_sog; //shots on goal
	int home_goals;
	int home_ast;
	int home_pen_minor;
	int home_pen_major;
	int home_pen_misconduct;
	int home_saves;
	int home_goals_allowed;
    
	int away_shots;
	int away_sog; //shots on goal
	int away_goals;
	int away_ast;
	int away_pen_minor;
	int away_pen_major;
	int away_pen_misconduct;
	int away_saves;
	int away_goals_allowed;
    
    
   public void sethomeshots(int home_shots) {
        this.home_shots = home_shots;
    }
    
    public void sethomesog(int home_sog) {
        this.home_sog = home_sog;
    }
    
    public void sethomegoals(int home_goals) {
        this.home_goals = home_goals;
    }
    
    public void sethomeast(int home_ast) {
        this.home_ast = home_ast;
    }
    
    public void sethomepenminor(int home_pen_minor) {
        this.home_pen_minor = home_pen_minor;
    }
    
    public void sethomepenmajor(int home_pen_major) {
        this.home_pen_major = home_pen_major;
    }
    
    public void sethomepenmisconduct(int home_pen_misconduct) {
        this.home_pen_misconduct = home_pen_misconduct;
    }
    
    public void sethomesaves(int home_saves) {
        this.home_saves = home_saves;
    }
    
    public void sethomegoalsallowed(int home_goals_allowed) {
        this.home_goals_allowed = home_goals_allowed;
    }
	    
    public void setawayshots(int away_shots) {
        this.away_shots = away_shots;
    }
    
    public void setawaysog(int away_sog) {
        this.away_sog = away_sog;
    }
    
    public void setawaygoals(int away_goals) {
        this.away_goals = away_goals;
    }
    
    public void setawayast(int away_ast) {
        this.away_ast = away_ast;
    }
    
    public void setawaypenminor(int away_pen_minor) {
        this.away_pen_minor = away_pen_minor;
    }
    
    public void setawaypenmajor(int away_pen_major) {
        this.away_pen_major = away_pen_major;
    }
    
    public void setawaypenmisconduct(int away_pen_misconduct) {
        this.away_pen_misconduct = away_pen_misconduct;
    }
    
    public void setawaysaves(int away_saves) {
        this.away_saves = away_saves;
    }
    
    public void setawaygoalsallowed(int away_goals_allowed) {
        this.away_goals_allowed = away_goals_allowed;
    }
	
    public int gethomeshots() {
        return this.home_shots;
    }
    
    public int gethomesog() {
        return this.home_sog;
    }
    
    public int gethomegoals() {
        return this.home_goals;
    }
    
    public int gethomeast() {
        return this.home_ast;
    }
    
    public int gethomepenminor() {
        return this.home_pen_minor;
    }
    
    public int gethomepenmajor() {
        return this.home_pen_major;
    }
    
    public int gethomepenmisconduct() {
        return this.home_pen_misconduct;
    }
    
    public int gethomesaves() {
        return this.home_saves;
    }
    
    public int gethomegoalsallowed() {
        return this.home_goals_allowed;
    }
    
    public int gethomepenminutes(){
    	return 2*home_pen_minor + 5*home_pen_major + 10*home_pen_misconduct;
    }
    
    public double gethomesavepercent(){
    	if((home_saves+home_goals_allowed)>0){
    		return home_saves/(home_saves+home_goals_allowed);
    	}
    	else{
    		return 0.0;
    	}
    }
    
    public int getawayshots() {
        return this.away_shots;
    }
    
    public int getawaysog() {
        return this.away_sog;
    }
    
    public int getawaygoals() {
        return this.away_goals;
    }
    
    public int getawayast() {
        return this.away_ast;
    }
    
    public int getawaypenminor() {
        return this.away_pen_minor;
    }
    
    public int getawaypenmajor() {
        return this.away_pen_major;
    }
    
    public int getawaypenmisconduct() {
        return this.away_pen_misconduct;
    }
    
    public int getawaysaves() {
        return this.away_saves;
    }
    
    public int getawaygoalsallowed() {
        return this.away_goals_allowed;
    }
    
    public int getawaypenminutes(){
    	return 2*away_pen_minor + 5*away_pen_major + 10*away_pen_misconduct;
    }
    
    public double getawaysavepercent(){
    	if((away_saves+away_goals_allowed)>0){
    		return away_saves/(away_saves+away_goals_allowed);
    	}
    	else{
    		return 0.0;
    	}
    }
    
	public String getHomeScoreText(){
		if(home_goals < 10){
			return "00"+ home_goals;
		}
		else if (home_goals < 100){
			return "0"+ home_goals;
		}
		else {
			return ""+ home_goals;
		}
	}
	
	public String getAwayScoreText(){
		if(away_goals < 10){
			return "00"+ away_goals;
		}
		else if (away_goals < 100){
			return "0"+ away_goals;
		}
		else {
			return ""+ away_goals;
		}
	}
}
