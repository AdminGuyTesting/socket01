package com.jds.ClientServerClient2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

public class PropertiesOfTheApp {
    static String SERVER_INFO = "client_information.ini";
    private static Logger logger = LoggerFactory.getLogger(PropertiesOfTheApp.class);

    public void checkIfPropertiesFileExistOrCreateTheDefaultFile(){
        // Specify the file path
        String filePath = SERVER_INFO;

        // Create a Path object for the specified file
        Path path = FileSystems.getDefault().getPath(filePath);

        // Check if the file exists
        if (Files.exists(path)) {
            logger.info("Property File "+ SERVER_INFO + " Exists. Information will be used");
        } else {
            System.out.println("File does not Exist, creating the default " + SERVER_INFO + " file with default information");
            createDefaultPropertiesFile();
        }
    }

    public void createDefaultPropertiesFile() {
        try (OutputStream output = new FileOutputStream(SERVER_INFO)) {

            Properties prop = new Properties();

            // set the properties value
            prop.setProperty("SERVER", "localhost");
            prop.setProperty("PORT", "8080");
            // save properties to project root folder
            prop.store(output, null);
            System.out.println(prop);
        } catch (IOException io) {
            io.printStackTrace();
        }
    }

    public int getPortPropertiesForTheApp(){
        int theport= 0 ;
        try (InputStream input = new FileInputStream(SERVER_INFO)) {

            Properties prop = new Properties();

            // load a properties file
            prop.load(input);

            // get the property value and print it out
            return Integer.parseInt(prop.getProperty("PORT"));

        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return theport;
    }

    public String getServerAdressFromProperties(){
        String myserver = "NOT FOUND" ;
        try (InputStream input = new FileInputStream(SERVER_INFO)) {

            Properties prop = new Properties();
            // load a properties file
            prop.load(input);
            // get the property value and print it out
            return prop.getProperty("SERVER");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return myserver;
    }
}
