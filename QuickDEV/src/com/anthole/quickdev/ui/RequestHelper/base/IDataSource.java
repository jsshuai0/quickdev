package com.anthole.quickdev.ui.RequestHelper.base;

import com.anthole.quickdev.http.TextHttpResponseHandler;

/**
 * 数据源
 * @author 123
 *
 */
public interface IDataSource {
	
	public void request(TextHttpResponseHandler responseHandler);
	
	public void requestPage(int page, TextHttpResponseHandler responseHandler);
	
}
