package com.anthole.quickdev;


import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
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
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		QAppManager.getAppManager().addActivity(this);
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
		AsyncHttpClientUtil.getInstance(this).cancelRequests(this, true);
		super.onDestroy();
		QAppManager.getAppManager().finishActivity(this);
	}
	
	protected void addFragment(Fragment fragment, int containerID){
		FragmentTransaction transaction = getFragmentManager().beginTransaction();
		transaction.setCustomAnimations(0, 0);
		transaction.add(containerID, fragment);
		transaction.commitAllowingStateLoss();
	}
	
	protected void hideFragment(Fragment fragment){
		FragmentTransaction transaction = getFragmentManager().beginTransaction();
		transaction.setCustomAnimations(0, 0);
		transaction.hide(fragment);
		transaction.commitAllowingStateLoss();
		
	}
	
	protected void showFragment(Fragment fragment){
		FragmentTransaction transaction = getFragmentManager().beginTransaction();
		transaction.setCustomAnimations(0, 0);
		transaction.show(fragment);
		transaction.commitAllowingStateLoss();
	}
	
	protected void removeFragment(Fragment fragment){
		FragmentTransaction transaction = getFragmentManager().beginTransaction();
		transaction.setCustomAnimations(0, 0);
		transaction.remove(fragment);
		transaction.commitAllowingStateLoss();
	}

	public void replaceFragment(Fragment fragment, int containerID) {

		FragmentTransaction transaction = getFragmentManager().beginTransaction();
		transaction.setCustomAnimations(0, 0);
		transaction.replace(containerID, fragment);
		transaction.commitAllowingStateLoss();
	}

	public void replaceFragment(Fragment fragment, int containerID, int animIn, int animOut) {
		FragmentTransaction transaction = getFragmentManager().beginTransaction();
		if (animIn > 0 || animOut > 0) {
			transaction.setCustomAnimations(animIn, animOut);
		}
		transaction.replace(containerID, fragment);
		transaction.commitAllowingStateLoss();
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
