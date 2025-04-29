package com.jds.ClientServerClient2;
/********************************************************************************************
 * To transmit an object via a SocketChannel, we’ll serialize it into a byte array and
 * wrap it in a ByteBuffer.
 * Before sending the serialized data, we also prepend a 4-byte integer to indicate the length of the byte array.
 *
 * This ensures the receiver knows how many bytes to read for the full object:
 * *****************************************************************************************/
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class ClientPart {
    Logger logger = LoggerFactory.getLogger(ClientPart.class);
    private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 8080;

    void demo01(){
        try (SocketChannel socketChannel = SocketChannel.open()) {
            socketChannel.connect(new InetSocketAddress(SERVER_ADDRESS, SERVER_PORT));
            //MyObject01 objectToSend = new MyObject01("Florence", 25);
            String monmessage="Votre commande a ete transmise aux cuisines";
            sendObject(socketChannel, monmessage); // Serialize and send
        } catch (IOException e) {
            // handle exception
            System.out.println("We got an error on the ClientPart:"+e.getMessage() );
            System.out.println(e.getStackTrace());
        }
    }

    void demo_sendingStringToServer() {
        try {

            String message1 = "Your order was accepted";
            String message2 = "Your meeting was accepted";
            String message3 = "Your car was fixed";

            sendObject03(message1);
            sendObject03(message2);
            sendObject03(message3);
        } catch (Exception e) {
            System.out.println("We got an error in demo02 sending data to the server: " + e.getMessage() + e.getStackTrace());
        }
    }

    void sendObject03(String message) throws Exception {

        for (int i = 1; i <= 3; i++) {
            try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
                 PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
                 BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
                String request = "Request " + i + " -- " + message;
                writer.println(request);
                String response = reader.readLine();
                System.out.println("Expected server answer : HTTP/1.1 200 OK - Processed request: ");
                System.out.println("Received server answer : "+ response);
            }
        }
    }

    void demo02() {
        try {
            sendObject02();
        } catch (Exception e) {
            System.out.println("We got an error in demo02 sending data to the server: " + e.getMessage() + e.getStackTrace());
        }
    }
    void sendObject02() throws Exception {

        for (int i = 1; i <= 3; i++) {
            try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
                 PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
                 BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
                String request = "Request " + i;
                writer.println(request);
                String response = reader.readLine();
                System.out.println("Expected server answer : HTTP/1.1 200 OK - Processed request: ");
                System.out.println("Received server answer : "+ response);
            }
        }
    }
    void sendObject(SocketChannel channel, String message) {
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        try (ObjectOutputStream objOut = new ObjectOutputStream(byteStream)) {
            objOut.writeObject(message);
        }catch (IOException e) {
            // handle exception
            System.out.println("We got an error on the ClientPart:"+e.getMessage() );
            System.out.println(e.getStackTrace());
        }
        byte[] bytes = byteStream.toByteArray();

        ByteBuffer lengthBuffer = ByteBuffer.allocate(4);
        lengthBuffer.putInt(bytes.length);
        lengthBuffer.flip();
        while (lengthBuffer.hasRemaining()) {
            try {
                channel.write(lengthBuffer);
            } catch (IOException e) {

                System.out.println("We got an error on the ClientPart:"+e.getMessage() );
                System.out.println(e.getStackTrace());
            }
        }

        ByteBuffer dataBuffer = ByteBuffer.wrap(bytes);
        while (dataBuffer.hasRemaining()) {
            try {
                channel.write(dataBuffer);
            } catch (IOException e) {
                // handle exception
                System.out.println("We got an error on the ClientPart:"+e.getMessage() );
               System.out.println(e.getStackTrace());
            }
        }
    }


    public void startClientPart(){
        try (SocketChannel socketChannel = SocketChannel.open()) {
            socketChannel.connect(new InetSocketAddress(SERVER_ADDRESS, SERVER_PORT));
            logger.info("Connected to the server...");
            // To send object here
            demo01();
        } catch (IOException e) {
            // handle exception
            System.out.println("We got an error on the ClientPart:"+e.getMessage() );
            System.out.println(e.getStackTrace());
        }
    }
}
