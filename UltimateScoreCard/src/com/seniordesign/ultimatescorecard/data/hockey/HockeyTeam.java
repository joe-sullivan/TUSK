package com.seniordesign.ultimatescorecard.data.hockey;

import java.util.ArrayList;

import com.seniordesign.ultimatescorecard.data.Team;
import com.seniordesign.ultimatescorecard.data.basketball.BasketballPlayer;
import com.seniordesign.ultimatescorecard.sqlite.helper.Teams;

public class HockeyTeam extends Team{
	private static final long serialVersionUID = -6287487346904566941L;
	private ArrayList<HockeyPlayer> players = new ArrayList<HockeyPlayer>();
	private HockeyPlayer _goaltender; 
	private Teams _team;
	private long _t_id, _g_id;

	public HockeyTeam (String teamName, boolean homeTeam){
		super(teamName, homeTeam);
		_homeTeam = homeTeam;
		_teamName = teamName;
	}
	
	public void setTeamAbbr(){
		_teamAbbr = _team.getabbv();

		/*
		if(team.equals("LA Kings")){
			_teamAbbr = "LA";
		}
		else if(team.equals("New York Rangers")){
			_teamAbbr = "NYR";
		}
		else if(team.equals("Boston Bruins")){
			_teamAbbr = "BOS";
		}
		else if(team.equals("Chicago Blackhawks")){
			_teamAbbr = "CHI";
		}
		else if(team.equals("Pittsburgh Penguins")){
			_teamAbbr = "PIT";
		}
		else if(team.equals("San Jose Sharks")){
			_teamAbbr = "SJ";
		}
		else {
			if(_homeTeam){
				_teamAbbr = "Home";
			}
			else{
				_teamAbbr = "Away";
			}
		}
		*/
	}
	
	public void setgid(long g_id){
		_g_id = g_id;
		setTeamPlayers(players);	
	}
	
	public void setData(long g_id, Teams team, ArrayList<HockeyPlayer> players){
		_g_id = g_id;
		_team = team;
		this.players = players;
		setTeamPlayers(players);
		_goaltender = players.get(0);

	}
	
	private void setTeamPlayers(ArrayList<HockeyPlayer> players){
		//use string to get t_id, then get players on that team
		for(HockeyPlayer p: players){
			p.setgid(_g_id);
		}
	}
	
