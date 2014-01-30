package com.seniordesign.ultimatescorecard.data;

import java.util.ArrayList;

public class BasketballTeam extends Team{
	private static final long serialVersionUID = -4547516998953968383L;
	private int _teamFouls = 0;
	private ArrayList<BasketballPlayer> players = new ArrayList<BasketballPlayer>();
	
	public BasketballTeam (String teamName, boolean homeTeam){
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
			players.add(new BasketballPlayer("Tony Parker"));
			players.add(new BasketballPlayer("Manu Ginobili"));
			players.add(new BasketballPlayer("Tiago Splitter"));
			players.add(new BasketballPlayer("Tim Duncan"));
			players.add(new BasketballPlayer("Kawhi Leonard"));
			players.add(new BasketballPlayer("Patty Mills"));
			players.add(new BasketballPlayer("Danny Green"));
			players.add(new BasketballPlayer("Marco Belinelli"));
			players.add(new BasketballPlayer("Boris Diaw"));
			players.add(new BasketballPlayer("Matt Bonner"));
		}
		else if(team.equals("Houston Rockets")){
			players.add(new BasketballPlayer("Jeremy Lin"));
			players.add(new BasketballPlayer("James Harden"));
			players.add(new BasketballPlayer("Omer Asik"));
			players.add(new BasketballPlayer("Dwight Howard"));
			players.add(new BasketballPlayer("Chandler Parson"));
			players.add(new BasketballPlayer("Patrick Beverley"));
			players.add(new BasketballPlayer("Aaron Brooks"));
			players.add(new BasketballPlayer("Omri Casspi"));
			players.add(new BasketballPlayer("Terrence Jones"));
			players.add(new BasketballPlayer("Donatas Motijunas"));
		}
		else if(team.equals("Dallas Mavericks")){
			players.add(new BasketballPlayer("Jose Calderon"));
			players.add(new BasketballPlayer("Monta Ellis"));
			players.add(new BasketballPlayer("Samuel Dalembert"));
			players.add(new BasketballPlayer("Shawn Marion"));
			players.add(new BasketballPlayer("Dirk Nowitzki"));
			players.add(new BasketballPlayer("Devin Harris"));
			players.add(new BasketballPlayer("Vince Carter"));
			players.add(new BasketballPlayer("DeJuan Blair"));
			players.add(new BasketballPlayer("Wayne Ellington"));
			players.add(new BasketballPlayer("Brandon Wright"));
		}
		else if(team.equals("Memphis Grizzlies")){
			players.add(new BasketballPlayer("Mike Conley"));
			players.add(new BasketballPlayer("Tony Allen"));
			players.add(new BasketballPlayer("Marc Gasol"));
			players.add(new BasketballPlayer("Zach Randolph"));
			players.add(new BasketballPlayer("Quincy Pondexter"));
			players.add(new BasketballPlayer("Kosta Koufos"));
			players.add(new BasketballPlayer("Mike Miller"));
			players.add(new BasketballPlayer("Jerryd Bayless"));
			players.add(new BasketballPlayer("Tayshaun Prince"));
			players.add(new BasketballPlayer("Ed Davis"));
			
		}
		else if(team.equals("New Orleans Hornets")){
			players.add(new BasketballPlayer("Jrue Holliday"));
			players.add(new BasketballPlayer("Eric Gordan"));
			players.add(new BasketballPlayer("Jason Smith"));
			players.add(new BasketballPlayer("Anthony Davis"));
			players.add(new BasketballPlayer("Al-Farouq Aminu"));
			players.add(new BasketballPlayer("Austin Rivers"));
			players.add(new BasketballPlayer("Anthony Morrow"));
			players.add(new BasketballPlayer("Tyreke Evans"));
			players.add(new BasketballPlayer("Ryan Anderson"));
			players.add(new BasketballPlayer("Jeff Withey"));
		}
		else {
			players.add(new BasketballPlayer("Player 1"));
			players.add(new BasketballPlayer("Player 2"));
			players.add(new BasketballPlayer("Player 3"));
			players.add(new BasketballPlayer("Player 4"));
			players.add(new BasketballPlayer("Player 5"));
			players.add(new BasketballPlayer("Player 6"));
			players.add(new BasketballPlayer("Player 7"));
			players.add(new BasketballPlayer("Player 8"));
			players.add(new BasketballPlayer("Player 9"));
			players.add(new BasketballPlayer("Player 10"));
		}
	}
	
	public BasketballPlayer getPlayer(int player){
		return players.get(player);
	}
	
	public BasketballPlayer getPlayer(String player){
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
	
	public BasketballPlayer getPlayerOnCourt(String player){
		for(int i=0; i<5; i++){
			if(player.equals(players.get(i).getName())){
				return players.get(i);
			}
		}
		return null;
	}
		
	public void teamFoul(){
		_teamFouls++;
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
			BasketballPlayer temp = players.get(i);
			players.set(i, players.get(j));
			players.set(j, temp);
		}
	}
}
