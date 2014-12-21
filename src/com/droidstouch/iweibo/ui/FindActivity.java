package com.droidstouch.iweibo.ui;

import com.droidstouch.iweibo.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class FindActivity extends Activity {
	
	Button search;

	protected void onCreate(Bundle savedInstanceState) {	
		super.onCreate(savedInstanceState);
		setContentView(R.layout.find);
		search=(Button)this.findViewById(R.id.title_bar_find);		
		search.setOnClickListener(new View.OnClickListener() {
			
	
			public void onClick(View v) {

				Intent intent=new Intent(FindActivity.this, SearchActivity.class);
				startActivity(intent);
				
				
				
				
				
			}
		});
		
	
	}

}
