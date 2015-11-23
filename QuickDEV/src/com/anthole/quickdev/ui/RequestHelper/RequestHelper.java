package com.anthole.quickdev.ui.RequestHelper;

import com.anthole.quickdev.adapter.QuickAdapter;
import com.anthole.quickdev.http.ResponseHandlerInterface;
import com.anthole.quickdev.http.TextHttpResponseHandler;
import com.anthole.quickdev.ui.MultiStateView;
import com.anthole.quickdev.ui.ptr.PtrFrameLayout;
import com.anthole.quickdev.ui.ptr.PtrHandler;

import android.content.Context;
import android.view.View;
import cz.msebera.android.httpclient.Header;

public class RequestHelper <T,V extends View> implements PtrHandler,IRequestHelper{
	
	Builder<T,V> builder;
	ResponseHandlerInterface responseHandler;
	V v;
	
	private RequestHelper (Builder<T,V> builder) {
		this.builder = builder;
		init();
	}
	
	private void init(){
		builder.multiStateView.addView(builder.ptr);
		builder.ptr.setPtrHandler(this);
		v =  (V) builder.ptr.getContentView();
		responseHandler = getResponseHandler();
		if(responseHandler == null){
			responseHandler = new TextHttpResponseHandler() {
				
				@Override
				public void onSuccess(int statusCode, Header[] headers, String responseString) {
					
				}
				
				@Override
				public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
					
				}
			};
		}
	}
	
	@Override
	public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
		
		return false;
	}

	@Override
	public void onRefreshBegin(PtrFrameLayout frame) {
		
	}

	@Override
	public ResponseHandlerInterface getResponseHandler() {
		return null;
	}
	

	public static class Builder<T,V extends View> {
		IDataSource dataSource;
//		QuickAdapter<T> adapter;
		IParser<T> parser;
		MultiStateView multiStateView;
		PtrFrameLayout ptr;
		
		public Builder(Context context){
			
		}
		
		public Builder setDataSource(IDataSource dataSource){
			this.dataSource = dataSource;
			return this;
		}
		
		public Builder setParser(IParser<T> parser){
			this.parser = parser;
			return this;
		}
		
		public Builder setContentView(PtrFrameLayout ptr){
			this.ptr = ptr;
			return this;
		}
		
		public Builder setMultiStateView(MultiStateView multiStateView){
			this.multiStateView = multiStateView;
			return this;
		}
		
		public RequestHelper<T,V> build(){
			return new RequestHelper<T,V>(this);
		}
		
		
	}

}
