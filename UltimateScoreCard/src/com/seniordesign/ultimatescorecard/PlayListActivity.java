package com.seniordesign.ultimatescorecard;

import java.util.ArrayList;

import com.seniordesign.ultimatescorecard.data.GameLog;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

public class PlayListActivity extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_play_by_play);
		
		GameLog log = (GameLog) getIntent().getSerializableExtra("gameLog");
		ArrayList<String> listOfPlay = 	log.getGameLog();
		LinearLayout list = (LinearLayout)findViewById(R.id.listofPlays);
		for(String s: listOfPlay){
			TextView tv = new TextView(getApplicationContext());
			tv.setTextColor(Color.BLACK);
			tv.setText(s);
			list.addView(tv);
		}
	}
}
