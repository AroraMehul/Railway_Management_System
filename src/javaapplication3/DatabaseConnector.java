/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication3;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Types;
import java.util.regex.PatternSyntaxException;

/**
 *
 * @author Mehul
 */
public class DatabaseConnector {

    private String message;
    private Connection con;

    public DatabaseConnector() {

        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");

//step2 create  the connection object  
            con = DriverManager.getConnection(
                    "jdbc:oracle:thin:@localhost:1521:orcl", "mehul", "mehul");
        } catch (Exception e) {
            System.out.println(e);
        }

    }
    
//    public String loginCheck(String s){
//        String[] splitArray = null;
//        try {
//            splitArray = s.split("/");
//            System.out.println("split hua");
//        } catch (PatternSyntaxException ex) {
//            System.out.println(ex);
//        }
//        System.out.println("for loop se pehle");
//        for(int i = 0 ; i < splitArray.length ; i++){
//            System.out.println(splitArray[i]);
//        }
//        try{
//            CallableStatement login = con.prepareCall("{call login(?,?,?)}");
//            System.out.println(Integer.parseInt(splitArray[0]));
//            login.setInt(1,Integer.parseInt(splitArray[0]));
//            System.out.println(splitArray[1]);
//            login.setString(2, splitArray[1]);
//            System.out.println("1");
//            login.registerOutParameter(3,Types.VARCHAR);
//            login.execute();
//            System.out.println("2");
//            if(login.getString(3) == null){
//                System.out.println("null aaya");
//            }
//            message = login.getString(3);
//            System.out.println("3");
//        }catch(Exception e){
//            System.out.println("kahaan");
//            System.out.println(e);
//        }
//        return message;
//    }
//
    public String registerCustomer(String s) {
        String[] splitArray = null;
        try {
            splitArray = s.split("/");
            System.out.println("split hua");
        } catch (PatternSyntaxException ex) {
            System.out.println(ex);
        }
        System.out.println("for loop se pehle");
        for(int i = 0 ; i < splitArray.length ; i++){
            System.out.println( i + " " +splitArray[i]);
        }
        try {
            CallableStatement regCust = con.prepareCall("{call register_account(? ,? ,? ,? ,? ,? ,? )}");
            regCust.setString(1, splitArray[1]);
            regCust.setString(2, splitArray[2]);
            regCust.setString(3, splitArray[3]);
            regCust.setString(4, splitArray[4]);
            regCust.setString(5, splitArray[5]);
            regCust.setInt(6,Integer.parseInt(splitArray[6]) );
            regCust.registerOutParameter(7, Types.VARCHAR);
            regCust.execute();
            message = regCust.getString(7);
            if(message == null){
                message = "User Successfull`y Registered";
            }
            System.out.println("out : " + regCust.getString(7));
        } catch (Exception e) {
            System.out.println(e);
        }
        return message;
    }

