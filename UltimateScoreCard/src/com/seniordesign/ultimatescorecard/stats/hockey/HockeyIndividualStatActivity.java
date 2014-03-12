package com.seniordesign.ultimatescorecard.stats.hockey;

import java.util.ArrayList;

import com.seniordesign.ultimatescorecard.R;
import com.seniordesign.ultimatescorecard.data.GameInfo;
import com.seniordesign.ultimatescorecard.sqlite.helper.Players;
import com.seniordesign.ultimatescorecard.sqlite.helper.ShotChartCoords;
import com.seniordesign.ultimatescorecard.sqlite.helper.Teams;
import com.seniordesign.ultimatescorecard.sqlite.hockey.HockeyDatabaseHelper;
import com.seniordesign.ultimatescorecard.sqlite.hockey.HockeyGames;
import com.seniordesign.ultimatescorecard.view.StaticFinalVars;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;

public class HockeyIndividualStatActivity extends FragmentActivity{

	protected HockeyDatabaseHelper _hockey_db;
	private ViewPager _pager;
	private PagerAdapter _pagerAdapter;
	protected String _name;
	protected long g_id;
	protected Teams _team;
	protected ArrayList<Players> _players;
	protected boolean _home;
	protected HockeyGames _game;
	protected ArrayList<ShotChartCoords> _shots;
	protected GameInfo _gameInfo;
	
	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_stats);
		
		_team = (Teams) getIntent().getSerializableExtra(StaticFinalVars.TEAM_INFO);
		_players = (ArrayList<Players>) getIntent().getSerializableExtra(StaticFinalVars.PLAYERS_INFO);
		g_id = (Long) getIntent().getSerializableExtra(StaticFinalVars.GAME_ID);
		_name = getIntent().getStringExtra(StaticFinalVars.PLAYER_NAME);
		_home = getIntent().getBooleanExtra(StaticFinalVars.HOME_OR_AWAY, true);
		_shots = (ArrayList<ShotChartCoords>) getIntent().getSerializableExtra(StaticFinalVars.SHOT_CHART);
		_gameInfo = (GameInfo) getIntent().getSerializableExtra(StaticFinalVars.GAME_INFO);
		int value = getIntent().getIntExtra(StaticFinalVars.DISPLAY_TYPE, 0);

		_hockey_db = new HockeyDatabaseHelper(getApplicationContext());
		_game = (HockeyGames) _hockey_db.getGame(g_id);
		
        _pager = (ViewPager) findViewById(R.id.statsPager);
        _pagerAdapter = new HockeyIndividualStatPageAdapter(getSupportFragmentManager());
        _pager.setAdapter(_pagerAdapter);
        _pager.setCurrentItem(value);
		
		
	}
		

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		finish();
	}
}
