package br.com.chat_websocket.domain;

public class DataDTO<T> {

	private Type type;
	private T data;

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

}
