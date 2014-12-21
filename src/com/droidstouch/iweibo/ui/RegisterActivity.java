package com.droidstouch.iweibo.ui;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import com.droidstouch.iweibo.R;
import com.droidstouch.iweibo.ui.LoginActivity.MyThread;

import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class RegisterActivity extends Activity {


	
	private EditText register_nakename;
	private EditText register_email;
	private EditText register_password;
	private EditText register_confirm_password;
	private Button register_btn;
    private ProgressDialog pd;
	private static final String login_path = "http://dreamerx.aliapp.com/servlet/RegisterAction";

	private Handler myHandler = new Handler() {
		public void handleMessage(Message msg) {
			if (msg.what == 1) {
				Intent intent = new Intent(RegisterActivity.this,
						LoginActivity.class);
				startActivity(intent);
			}

			else if (msg.what == 0) {
				
				ShowDialog("注册失败请重试");

			}
		};
	};

	
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		setContentView(R.layout.register);
		
		

		register_nakename = (EditText) findViewById(R.id.register_nakename);

		register_email = (EditText) findViewById(R.id.register_email);

		register_password = (EditText) findViewById(R.id.register_password);

		register_confirm_password = (EditText) findViewById(R.id.register_confirm_password);

		register_btn = (Button) findViewById(R.id.register_btn);
		pd=new ProgressDialog(this);
		pd.setTitle("提示信息");
		pd.setMessage("正在注册，请稍后...");

		register_btn.setOnClickListener(new View.OnClickListener() {

			public void onClick(View arg0) {
				if( Available()){
				new Thread(new MyThread()).start();
				
				pd.show();
				RegisterActivity.this.finish();
			}
			}
		});

	}

	public class MyThread implements Runnable {
		public void run() {

			Message message = Message.obtain();
			String userName = register_nakename.getText().toString();
			String password = register_password.getText().toString();
			String email= register_email.getText().toString();
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("username", userName));
			params.add(new BasicNameValuePair("password", password));
			params.add(new BasicNameValuePair("email", email));
			HttpClient httpClient = new DefaultHttpClient();
			HttpPost httpPost = new HttpPost(login_path);
			HttpResponse httpResponse;
			try {
				httpPost.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
				httpResponse = httpClient.execute(httpPost);
				if (httpResponse.getStatusLine().getStatusCode() == 200) {
					String result = EntityUtils.toString(httpResponse
							.getEntity());
					if (result.equals("successRegister")) {
						message.what = 1;
						myHandler.sendMessage(message);
					} else {
						message.what = 0;
						myHandler.sendMessage(message);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

		
		
	
	}
	
	
	public boolean Available()
	{
		String password=register_password.getText().toString();
		String confirm = register_confirm_password.getText().toString();	    
		String userName = register_nakename.getText().toString();
		String email= register_email.getText().toString();
		
	if(userName.equals(""))
	{
		ShowDialog("账号是必填项!");
		return false;
	}
	
	if(email.equals(""))
	{
		ShowDialog("邮箱是必填项!");
		return false;
	}
		
	if(password.equals(""))
	{
		ShowDialog("密码是必填项!");
		return false;
	}
	else if(confirm.equals(""))
	{
		ShowDialog("请确认密码");
		return false;
	}
	
	if (!confirm.equals(password))
	{
		ShowDialog("请重新确认密码");
		return false;
	}
	
	return true;
}
	

	
	private void ShowDialog(String msg) 
	{
		AlertDialog.Builder bulider = new AlertDialog.Builder(this);
		bulider.setMessage(msg);
		bulider.setCancelable(false);
		bulider.setPositiveButton("确定", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
			}
		}).show();
	}
}
