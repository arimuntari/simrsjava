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
import main.layout.Pasien;

/**
 *
 * @author Muntari
 */
public class ActionPasien extends Pasien implements Action{
     private DefaultTableModel model ;
     private JTextField txtid = getTxtid();
     private JTextField txtname = geTxtname();
     private JTextField txtphone = getTxtphone();
    
    public  ActionPasien(){
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
        getBtnEdit().addActionListener((e) -> {
            edit();
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
            ResultSet rss = stm.executeQuery("select * from patient order by code asc");
            int no =0;
            while(rss.next()){
               no++;
               model.addRow(new Object[]{no, rss.getInt("id"), rss.getString("code"), rss.getString("name"), rss.getString("phone_number") });
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
            int id = (int) getjData().getValueAt(del, 1);
            Connection con = Connect.Open();
            Statement stm = con.createStatement();
            ResultSet rss = stm.executeQuery("delete from patient where id ='"+id+"'");
            model.setRowCount(0);
            refresh();
            stm.close();
        } catch (Exception err) {
            JOptionPane.showMessageDialog(this, err);
        }
    }
     @Override
    public void simpan(){
        String  id = txtid.getText();
        String  name = txtname.getText();
        String  phone = txtphone.getText();
        if(!name.equals("")){
            try {
                Connection con = Connect.Open();
                Statement stm = con.createStatement();
                if(id.equals("")){
                   ResultSet rss = stm.executeQuery("insert into patient (id, code, name, phone_number)"
                           + " values (id_patient.nextval, codePatient('"+name+"'), '"+name+"', '"+phone+"')");
                }else{
                   ResultSet rss = stm.executeQuery("update patient set  name = '"+name+"', phone_number = '"+phone+"'  where id = '"+id+"'");
                }
               stm.close();
                 model.setRowCount(0);
                refresh();
                batal();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, e);
            }
        }else{
            JOptionPane.showMessageDialog(this, "Anda Belum Memasukkan Nama");
        }
    }
     @Override
    public void edit(){
        try {
            int edit =  getjData().getSelectedRow();
            
            txtid.setText(getjData().getValueAt(edit, 1).toString());    
            txtname.setText(getjData().getValueAt(edit, 3).toString());
            txtphone.setText(getjData().getValueAt(edit, 4).toString());
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Anda Belum Memilih Data"+e);
        }
    }
     @Override
    public void batal(){
        txtname.setText("");
        txtphone.setText("");
        txtid.setText("");
    }
}
