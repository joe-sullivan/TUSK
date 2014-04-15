package com.seniordesign.ultimatescorecard.stats.football;

import com.seniordesign.ultimatescorecard.R;
import com.seniordesign.ultimatescorecard.data.football.FootballPlayer;
import com.seniordesign.ultimatescorecard.data.football.FootballTeam;
import com.seniordesign.ultimatescorecard.view.StaticFinalVars;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

public class FootballIndividualStatActivity extends FragmentActivity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_individual_football);
	
		FootballTeam team = (FootballTeam) getIntent().getSerializableExtra(StaticFinalVars.TEAM_INFO);
		String name = getIntent().getStringExtra(StaticFinalVars.PLAYER_NAME);
		FootballPlayer player =	team.getPlayer(name);
	}
		
		

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		finish();
	}
}
