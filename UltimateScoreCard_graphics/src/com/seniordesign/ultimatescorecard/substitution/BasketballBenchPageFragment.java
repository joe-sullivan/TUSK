package com.seniordesign.ultimatescorecard.substitution;

import com.seniordesign.ultimatescorecard.R;
import com.seniordesign.ultimatescorecard.data.GameInfo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

public class BasketballBenchPageFragment extends Fragment{
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = (View) inflater.inflate(R.layout.fragment_bench_basketball, container, false);
        return view;
	}
	
	@Override
	public void onResume() {
		super.onResume();
		setPlayer(((BasketballSubstitutionActivity)getActivity()).getTeamInfo(), ((BasketballSubstitutionActivity)getActivity()).getHomeTeam());
	}

	private void setPlayer(GameInfo gameInfo, boolean status){
		//homeTeam
		if(status){
			if(!(gameInfo.getHomePlayers().size()<6)){
				TextView view1 = ((TextView) getView().findViewById(R.id.bench1Name));
				view1.setText(gameInfo.getPlayer(5,status).getpname());
				view1.setOnClickListener(swapPageListener);
				
				if(!(gameInfo.getHomePlayers().size()<7)){

					TextView view2 = ((TextView) getView().findViewById(R.id.bench2Name));
					view2.setText(gameInfo.getPlayer(6,status).getpname());
					view2.setOnClickListener(swapPageListener);
					
					if(!(gameInfo.getHomePlayers().size()<8)){
	
						TextView view3 = ((TextView) getView().findViewById(R.id.bench3Name));
						view3.setText(gameInfo.getPlayer(7,status).getpname());
						view3.setOnClickListener(swapPageListener);
						
						if(!(gameInfo.getHomePlayers().size()<9)){
		
							TextView view4 = ((TextView) getView().findViewById(R.id.bench4Name));
							view4.setText(gameInfo.getPlayer(8,status).getpname());
							view4.setOnClickListener(swapPageListener);
							
							if(!(gameInfo.getHomePlayers().size()<10)){
			
								TextView view5 = ((TextView) getView().findViewById(R.id.bench5Name));
								view5.setText(gameInfo.getPlayer(9,status).getpname());
								view5.setOnClickListener(swapPageListener);
								
								if(!(gameInfo.getHomePlayers().size()<11)){
									
									TextView view6 = ((TextView) getView().findViewById(R.id.bench6Name));
									view6.setText(gameInfo.getPlayer(10,status).getpname());
									view6.setOnClickListener(swapPageListener);
									
									if(!(gameInfo.getHomePlayers().size()<12)){
										
										TextView view7 = ((TextView) getView().findViewById(R.id.bench7Name));
										view7.setText(gameInfo.getPlayer(11,status).getpname());
										view7.setOnClickListener(swapPageListener);
										
										if(!(gameInfo.getHomePlayers().size()<13)){
											
											TextView view8 = ((TextView) getView().findViewById(R.id.bench8Name));
											view8.setText(gameInfo.getPlayer(12,status).getpname());
											view8.setOnClickListener(swapPageListener);
											
											if(!(gameInfo.getHomePlayers().size()<14)){
												
												TextView view9 = ((TextView) getView().findViewById(R.id.bench9Name));
												view9.setText(gameInfo.getPlayer(13,status).getpname());
												view9.setOnClickListener(swapPageListener);
												
												if(!(gameInfo.getHomePlayers().size()<15)){
													
													TextView view10 = ((TextView) getView().findViewById(R.id.bench10Name));
													view10.setText(gameInfo.getPlayer(14,status).getpname());
													view10.setOnClickListener(swapPageListener);
												}
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}
		//awayTeam
		if(!status){
			if(!(gameInfo.getAwayPlayers().size()<6)){
				TextView view1 = ((TextView) getView().findViewById(R.id.bench1Name));
				view1.setText(gameInfo.getPlayer(5,status).getpname());
				view1.setOnClickListener(swapPageListener);
				
				if(!(gameInfo.getAwayPlayers().size()<7)){

					TextView view2 = ((TextView) getView().findViewById(R.id.bench2Name));
					view2.setText(gameInfo.getPlayer(6,status).getpname());
					view2.setOnClickListener(swapPageListener);
					
					if(!(gameInfo.getAwayPlayers().size()<8)){
	
						TextView view3 = ((TextView) getView().findViewById(R.id.bench3Name));
						view3.setText(gameInfo.getPlayer(7,status).getpname());
						view3.setOnClickListener(swapPageListener);
						
						if(!(gameInfo.getAwayPlayers().size()<9)){
		
							TextView view4 = ((TextView) getView().findViewById(R.id.bench4Name));
							view4.setText(gameInfo.getPlayer(8,status).getpname());
							view4.setOnClickListener(swapPageListener);
							
							if(!(gameInfo.getAwayPlayers().size()<10)){
			
								TextView view5 = ((TextView) getView().findViewById(R.id.bench5Name));
								view5.setText(gameInfo.getPlayer(9,status).getpname());
								view5.setOnClickListener(swapPageListener);
								
								if(!(gameInfo.getAwayPlayers().size()<11)){
									
									TextView view6 = ((TextView) getView().findViewById(R.id.bench6Name));
									view6.setText(gameInfo.getPlayer(10,status).getpname());
									view6.setOnClickListener(swapPageListener);
									
									if(!(gameInfo.getAwayPlayers().size()<12)){
										
										TextView view7 = ((TextView) getView().findViewById(R.id.bench7Name));
										view7.setText(gameInfo.getPlayer(11,status).getpname());
										view7.setOnClickListener(swapPageListener);
										
										if(!(gameInfo.getAwayPlayers().size()<13)){
											
											TextView view8 = ((TextView) getView().findViewById(R.id.bench8Name));
											view8.setText(gameInfo.getPlayer(12,status).getpname());
											view8.setOnClickListener(swapPageListener);
											
											if(!(gameInfo.getAwayPlayers().size()<14)){
												
												TextView view9 = ((TextView) getView().findViewById(R.id.bench9Name));
												view9.setText(gameInfo.getPlayer(13,status).getpname());
												view9.setOnClickListener(swapPageListener);
												
												if(!(gameInfo.getAwayPlayers().size()<15)){
													
													TextView view10 = ((TextView) getView().findViewById(R.id.bench10Name));
													view10.setText(gameInfo.getPlayer(14,status).getpname());
													view10.setOnClickListener(swapPageListener);
												}
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}
		
	}
	
	public void refresh(){
		setPlayer(((BasketballSubstitutionActivity)getActivity()).getTeamInfo(), ((BasketballSubstitutionActivity)getActivity()).getHomeTeam());
	}
	
	private OnClickListener swapPageListener = new OnClickListener(){
		@Override
		public void onClick(View v) {
			((View)v.getParent()).setBackgroundColor(getResources().getColor(R.color.gray));
			((BasketballSubstitutionActivity)getActivity()).switchPages();
			((BasketballSubstitutionActivity)getActivity()).setPlayerIn(((TextView)v).getText().toString());
		}
	};
}
