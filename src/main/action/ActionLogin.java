/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.action;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import main.Connect;
import main.Login;
import main.Variable;
import main.layout.Diagnosa;
import main.layout.FormLogin;
import main.layout.Layout;

/**
 *
 * @author Muntari
 */
public class ActionLogin extends Login{
    public void actLogin(String username, String password, FormLogin form){
        if(username.equals("") ||  password.equals("")){
            JOptionPane.showMessageDialog(null, "Anda Belum Memasukkan Username/Password");
        }else {
            try {
                Connection con = Connect.Open();
                Statement stm = con.createStatement();
                ResultSet rss = stm.executeQuery("Select * from userslogin where username ='"+username+"' and password = '"+password+"'");
                if(rss.next()){    
                    System.out.println(rss.getString("name"));
                    
                    setNama(rss.getString("name"));
                    setUsername(rss.getString("username"));
                    setPassword(rss.getString("password"));
                    setStatus(rss.getString("tipe"));
                    
                    JOptionPane.showMessageDialog(null, "Login Berhasil!!"); 
                    Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
                    Layout lay = new Layout();
                    lay.setBounds((int) (d.getWidth()/2 - 300), (int) (d.getHeight()/2-250), Variable.getWidth(), Variable.getHeight());
                    lay.setLog(this);
                    lay.setVisible(true);
                    
                    form.setVisible(false);
                    stm.close();
                }else{
                   JOptionPane.showMessageDialog(null, "Username dan Password Salah!!");  
                }
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }
}
