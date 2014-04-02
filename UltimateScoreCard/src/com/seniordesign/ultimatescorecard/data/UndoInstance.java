package com.seniordesign.ultimatescorecard.data;

import java.util.ArrayList;

import android.widget.ImageView;

import com.seniordesign.ultimatescorecard.sqlite.helper.PlayByPlay;
import com.seniordesign.ultimatescorecard.sqlite.helper.ShotChartCoords;

public class UndoInstance {
	
	private long _gid; 
	//playbyplay
	private PlayByPlay _pbp; 
	//stats
	private ArrayList<StatData> _pstats, _tstats; 
	//shot chart
	private ImageView _iv; 
	private ShotChartCoords _shot; 
	private boolean _home; 
	
	public UndoInstance(){
		_pstats = new ArrayList<StatData>();
		_tstats = new ArrayList<StatData>();
	}
	
	public void setgid(long g_id){
		_gid = g_id;
	}
	
	public void setpbp(PlayByPlay pbp){
		_pbp = pbp;
	}
	
	public void addpstats(ArrayList<StatData> pstats){
		_pstats.addAll(pstats);
	}
	
	public void addtstats(ArrayList<StatData> tstats){
		_tstats.addAll(tstats);
	}
	
	public void setiv(ImageView iv){
		_iv = iv;
	}
	
	public void setshot(ShotChartCoords shot){
		_shot = shot;
	}
	
	public void sethome(boolean home){
		_home = home;
	}
	
	//getters
	
	public long getgid(){
		return _gid;
	}
	
	public PlayByPlay getpbp(){
		return _pbp;
	}
	
	public ArrayList<StatData> getpstats(){
		return _pstats;
	}
	
	public ArrayList<StatData> gettstats(){
		return _tstats;
	}
	
	public ImageView getiv(){
		return _iv;
	}
	
	public ShotChartCoords getshot(){
		return _shot;
	}
	
	public boolean gethome(){
		return _home;
	}
	
	public void clearpstats(){
		_pstats = new ArrayList<StatData>();
	}
	
	public void cleartstats(){
		_tstats = new ArrayList<StatData>();
	}
}
