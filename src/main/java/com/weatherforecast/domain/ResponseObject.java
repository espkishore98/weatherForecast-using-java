package com.weatherforecast.domain;
import org.springframework.http.HttpStatus;


public class ResponseObject {

	private Object response;
	private String message;
	private Object status;

	public ResponseObject(Object response, String message, HttpStatus status) {
		super();
		this.response = response;
		this.message = message;
		this.status = status;
	}

	public Object getResponse() {
		return response;
	}

	public String getMessage() {
		return message;
	}

	public int getStatus() {
		if (this.status instanceof HttpStatus) {
			return ((HttpStatus) this.status).value();
		} else {
			return (Integer) this.status;
		}
	}

	@Override
	public String toString() {
		return "ResponseObject [response=" + response + ", message=" + message + ", status=" + status + "]";
	}

}