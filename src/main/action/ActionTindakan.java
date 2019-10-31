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
import main.layout.Tindakan;

/**
 *
 * @author Muntari
 */
public class ActionTindakan extends Tindakan implements Action{
     private DefaultTableModel model ;
     private JTextField txtid = getTxtid();
     private JTextField txtname = geTxtname();
     private JTextField txtprice = getTxtprice();
    
    public  ActionTindakan(){
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
            ResultSet rss = stm.executeQuery("select * from action order by id desc");
            int no =0;
            while(rss.next()){
               no++;
               model.addRow(new Object[]{no, rss.getInt("id"), rss.getString("name"), rss.getString("price") });
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
            ResultSet rss = stm.executeQuery("delete from action where id ='"+id+"'");
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
        String  price = txtprice.getText();
        if(!name.equals("")){
            try {
                Connection con = Connect.Open();
                Statement stm = con.createStatement();
                if(id.equals("")){
                   ResultSet rss = stm.executeQuery("insert into action (id, name, price) values (id_action.nextval, '"+name+"', '"+price+"')");
                }else{
                   ResultSet rss = stm.executeQuery("update action set  name = '"+name+"', price = '"+price+"'  where id = '"+id+"'");
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
            txtname.setText(getjData().getValueAt(edit, 2).toString());
            txtprice.setText(getjData().getValueAt(edit, 3).toString());
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Anda Belum Memilih Data"+e);
        }
    }
     @Override
    public void batal(){
        txtname.setText("");
        txtprice.setText("");
        txtid.setText("");
    }
}
