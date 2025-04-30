package com.jds.ClientServerClient2;
/* *******************************************************************************************
 * Client side of the App
 * It can send data to the server side
 *  - as simple text
 *  - as a json object from class MyObject01
 * *****************************************************************************************/
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class ClientPartSendObject {
    // the logger
    Logger logger = LoggerFactory.getLogger(ClientPartSendObject.class);

    // server connection information
    private String SERVER_ADDRESS;
    private int SERVER_PORT;

    /********************************************************************************************
     * Client side of the App
     *  - Data created and is sent as a json object from class MyObject01
     *  so the object is converted to a Json string before sending the string
     * *****************************************************************************************/

    public ClientPartSendObject(){
        PropertiesOfTheApp prop = new PropertiesOfTheApp();
        prop.checkIfPropertiesFileExistOrCreateTheDefaultFile();
        SERVER_PORT = prop.getPortPropertiesForTheApp();
        SERVER_ADDRESS = prop.getServerAdressFromProperties();
    }
     void demo_sendingObjectToServer() {
        try {

            MyObject01 message1 = new MyObject01("Lili", 17 );
            MyObject01 message2 = new MyObject01("Aniss",22);
            MyObject01 message3 = new MyObject01("Mary",23);

            sendObject03(message1);
            sendObject03(message2);
            sendObject03(message3);
        } catch (Exception e) {
            System.out.println("We got an error in demo02 sending data to the server: " + e.getMessage() + e.getStackTrace());
        }
    }

    /*****************************************************************
     * Method for sending the data in the socket to the server
     * ***************************************************************/
    void sendObject03(MyObject01 mydata) throws Exception {


            try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
                 PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
                 BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

                Gson gson = new Gson();
                String json = gson.toJson(mydata);

                writer.println(json);

                String response = reader.readLine();
                System.out.println("Expected server answer : HTTP/1.1 200 OK - Processed request: ");
                System.out.println("Received server answer : "+ response);

        }
    }


/********************************************************************
 * unused at the moment
 * *******************************************************************/
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

}
