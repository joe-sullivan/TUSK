package com.seniordesign.ultimatescorecard.sqlite.basketball;

import com.seniordesign.ultimatescorecard.sqlite.helper.Games;

public class BasketballGames extends Games{

	private static final long serialVersionUID = 5090276336298642304L;
	
	int home_pts;
	int home_fgm;
	int home_fga;
	int home_fgm3;
	int home_fga3;
	int home_ftm;
	int home_fta;
	int home_oreb;
	int home_dreb;
    int home_ast;
    int home_stl;
    int home_blk;
    int home_to;
    int home_pf;
    int home_tech;
    int home_flagrant;
    
	int away_pts;
	int away_fgm;
	int away_fga;
	int away_fgm3;
	int away_fga3;
	int away_ftm;
	int away_fta;
	int away_oreb;
	int away_dreb;
    int away_ast;
    int away_stl;
    int away_blk;
    int away_to;
    int away_pf;
    int away_tech;
    int away_flagrant;
    
    
    public void sethomepts(int home_pts) {
        this.home_pts = home_pts;
    }
    
    public void sethomefgm(int home_fgm) {
        this.home_fgm = home_fgm;
    }
    
    public void sethomefga(int home_fga) {
        this.home_fga = home_fga;
    }
    
    public void sethomefgm3(int home_fgm3) {
        this.home_fgm3 = home_fgm3;
    }
    
    public void sethomefga3(int home_fga3) {
        this.home_fga3 = home_fga3;
    }
    
    public void sethomeftm(int home_ftm) {
        this.home_ftm = home_ftm;
    }
    
    public void sethomefta(int home_fta) {
        this.home_fta = home_fta;
    }
    
    public void sethomeoreb(int home_oreb) {
        this.home_oreb = home_oreb;
    }
    
    public void sethomedreb(int home_dreb) {
        this.home_dreb = home_dreb;
    }
    
    public void sethomeast(int home_ast) {
        this.home_ast = home_ast;
    }
    
    public void sethomestl(int home_stl) {
        this.home_stl = home_stl;
    }
    
    public void sethomeblk(int home_blk) {
        this.home_blk = home_blk;
    }
    
    public void sethometo(int home_to) {
        this.home_to = home_to;
    }
 
    public void sethomepf(int home_pf) {
        this.home_pf = home_pf;
    }
    
    public void sethometech(int home_tech) {
        this.home_tech = home_tech;
    }
    
    public void sethomeflagrant(int home_flagrant) {
        this.home_flagrant = home_flagrant;
    }
    
    public void setawaypts(int away_pts) {
        this.away_pts = away_pts;
    }
    
    public void setawayfgm(int away_fgm) {
        this.away_fgm = away_fgm;
    }
    
    public void setawayfga(int away_fga) {
        this.away_fga = away_fga;
    }
    
    public void setawayfgm3(int away_fgm3) {
        this.away_fgm3 = away_fgm3;
    }
    
    public void setawayfga3(int away_fga3) {
        this.away_fga3 = away_fga3;
    }
    
    public void setawayftm(int away_ftm) {
        this.away_ftm = away_ftm;
    }
    
    public void setawayfta(int away_fta) {
        this.away_fta = away_fta;
    }
    
    public void setawayoreb(int away_oreb) {
        this.away_oreb = away_oreb;
    }
    
    public void setawaydreb(int away_dreb) {
        this.away_dreb = away_dreb;
    }
    
    public void setawayast(int away_ast) {
        this.away_ast = away_ast;
    }
    
    public void setawaystl(int away_stl) {
        this.away_stl = away_stl;
    }
    
    public void setawayblk(int away_blk) {
        this.away_blk = away_blk;
    }
    
    public void setawayto(int away_to) {
        this.away_to = away_to;
    }
 
    public void setawaypf(int away_pf) {
        this.away_pf = away_pf;
    }
    
    public void setawaytech(int away_tech) {
        this.away_tech = away_tech;
    }
    
    public void setawayflagrant(int away_flagrant) {
        this.away_flagrant = away_flagrant;
    }
	
    public int gethomepts() {
        return this.home_pts;
    }
    
    public int gethomefgm() {
        return this.home_fgm;
    }
    
    public int gethomefga() {
        return this.home_fga;
    }
    
    public int gethomefgm3() {
        return this.home_fgm3;
    }
    
    public int gethomefga3() {
        return this.home_fga3;
    }
    
    public int gethomeftm() {
        return this.home_ftm;
    }
    
    public int gethomefta() {
        return this.home_fta;
    }
    
    public int gethomeoreb() {
        return this.home_oreb;
    }
    
    public int gethomedreb() {
        return this.home_dreb;
    }
    
    public int gethomeast() {
        return this.home_ast;
    }
    
    public int gethomestl() {
        return this.home_stl;
    }
    
    public int gethomeblk() {
        return this.home_blk;
    }
    
    public int gethometo() {
        return this.home_to;
    }
    
    public int gethomepf() {
        return this.home_pf;
    }
    
    public int gethometech() {
        return this.home_tech;
    }
    
    public int gethomeflagrant() {
        return this.home_flagrant;
    }
    
	public double gethomefgpercent(){
		if(home_fga > 0){
			return (double) home_fgm / home_fga;
		}
		else{
			return 0.0;
		}
	}
	
	public double gethomeftpercent(){
		if(home_fta > 0){
			return (double) home_ftm / home_fta;
		}
		else{
			return 0.0;
		}
	}
	
	public double gethomefg3percent(){
		if(home_fga3 > 0){
			return (double) home_fgm3 / home_fga3;
		}
		else{
			return 0.0;
		}
	}

    public int getawaypts() {
        return this.away_pts;
    }
    
    public int getawayfgm() {
        return this.away_fgm;
    }
    
    public int getawayfga() {
        return this.away_fga;
    }
    
    public int getawayfgm3() {
        return this.away_fgm3;
    }
    
    public int getawayfga3() {
        return this.away_fga3;
    }
    
    public int getawayftm() {
        return this.away_ftm;
    }
    
    public int getawayfta() {
        return this.away_fta;
    }
    
    public int getawayoreb() {
        return this.away_oreb;
    }
    
    public int getawaydreb() {
        return this.away_dreb;
    }
    
    public int getawayast() {
        return this.away_ast;
    }
    
    public int getawaystl() {
        return this.away_stl;
    }
    
    public int getawayblk() {
        return this.away_blk;
    }
    
    public int getawayto() {
        return this.away_to;
    }
    
    public int getawaypf() {
        return this.away_pf;
    }
    
    public int getawaytech() {
        return this.away_tech;
    }
    
    public int getawayflagrant() {
        return this.away_flagrant;
    }
    
	public double getawayfgpercent(){
		if(away_fga > 0){
			return (double) away_fgm / away_fga;
		}
		else{
			return 0.0;
		}
	}
	
	public double getawayfg3percent(){
		if(away_fga3 > 0){
			return (double) away_fgm3 / away_fga3;
		}
		else{
			return 0.0;
		}
	}
	
	public double getawayftpercent(){
		if(away_fta > 0){
			return (double) away_ftm / away_fta;
		}
		else{
			return 0.0;
		}
	}
	
	public String getHomeScoreText(){
		if(home_pts < 10){
			return "00"+ home_pts;
		}
		else if (home_pts < 100){
			return "0"+ home_pts;
		}
		else {
			return ""+ home_pts;
		}
	}
	
	public String getAwayScoreText(){
		if(away_pts < 10){
			return "00"+ away_pts;
		}
		else if (away_pts < 100){
			return "0"+ away_pts;
		}
		else {
			return ""+ away_pts;
		}
	}
}
