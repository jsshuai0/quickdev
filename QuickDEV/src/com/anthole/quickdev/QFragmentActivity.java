package com.anthole.quickdev;


import com.anthole.quickdev.commonUtils.KeyBoardUtils;
import com.anthole.quickdev.http.RequestHandle;
import com.anthole.quickdev.http.base.AsyncHttpClientUtil;
import com.anthole.quickdev.ui.IProgressDialog;
import com.anthole.quickdev.ui.IProgressDialog.RequestBean;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import butterknife.ButterKnife;

public abstract class QFragmentActivity extends FragmentActivity {
	
	protected IProgressDialog iProgressDialog;
	
	protected abstract void initData(Bundle savedInstanceState);
	protected abstract int getLayoutId();
	protected abstract IProgressDialog createIProgressDialog();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		QAppManager.getAppManager().addActivity(this);
		setContentView(getLayoutId());
		initData(savedInstanceState);
		register();
	}
	
	@Override
	public void setContentView(int layoutResID) {
		super.setContentView(layoutResID);
		ButterKnife.bind(this);
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
		unRegister();
		AsyncHttpClientUtil.getInstance(this).cancelRequests(this, true);
		super.onDestroy();
		QAppManager.getAppManager().finishActivity(this);
	}
	
	protected void addFragment(Fragment fragment, int containerID){
		FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
		transaction.setCustomAnimations(0, 0);
		transaction.add(containerID, fragment);
		transaction.commitAllowingStateLoss();
	}
	
	protected void hideFragment(Fragment fragment){
		FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
		transaction.setCustomAnimations(0, 0);
		transaction.hide(fragment);
		transaction.commitAllowingStateLoss();
		
	}
	
	protected void showFragment(Fragment fragment){
		FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
		transaction.setCustomAnimations(0, 0);
		transaction.show(fragment);
		transaction.commitAllowingStateLoss();
	}
	
	protected void removeFragment(Fragment fragment){
		FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
		transaction.setCustomAnimations(0, 0);
		transaction.remove(fragment);
		transaction.commitAllowingStateLoss();
	}

	protected void replaceFragment(Fragment fragment, int containerID) {

		FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
		transaction.setCustomAnimations(0, 0);
		transaction.replace(containerID, fragment);
		transaction.commitAllowingStateLoss();
	}

	protected void replaceFragment(Fragment fragment, int containerID, int animIn, int animOut) {
		FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
		if (animIn > 0 || animOut > 0) {
			transaction.setCustomAnimations(animIn, animOut);
		}
		transaction.replace(containerID, fragment);
		transaction.commitAllowingStateLoss();
	}
	
	protected void showLoadingDialog(RequestHandle requestHandle) {
		showLoadingDialog("", requestHandle,0);
	}
	
	protected void showLoadingDialog(RequestHandle requestHandle, long delayMillis) {
		showLoadingDialog("", requestHandle,delayMillis);
	}

	protected void showLoadingDialog(String content, RequestHandle requestHandle, long delayMillis) {
		if (iProgressDialog == null) {
			iProgressDialog = createIProgressDialog();
		} 
		RequestBean bean = new RequestBean();
		bean.setContent(content);
		bean.setHandle(requestHandle);
		iProgressDialog.setContent(bean);
		handlerProgress.sendEmptyMessageDelayed(1, delayMillis);
		
	}

	protected void dismissLoadingDialog() {
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
	
	public void jump2Activity(Class<?> target){
		Intent intent = new Intent(this,target);
		startActivity(intent);
	}
	
	public void jump2Activity(Class<?> target,Bundle extras){
		Intent intent = new Intent(this,target);
		intent.putExtras(extras);
		startActivity(intent);
	}
	
	IntentFilter filter;

	public void register() {
		String[] actions = filterActions();
		if (actions == null || actions.length == 0) {
			return;
		}
		filter = new IntentFilter();
		for (String action : actions) {
			filter.addAction(action);
		}
		filter.addCategory(getPackageName());
		registerReceiver(receiver, filter);
	}
	
	public void unRegister() {
		if (filter != null) {
			unregisterReceiver(receiver);
			filter = null;
		}
	}
	
	public String[] filterActions() {
		return null;
	}
	
	public void onReceive(Context context, Intent intent) {
		
	};
	
	BroadcastReceiver receiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			QFragmentActivity.this.onReceive(context, intent);
		}
	};

}
