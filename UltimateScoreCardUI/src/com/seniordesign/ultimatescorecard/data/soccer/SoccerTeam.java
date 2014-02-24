package com.seniordesign.ultimatescorecard.data.soccer;

import java.util.ArrayList;

import com.seniordesign.ultimatescorecard.data.Team;

public class SoccerTeam extends Team{
	private static final long serialVersionUID = -6026271836806435470L;
	private ArrayList<SoccerPlayer> players = new ArrayList<SoccerPlayer>();
	
	public SoccerTeam (String teamName, boolean homeTeam){
		super(teamName, homeTeam);
		setTeamAbbr(teamName);
		setTeamPlayers(teamName);
	}
	
	private void setTeamAbbr(String team){
		if(team.equals("United States")){
			_teamAbbr = "USA";
		}
		else if(team.equals("Spain")){
			_teamAbbr = "ESP";
		}
		else if(team.equals("Argentina")){
			_teamAbbr = "ARG";
		}
		else if(team.equals("Germany")){
			_teamAbbr = "GER";
		}
		else if(team.equals("Brazil")){
			_teamAbbr = "BRZ";
		}
		else if(team.equals("England")){
			_teamAbbr = "ENG";
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
		if(team.equals("United States")){
			players.add(new SoccerPlayer("Tim Howard"));
			players.add(new SoccerPlayer("DaMarcus Beasley"));
			players.add(new SoccerPlayer("Oguchi Onyewu"));
			players.add(new SoccerPlayer("Clarence Goodson"));
			players.add(new SoccerPlayer("Michael Bradley"));
			players.add(new SoccerPlayer("Maurice Edu"));
			players.add(new SoccerPlayer("Kyle Beckerman"));
			players.add(new SoccerPlayer("Benny Feilhaber"));
			players.add(new SoccerPlayer("Landon Donovan"));
			players.add(new SoccerPlayer("Clint Dempsey"));
			players.add(new SoccerPlayer("Jozy Altidore"));
		}
		else if(team.equals("Spain")){
			players.add(new SoccerPlayer("Iker Casillas"));
			players.add(new SoccerPlayer("Sergio Ramos"));
			players.add(new SoccerPlayer("Alvaro Arbeloa"));
			players.add(new SoccerPlayer("Raul Albiol"));
			players.add(new SoccerPlayer("Xabi Alonso"));
			players.add(new SoccerPlayer("Andres Iniesta"));
			players.add(new SoccerPlayer("Sergio Busquets"));
			players.add(new SoccerPlayer("Santi Cazorla"));
			players.add(new SoccerPlayer("David Villa"));
			players.add(new SoccerPlayer("Pedro"));
		}
		else if(team.equals("Argentina")){
			players.add(new SoccerPlayer("Sergio Romero"));
			players.add(new SoccerPlayer("Pablo Zabaleta"));
			players.add(new SoccerPlayer("Federico Fernandez"));
			players.add(new SoccerPlayer("Marco Rojo"));
			players.add(new SoccerPlayer("Fernando Gago"));
			players.add(new SoccerPlayer("Angel Di Maria"));
			players.add(new SoccerPlayer("Javier Mascherano"));
			players.add(new SoccerPlayer("Ever Benega"));
			players.add(new SoccerPlayer("Ricardo Alvarez"));
			players.add(new SoccerPlayer("Gonzalo Higuain"));
			players.add(new SoccerPlayer("Lionel Messi"));
		}
		else if(team.equals("Germany")){
			players.add(new SoccerPlayer("Rene Adler"));
			players.add(new SoccerPlayer("Philipp Lahm"));
			players.add(new SoccerPlayer("Per Mertesacker"));
			players.add(new SoccerPlayer("Jerome Boateng"));
			players.add(new SoccerPlayer("Heiko Westermann"));
			players.add(new SoccerPlayer("Bastian Schweisteiger"));
			players.add(new SoccerPlayer("Mesut Ozil"));
			players.add(new SoccerPlayer("Thomas Muller"));
			players.add(new SoccerPlayer("Mario Gotze"));
			players.add(new SoccerPlayer("Marco Reus"));
			players.add(new SoccerPlayer("Max Kruse"));
			
		}
		else if(team.equals("Brazil")){
			players.add(new SoccerPlayer("Juilo Cesar"));
			players.add(new SoccerPlayer("Thaigo Silva"));
			players.add(new SoccerPlayer("Dani Alves"));
			players.add(new SoccerPlayer("David Luiz"));
			players.add(new SoccerPlayer("Marcelo"));
			players.add(new SoccerPlayer("Ramires"));
			players.add(new SoccerPlayer("Oscar"));
			players.add(new SoccerPlayer("Luis Gustavo"));
			players.add(new SoccerPlayer("Paulinho"));
			players.add(new SoccerPlayer("Robinho"));
			players.add(new SoccerPlayer("Neymar"));
		}
		else if(team.equals("England")){
			players.add(new SoccerPlayer("Joe Hart"));
			players.add(new SoccerPlayer("Ashley Cole"));
			players.add(new SoccerPlayer("Glen Johnson"));
			players.add(new SoccerPlayer("Phil Jagielka"));
			players.add(new SoccerPlayer("Gary Cahill"));
			players.add(new SoccerPlayer("Steven Gerrard"));
			players.add(new SoccerPlayer("Frank Lampard"));
			players.add(new SoccerPlayer("James Milner"));
			players.add(new SoccerPlayer("Wayne Rooney"));
			players.add(new SoccerPlayer("Jermaine Defoe"));
			players.add(new SoccerPlayer("Daniel Sturridge"));
		}
		else {
			players.add(new SoccerPlayer("Goalkeeper"));
			players.add(new SoccerPlayer("Defenseman 1"));
			players.add(new SoccerPlayer("Defenseman 2"));
			players.add(new SoccerPlayer("Defenseman 3"));
			players.add(new SoccerPlayer("Defenseman 4"));
			players.add(new SoccerPlayer("Midfielder 1"));
			players.add(new SoccerPlayer("Midfielder 2"));
			players.add(new SoccerPlayer("Midfielder 3"));
			players.add(new SoccerPlayer("Midfielder 4"));
			players.add(new SoccerPlayer("Forward 1"));
			players.add(new SoccerPlayer("Forward 2"));
		}
	}
	
	public SoccerPlayer getPlayer(int player){
		return players.get(player);
	}
	
	public SoccerPlayer getPlayer(String player){
		for(int i=0; i<players.size(); i++){
			if(player.equals(players.get(i).getName())){
				return players.get(i);
			}
		}
		return null;
	}	
	public int numberPlayers(){
		return players.size();
	}	
	public ArrayList<SoccerPlayer> getRoster(){
		return players;
	}
}
