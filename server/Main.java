package main;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }
    
    public void start(Stage primaryStage) {

        GNTextArea gnText = new GNTextArea();
        gnText.setPrefRowCount(25);
        gnText.setWrapText(true);
        gnText.setEditable(false);
              
        VBox vbox = new VBox();
        vbox.setPadding(new Insets(30, 30, 30, 30));
        vbox.getChildren().add(gnText);
 
        vbox.getChildren().add(new Text());
        vbox.getChildren().add(new Text("SEND A MESSAGE"));
                       
        vbox.getChildren().add(new Text());
        HBox customerBox = new HBox();
        ObservableList<String> customers = FXCollections.observableArrayList("Heaven's Pizza", "Dion's Burgers");
        ComboBox selectCustomer = new ComboBox(customers);
        customerBox.getChildren().add(new Text("TO Customer:         "));
        customerBox.getChildren().add(selectCustomer);
        vbox.getChildren().add(customerBox);

        vbox.getChildren().add(new Text());
        HBox platformBox = new HBox();
        ObservableList<String> platforms = FXCollections.observableArrayList("Facebook", "Twitter", "Instagram");
        ComboBox selectPlatform = new ComboBox(platforms);
        platformBox.getChildren().add(new Text("Platform:                 "));
        platformBox.getChildren().add(selectPlatform);
        vbox.getChildren().add(platformBox);
        
        vbox.getChildren().add(new Text());
        HBox usernameBox = new HBox();
        TextArea username = new TextArea();
        username.setPrefColumnCount(20);
        username.setPrefRowCount(1);
        usernameBox.getChildren().add(new Text("FROM Username:   "));
        usernameBox.getChildren().add(username);
        vbox.getChildren().add(usernameBox);
               
        vbox.getChildren().add(new Text());
        HBox messageBox = new HBox();
        TextArea messageText = new TextArea();
        messageText.setPrefRowCount(5);
        messageBox.getChildren().add(new Text("Message:                "));
        messageBox.getChildren().add(messageText);
        vbox.getChildren().add(messageBox);

        vbox.getChildren().add(new Text());
        Button sendButton = new Button("Send");
        sendButton.setMaxWidth(150);
        vbox.getChildren().add(sendButton);
        
        Scene scene = new Scene(vbox);
        
        primaryStage.setTitle("Get Noticed! Server");
        primaryStage.setMaximized(true);
        primaryStage.setScene(scene);
        primaryStage.show();
        
        TCPListener tcpListener = new TCPListener(gnText);
        tcpListener.start();
        
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent t) {
            	tcpListener.kill();
            }
        });
        
        sendButton.setOnAction(new EventHandler<ActionEvent>() {
        	@Override
        	public void handle(ActionEvent event) {
        		gnText.postMessage(selectCustomer.getValue().toString(), selectPlatform.getValue().toString(), username.getText(), messageText.getText());
        		selectCustomer.getSelectionModel().clearSelection();
        		username.setText("");
        		selectPlatform.getSelectionModel().clearSelection();
        		messageText.setText("");
        	}
        });
    
    }

}
