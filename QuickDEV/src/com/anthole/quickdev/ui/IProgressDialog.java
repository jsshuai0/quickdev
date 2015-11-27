package com.anthole.quickdev.ui;

import java.io.Serializable;

import com.anthole.quickdev.http.RequestHandle;

public interface IProgressDialog {
	
	public void setContent(RequestBean requestBean);
	
	public void showDialog();
	
	public void dismissDialog();
	
	
	public static class RequestBean implements Serializable{
		/**
		 * 
		 */
		private static final long serialVersionUID = -6202874289713032642L;
		private String content;
		private RequestHandle handle;
		public String getContent() {
			return content;
		}
		public void setContent(String content) {
			this.content = content;
		}
		public RequestHandle getHandle() {
			return handle;
		}
		public void setHandle(RequestHandle handle) {
			this.handle = handle;
		}
	}

}
