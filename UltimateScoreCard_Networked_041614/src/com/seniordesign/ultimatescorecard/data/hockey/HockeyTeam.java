package com.seniordesign.ultimatescorecard.data.hockey;

import java.util.ArrayList;

import com.seniordesign.ultimatescorecard.data.Team;
import com.seniordesign.ultimatescorecard.sqlite.helper.Teams;

public class HockeyTeam extends Team{
	private static final long serialVersionUID = -6287487346904566941L;
	private ArrayList<HockeyPlayer> players = new ArrayList<HockeyPlayer>();
	private HockeyPlayer _goaltender; 
	private Teams _team;
	private long _g_id;

	public HockeyTeam (String teamName, boolean homeTeam){
		super(teamName, homeTeam);
		_homeTeam = homeTeam;
		_teamName = teamName;
	}
	
	public void setTeamAbbr(){
		_teamAbbr = _team.getabbv();
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
