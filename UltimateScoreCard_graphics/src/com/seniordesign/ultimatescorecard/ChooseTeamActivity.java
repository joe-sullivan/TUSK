package com.seniordesign.ultimatescorecard;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.seniordesign.ultimatescorecard.data.basketball.BasketballActivity;
import com.seniordesign.ultimatescorecard.data.basketball.BasketballGameTime;
import com.seniordesign.ultimatescorecard.data.football.FootballActivity;
import com.seniordesign.ultimatescorecard.data.football.FootballGameTime;
import com.seniordesign.ultimatescorecard.data.hockey.HockeyActivity;
import com.seniordesign.ultimatescorecard.data.hockey.HockeyGameTime;
import com.seniordesign.ultimatescorecard.data.hockey.HockeyPlayer;
import com.seniordesign.ultimatescorecard.data.soccer.SoccerActivity;
import com.seniordesign.ultimatescorecard.data.soccer.SoccerGameTime;
import com.seniordesign.ultimatescorecard.sqlite.basketball.BasketballDatabaseHelper;
import com.seniordesign.ultimatescorecard.sqlite.football.FootballDatabaseHelper;
import com.seniordesign.ultimatescorecard.sqlite.helper.DatabaseHelper;
import com.seniordesign.ultimatescorecard.sqlite.helper.Teams;
import com.seniordesign.ultimatescorecard.sqlite.hockey.HockeyDatabaseHelper;
import com.seniordesign.ultimatescorecard.sqlite.soccer.SoccerDatabaseHelper;
import com.seniordesign.ultimatescorecard.view.StaticFinalVars;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupCollapseListener;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.TextView;
import android.widget.Toast;

