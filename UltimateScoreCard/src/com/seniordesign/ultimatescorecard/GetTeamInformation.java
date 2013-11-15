package com.seniordesign.ultimatescorecard;

import java.io.Serializable;

//a simple class with hard-coded names
//use right now to for the sake of testing interface
//will be replaced with a class that will retrieve these data from database
//methods for this class very explanatory

public class GetTeamInformation implements Serializable {
	private static final long serialVersionUID = 3390144989945164475L;
	private String _homeTeam, _awayTeam;
	private String _homeAbbr, _awayAbbr;
	private Player[] _homePlayers, _awayPlayers;
	
	public GetTeamInformation(String away, String home){
		_homeTeam = home;
		_awayTeam = away;
		_homeAbbr = setTeamAbbr(_homeTeam);
		_awayAbbr = setTeamAbbr(_awayTeam);
		_homePlayers = setTeamPlayers(_homeTeam);
		_awayPlayers = setTeamPlayers(_awayTeam);
	}
	
	//set the team's player (currently at five)
	private Player[] setTeamPlayers(String team){
		Player[] players = new Player[5];
		if(team.equals("San Antonio Spurs")){
			players[0] = new Player("Tony Parker");
			players[1] = new Player("Manu Ginobili");
			players[2] = new Player("Tiago Splitter");
			players[3] = new Player("Tim Duncan");
			players[4] = new Player("Kawhi Leonard");
		}
		else if(team.equals("Houston Rockets")){
			players[0] = new Player("Jeremy Lin");
			players[1] = new Player("James Harden");
			players[2] = new Player("Omer Asik");
			players[3] = new Player("Dwight Howard");
			players[4] = new Player("Chandler Parson");
		}
		else if(team.equals("Dallas Mavericks")){
			players[0] = new Player("Jose Calderon");
			players[1] = new Player("Monta Ellis");
			players[2] = new Player("Samuel Dalembert");
			players[3] = new Player("Shawn Marion");
			players[4] = new Player("Dirk Nowitzki");
		}
		else if(team.equals("Memphis Grizzlies")){
			players[0] = new Player("Mike Conley");
			players[1] = new Player("Tony Allen");
			players[2] = new Player("Marc Gasol");
			players[3] = new Player("Zach Randolph");
			players[4] = new Player("Quincy Pondexter");
		}
		else {
			players[0] = new Player("Player 1");
			players[1] = new Player("Player 2");
			players[2] = new Player("Player 3");
			players[3] = new Player("Player 4");
			players[4] = new Player("Player 5");
		}
		return players;
	}
	
	//setting a team's abbreviation
	private String setTeamAbbr(String team){
		String abbr;
		if(team.equals("San Antonio Spurs")){
			abbr = "SA";
		}
		else if(team.equals("Houston Rockets")){
			abbr = "HOU";
		}
		else if(team.equals("Dallas Mavericks")){
			abbr = "DAL";
		}
		else if(team.equals("Memphis Grizzlies")){
			abbr = "MEM";
		}
		else {
			abbr = null;
		}
		return abbr;
	}
	
	//getting the name of a player given team name and which player 
	public Player getPlayer(String whichTeam, int player){
		if(whichTeam.equals(_homeTeam)){
			return _homePlayers[player];
		}
		else{
			return _awayPlayers[player];
		}
	}
	
	//getting the name of a player given team name and which player name
	public Player getPlayer(String whichTeam, String player){
		if(whichTeam.equals(_homeTeam)){
			for(int i=0; i<_homePlayers.length; i++){
				if(player.equals(_homePlayers[i].getName())){
					return _homePlayers[i];
				}
			}
		}
		else{
			for(int i=0; i<_awayPlayers.length; i++){
				if(player.equals(_awayPlayers[i].getName())){
					return _awayPlayers[i];
				}
			}
		}
		return null;
	}
	
	//Getter and setter for team names
	public String getHomeTeam(){
		return _homeTeam;
	}
	public void setHomeTeam(String homeTeam){
		_homeTeam = homeTeam;
	}
	public String getAwayTeam(){
		return _awayTeam;
	}
	public void setAwayTeam(String awayTeam){
		_awayTeam = awayTeam;
	}
	
	//Getters for team abbreviations
	public String getHomeAbbr(){
		return _homeAbbr;
	}
	public String getAwayAbbr(){
		return _awayAbbr;
	}
}
