package com.seniordesign.ultimatescorecard.stats.basketball;

import java.util.ArrayList;

import com.seniordesign.ultimatescorecard.R;
import com.seniordesign.ultimatescorecard.data.basketball.BasketballPlayer;
import com.seniordesign.ultimatescorecard.data.basketball.BasketballTeam;
import com.seniordesign.ultimatescorecard.sqlite.basketball.BasketballDatabaseHelper;
import com.seniordesign.ultimatescorecard.sqlite.basketball.BasketballGameStats;
import com.seniordesign.ultimatescorecard.sqlite.basketball.BasketballGames;
import com.seniordesign.ultimatescorecard.sqlite.helper.*;
import com.seniordesign.ultimatescorecard.view.StaticFinalVars;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.TextView;

public class BasketballIndividualStatActivity extends FragmentActivity{

	public BasketballDatabaseHelper _basketball_db;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_individual_basketball);
		
		Teams team = (Teams) getIntent().getSerializableExtra(StaticFinalVars.TEAM_INFO);
		ArrayList<Players> players = (ArrayList<Players>) getIntent().getSerializableExtra(StaticFinalVars.PLAYERS_INFO);
		long g_id = (Long) getIntent().getSerializableExtra(StaticFinalVars.GAME_ID);
		String name = getIntent().getStringExtra(StaticFinalVars.PLAYER_NAME);
		boolean home = getIntent().getBooleanExtra(StaticFinalVars.HOME_OR_AWAY, true);

		_basketball_db = new BasketballDatabaseHelper(getApplicationContext());
		BasketballGames game = (BasketballGames) _basketball_db.getGame(g_id);
			
		if(name.equals(team.getabbv() + " Stats")){
			if(home){
				((TextView)findViewById(R.id.playerName)).setText(team.getabbv());
				((TextView)findViewById(R.id.teamName)).setText(team.gettname());
				((TextView)findViewById(R.id.pointTotal)).setText("Points: "+game.gethomepts());	
				((TextView)findViewById(R.id.fieldGoalTotal)).setText(				
						"Field Goals:" +
						"\n Made: " + game.gethomefgm() +
						"\t\t Missed: " + game.gethomefga() +
						"\n FG %: " + game.gethomefgpercent());	
				((TextView)findViewById(R.id.fieldGoal3Total)).setText(				
						"3 Point Field Goals:" +
						"\n Made: " + game.gethomefgm3() +
						"\t\t Missed: " + game.gethomefga3() +
						"\n FG %: " + game.gethomefg3percent());		
				((TextView)findViewById(R.id.freeThrowTotal)).setText(
						"Free Throws:" +
						"\n Made: " + game.gethomeftm() +
						"\t\t Missed: " + game.gethomefta() +
						"\n FT %: " + game.gethomeftpercent());	
			
				((TextView)findViewById(R.id.reboundTotal)).setText("Rebounds: "+(game.gethomedreb()+game.gethomeoreb()));
				((TextView)findViewById(R.id.dReboundTotal)).setText("Defensive Rebounds: "+game.gethomedreb());
				((TextView)findViewById(R.id.oReboundTotal)).setText("Offensive Rebounds: "+game.gethomeoreb());
				((TextView)findViewById(R.id.assistTotal)).setText("Assists: "+game.gethomeast());
				((TextView)findViewById(R.id.stealTotal)).setText("Steals: "+game.gethomestl());
				((TextView)findViewById(R.id.blocksTotal)).setText("Blocks: "+game.gethomeblk());
				((TextView)findViewById(R.id.foulTotal)).setText("Fouls: "+game.gethomepf());
				((TextView)findViewById(R.id.techFouls)).setText("Technical Fouls: "+game.gethometech());
				((TextView)findViewById(R.id.flagrantFouls)).setText("Flagrant Fouls: "+game.gethomeflagrant());
		
			}
			else{
				((TextView)findViewById(R.id.playerName)).setText(team.getabbv());
				((TextView)findViewById(R.id.teamName)).setText(team.gettname());
				((TextView)findViewById(R.id.pointTotal)).setText("Points: "+game.getawaypts());	
				((TextView)findViewById(R.id.fieldGoalTotal)).setText(				
						"Field Goals:" +
						"\n Made: " + game.getawayfgm() +
						"\t\t Missed: " + game.getawayfga() +
						"\n FG %: " + game.getawayfgpercent());		
				((TextView)findViewById(R.id.fieldGoal3Total)).setText(				
						"Field Goals:" +
						"\n Made: " + game.getawayfgm3() +
						"\t\t Missed: " + game.getawayfga3() +
						"\n FG %: " + game.getawayfg3percent());	
				((TextView)findViewById(R.id.freeThrowTotal)).setText(
						"Free Throws:" +
						"\n Made: " + game.getawayftm() +
						"\t\t Missed: " + game.getawayfta() +
						"\n FT %: " + game.getawayftpercent());	
			
				((TextView)findViewById(R.id.reboundTotal)).setText("Rebounds: "+(game.getawaydreb()+game.getawayoreb()));
				((TextView)findViewById(R.id.dReboundTotal)).setText("Defensive Rebounds: "+game.getawaydreb());
				((TextView)findViewById(R.id.oReboundTotal)).setText("Offensive Rebounds: "+game.getawayoreb());
				((TextView)findViewById(R.id.assistTotal)).setText("Assists: "+game.getawayast());
				((TextView)findViewById(R.id.stealTotal)).setText("Steals: "+game.getawaystl());
				((TextView)findViewById(R.id.blocksTotal)).setText("Blocks: "+game.getawayblk());
				((TextView)findViewById(R.id.foulTotal)).setText("Fouls: "+game.getawaypf());
				((TextView)findViewById(R.id.techFouls)).setText("Technical Fouls: "+game.getawaytech());
				((TextView)findViewById(R.id.flagrantFouls)).setText("Flagrant Fouls: "+game.getawayflagrant());
		
			}
		}
		else{
			Players player = null;
			for(Players p: players){
				if(p.getpname().equals(name)){
					player = p;
				}
			}
			BasketballGameStats stats = _basketball_db.getPlayerGameStats(g_id, player.getpid());
			
			((TextView)findViewById(R.id.playerName)).setText(player.getpname());
			((TextView)findViewById(R.id.teamName)).setText(team.gettname());
			((TextView)findViewById(R.id.pointTotal)).setText("Points: "+stats.getpts());	
			((TextView)findViewById(R.id.fieldGoalTotal)).setText(				
					"Field Goals:" +
					"\n Made: " + stats.getfgm() +
					"\t\t Missed: " + stats.getfga() +
					"\n FG %: " + stats.getfgpercent());	
			((TextView)findViewById(R.id.fieldGoal3Total)).setText(				
					"Field Goals:" +
					"\n Made: " + stats.getfgm3() +
					"\t\t Missed: " + stats.getfga3() +
					"\n FG %: " + stats.getfg3percent());	
			((TextView)findViewById(R.id.freeThrowTotal)).setText(
					"Free Throws:" +
					"\n Made: " + stats.getftm() +
					"\t\t Missed: " + stats.getfta() +
					"\n FT %: " + stats.getftpercent());	
		
			((TextView)findViewById(R.id.reboundTotal)).setText("Rebounds: "+(stats.getdreb()+stats.getoreb()));
			((TextView)findViewById(R.id.dReboundTotal)).setText("Defensive Rebounds: "+stats.getdreb());
		((TextView)findViewById(R.id.oReboundTotal)).setText("Offensive Rebounds: "+stats.getoreb());
			((TextView)findViewById(R.id.assistTotal)).setText("Assists: "+stats.getast());
			((TextView)findViewById(R.id.stealTotal)).setText("Steals: "+stats.getstl());
			((TextView)findViewById(R.id.blocksTotal)).setText("Blocks: "+stats.getblk());
			((TextView)findViewById(R.id.foulTotal)).setText("Fouls: "+stats.getpf());
			((TextView)findViewById(R.id.techFouls)).setText("Technical Fouls: "+stats.gettech());
			((TextView)findViewById(R.id.flagrantFouls)).setText("Flagrant Fouls: "+stats.getflagrant());
		}
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		finish();
	}
}
