package br.com.chat_websocket.server;

import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;
import com.google.gson.Gson;
import br.com.chat_websocket.domain.Message;

public class MessageDecoder implements Decoder.Text<Message> {

	private Gson gson = new Gson();

	@Override
	public Message decode(String msg) throws DecodeException {
		return gson.fromJson(msg, Message.class);
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
	}

	@Override
	public void init(EndpointConfig arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public boolean willDecode(String msg) {
		return (msg != null);
	}

}
