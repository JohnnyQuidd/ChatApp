package dto;

public class MessageDTO {
	private String receiverUsername;
	private String subject;
	private String content;
	private String senderUsername;
	
	public MessageDTO(String receiverUsername, String subject, String content, String senderUsername) {
		super();
		this.receiverUsername = receiverUsername;
		this.subject = subject;
		this.content = content;
		this.senderUsername = senderUsername;
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

	public String getSenderUsername() {
		return senderUsername;
	}

	public void setSenderUsername(String senderUsername) {
		this.senderUsername = senderUsername;
	}
	
	
	
	
}
