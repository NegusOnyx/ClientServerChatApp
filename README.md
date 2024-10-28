
# Client-Server Chat Application

## Overview
This is a simple Java-based chat application using a client-server architecture. Multiple clients can connect to the server and send messages, which are broadcast to all connected clients. The application also supports private messaging between clients.

## Features
- Real-time messaging between multiple clients.
- Private messaging using the `/w` command (whisper).
- Server-side GUI to monitor connected clients and messages.
- Usernames to identify each client.
- Built using `Socket` programming and multithreading in Java.

## Technologies Used
- **Java SE**: Core language for server and client logic.
- **Swing**: Used for creating the GUI for both the server and client.
- **Socket Programming**: Handles the communication between server and clients.
- **Multithreading**: Supports multiple clients communicating simultaneously.

## Requirements
- Java JDK 8 or above.
- NetBeans IDE (or any preferred Java IDE).

## How to Run the Application

### Server
1. Open the `ChatServer` class located in `src/server/ChatServer.java`.
2. Run the `ChatServer` class.
   - The server will start listening on port `5678` by default and display a GUI to monitor activity.
   - You will see logs in the server window when clients connect or send messages.

### Client
1. Open the `ChatClient` class located in `src/client/ChatClient.java`.
2. Run the `ChatClient` class.
   - A GUI window will prompt the client to enter a username.
   - Once connected, clients can send messages to the chat.
   - Private messages can be sent using the following format:
     ```
     /w <recipient_username> <message>
     ```

## Usage Instructions
- **Broadcast message**: Type your message and press Enter. All connected clients will receive the message.
- **Private message**: Use the `/w` command followed by the recipient's username and the message. Example:
     ```
     /w John Hello, John!
     ```
     This message will only be seen by the client with the username "John."

## Example Usage
- **Broadcast Message**: Simply type a message in the client window and press "Enter." All clients connected to the server will receive the message.
- **Private Message**: Type `/w <recipient_username> <message>` to send a private message to a specific client. For example:

/w John Hello, John!

This message will only be seen by the client with the username "John."

## Future Improvements
- Implement user authentication for enhanced security.
- Improve the GUI design to make it more user-friendly.
- Add features like message history or file sharing between clients.
- Incorporate message encryption for secure communication.

## License
This project is open-source and available under the MIT License.

## Author
- **Larona Thandanani Bongumusa Mavuso**

