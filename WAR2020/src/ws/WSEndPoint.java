package ws;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.ejb.LocalBean;
import javax.ejb.Singleton;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import javax.websocket.server.PathParam;

@Singleton
@ServerEndpoint("/ws/{username}")
@LocalBean
public class WSEndPoint {
	static Map<String, Session> sessions = new HashMap<>();
	
	@OnOpen
	public void onOpen(@PathParam("username") String username, Session session) {
		if(!sessions.containsKey(username)) {
			sessions.put(username, session);
		}

	}
	
	@OnMessage
	public void echoTextMessage(String msg) {	
		
		try {
	        for (String s : sessions.keySet()) {
	        	sessions.get(s).getBasicRemote().sendText(msg);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@OnClose
	public void close(@PathParam("username") String username, Session session) {
		sessions.remove(username);
	}
	
	@OnError
	public void error(@PathParam("username") String username, Session session, Throwable t) {
		sessions.remove(username);
		t.printStackTrace();
	}
	
	public void sendMessageToSpecificUser(String username, String message) {
		try {
	        for (String s : sessions.keySet()) {
	        	if(s.equals(username)) {
	        		sessions.get(s).getBasicRemote().sendText(message);
	        		break;
	        	}        	
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void broadcastMessageToAllUsersExceptSender(String username, String message) {
		for(String s : sessions.keySet()) {
			if(!s.equals(username)) {
				try {
					sessions.get(s).getBasicRemote().sendText(message);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

}
