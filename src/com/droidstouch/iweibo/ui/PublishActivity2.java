package com.droidstouch.iweibo.ui;

import com.droidstouch.iweibo.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class PublishActivity2 extends Activity {
	
	private Button write_bt_back;
	private Button next,sort_bt_personal,sort_bt_team,sort_bt_school,sort_bt_socity,sort_bt_goodthing,sort_bt_chuangye,sort_bt_search,sort_bt_internet;
	private EditText edit_Name;
	private EditText edit_Intro;
	private TextView sort,sort2;

	String sort11;
	String sort22;
	String itemIntro;
	String itemName;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.publish);
		edit_Name=(EditText) findViewById(R.id.edit_Title);
		edit_Intro=(EditText)findViewById(R.id.edit_intro);
		
		sort=(TextView)findViewById(R.id.txt_sort);
		sort2=(TextView)findViewById(R.id.txt_sort2);
		
		
	   next=(Button)findViewById(R.id.next);
	   
		write_bt_back=(Button)findViewById(R.id.write_bt_back);
		
		sort_bt_personal=(Button)findViewById(R.id.sort_bt_personal);
		sort_bt_personal.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {

				if(sort.getText()==""){
				sort.setText("个人");
				}
				
				else{
					sort2.setText("个人");
				}
				
				
			
			}
		});
		
		
		
		
		sort_bt_team=(Button)findViewById(R.id.sort_bt_team);
		sort_bt_team.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {

				if(sort.getText()==""){
				sort.setText("团队");
				}
				
				else{
					sort2.setText("团队");
				}
				
				
			
			}
		});
		
		sort_bt_school=(Button)findViewById(R.id.sort_bt_school);
		sort_bt_school.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {

				if(sort.getText()==""){
				sort.setText("校园");
				}
				
				
				else{
					sort2.setText("校园");
					
				}
				
				
			
			}
		});
		
		sort_bt_socity=(Button)findViewById(R.id.sort_bt_social);
		sort_bt_socity.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {

				if(sort.getText()==""){
				sort.setText("社会");
				}
				
				else{
					sort2.setText("社会");
				}
				
				
			
			}
		});
		
		sort_bt_goodthing=(Button)findViewById(R.id.sort_bt_goodthing);
		sort_bt_goodthing.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {

				if(sort.getText()==""){
				sort.setText("公益");
				}
				
				
				else{
					sort2.setText("公益");
				}
				
				
			
			}
		});
		
		sort_bt_chuangye=(Button)findViewById(R.id.sort_bt_chuangye);
		sort_bt_chuangye.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {

				if(sort.getText()==""){
				sort.setText("创业");
				}
				
				else{
					sort2.setText("创业");
				}
				
				
			
			}
		});
		
		
		
		sort_bt_search=(Button)findViewById(R.id.sort_bt_search);
		sort_bt_search.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {

				if(sort.getText()==""){
				sort.setText("科研");
				}
				
				
				else{
					sort2.setText("科研");
				}
				
				
			
			}
		});
		
		
		sort_bt_internet=(Button)findViewById(R.id.sort_bt_internet);
		sort_bt_internet.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {

				if(sort.getText()==""){
				sort.setText("互联网");
				}
				
				
				else{
					sort2.setText("互联网");
				}
				
				
			
			}
		});
		
		
		
		
		
		next.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				itemName=edit_Name.getText().toString();
				itemIntro=edit_Intro.getText().toString();
				sort11=sort.getText().toString();
				sort22=sort2.getText().toString();
				Intent intent=new Intent(PublishActivity2.this,PublishActivity.class);
				Bundle bundle = new Bundle();
				
				
				bundle.putString("itemName", itemName);
				bundle.putString("sort11", sort11);
				bundle.putString("sort22", sort22);
				bundle.putString("itemIntro", itemIntro);
				intent.putExtras(bundle);
				
				startActivity(intent);
				PublishActivity2.this.finish();
				
				// TODO Auto-generated method stub
				
			}
		});
		
		
		write_bt_back.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				
				Intent intent=new Intent(PublishActivity2.this,DreamerActivity.class);
				startActivity(intent);
				
				PublishActivity2.this.finish();
				
				
				
				
			}
		});
		
		
	}
	
	
	
	
	
	
	
	
	

}
