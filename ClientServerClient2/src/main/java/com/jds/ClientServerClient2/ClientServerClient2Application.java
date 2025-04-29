package com.jds.ClientServerClient2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ClientServerClient2Application {

	void sendSimpleTypes(){
		ClientPart clt = new ClientPart();
		clt.demo01();
		//clt.demo02();
		//clt.demo_sendingStringToServer();

	}

	static void sendingObjectsToServer(){
		ClientPartSendObject cltobj = new ClientPartSendObject();
		cltobj.demo_sendingObjectToServer();
	}

	public static void main(String[] args) {
		sendingObjectsToServer();
		//SpringApplication.run(ClientServerClient2Application.class, args);
	}

}