//    public String getMessage() {
//        try {
////step1 load the driver class  
//
////step3 create the statement object  
//            Statement stmt = con.createStatement();
//
////step4 execute query  
//            ResultSet rs = stmt.executeQuery("select * from emp");
//            while (rs.next()) {
//                message = rs.getInt(1) + "  " + rs.getString(2) + "  " + rs.getString(3);
//            }
//
////step5 close the connection object  
//            con.close();
//
//        } catch (Exception e) {
//            System.out.println(e);
//        }
//        return message;
//    }
//    
//    public String updateCustomer(String s){
//        
//        String[] splitArray = null;
//        try {
//            splitArray = s.split("/");
//            System.out.println("split hua");
//        } catch (PatternSyntaxException ex) {
//            System.out.println(ex);
//        }
//        System.out.println("for loop se pehle");
//        for(int i = 0 ; i < splitArray.length ; i++){
//            System.out.println(splitArray[i]);
//        }
//        try{
//            CallableStatement updateCust = con.prepareCall("{call update_customer(?,?,?,?)}");
//            updateCust.setString(1, splitArray[0]);
//            updateCust.setString(2, splitArray[1]);
//            updateCust.setString(3,splitArray[2]);
//            updateCust.registerOutParameter(4, Types.VARCHAR);
//            updateCust.execute();
//            message = updateCust.getString(4);
//            System.out.println(message);
//        }catch(Exception e){
//            System.out.println(e);
//        }
//        return message;
//    }
//    
    public String getTrain(String s){
        try{
            CallableStatement getTrain = con.prepareCall("{call get_train(?,?)}");
            System.out.println(s);
            getTrain.setInt(1, Integer.parseInt(s));
            getTrain.registerOutParameter(2, Types.VARCHAR);
            getTrain.execute();
            if(message == null){
                System.out.println("null");
            }
            message = getTrain.getString(2);
            System.out.println(message);
        }catch(Exception e){
            System.out.println(e);
        }
        return message;
    }
    
    public String possible_stations(String s)
{
    String[] splitArray = null;
    try{
         CallableStatement pb_stn = con.prepareCall("{call possible_stations(?,?)}");
         pb_stn.setInt(1, Integer.parseInt(s));
         pb_stn.registerOutParameter(2, Types.VARCHAR);
         pb_stn.execute();
         message = pb_stn.getString(2);
         if(message == null)
         {
             System.out.println("null");
         }
         message = pb_stn.getString(2);
         System.out.println(message);
         
    }catch(Exception e){
        System.out.println(e);
    }
    
    return message;
}
    
    
    
    
    
    // Shubham vale
    
     public String get_account(String s)
    {
        String[] splitArray = null;
        try{
            CallableStatement get_acc = con.prepareCall("{call get_account(?,?)}");
            get_acc.setString(1, splitArray[0]);
            get_acc.registerOutParameter(2,Types.VARCHAR);
            get_acc.execute();
            message = get_acc.getString(2);
            System.out.println(message);
        }catch(Exception e){
            System.out.println(e);
        }
        return message;
    }
   
    public String delete_account(String s)
    {
        String[] splitArray = null;
        try{
            CallableStatement del_acc = con.prepareCall("{call del_account(?,?)}");
            del_acc.setString(1, splitArray[0]);
            del_acc.registerOutParameter(2, Types.VARCHAR);
            del_acc.execute();
            message = del_acc.getString(2);
            System.out.println(message);
        }catch(Exception e){
            System.out.println(e);
        }
        return message;
    }
   
    public String login(String s)
    {
        String[] splitArray  = null;
         try {
            splitArray = s.split("/");
            System.out.println("split hua");
        } catch (PatternSyntaxException ex) {
            System.out.println(ex);
        }
        try{
            CallableStatement lg = con.prepareCall("{call login(?,?,?)}");
            System.out.println(splitArray[1]);
            lg.setString(1, splitArray[0]);
            lg.setString(2, splitArray[1]);
            lg.registerOutParameter(3, Types.VARCHAR);
            lg.execute();
            message = lg.getString(3);
            System.out.println(message);
        }catch(Exception e){
            System.out.println(e);
        }
        return message;
    }
   
    public String update_account(String s)
    {
        String[] splitArray = null;
        try {
            splitArray = s.split("/");
            System.out.println("split hua");
        } catch (PatternSyntaxException ex) {
            System.out.println(ex);
        }
        try{
            CallableStatement up_acc = con.prepareCall("{call update_account(?,?,?,?)}");
            up_acc.setString(1, splitArray[0]);
            up_acc.setString(2, splitArray[1]);
            up_acc.setString(3, splitArray[2]);
            up_acc.registerOutParameter(4, Types.VARCHAR);
            up_acc.execute();
            message = up_acc.getString(4);
            System.out.println(message);
        }catch(Exception e){
            System.out.println(e);
        }
        return message;
    }
   
    public String add_passenger(String s)
    {
        String[] splitArray = null;
         try {
            splitArray = s.split("/");
            System.out.println("split hua");
        } catch (PatternSyntaxException ex) {
            System.out.println(ex);
        }
         for(int i = 0 ; i < splitArray.length ; i++){
             System.out.println(splitArray[i]);
         }
        try{
            CallableStatement add_p = con.prepareCall("{call add_passenger(?,?,?,?,?,?)}");
            add_p.setString(1, splitArray[0]);
            System.out.println("1");
            add_p.setString(2, splitArray[1]);
            System.out.println("2");
            add_p.setString(3, splitArray[2]);
            System.out.println("3");
            add_p.setString(4, splitArray[3]);
            System.out.println("4");
            add_p.setInt(5, Integer.parseInt(splitArray[4]));
            System.out.println("5");
            add_p.registerOutParameter(6, Types.VARCHAR);
            System.out.println("6");
            add_p.execute();
            System.out.println("7");
            message = add_p.getString(6);
            System.out.println("8");
            System.out.println(message);
        }catch(Exception e){
            System.out.println(e);
            System.out.println("error mein hoon");
        }
        return message;
    }
   
    public String register_station(String s)
    {
        String[] splitArray = null;
        try {
            splitArray = s.split("/");
            System.out.println("split hua");
        } catch (PatternSyntaxException ex) {
            System.out.println(ex);
        }
        System.out.println(splitArray[0]);
        System.out.println(splitArray[1]);
        try{
            CallableStatement reg_stn = con.prepareCall("{call register_station(?,?,?)}");
            reg_stn.setString(1, splitArray[0]);
            reg_stn.setString(2, splitArray[1]);
            reg_stn.registerOutParameter(3, Types.VARCHAR);
            reg_stn.execute();
            message = reg_stn.getString(3);
            if(message == null){
                message = "Station Registered";
            }
            System.out.println(message);
        }catch(Exception e){
            System.out.println(e);
        }
        return message;
    }
   
    public String delete_station(String s)
    {
        String[] splitArray = null;
        try{
            CallableStatement del_stn = con.prepareCall("{call delete_station(?,?)}");
            del_stn.setString(1, s);
            del_stn.registerOutParameter(2, Types.VARCHAR);
            del_stn.execute();
            message = del_stn.getString(2);
            System.out.println(message);
        }catch(Exception e){
            System.out.println(e);
        }
        return message;
    }
    
     public String update_station(String s)
    {
        String[] splitArray = null;
        try {
            splitArray = s.split("/");
            System.out.println("split hua");
        } catch (PatternSyntaxException ex) {
            System.out.println(ex);
        }
        try{
            CallableStatement up_stn = con.prepareCall("{call update_station(?,?,?,?)}");
            up_stn.setString(1, splitArray[0]);
            up_stn.setString(2, splitArray[1]);
            up_stn.setString(3, splitArray[2]);
            up_stn.registerOutParameter(4, Types.VARCHAR);
            up_stn.execute();
            message = up_stn.getString(4);
            System.out.println(message);
        }catch(Exception e){
            System.out.println(e);
        }
        return message;
    }
   
    public String possible_trains(String s)
    {
        String[] splitArray = null;
        try {
            splitArray = s.split("/");
            System.out.println("split hua");
        } catch (PatternSyntaxException ex) {
            System.out.println(ex);
        }
        System.out.println(splitArray[0]);
        try{
            CallableStatement pb_trns = con.prepareCall("{call possible_trains(?,?,?,?)}");
            pb_trns.setString(1, splitArray[2]);
            pb_trns.setString(2, splitArray[0]);
            pb_trns.setString(3, splitArray[1]);
            pb_trns.registerOutParameter(4, Types.VARCHAR);
            pb_trns.execute();
            message = pb_trns.getString(4);
            System.out.println(message);
        }catch(Exception e){
            System.out.println(e);
        }
        return message;
    }
   
    public String book_ticket(String s)
    {
        String[] splitArray = null;
         try {
            splitArray = s.split("/");
            System.out.println("split hua");
        } catch (PatternSyntaxException ex) {
            System.out.println(ex);
        }
         
        try{
            CallableStatement bk_tkt = con.prepareCall("{call book_ticket(?,?,?,?,?)}");
            bk_tkt.setString(1, splitArray[0]);
            bk_tkt.setString(2, splitArray[1]);
            bk_tkt.setString(3, splitArray[2]);
            bk_tkt.setString(4, splitArray[3]);
            bk_tkt.registerOutParameter(5, Types.VARCHAR);
            bk_tkt.execute();
            
            String st = "";
         for(int i = 4 ; i  < splitArray.length-1 ; i++){
             st+=splitArray[i] + "/";
         }
         st+=splitArray[splitArray.length-1];
            System.out.println(st);
         
         message = add_passenger(st);
            System.out.println(message);
        }catch(Exception e){
            System.out.println(e);
        }
        return message;
    }
   
    public String register_train(String s)
    {
        String[] splitArray = null;
        try {
            splitArray = s.split("/");
            System.out.println("split hua");
        } catch (PatternSyntaxException ex) {
            System.out.println(ex);
        }
        try{
            CallableStatement rg_trn = con.prepareCall("{call register_train(?,?,?,?,?,?,?)}");
            rg_trn.setInt(1, Integer.parseInt(splitArray[0]));
            rg_trn.setString(2, splitArray[1]);
            rg_trn.setString(3, splitArray[2]);
            rg_trn.setString(4, splitArray[3]);
            rg_trn.setString(5, splitArray[4]);
            rg_trn.setString(6, splitArray[5]);
            rg_trn.registerOutParameter(7, Types.VARCHAR);
            rg_trn.execute();
            message = rg_trn.getString(7);
            if(message == null){
                message = "Train added successfuly";
            }
            System.out.println(message);
        }catch(Exception e){
            System.out.println(e);
        }
        return message;
    }
    
    public String get_train(String s)
    {
       String[] splitArray = null;
        try{
            CallableStatement gt_trn = con.prepareCall("{call get_train(?,?)}");
            gt_trn.setString(1, splitArray[0]);
            gt_trn.registerOutParameter(2, Types.VARCHAR);
            gt_trn.execute();
            message = gt_trn.getString(2);
            System.out.println(message);
        }catch(Exception e){
            System.out.println(e);
        }
        return message;
    }
   
    public String delete_train(String s)
    {
        String[] splitArray = null;
        try{
            CallableStatement dt_trn = con.prepareCall("{call delete_train(?,?)}");
            dt_trn.setInt(1, Integer.parseInt(s));
            dt_trn.registerOutParameter(2, Types.VARCHAR);
            dt_trn.execute();
            message = dt_trn.getString(2);
            System.out.println(message);
        }catch(Exception e){
            System.out.println(e);
        }
        return message;
    }
  
    public String update_train(String s)
    {
        String[] splitArray = null;
        try {
            splitArray = s.split("/");
            System.out.println("split hua");
        } catch (PatternSyntaxException ex) {
            System.out.println(ex);
        }
        try{
            CallableStatement up_trn = con.prepareCall(" {call update_train(?,?,?,?)}");
            up_trn.setString(1, splitArray[0]);
            up_trn.setString(2, splitArray[1]);
            up_trn.setString(3, splitArray[2]);
            up_trn.registerOutParameter(4, Types.VARCHAR);
            up_trn.execute();
            message = up_trn.getString(4);
            System.out.println(message);
        }catch(Exception e){
            System.out.println(e);
        }
       
        return message;
    }
    
    public String check_seats(String s)
    {
        String[] splitArray = null;
        try {
            splitArray = s.split("/");
            System.out.println("split hua");
        } catch (PatternSyntaxException ex) {
            System.out.println(ex);
        }
        try{
            CallableStatement dt_trn = con.prepareCall("{call check_availabilty(?,?,?)}");
            dt_trn.setInt(1, Integer.parseInt(splitArray[0]));
            dt_trn.setString(2, splitArray[1]);
            dt_trn.registerOutParameter(3, Types.VARCHAR);
            dt_trn.execute();
            message = dt_trn.getString(3);
            System.out.println(message);
        }catch(Exception e){
            System.out.println(e);
        }
        return message;
    }

}
