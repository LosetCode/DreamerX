package com.droidstouch.iweibo.util;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class Request {
    public static InputStream HandlerData(String url) {
       InputStream inStream=null;

       try {
           URL feedUrl = new URL(url);
           URLConnection conn = feedUrl.openConnection();
           conn.setConnectTimeout(10 * 1000);
           inStream = conn.getInputStream();
       } catch (Exception e) {
           e.printStackTrace();
       }
       
       return inStream;
    }
}