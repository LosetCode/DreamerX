package com.droidstouch.iweibo.ui;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;

import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;

import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import com.droidstouch.iweibo.R;
import com.droidstouch.iweibo.bean.userinfo;

import android.os.Bundle;
import android.os.Handler;

import android.os.Message;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;

import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity {
	public static final userinfo user = new userinfo();
	private static final String login_path = "http://dreamerx.aliapp.com/servlet/LoginAction";
	private EditText Id,Password;
	private Button Login,Register;
	private ProgressDialog pd;
	private String username;
	private String password;
	private Handler myHandler=new Handler(){
		public void handleMessage(Message msg) {
			if(msg.what==1)
			{
				pd.dismiss();
				user.setUsername(username);
				user.setPassword(password);
				Intent intent=new Intent(LoginActivity.this,MainActivity.class);
				startActivity(intent);
				LoginActivity.this.finish();

			}
			else if(msg.what==0)
			{
				pd.dismiss();
				ShowDialog("用户名或密码错误，请重新输入!");
			}
			else if(msg.what==2)
			{
				pd.dismiss();
				Toast.makeText(LoginActivity.this, "请检查您的网络", Toast.LENGTH_LONG).show();
			}
		};
	};
	
	
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		
		setContentView(R.layout.login);
		
		Id=(EditText)this.findViewById(R.id.Login_Id);
		Password=(EditText)this.findViewById(R.id.Login_Password);
		Login=(Button)this.findViewById(R.id.btn_login);
		pd=new ProgressDialog(this);
		pd.setTitle("提示信息");
		pd.setMessage("正在登陆，请稍后...");
		
		
		
		
		
		Login.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View v)
			{
				if(Available())
				{
					new Thread(new MyThread()).start();
					pd.show();
				}
			}
		});
		Register=(Button)this.findViewById(R.id.btn_register);
		Register.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				
				
				Intent intent=new Intent(LoginActivity.this,RegisterActivity.class);
				startActivity(intent);
			}
		});
	}
	public class MyThread implements Runnable{
		public void run() {
			Message message=Message.obtain();
			username=Id.getText().toString();
			password=Password.getText().toString();
			List<NameValuePair> params=new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("username",username));
			params.add(new BasicNameValuePair("password",password));
			HttpClient httpClient=new DefaultHttpClient();
			HttpPost httpPost=new HttpPost(login_path);
			HttpResponse httpResponse;
			
			try {
				
				httpPost.setEntity(new UrlEncodedFormEntity(params,HTTP.UTF_8));
				httpResponse=httpClient.execute(httpPost);
				// 连接超时
				httpClient.getParams().setParameter(
		                 CoreConnectionPNames.CONNECTION_TIMEOUT, 3000);
		         // 请求超时
		         httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT,
		                 3000);
				if(httpResponse.getStatusLine().getStatusCode()==200)
				{
					
					String result=EntityUtils.toString(httpResponse.getEntity());
					if(result.equals("successLogin")){
						message.what=1;
						myHandler.sendMessage(message);
					}
					else{
						message.what=0;
						myHandler.sendMessage(message);
					}
				}else{
					message.what=2;
					myHandler.sendMessage(message);
					}
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
		    }
		}
	}
	public boolean Available()
	{
		String Username=Id.getText().toString();
		String Userpassword=Password.getText().toString();
		if(Username.equals(""))
		{
			ShowDialog("账号是必填项!");
			return false;
		}
		else if(Userpassword.equals(""))
		{
			ShowDialog("密码是必填项!");
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
