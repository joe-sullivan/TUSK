package com.seniordesign.ultimatescorecard.data;

import java.io.Serializable;

public class GameTime implements Serializable{
	private static final long serialVersionUID = -2473466007467908254L;
	protected boolean _gameStarted = false;
	protected boolean _possession = false;				//true=home, false=away
	
	public boolean isGameStarted(){
		return _gameStarted;
	}
	
	public void gameStarted(){
		_gameStarted = true;
	}
	
	public void gameStopped(){
		_gameStarted = false;
	}
	
	public void setPossession(boolean initial){
		_possession = initial;
	}
	
	public void changePossession(){
		_possession = !_possession;
	}
	
	public boolean getPossession(){
		return _possession;
	}
}
