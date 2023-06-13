package com.example.udpreciever;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class UDPMessageReceiver extends Application {

    private static final int BUFFER_SIZE = 1024*10;
    private static final int UDP_PORT = 9001;

    private TextArea messageArea;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("UDP Message Receiver");

        messageArea = new TextArea();
        messageArea.setEditable(false);

        VBox vbox = new VBox(messageArea);
        Scene scene = new Scene(vbox, 800, 300);

        primaryStage.setScene(scene);
        primaryStage.show();

        startMessageReceiver();
    }

    private boolean isReceivingMessages = true;

    private void startMessageReceiver() {
        new Thread(() -> {
            try {
                DatagramSocket socket = new DatagramSocket(UDP_PORT);

                byte[] buffer = new byte[BUFFER_SIZE];
                DatagramPacket packet = new DatagramPacket(buffer, BUFFER_SIZE);

                while (isReceivingMessages) {
                    socket.receive(packet);

                    String json = new String(packet.getData(), 0, packet.getLength());
                    displayMessage(json);

                    packet.setLength(BUFFER_SIZE); // Reset the packet length for the next receive
                }

                socket.close(); // Close the socket when done receiving
            } catch (Exception e) {
                displayMessage("Error receiving UDP message: " + e.getMessage());
            }
        }).start();
    }

    private void displayMessage(String message) {
        Platform.runLater(() -> {
            messageArea.appendText(message + "\n");
        });
    }


    public static void main(String[] args) {
        launch(args);
    }
}
