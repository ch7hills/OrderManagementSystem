package com.order.model;

import java.util.Date;

public class Response {
	private Date timestamp;
	private Status status;
	private Object data;

	public Response(Date timestamp, Status status, Object data) {
		super();
		this.timestamp = timestamp;
		this.status = status;
		this.data = data;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	
	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "Response [timestamp=" + timestamp + ", status=" + status + ", data=" + data + "]";
	}
}
