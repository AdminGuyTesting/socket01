package com.jds.ClientServerServer2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ClientServerServer2Application   {

	static void demo_getSimpleData() throws Exception {
		OneThreadPerConnectionServer oneThreadPerConnectionServer = new OneThreadPerConnectionServer();
		oneThreadPerConnectionServer.startServer();
	}

	static void demo_getObject() throws Exception  {
	OneThreadPerConnectionServerReceiveAsObject ot = new OneThreadPerConnectionServerReceiveAsObject();
	ot.startServer();
	}

	public static void main(String[] args) throws Exception  {
		demo_getObject();

		//SpringApplication.run(ClientServerServer2Application.class, args);
	}

}
