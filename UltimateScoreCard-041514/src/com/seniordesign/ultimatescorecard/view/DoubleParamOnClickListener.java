package com.seniordesign.ultimatescorecard.view;

import android.view.View;
import android.view.View.OnClickListener;

public class DoubleParamOnClickListener implements OnClickListener{
	private String _string;
	private int _value;
	public DoubleParamOnClickListener(int value, String str){
		_string =str;
		_value = value;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
	}
	
	public int getValue(){
		return _value;
	}
	
	public String getString(){
		return _string;
	}
}
