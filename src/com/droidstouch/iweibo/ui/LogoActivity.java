package com.droidstouch.iweibo.ui;

import com.droidstouch.iweibo.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.ImageView;

public class LogoActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		//���娑����棰�
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		//���娑���舵�����
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		setContentView(R.layout.logo);
        
		
		AlphaAnimation animation=new AlphaAnimation(0.0f,1.0f);
		animation.setDuration(1800);
		
		ImageView img_Logo=(ImageView)this.findViewById(R.id.img_logo);
		img_Logo.setAnimation(animation);
		
		animation.setAnimationListener(new AnimationListener()
		{
			
			public void onAnimationStart(Animation animation){
				
			}
			
            public void onAnimationRepeat(Animation animation){
				
			}
            
            public void onAnimationEnd(Animation animation){
				
            	Intent intent=new Intent(LogoActivity.this,LoginActivity.class);
            	startActivity(intent);
				LogoActivity.this.finish();

			}

		
		});
		
		
		
		
		
	}

}
