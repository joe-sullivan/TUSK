package com.seniordesign.ultimatescorecard.substitution;

import com.seniordesign.ultimatescorecard.R;
import com.seniordesign.ultimatescorecard.data.GameTime;
import com.seniordesign.ultimatescorecard.data.Team;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class ActivePageFragment extends Fragment{
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = (View) inflater.inflate(R.layout.fragment_active, container, false);
        return view;
	}	
	
	@Override
	public void onResume() {
		super.onResume();
		refresh();
	}

	private void setPlayer(GameTime gti, boolean status){
		Team team = null;
		if(status){
			team = gti.getTheHomeTeam();
		}
		else{
			team = gti.getTheAwayTeam();
		}
		TextView view1 = ((TextView) getView().findViewById(R.id.active1Name));
		view1.setText(team.getPlayer(0).getName());
		view1.setOnClickListener(swapPageListener);
		
		TextView view2 = ((TextView) getView().findViewById(R.id.active2Name));
		view2.setText(team.getPlayer(1).getName());
		view2.setOnClickListener(swapPageListener);
		
		TextView view3 = ((TextView) getView().findViewById(R.id.active3Name));
		view3.setText(team.getPlayer(2).getName());
		view3.setOnClickListener(swapPageListener);
		
		TextView view4 = ((TextView) getView().findViewById(R.id.active4Name));
		view4.setText(team.getPlayer(3).getName());
		view4.setOnClickListener(swapPageListener);
		
		TextView view5 = ((TextView) getView().findViewById(R.id.active5Name));
		view5.setText(team.getPlayer(4).getName());
		view5.setOnClickListener(swapPageListener);
	}
	
	public void refresh(){
		setPlayer(((SubstitutionActivity)getActivity()).getTeamInfo(), ((SubstitutionActivity)getActivity()).getHomeTeam());
	}
	
	private OnClickListener swapPageListener = new OnClickListener(){
		@Override
		public void onClick(View v) {
			((View)v.getParent()).setBackgroundColor(getResources().getColor(R.color.gray));
			((SubstitutionActivity)getActivity()).switchPages();
			((SubstitutionActivity)getActivity()).setPlayerOut(((TextView)v).getText().toString());
		}
	};
	
	
}
