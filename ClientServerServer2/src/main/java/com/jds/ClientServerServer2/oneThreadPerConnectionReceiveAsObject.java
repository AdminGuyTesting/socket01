package com.jds.ClientServerServer2;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class oneThreadPerConnectionReceiveAsObject extends Thread {
    ClientConnection clientConnection;
    private Logger logger = LoggerFactory.getLogger(oneThreadPerConnectionReceiveAsObject.class);

    public oneThreadPerConnectionReceiveAsObject(ClientConnection cltcnx){
        this.clientConnection = cltcnx;
    }

    @Override
    public void run() {
        try (ClientConnection client = this.clientConnection) {
            String request;
            Gson gson = new GsonBuilder().setPrettyPrinting().create();


            while ((request = client.getReader().readLine()) != null) {
                Thread.sleep(1000); // simulate server doing work

                MyObject01 mo = gson.fromJson(request, MyObject01.class);

                logger.info("Server receiving and Processing request for : {} {}", mo.getName(),mo.getAge());
                clientConnection.getWriter().println("HTTP/1.1 200 OK - Processed request: " + request);
                logger.info("LOGGER INFO: Processed request: {}", request);
            }
        } catch (Exception e) {
            logger.error("Error processing request", e);
        }
    }

    MyObject01 receiveObject(SocketChannel channel) throws IOException, ClassNotFoundException {
        ByteBuffer lengthBuffer = ByteBuffer.allocate(4);
        while (lengthBuffer.hasRemaining()) {
            if (channel.read(lengthBuffer) == -1) {
                throw new EOFException("Connection closed prematurely");
            }
        }
        lengthBuffer.flip();
        int length = lengthBuffer.getInt();

        // Read exactly 'length' bytes
        ByteBuffer dataBuffer = ByteBuffer.allocate(length);
        while (dataBuffer.hasRemaining()) {
            if (channel.read(dataBuffer) == -1) {
                throw new EOFException("Incomplete data received");
            }
        }
        dataBuffer.flip();

        byte[] bytes = new byte[length];
        dataBuffer.get(bytes);
        try (ObjectInputStream objIn = new ObjectInputStream(new ByteArrayInputStream(bytes))) {
            return (MyObject01) objIn.readObject();
        }
    }


}
