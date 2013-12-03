package com.seniordesign.ultimatescorecard;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

//in sync with activity_choose_team.xml
public class ChooseTeamActivity extends Activity{
	LinearLayout _listOfTeams;																			//refers to the linear layout where we add the team names
	Button _deleteButton;																				//obviously refers to the delete button
	private SharedPreferences _teamsEntered;															//this is where we can store some information
	private Editor _prefEditor;																			//use the preference editor to edit the shared preference
	private boolean _selectAwayTeam = false;															//false = select Home team, true = select Away team
	private boolean _setDelete = false;																	//false = we can delete a team, true = we can't delete a team
	private String[] _teams = new String[2];															//string of two teams, used for passing names to next activity
	private TextView _teamSelectTitle;
	private String _sportType;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_choose_team);
		_sportType = getIntent().getExtras().getString("SPORT");
		
		_teamsEntered = getSharedPreferences( _sportType+"TeamList" , MODE_PRIVATE);					//get shared preference, codeword = basketballTeamList
		_prefEditor = _teamsEntered.edit();																//preference editor allows us to edit specified shared preference
		
		_teamSelectTitle = (TextView) findViewById(R.id.team_selection_title);							//getting the view of some features implemented in xml
		_listOfTeams = (LinearLayout) findViewById (R.id.teamListLayout);
		_deleteButton = (Button) findViewById (R.id.deleteTeamButton);
		
		if(_teamsEntered.getString("basketballTeamList", null) != null){								//read from shared preference to get saved team names if any
			loadTeams();
		}
	}	
	
	//when back button is hit to return to this page (restart it)
	@Override
	protected void onRestart() {
		super.onRestart();
		_selectAwayTeam = false;																		//time to select home team
		_teamSelectTitle.setText(getResources().getString(R.string.home_team_select_title));			//change the text of title
		_teams[0] = null;																				//empty array of team names to be sent along
		_teams[1] = null;
		_deleteButton.setEnabled(true);																	//reset the delete button
		for(int i=0; i < _listOfTeams.getChildCount(); i++){											//refresh all items in the linear layout
			TextView newItem = ((TextView)_listOfTeams.getChildAt(i));	
			newItem.setBackgroundColor(getResources().getColor(R.color.white));
			newItem.setTextColor(getResources().getColor(R.color.black));
		}
	}
	
	//getting string from shared preference, parsing it, and add to linear layout
	private void loadTeams(){
		String[] names = _teamsEntered.getString(_sportType+"TeamList", null).split(",");
		for(int i=0; i<names.length; i++){
			this.addNewTeam(names[i]);
		}
	}

	//saving team name in linear layout to the shared preference in phone
	@SuppressLint("CommitPrefEdits")
	private void saveTeams(){
		StringBuilder sb = new StringBuilder();															//building the string to save
		for(int i=0; i < _listOfTeams.getChildCount(); i++){
			sb.append(((TextView)_listOfTeams.getChildAt(i)).getText().toString()+",");					//put all the items in the list in one string separated by commas
		}
		if(sb.toString().length() > 0){																	//if our list is NOT empty
			_prefEditor.putString(_sportType+"TeamList", sb.toString());									//put the string in shared preferences
			_prefEditor.commit();																		//commit the change
		}
		else{																							//we no longer have anything in our list
			_prefEditor.clear();																		//clear saved material in shared preference
			_prefEditor.commit();																		//commit the change
		}
	}
	
	//when the create a team button is pressed, this method is executed
	public void addViews(View v){
		Builder teamCreate = new Builder(this);															//prompt user to enter team name
		teamCreate.setTitle("Team Creation");
		teamCreate.setMessage("Please enter team name.");
		
		final EditText input = new EditText(this);														//create a edit text box for the message box
		teamCreate.setView(input);
		
		teamCreate.setPositiveButton("OK", new DialogInterface.OnClickListener(){						//a OK button
			@Override
			public void onClick(DialogInterface arg0, int arg1) {										//giving our OK button some functionalities
				if(input.getText().toString().length() > 0){
					addNewTeam(input.getText().toString());												//calling our addNewTeam method to actually add the team
				}
			}
		});
		teamCreate.setNegativeButton("Cancel", new DialogInterface.OnClickListener(){					//a CANCEL button
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				//do nothing, just exit dialog box
			}
		});
		teamCreate.show();
	}
	
	//actually add a new team (list item) to our view
	private void addNewTeam(String teamName){
		final TextView textView = newTextView(teamName);														//creating a new text view giving the string we want it to display (Team Name)
		textView.setOnClickListener(new OnClickListener(){														//making this text view clickable
			@Override
			public void onClick(View view) {
				textView.setBackgroundColor(getResources().getColor(R.color.black));							//change color of background and text when view is clicked
				textView.setTextColor(getResources().getColor(R.color.white));
				if(_setDelete){																					//removing the item, we're in delete mode
					_listOfTeams.removeView(textView);
					_setDelete = false;
					_teamSelectTitle.setText(getResources().getString(R.string.home_team_select_title));
				}
				else if(!_selectAwayTeam){																		//we are selecting home team, now change interface to prompt for away team selection
					_teams[0] = textView.getText().toString();
					_teamSelectTitle.setText(getResources().getString(R.string.away_team_select_title));
					_selectAwayTeam = true;
					_deleteButton.setEnabled(false);															//when one team is selected, disable the delete feature
				}
				else{																							//not in delete mode, so one team was selected
					if(_teams[0] != textView.getText().toString()){	
						_teams[1] = textView.getText().toString();												//add the second team to the string array
						confirmTeams(textView);																	//call confirmTeam method to got to next activity
					}
					else{																						//basically, here, we're cancelling our selection
						textView.setBackgroundColor(getResources().getColor(R.color.white));
						textView.setTextColor(getResources().getColor(R.color.black));
						_teamSelectTitle.setText(getResources().getString(R.string.home_team_select_title)); 	//back to selecting home team
						_teams[0] = null;																		//set first team (home team) to null
						_selectAwayTeam = false;																//we're not selecting the away team anymore
						_deleteButton.setEnabled(true);															//enable the delete button
					}
				}
			}
		});
		_listOfTeams.addView(textView);																			//add the textView to the linear layout
	} 
	
	//confirm that those are the teams that we want to keep scores for
	public void confirmTeams(final TextView tv){
		Builder confirmDialog = new Builder(this);																//building a dialog box for this
		confirmDialog.setTitle("Team Selection Confirmation");
		confirmDialog.setMessage("Keeping scores for "+ _teams[1] + " vs. " + _teams[0] + "?");
				
		confirmDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener(){							//the positive yes button
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				Intent intent;
				if(_sportType.equals("soccer")){
					intent = new Intent(getApplicationContext(), BasketballActivity.class);						//create new intent (basketball activity)
				}
				else if (_sportType.equals("football")){
					intent = new Intent(getApplicationContext(), BasketballActivity.class);
				}
				else if (_sportType.equals("baseball")){
					intent = new Intent(getApplicationContext(), BasketballActivity.class);
				}
				else{
					intent = new Intent(getApplicationContext(), BasketballActivity.class);	
				}
				intent.putExtra("home", _teams[0]);																//adding additional items to intent to send to next activity (passing data)
				intent.putExtra("away", _teams[1]);
				startActivity(intent);																			//let's go
			}
		});
		confirmDialog.setNegativeButton("No", new DialogInterface.OnClickListener(){							//the negative no button		
			@Override
			public void onClick(DialogInterface arg0, int arg1) {							
				tv.setBackgroundColor(getResources().getColor(R.color.white));									//undo the selection of the second (away) team
				tv.setTextColor(getResources().getColor(R.color.black));
				_teams[1] = null;
			}
		});
		confirmDialog.show();																					//make it happen
	}
	
	//creating the new text view and returning it
	private TextView newTextView(String teamName){
		final TextView textView = new TextView(this);														//these are all the stuff that you can do statically in xml
		textView.setText(teamName);																			//here, we're dynamically programming them in Java
		textView.setPadding(5,5,5,5);
		textView.setTextSize(24);
		return textView;
	}

	//deleting a team from the linear layout we called "listOfTeams"
	public void deleteATeam(View view){
		if(_listOfTeams.getChildCount() > 0){																		//as long as we have items to delete, then we can use this
			_teamSelectTitle.setText(getResources().getString(R.string.delete_team_title));
			_setDelete = true;											
		}
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
		saveTeams();
		finish();
	}
}
