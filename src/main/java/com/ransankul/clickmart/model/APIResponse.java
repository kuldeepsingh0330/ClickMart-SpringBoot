package com.ransankul.clickmart.model;

import org.springframework.http.HttpStatus;

public class APIResponse<T> {
	
	private String statusCode;
	private T data;
	private String msg;
	public String getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}
	public T getData() {
		return data;
	}
	public void setData(T data) {
		this.data = data;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public APIResponse() {
		super();
	}
	public APIResponse(String statusCode, T data, String msg) {
		super();
		this.statusCode = statusCode;
		this.data = data;
		this.msg = msg;
	}
	
	
	

}
