package br.com.chat_websocket;

import java.util.Scanner;

import javax.websocket.DeploymentException;

import org.glassfish.tyrus.server.Server;
import br.com.chat_websocket.server.ChatEndpoint;

public class App {

	public static void main(String[] args) {
		runServer();
	}

	private static void runServer() {
		Server server = new Server("localhost", 8080, "/websocket", null, ChatEndpoint.class);

		try {
			server.start();
			System.out.println("Digite qualquer coisa para finalizar");
			Scanner in = new Scanner(System.in);
			in.nextLine();
			in.close();
		} catch (DeploymentException e) {
			e.printStackTrace();
		} finally {
			server.stop();
		}
	}
}
