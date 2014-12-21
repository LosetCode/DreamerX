package com.droidstouch.iweibo.ui;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.dreamerx.adapter.DreamerxAdapter;
import com.droidstouch.iweibo.R;
import com.droidstouch.iweibo.bean.listInfo;
import com.droidstouch.iweibo.util.ReFlashListView;
import com.droidstouch.iweibo.util.ReFlashListView.IReflashListener;

public class DreamerActivity extends Activity implements IReflashListener{
		private String UrlStr = "http://dreamerx.aliapp.com/servlet/FirstPageAction";
		private Button  write_bt= null;
		private ArrayList<listInfo> status = new ArrayList<listInfo>();
		private DreamerxAdapter adapter;
		private Handler myHandler;
		private ArrayList<HashMap<String, Object>> params;
		private ReFlashListView list = null;
		private listInfo Statu1= new listInfo();
		private listInfo Statu2= new listInfo();
		
		@Override
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.dreamer);
			list = (ReFlashListView)findViewById(R.id.lv_dreamer);
			list.setAdapter(adapter);
			list.setinterface(this);
			list.state=3;
			list.reflashViewByState();
			onReflash();
			list.setOnItemClickListener(new OnItemClickListener(){


				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int position, long arg3) {
					//Intent intent = new Intent(DreamerActivity.this,);
					
				}});
			//////////////////////////////////////////////////////////////////////
			write_bt = (Button)findViewById(R.id.write_btn);
			write_bt.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					Intent intent = new Intent(DreamerActivity.this,PublishActivity2.class);
					startActivity(intent);
				}
			});
			////////////////////////////////////////////////////////////////////////
			
			///////////////////////////////////////////////////////////////////////
			myHandler=new Handler()
			{
				@Override
				public void handleMessage(android.os.Message msg)
				{
					if(msg.what==1)
					{
						showlist(status);
					}
					if(msg.what==2)
					{
						Toast.makeText(DreamerActivity.this, "请检查您的网络", Toast.LENGTH_LONG).show();
						list.reflashComplete();
					}
					if(msg.what==3){
						Toast.makeText(DreamerActivity.this, "加载失败", Toast.LENGTH_LONG).show();
						list.loadingComplete();
					}
				};
			};
			
		}
		
		public String conn(String url){
			String jsonStr = null;
			if(adapter == null){
				Statu1.setId("0");
			}else{
				Statu1 = (listInfo) adapter.getItem(0);
			}
			String DreamerURL = url;
			HttpResponse response;
			HttpClient httpClient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost(DreamerURL);
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
			nameValuePairs.add(new BasicNameValuePair("item_id",Statu1.getId()));
			try {
				httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs,"UTF-8"));
				response = httpClient.execute(httppost);
				// 连接超时
		         httpClient.getParams().setParameter(
		                 CoreConnectionPNames.CONNECTION_TIMEOUT, 100);
		         // 请求超时
		         httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT,
		                 100);
				if(response.getStatusLine().getStatusCode()==200){
					jsonStr=EntityUtils.toString(response.getEntity());
					return jsonStr;
				}
				}catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				} catch (ParseException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			return null;
		}
		public String connlast(String url){
			System.out.println("shangla ");
			String jsonStr = null;
			Statu2 = status.get(status.size()-1);
			String DreamerURL = url;
			HttpResponse response;
			System.out.println("shangla2");
			HttpClient httpClient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost(DreamerURL);
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
			nameValuePairs.add(new BasicNameValuePair("item_id",Statu2.getId()));
			try {
				httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs,"UTF-8"));
				response = httpClient.execute(httppost);
				// 连接超时
		         httpClient.getParams().setParameter(
		                 CoreConnectionPNames.CONNECTION_TIMEOUT, 100);
		         // 请求超时
		         httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT,
		                 100);
				if(response.getStatusLine().getStatusCode()==200){
					jsonStr=EntityUtils.toString(response.getEntity());
					return jsonStr;
				}
				}catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				} catch (ParseException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			return null;
		}
		/*解析*/
		private static ArrayList<HashMap<String, Object>> Analysis(String jsonStr)
	            throws JSONException {
	        JSONArray jsonArray = null;
	        // 初始化list数组对象
	        ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
	        jsonArray = new JSONArray(jsonStr);
	        for (int i = 0; i < jsonArray.length(); i++) {
	            JSONObject jsonObject = jsonArray.getJSONObject(i);
	            // 初始化map数组对象
	            HashMap<String, Object> map = new HashMap<String, Object>();
	            map.put("content", jsonObject.getString("itemcontent"));
	            map.put("item_imageURL", jsonObject.getString("itempicture"));
	            map.put("txt_username", jsonObject.getString("username"));
	            map.put("image_userheadURL", jsonObject.getString("userpicture"));
	            map.put("item_id",jsonObject.getString("itemid"));
	            list.add(map);
	        }
	        return list;
	    }
		/*
		 * 将解析出来的ArrayList<HashMap<String, Object>> 装入ArrayList<Status>中
		 * */
		private void refreshStatus(ArrayList<HashMap<String, Object>> params){
			for(int i=0;i<params.size();i++){
				listInfo statu=new listInfo();
				statu.setContent(params.get(i).get("content").toString());
				statu.setPhotoUrl(params.get(i).get("item_imageURL").toString());
				statu.setUsername(params.get(i).get("txt_username").toString());
				statu.setUserphotoUrl(params.get(i).get("image_userheadURL").toString());
				statu.setId(params.get(i).get("item_id").toString());
				status.add(statu);
			}
		}

		@Override
		public void onReflash() {
			//获取最新数据
			new Thread(){
				String jsonStr1 = null;
				@Override
				public void run()
				{
					jsonStr1 = conn(UrlStr);
					if(jsonStr1==null){
						myHandler.sendEmptyMessage(2);
					}else{
						try {
							params = Analysis(jsonStr1);
							refreshStatus(params);
							} catch (JSONException e) {
							e.printStackTrace();
						}
						myHandler.sendEmptyMessage(1);
					}
				}
			}.start();
			//通知界面显示数据
			//通知listview刷新数据完毕
			
		}
		private void showlist(ArrayList<listInfo> params){
			if(adapter == null){
				adapter = new DreamerxAdapter(DreamerActivity.this,status);
				list.setAdapter(adapter);
				list.reflashComplete();
			}else{
				adapter.notifyDataSetChanged();
				list.reflashComplete();
				list.loadingComplete();
			}
		}

		@Override
		public void onLoad() {
			
			new Thread(){
				String jsonStr2=null;
				@Override
				public void run(){
					jsonStr2 = connlast(UrlStr);
					System.out.println(jsonStr2);
					if(jsonStr2==null){
						myHandler.sendEmptyMessage(3);
					}else{
						try {
							params = Analysis(jsonStr2);
							refreshStatus(params);
							} catch (JSONException e) {
							e.printStackTrace();
						}
						myHandler.sendEmptyMessage(1);
					}
				}
				
			}.start();
			
		}
	
}
