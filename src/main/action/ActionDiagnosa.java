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
import javax.swing.table.DefaultTableModel;
import main.Connect;
import main.layout.Diagnosa;

/**
 *
 * @author Muntari
 */
public class ActionDiagnosa extends Diagnosa{
     private DefaultTableModel model ;
    
    public  ActionDiagnosa(){
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
    public void refresh(){  
        try {
            Connection con = Connect.Open();
            Statement stm = con.createStatement();
            ResultSet rss = stm.executeQuery("select * from diagnosis order by id desc");
            int no =0;
            while(rss.next()){
               no++;
               model.addRow(new Object[]{no, rss.getInt("id"), rss.getString("name") });
            }  
            stm.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, e);
        }
    }
    public void delete(){
       try {
            int del =  getjData().getSelectedRow();
            int id = (int) getjData().getValueAt(del, 1);
            Connection con = Connect.Open();
            Statement stm = con.createStatement();
            ResultSet rss = stm.executeQuery("delete from diagnosis where id ='"+id+"'");
            model.setRowCount(0);
            refresh();
            stm.close();
        } catch (Exception err) {
            JOptionPane.showMessageDialog(this, "Anda Belum Memilih Data");
        }
    }
    public void simpan(){
        String  id = getTxtid().getText();
        String  name = getTxtnamediagnosis().getText();
        if(!name.equals("")){
            try {
                Connection con = Connect.Open();
                Statement stm = con.createStatement();
                if(id.equals("")){
                   ResultSet rss = stm.executeQuery("insert into diagnosis (id, name) values (id_diagnosis.nextval, '"+name+"')");
                }else{
                   ResultSet rss = stm.executeQuery("update diagnosis set  name = '"+name+"' where id = '"+id+"'");
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
    public void edit(){
        try {
            int edit =  getjData().getSelectedRow();
            String id = getjData().getValueAt(edit, 1).toString();
            String name = (String) getjData().getValueAt(edit, 2);       
            getTxtnamediagnosis().setText(name);
            getTxtid().setText(id);
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Anda Belum Memilih Data"+e);
        }
    }
    public void batal(){
        getTxtnamediagnosis().setText("");
        getTxtid().setText("");
    }
}
