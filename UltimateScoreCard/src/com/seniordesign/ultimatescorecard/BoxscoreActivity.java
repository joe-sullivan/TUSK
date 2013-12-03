package com.seniordesign.ultimatescorecard;

import com.seniordesign.ultimatescorecard.R;
import com.seniordesign.ultimatescorecard.data.GameLog;
import com.seniordesign.ultimatescorecard.data.GameTime;
import com.seniordesign.ultimatescorecard.data.Player;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class BoxscoreActivity extends Activity{	
	private String _home, _away;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_boxscore);
		
		final GameTime gti = (GameTime) getIntent().getSerializableExtra("teamInfo");
		final GameLog log = (GameLog) getIntent().getSerializableExtra("gameLog");
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
		
		((Button)findViewById(R.id.seePlaysButton)).setOnClickListener(new OnClickListener(){
			@Override
				public void onClick(View v) {
					Intent intent = new Intent(getApplicationContext(), PlayListActivity.class);	
					intent.putExtra("teamInfo", gti);
					intent.putExtra("gameLog", log);	
					startActivity(intent);	
				}
			}
		);
	}
	
	private String statLine(Player player){
		return player.getName()+" : "+getPoints(player)+" pts / "+player.getRebounds()+" rebs / "+player.getAssists()+" asts";
	}
	
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
	        Toast.makeText(this, "landscape", Toast.LENGTH_SHORT).show();
	    } 
		else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
	        Toast.makeText(this, "portrait", Toast.LENGTH_SHORT).show();
	    }
	}
	
	private int getPoints(Player player){
		return (player.getThreePtsMade() * 3) + (player.getTwoPtsMade() * 2) + player.getFTMade();
	}	
}
