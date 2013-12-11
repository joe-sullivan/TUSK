package com.seniordesign.ultimatescorecard;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class CreateTeamActivity extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_team);
		
		findViewById(R.id.addPlayer).setOnClickListener(addPlayerListener);
		findViewById(R.id.editPlayer).setOnClickListener(editPlayerListener);
		findViewById(R.id.deletePlayer).setOnClickListener(deletePlayerListener);
		findViewById(R.id.confirmTeam).setOnClickListener(confirmTeamListener);
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
			
		}
	};
	
	private OnClickListener deletePlayerListener = new OnClickListener(){
		@Override
		public void onClick(View view) {
			
		}
	};
	
	private OnClickListener confirmTeamListener = new OnClickListener(){
		@Override
		public void onClick(View view) {
			
		}
	};
	
	private void addingPlayers(){
		Builder confirmDialog = new Builder(this);																
		confirmDialog.setTitle("Create A Player");
				
		confirmDialog.setPositiveButton("Save", new DialogInterface.OnClickListener(){	
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				
			}
		});
		confirmDialog.setNegativeButton("Delete", new DialogInterface.OnClickListener(){		
			@Override
			public void onClick(DialogInterface arg0, int arg1) {							
				
			}
		});
		confirmDialog.show();	
	}
	
	private void editingPlayers(){
		Builder confirmDialog = new Builder(this);																
		confirmDialog.setTitle("Edit A Player");
				
		confirmDialog.setPositiveButton("Save", new DialogInterface.OnClickListener(){	
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				
			}
		});
		confirmDialog.setNegativeButton("Delete", new DialogInterface.OnClickListener(){		
			@Override
			public void onClick(DialogInterface arg0, int arg1) {							
				
			}
		});
		confirmDialog.show();	
	}
}
