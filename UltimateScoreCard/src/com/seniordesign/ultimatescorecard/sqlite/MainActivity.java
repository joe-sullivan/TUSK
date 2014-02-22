package com.seniordesign.ultimatescorecard.sqlite;

import java.util.List;

import com.seniordesign.ultimatescorecard.sqlite.basketball.BasketballDatabaseHelper;
import com.seniordesign.ultimatescorecard.sqlite.basketball.BasketballGameStats;
import com.seniordesign.ultimatescorecard.sqlite.basketball.ShotChartCoords;
import com.seniordesign.ultimatescorecard.sqlite.helper.*;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;

public class MainActivity extends Activity {

	BasketballDatabaseHelper basketball_db;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
/*	
		basketball_db = new BasketballDatabaseHelper(getApplicationContext());
		basketball_db.onUpgrade(basketball_db.getWritableDatabase(), 0, 0);
		football_db = new FootballDatabaseHelper(getApplicationContext());
		football_db.onUpgrade(football_db.getWritableDatabase(), 0, 0);
		baseball_db = new BaseballDatabaseHelper(getApplicationContext());
		baseball_db.onUpgrade(baseball_db.getWritableDatabase(), 0, 0);
		soccer_db = new SoccerDatabaseHelper(getApplicationContext());
		soccer_db.onUpgrade(soccer_db.getWritableDatabase(), 0, 0);
*/
		basketball_db = new BasketballDatabaseHelper(getApplicationContext());
		//basketball_db.deleteAll();
		

		//Creating Teams
		Teams spurs = new Teams("San Antonio Spurs", "SAS", "Gregg Popovich", "Basketball");
		Teams rockets = new Teams("Houston Rockets", "HOU", "Kevin McHale", "Basketball");
		//Teams patriots = new Teams("New England Patriots", "Bill Belichick", "Football");
		//Insert Teams into database
		long spurs_id = basketball_db.createTeams(spurs);
		long rockets_id = basketball_db.createTeams(rockets);
		
