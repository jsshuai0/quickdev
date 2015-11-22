package com.anthole.quickdev.invoke;

import java.io.File;

import android.content.Context;

import com.anthole.quickdev.commonUtils.fileUtils.StorageUtils;

public class FileInvoke{
	
	private static FileInvoke instance;
	private Context mContext;
	private String mDir;

	public static FileInvoke getInstance() {
		if(instance == null){
			instance = new FileInvoke(); 
		}
		return instance;
	}
	private FileInvoke(){
		
	}
	
	public void init(Context context, String dir){
		mContext = context;
		this.mDir = dir;
	}
	
	/**
	 * 配置 appDir
	 * @return
	 */
	public File getAppDir(){
		File file = StorageUtils.getOwnCacheDirectory(mContext, mDir);
		if(!file.exists()){
			file.mkdirs();
		}
		return file;
	}
	

}
