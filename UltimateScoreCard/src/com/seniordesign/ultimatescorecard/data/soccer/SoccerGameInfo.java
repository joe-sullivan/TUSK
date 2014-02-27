package com.seniordesign.ultimatescorecard.data.soccer;

import java.io.Serializable;
import java.util.ArrayList;

import com.seniordesign.ultimatescorecard.sqlite.helper.Players;
import com.seniordesign.ultimatescorecard.sqlite.helper.Teams;

public class SoccerGameInfo implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -531764763226162832L;
	public Teams _homeTeam, _awayTeam;
	public ArrayList<Players> _homeTeamPlayers, _awayTeamPlayers;
	public long g_id;
	
	public SoccerGameInfo(Teams _homeTeamPull, Teams _awayTeamPull,
			ArrayList<Players> _homeTeamPlayersPull,
			ArrayList<Players> _awayTeamPlayersPull, long g_id) {
		_homeTeam = _homeTeamPull;
		_awayTeam = _awayTeamPull;
		_homeTeamPlayers = _homeTeamPlayersPull;
		_awayTeamPlayers = _awayTeamPlayersPull;
		this.g_id = g_id;
	}
	
	public Teams getHomeTeam(){
		return _homeTeam;
	}
	
	public Teams getAwayTeam(){
		return _awayTeam;
	}
	
	public ArrayList<Players> getHomePlayers(){
		return _homeTeamPlayers;
	}
	
	public ArrayList<Players> getAwayPlayers(){
		return _awayTeamPlayers;
	}
	
	public long getgid(){
		return g_id;
	}

	public int numberPlayers(boolean homeTeam){
		if(homeTeam){
			return _homeTeamPlayers.size();
		}
		else{
			return _awayTeamPlayers.size();
		}
	}
	
	public Players getPlayer(int player, boolean home){
		if(home){
			return _homeTeamPlayers.get(player);
		}
		else{
			return _awayTeamPlayers.get(player);
		}
	}
	
	public Players getPlayer(String player, boolean home){
		if(home){
			for(int i=0; i<_homeTeamPlayers.size(); i++){
				if(player.equals(_homeTeamPlayers.get(i).getpname())){
					return _homeTeamPlayers.get(i);
				}
			}
		}
		else{
			for(int i=0; i<_awayTeamPlayers.size(); i++){
				if(player.equals(_awayTeamPlayers.get(i).getpname())){
					return _awayTeamPlayers.get(i);
				}
			}
		}
		return null;
	}
	
	public int getPlayerAsPositionInArray(String player, boolean home){
		if(home){
			for(int i=0; i<_homeTeamPlayers.size(); i++){
				if(player.equals(_homeTeamPlayers.get(i).getpname())){
					return i;
				}
			}
		}
		else{
			for(int i=0; i<_awayTeamPlayers.size(); i++){
				if(player.equals(_awayTeamPlayers.get(i).getpname())){
					return i;
				}
			}
		}
		return -1;
	}
	
	public Players getPlayerOnCourt(String player, boolean home){
		if(home){
			for(int i=0; i<5; i++){
				if(player.equals(_homeTeamPlayers.get(i).getpname())){
					return _homeTeamPlayers.get(i);
				}
			}
		}
		else{
			for(int i=0; i<5; i++){
				if(player.equals(_awayTeamPlayers.get(i).getpname())){
					return _awayTeamPlayers.get(i);
				}
			}
		}
		return null;
	}
	
	public void swapPlayer(String playerIn, String playerOut, boolean home){
		if(home){
			int i = getPlayerAsPositionInArray(playerIn, home);
			int j = getPlayerAsPositionInArray(playerOut, home);
			if(i >= 0 && i<_homeTeamPlayers.size() && j >= 0 && j<_homeTeamPlayers.size()){
				Players temp = _homeTeamPlayers.get(i);
				_homeTeamPlayers.set(i, _homeTeamPlayers.get(j));
				_homeTeamPlayers.set(j, temp);
			}
		}
		else{
			int i = getPlayerAsPositionInArray(playerIn, home);
			int j = getPlayerAsPositionInArray(playerOut, home);
			if(i >= 0 && i<_awayTeamPlayers.size() && j >= 0 && j<_awayTeamPlayers.size()){
				Players temp = _awayTeamPlayers.get(i);
				_awayTeamPlayers.set(i, _awayTeamPlayers.get(j));
				_awayTeamPlayers.set(j, temp);
			}
		}
		
	}
}
