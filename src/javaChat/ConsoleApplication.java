/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaChat;

import java.io.IOException;
import javaChat.ClientConnection;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Serlii R. Crusita
 */
public class ConsoleApplication {
    
    private ClientConnection client;
    public class ReadInput extends Thread{
        public void run() {
            try {
                String inputKeyboard;
                do {
                    System.out.println(">>");
                    inputKeyboard = client.inputString();
                    client.writeStream(inputKeyboard);
                }while(! inputKeyboard.equals("Quit"));
                client.disconnect();
            }catch(IOException E){
                System.out.println("Error");
            }
        }
    }
    
    
    
    public class WriteOutput extends Thread{
        public void run(){
            try {
                String inputan;
                while((inputan = client.readStream()) != null){
                    System.out.println(inputan);
                    System.out.println(">>");
                }
            }catch (IOException E){
                System.out.println("Error");
            }
        }
    }
    
    public void startChat(){
        try {
            client = new ClientConnection();
            System.out.println("Input server IP :");
            String ip = client.inputString();
            client.connect(ip);
            ReadInput in = new ReadInput();
            WriteOutput out = new WriteOutput();
            in.start();
            out.start();
        }
        catch(IOException E){
            System.out.println("Error");
        }
    }
    
}
