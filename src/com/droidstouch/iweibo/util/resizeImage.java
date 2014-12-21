package com.droidstouch.iweibo.util;

import android.graphics.Bitmap;
import android.graphics.Matrix;
/*
 * 裁剪图片
 * */
public class resizeImage {

	public static final Bitmap ResizeImage(Bitmap originalBitmap, int newWidth, int newHeight){
		int width = originalBitmap.getWidth();
		int height = originalBitmap.getHeight();
		float scanleWidth = (float)newWidth/width;
		float scanleHeight = (float)newHeight/height;
		Matrix matrix = new Matrix();
		matrix.postScale(scanleWidth,scanleHeight);
		Bitmap resizedBitmap = Bitmap.createBitmap(originalBitmap,0,0,width,height,matrix,true);
		return resizedBitmap;
	}
}
