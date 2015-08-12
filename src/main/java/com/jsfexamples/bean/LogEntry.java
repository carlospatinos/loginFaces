package com.jsfexamples.bean;

import java.io.Serializable;

public class LogEntry implements Serializable {

	private static final long serialVersionUID = 8207031487567124388L;
	private String date;
	private String message;
	private String level;
	
	public LogEntry(String date, String message, String level) {
		this.date = date;
		this.message = message;
		this.level = level;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}
}
