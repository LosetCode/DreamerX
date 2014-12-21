package com.dreamerx.adapter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.util.HashMap;
import com.droidstouch.iweibo.util.Request;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;


public class Asynctask1 extends AsyncTask<String,Integer, Bitmap[]> {
	
	
	private HashMap<String,SoftReference<Bitmap>> imageCache = null;
	private ImageView image;
    // 初始化
    public Asynctask1(ImageView image) {
 	   this.image=image;
 	   imageCache = new HashMap<String, SoftReference<Bitmap>>();
    }

    @Override
    protected Bitmap[] doInBackground(String... params) 
    {	
    	Bitmap[] bitmap = new Bitmap[2];
    	Bitmap photo ;
    	for(int i=0;i<params.length;i++){
    		//在内存缓存中，则返回bitmap对象
    			if(imageCache.containsKey(params[i]))
    			{	
    				SoftReference<Bitmap> reference = imageCache.get(params[i]);
    				photo = reference.get();
    				if(photo != null){
    					bitmap[i]=photo;
    				}
    			}
    			else 
    			{	
    				//对本地缓存的查找
    				String bitmapName = params[i].substring(params[i].lastIndexOf("/")+1);
    				File cacheDir = new File("/mnt/sdcard/DreamerX/");
    				File[] cacheFiles = cacheDir.listFiles();
    				int j = 0;
    				if(null!=cacheFiles){
	    				for(;j<cacheFiles.length;j++)
	    				{
	    					if(bitmapName.equals(cacheFiles[j].getName()))
	    					{
	    						photo =BitmapFactory.decodeFile("/mnt/sdcard/DreamerX/" + bitmapName);
	    						bitmap[i]=photo;
	    					}
	    				}
    				}
    			}
    			if(bitmap[i]==null)
    			{	
				    InputStream bitmapIs = Request.HandlerData(params[i]); //通过网络获得图片输入流   
				    photo = BitmapFactory.decodeStream(bitmapIs);    
		            imageCache.put(params[i], new SoftReference<Bitmap>(photo)); //存在缓存里   
		            File dir = new File("/mnt/sdcard/DreamerX/");    
		            if(!dir.exists())    
		            {    
		                dir.mkdirs();  
		            }    
		                
		            File bitmapFile = new File("/mnt/sdcard/DreamerX/" +     
		            		params[i].substring(params[i].lastIndexOf("/") + 1));    
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
		                photo.compress(Bitmap.CompressFormat.PNG,     
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
		            bitmap[i]=photo;
    			}
    	}
		return bitmap;
    }
    @Override
	public void onPostExecute(Bitmap[] bitmap){
    	image.setImageBitmap(bitmap[1]);
    }
 }