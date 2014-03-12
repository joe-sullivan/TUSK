package com.seniordesign.ultimatescorecard.stats.basketball;

import java.util.ArrayList;

import com.seniordesign.ultimatescorecard.R;
import com.seniordesign.ultimatescorecard.sqlite.basketball.BasketballDatabaseHelper;
import com.seniordesign.ultimatescorecard.sqlite.basketball.BasketballGameStats;
import com.seniordesign.ultimatescorecard.sqlite.basketball.BasketballGames;
import com.seniordesign.ultimatescorecard.sqlite.helper.Players;
import com.seniordesign.ultimatescorecard.sqlite.helper.Teams;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class BasketballIndividualStatFragment extends Fragment{
	
	private BasketballDatabaseHelper _db;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = (View) inflater.inflate(R.layout.fragment_individual_basketball, container, false);
		setHasOptionsMenu(true);
		return view;
	}
	
	@Override
	public void onResume() {
		super.onResume();
		_db = ((BasketballIndividualStatActivity) getActivity())._basketball_db;
		BasketballGames game = ((BasketballIndividualStatActivity) getActivity())._game;
		String name = ((BasketballIndividualStatActivity) getActivity())._name;
		Teams team = ((BasketballIndividualStatActivity) getActivity())._team;
		boolean home = ((BasketballIndividualStatActivity) getActivity())._home;
		ArrayList<Players> players = ((BasketballIndividualStatActivity) getActivity())._players;
		long g_id = ((BasketballIndividualStatActivity) getActivity()).g_id;
		
		if(name.equals(team.getabbv() + " Stats")){
			if(home){
				((TextView)((BasketballIndividualStatActivity) getActivity()).findViewById(R.id.playerName)).setText(team.getabbv());
				((TextView)((BasketballIndividualStatActivity) getActivity()).findViewById(R.id.teamName)).setText(team.gettname());
				((TextView)((BasketballIndividualStatActivity) getActivity()).findViewById(R.id.pointTotal)).setText("Points: "+game.gethomepts());	
				((TextView)((BasketballIndividualStatActivity) getActivity()).findViewById(R.id.fieldGoalTotal)).setText(				
						"Field Goals:" +
						"\n Made: " + game.gethomefgm() +
						"\t\t Missed: " + game.gethomefga() +
						"\n FG %: " + game.gethomefgpercent());	
				((TextView)((BasketballIndividualStatActivity) getActivity()).findViewById(R.id.fieldGoal3Total)).setText(				
						"3 Point Field Goals:" +
						"\n Made: " + game.gethomefgm3() +
						"\t\t Missed: " + game.gethomefga3() +
						"\n FG %: " + game.gethomefg3percent());		
				((TextView)((BasketballIndividualStatActivity) getActivity()).findViewById(R.id.freeThrowTotal)).setText(
						"Free Throws:" +
						"\n Made: " + game.gethomeftm() +
						"\t\t Missed: " + game.gethomefta() +
						"\n FT %: " + game.gethomeftpercent());	
			
				((TextView)((BasketballIndividualStatActivity) getActivity()).findViewById(R.id.reboundTotal)).setText("Rebounds: "+(game.gethomedreb()+game.gethomeoreb()));
				((TextView)((BasketballIndividualStatActivity) getActivity()).findViewById(R.id.dReboundTotal)).setText("Defensive Rebounds: "+game.gethomedreb());
				((TextView)((BasketballIndividualStatActivity) getActivity()).findViewById(R.id.oReboundTotal)).setText("Offensive Rebounds: "+game.gethomeoreb());
				((TextView)((BasketballIndividualStatActivity) getActivity()).findViewById(R.id.assistTotal)).setText("Assists: "+game.gethomeast());
				((TextView)((BasketballIndividualStatActivity) getActivity()).findViewById(R.id.stealTotal)).setText("Steals: "+game.gethomestl());
				((TextView)((BasketballIndividualStatActivity) getActivity()).findViewById(R.id.blocksTotal)).setText("Blocks: "+game.gethomeblk());
				((TextView)((BasketballIndividualStatActivity) getActivity()).findViewById(R.id.foulTotal)).setText("Fouls: "+game.gethomepf());
				((TextView)((BasketballIndividualStatActivity) getActivity()).findViewById(R.id.techFouls)).setText("Technical Fouls: "+game.gethometech());
				((TextView)((BasketballIndividualStatActivity) getActivity()).findViewById(R.id.flagrantFouls)).setText("Flagrant Fouls: "+game.gethomeflagrant());
		
			}
			else{
				((TextView)((BasketballIndividualStatActivity) getActivity()).findViewById(R.id.playerName)).setText(team.getabbv());
				((TextView)((BasketballIndividualStatActivity) getActivity()).findViewById(R.id.teamName)).setText(team.gettname());
				((TextView)((BasketballIndividualStatActivity) getActivity()).findViewById(R.id.pointTotal)).setText("Points: "+game.getawaypts());	
				((TextView)((BasketballIndividualStatActivity) getActivity()).findViewById(R.id.fieldGoalTotal)).setText(				
						"Field Goals:" +
						"\n Made: " + game.getawayfgm() +
						"\t\t Missed: " + game.getawayfga() +
						"\n FG %: " + game.getawayfgpercent());		
				((TextView)((BasketballIndividualStatActivity) getActivity()).findViewById(R.id.fieldGoal3Total)).setText(				
						"Field Goals:" +
						"\n Made: " + game.getawayfgm3() +
						"\t\t Missed: " + game.getawayfga3() +
						"\n FG %: " + game.getawayfg3percent());	
				((TextView)((BasketballIndividualStatActivity) getActivity()).findViewById(R.id.freeThrowTotal)).setText(
						"Free Throws:" +
						"\n Made: " + game.getawayftm() +
						"\t\t Missed: " + game.getawayfta() +
						"\n FT %: " + game.getawayftpercent());	
			
				((TextView)((BasketballIndividualStatActivity) getActivity()).findViewById(R.id.reboundTotal)).setText("Rebounds: "+(game.getawaydreb()+game.getawayoreb()));
				((TextView)((BasketballIndividualStatActivity) getActivity()).findViewById(R.id.dReboundTotal)).setText("Defensive Rebounds: "+game.getawaydreb());
				((TextView)((BasketballIndividualStatActivity) getActivity()).findViewById(R.id.oReboundTotal)).setText("Offensive Rebounds: "+game.getawayoreb());
				((TextView)((BasketballIndividualStatActivity) getActivity()).findViewById(R.id.assistTotal)).setText("Assists: "+game.getawayast());
				((TextView)((BasketballIndividualStatActivity) getActivity()).findViewById(R.id.stealTotal)).setText("Steals: "+game.getawaystl());
				((TextView)((BasketballIndividualStatActivity) getActivity()).findViewById(R.id.blocksTotal)).setText("Blocks: "+game.getawayblk());
				((TextView)((BasketballIndividualStatActivity) getActivity()).findViewById(R.id.foulTotal)).setText("Fouls: "+game.getawaypf());
				((TextView)((BasketballIndividualStatActivity) getActivity()).findViewById(R.id.techFouls)).setText("Technical Fouls: "+game.getawaytech());
				((TextView)((BasketballIndividualStatActivity) getActivity()).findViewById(R.id.flagrantFouls)).setText("Flagrant Fouls: "+game.getawayflagrant());
		
			}
		}
		else{
			Players player = null;
			for(Players p: players){
				if(p.getpname().equals(name)){
					player = p;
				}
			}			
			BasketballGameStats stats = _db.getPlayerGameStats(g_id, player.getpid());
			((TextView)((BasketballIndividualStatActivity) getActivity()).findViewById(R.id.playerName)).setText(player.getpname());
			((TextView)((BasketballIndividualStatActivity) getActivity()).findViewById(R.id.teamName)).setText(team.gettname());
			((TextView)((BasketballIndividualStatActivity) getActivity()).findViewById(R.id.pointTotal)).setText("Points: "+stats.getpts());	
			((TextView)((BasketballIndividualStatActivity) getActivity()).findViewById(R.id.fieldGoalTotal)).setText(				
					"Field Goals:" +
					"\n Made: " + stats.getfgm() +
					"\t\t Missed: " + stats.getfga() +
					"\n FG %: " + stats.getfgpercent());	
			((TextView)((BasketballIndividualStatActivity) getActivity()).findViewById(R.id.fieldGoal3Total)).setText(				
					"Field Goals:" +
					"\n Made: " + stats.getfgm3() +
					"\t\t Missed: " + stats.getfga3() +
					"\n FG %: " + stats.getfg3percent());	
			((TextView)((BasketballIndividualStatActivity) getActivity()).findViewById(R.id.freeThrowTotal)).setText(
					"Free Throws:" +
					"\n Made: " + stats.getftm() +
					"\t\t Missed: " + stats.getfta() +
					"\n FT %: " + stats.getftpercent());	
		
			((TextView)((BasketballIndividualStatActivity) getActivity()).findViewById(R.id.reboundTotal)).setText("Rebounds: "+(stats.getdreb()+stats.getoreb()));
			((TextView)((BasketballIndividualStatActivity) getActivity()).findViewById(R.id.dReboundTotal)).setText("Defensive Rebounds: "+stats.getdreb());
			((TextView)((BasketballIndividualStatActivity) getActivity()).findViewById(R.id.oReboundTotal)).setText("Offensive Rebounds: "+stats.getoreb());
			((TextView)((BasketballIndividualStatActivity) getActivity()).findViewById(R.id.assistTotal)).setText("Assists: "+stats.getast());
			((TextView)((BasketballIndividualStatActivity) getActivity()).findViewById(R.id.stealTotal)).setText("Steals: "+stats.getstl());
			((TextView)((BasketballIndividualStatActivity) getActivity()).findViewById(R.id.blocksTotal)).setText("Blocks: "+stats.getblk());
			((TextView)((BasketballIndividualStatActivity) getActivity()).findViewById(R.id.foulTotal)).setText("Fouls: "+stats.getpf());
			((TextView)((BasketballIndividualStatActivity) getActivity()).findViewById(R.id.techFouls)).setText("Technical Fouls: "+stats.gettech());
			((TextView)((BasketballIndividualStatActivity) getActivity()).findViewById(R.id.flagrantFouls)).setText("Flagrant Fouls: "+stats.getflagrant());
		}
	}
}
