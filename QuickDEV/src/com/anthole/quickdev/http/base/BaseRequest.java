package com.anthole.quickdev.http.base;

import com.anthole.quickdev.http.RequestParams;
import com.anthole.quickdev.http.ResponseHandlerInterface;

public interface BaseRequest {
	 

	/**
	 * 开始请求
	 * 
	 * @param responseHandler
	 */
	public void request(ResponseHandlerInterface responseHandler);

	/**
	 * 开始请求
	 * 
	 * @param params
	 * @param responseHandler
	 */
	public void request(RequestParams params, ResponseHandlerInterface responseHandler);

	public boolean cancel(boolean mayInterruptIfRunning);

	public String getHost();

	/**
	 * 获取请求方法 get post
	 * 
	 * @return
	 */
	public HttpMethod getHttpMethod();

	/**
	 * 获取请求参数
	 * 
	 * @return
	 */
	public RequestParams getParams();

	public enum HttpMethod {
		POST, GET
	}

}
