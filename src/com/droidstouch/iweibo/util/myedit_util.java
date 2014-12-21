package com.droidstouch.iweibo.util;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.util.AttributeSet;
import android.widget.EditText;

public class myedit_util extends EditText{

	public myedit_util(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public myedit_util(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
	public void inserDrawable(int id){
		final SpannableString ss = new SpannableString("easy");
		Drawable d = getResources().getDrawable(id);
		d.setBounds(0, 0, d.getIntrinsicWidth(), d.getIntrinsicHeight());
		ImageSpan span = new ImageSpan(d,ImageSpan.ALIGN_BASELINE);
		ss.setSpan(span, 0, "easy".length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
	}
}
