package com.jds.ClientServerServer2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class oneThreadPerConnection extends Thread {
    ClientConnection clientConnection;
    private Logger logger = LoggerFactory.getLogger(oneThreadPerConnection.class);

    public oneThreadPerConnection(ClientConnection cltcnx){
        this.clientConnection = cltcnx;
    }

    @Override
    public void run() {
        try (ClientConnection client = this.clientConnection) {
            String request;
            while ((request = client.getReader().readLine()) != null) {
                Thread.sleep(1000); // simulate server doing work
                logger.info("Server receiving and Processing request: {}", request);
                clientConnection.getWriter().println("HTTP/1.1 200 OK - Processed request: " + request);
                //logger.info("LOGGER INFO: Processed request: {}", request);
            }
        } catch (Exception e) {
            logger.error("Error processing request", e);
        }
    }

}
