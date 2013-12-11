package com.seniordesign.ultimatescorecard.clock;

import android.os.Handler;
import android.widget.TextView;

public class GameClock {
	private long _millisInFuture;
	private long _countDownInterval = 1000;
	private TextView _textView;
	private boolean _status;
	
	public GameClock(long millisInFuture, TextView textView){
		_millisInFuture = millisInFuture;
		_textView = textView;
		_textView.setText(displayTime(secToMin(millisInFuture/1000), remainSec(millisInFuture/1000)));
		_status = false;
		initialize();
	}

	public void start(){
		_status = true;
	}
	
	public void stop(){
		_status = false;
	}
	
	public long getCurrentTime(){
		return _millisInFuture;
	}
	
	public void initialize(){
		final Handler handler = new Handler();
		final Runnable counter = new Runnable(){
			@Override
			public void run() {
				long sec = _millisInFuture/1000;
				if(_status){
					if(_millisInFuture <= 0){
						_textView.setText("00:00");
						_status = false;
					}
					else{
						_textView.setText(displayTime(secToMin(sec), remainSec(sec)));
						_millisInFuture -= _countDownInterval;
						handler.postDelayed(this, _countDownInterval);
					}
				}
				else{
					handler.postDelayed(this, _countDownInterval);
				}
			}
		};
		handler.postDelayed(counter, _countDownInterval);
	}
	
	public boolean isStarted(){
		return _status;
	}
	
	public void restartTimer(long millisInFuture){
		_millisInFuture =  millisInFuture;
		_textView.setText(displayTime(secToMin(_millisInFuture/1000), remainSec(_millisInFuture/1000)));
		initialize();
	}
		
	private int secToMin(long seconds){
		return (int) (seconds/60);
	}
	private int remainSec(long seconds){
		return (int) (seconds%60);
	}
	private String displayTime(int minutes, int seconds){
		if(minutes < 10){
			if(seconds < 10){
				return ("0"+minutes+":0"+seconds);
			}
			else{
				return ("0"+minutes+":"+seconds);
			}
		}
		else{
			if(seconds < 10){
				return (minutes+":0"+seconds);
			}
			else{
				return (+minutes+":"+seconds);
			}
		}
	}
}