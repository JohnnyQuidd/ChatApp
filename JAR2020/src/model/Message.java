package model;

import java.io.Serializable;
import java.time.LocalDateTime;

@SuppressWarnings("serial")
public class Message implements Serializable{
	private User sender;
	private User receiver;
	private String subject;
	private LocalDateTime time;
	private String content;
	
	public Message() {}

	public Message(User sender, User receiver, String subject, LocalDateTime time, String content) {
		super();
		this.sender = sender;
		this.receiver = receiver;
		this.subject = subject;
		this.time = time;
		this.content = content;
	}

	public User getSender() {
		return sender;
	}

	public void setSender(User sender) {
		this.sender = sender;
	}

	public User getReceiver() {
		return receiver;
	}

	public void setReceiver(User receiver) {
		this.receiver = receiver;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public LocalDateTime getTime() {
		return time;
	}

	public void setTime(LocalDateTime time) {
		this.time = time;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
}
