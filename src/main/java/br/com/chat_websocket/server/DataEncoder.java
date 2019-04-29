package br.com.chat_websocket.server;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

import com.google.gson.Gson;

import br.com.chat_websocket.domain.DataDTO;

public class DataEncoder<T> implements Encoder.Text<DataDTO<T>> {

	private Gson gson = new Gson();

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void init(EndpointConfig arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public String encode(DataDTO<T> arg0) throws EncodeException {
		return gson.toJson(arg0);
	}

}
