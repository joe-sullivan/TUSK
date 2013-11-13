package com.seniordesign.ultimatescorecard;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ChooseTeamActivity extends Activity{
	LinearLayout listOfTeams;
	Button deleteButton;
	private SharedPreferences teamsEntered;
	private Editor prefEditor;
	private boolean selectTeam = false;			//false = select Home team, true = select Away team
	private boolean setDelete = false;
	private String[] teams = new String[2];
	private TextView teamSelectTitle;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_choose_team);
		teamsEntered = getSharedPreferences("basketballTeamList", MODE_PRIVATE);
		prefEditor = teamsEntered.edit();
		teamSelectTitle = (TextView) findViewById(R.id.team_selection_title);
		listOfTeams = (LinearLayout) findViewById (R.id.teamListLayout);
		deleteButton = (Button) findViewById (R.id.deleteTeamButton);
		if(teamsEntered.getString("basketballTeamList", null) != null){
			loadTeams();
		}
	}	
	
	@Override
	protected void onRestart() {
		super.onRestart();
		selectTeam = false;
		teamSelectTitle.setText(getResources().getString(R.string.home_team_select_title));
		teams[0] = null;
		teams[1] = null;
		deleteButton.setEnabled(true);
		for(int i=0; i < listOfTeams.getChildCount(); i++){
			TextView newItem = ((TextView)listOfTeams.getChildAt(i));
			newItem.setBackgroundColor(getResources().getColor(R.color.white));
			newItem.setTextColor(getResources().getColor(R.color.black));
		}
	}
	
	private void loadTeams(){
		String[] names = teamsEntered.getString("basketballTeamList", null).split(",");
		for(int i=0; i<names.length; i++){
			this.addNewTeam(names[i]);
		}
	}

	@SuppressLint("CommitPrefEdits")
	private void saveTeams(){
		StringBuilder sb = new StringBuilder();
		for(int i=0; i < listOfTeams.getChildCount(); i++){
			sb.append(((TextView)listOfTeams.getChildAt(i)).getText().toString()+",");
		}
		if(sb.toString().length() > 0){
			prefEditor.putString("basketballTeamList", sb.toString());
			prefEditor.commit();
		}
		else{
			prefEditor.clear();
			prefEditor.commit();
		}
	}
	
	public void addViews(View v){
		Builder teamCreate = new Builder(this);
		teamCreate.setTitle("Team Creation");
		teamCreate.setMessage("Please enter team name.");
		
		final EditText input = new EditText(this);
		teamCreate.setView(input);
		
		teamCreate.setPositiveButton("OK", new DialogInterface.OnClickListener(){
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				if(input.getText().toString().length() > 0){
					addNewTeam(input.getText().toString());
				}
			}
		});
		
		teamCreate.setNegativeButton("Cancel", new DialogInterface.OnClickListener(){
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				//do nothing, just exit dialog box
			}
		});
		teamCreate.show();
	}
		
	private void addNewTeam(String teamName){
		final TextView textView = newTextView(teamName);
		textView.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View view) {
				textView.setBackgroundColor(getResources().getColor(R.color.black));
				textView.setTextColor(getResources().getColor(R.color.white));
				if(setDelete){
					listOfTeams.removeView(textView);
					setDelete = false;
					teamSelectTitle.setText(getResources().getString(R.string.home_team_select_title));
					
				}
				else if(!selectTeam){
					teams[0] = textView.getText().toString();
					teamSelectTitle.setText(getResources().getString(R.string.away_team_select_title));
					selectTeam = true;
					deleteButton.setEnabled(false);
				}
				else{
					if(teams[0] != textView.getText().toString()){
						teams[1] = textView.getText().toString();
						confirmTeams(textView);
					}
					else{
						textView.setBackgroundColor(getResources().getColor(R.color.white));
						textView.setTextColor(getResources().getColor(R.color.black));
						teamSelectTitle.setText(getResources().getString(R.string.home_team_select_title));
						teams[0] = null;
						selectTeam = false;
						deleteButton.setEnabled(true);
					}
				}
			}
		});
		listOfTeams.addView(textView);
	} 
	
	public void confirmTeams(final TextView tv){
		Builder confirmDialog = new Builder(this);
		confirmDialog.setTitle("Team Selection Confirmation");
		confirmDialog.setMessage("Keeping scores for "+ teams[1] + " vs. " + teams[0] + "?");
				
		confirmDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener(){
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				Intent intent = new Intent(getApplicationContext(), BasketballActivity.class);	//create new intent
				intent.putExtra("Home", teams[0]);
				intent.putExtra("Away", teams[1]);
				startActivity(intent);	
			}
		});
		
		confirmDialog.setNegativeButton("No", new DialogInterface.OnClickListener(){
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				tv.setBackgroundColor(getResources().getColor(R.color.white));
				tv.setTextColor(getResources().getColor(R.color.black));
				teams[1] = null;
			}
		});
		confirmDialog.show();
	}
	
	private TextView newTextView(String teamName){
		final TextView textView = new TextView(this);
		textView.setText(teamName);
		textView.setPadding(5,5,5,5);
		textView.setTextSize(24);
		return textView;
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode == KeyEvent.KEYCODE_BACK){
			Builder alert = new Builder(this);
			alert.setTitle("Return to Main Menu");
			alert.setMessage("Are you sure?");
			
			alert.setPositiveButton("Yes", new DialogInterface.OnClickListener(){
				@Override
				public void onClick(DialogInterface dialog, int which) {
					onBackPressed();
					saveTeams();
					finish();
				}
			});
			alert.setNegativeButton("No", new DialogInterface.OnClickListener(){
				@Override
				public void onClick(DialogInterface dialog, int which) {
					//do nothing, just exit dialog box			
				}
			});
			
			alert.show();
			return true;
		}
		else{
			return super.onKeyDown(keyCode, event);
		}
	}

	public void deleteATeam(View view){
		if(listOfTeams.getChildCount() > 0){
			teamSelectTitle.setText(getResources().getString(R.string.delete_team_title));
			setDelete = true;
		}
	}
	
	@Override
	public void onBackPressed() {
		super.onBackPressed();
	}
}
