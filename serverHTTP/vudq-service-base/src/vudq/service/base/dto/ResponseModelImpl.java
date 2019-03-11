package vudq.service.base.dto;

import java.io.Serializable;

public class ResponseModelImpl<T> implements ResponseModel,Serializable {
	public boolean success;
	public String message;
	public T data;
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public T getData() {
		return data;
	}
	public void setData(T data) {
		this.data = data;
	}

	
}
