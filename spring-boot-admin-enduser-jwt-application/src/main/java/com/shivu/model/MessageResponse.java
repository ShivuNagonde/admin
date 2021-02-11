package com.shivu.model;

public class MessageResponse {

	private String message;

	public MessageResponse(String message) {
		this.setMessage(message);
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
