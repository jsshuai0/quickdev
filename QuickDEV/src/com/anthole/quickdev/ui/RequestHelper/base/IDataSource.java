package com.anthole.quickdev.ui.RequestHelper.base;

import com.anthole.quickdev.http.RequestParams;
import com.anthole.quickdev.http.TextHttpResponseHandler;

/**
 * 数据源
 * @author 123
 *
 */
public interface IDataSource<T extends TextHttpResponseHandler> {
	
	public void request(T responseHandler);
	
	public void request(RequestParams params,T responseHandler);
	
	public void requestPage(int page, T responseHandler);
	
}
