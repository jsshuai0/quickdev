package com.anthole.quickdev;

import android.app.Application;

import com.anthole.quickdev.cache.ACache;
import com.anthole.quickdev.commonUtils.UniversalImageLoader;
import com.anthole.quickdev.invoke.FileInvoke;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

public abstract class QApplication extends Application{
	
	private static QApplication application;
	
	@Override
	public void onCreate() {
		super.onCreate();
		application = this;
		FileInvoke.getInstance().init(this, getAppDir());
		ImageLoaderConfiguration config = UniversalImageLoader.getDefaultImageLoaderConfiguration(getApplicationContext());
        ImageLoader.getInstance().init(config);
	}
	
	/**
	 * 获取Application
	 * 
	 * @return
	 */
	public static QApplication getApplication()
	{
		return application;
	}
	
	/**
	 * 配置  app dir 如 qiugou
	 * @return
	 */
	public abstract String getAppDir();

}