		List<Teams> teams = basketball_db.getAllTeams();
		for(Teams t: teams){
			Log.d("Team name", "Team name: " + t.gettname());
		}
		Log.d("Team count", "Team Count: " + basketball_db.getAllTeams().size());
		Log.d("Get Team", "getTeam: "+ basketball_db.getTeam(spurs_id).gettname());
		
		
		//Creating Players
		Players MG20 = new Players(spurs_id, "Manu Ginobili", 20);
		Players TD21 = new Players(spurs_id, "Tim Duncan", 21);
		Players JH13 = new Players(rockets_id, "James Harden", 13);
/*		
		//Insert Players into database
		long MG20_id = basketball_db.createPlayers(MG20);
		long TD21_id = basketball_db.createPlayers(TD21);
		long JH13_id = basketball_db.createPlayers(JH13);
		
		List<Players> players = basketball_db.getPlayersTeam(spurs_id);
		
		for(Players p: players){
			Log.d("Player name", "Player name: " + p.getpname());
			Log.d("Player ID", "Player ID: " + p.getpid());
		}
		
		Log.d("Player count", "Player Count: " + basketball_db.getAllPlayers().size());
		
		//Creating Game
		Games game1 = new Games(spurs_id, rockets_id, "12/19/2013");
		Games game2 = new Games(spurs_id, rockets_id, "1/16/2013");
		
		//Insert Game into database
		long g1_id = basketball_db.createGame(game1);	
		long g2_id = basketball_db.createGame(game2);
		List<Games> games = basketball_db.getAllGames();	
		for(Games g: games){
			Log.d("Game ID", "Game ID: " + g.getgid());
		}
		Log.d("Get Game", "getGame: "+ basketball_db.getGame(g1_id).getDate());

		Log.d("Game count", "Game Count: " + basketball_db.getAllGames().size());
		
		basketball_db.addStats(g1_id, MG20_id, "pts", 10);
		basketball_db.addStats(g1_id, TD21_id, "pts", 21);
		basketball_db.addStats(g1_id, MG20_id, "pts", 10);
		basketball_db.addStats(g1_id, JH13_id, "pts", 3);
		basketball_db.addStats(g1_id, MG20_id, "fgm", 1);
		basketball_db.addStats(g1_id, MG20_id, "fgm", 1);
		basketball_db.addStats(g1_id, MG20_id, "fga", 2);
		basketball_db.addStats(g1_id, MG20_id, "fga", 2);
		basketball_db.addStats(g1_id, MG20_id, "fgm3", 3);
		basketball_db.addStats(g1_id, MG20_id, "fgm3", 3);
		basketball_db.addStats(g1_id, MG20_id, "fga3", 4);
		basketball_db.addStats(g1_id, MG20_id, "fga3", 4);
		basketball_db.addStats(g1_id, MG20_id, "ftm", 5);
		basketball_db.addStats(g1_id, MG20_id, "ftm", 5);
		basketball_db.addStats(g1_id, MG20_id, "fta", 6);
		basketball_db.addStats(g1_id, MG20_id, "fta", 6);
		basketball_db.addStats(g1_id, MG20_id, "oreb", 7);
		basketball_db.addStats(g1_id, MG20_id, "oreb", 7);
		basketball_db.addStats(g1_id, MG20_id, "dreb", 8);
		basketball_db.addStats(g1_id, MG20_id, "dreb", 8);
		basketball_db.addStats(g1_id, MG20_id, "ast", 9);
		basketball_db.addStats(g1_id, MG20_id, "ast", 9);
		basketball_db.addStats(g1_id, MG20_id, "stl", 10);
		basketball_db.addStats(g1_id, MG20_id, "stl", 10);
		basketball_db.addStats(g1_id, MG20_id, "blk", 11);
		basketball_db.addStats(g1_id, MG20_id, "blk", 11);
		basketball_db.addStats(g1_id, MG20_id, "turnover", 12);
		basketball_db.addStats(g1_id, MG20_id, "turnover", 12);
		basketball_db.addStats(g1_id, MG20_id, "pf", 13);
		basketball_db.addStats(g1_id, MG20_id, "pf", 13);
		
		basketball_db.addStats(g2_id, MG20_id, "pts", 35);
		
		
		//GameStats
		List<BasketballGameStats> gamestatslist = basketball_db.getAllGameStats();
		//BasketballGameStats gs = basketball_db.getPlayerGameStats(g1_id, MG20_id);
		List<BasketballGameStats> gamestatslist2 = basketball_db.getPlayerAllGameStats(MG20_id);

		for(BasketballGameStats gs: gamestatslist2){
			Log.d("Player ID", "Player ID: " + gs.getpid());
			Log.d("Player points", "Player points: " + gs.getpts());
			Log.d("Player points", "Player points: " + gs.getfgm());
			Log.d("Player points", "Player points: " + gs.getfga());
			Log.d("Player points", "Player points: " + gs.getfgm3());
			Log.d("Player points", "Player points: " + gs.getfga3());
			Log.d("Player points", "Player points: " + gs.getftm());
			Log.d("Player points", "Player points: " + gs.getfta());
			Log.d("Player points", "Player points: " + gs.getoreb());
			Log.d("Player points", "Player points: " + gs.getdreb());
			Log.d("Player points", "Player points: " + gs.getast());
			Log.d("Player points", "Player points: " + gs.getstl());
			Log.d("Player points", "Player points: " + gs.getblk());
			Log.d("Player points", "Player points: " + gs.getto());
			Log.d("Player points", "Player points: " + gs.getpf());

		}
		
		Log.d("GameStats count", "GameStats Count: " + basketball_db.getAllGameStats().size());
		
		//Creating Plays
		PlayByPlay play1 = new PlayByPlay(g1_id, "2 Point field goal attempt by James Harden Blocked by Tim Duncan", "time", "1", 43, 20);
		long play1_id = basketball_db.createPlayByPlay(play1);
		ShotChartCoords shot1 = new ShotChartCoords(play1_id, g1_id, JH13_id, 10, 20, "miss");
		long shot1_id = basketball_db.createShot(shot1);
		PlayByPlay play2 = new PlayByPlay(g1_id, "3 Point shot made by Manu Ginobili, Assisted by Tim Duncan", "time", "1", 43, 20);
		long play2_id = basketball_db.createPlayByPlay(play2);
		ShotChartCoords shot2 = new ShotChartCoords(play2_id, g1_id, MG20_id, 0, 40, "make");
		long shot2_id = basketball_db.createShot(shot2);

		List<PlayByPlay> pbps = basketball_db.getPlayByPlayGame(g1_id);
		
		for(PlayByPlay pbp: pbps){
			Log.d("PBP ID", "PBP ID: " + pbp.getaid());
		}
		
		Log.d("PBP count", "PBP Count: " + basketball_db.getPlayByPlayGame(g1_id).size());
		
		List<ShotChartCoords> shots = basketball_db.getAllShots();
		
		for(ShotChartCoords shot: shots){
			Log.d("shot x", "shot x: " + shot.getx());
			Log.d("shot y", "shot y: " + shot.gety());

		}
		
		Log.d("shot count", "shot Count: " + basketball_db.getAllTeamShotsGame(spurs_id, g1_id).size());
		
		//basketball_db.deleteAll();
		//football_db.deleteAll();
		
*/		
		basketball_db.closeDB();
	}

}
