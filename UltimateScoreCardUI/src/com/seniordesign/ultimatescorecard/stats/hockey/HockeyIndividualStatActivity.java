package com.seniordesign.ultimatescorecard.stats.hockey;

import com.seniordesign.ultimatescorecard.R;
import com.seniordesign.ultimatescorecard.data.hockey.HockeyPlayer;
import com.seniordesign.ultimatescorecard.data.hockey.HockeyTeam;
import com.seniordesign.ultimatescorecard.view.StaticFinalVars;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.TextView;

public class HockeyIndividualStatActivity extends FragmentActivity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_individual_hockey);
		
		HockeyTeam team = (HockeyTeam) getIntent().getSerializableExtra(StaticFinalVars.TEAM_INFO);
		String name = getIntent().getStringExtra(StaticFinalVars.PLAYER_NAME);
		HockeyPlayer player = team.getPlayer(name);
		
		((TextView)findViewById(R.id.playerName)).setText(player.getName());
		((TextView)findViewById(R.id.teamName)).setText(team.getTeamName());
		((TextView)findViewById(R.id.goalTotal)).setText("Goals: "+player.getGoals());
		((TextView)findViewById(R.id.assistTotal)).setText("Assists: "+player.getAssist());
		((TextView)findViewById(R.id.penaltyTotal)).setText("Penalties: "+player.getMajors());
		((TextView)findViewById(R.id.penaltyTypeTotal)).setText(
				" Minors:" + player.getMinors() +
				"\n Majors: " + player.getMajors() +
				"\n Misconduct: " + player.getMisconduct());
		((TextView)findViewById(R.id.penaltyMinsTotal)).setText("Penalty Minutes: "+player.getPenaltyMins());
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		finish();
	}
}
