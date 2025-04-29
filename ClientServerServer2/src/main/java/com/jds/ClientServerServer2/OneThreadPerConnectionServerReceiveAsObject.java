package com.jds.ClientServerServer2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class OneThreadPerConnectionServerReceiveAsObject {
    private static final int PORT = 8080;
    Logger logger = LoggerFactory.getLogger(OneThreadPerConnectionServerReceiveAsObject.class);

    public void startServer() throws Exception {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            logger.info("Server started on port {}", PORT);
            while (!serverSocket.isClosed()) {
                try {
                    Socket newClient = serverSocket.accept();
                    logger.info("New client connected: {}", newClient.getInetAddress());
                    ClientConnection clientConnection = new ClientConnection(newClient);
                    new oneThreadPerConnectionReceiveAsObject(clientConnection).start();
                } catch (IOException e) {
                    logger.error("Error accepting connection", e);
                }
            }
        } catch (IOException e) {
            logger.error("Error starting server", e);
        }
    }

}
