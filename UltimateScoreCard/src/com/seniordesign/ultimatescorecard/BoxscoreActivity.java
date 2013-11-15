package com.seniordesign.ultimatescorecard;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class BoxscoreActivity extends Activity{
	private String _home, _away;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_boxscore);
		
		GetTeamInformation gti = (GetTeamInformation) getIntent().getSerializableExtra("teamInfo");
		_home = gti.getHomeTeam();
		_away = gti.getAwayTeam();
		
		((TextView)findViewById(R.id.awayTeamName)).setText(_away);
		((TextView)findViewById(R.id.homeTeamName)).setText(_home);
		
		((TextView)findViewById(R.id.player1Name)).setText(statLine(gti.getPlayer(_away, 0)));
		((TextView)findViewById(R.id.player2Name)).setText(statLine(gti.getPlayer(_away, 1)));
		((TextView)findViewById(R.id.player3Name)).setText(statLine(gti.getPlayer(_away, 2)));
		((TextView)findViewById(R.id.player4Name)).setText(statLine(gti.getPlayer(_away, 3)));
		((TextView)findViewById(R.id.player5Name)).setText(statLine(gti.getPlayer(_away, 4)));
		
		((TextView)findViewById(R.id.player6Name)).setText(statLine(gti.getPlayer(_home, 0)));
		((TextView)findViewById(R.id.player7Name)).setText(statLine(gti.getPlayer(_home, 1)));
		((TextView)findViewById(R.id.player8Name)).setText(statLine(gti.getPlayer(_home, 2)));
		((TextView)findViewById(R.id.player9Name)).setText(statLine(gti.getPlayer(_home, 3)));
		((TextView)findViewById(R.id.player10Name)).setText(statLine(gti.getPlayer(_home, 4)));		
	}
	
	private String statLine(Player player){
		return player.getName()+" : "+player.getPoints()+" pts / "+player.getRebounds()+" rebs / "+player.getAssists()+" asts";
	}

}
