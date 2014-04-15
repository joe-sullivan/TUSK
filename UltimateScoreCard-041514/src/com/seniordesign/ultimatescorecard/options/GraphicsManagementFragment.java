package com.seniordesign.ultimatescorecard.options;

import com.seniordesign.ultimatescorecard.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class GraphicsManagementFragment extends Fragment{
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = (View) inflater.inflate(R.layout.fragment_graphics_mgmt, container, false);
        return view;
	}	
}
