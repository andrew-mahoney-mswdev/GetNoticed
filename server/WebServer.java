package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class WebServer {
	private ServerSocket serverSocket;
	
	WebServer() {
		try {
			serverSocket = new ServerSocket(80);
		} catch (IOException e) {e.printStackTrace();}		
	}
	
	public String getTCP() {
		String message = "";

		try {
			Socket socket = serverSocket.accept();
			BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			message = in.readLine();
			System.out.println("RECEIVED FROM " + socket.getInetAddress().toString() + ":" + socket.getPort() + ": " + message);
			in.close();
			socket.close();
		} catch (IOException e) {e.printStackTrace();}
		
		return message;
	}
	
	public void sendTCP(String message) throws IOException {
		Socket socket = new Socket("***.***.***.***", 7801);
		PrintWriter out = new PrintWriter(socket.getOutputStream());
		System.out.println("SENDING: " + message);
		out.write(message);
		out.flush();
		out.close();
		socket.close();
	}

}
