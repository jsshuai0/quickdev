package com.anthole.quickdev.ui.RequestHelper.base;

import com.anthole.quickdev.http.TextHttpResponseHandler;

public interface IRequestHelper {
	
	public TextHttpResponseHandler getResponseHandler();
	
	public void refresh();
	
}
