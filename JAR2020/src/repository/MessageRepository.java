package repository;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.LocalBean;
import javax.ejb.Singleton;

import model.Message;
@Singleton
@LocalBean
public class MessageRepository {
	private HashMap<String, ArrayList<Message>> messages = new HashMap<>();
	
	public MessageRepository() {
		messages = new HashMap<String, ArrayList<Message>>();
	}
	
	public ArrayList<Message> messagesForUser(String username){
		return messages.get(username);
	}
	
	public void addNewMessage(String username, Message message) {
		ArrayList<Message> arrayMessages = messages.get(username);
		if(arrayMessages == null) {
			messages.put(username, new ArrayList<Message>());
		}
		if(messages.get(username) == null)
			messages.put(username, new ArrayList<Message>());
		messages.get(username).add(message);
		if(messages.get(message.getReceiver().getUsername()) == null)
			messages.put(message.getReceiver().getUsername(), new ArrayList<Message>());
		messages.get(message.getReceiver().getUsername()).add(message);
	}
	
	public void addBroadcastedMessageFromUser(String username, Message message) {
		ArrayList<Message> arrayMessages = messages.get(username);
		if(arrayMessages == null) {
			messages.put(username, new ArrayList<Message>());
		}
		for(String uname : messages.keySet()) {
			messages.get(uname).add(message);
		}
	}
	
	@SuppressWarnings("unchecked")
	@PostConstruct
	public void readMessagesFromAFile() {
		try {
			FileInputStream fileInput = new FileInputStream("messages.txt");
			ObjectInputStream in = new ObjectInputStream(fileInput);
			messages = (HashMap<String, ArrayList<Message>>) in.readObject();
			in.close();
			fileInput.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			saveMessagesToAFile();
			e.printStackTrace();
		}
	}
	
	@PreDestroy
	public void saveMessagesToAFile() {
		try {
	         FileOutputStream fileOut =
	         new FileOutputStream("messages.txt");
	         ObjectOutputStream out = new ObjectOutputStream(fileOut);
	         out.writeObject(messages);
	         out.close();
	         fileOut.close();
	      } catch (IOException i) {
	         i.printStackTrace();
	      }
	}

}
