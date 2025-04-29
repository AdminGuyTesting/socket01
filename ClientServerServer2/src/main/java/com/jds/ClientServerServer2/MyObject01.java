package com.jds.ClientServerServer2;
/******************************
 * https://www.baeldung.com/java-send-receive-serialized-object-in-socket-channel
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
