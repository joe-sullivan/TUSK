package com.seniordesign.ultimatescorecard.data;

import java.io.Serializable;
import java.util.ArrayList;

public class Team implements Serializable{
	private static final long serialVersionUID = -4547516998953968383L;
	private String _teamName;
	private String _teamAbbr;
	private boolean _homeTeam;
	private int _score = 0, _teamFouls = 0, _teamTurnovers = 0;
	private ArrayList<Player> players = new ArrayList<Player>();
	
	public Team (String teamName, boolean homeTeam){
		_homeTeam = homeTeam;
		_teamName = teamName;
		setTeamAbbr(teamName);
		setTeamPlayers(teamName);	
	}
	
	private void setTeamAbbr(String team){
		if(team.equals("San Antonio Spurs")){
			_teamAbbr = "SA";
		}
		else if(team.equals("Houston Rockets")){
			_teamAbbr = "HOU";
		}
		else if(team.equals("Dallas Mavericks")){
			_teamAbbr = "DAL";
		}
		else if(team.equals("Memphis Grizzlies")){
			_teamAbbr = "MEM";
		}
		else if(team.equals("New Orleans Pelicans")){
			_teamAbbr = "MEM";
		}
		else {
			if(_homeTeam){
				_teamAbbr = "Home";
			}
			else{
				_teamAbbr = "Away";
			}
		}
	}
	
	private void setTeamPlayers(String team){
		if(team.equals("San Antonio Spurs")){
			players.add(new Player("Tony Parker"));
			players.add(new Player("Manu Ginobili"));
			players.add(new Player("Tiago Splitter"));
			players.add(new Player("Tim Duncan"));
			players.add(new Player("Kawhi Leonard"));
			players.add(new Player("Patty Mills"));
			players.add(new Player("Danny Green"));
			players.add(new Player("Marco Belinelli"));
			players.add(new Player("Boris Diaw"));
			players.add(new Player("Matt Bonner"));
		}
		else if(team.equals("Houston Rockets")){
			players.add(new Player("Jeremy Lin"));
			players.add(new Player("James Harden"));
			players.add(new Player("Omer Asik"));
			players.add(new Player("Dwight Howard"));
			players.add(new Player("Chandler Parson"));
			players.add(new Player("Patrick Beverley"));
			players.add(new Player("Aaron Brooks"));
			players.add(new Player("Omri Casspi"));
			players.add(new Player("Terrence Jones"));
			players.add(new Player("Donatas Motijunas"));
		}
		else if(team.equals("Dallas Mavericks")){
			players.add(new Player("Jose Calderon"));
			players.add(new Player("Monta Ellis"));
			players.add(new Player("Samuel Dalembert"));
			players.add(new Player("Shawn Marion"));
			players.add(new Player("Dirk Nowitzki"));
			players.add(new Player("Devin Harris"));
			players.add(new Player("Vince Carter"));
			players.add(new Player("DeJuan Blair"));
			players.add(new Player("Wayne Ellington"));
			players.add(new Player("Brandon Wright"));
		}
		else if(team.equals("Memphis Grizzlies")){
			players.add(new Player("Mike Conley"));
			players.add(new Player("Tony Allen"));
			players.add(new Player("Marc Gasol"));
			players.add(new Player("Zach Randolph"));
			players.add(new Player("Quincy Pondexter"));
			players.add(new Player("Kosta Koufos"));
			players.add(new Player("Mike Miller"));
			players.add(new Player("Jerryd Bayless"));
			players.add(new Player("Tayshaun Prince"));
			players.add(new Player("Ed Davis"));
			
		}
		else if(team.equals("New Orleans Hornets")){
			players.add(new Player("Jrue Holliday"));
			players.add(new Player("Eric Gordan"));
			players.add(new Player("Jason Smith"));
			players.add(new Player("Anthony Davis"));
			players.add(new Player("Al-Farouq Aminu"));
			players.add(new Player("Austin Rivers"));
			players.add(new Player("Anthony Morrow"));
			players.add(new Player("Tyreke Evans"));
			players.add(new Player("Ryan Anderson"));
			players.add(new Player("Jeff Withey"));
		}
		else {
			players.add(new Player("Player 1"));
			players.add(new Player("Player 2"));
			players.add(new Player("Player 3"));
			players.add(new Player("Player 4"));
			players.add(new Player("Player 5"));
			players.add(new Player("Player 6"));
			players.add(new Player("Player 7"));
			players.add(new Player("Player 8"));
			players.add(new Player("Player 9"));
			players.add(new Player("Player 10"));
		}
	}
	
	public Player getPlayer(int player){
		return players.get(player);
	}
	
	public Player getPlayer(String player){
		for(int i=0; i<players.size(); i++){
			if(player.equals(players.get(i).getName())){
				return players.get(i);
			}
		}
		return null;
	}
	
	public int getPlayerAsPositionInArray(String player){
		for(int i=0; i<players.size(); i++){
			if(player.equals(players.get(i).getName())){
				return i;
			}
		}
		return -1;
	}
	
	public Player getPlayerOnCourt(String player){
		for(int i=0; i<5; i++){
			if(player.equals(players.get(i).getName())){
				return players.get(i);
			}
		}
		return null;
	}
	
	public String getTeamAbbr(){
		return _teamAbbr;
	}
	
	public String getTeamName(){
		return _teamName;
	}
	
	public boolean isHomeTeam(){
		return _homeTeam;
	}
	
	public void increaseScore(int points){
		_score += points;
	}
	
	public void teamFoul(){
		_teamFouls++;
	}
	
	public void teamTurnover(){
		_teamTurnovers++;
	}
	
	public int getScore(){
		return _score;
	}
	
	public int getTeamFouls(){
		return _teamFouls;
	}
	
	public int getTeamTurnovers(){
		return _teamTurnovers;
	}
	
	public int numberPlayers(){
		return players.size();
	}
	
	public void scoreChange(int points, String playerName){
		increaseScore(points);
		if(points == 3){
			getPlayer(playerName).madeThree();
		}
		else if(points == 2){
			getPlayer(playerName).madeTwo();
		}
		else{
			getPlayer(playerName).madeFreeThrow();
		}
	}
	
	public void swapPlayer(String playerIn, String playerOut){
		int i = getPlayerAsPositionInArray(playerIn);
		int j = getPlayerAsPositionInArray(playerOut);
		if(i >= 0 && i<players.size() && j >= 0 && j<players.size()){
			Player temp = players.get(i);
			players.set(i, players.get(j));
			players.set(j, temp);
		}
	}
}
