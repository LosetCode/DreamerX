package com.droidstouch.iweibo.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.util.HashMap;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;

public class AsyncBitmapLoader {
/*
 * 内存图片软引用缓冲
 * */
	private HashMap<String,SoftReference<Bitmap>> imageCache = null;
	public AsyncBitmapLoader(){
		imageCache = new HashMap<String,SoftReference<Bitmap>>();
	}
	public Bitmap loadBitmap(final ImageView imageview,final String imageURL,final ImageCallBack iamgecallback)
	{
		//在内存缓存中，则返回bitmap对象
		if(imageCache.containsKey(imageURL))
		{
			SoftReference<Bitmap> reference = imageCache.get(imageURL);
			Bitmap bitmap = reference.get();
			if(bitmap != null){
				return bitmap;
			}
		}
		else
		{
			//对本地缓存的查找
			String bitmapName = imageURL.substring(imageURL.lastIndexOf("/")+1);
			File cacheDir = new File("/mnt/sdcard/iweibo/");
			File[] cacheFile = cacheDir.listFiles();
			int i = 0;
			for(;i<cacheFile.length;i++)
			{
				if(bitmapName.equals(cacheFile[i].getName()))
				{
					return BitmapFactory.decodeFile("/mnt/sdcard/test/" + bitmapName);
				}
			}
		}	
	
	
	
	final Handler handler = new Handler()    
    {    
        /* (non-Javadoc)   
         * @see android.os.Handler#handleMessage(android.os.Message)   
         */    
        @Override    
        public void handleMessage(Message msg)    
        {    
        	iamgecallback.imageLoad(imageview, (Bitmap)msg.obj);    
        }    
    };  
        
    //如果不在内存缓存中，也不在本地（被jvm回收掉），则开启线程下载图片    
    new Thread()    
    {    
        /* (non-Javadoc)   
         * @see java.lang.Thread#run()   
         */    
        @Override    
        public void run()    
        {    
            InputStream bitmapIs = Request.HandlerData(imageURL); //通过网络获得图片输入流   
                
            Bitmap bitmap = BitmapFactory.decodeStream(bitmapIs);    
            imageCache.put(imageURL, new SoftReference<Bitmap>(bitmap)); //存在缓存里   
            Message msg = handler.obtainMessage(0, bitmap);    
            handler.sendMessage(msg);    
                
            File dir = new File("/mnt/sdcard/test/");    
            if(!dir.exists())    
            {    
                dir.mkdirs();    
            }    
                
            File bitmapFile = new File("/mnt/sdcard/test/" +     
                    imageURL.substring(imageURL.lastIndexOf("/") + 1));    
            if(!bitmapFile.exists())  //如果文件不存在，则存起来  
            {    
                try    
                {    
                    bitmapFile.createNewFile();    
                }    
                catch (IOException e)    
                {    
                    e.printStackTrace();    
                }    
            }    
            FileOutputStream fos;    
            try    
            {    
                fos = new FileOutputStream(bitmapFile); //写进文件中   
                bitmap.compress(Bitmap.CompressFormat.PNG,     
                        100, fos);    
                fos.close();    
            }    
            catch (FileNotFoundException e)    
            {    
                e.printStackTrace();    
            }    
            catch (IOException e)    
            {    
                e.printStackTrace();    
            }    
        }    
    }.start();    
        
    return null;    
}    

public interface ImageCallBack    
{    
    public void imageLoad(ImageView imageView, Bitmap bitmap);    
}

}
