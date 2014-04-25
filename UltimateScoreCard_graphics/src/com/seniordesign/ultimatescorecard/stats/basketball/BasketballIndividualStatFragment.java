package com.seniordesign.ultimatescorecard.stats.basketball;

import java.util.ArrayList;

import com.seniordesign.ultimatescorecard.R;
import com.seniordesign.ultimatescorecard.data.GameInfo;
import com.seniordesign.ultimatescorecard.sqlite.basketball.BasketballDatabaseHelper;
import com.seniordesign.ultimatescorecard.sqlite.basketball.BasketballGameStats;
import com.seniordesign.ultimatescorecard.sqlite.basketball.BasketballGames;
import com.seniordesign.ultimatescorecard.sqlite.helper.Games;
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
	private boolean _ifGameView, _ifPlayerView, _home, _ifTeamStats, _average;
	private Players _player;
	private GameInfo _gameInfo;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = (View) inflater.inflate(R.layout.fragment_individual_basketball, container, false);
		setHasOptionsMenu(true);
		view.setBackgroundResource(R.drawable.background_hardwood);

		return view;
	}
	
	@Override
	public void onResume() {
		super.onResume();
		_db = ((BasketballIndividualStatActivity) getActivity())._db;
		
		_ifGameView = ((BasketballIndividualStatActivity) getActivity())._ifGameView;
		_ifPlayerView = ((BasketballIndividualStatActivity) getActivity())._ifPlayerView;
		
		if(_ifGameView){
			_player = ((BasketballIndividualStatActivity) getActivity())._player;
			_gameInfo = ((BasketballIndividualStatActivity) getActivity())._gameInfo;
			_home = ((BasketballIndividualStatActivity) getActivity())._home;
			_ifTeamStats = ((BasketballIndividualStatActivity) getActivity())._ifTeamStats;
			
			if(_ifTeamStats){
				BasketballGames game = ((BasketballIndividualStatActivity) getActivity())._game;
				if(_home){
					Teams team = _gameInfo.getHomeTeam();
					((TextView)((BasketballIndividualStatActivity) getActivity()).findViewById(R.id.playerName)).setText(team.getabbv());
					((TextView)((BasketballIndividualStatActivity) getActivity()).findViewById(R.id.teamName)).setText(team.gettname());
					((TextView)((BasketballIndividualStatActivity) getActivity()).findViewById(R.id.pointTotal)).setText("Points: "+game.gethomepts());	
					((TextView)((BasketballIndividualStatActivity) getActivity()).findViewById(R.id.fieldGoalTotal)).setText(				
							"Field Goals:" +
							"\n Made: " + game.gethomefgm() +
							"\t\t Attempted: " + game.gethomefga() +
							"\n FG %: " + game.gethomefgpercent());	
					((TextView)((BasketballIndividualStatActivity) getActivity()).findViewById(R.id.fieldGoal3Total)).setText(				
							"3 Point Field Goals:" +
							"\n Made: " + game.gethomefgm3() +
							"\t\t Attempted: " + game.gethomefga3() +
							"\n FG %: " + game.gethomefg3percent());		
					((TextView)((BasketballIndividualStatActivity) getActivity()).findViewById(R.id.freeThrowTotal)).setText(
							"Free Throws:" +
							"\n Made: " + game.gethomeftm() +
							"\t\t Attempted: " + game.gethomefta() +
							"\n FT %: " + game.gethomeftpercent());	
				
					((TextView)((BasketballIndividualStatActivity) getActivity()).findViewById(R.id.reboundTotal)).setText("Rebounds: "+(game.gethomedreb()+game.gethomeoreb()));
					((TextView)((BasketballIndividualStatActivity) getActivity()).findViewById(R.id.dReboundTotal)).setText("Defensive Rebounds: "+game.gethomedreb());
					((TextView)((BasketballIndividualStatActivity) getActivity()).findViewById(R.id.oReboundTotal)).setText("Offensive Rebounds: "+game.gethomeoreb());
					((TextView)((BasketballIndividualStatActivity) getActivity()).findViewById(R.id.assistTotal)).setText("Assists: "+game.gethomeast());
					((TextView)((BasketballIndividualStatActivity) getActivity()).findViewById(R.id.stealTotal)).setText("Steals: "+game.gethomestl());
					((TextView)((BasketballIndividualStatActivity) getActivity()).findViewById(R.id.blocksTotal)).setText("Blocks: "+game.gethomeblk());
					((TextView)((BasketballIndividualStatActivity) getActivity()).findViewById(R.id.turnoverTotal)).setText("Turnovers: "+game.gethometo());
					((TextView)((BasketballIndividualStatActivity) getActivity()).findViewById(R.id.foulTotal)).setText("Fouls: "+game.gethomepf());
					((TextView)((BasketballIndividualStatActivity) getActivity()).findViewById(R.id.techFouls)).setText("Technical Fouls: "+game.gethometech());
					((TextView)((BasketballIndividualStatActivity) getActivity()).findViewById(R.id.flagrantFouls)).setText("Flagrant Fouls: "+game.gethomeflagrant());
					((TextView)((BasketballIndividualStatActivity) getActivity()).findViewById(R.id.assist2turnover)).setText("AST/TO: "+game.gethomeAst2TO());
					((TextView)((BasketballIndividualStatActivity) getActivity()).findViewById(R.id.AFG)).setText("AFG%: "+game.gethomeAFG());
			
				}
				else{
					Teams team = _gameInfo.getAwayTeam();
					((TextView)((BasketballIndividualStatActivity) getActivity()).findViewById(R.id.playerName)).setText(team.getabbv());
					((TextView)((BasketballIndividualStatActivity) getActivity()).findViewById(R.id.teamName)).setText(team.gettname());
					((TextView)((BasketballIndividualStatActivity) getActivity()).findViewById(R.id.pointTotal)).setText("Points: "+game.getawaypts());	
					((TextView)((BasketballIndividualStatActivity) getActivity()).findViewById(R.id.fieldGoalTotal)).setText(				
							"Field Goals:" +
							"\n Made: " + game.getawayfgm() +
							"\t\t Attempted: " + game.getawayfga() +
							"\n FG %: " + game.getawayfgpercent());		
					((TextView)((BasketballIndividualStatActivity) getActivity()).findViewById(R.id.fieldGoal3Total)).setText(				
							"3 Point Field Goals:" +
							"\n Made: " + game.getawayfgm3() +
							"\t\t Attempted: " + game.getawayfga3() +
							"\n FG %: " + game.getawayfg3percent());	
					((TextView)((BasketballIndividualStatActivity) getActivity()).findViewById(R.id.freeThrowTotal)).setText(
							"Free Throws:" +
							"\n Made: " + game.getawayftm() +
							"\t\t Attempted: " + game.getawayfta() +
							"\n FT %: " + game.getawayftpercent());	
				
					((TextView)((BasketballIndividualStatActivity) getActivity()).findViewById(R.id.reboundTotal)).setText("Rebounds: "+(game.getawaydreb()+game.getawayoreb()));
					((TextView)((BasketballIndividualStatActivity) getActivity()).findViewById(R.id.dReboundTotal)).setText("Defensive Rebounds: "+game.getawaydreb());
					((TextView)((BasketballIndividualStatActivity) getActivity()).findViewById(R.id.oReboundTotal)).setText("Offensive Rebounds: "+game.getawayoreb());
					((TextView)((BasketballIndividualStatActivity) getActivity()).findViewById(R.id.assistTotal)).setText("Assists: "+game.getawayast());
					((TextView)((BasketballIndividualStatActivity) getActivity()).findViewById(R.id.stealTotal)).setText("Steals: "+game.getawaystl());
					((TextView)((BasketballIndividualStatActivity) getActivity()).findViewById(R.id.blocksTotal)).setText("Blocks: "+game.getawayblk());
					((TextView)((BasketballIndividualStatActivity) getActivity()).findViewById(R.id.turnoverTotal)).setText("Turnovers: "+game.getawayto());
					((TextView)((BasketballIndividualStatActivity) getActivity()).findViewById(R.id.foulTotal)).setText("Fouls: "+game.getawaypf());
					((TextView)((BasketballIndividualStatActivity) getActivity()).findViewById(R.id.techFouls)).setText("Technical Fouls: "+game.getawaytech());
					((TextView)((BasketballIndividualStatActivity) getActivity()).findViewById(R.id.flagrantFouls)).setText("Flagrant Fouls: "+game.getawayflagrant());
					((TextView)((BasketballIndividualStatActivity) getActivity()).findViewById(R.id.assist2turnover)).setText("AST/TO: "+game.getawayAst2TO());
					((TextView)((BasketballIndividualStatActivity) getActivity()).findViewById(R.id.AFG)).setText("AFG%: "+game.getawayAFG());
			
				}
			}
			else{
				BasketballGameStats stats = _db.getPlayerGameStats(_gameInfo.getgid(), _player.getpid());
				Teams team = null;
				if(_home){
					team = _gameInfo.getHomeTeam();
				}
				else{
					team = _gameInfo.getAwayTeam();
				}
				((TextView)((BasketballIndividualStatActivity) getActivity()).findViewById(R.id.playerName)).setText(_player.getpname());
				((TextView)((BasketballIndividualStatActivity) getActivity()).findViewById(R.id.teamName)).setText(team.gettname());
				((TextView)((BasketballIndividualStatActivity) getActivity()).findViewById(R.id.pointTotal)).setText("Points: "+stats.getpts());	
				((TextView)((BasketballIndividualStatActivity) getActivity()).findViewById(R.id.fieldGoalTotal)).setText(				
						"Field Goals:" +
						"\n Made: " + stats.getfgm() +
						"\t\t Attempted: " + stats.getfga() +
						"\n FG %: " + stats.getfgpercent());	
				((TextView)((BasketballIndividualStatActivity) getActivity()).findViewById(R.id.fieldGoal3Total)).setText(				
						"3 Point Field Goals:" +
						"\n Made: " + stats.getfgm3() +
						"\t\t Attempted: " + stats.getfga3() +
						"\n FG %: " + stats.getfg3percent());	
				((TextView)((BasketballIndividualStatActivity) getActivity()).findViewById(R.id.freeThrowTotal)).setText(
						"Free Throws:" +
						"\n Made: " + stats.getftm() +
						"\t\t Attempted: " + stats.getfta() +
						"\n FT %: " + stats.getftpercent());	
			
				((TextView)((BasketballIndividualStatActivity) getActivity()).findViewById(R.id.reboundTotal)).setText("Rebounds: "+(stats.getdreb()+stats.getoreb()));
				((TextView)((BasketballIndividualStatActivity) getActivity()).findViewById(R.id.dReboundTotal)).setText("Defensive Rebounds: "+stats.getdreb());
				((TextView)((BasketballIndividualStatActivity) getActivity()).findViewById(R.id.oReboundTotal)).setText("Offensive Rebounds: "+stats.getoreb());
				((TextView)((BasketballIndividualStatActivity) getActivity()).findViewById(R.id.assistTotal)).setText("Assists: "+stats.getast());
				((TextView)((BasketballIndividualStatActivity) getActivity()).findViewById(R.id.stealTotal)).setText("Steals: "+stats.getstl());
				((TextView)((BasketballIndividualStatActivity) getActivity()).findViewById(R.id.blocksTotal)).setText("Blocks: "+stats.getblk());
				((TextView)((BasketballIndividualStatActivity) getActivity()).findViewById(R.id.turnoverTotal)).setText("Turnovers: "+stats.getto());
				((TextView)((BasketballIndividualStatActivity) getActivity()).findViewById(R.id.foulTotal)).setText("Fouls: "+stats.getpf());
				((TextView)((BasketballIndividualStatActivity) getActivity()).findViewById(R.id.techFouls)).setText("Technical Fouls: "+stats.gettech());
				((TextView)((BasketballIndividualStatActivity) getActivity()).findViewById(R.id.flagrantFouls)).setText("Flagrant Fouls: "+stats.getflagrant());
				((TextView)((BasketballIndividualStatActivity) getActivity()).findViewById(R.id.assist2turnover)).setText("AST/TO: "+stats.getAst2TO());
				((TextView)((BasketballIndividualStatActivity) getActivity()).findViewById(R.id.AFG)).setText("AFG%: "+stats.getAFG());
			}
			
		}
		else if(_ifPlayerView){
			_average = ((BasketballIndividualStatActivity) getActivity())._average;
			_player = ((BasketballIndividualStatActivity) getActivity())._player;
			Teams team = ((BasketballIndividualStatActivity) getActivity())._team;

			if(!_average){
				BasketballGames game = ((BasketballIndividualStatActivity) getActivity())._game;
				BasketballGameStats stats = _db.getPlayerGameStats(game.getgid(), _player.getpid());
				
				((TextView)((BasketballIndividualStatActivity) getActivity()).findViewById(R.id.playerName)).setText(_player.getpname());
				((TextView)((BasketballIndividualStatActivity) getActivity()).findViewById(R.id.teamName)).setText(team.gettname());
				((TextView)((BasketballIndividualStatActivity) getActivity()).findViewById(R.id.pointTotal)).setText("Points: "+stats.getpts());	
				((TextView)((BasketballIndividualStatActivity) getActivity()).findViewById(R.id.fieldGoalTotal)).setText(				
						"Field Goals:" +
						"\n Made: " + stats.getfgm() +
						"\t\t Attempted: " + stats.getfga() +
						"\n FG %: " + stats.getfgpercent());	
				((TextView)((BasketballIndividualStatActivity) getActivity()).findViewById(R.id.fieldGoal3Total)).setText(				
						"3 Point Field Goals:" +
						"\n Made: " + stats.getfgm3() +
						"\t\t Attempted: " + stats.getfga3() +
						"\n FG %: " + stats.getfg3percent());	
				((TextView)((BasketballIndividualStatActivity) getActivity()).findViewById(R.id.freeThrowTotal)).setText(
						"Free Throws:" +
						"\n Made: " + stats.getftm() +
						"\t\t Attempted: " + stats.getfta() +
						"\n FT %: " + stats.getftpercent());	
			
				((TextView)((BasketballIndividualStatActivity) getActivity()).findViewById(R.id.reboundTotal)).setText("Rebounds: "+(stats.getdreb()+stats.getoreb()));
				((TextView)((BasketballIndividualStatActivity) getActivity()).findViewById(R.id.dReboundTotal)).setText("Defensive Rebounds: "+stats.getdreb());
				((TextView)((BasketballIndividualStatActivity) getActivity()).findViewById(R.id.oReboundTotal)).setText("Offensive Rebounds: "+stats.getoreb());
				((TextView)((BasketballIndividualStatActivity) getActivity()).findViewById(R.id.assistTotal)).setText("Assists: "+stats.getast());
				((TextView)((BasketballIndividualStatActivity) getActivity()).findViewById(R.id.stealTotal)).setText("Steals: "+stats.getstl());
				((TextView)((BasketballIndividualStatActivity) getActivity()).findViewById(R.id.blocksTotal)).setText("Blocks: "+stats.getblk());
				((TextView)((BasketballIndividualStatActivity) getActivity()).findViewById(R.id.turnoverTotal)).setText("Turnovers: "+stats.getto());
				((TextView)((BasketballIndividualStatActivity) getActivity()).findViewById(R.id.foulTotal)).setText("Fouls: "+stats.getpf());
				((TextView)((BasketballIndividualStatActivity) getActivity()).findViewById(R.id.techFouls)).setText("Technical Fouls: "+stats.gettech());
				((TextView)((BasketballIndividualStatActivity) getActivity()).findViewById(R.id.flagrantFouls)).setText("Flagrant Fouls: "+stats.getflagrant());
				((TextView)((BasketballIndividualStatActivity) getActivity()).findViewById(R.id.assist2turnover)).setText("AST/TO: "+stats.getAst2TO());
				((TextView)((BasketballIndividualStatActivity) getActivity()).findViewById(R.id.AFG)).setText("AFG%: "+stats.getAFG());
			
			}
			else{
				ArrayList<BasketballGameStats> allStats = (ArrayList<BasketballGameStats>) _db.getPlayerAllGameStats(_player.getpid());
				ArrayList<Games> games = (ArrayList<Games>) _db.getAllGamesTeam(_player.gettid());
				double pts = 0,fgm = 0,fga = 0,fgm3 = 0,fga3 = 0,ftm = 0,fta = 0,oreb = 0,dreb = 0,
						ast = 0,stl = 0,blk = 0,to = 0,pf = 0,tech = 0,flagrant = 0;
				int n = 0;
				for(BasketballGameStats s: allStats){
					for(Games g: games){
						if(s.getgid()==g.getgid()){
							pts += s.getpts();
							fgm += s.getfgm();
							fga += s.getfga();
							fgm3 += s.getfgm3();
							fga3 += s.getfga3();
							ftm += s.getftm();
							fta += s.getfta();
							oreb += s.getoreb();
							dreb += s.getdreb();
						    ast += s.getast();
						    stl += s.getstl();
						    blk += s.getblk();
						    to += s.getto();
						    pf += s.getpf();
						    tech += s.gettech();
						    flagrant += s.getflagrant();
						    n++;
						}
					}
				}
				pts /= n;
				fgm /= n;
				fga /= n;
				fgm3 /= n;
				fga3 /= n;
				ftm /= n;
				fta /= n;
				oreb /= n;
				dreb /= n;
			    ast /= n;
			    stl /= n;
			    blk /= n;
			    to /= n;
			    pf /= n;
			    tech /= n;
			    flagrant /= n;
			    
			    String fgpercent;
				if(fga > 0){
					fgpercent= String.format("%.3f", (double) fgm / fga);
				}
				else{
					fgpercent= "N/A";
				}
				
				String fg3percent;
				if(fga3 > 0){
					fg3percent = String.format("%.3f", (double) fgm3 / fga3);
				}
				else{
					fg3percent = "N/A";
				}
				
				String ftpercent;
				if(fta > 0){
					ftpercent= String.format("%.3f", (double) ftm / fta);
				}
				else{
					ftpercent= "N/A";
				}
				
				String ast2TO;
				if(to>0){
					ast2TO = String.format("%.2f", (double) ast/to);
				}
				else{
					ast2TO = "N/A";
				}
				
				String AFG;
				if(fga>0){
					AFG = String.format("%.3f", (double) (fgm+fgm3/2.0)/fga);
				}
				else{
					AFG = "N/A";
				}			
			    
			    ((TextView)((BasketballIndividualStatActivity) getActivity()).findViewById(R.id.playerName)).setText(_player.getpname() + " - Average Stats");
				((TextView)((BasketballIndividualStatActivity) getActivity()).findViewById(R.id.teamName)).setText(team.gettname());
				((TextView)((BasketballIndividualStatActivity) getActivity()).findViewById(R.id.pointTotal)).setText("Points: "+String.format("%.2f",pts));	
				((TextView)((BasketballIndividualStatActivity) getActivity()).findViewById(R.id.fieldGoalTotal)).setText(				
						"Field Goals:" +
						"\n Made: " + String.format("%.2f",fgm) +
						"\t\t Attempted: " + String.format("%.2f",fga) +
						"\n FG %: " + fgpercent);	
				((TextView)((BasketballIndividualStatActivity) getActivity()).findViewById(R.id.fieldGoal3Total)).setText(				
						"3 Point Field Goals:" +
						"\n Made: " + String.format("%.2f",fgm3) +
						"\t\t Attempted: " + String.format("%.2f",fga3) +
						"\n FG %: " + fg3percent);	
				((TextView)((BasketballIndividualStatActivity) getActivity()).findViewById(R.id.freeThrowTotal)).setText(
						"Free Throws:" +
						"\n Made: " + String.format("%.2f",ftm) +
						"\t\t Attempted: " + String.format("%.2f",fta) +
						"\n FT %: " + ftpercent);	
			
				((TextView)((BasketballIndividualStatActivity) getActivity()).findViewById(R.id.reboundTotal)).setText("Rebounds: "+String.format("%.2f",(dreb+oreb)));
				((TextView)((BasketballIndividualStatActivity) getActivity()).findViewById(R.id.dReboundTotal)).setText("Defensive Rebounds: "+String.format("%.2f",dreb));
				((TextView)((BasketballIndividualStatActivity) getActivity()).findViewById(R.id.oReboundTotal)).setText("Offensive Rebounds: "+String.format("%.2f",oreb));
				((TextView)((BasketballIndividualStatActivity) getActivity()).findViewById(R.id.assistTotal)).setText("Assists: "+String.format("%.2f",ast));
				((TextView)((BasketballIndividualStatActivity) getActivity()).findViewById(R.id.stealTotal)).setText("Steals: "+String.format("%.2f",stl));
				((TextView)((BasketballIndividualStatActivity) getActivity()).findViewById(R.id.blocksTotal)).setText("Blocks: "+String.format("%.2f",blk));
				((TextView)((BasketballIndividualStatActivity) getActivity()).findViewById(R.id.turnoverTotal)).setText("Turnovers: "+String.format("%.2f",to));
				((TextView)((BasketballIndividualStatActivity) getActivity()).findViewById(R.id.foulTotal)).setText("Fouls: "+String.format("%.2f",pf));
				((TextView)((BasketballIndividualStatActivity) getActivity()).findViewById(R.id.techFouls)).setText("Technical Fouls: "+String.format("%.2f",tech));
				((TextView)((BasketballIndividualStatActivity) getActivity()).findViewById(R.id.flagrantFouls)).setText("Flagrant Fouls: "+String.format("%.2f",flagrant));
				((TextView)((BasketballIndividualStatActivity) getActivity()).findViewById(R.id.assist2turnover)).setText("AST/TO: "+ast2TO);
				((TextView)((BasketballIndividualStatActivity) getActivity()).findViewById(R.id.AFG)).setText("AFG%: "+AFG);
		
			}
		}
	}
}
