package com.seniordesign.ultimatescorecard.options;

import com.seniordesign.ultimatescorecard.R;
import com.seniordesign.ultimatescorecard.view.StaticFinalVars;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

public class GraphicsManagementFragment extends Fragment{
	private SharedPreferences _prefs;
	private String _selectedColor;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = (View) inflater.inflate(R.layout.fragment_graphics_mgmt, container, false);
		_prefs = getActivity().getSharedPreferences("GameClock", Context.MODE_PRIVATE);
		_selectedColor = _prefs.getString(StaticFinalVars.COLOR_PREFS, "teal");
        return view;
	}	
	
	@Override
	public void onStart() {
		getView().findViewById(R.id.buttonRed).setOnClickListener(selectColorListener);
		getView().findViewById(R.id.buttonOrange).setOnClickListener(selectColorListener);
		getView().findViewById(R.id.buttonYellow).setOnClickListener(selectColorListener);
		getView().findViewById(R.id.buttonGreen).setOnClickListener(selectColorListener);
		getView().findViewById(R.id.buttonTeal).setOnClickListener(selectColorListener);
		getView().findViewById(R.id.buttonBlue).setOnClickListener(selectColorListener);
		getView().findViewById(R.id.buttonIndigo).setOnClickListener(selectColorListener);
		getView().findViewById(R.id.buttonViolet).setOnClickListener(selectColorListener);
		getView().findViewById(R.id.buttonSave).setOnClickListener(saveColorListener);
		super.onStart();		
	}
	
	@Override
	public void onResume() {
		super.onResume();
		refresh();
	}
	
	@SuppressLint("DefaultLocale")
	private OnClickListener selectColorListener = new OnClickListener(){
		@Override
		public void onClick(View view) {
			_selectedColor = ((Button)view).getText().toString().toLowerCase();
			refresh();
		}			
	};

	private OnClickListener saveColorListener = new OnClickListener(){
		@Override
		public void onClick(View view) {
			_prefs.edit().putString(StaticFinalVars.COLOR_PREFS, _selectedColor).commit();
		}			
	};
	
	private void refresh(){
		if(_selectedColor.equals("red")){
			getView().setBackgroundResource(R.drawable.ace_red);
		}
		else if (_selectedColor.equals("orange")){
			getView().setBackgroundResource(R.drawable.ace_orange);
		}
		else if (_selectedColor.equals("yellow")){
			getView().setBackgroundResource(R.drawable.ace_yellow);
		}
		else if (_selectedColor.equals("green")){
			getView().setBackgroundResource(R.drawable.ace_green);
		}
		else if (_selectedColor.equals("teal")){
			getView().setBackgroundResource(R.drawable.ace_teal);
		}
		else if (_selectedColor.equals("blue")){
			getView().setBackgroundResource(R.drawable.ace_blue);
		}
		else if (_selectedColor.equals("indigo")){
			getView().setBackgroundResource(R.drawable.ace_indigo);
		}
		else if (_selectedColor.equals("violet")){
			getView().setBackgroundResource(R.drawable.ace_violet);
		}
		else {
			getView().setBackgroundResource(R.drawable.ace_teal);
		}
	}
	
	
}

