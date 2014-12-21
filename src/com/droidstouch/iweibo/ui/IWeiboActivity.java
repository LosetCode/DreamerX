package com.droidstouch.iweibo.ui;

import java.util.ArrayList;
import java.util.HashMap;


/***
 * 
 * Activity 接口
 * 
 *
 */


public interface IWeiboActivity {

	
	//初始化数据
	void init();
	
	//刷新UI
	void refresh(ArrayList<HashMap<String, Object>> params);
	
}
