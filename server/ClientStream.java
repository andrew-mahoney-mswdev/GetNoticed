package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class ClientStream {
	public Socket socket;
	public BufferedReader in;
	public PrintWriter out;

	public void accept(ServerSocket serverSocket) {
		try {
			socket = serverSocket.accept();
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new PrintWriter(socket.getOutputStream());
		} catch (IOException e) {e.printStackTrace();}
	}
	
	public String getPacket() {
		try {
			return in.readLine();
		} catch (IOException e) {e.printStackTrace();}
		return null;
	}

	public void sendPacket(String message) {
		out.write(message);
		out.flush();
	}

	public void close() {
		try {
			out.close();
			in.close();
			socket.close();
		} catch (IOException e) {e.printStackTrace();}
	}
	
	public String getAddress() {return socket.getInetAddress().toString();}
	public int getPort() {return socket.getPort();}
}
