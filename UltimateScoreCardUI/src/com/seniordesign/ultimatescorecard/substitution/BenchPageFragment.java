package com.seniordesign.ultimatescorecard.substitution;

import com.seniordesign.ultimatescorecard.R;
import com.seniordesign.ultimatescorecard.data.basketball.BasketballGameTime;
import com.seniordesign.ultimatescorecard.data.basketball.BasketballTeam;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

public class BenchPageFragment extends Fragment{
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = (View) inflater.inflate(R.layout.fragment_bench, container, false);
        return view;
	}
	
	@Override
	public void onResume() {
		super.onResume();
		setPlayer(((SubstitutionActivity)getActivity()).getTeamInfo(), ((SubstitutionActivity)getActivity()).getHomeTeam());
	}

	private void setPlayer(BasketballGameTime gti, boolean status){
		BasketballTeam team = null;
		if(status){
			team = gti.getTheHomeTeam();
		}
		else{
			team = gti.getTheAwayTeam();
		}
		TextView view1 = ((TextView) getView().findViewById(R.id.bench1Name));
		view1.setText(team.getPlayer(5).getName());
		view1.setOnClickListener(swapPageListener);
		
		TextView view2 = ((TextView) getView().findViewById(R.id.bench2Name));
		view2.setText(team.getPlayer(6).getName());
		view2.setOnClickListener(swapPageListener);
		
		TextView view3 = ((TextView) getView().findViewById(R.id.bench3Name));
		view3.setText(team.getPlayer(7).getName());
		view3.setOnClickListener(swapPageListener);
		
		TextView view4 = ((TextView) getView().findViewById(R.id.bench4Name));
		view4.setText(team.getPlayer(8).getName());
		view4.setOnClickListener(swapPageListener);
		
		TextView view5 = ((TextView) getView().findViewById(R.id.bench5Name));
		view5.setText(team.getPlayer(9).getName());
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
			((SubstitutionActivity)getActivity()).setPlayerIn(((TextView)v).getText().toString());
		}
	};
}
