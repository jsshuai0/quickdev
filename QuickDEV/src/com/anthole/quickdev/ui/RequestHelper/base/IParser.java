package com.anthole.quickdev.ui.RequestHelper.base;

import java.util.List;

/**
 * 数据解析
 * @author 123
 *
 * @param <T>
 */
public interface IParser<T> {
	
	public List<T> parseList(String data);

}
