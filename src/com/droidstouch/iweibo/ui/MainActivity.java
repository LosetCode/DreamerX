package com.droidstouch.iweibo.ui;

import com.droidstouch.iweibo.R;

import android.app.Activity;
import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View.OnClickListener;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

public class MainActivity extends TabActivity {
	
	
	private static final String DREAMER_TAB="dreamer";
	private static final String FIND_TAB="find";
	private static final String LIST_TAB="list";
	private static final String MESSAGE_TAB="message";
	private static final String USER_TAB="user";
			
	private TabHost tabHost;
	
	
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.main);
		
		
	
		
		
		tabHost=this.getTabHost();
		
		
		TabSpec dreamerSpec=tabHost.newTabSpec(DREAMER_TAB).setIndicator(DREAMER_TAB).setContent(new Intent(this,DreamerActivity.class));
		TabSpec findSpec=tabHost.newTabSpec(FIND_TAB).setIndicator(DREAMER_TAB).setContent(new Intent(this,FindActivity.class));
		TabSpec listSpec=tabHost.newTabSpec(LIST_TAB).setIndicator(DREAMER_TAB).setContent(new Intent(this,ListActivity.class));
		TabSpec messageSpec=tabHost.newTabSpec(MESSAGE_TAB).setIndicator(DREAMER_TAB).setContent(new Intent(this,MessageActivity.class));
		TabSpec userSpec=tabHost.newTabSpec(USER_TAB).setIndicator(DREAMER_TAB).setContent(new Intent(this,UserActivity.class));
		

		tabHost.addTab(dreamerSpec); 
		tabHost.addTab(findSpec);
		tabHost.addTab(listSpec);
		tabHost.addTab(messageSpec);
		tabHost.addTab(userSpec);
		
        RadioGroup radiogroup=(RadioGroup)this.findViewById(R.id.rg_main_btns);
        
        radiogroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
        	
        	
			public void onCheckedChanged(RadioGroup arg0, int checkedId) {

			switch (checkedId) {
			case R.id.dreamer_rb:
			tabHost.setCurrentTabByTag(DREAMER_TAB);
				break;
				
				
				case R.id.find_rb:
				tabHost.setCurrentTabByTag(FIND_TAB);
					break;
					
					
					case R.id.list_rb:
					tabHost.setCurrentTabByTag(LIST_TAB);
						break;
						
						
						case R.id.message_rb:
						tabHost.setCurrentTabByTag(MESSAGE_TAB);
							break;
							
							
							case R.id.user_rb:
							tabHost.setCurrentTabByTag(USER_TAB);
								break;
			default:
				break;
			}
			}
		});
	}
}
