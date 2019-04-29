package br.com.chat_websocket.server;

import java.io.IOException;
import java.util.HashMap;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import javax.websocket.EncodeException;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import br.com.chat_websocket.domain.DataDTO;
import br.com.chat_websocket.domain.Message;
import br.com.chat_websocket.domain.Type;

@ServerEndpoint(value = "/chat/{username}", encoders = DataEncoder.class, decoders = MessageDecoder.class)
public class ChatEndpoint {

	private Session session;
	private static Set<ChatEndpoint> chatEndpoints = new CopyOnWriteArraySet<>();
	private static HashMap<String, String> users = new HashMap<>();

	@OnOpen
	public void onOpen(Session session, @PathParam("username") String username) {
		this.session = session;
		chatEndpoints.add(this);
		users.put(session.getId(), username);

		DataDTO<Object> users = new DataDTO<>();
		users.setType(Type.USERS_CONNECTED);
		users.setData(ChatEndpoint.users);

		broadcast(users);
	}

	@OnMessage
	public void onMessage(Session session, Message msg) {
		DataDTO<Message> messageDTO = new DataDTO<>();
		messageDTO.setType(Type.MESSAGE);
		messageDTO.setData(msg);

		broadcast(messageDTO);
	}

	@OnClose
	public void onClose(Session session) {
		chatEndpoints.remove(this);
		Message message = new Message(users.get(session.getId()), "Desconectado");
		// TODO
	}

	private void broadcast(DataDTO<?> msg) {
		chatEndpoints.forEach(endPoint -> {
			synchronized (endPoint) {
				try {
					endPoint.session.getBasicRemote().sendObject(msg);
				} catch (IOException | EncodeException e) {
					e.printStackTrace();
				}
			}
		});
	}
}
