package com.anthole.quickdev.ui.RequestHelper;


import com.anthole.quickdev.http.TextHttpResponseHandler;
import com.anthole.quickdev.ui.MultiStateView;
import com.anthole.quickdev.ui.RequestHelper.base.IDataSource;
import com.anthole.quickdev.ui.RequestHelper.base.IDisplayer;
import com.anthole.quickdev.ui.RequestHelper.base.IRequestHelper;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;

public abstract class RequestHelper <T,V extends View> implements IRequestHelper{
	
	protected TextHttpResponseHandler responseHandler;
	
	protected Context context;
	protected IDataSource dataSource;
	protected IDisplayer<T> displayer;
	protected MultiStateView multiStateView;
	protected V contentView;
	
	public void setDataSource(IDataSource dataSource){
		this.dataSource = dataSource;
	}
	
	public void setDisplayer(IDisplayer<T> displayer){
		this.displayer = displayer;
	}
	
	public void setContentView(V contentView){
		this.contentView = contentView;
	}
	
	public void setMultiStateView(MultiStateView multiStateView){
		this.multiStateView = multiStateView;
		View error = multiStateView.getView(MultiStateView.VIEW_STATE_ERROR);
		error.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				refresh();
			}
		});
	}
	
	public RequestHelper (Context context) {
		this.context = context;
		init();
	}
	
	private void init(){
		responseHandler = getResponseHandler();
	}

	@Override
	public void refresh() {
		dataSource.request(responseHandler);
		RequestHelper.this.multiStateView.setViewState(MultiStateView.VIEW_STATE_LOADING);
	}



}
