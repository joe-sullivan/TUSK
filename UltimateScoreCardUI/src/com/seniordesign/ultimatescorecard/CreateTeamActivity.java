package com.seniordesign.ultimatescorecard;

import com.seniordesign.ultimatescorecard.view.StaticFinalVars;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class CreateTeamActivity extends Activity{
	private LinearLayout _playerList;
	private View _viewSelected;
	private String _oldTeamName = "";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_team);
		
		_playerList = (LinearLayout)findViewById(R.id.playerList);		
		findViewById(R.id.addPlayer).setOnClickListener(addPlayerListener);
		findViewById(R.id.editPlayer).setOnClickListener(editPlayerListener);
		findViewById(R.id.deletePlayer).setOnClickListener(deletePlayerListener);
		findViewById(R.id.confirmTeam).setOnClickListener(confirmTeamListener);
		
		String teamEditor = getIntent().getStringExtra(StaticFinalVars.CREATE_EDIT);
		if(!teamEditor.equals("")){
			((EditText)findViewById(R.id.teamNameEditText)).setText(teamEditor);
			_oldTeamName = teamEditor;
		}
	}

	private OnClickListener addPlayerListener = new OnClickListener(){
		@Override
		public void onClick(View view) {
			addingPlayers();			
		}
	};
	
	private OnClickListener editPlayerListener = new OnClickListener(){
		@Override
		public void onClick(View view) {
			if(_viewSelected != null){
				editingPlayer(_viewSelected);
			}
		}
	};
	
	private OnClickListener deletePlayerListener = new OnClickListener(){
		@Override
		public void onClick(View view) {
			if(_viewSelected != null){
				_playerList.removeView(_viewSelected);
				_viewSelected = null;
			}
		}
	};
	
	private OnClickListener confirmTeamListener = new OnClickListener(){
		@Override
		public void onClick(View view) {
			String teamName = ((EditText)findViewById(R.id.teamNameEditText)).getText().toString();
			String teamAbbr = ((EditText)findViewById(R.id.teamAbbrEditText)).getText().toString();
			String coachName = ((EditText)findViewById(R.id.coachNameEditText)).getText().toString();
			for(int i=0; i<_playerList.getChildCount(); i++){
				String playerName = ((TextView)((LinearLayout)_playerList.getChildAt(i)).getChildAt(1)).getText().toString();
			}
			
			Intent intent = new Intent();
			intent.putExtra(StaticFinalVars.TEAM_NAME, teamName);
			intent.putExtra(StaticFinalVars.OLD_TEAM_NAME, _oldTeamName);
			setResult(Activity.RESULT_OK, intent);
			finish();	
		}
	};
	
	private OnClickListener selectPlayerListener = new OnClickListener(){
		@Override
		public void onClick(View view) {
			if(_viewSelected != view){
				_viewSelected = view;
				_viewSelected.setBackgroundColor(getResources().getColor(R.color.robin_egg_blue));
			}
			else{				
				_viewSelected.setBackgroundColor(getResources().getColor(R.color.white));
				_viewSelected = null;
			}
		}
	};
	
	private void addingPlayers(){
		Builder confirmDialog = new Builder(this);																
		confirmDialog.setTitle("Create A Player");
		
		LayoutInflater inflater = this.getLayoutInflater();
		final View layout = inflater.inflate(R.layout.dialog_player, null);
		confirmDialog.setView(layout);
		
		confirmDialog.setPositiveButton("Save", new DialogInterface.OnClickListener(){	
			@Override
			public void onClick(DialogInterface dialog, int arg1) {
				String name = ((EditText)layout.findViewById(R.id.playerNameEditText)).getText().toString();
				String number = ((EditText)layout.findViewById(R.id.playerNumberEditText)).getText().toString();
				if(!name.equals("") && !number.equals("")){
					_playerList.addView(newPlayerItem(name, number));
				}
				dialog.dismiss();
			}
		});
		confirmDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener(){		
			@Override
			public void onClick(DialogInterface dialog, int arg1) {							
				dialog.dismiss();
			}
		});
		confirmDialog.show();	
	}
	
	private void editingPlayer(final View viewSelected){
		Builder confirmDialog = new Builder(this);																
		confirmDialog.setTitle("Edit A Player");
		
		LayoutInflater inflater = this.getLayoutInflater();
		final View layout = inflater.inflate(R.layout.dialog_player, null);
		confirmDialog.setView(layout);
		
		((EditText)layout.findViewById(R.id.playerNameEditText)).setText(((TextView)((LinearLayout)viewSelected).getChildAt(1)).getText().toString());
		((EditText)layout.findViewById(R.id.playerNumberEditText)).setText(((TextView)((LinearLayout)viewSelected).getChildAt(0)).getText().toString());
		
		confirmDialog.setPositiveButton("Save", new DialogInterface.OnClickListener(){	
			@Override
			public void onClick(DialogInterface dialog, int arg1) {
				String name = ((EditText)layout.findViewById(R.id.playerNameEditText)).getText().toString();
				String number = ((EditText)layout.findViewById(R.id.playerNumberEditText)).getText().toString();
				if(!name.equals("") && !number.equals("")){
					((TextView)((LinearLayout)viewSelected).getChildAt(1)).setText(name);
					((TextView)((LinearLayout)viewSelected).getChildAt(0)).setText(number);
				}
				dialog.dismiss();
				_viewSelected.setBackgroundColor(getResources().getColor(R.color.white));
				_viewSelected = null;	
			}
		});
		confirmDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener(){		
			@Override
			public void onClick(DialogInterface dialog, int arg1) {		
				dialog.dismiss();
			}
		});
		confirmDialog.show();	
	}
	
	//creating the new text view combination and returning it
	private LinearLayout newPlayerItem(String name, String number){
		LinearLayout layout = new LinearLayout(this);
		
		TextView textView = new TextView(this);		
		textView.setText(number);
		textView.setTextSize(24);
		textView.setPadding(5, 5, 5, 5);
		textView.setGravity(5);
		
		TextView textView2 = new TextView(this);		
		textView2.setText(name);
		textView2.setTextSize(24);
		textView2.setPadding(30, 5, 5, 5);
		textView2.setGravity(1);
		
		layout.addView(textView);
		layout.addView(textView2);
		
		layout.setOnClickListener(selectPlayerListener);
		return layout;
	}
}
