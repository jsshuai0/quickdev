package com.anthole.quickdev;


import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import butterknife.ButterKnife;

import com.anthole.quickdev.commonUtils.KeyBoardUtils;
import com.anthole.quickdev.http.RequestHandle;
import com.anthole.quickdev.http.base.AsyncHttpClientUtil;
import com.anthole.quickdev.ui.IProgressDialog;
import com.anthole.quickdev.ui.IProgressDialog.RequestBean;

public abstract class QActivity extends Activity {
	
	protected IProgressDialog iProgressDialog;
	
	protected abstract void initData(Bundle savedInstanceState);
	protected abstract int getLayoutId();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		QAppManager.getAppManager().addActivity(this);
		setContentView(getLayoutId());
		initData(savedInstanceState);
	}
	
	@Override
	public void setContentView(int layoutResID) {
		super.setContentView(layoutResID);
		ButterKnife.bind(this);
	}
	
	public void jump2Activity(Class<?> target){
		Intent intent = new Intent(this,target);
		startActivity(intent);
	}
	
	public void jump2Activity(Class<?> target,Bundle extras){
		Intent intent = new Intent(this,target);
		intent.putExtras(extras);
		startActivity(intent);
	}
	
	@Override
	public void finish() {
		//关闭软键盘
		dismissLoadingDialog();
		KeyBoardUtils.hideSoftInput(this);
		super.finish();
	}
	
	@Override
	protected void onDestroy() {
		AsyncHttpClientUtil.getInstance(this).cancelRequests(this, true);
		super.onDestroy();
		QAppManager.getAppManager().finishActivity(this);
	}
	
	public abstract IProgressDialog createIProgressDialog();
	
	public void showLoadingDialog(RequestHandle requestHandle) {
		showLoadingDialog("", requestHandle,0);
	}
	
	public void showLoadingDialog(RequestHandle requestHandle, long delayMillis) {
		showLoadingDialog("", requestHandle,delayMillis);
	}

	public void showLoadingDialog(String content, RequestHandle requestHandle, long delayMillis) {
		if (iProgressDialog == null) {
			iProgressDialog = createIProgressDialog();
		} 
		RequestBean bean = new RequestBean();
		bean.setContent(content);
		bean.setHandle(requestHandle);
		iProgressDialog.setContent(bean);
		handlerProgress.sendEmptyMessageDelayed(1, delayMillis);
		
	}

	public void dismissLoadingDialog() {
		handlerProgress.removeMessages(1);
		if(iProgressDialog!=null){
			iProgressDialog.dismissDialog();
		}
	}
	
	Handler handlerProgress = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			iProgressDialog.showDialog();
		}
	};

}
