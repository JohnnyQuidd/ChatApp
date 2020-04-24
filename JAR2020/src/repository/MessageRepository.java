package repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

import model.Message;

public class MessageRepository {
	private HashMap<String, ArrayList<Message>> messages = new HashMap<>();
	private UserRepository userRepo = new UserRepository();
	
	public MessageRepository() {
		ArrayList<Message> message = new ArrayList<Message>();
		message.add(new Message(userRepo.findUserByUsername("admin"), userRepo.findUserByUsername("guest"),
				"Welcome", LocalDateTime.now(), "Welcome to our new Chat App"));
		messages.put("guest", message);
	}
	
	public ArrayList<Message> messagesForUser(String username){
		return messages.get(username);
	}
	
	public void addNewMessage(String username, Message message) {
		ArrayList<Message> arrayMessages = messages.get(username);
		if(arrayMessages == null) {
			messages.put(username, new ArrayList<Message>());
		}
		messages.get(username).add(message);
	}

}
