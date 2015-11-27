package com.anthole.quickdev.ui.RequestHelper;


import com.anthole.quickdev.http.ResponseHandlerInterface;
import com.anthole.quickdev.ui.MultiStateView;
import com.anthole.quickdev.ui.RequestHelper.base.IDataSource;
import com.anthole.quickdev.ui.RequestHelper.base.IParser;
import com.anthole.quickdev.ui.RequestHelper.base.IRequestHelper;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;

public abstract class RequestHelper <T,V extends View,H extends ResponseHandlerInterface> implements IRequestHelper<H>{
	
	protected H responseHandler;
	
	protected Context context;
	protected IDataSource<H> dataSource;
	protected IParser<T> parser;
	protected MultiStateView multiStateView;
	protected V contentView;
	
	public void setDataSource(IDataSource<H> dataSource){
		this.dataSource = dataSource;
	}
	
	public void setParser(IParser<T> parser){
		this.parser = parser;
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
	
	@Override
	public void setViewState(int state){
		if(this.multiStateView!=null){
			multiStateView.setViewState(state);
		}
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
