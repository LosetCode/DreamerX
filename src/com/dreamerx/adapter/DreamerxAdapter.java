package com.dreamerx.adapter;
import java.util.ArrayList;
import java.util.List;

import com.droidstouch.iweibo.R;
import com.droidstouch.iweibo.bean.listInfo;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
public class DreamerxAdapter extends BaseAdapter{
	private Context context;//上下文
	private LayoutInflater inflater; //布局填充器
	private ViewHolder holder;
	private  List<listInfo> status;
	Bitmap bitmap =null;
	Bitmap userpic = null;
	Handler myHandler;

	public DreamerxAdapter(Context context,List<listInfo> status){
		if(null != status){
			this.status=status;
		}else{
			this.status = new ArrayList<listInfo>();
		}
		
		this.context=context;
		inflater =LayoutInflater.from(context);	
	}

	@Override
	public void notifyDataSetChanged() {
		super.notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		return status==null?0:status.size();
	}

	@Override
	public Object getItem(int arg0) {
		return status==null?null:status.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		
		return 0;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		if(null==status){
			return null;
		}else{
		holder = new ViewHolder();
		if(null == arg1){
			arg1=inflater.inflate(R.layout.dreamerx_item, null);
			holder.content = (TextView) arg1.findViewById(R.id.item_title);
			holder.item_image = (ImageView)arg1.findViewById(R.id.item_photo);
			arg1.setTag(holder);
		}else{
			holder = (ViewHolder) arg1.getTag();
		}
		listInfo statu = status.get(arg0);
		holder.content.setText(statu.getContent());
		
		asyncImageLoad(holder.item_image,statu.getUserphotoUrl(),statu.getPhotoUrl());
		return arg1;
		}
		
	}
	
	private void asyncImageLoad(ImageView item_image, 
								String UserphotoUrl,String photoUrl) {
		Asynctask1 asynctask = new Asynctask1(item_image);
		asynctask.execute(UserphotoUrl,photoUrl);
	}

	private class ViewHolder{
		private ImageView item_image;
		private TextView content;
	}

}
