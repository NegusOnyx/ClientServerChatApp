package server;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.*;
import java.util.HashSet;
import java.util.Set;

public class ChatServer {
    private static Set<ClientHandler> clientHandlers = new HashSet<>();
    private JTextArea messageArea;

    public static void main(String[] args) {
        new ChatServer().startServer();
    }

    public ChatServer() {
        // Create GUI for the server
        JFrame frame = new JFrame("Chat Server");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 400);

        messageArea = new JTextArea();
        messageArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(messageArea);
        frame.add(scrollPane, BorderLayout.CENTER);

        frame.setVisible(true);
    }

    public void startServer() {
        try (ServerSocket serverSocket = new ServerSocket(5678)) { // Changed port to 5678
            updateMessageArea("Server is running on port 5678...");

            while (true) {
                Socket socket = serverSocket.accept();
                updateMessageArea("New client connected...");
                ClientHandler clientHandler = new ClientHandler(socket);
                clientHandlers.add(clientHandler);
                new Thread(clientHandler).start();
            }
        } catch (IOException e) {
            updateMessageArea("Server error: " + e.getMessage());
        }
    }

    private void updateMessageArea(String message) {
        messageArea.append(message + "\n");
    }

    // Broadcast message to all clients
    public static void broadcastMessage(String message, ClientHandler sender) {
        for (ClientHandler client : clientHandlers) {
            client.sendMessage(message); // Send message to all clients including the sender
        }
    }

    // Send a private message
    public static void privateMessage(String message, String recipient, ClientHandler sender) {
        boolean recipientFound = false;
        for (ClientHandler client : clientHandlers) {
            if (client.getUsername().equals(recipient)) {
                client.sendMessage("[Private] " + sender.getUsername() + ": " + message);
                sender.sendMessage("[Private to " + recipient + "] " + message); // Confirmation to sender
                recipientFound = true;
                break;
            }
        }
        if (!recipientFound) {
            sender.sendMessage("User " + recipient + " not found.");
        }
    }

    public static void removeClient(ClientHandler clientHandler) {
        clientHandlers.remove(clientHandler);
    }

    public class ClientHandler implements Runnable {
        private Socket socket;
        private PrintWriter writer;
        private BufferedReader reader;
        private String username;

        public ClientHandler(Socket socket) {
            this.socket = socket;
            try {
                writer = new PrintWriter(socket.getOutputStream(), true);
                reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                // Read the username from the client
                this.username = reader.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public String getUsername() {
            return this.username;
        }

        public void sendMessage(String message) {
            writer.println(message);
        }

        @Override
        public void run() {
            String message;
            try {
                while ((message = reader.readLine()) != null) {
                    // Handle the messages, broadcast, or private
                    if (message.startsWith("/w ")) {
                        String[] tokens = message.split(" ", 3);
                        if (tokens.length == 3) {
                            ChatServer.privateMessage(tokens[2], tokens[1], this);
                        }
                    } else {
                        ChatServer.broadcastMessage(username + ": " + message, this);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