// in sync with activity_choose_team.xml
public class ChooseTeamActivity extends Activity{													
	Button _addEditTeam, _deleteButton;											// this is where we can store some information
	private boolean _selectHomeTeam = false;
	private boolean _selectAwayTeam = false;									// false = select Home team, true = select Away team
	private Teams[] _teams = new Teams[2];										// Array of two Teams, used for passing teams to next activity
	private TextView _teamSelectTitle;
	private String _sportType;
	//databases
	public DatabaseHelper _db;
	public ArrayList<Teams> teamList = null;
	//expandable list views
	public String _childHome;
	public String _childAway;
	ExpandableListAdapter listAdapterHome;
    ExpandableListAdapter listAdapterAway;
    ExpandableListView ex_ListViewHome;
    ExpandableListView ex_ListViewAway;
    List<String> listDataHeaderHome;
    List<String> listDataHeaderAway;
    HashMap<String, List<String>> listDataChildHome;
    HashMap<String, List<String>> listDataChildAway;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_choose_team);
		_sportType = getIntent().getExtras().getString(StaticFinalVars.SPORT_TYPE);		
		_addEditTeam = (Button) findViewById (R.id.createNewTeam);
		_deleteButton = (Button) findViewById (R.id.deleteTeamButton);
		_addEditTeam.setOnTouchListener(longButtonTouchListener);
		_deleteButton.setOnTouchListener(longButtonTouchListener);
		
		//databases
		if(_sportType.equals("basketball")){
			_db = new BasketballDatabaseHelper(getApplicationContext());
			findViewById(R.id.chooseTeamActivity).setBackgroundResource(R.drawable.background_basketball);
			_addEditTeam.setBackgroundResource(R.drawable.view_style_slant);			
			_deleteButton.setBackgroundResource(R.drawable.view_style_slant);
			
		}
		else if(_sportType.equals("hockey")){
			_db = new HockeyDatabaseHelper(getApplicationContext());
			findViewById(R.id.chooseTeamActivity).setBackgroundResource(R.drawable.background_hockey);
			_addEditTeam.setBackgroundResource(R.drawable.view_style_gradiant);
			_deleteButton.setBackgroundResource(R.drawable.view_style_gradiant);
		}
		else if(_sportType.equals("soccer")){
			_db = new SoccerDatabaseHelper(getApplicationContext());
			findViewById(R.id.chooseTeamActivity).setBackgroundResource(R.drawable.background_soccer);
			_addEditTeam.setBackgroundResource(R.drawable.view_style_slant);
			_deleteButton.setBackgroundResource(R.drawable.view_style_slant);
		}
		else if(_sportType.equals("football")){
			_db = new FootballDatabaseHelper(getApplicationContext());
			findViewById(R.id.chooseTeamActivity).setBackgroundResource(R.drawable.background_football);
			_addEditTeam.setBackgroundResource(R.drawable.view_style_slant);
			_deleteButton.setBackgroundResource(R.drawable.view_style_slant);
		}
		else{ //should never get here anyways
			_addEditTeam.setBackgroundResource(R.drawable.view_style_slant);
			_deleteButton.setBackgroundResource(R.drawable.view_style_slant);
		}
		
		_teamSelectTitle = (TextView) findViewById(R.id.team_selection_title);
		
		// get the expandable list view
        ex_ListViewHome = (ExpandableListView) findViewById(R.id.expListViewHome);
        ex_ListViewAway = (ExpandableListView) findViewById(R.id.expListViewAway);
        
        // preparing list data
        prepareListData();
        		
		// expListview Group expanded listener
        ex_ListViewHome.setOnGroupExpandListener(new OnGroupExpandListener() {
            public void onGroupExpand(int groupPosition) {
                /*Toast.makeText(getApplicationContext(),
                        listDataHeaderHome.get(groupPosition) + " Expanded",
                        Toast.LENGTH_SHORT).show();*/
            }
        });
        ex_ListViewAway.setOnGroupExpandListener(new OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
            }
        });

        // List view Group collapsed listener
        ex_ListViewHome.setOnGroupCollapseListener(new OnGroupCollapseListener() {
            @Override
            public void onGroupCollapse(int groupPosition) {
                /*Toast.makeText(getApplicationContext(),
                        listDataHeaderHome.get(groupPosition) + " Collapsed",
                        Toast.LENGTH_SHORT).show();*/
            }
        });
        ex_ListViewAway.setOnGroupCollapseListener(new OnGroupCollapseListener() {
            @Override
            public void onGroupCollapse(int groupPosition) {
            }
        });

        // List view on child click listeners
        ex_ListViewHome.setOnChildClickListener(new OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parentA, View v,
                                        int groupPositionA, int childPositionA, long id) {
                /*Toast.makeText(getApplicationContext(), listDataHeaderHome.get(groupPositionA) + " : " +
                        listDataChildHome.get(listDataHeaderHome.get(groupPositionA)).get(childPositionA), Toast.LENGTH_SHORT).show();                
                              */
        		_childHome = listDataChildHome.get(listDataHeaderHome.get(groupPositionA)).get(childPositionA);
        		teamList = (ArrayList<Teams>) _db.getAllTeams();
        		for(Teams t: teamList){								// get database 
					if(t.gettname().equals(_childHome)){
						_teams[0] = t; 								// set Home team
					}
                }	
        		if(teamList.size() < 2){							// if list of teams is less than 2 show message
        			Toast.makeText(getApplicationContext(), "Create Another Team", Toast.LENGTH_SHORT).show();
        			prepareListData();
					return false;
        		}
        		
                if (_selectAwayTeam == true){
                	if(!_teams[1].gettname().toString().equals(_childHome)){
                        listDataHeaderHome.set(groupPositionA, _childHome);                
                        changeHomeListData(_childHome);		
                        _teamSelectTitle.setText(getResources().getString(R.string.home_team_select_title));
    					_addEditTeam.setText("Create a New Team");
    					_selectHomeTeam = true;
    					ex_ListViewHome.collapseGroup(groupPositionA); 
    					confirmTeams();
                	}    
                	else{
                		Toast.makeText(getApplicationContext(), "Team already Selected", Toast.LENGTH_SHORT).show();
                		ex_ListViewHome.collapseGroup(groupPositionA); 
                		return false;
                	}					
				}
                else if (_selectAwayTeam == false){
                	listDataHeaderHome.set(groupPositionA, _childHome);                
                    changeHomeListData(_childHome);
	                _teamSelectTitle.setText(getResources().getString(R.string.away_team_select_title));
					_addEditTeam.setText("Edit Selected Team");
					_selectHomeTeam = true;
					ex_ListViewHome.collapseGroup(groupPositionA);  
                }
                return false;
			}
            
        });
        ex_ListViewAway.setOnChildClickListener(new OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parentB, View v,
                                        int groupPositionB, int childPositionB, long id) {
                _childAway = listDataChildAway.get(listDataHeaderAway.get(groupPositionB)).get(childPositionB);
                teamList = (ArrayList<Teams>) _db.getAllTeams();        
        		for(Teams t: teamList){
					if(t.gettname().equals(_childAway)){
						_teams[1] = t; // set Away team
					}
                } 	
        		if(teamList.size() < 2){
        			Toast.makeText(getApplicationContext(), "Create Another Team", Toast.LENGTH_SHORT).show();
        			prepareListData();
					return false;
        		}        
        		
                if (_selectHomeTeam == true){
                	if(!_teams[0].gettname().toString().equals(_childAway)){                		
	                    listDataHeaderAway.set(groupPositionB, _childAway);   
	                    changeAwayListData(_childAway);	
		                _teamSelectTitle.setText(getResources().getString(R.string.home_team_select_title));
	                    _addEditTeam.setText("Create a New Team");
		                _selectAwayTeam = true;
						ex_ListViewAway.collapseGroup(groupPositionB); 
						confirmTeams();
                	}
	            	else{
	            		Toast.makeText(getApplicationContext(), "Team already Selected", Toast.LENGTH_SHORT).show();
	            		ex_ListViewAway.collapseGroup(groupPositionB); 
	            		return false;
	            	}					
				}
                else{
                	listDataHeaderAway.set(groupPositionB, _childAway);                
                    changeAwayListData(_childAway);
	                _teamSelectTitle.setText(getResources().getString(R.string.home_team_select_title));
					_addEditTeam.setText("Edit Selected Team");
	                _selectAwayTeam = true;
	                ex_ListViewAway.collapseGroup(groupPositionB);   
                }
                return false;
            }
        });
	}	
	// change the Home team expandable list 
	public void changeHomeListData(String child) {
        listDataHeaderHome = new ArrayList<String>();
        listDataChildHome = new HashMap<String, List<String>>();
        // Adding child heater data
        listDataHeaderHome.add(child);	
		// Adding child data
		List<String> TeamHome = new ArrayList<String>();    
        List<Teams> teamList = new ArrayList<Teams>();        
        teamList = (ArrayList<Teams>) _db.getAllTeams();        
		for(Teams t: teamList){
			TeamHome.add(t.gettname());
		}
        listDataChildHome.put(listDataHeaderHome.get(0),TeamHome); // Header, Child Home data
        listAdapterHome = new ExpandableListAdapter(this, listDataHeaderHome, listDataChildHome);
        ex_ListViewHome.setAdapter(listAdapterHome);
    }
	// change the Away team expandable list 
	public void changeAwayListData(String child) {
        listDataHeaderAway = new ArrayList<String>();
        listDataChildAway = new HashMap<String, List<String>>();
        // Adding child heater data
        listDataHeaderAway.add(child);		
		// Adding child data
		List<String> TeamAway = new ArrayList<String>();   
        List<Teams> teamList = new ArrayList<Teams>();        
        teamList = (ArrayList<Teams>) _db.getAllTeams();        
		for(Teams t: teamList){
			TeamAway.add(t.gettname());
		}
        listDataChildAway.put(listDataHeaderAway.get(0),TeamAway); // Header, Child Home data
        listAdapterAway = new ExpandableListAdapter(this, listDataHeaderAway, listDataChildAway);
        ex_ListViewAway.setAdapter(listAdapterAway);
    }
	// initialize expandable lists Home and Away teams
	public void prepareListData() {
        listDataHeaderHome = new ArrayList<String>();
        listDataHeaderAway = new ArrayList<String>();
        listDataChildHome = new HashMap<String, List<String>>();
        listDataChildAway = new HashMap<String, List<String>>();

        // Adding child heater data
        listDataHeaderHome.add("Home Team");
        listDataHeaderAway.add("Away Team");
		
		// Adding child data
		List<String> TeamHome = new ArrayList<String>();
     	List<String> TeamAway = new ArrayList<String>();  
     	
        // Load team list           
        teamList = (ArrayList<Teams>) _db.getAllTeams();
		for(Teams t: teamList){
			TeamHome.add(t.gettname());
			TeamAway.add(t.gettname());
		}
		if(teamList.isEmpty())
			_deleteButton.setEnabled(false);  
        listDataChildHome.put(listDataHeaderHome.get(0),TeamHome) ; // Header, Child Home data
        listAdapterHome = new ExpandableListAdapter(this, listDataHeaderHome, listDataChildHome);
        ex_ListViewHome.setAdapter(listAdapterHome);
        listDataChildAway.put(listDataHeaderAway.get(0),TeamAway); // Header, Child Away data
        listAdapterAway = new ExpandableListAdapter(this, listDataHeaderAway, listDataChildAway);
        ex_ListViewAway.setAdapter(listAdapterAway);
        _selectHomeTeam = false;
        _selectAwayTeam = false;
		_addEditTeam.setText("Create a New Team");
    }

	//when back button is hit to return to this page (restart it)
	@Override
	protected void onRestart() {
		super.onRestart();
		_selectHomeTeam = false;
		_selectAwayTeam = false;//time to select home team
		_teamSelectTitle.setText(getResources().getString(R.string.home_team_select_title));			//change the text of title
		_teams[0] = null;																				//empty array of team names to be sent along
		_teams[1] = null;
		prepareListData();
		_deleteButton.setEnabled(true);																	//reset the delete button
		
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		prepareListData();								 // initialize expandable list views
	}

	//when the create a team (edit selected team) button is pressed, this method is executed
	public void addViews(View v){
		Intent intent = new Intent(getApplicationContext(), CreateTeamActivity.class);
		if(!_selectHomeTeam && !_selectAwayTeam){
			intent.putExtra(StaticFinalVars.CREATE_EDIT, "");
			intent.putExtra(StaticFinalVars.SPORT_TYPE, _sportType);
			startActivityForResult(intent, StaticFinalVars.CREATE_TEAM_CODE);	
			_addEditTeam.setText("Create a New Team");
		}
		else if(_selectHomeTeam){
			intent.putExtra(StaticFinalVars.CREATE_EDIT, _teams[0].gettname());
			intent.putExtra(StaticFinalVars.SPORT_TYPE, _sportType);
			startActivityForResult(intent, StaticFinalVars.EDIT_TEAM_CODE);	
			_addEditTeam.setText("Create a New Team");
		}
		else if(_selectAwayTeam){
			intent.putExtra(StaticFinalVars.CREATE_EDIT, _teams[1].gettname());
			intent.putExtra(StaticFinalVars.SPORT_TYPE, _sportType);
			startActivityForResult(intent, StaticFinalVars.EDIT_TEAM_CODE);
			_addEditTeam.setText("Create a New Team");
		}
	}
	
	public OnTouchListener longButtonTouchListener = new OnTouchListener(){
		@Override
		public boolean onTouch(View view, MotionEvent event) {
			if(event.getAction() == MotionEvent.ACTION_DOWN){
				view.setBackgroundResource(R.drawable.view_style_plain_long_clicked);
				((Button)view).setTextColor(getResources().getColor(R.color.white));		
			}
			else if(event.getAction() == MotionEvent.ACTION_UP){
				((Button)view).setTextColor(getResources().getColor(R.color.black));		
				if(_sportType.equals("basketball")){
					_addEditTeam.setBackgroundResource(R.drawable.view_style_slant);			
					_deleteButton.setBackgroundResource(R.drawable.view_style_slant);
				}
				else if(_sportType.equals("hockey")){
					_addEditTeam.setBackgroundResource(R.drawable.view_style_gradiant);
					_deleteButton.setBackgroundResource(R.drawable.view_style_gradiant);
				}
				else if(_sportType.equals("soccer")){
					_addEditTeam.setBackgroundResource(R.drawable.view_style_slant);
					_deleteButton.setBackgroundResource(R.drawable.view_style_slant);
				}
				else if(_sportType.equals("football")){
					_addEditTeam.setBackgroundResource(R.drawable.view_style_slant);
					_deleteButton.setBackgroundResource(R.drawable.view_style_slant);
				}	
			}
			return false;
		}
	};
	
	//confirm that those are the teams that we want to keep scores for
	//public void confirmTeams(final TextView tv){
	public void confirmTeams(){
		Builder confirmDialog = new Builder(this);																//building a dialog box for this
		confirmDialog.setTitle("Team Selection Confirmation");
		confirmDialog.setMessage("Keeping scores for "+ _teams[0].gettname() + " vs. " + _teams[1].gettname() + "?");
				
		confirmDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener(){							//the positive yes button
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				Intent intent;
				if(_sportType.equals("soccer")){
					intent = new Intent(getApplicationContext(), SoccerActivity.class);						//create new intent (basketball activity)
					intent.putExtra(StaticFinalVars.GAME_TIME, new SoccerGameTime(_teams[0], _teams[1]));
				}
				else if (_sportType.equals("football")){
					intent = new Intent(getApplicationContext(), FootballActivity.class);
					intent.putExtra(StaticFinalVars.GAME_TIME, new FootballGameTime(_teams[0], _teams[1]));
				}
				else if (_sportType.equals("hockey")){
					intent = new Intent(getApplicationContext(), HockeyActivity.class);
					intent.putExtra(StaticFinalVars.GAME_TIME, new HockeyGameTime(_teams[0], _teams[1]));
				}
				else {
					intent = new Intent(getApplicationContext(), BasketballActivity.class);	
					intent.putExtra(StaticFinalVars.GAME_TIME, new BasketballGameTime(_teams[0], _teams[1]));
				}				
				startActivity(intent);																//let's go
			}
		});
		confirmDialog.setNegativeButton("No", new DialogInterface.OnClickListener(){				//the negative no button		
			@Override
			public void onClick(DialogInterface arg0, int arg1) {		
				_teams[0] = null;
				_teams[1] = null;
				_selectHomeTeam = false;
				_selectAwayTeam = false;
				_deleteButton.setEnabled(true);	
				// preparing list data
		        prepareListData();
			}
		});
		confirmDialog.show();																		//make it happen
	}
	
	//deleting a team if the database is not empty
	public void deleteATeam(View view){
		
		Builder builder = new Builder(ChooseTeamActivity.this);
		builder.setTitle("Delete Team:");
		final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String> (ChooseTeamActivity.this,
				android.R.layout.select_dialog_singlechoice);
		for(Teams team : teamList){
			arrayAdapter.add(team.gettname());
		}
		
		builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});			
		builder.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				String teamName = arrayAdapter.getItem(which);
				for(Teams t: teamList){
					if(t.gettname().equals(teamName)){
						final Teams team = t;
						Builder b = new Builder(ChooseTeamActivity.this);
						b.setTitle("Delete Team");
						b.setMessage("Are you sure you want to delete the team '" + teamName + "'?");
						
						b.setPositiveButton("Yes", new DialogInterface.OnClickListener(){	
							@Override
							public void onClick(DialogInterface dialog, int arg1) {
								_db.deleteTeam(team.gettid());
						        prepareListData();
							}
						});
						b.setNegativeButton("No", new DialogInterface.OnClickListener(){	
							@Override
							public void onClick(DialogInterface dialog, int arg1) {
								
							}
						});
						b.show();
					}
				}
			}
		});
		builder.show();
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
	
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		//saveTeams();
		finish();
	}
}
