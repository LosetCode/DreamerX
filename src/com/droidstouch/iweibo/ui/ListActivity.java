package com.droidstouch.iweibo.ui;

import java.util.ArrayList;
import java.util.List;

import com.droidstouch.iweibo.R;
import com.droidstouch.iweibo.fragment.RealizedFragment;
import com.droidstouch.iweibo.fragment.RealizeingFragment;

import android.app.Activity;
import android.app.TabActivity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;

public class ListActivity extends FragmentActivity {
	private List<Fragment> Datas ;
	private FragmentPagerAdapter madapter;
	private TextView realizeing;
	private TextView realized;
	private ViewPager mViewPager;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.listmain);
		initView();
	}
	private void initView() {
		mViewPager = (ViewPager) findViewById(R.id.viewPager);
		Datas = new ArrayList<Fragment>();
		RealizeingFragment tab1 = new RealizeingFragment();
		RealizedFragment tab2 = new RealizedFragment();
		Datas.add(tab1);
		Datas.add(tab2);
		realizeing = (TextView)findViewById(R.id.realizing);
		realized = (TextView)findViewById(R.id.realized);
		madapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
			
			@Override
			public int getCount() {
				return Datas.size();
			}
			
			@Override
			public Fragment getItem(int arg0) {
				return Datas.get(arg0);
			}
		};
		mViewPager.setAdapter(madapter);
		mViewPager.setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int Position) {
				resetText();
				switch(Position){
				case 0:
					realizeing.setTextColor(getResources().getColor(R.color.mgreen));
					break;
				case 1:
					realized.setTextColor(getResources().getColor(R.color.mgreen));
					break;
			
				}
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	protected void resetText() {
		realizeing.setTextColor(Color.WHITE);
		realized.setTextColor(Color.WHITE);
	}

}
