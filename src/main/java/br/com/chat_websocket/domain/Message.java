package br.com.chat_websocket.domain;

public class Message {

	private String from;
	private String to;
	private String content;

	public Message(String from, String content) {
		this.from = from;
		this.content = content;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getFrom() {
		return from;
	}

	public String getTo() {
		return to;
	}

	public String getContent() {
		return content;
	}
}
