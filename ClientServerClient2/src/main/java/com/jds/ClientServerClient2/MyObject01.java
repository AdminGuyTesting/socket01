package com.jds.ClientServerClient2;
/******************************
 * 20250429- Reading article: https://www.baeldung.com/java-send-receive-serialized-object-in-socket-channel
 * This is a simple class that represents the object to be sent to the server side
 * *********************************************/
import java.io.Serializable;

public class MyObject01 implements Serializable {
    private String name;
    private int age;

    public MyObject01(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String toString(){
        return "Now we know this person: " + this.name + " and he/she is " + this.age+ " years of age";
    }
}
