package beans;

import java.time.LocalDateTime;
import java.util.ArrayList;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import dto.MessageDTO;
import model.Message;
import model.User;
import repository.MessageRepository;
import repository.UserRepository;
import ws.WSEndPoint;

@Stateless
@Path("/messages")
@LocalBean
public class MessageBean {
	private UserRepository userRepo = new UserRepository();
	private MessageRepository messageRepo = new MessageRepository();
	@EJB
	private WSEndPoint ws;
	
	@GET
	@Path("/{user}")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Message> listOfMessagesForUser(@PathParam("user") String username) {
		return messageRepo.messagesForUser(username);
	}
	
	@POST
	@Path("/{user}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response sendMessageToUser(MessageDTO messageDTO, @Context HttpServletRequest request) {
		Message message = formMessageOutOfDTO(messageDTO, request);
		String username = messageDTO.getReceiverUsername();
		ws.sendMessageToSpecificUser(username, message.getContent());
		messageRepo.addNewMessage(messageDTO.getReceiverUsername(), message);
		
		return Response.status(200).build();
	}
	
	@POST
	@Path("/all")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response sendMessageToAllUsers(MessageDTO messageDTO, @Context HttpServletRequest request) {
		Message message = formMessageOutOfDTO(messageDTO, request);
		ws.echoTextMessage(message.getContent());
		return Response.status(200).build();
	}
	
	private Message formMessageOutOfDTO(MessageDTO messageDTO, HttpServletRequest request) {
		String senderUsername = (String) request.getSession().getAttribute("username");
		User sender = userRepo.findUserByUsername(senderUsername);
		User receiver = userRepo.findUserByUsername(messageDTO.getReceiverUsername());
		LocalDateTime dateTime = LocalDateTime.now();
		
		Message message = new Message(sender, receiver, messageDTO.getSubject(), dateTime, messageDTO.getContent());
		return message;
	}
}
