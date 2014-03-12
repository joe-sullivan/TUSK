package com.seniordesign.ultimatescorecard.view;

import java.util.ArrayList;

import com.seniordesign.ultimatescorecard.R;
import com.seniordesign.ultimatescorecard.sqlite.basketball.BasketballDatabaseHelper;
import com.seniordesign.ultimatescorecard.sqlite.helper.DatabaseHelper;
import com.seniordesign.ultimatescorecard.sqlite.helper.Players;
import com.seniordesign.ultimatescorecard.sqlite.helper.ShotChartCoords;
import com.seniordesign.ultimatescorecard.sqlite.hockey.HockeyDatabaseHelper;
import com.seniordesign.ultimatescorecard.sqlite.soccer.SoccerDatabaseHelper;

import android.content.Context;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;

public class ShotIconAdder {
	private RelativeLayout _homeLayout; 
	private RelativeLayout _awayLayout;
	private Context _context;
	private int[] _shotLocation = new int[2];
	private boolean _hitMiss;
	private String _pname;
	private DatabaseHelper _db;
	private String _sport;
	
	public ShotIconAdder (RelativeLayout home, RelativeLayout away, Context context, String sport){
		_homeLayout = home;
		_awayLayout = away;
		_context = context;
		_sport = sport;
		if(_sport.equals("basketball")){
			_db = new BasketballDatabaseHelper(context);
		}
		else if(_sport.equals("hockey")){
			_db = new HockeyDatabaseHelper(context);
		}
		else if(_sport.equals("soccer")){
			_db = new SoccerDatabaseHelper(context);
		}
	}
	
	public void setShotLocation(int x, int y){
		_shotLocation[0] = x;
		_shotLocation[1] = y;
	}
	
	public void setShotHitMiss(boolean possession, boolean hitMiss){
		_hitMiss = hitMiss;
		LayoutParams lp = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		lp.leftMargin = _shotLocation[0]-25;
		lp.topMargin = _shotLocation[1]+60;
		ImageView iv = new ImageView(_context);
		if(hitMiss){
			iv.setBackgroundResource(R.drawable.made_shot);
		}
		else{
			iv.setBackgroundResource(R.drawable.missed_shot);
		}
		iv.setLayoutParams(lp);
		if(possession){
			_homeLayout.addView(iv);
		}
		else{
			_awayLayout.addView(iv);
		}
	}
	
	public void setPlayer(String pname){
		_pname = pname;
	}
	
	public void createShot(String pname, long g_id, long home_id, long away_id){
		if(pname!=null){
			_pname = pname;
		}
		Players player = null;
		ArrayList<Players> players = (ArrayList<Players>) _db.getPlayersTeam2(home_id);
		for(Players p: players){
			if(_pname.equals(p.getpname())){
				player = p;
				Log.i("Player", "Player team: " + player.gettid());
				break;
			}
		}
		if(player == null){
			players = (ArrayList<Players>) _db.getPlayersTeam2(away_id);
			for(Players p: players){
				if(_pname.equals(p.getpname())){
					player = p;
					Log.i("Player", "Player team: " + player.gettid());

					break;
				}
			}
		}

		if(_hitMiss){
			_db.createShot(new ShotChartCoords(g_id, player.getpid(), player.gettid(), _shotLocation[0], _shotLocation[1], "make"));

		}
		else{
			_db.createShot(new ShotChartCoords(g_id, player.getpid(), player.gettid(), _shotLocation[0], _shotLocation[1], "miss"));

		}
		ArrayList<ShotChartCoords> shots = (ArrayList<ShotChartCoords>) _db.getAllShots();

		for(ShotChartCoords shot: shots){
			Log.i("shot", "Shot team: " + shot.gettid());
		}
	}
}
