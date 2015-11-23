package com.anthole.quickdev.ui.RequestHelper;

import com.anthole.quickdev.http.ResponseHandlerInterface;

/**
 * 数据源
 * @author 123
 *
 */
public interface IDataSource {
	
	public void request(ResponseHandlerInterface responseHandler);
	
	public void requestPage(int page,ResponseHandlerInterface responseHandler);

}
