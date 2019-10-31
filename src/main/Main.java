/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.sql.Connection;
import java.sql.SQLException;
import main.layout.FormLogin;

/**
 *
 * @author Muntari
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws SQLException {
        Connection con = Connect.Open();
        FormLogin login = new FormLogin();
        login.setVisible(true);
    }
    
}
