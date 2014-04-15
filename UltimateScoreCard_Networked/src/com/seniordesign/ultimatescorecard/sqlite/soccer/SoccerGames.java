package com.seniordesign.ultimatescorecard.sqlite.soccer;

import com.seniordesign.ultimatescorecard.sqlite.helper.Games;

public class SoccerGames extends Games{

	private static final long serialVersionUID = -4169498672945890878L;
	int home_shots;
	int home_sog; //shots on goal
	int home_goals;
	int home_ast;
	int home_fouls;
	int home_pka; //penalty kick attempts
	int home_pkg; //penalty kick goals
	int home_offside;
	int home_ycard;
	int home_rcard;
	int home_save_opps;
	int home_saves;
	int home_goals_allowed;
    
	int away_shots;
	int away_sog; //shots on goal
	int away_goals;
	int away_ast;
	int away_fouls;
	int away_pka; //penalty kick attempts
	int away_pkg; //penalty kick goals
	int away_offside;
	int away_ycard;
	int away_rcard;
	int away_save_opps;
	int away_saves;
	int away_goals_allowed;
    
    
   public void sethomeshots(int shots) {
        this.home_shots = shots;
    }
    
    public void sethomesog(int sog) {
        this.home_sog = sog;
    }
    
    public void sethomegoals(int goals) {
        this.home_goals = goals;
    }
    
    public void sethomeast(int ast) {
        this.home_ast = ast;
    }
    
    public void sethomefouls(int fouls) {
        this.home_fouls = fouls;
    }
    
    public void sethomepka(int pka) {
        this.home_pka = pka;
    }
    
    public void sethomepkg(int pkg) {
        this.home_pkg = pkg;
    }
    
    public void sethomeoffside(int offside) {
        this.home_offside = offside;
    }
    
    public void sethomeycard(int ycard) {
        this.home_ycard = ycard;
    }
    
    public void sethomercard(int rcard) {
        this.home_rcard = rcard;
    }
    
    public void sethomesave_opps(int save_opps) {
        this.home_save_opps = save_opps;
    }
    
    public void sethomesaves(int saves) {
        this.home_saves = saves;
    }
    
    public void sethomegoalsallowed(int goals_allowed) {
        this.home_goals_allowed = goals_allowed;
    }

   public void setawayshots(int shots) {
        this.away_shots = shots;
    }
    
    public void setawaysog(int sog) {
        this.away_sog = sog;
    }
    
    public void setawaygoals(int goals) {
        this.away_goals = goals;
    }
    
    public void setawayast(int ast) {
        this.away_ast = ast;
    }
    
    public void setawayfouls(int fouls) {
        this.away_fouls = fouls;
    }
    
    public void setawaypka(int pka) {
        this.away_pka = pka;
    }
    
    public void setawaypkg(int pkg) {
        this.away_pkg = pkg;
    }
    
    public void setawayoffside(int offside) {
        this.away_offside = offside;
    }
    
    public void setawayycard(int ycard) {
        this.away_ycard = ycard;
    }
    
    public void setawayrcard(int rcard) {
        this.away_rcard = rcard;
    }
    
    public void setawaysave_opps(int save_opps) {
        this.away_save_opps = save_opps;
    }
    
    public void setawaysaves(int saves) {
        this.away_saves = saves;
    }
    
    public void setawaygoalsallowed(int goals_allowed) {
        this.away_goals_allowed = goals_allowed;
    }
    
    public long gethomeshots() {
        return this.home_shots;
    }
    
    public long gethomesog() {
        return this.home_sog;
    }
    
    public long gethomegoals() {
        return this.home_goals;
    }
    
    public long gethomeast() {
        return this.home_ast;
    }
    
    public long gethomefouls() {
        return this.home_fouls;
    }
    
    public long gethomepka() {
        return this.home_pka;
    }
    
    public long gethomepkg() {
        return this.home_pkg;
    }
    
    public long gethomeoffside() {
        return this.home_offside;
    }
    
    public long gethomeycard() {
        return this.home_ycard;
    }
    
    public long gethomercard() {
        return this.home_rcard;
    }
    
    public long gethomesaveopps() {
        return this.home_save_opps;
    }
    
    public long gethomesaves() {
        return this.home_saves;
    }
    
    public long gethomegoalsallowed() {
        return this.home_goals_allowed;
    }
    
    public double gethomesavepercent(){
    	if((home_saves+home_goals_allowed)>0){
    		return home_saves/(home_saves+home_goals_allowed);
    	}
    	else{
    		return 0.0;
    	}
    }
    
    public long getawayshots() {
        return this.away_shots;
    }
    
    public long getawaysog() {
        return this.away_sog;
    }
    
    public long getawaygoals() {
        return this.away_goals;
    }
    
    public long getawayast() {
        return this.away_ast;
    }
    
    public long getawayfouls() {
        return this.away_fouls;
    }
    
    public long getawaypka() {
        return this.away_pka;
    }
    
    public long getawaypkg() {
        return this.away_pkg;
    }
    
    public long getawayoffside() {
        return this.away_offside;
    }
    
    public long getawayycard() {
        return this.away_ycard;
    }
    
    public long getawayrcard() {
        return this.away_rcard;
    }
    
    public long getawaysaveopps() {
        return this.away_save_opps;
    }
    
    public long getawaysaves() {
        return this.away_saves;
    }
    
    public long getawaygoalsallowed() {
        return this.away_goals_allowed;
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
