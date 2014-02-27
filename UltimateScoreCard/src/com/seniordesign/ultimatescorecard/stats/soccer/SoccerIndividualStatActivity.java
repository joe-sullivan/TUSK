package com.seniordesign.ultimatescorecard.stats.soccer;

import com.seniordesign.ultimatescorecard.R;
import com.seniordesign.ultimatescorecard.data.soccer.SoccerPlayer;
import com.seniordesign.ultimatescorecard.data.soccer.SoccerTeam;
import com.seniordesign.ultimatescorecard.view.StaticFinalVars;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.TextView;

public class SoccerIndividualStatActivity extends FragmentActivity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_individual_soccer);
		
		SoccerTeam team = (SoccerTeam) getIntent().getSerializableExtra(StaticFinalVars.TEAM_INFO);
		String name = getIntent().getStringExtra(StaticFinalVars.PLAYER_NAME);
		SoccerPlayer player =	team.getPlayer(name);
		
		((TextView)findViewById(R.id.playerName)).setText(player.getName());
		((TextView)findViewById(R.id.teamName)).setText(team.getTeamName());
		((TextView)findViewById(R.id.goalTotal)).setText("Goals: "+player.getGoals());
		((TextView)findViewById(R.id.assistTotal)).setText("Assists: "+player.getAssists());
		((TextView)findViewById(R.id.penaltyTypeTotal)).setText(
				" Yellow Cards:" + player.getYellow() +
				"\n Red Cards: " + player.getRed());
		
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		finish();
	}
}
