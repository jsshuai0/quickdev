package com.anthole.quickdev.ui.RequestHelper;

/**
 * 数据解析
 * @author 123
 *
 * @param <T>
 */
public interface IParser<T> {
	
	public T parse(String data);

}
