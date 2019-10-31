/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Connection;

/**
 *
 * @author Muntari
 */
public class Connect {
 private static Connection conn;  
 private static String driverName = "oracle.jdbc.driver.OracleDriver";  
 private static String jdbc = "jdbc:oracle:thin:";  
 private static String host = "@localhost:";  
 private static String port = "1521:";  
 private static String SID = "xe";   
 private static String database = "SIMRS";   
 private static String url = jdbc + host + port + SID;  
 private static String username = "simrs";   
 private static String password = "123";  
    public static Connection Open() throws SQLException {  
       if (conn == null) {  
           try {  
               Class.forName(driverName);  
               System.out.println("Class Driver Ditemukan");  
               try {  
                   conn = DriverManager.getConnection(url, username, password);  
                   conn.setAutoCommit(true);
                   System.out.println("Koneksi Database Sukses");  
               }catch (SQLException se) {  
                   System.out.println("Koneksi Database Gagal : " + se);  
                   System.exit(0);  
               }  
           } catch (ClassNotFoundException ex) {  
               System.out.println("Class Driver Tidak Ditemukan, Terjadi Kesalahan Pada : " + ex);  
               System.exit(0);  
           }  
       }  
      return conn;  
    }  
    
    public void Close(){
        try {
            conn.close();
        } catch (Exception e) {
            System.out.println("Tidak dapat menutup koneksi database!" + e);
        }
        conn = null;
    }
}
