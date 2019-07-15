/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author Mehul
 */
public class JavaApplication3 {
    static BufferedReader br;
    static InputStreamReader isr;
    static String message;
    static Socket s;
    static ServerSocket ss;
    static DatabaseConnector db;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
       
        // TODO code application logic here
        try{
            ss = new ServerSocket(7800);
            while(true){
                s = ss.accept();
                
                isr = new InputStreamReader(s.getInputStream());
                br = new BufferedReader(isr);
                message = br.readLine();
                System.out.println(message);
                System.out.println(message.charAt(0));
                if(message.charAt(0) == '1'){
                    System.out.println("ander aaya");
                    String mess = "";
                    for(int i = 1 ; i < message.length() ; i ++){
                        mess+= message.charAt(i);
                    }
                    System.out.println(mess);
                    loginAndroid(mess);
                }
                if(message.charAt(0) == '2'){
                    String mess = "";
                    for(int i = 1 ; i < message.length() ; i ++){
                        mess+= message.charAt(i);
                    }
                    updateAndroid(mess);
                }
                if(message.charAt(0) == '3'){
                    String mess = "";
                    for(int i = 1 ; i < message.length() ; i ++){
                        mess+= message.charAt(i);
                    }
                    trainAndroid(mess);
                }
                if(message.charAt(0) == '4'){
                    String mess = "";
                    for(int i = 1 ; i < message.length() ; i ++){
                        mess+= message.charAt(i);
                    }
                    possibleList(mess);
                }
                if(message.charAt(0) == '5'){
                    String mess = "";
                    for(int i = 1 ; i < message.length() ; i ++){
                        mess+= message.charAt(i);
                    }
                    book(mess);
                }
                if(message.charAt(0) == '6'){
                    String mess = "";
                    for(int i = 1 ; i < message.length() ; i ++){
                        mess+= message.charAt(i);
                    }
                    station(mess);
                }
                
                if(message.charAt(0) == '7'){
                    String mess = "";
                    for(int i = 1 ; i < message.length() ; i ++){
                        mess+= message.charAt(i);
                    }
                    updateStation(mess);
                }
                
                if(message.charAt(0) == '8'){
                    String mess = "";
                    for(int i = 1 ; i < message.length() ; i ++){
                        mess+= message.charAt(i);
                    }
                    updateTrain(mess);
                }
                
                if(message.charAt(0) == '9'){
                    String mess = "";
                    for(int i = 1 ; i < message.length() ; i ++){
                        mess+= message.charAt(i);
                    }
                    deleteTrain(mess);
                }
                
                if(message.charAt(0) == 'a'){
                    String mess = "";
                    for(int i = 1 ; i < message.length() ; i ++){
                        mess+= message.charAt(i);
                    }
                    deleteStation(mess);
                }
                
                if(message.charAt(0) == 'b'){
                    String mess = "";
                    for(int i = 1 ; i < message.length() ; i ++){
                        mess+= message.charAt(i);
                    }
                    addTrain(mess);
                }
                
                if(message.charAt(0) == 'c'){
                    String mess = "";
                    for(int i = 1 ; i < message.length() ; i ++){
                        mess+= message.charAt(i);
                    }
                    addStation(mess);
                }
                
                if(message.charAt(0) == 'd'){
                    String mess = "";
                    for(int i = 1 ; i < message.length() ; i ++){
                        mess+= message.charAt(i);
                    }
                    checkAvail(mess);
                }
                System.out.println("hello");
                if(message.charAt(0) == 'e'){
                    String mess = "";
                    for(int i = 1 ; i < message.length() ; i ++){
                        mess+= message.charAt(i);
                    }
                    signUp(mess);
                }
            }
        }catch(IOException e){
            System.out.println(e);
        }
        
    }
    
    public static void loginAndroid(String st){
        
        db = new DatabaseConnector();
        
        try{
            System.out.println("heyyyy");
            Socket s = new Socket("100.64.152.188",7802);
            System.out.println("socket");
            PrintWriter pw = new PrintWriter(s.getOutputStream());
            System.out.println("writer bana");
            pw.write(db.login(st));
            pw.flush();
            pw.close();
            s.close();
        }catch(IOException e){
            System.out.println("sendAndroid " + e);
        }
    }
     public static void updateAndroid(String st){
        
        db = new DatabaseConnector();
        
        try{
            System.out.println("heyyyy");
            Socket s = new Socket("100.64.152.188",7802);
            System.out.println("socket");
            PrintWriter pw = new PrintWriter(s.getOutputStream());
            System.out.println("writer bana");
            pw.write(db.update_account(st));
            pw.flush();
            pw.close();
            s.close();
        }catch(IOException e){
            System.out.println("sendAndroid " + e);
        }
    }
     
     public static void updateStation(String st){
        
        db = new DatabaseConnector();
        
        try{
            System.out.println("heyyyy");
            Socket s = new Socket("100.64.152.188",7802);
            System.out.println("socket");
            PrintWriter pw = new PrintWriter(s.getOutputStream());
            System.out.println("writer bana");
            pw.write(db.update_station(st));
            pw.flush();
            pw.close();
            s.close();
        }catch(IOException e){
            System.out.println("sendAndroid " + e);
        }
    }
     
      public static void updateTrain(String st){
        
        db = new DatabaseConnector();
        
        try{
            System.out.println("heyyyy");
            Socket s = new Socket("100.64.152.188",7802);
            System.out.println("socket");
            PrintWriter pw = new PrintWriter(s.getOutputStream());
            System.out.println("writer bana");
            pw.write(db.update_train(st));
            pw.flush();
            pw.close();
            s.close();
        }catch(IOException e){
            System.out.println("sendAndroid " + e);
        }
    }
     
     public static void trainAndroid(String st){
        
        db = new DatabaseConnector();
        
        try{
            System.out.println("heyyyy");
            Socket s = new Socket("100.64.152.188",7802);
            System.out.println("socket");
            PrintWriter pw = new PrintWriter(s.getOutputStream());
            System.out.println("writer bana");
            pw.write(db.getTrain(st));
            pw.flush();
            pw.close();
            s.close();
        }catch(IOException e){
            System.out.println("sendAndroid " + e);
        }
    }
     
     public static void possibleList(String st){
         db = new DatabaseConnector();
        
        try{
            System.out.println("heyyyy");
            Socket s = new Socket("100.64.152.188",7802);
            System.out.println("socket");
            PrintWriter pw = new PrintWriter(s.getOutputStream());
            System.out.println("writer bana");
            pw.write(db.possible_trains(st));
            pw.flush();
            pw.close();
            s.close();
        }catch(IOException e){
            System.out.println("sendAndroid " + e);
        }
     }
    
      public static void book(String st){
         db = new DatabaseConnector();
        
        try{
            System.out.println("heyyyy");
            Socket s = new Socket("100.64.152.188",7802);
            System.out.println("socket");
            PrintWriter pw = new PrintWriter(s.getOutputStream());
            System.out.println("writer bana");
            pw.write(db.book_ticket(st));
            pw.flush();
            pw.close();
            s.close();
        }catch(IOException e){
            System.out.println("sendAndroid " + e);
        }
     }
      
      public static void station(String st){
        
        db = new DatabaseConnector();
        
        try{
            System.out.println("heyyyy");
            Socket s = new Socket("100.64.152.188",7802);
            System.out.println("socket");
            PrintWriter pw = new PrintWriter(s.getOutputStream());
            System.out.println("writer bana");
            pw.write(db.possible_stations(st));
            pw.flush();
            pw.close();
            s.close();
        }catch(IOException e){
            System.out.println("sendAndroid " + e);
        }
    }
     
     
    public static void deleteStation(String st){
        
        db = new DatabaseConnector();
        
        try{
            System.out.println("heyyyy");
            Socket s = new Socket("100.64.152.188",7802);
            System.out.println("socket");
            PrintWriter pw = new PrintWriter(s.getOutputStream());
            System.out.println("writer bana");
            pw.write(db.delete_station(st));
            pw.flush();
            pw.close();
            s.close();
        }catch(IOException e){
            System.out.println("sendAndroid " + e);
        }
    }
    
    public static void deleteTrain(String st){
        
        db = new DatabaseConnector();
        
        try{
            System.out.println("heyyyy");
            Socket s = new Socket("100.64.152.188",7802);
            System.out.println("socket");
            PrintWriter pw = new PrintWriter(s.getOutputStream());
            System.out.println("writer bana");
            pw.write(db.delete_train(st));
            pw.flush();
            pw.close();
            s.close();
        }catch(IOException e){
            System.out.println("sendAndroid " + e);
        }
    }
    
    public static void addTrain(String st){
        
        db = new DatabaseConnector();
        
        try{
            System.out.println("heyyyy");
            Socket s = new Socket("100.64.152.188",7802);
            System.out.println("socket");
            PrintWriter pw = new PrintWriter(s.getOutputStream());
            System.out.println("writer bana");
            pw.write(db.register_train(st));
            pw.flush();
            pw.close();
            s.close();
        }catch(IOException e){
            System.out.println("sendAndroid " + e);
        }
    }
    public static void addStation(String st){
        
        db = new DatabaseConnector();
        
        try{
            System.out.println("heyyyy");
            Socket s = new Socket("100.64.152.188",7802);
            System.out.println("socket");
            PrintWriter pw = new PrintWriter(s.getOutputStream());
            System.out.println("writer bana");
            pw.write(db.register_station(st));
            pw.flush();
            pw.close();
            s.close();
        }catch(IOException e){
            System.out.println("sendAndroid " + e);
        }
    }
    
    public static void checkAvail(String st){
        
        db = new DatabaseConnector();
        
        try{
            System.out.println("heyyyy");
            Socket s = new Socket("100.64.152.188",7802);
            System.out.println("socket");
            PrintWriter pw = new PrintWriter(s.getOutputStream());
            System.out.println("writer bana");
            pw.write(db.check_seats(st));
            pw.flush();
            pw.close();
            s.close();
        }catch(IOException e){
            System.out.println("sendAndroid " + e);
        }
    }
    
    public static void signUp(String st){
        
        db = new DatabaseConnector();
        
        try{
            System.out.println("heyyyy");
            Socket s = new Socket("100.64.152.188",7802);
            System.out.println("socket");
            PrintWriter pw = new PrintWriter(s.getOutputStream());
            System.out.println("writer bana");
            pw.write(db.registerCustomer(st));
            pw.flush();
            pw.close();
            s.close();
        }catch(IOException e){
            System.out.println("sendAndroid " + e);
        }
    }
}
