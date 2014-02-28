package com.seniordesign.ultimatescorecard.substitution;

import com.seniordesign.ultimatescorecard.R;
import com.seniordesign.ultimatescorecard.data.GameInfo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class SoccerActivePageFragment extends Fragment{
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = (View) inflater.inflate(R.layout.fragment_active_soccer, container, false);
        return view;
	}	
	
	@Override
	public void onResume() {
		super.onResume();
		refresh();
	}

	private void setPlayer(GameInfo gameInfo, boolean status){
		TextView view1 = ((TextView) getView().findViewById(R.id.active1Name));
		view1.setText(gameInfo.getPlayer(0,status).getpname());
		view1.setOnClickListener(swapPageListener);
		
		TextView view2 = ((TextView) getView().findViewById(R.id.active2Name));
		view2.setText(gameInfo.getPlayer(1,status).getpname());
		view2.setOnClickListener(swapPageListener);
		
		TextView view3 = ((TextView) getView().findViewById(R.id.active3Name));
		view3.setText(gameInfo.getPlayer(2,status).getpname());
		view3.setOnClickListener(swapPageListener);
		
		TextView view4 = ((TextView) getView().findViewById(R.id.active4Name));
		view4.setText(gameInfo.getPlayer(3,status).getpname());
		view4.setOnClickListener(swapPageListener);
		
		TextView view5 = ((TextView) getView().findViewById(R.id.active5Name));
		view5.setText(gameInfo.getPlayer(4,status).getpname());
		view5.setOnClickListener(swapPageListener);
	
		TextView view6 = ((TextView) getView().findViewById(R.id.active6Name));
		view6.setText(gameInfo.getPlayer(5,status).getpname());
		view6.setOnClickListener(swapPageListener);
		
		TextView view7 = ((TextView) getView().findViewById(R.id.active7Name));
		view7.setText(gameInfo.getPlayer(6,status).getpname());
		view7.setOnClickListener(swapPageListener);
		
		TextView view8 = ((TextView) getView().findViewById(R.id.active8Name));
		view8.setText(gameInfo.getPlayer(7,status).getpname());
		view8.setOnClickListener(swapPageListener);
		
		TextView view9 = ((TextView) getView().findViewById(R.id.active9Name));
		view9.setText(gameInfo.getPlayer(8,status).getpname());
		view9.setOnClickListener(swapPageListener);
		
		TextView view10 = ((TextView) getView().findViewById(R.id.active10Name));
		view10.setText(gameInfo.getPlayer(9,status).getpname());
		view10.setOnClickListener(swapPageListener);
		
		TextView view11 = ((TextView) getView().findViewById(R.id.active11Name));
		view11.setText(gameInfo.getPlayer(10,status).getpname());
		view11.setOnClickListener(swapPageListener);
	}
	
	public void refresh(){
		setPlayer(((SoccerSubstitutionActivity)getActivity()).getTeamInfo(), ((SoccerSubstitutionActivity)getActivity()).getHomeTeam());
	}
	
	private OnClickListener swapPageListener = new OnClickListener(){
		@Override
		public void onClick(View v) {
			((View)v.getParent()).setBackgroundColor(getResources().getColor(R.color.gray));
			((SoccerSubstitutionActivity)getActivity()).switchPages();
			((SoccerSubstitutionActivity)getActivity()).setPlayerOut(((TextView)v).getText().toString());
		}
	};
	
	
}
