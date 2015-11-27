package com.anthole.quickdev;

import com.anthole.quickdev.http.RequestHandle;
import com.anthole.quickdev.ui.IProgressDialog;
import com.anthole.quickdev.ui.IProgressDialog.RequestBean;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.ButterKnife;

public abstract class QFragment extends Fragment{
	
	protected Activity mContext;
	protected View rootView;
	protected IProgressDialog iProgressDialog;
	
	protected abstract void initData(Bundle savedInstanceState);
	protected abstract int getLayoutId();
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		initData(savedInstanceState);
	}
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		mContext = activity;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		if (rootView == null) {
			if (getLayoutId() == 0) {
				return super.onCreateView(inflater, container, savedInstanceState);
			}
			rootView = inflater.inflate(getLayoutId(), null, false);
			ButterKnife.bind(this, rootView);
		}
		return rootView;
	}

	public View getRootView() {
		return rootView;
	}
	
	protected View findViewById(int id){
		if(rootView==null){
			return null;
		}
		return rootView.findViewById(id);
	}

	@Override
	public void onDestroyView() {
		if (rootView != null) {
			((ViewGroup) rootView.getParent()).removeView(rootView);
		}
		super.onDestroyView();
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
	
	public void jump2Activity(Class<?> target){
		Intent intent = new Intent(mContext,target);
		startActivity(intent);
	}
	
	public void jump2Activity(Class<?> target,Bundle extras){
		Intent intent = new Intent(mContext,target);
		intent.putExtras(extras);
		startActivity(intent);
	}
	
	
	IntentFilter filter;
	
	BroadcastReceiver receiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			QFragment.this.onReceive(context, intent);
		}
	};
	
	public String[] filterActions() {
		return null;
	}
	
	public void onReceive(Context context, Intent intent) {
		
	};

	public void register() {
		if (mContext != null) {
			String[] actions = filterActions();
			if (actions == null || actions.length == 0) {
				return;
			}
			filter = new IntentFilter();
			filter.addCategory(mContext.getPackageName());
			for (String action : actions) {
				filter.addAction(action);
			}
			mContext.registerReceiver(receiver, filter);
		}
	}

	public void unRegister() {
		if (mContext != null && filter != null) {
			mContext.unregisterReceiver(receiver);
			filter = null;
		}
	}
	

}
