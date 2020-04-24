package dto;

public class MessageDTO {
	private String receiverUsername;
	private String subject;
	private String content;
	
	public MessageDTO(String receiverUsername, String subject, String content) {
		super();
		this.receiverUsername = receiverUsername;
		this.subject = subject;
		this.content = content;
	}
	
	public MessageDTO() {}

	public String getReceiverUsername() {
		return receiverUsername;
	}

	public void setReceiverUsername(String receiverUsername) {
		this.receiverUsername = receiverUsername;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	
}
