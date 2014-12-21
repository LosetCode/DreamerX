package com.droidstouch.iweibo.fragment;

import java.util.ArrayList;

import com.dreamerx.adapter.DreamerxAdapter;
import com.droidstouch.iweibo.R;
import com.droidstouch.iweibo.bean.listInfo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class RealizedFragment extends Fragment {
	private ArrayList<listInfo> status = new ArrayList<listInfo>();
	private DreamerxAdapter adapter;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return inflater.inflate(R.layout.relize02, container,false);
	}
	

}
