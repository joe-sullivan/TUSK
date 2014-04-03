package com.seniordesign.ultimatescorecard.data.basketball;

import java.util.ArrayList;

import com.seniordesign.ultimatescorecard.data.Team;
import com.seniordesign.ultimatescorecard.sqlite.helper.*;

public class BasketballTeam extends Team{
	private static final long serialVersionUID = -4547516998953968383L;
	private ArrayList<BasketballPlayer> players = new ArrayList<BasketballPlayer>();
	private Teams _team;
	private long _t_id, _g_id;

	
	public BasketballTeam (String teamName, boolean homeTeam){
		super(teamName,homeTeam);
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
	
	public void setData(long g_id, Teams team, ArrayList<BasketballPlayer> players){
		_g_id = g_id;
		_team = team;
		this.players = players;
		setTeamPlayers(players);
	}
	
	private void setTeamPlayers(ArrayList<BasketballPlayer> players){
		//use string to get t_id, then get players on that team
		for(BasketballPlayer p: players){
			p.setgid(_g_id);
		}
	}
	
	public BasketballPlayer getPlayer(int player){
		return players.get(player);
	}
	
	public BasketballPlayer getPlayer(String player){
		for(int i=0; i<players.size(); i++){
			if(player.equals(players.get(i).getpname())){
				return players.get(i);
			}
		}
		return null;
	}
	
	public int getPlayerAsPositionInArray(String player){
		for(int i=0; i<players.size(); i++){
			if(player.equals(players.get(i).getpname())){
				return i;
			}
		}
		return -1;
	}
	
	public BasketballPlayer getPlayerOnCourt(String player){
		for(int i=0; i<5; i++){
			if(player.equals(players.get(i).getpname())){
				return players.get(i);
			}
		}
		return null;
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
	
	public long gettid(){
		return _t_id;
	}
	
	public void setTeamOrder(ArrayList<Players> ps){
		ArrayList<BasketballPlayer> temp = new ArrayList<BasketballPlayer>();
		for(Players p: ps){
			for(BasketballPlayer p2: players){
				if(p.getpname().equals(p2.getpname())){
					temp.add(p2);
				}
			}
		}
		players = temp;
	}
}
