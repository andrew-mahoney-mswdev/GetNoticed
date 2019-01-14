package main;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

import javafx.scene.control.TextArea;

public class GNTextArea extends TextArea {
	
	private void appendTextln() {
		appendText("\n");
	}
	
	private void appendTextln(String message) {
		appendText(message + "\n");
	}
	
	public void postJob(String message) {
		appendTextln("INCOMING");
		appendTextln("Company: Heaven's Pizza");
		appendTextln("Facebook: https://www.facebook.com/HeavenWoodfirePizza");
		appendTextln("Login: max@heavenspizza.co.nz | Ds3sT62s");
		appendTextln("Twitter: -");
		appendTextln("Instagram: -");
		appendTextln("Message:");
		appendTextln(message);
		appendTextln();
	}

	public void postMessage(String customer, String platform, String username, String message) {
		appendTextln("OUTGOING");
		appendTextln("Customer: " + customer);
		appendTextln("Platform: " + platform);
		appendTextln("Username: " + username);
		appendTextln("Message: ");
		appendTextln(message);
		appendTextln();
		TCPListener.sendTCP("FROM: " + username + "\n" + message);
	}

}
