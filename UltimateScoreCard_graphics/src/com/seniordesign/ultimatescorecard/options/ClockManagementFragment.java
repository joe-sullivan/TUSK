package com.seniordesign.ultimatescorecard.options;

import com.seniordesign.ultimatescorecard.R;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;

public class ClockManagementFragment extends Fragment{
	private Button _sportButton, _numPerButton, _perLenButton, _otLenButton, _saveButton;
	private SharedPreferences _prefs;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = (View) inflater.inflate(R.layout.fragment_clock_mgmt, container, false);
        return view;
	}
	
	@Override
	public void onStart() {
		_prefs = getActivity().getSharedPreferences("GameClock", Context.MODE_PRIVATE);
		
		_sportButton = (Button)getView().findViewById(R.id.choose_sport_button);
		_sportButton.setOnClickListener(selectSportListener);
		_numPerButton = (Button)getView().findViewById(R.id.number_period_button);
		_numPerButton.setOnClickListener(selectNumPeriodListener);
		_perLenButton = (Button)getView().findViewById(R.id.period_length_button);
		_perLenButton.setOnClickListener(selectPeriodLengthListener);	
		_otLenButton = (Button)getView().findViewById(R.id.ot_length_button);
		_otLenButton.setOnClickListener(selectOTLengthListener);	
		_saveButton = (Button)getView().findViewById(R.id.save_button);
		_saveButton.setOnClickListener(saveButtonListener);
		super.onStart();		
	}
	
	private OnClickListener selectSportListener = new OnClickListener(){
		@Override
		public void onClick(View view) {
			Builder builder = new Builder(ClockManagementFragment.this.getActivity());
			builder.setTitle("Select Sport");
			final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String> (ClockManagementFragment.this.getActivity(), android.R.layout.select_dialog_singlechoice);
			arrayAdapter.add("Basketball");
			arrayAdapter.add("Football");
			arrayAdapter.add("Hockey");
			arrayAdapter.add("Soccer");
			builder.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();
				}
			});			
			builder.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {					
					buttonEnabler(true, false, false);
					resetButtonText(true, true, arrayAdapter.getItem(which));
					_sportButton.setText(arrayAdapter.getItem(which));
				}
			});
			builder.show();
		}		
	};
	
	private OnClickListener selectNumPeriodListener = new OnClickListener(){
		@Override
		public void onClick(View view) {
			Builder builder = new Builder(ClockManagementFragment.this.getActivity());
			builder.setTitle("Number of Periods:");
			final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String> (ClockManagementFragment.this.getActivity(), android.R.layout.select_dialog_singlechoice);
			arrayAdapter.add("1 Period");
			arrayAdapter.add("2 Halves");
			if(_sportButton.getText().equals("Basketball") || _sportButton.getText().equals("Hockey") || _sportButton.getText().equals("Football")){
				arrayAdapter.add("3 Periods");
			}
			if(_sportButton.getText().equals("Basketball") || _sportButton.getText().equals("Football")){
				arrayAdapter.add("4 Quarters");
			}
			builder.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();
				}
			});			
			builder.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {					
					buttonEnabler(true, true, false);
					resetButtonText(false, true, _sportButton.getText().toString());
					_numPerButton.setText(arrayAdapter.getItem(which));
				}
			});
			builder.show();
		}		
	};
	
	private OnClickListener selectPeriodLengthListener = new OnClickListener(){
		@Override
		public void onClick(View view) {
			Builder builder = new Builder(ClockManagementFragment.this.getActivity());
			builder.setTitle("Period Length: (in minutes)");
			final EditText input = new EditText(ClockManagementFragment.this.getActivity());
			
			if(_sportButton.getText().equals("Basketball")){
				input.setText((_prefs.getString("perLenBasketball", getActivity().getResources().getString(R.string.period_length))).replace(" minutes" , ""));
			}
			else if (_sportButton.getText().equals("Football")){
				input.setText((_prefs.getString("perLenFootball", getActivity().getResources().getString(R.string.period_length))).replace(" minutes" , ""));
			}
			else if (_sportButton.getText().equals("Hockey")){
				input.setText((_prefs.getString("perLenHockey", getActivity().getResources().getString(R.string.period_length))).replace(" minutes" , ""));		
			}
			else if (_sportButton.getText().equals("Soccer")){
				input.setText((_prefs.getString("perLenSoccer", getActivity().getResources().getString(R.string.period_length))).replace(" minutes" , ""));
			}
			
			builder.setView(input);
			
			builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					if(!isInteger(input.getText().toString())){
						popErrorDialog();
					}
					else if(Integer.parseInt(input.getText().toString()) <= 120 && Integer.parseInt(input.getText().toString()) > 0){
						_perLenButton.setText(input.getText().toString()+" minutes");
						dialog.dismiss();
						buttonEnabler(true, true, true);
					}
					
					else{
						popErrorDialog();
					}
				}
			});
			builder.show();
		}		
	};
	
	private OnClickListener selectOTLengthListener = new OnClickListener(){
		@Override
		public void onClick(View view) {
			Builder builder = new Builder(ClockManagementFragment.this.getActivity());
			builder.setTitle("Overtime Length: (in minutes)");
			final EditText input = new EditText(ClockManagementFragment.this.getActivity());
			
			if(_sportButton.getText().equals("Basketball")){
				input.setText((_prefs.getString("OTLenBasketball", getActivity().getResources().getString(R.string.ot_length))).replace(" minutes" , ""));
			}
			else if (_sportButton.getText().equals("Football")){
				input.setText((_prefs.getString("OTLenFootball", getActivity().getResources().getString(R.string.ot_length))).replace(" minutes" , ""));
			}
			else if (_sportButton.getText().equals("Hockey")){
				input.setText((_prefs.getString("OTLenHockey", getActivity().getResources().getString(R.string.ot_length))).replace(" minutes" , ""));		
			}
			else if (_sportButton.getText().equals("Soccer")){
				input.setText((_prefs.getString("OTLenSoccer", getActivity().getResources().getString(R.string.ot_length))).replace(" minutes" , ""));
			}
			
			builder.setView(input);
			
			builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					if(!isInteger(input.getText().toString())){
						popErrorDialog();
					}
					else if(Integer.parseInt(input.getText().toString()) <= 120 && Integer.parseInt(input.getText().toString()) > 0){
						_otLenButton.setText(input.getText().toString()+" minutes");
						dialog.dismiss();
						buttonEnabler(true, true, true);
					}
					
					else{
						popErrorDialog();
					}
				}
			});
			builder.show();
		}		
	};
	
	private OnClickListener saveButtonListener = new OnClickListener(){
		@Override
		public void onClick(View view) {
			Builder builder = new Builder(ClockManagementFragment.this.getActivity());
			builder.setTitle("Confirmation");	
			builder.setMessage("Are you sure you want to save these settings?");
			builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					if(!_sportButton.getText().toString().equals(getActivity().getResources().getString(R.string.choose_sport))){
						_prefs.edit().putString("numPer"+_sportButton.getText().toString(), _numPerButton.getText().toString()).commit();
						_prefs.edit().putString("perLen"+_sportButton.getText().toString(), _perLenButton.getText().toString()).commit();
						_prefs.edit().putString("otLen"+_sportButton.getText().toString(), _otLenButton.getText().toString()).commit();
						getActivity().onBackPressed();
					}
					else{
						dialog.dismiss();
					}
				}
			});
			builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();
				}
			});
			builder.show();
		}		
	};
	
	private void buttonEnabler(boolean numPer, boolean perLen, boolean save){
		 _numPerButton.setEnabled(numPer);
		 _perLenButton.setEnabled(perLen);
		 _otLenButton.setEnabled(perLen);
		 _saveButton.setEnabled(save);
	}
	
	private void resetButtonText(boolean numPer, boolean perLen, String sport){
		if(numPer){
			if(sport.equals("Basketball")){
				_numPerButton.setText(_prefs.getString("numPerBasketball", getActivity().getResources().getString(R.string.number_period)));
			}
			else if (sport.equals("Football")){
				_numPerButton.setText(_prefs.getString("numPerFootball", getActivity().getResources().getString(R.string.number_period)));
			}
			else if (sport.equals("Hockey")){
				_numPerButton.setText(_prefs.getString("numPerHockey", getActivity().getResources().getString(R.string.number_period)));		
			}
			else if (sport.equals("Soccer")){
				_numPerButton.setText(_prefs.getString("numPerSoccer", getActivity().getResources().getString(R.string.number_period)));
			}
			else{
				_numPerButton.setText(getActivity().getResources().getString(R.string.number_period));
			}
		}
		if(perLen){
			if(sport.equals("Basketball")){
				_perLenButton.setText(_prefs.getString("perLenBasketball", getActivity().getResources().getString(R.string.period_length)));
				_otLenButton.setText(_prefs.getString("otLenBasketball", getActivity().getResources().getString(R.string.ot_length)));
			}
			else if (sport.equals("Football")){
				_perLenButton.setText(_prefs.getString("perLenFootball", getActivity().getResources().getString(R.string.period_length)));
				_otLenButton.setText(_prefs.getString("otLenFootball", getActivity().getResources().getString(R.string.ot_length)));
			}
			else if (sport.equals("Hockey")){
				_perLenButton.setText(_prefs.getString("perLenHockey", getActivity().getResources().getString(R.string.period_length)));
				_otLenButton.setText(_prefs.getString("otLenHockey", getActivity().getResources().getString(R.string.ot_length)));
			}
			else if (sport.equals("Soccer")){
				_perLenButton.setText(_prefs.getString("perLenSoccer", getActivity().getResources().getString(R.string.period_length)));
				_otLenButton.setText(_prefs.getString("otLenSoccer", getActivity().getResources().getString(R.string.ot_length)));
			}
			else{
				_perLenButton.setText(getActivity().getResources().getString(R.string.period_length));
				_otLenButton.setText(getActivity().getResources().getString(R.string.ot_length));
			}
		}
	}
	
	private void popErrorDialog(){
		Builder builder = new Builder(getActivity());
		builder.setTitle("Invalid Input");
		builder.setMessage("Input must be an integer between 1 and 120 minutes");
		builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		builder.show();
	}
	
	public static boolean isInteger(String s) {
	    try { 
	        Integer.parseInt(s); 
	    } catch(NumberFormatException e) { 
	        return false; 
	    }
	    // only got here if we didn't return false
	    return true;
	}
}
