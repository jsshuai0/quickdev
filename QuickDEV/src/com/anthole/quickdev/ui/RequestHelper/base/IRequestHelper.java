package com.anthole.quickdev.ui.RequestHelper.base;

import com.anthole.quickdev.http.ResponseHandlerInterface;

public interface IRequestHelper<H extends ResponseHandlerInterface> {
	
	public H getResponseHandler();
	
	public void setViewState(int state);
	
	public void refresh();
	
}