	/*
	private void setTeamPlayers(String team){
		if(team.equals("LA Kings")){
			players.add(new HockeyPlayer("Jonathan Quick"));
			players.add(new HockeyPlayer("Drew Doughty"));
			players.add(new HockeyPlayer("Slava Voynov"));
			players.add(new HockeyPlayer("Willie Mitchell"));
			players.add(new HockeyPlayer("Anze Kopitar"));
			players.add(new HockeyPlayer("Jeff Carter"));
			players.add(new HockeyPlayer("Dwight King"));
			players.add(new HockeyPlayer("Kyle Clifford"));
			players.add(new HockeyPlayer("Justin Williams"));
			players.add(new HockeyPlayer("Dustin Brown"));
		}
		else if(team.equals("New York Rangers")){
			players.add(new HockeyPlayer("Henrik Lundqvist"));
			players.add(new HockeyPlayer("Dan Girardi"));
			players.add(new HockeyPlayer("Ryan McDonagh"));
			players.add(new HockeyPlayer("Marc Stall"));
			players.add(new HockeyPlayer("Brad Richards"));
			players.add(new HockeyPlayer("Rick Nash"));
			players.add(new HockeyPlayer("Daniel Carcillo"));
			players.add(new HockeyPlayer("Ryan Callahan"));
			players.add(new HockeyPlayer("Mats Zuccarello"));
		}
		else if(team.equals("Boston Bruins")){
			players.add(new HockeyPlayer("Tuukka Rask"));
			players.add(new HockeyPlayer("Johnny Boychuk"));
			players.add(new HockeyPlayer("Zdeno Chara"));
			players.add(new HockeyPlayer("Matt Bartkowski"));
			players.add(new HockeyPlayer("Patrice Bergeron"));
			players.add(new HockeyPlayer("Carl Soderberg"));
			players.add(new HockeyPlayer("Jordan Caron"));
			players.add(new HockeyPlayer("Loui Eriksson"));
			players.add(new HockeyPlayer("Jarome Iginla"));
			players.add(new HockeyPlayer("Reilly Smith"));
		}
		else if(team.equals("Chicago Blackhawks")){
			players.add(new HockeyPlayer("Corey Crawford"));
			players.add(new HockeyPlayer("Duncan Keith"));
			players.add(new HockeyPlayer("Brent Seabrook"));
			players.add(new HockeyPlayer("Niklas Hjalmarsson"));
			players.add(new HockeyPlayer("Jonathan Toews"));
			players.add(new HockeyPlayer("Andrew Shaw"));
			players.add(new HockeyPlayer("Patrick Sharp"));
			players.add(new HockeyPlayer("Brandon Saad"));
			players.add(new HockeyPlayer("Patrick Kane"));
			players.add(new HockeyPlayer("Kris Versteeg"));
			
		}
		else if(team.equals("Pittsburgh Penguins")){
			players.add(new HockeyPlayer("Marc-Andre Fleury"));
			players.add(new HockeyPlayer("Paul Martin"));
			players.add(new HockeyPlayer("Kris Letang"));
			players.add(new HockeyPlayer("Brooks Orpik"));
			players.add(new HockeyPlayer("Sidney Crosby"));
			players.add(new HockeyPlayer("Evgeni Malkin"));
			players.add(new HockeyPlayer("James Neal"));
			players.add(new HockeyPlayer("Chris Kunitz"));
			players.add(new HockeyPlayer("Pascal Dupuis"));
			players.add(new HockeyPlayer("Matt D'Agostini"));
		}
		
		else if(team.equals("San Jose Sharks")){
			players.add(new HockeyPlayer("Antti Niemi"));
			players.add(new HockeyPlayer("Justin Braun"));
			players.add(new HockeyPlayer("Dan Boyle"));
			players.add(new HockeyPlayer("Marc-Edouard Vlasic"));
			players.add(new HockeyPlayer("Patrick Marleau"));
			players.add(new HockeyPlayer("Joe Pavelski"));
			players.add(new HockeyPlayer("Matt Nieto"));
			players.add(new HockeyPlayer("James Sheppard"));
			players.add(new HockeyPlayer("Brent Burns"));
			players.add(new HockeyPlayer("Martin Havlat"));
		}
		
		else {
			players.add(new HockeyPlayer("Goaltender"));
			players.add(new HockeyPlayer("Defenseman 1"));
			players.add(new HockeyPlayer("Defenseman 2"));
			players.add(new HockeyPlayer("Defenseman 3"));
			players.add(new HockeyPlayer("Center 1"));
			players.add(new HockeyPlayer("Center 2"));
			players.add(new HockeyPlayer("Center 3"));
			players.add(new HockeyPlayer("Left Wing 1"));
			players.add(new HockeyPlayer("Left Wing 2"));
			players.add(new HockeyPlayer("Right Wing 1"));
			players.add(new HockeyPlayer("Right Wing 2"));
		}
	}
	*/
	
	public HockeyPlayer getPlayer(int player){
		return players.get(player);
	}
	
	public HockeyPlayer getPlayer(String player){
		for(int i=0; i<players.size(); i++){
			if(player.equals(players.get(i).getpname())){
				return players.get(i);
			}
		}
		return null;
	}
	
	public int numberPlayers(){
		return players.size();
	}
	
	public ArrayList<HockeyPlayer> getRoster(){
		return players;
	}
	
	public HockeyPlayer getGoalie(){
		return _goaltender;
	}
	public void setGoalie(HockeyPlayer player){
		_goaltender = player;
	}
}
