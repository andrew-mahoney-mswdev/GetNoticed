package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.HashMap;

public class TCPListener extends Thread {
	private boolean running = true;
	private GNTextArea text;

	TCPListener(GNTextArea _text) {
		text = _text;
	}
	
	public void run() {
		try {	
			ServerSocket serverSocket = new ServerSocket(80);
			System.out.println("WAITING ON: " + InetAddress.getLocalHost().getHostAddress() + ":" + serverSocket.getLocalPort());
			serverSocket.setSoTimeout(100);
			
			while (running == true) {
				try {
					Socket socket = serverSocket.accept();
					BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
					String message = in.readLine();
					System.out.println("RECEIVED FROM " + socket.getInetAddress().toString() + ":" + socket.getPort() + ": " + message);
					text.postJob(message);
					in.close();
					socket.close();
				} catch (SocketTimeoutException e) {}
			}
			
			serverSocket.close();
		} catch (IOException e) {e.printStackTrace();}
	}

	public void kill() {
		running = false;
	}
	
	public static void sendTCP(String message) {
		try {
			Socket socket = new Socket("192.168.2.64", 7801);//("10.140.51.33", 7801);
			PrintWriter out = new PrintWriter(socket.getOutputStream());
			System.out.println("SENDING: " + message);
			out.write(message);
			out.flush();
			out.close();
			socket.close();
		} catch (IOException e) {e.printStackTrace();}
	}

}
