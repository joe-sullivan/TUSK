package com.seniordesign.ultimatescorecard.data.soccer;

import java.util.ArrayList;

import com.seniordesign.ultimatescorecard.data.Team;
import com.seniordesign.ultimatescorecard.sqlite.helper.Teams;

public class SoccerTeam extends Team{
	private static final long serialVersionUID = -6026271836806435470L;
	private ArrayList<SoccerPlayer> players = new ArrayList<SoccerPlayer>();
	private SoccerPlayer _goalkeeper;
	private Teams _team;
	private long _g_id;
	
	public SoccerTeam (String teamName, boolean homeTeam){
		super(teamName, homeTeam);
		_homeTeam = homeTeam;
		_teamName = teamName;		
	}
	
	public void setTeamAbbr(){
		_teamAbbr = _team.getabbv();
	}
	
	public void setData(long g_id, Teams team, ArrayList<SoccerPlayer> players){
		_g_id = g_id;
		_team = team;
		this.players = players;
		setTeamPlayers(players);
		_goalkeeper = players.get(0);

	}
	
	private void setTeamPlayers(ArrayList<SoccerPlayer> players){
		//use string to get t_id, then get players on that team
		for(SoccerPlayer p: players){
			p.setgid(_g_id);
		}
	}

	public SoccerPlayer getPlayer(int player){
		return players.get(player);
	}
	
	public SoccerPlayer getPlayer(String player){
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
	public ArrayList<SoccerPlayer> getRoster(){
		return players;
	}
	
	public SoccerPlayer getGoalie(){
		return _goalkeeper;
	}
	
	public void setGoalie(SoccerPlayer player){
		_goalkeeper = player;
	}
}
