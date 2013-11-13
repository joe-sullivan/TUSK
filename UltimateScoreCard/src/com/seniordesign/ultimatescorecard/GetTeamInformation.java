package com.seniordesign.ultimatescorecard;

public class GetTeamInformation {
	
	public String abbreviation (String teamName){
		if(teamName.equals("San Antonio Spurs")){
			return "SA Spurs";
		}
		else if(teamName.equals("Houston Rockets")){
			return "HOU Rockets";
		}
		else if(teamName.equals("Los Angeles Lakers")){
			return "LA Lakers";
		}
		else if(teamName.equals("New York Knicks")){
			return "NY Knicks";
		}
		else if(teamName.equals("Uconn Huskies")){
			return "UConn";
		}
		else{
			return null;
		}
	}
	
	public String player1 (String teamName){
		if(teamName.equals("San Antonio Spurs")){
			return "Tony Parker";
		}
		else if(teamName.equals("Houston Rockets")){
			return "Jeremy Lin";
		}
		else if(teamName.equals("Los Angeles Lakers")){
			return "Steve Nash";
		}
		else if(teamName.equals("New York Knicks")){
			return "Raymond Felton";
		}
		else if(teamName.equals("Uconn Huskies")){
			return "Shabazz Napier";
		}
		else{
			return "Player 1";
		}
	}
	
	public String player2 (String teamName){
		if(teamName.equals("San Antonio Spurs")){
			return "Manu Ginobili";
		}
		else if(teamName.equals("Houston Rockets")){
			return "James Harden";
		}
		else if(teamName.equals("Los Angeles Lakers")){
			return "Kobe Bryant";
		}
		else if(teamName.equals("New York Knicks")){
			return "JR Smith";
		}
		else if(teamName.equals("Uconn Huskies")){
			return "Ryan Boatright";
		}
		else{
			return "Player 2";
		}
	}
	
	public String player3 (String teamName){
		if(teamName.equals("San Antonio Spurs")){
			return "Tiago Splitter";
		}
		else if(teamName.equals("Houston Rockets")){
			return "Omer Asik";
		}
		else if(teamName.equals("Los Angeles Lakers")){
			return "Pau Gasol";
		}
		else if(teamName.equals("New York Knicks")){
			return "Tyson Chandler";
		}
		else if(teamName.equals("Uconn Huskies")){
			return "Amida Brimah";
		}
		else{
			return "Player 3";
		}
	}
	
	public String player4 (String teamName){
		if(teamName.equals("San Antonio Spurs")){
			return "Tim Duncan";
		}
		else if(teamName.equals("Houston Rockets")){
			return "Dwight Howard";
		}
		else if(teamName.equals("Los Angeles Lakers")){
			return "Chris Kaman";
		}
		else if(teamName.equals("New York Knicks")){
			return "Carmelo Anthony";
		}
		else if(teamName.equals("Uconn Huskies")){
			return "DeAndre Daniels";
		}
		else{
			return "Player 4";
		}
	}
	
	public String player5 (String teamName){
		if(teamName.equals("San Antonio Spurs")){
			return "Kawhi Leonard";
		}
		else if(teamName.equals("Houston Rockets")){
			return "Chandler Parson";
		}
		else if(teamName.equals("Los Angeles Lakers")){
			return "Nick Young";
		}
		else if(teamName.equals("New York Knicks")){
			return "Iman Shumpert";
		}
		else if(teamName.equals("Uconn Huskies")){
			return "Omar Calhoun";
		}
		else{
			return "Player 5";
		}
	}
}
