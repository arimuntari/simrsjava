/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.action;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import main.Connect;
import main.layout.Pendaftaran;

/**
 *
 * @author Muntari
 */
public class ActionPendaftaran extends Pendaftaran implements Action{
     private DefaultTableModel model ;
     private JTextField txtid = getTxtid();
     private JTextField txtcode = getTxtcode();
     private JTextField txtname = geTxtname();
     private JTextField txtphone = getTxtphone();
    
    public  ActionPendaftaran(){
        txtid.setVisible(false);
        model = (DefaultTableModel) getjData().getModel();
        refresh();
        DefaultTableModel model = (DefaultTableModel) getjData().getModel();
        getBtnSave().addActionListener((e) -> {
            simpan();
        });
        getBtnDel().addActionListener((e) -> {
            delete();
        });
        getBtnUnd().addActionListener((e) -> {
            batal();
        });
    }
     @Override
    public void refresh(){  
        try {
            Connection con = Connect.Open();
            Statement stm = con.createStatement();
            ResultSet rss = stm.executeQuery("select a.*, b.code, b.name, b.phone_number, TO_CHAR( date_register, 'dd/mm/yy HH24:MI:SS' )as datereg from register a inner join patient b on a.patient_id = b.id where trunc(date_register) = trunc(sysdate) and a.status !='1' order by no_register asc");
           
            while(rss.next()){
               model.addRow(new Object[]{ rss.getInt("id"),rss.getString("no_register"), rss.getString("code"), rss.getString("name"), rss.getString("phone_number"), rss.getString("datereg") });
            }  
            stm.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, e);
        }
    }
     @Override
    public void delete(){
       try {
            int del =  getjData().getSelectedRow();
            int id = (int) getjData().getValueAt(del, 0);
            Connection con = Connect.Open();
            Statement stm = con.createStatement();
            ResultSet rss = stm.executeQuery("delete from register where id ='"+id+"'");
            model.setRowCount(0);
            refresh();
            stm.close();
        } catch (Exception err) {
            JOptionPane.showMessageDialog(this, "Anda Belum Memilih Data");
        }
    }
     @Override
    public void simpan(){
        String  id = txtid.getText();
        String  name = txtname.getText();
        String  phone = txtphone.getText();
        String  code = txtcode.getText();
        if(!name.equals("") || !code.equals("")){
            try {
                Connection con = Connect.Open();
                Statement stm = con.createStatement();
                if(id.equals("")){
                   ResultSet rss = stm.executeQuery("call registerPasien('"+name+"', '"+phone+"', '"+code+"')");
                }else{
                   ResultSet rss = stm.executeQuery("update medicine set  name = '"+name+"', price = '"+phone+"', stock = '"+code+"'  where id = '"+id+"'");
                }
               stm.close();
                 model.setRowCount(0);
                refresh();
                batal();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, e);
            }
        }else{
            JOptionPane.showMessageDialog(this, "Anda Belum Memasukkan Nama / Kode");
        }
    }
     @Override
    public void edit(){
       
    }
     @Override
    public void batal(){
        txtname.setText("");
        txtcode.setText("");
        txtid.setText("");
        txtphone.setText("");
    }
}
