package com.anthole.quickdev.http.base;

import android.content.Context;

import com.anthole.quickdev.http.AsyncHttpClient;
import com.anthole.quickdev.http.PersistentCookieStore;


/**
 * 全局用同一个   AsyncHttpClient 对象 ，并保持 Cookie
 * @author 123
 *
 */
public class AsyncHttpClientUtil {
	
	private static AsyncHttpClient client;  
	
	private static PersistentCookieStore mCookieStore;
	  
    public static AsyncHttpClient getInstance(Context paramContext) {  
        if (client == null) {  
            client = new AsyncHttpClient();  
            client.setTimeout(300*1000);
            client.setConnectTimeout(30*1000);
            mCookieStore = new PersistentCookieStore(paramContext); 
            client.setCookieStore(mCookieStore);  
        }
        return client;  
    }
    
    public  static PersistentCookieStore getCookieStore(){
    	return mCookieStore;
    }
    
    public static void clearSession(Context paramContext){
    	PersistentCookieStore myCookieStore = mCookieStore; 
    	if(myCookieStore!=null){
    		myCookieStore.clear();
    		client.setCookieStore(mCookieStore);
    	}
    	
    }
    

}
