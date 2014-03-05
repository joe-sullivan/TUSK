package com.seniordesign.ultimatescorecard.stats.basketball;

import com.seniordesign.ultimatescorecard.R;
import com.seniordesign.ultimatescorecard.data.basketball.BasketballPlayer;
import com.seniordesign.ultimatescorecard.data.basketball.BasketballTeam;
import com.seniordesign.ultimatescorecard.view.StaticFinalVars;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.TextView;

public class BasketballIndividualStatActivity extends FragmentActivity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_individual_basketball);
		
		BasketballTeam team = (BasketballTeam) getIntent().getSerializableExtra(StaticFinalVars.TEAM_INFO);
		String name = getIntent().getStringExtra(StaticFinalVars.PLAYER_NAME);
		BasketballPlayer player =	team.getPlayer(name);
		
		((TextView)findViewById(R.id.playerName)).setText(player.getName());
		((TextView)findViewById(R.id.teamName)).setText(team.getTeamName());
		((TextView)findViewById(R.id.pointTotal)).setText("Points: "+player.pointScored());
		((TextView)findViewById(R.id.fieldGoalTotal)).setText(				
				"Field Goals:" +
				"\n Made: " + player.fieldGoalMade() +
				"\t\t Missed: " + player.fieldGoalAttempted() +
				"\n FG %: " + player.fieldGoalPCT());				
		((TextView)findViewById(R.id.freeThrowTotal)).setText(
				"Free Throws:" +
				"\n Made: " + player.getFTMade() +
				"\t\t Missed: " + player.freeThrowAttempted() +
				"\n FT %: " + player.freeThrowPCT());	
		((TextView)findViewById(R.id.reboundTotal)).setText("Rebounds: "+player.getRebounds());
		((TextView)findViewById(R.id.assistTotal)).setText("Assists: "+player.getAssists());
		((TextView)findViewById(R.id.stealTotal)).setText("Steals: "+player.getSteals());
		((TextView)findViewById(R.id.blocksTotal)).setText("Blocks: "+player.getBlocks());
		((TextView)findViewById(R.id.foulTotal)).setText("Fouls: "+player.getFouls());
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		finish();
	}
}
