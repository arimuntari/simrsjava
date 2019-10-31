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
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import main.ComboItem;
import main.Connect;
import main.layout.Pemeriksaan;

/**
 *
 * @author Muntari
 */
public class ActionPemeriksaan extends Pemeriksaan implements Action{
     private DefaultTableModel model ;
     private DefaultTableModel modeldiag ;
     private DefaultTableModel modeltind ;
     private JButton btnRefresh = getBtnRefresh();
     private JButton btnSavediag = getBtnSavediag();
     private JButton btnSavetind = getBtnSavetind();
     private JButton btnDeldiag = getBtndeldiag();
     private JButton btnDeltind = getBtnDeltind();
     private JComboBox selectdiag = getjComboBox1();
     private JComboBox selecttind = getjComboBox2();
     private String regis_id;
     
     public  ActionPemeriksaan(){
        model = (DefaultTableModel) getjData().getModel();
        modeldiag = (DefaultTableModel) getJdiag().getModel();
        modeltind = (DefaultTableModel) getJtind().getModel();
        refresh();
        getjData().getSelectionModel().addListSelectionListener((e) -> {
             try {
               int edit =  getjData().getSelectedRow();
                regis_id = getjData().getValueAt(edit, 0).toString();
                refresh(regis_id);
            } catch (Exception err) {
                
            }
        });
        DefaultTableModel model = (DefaultTableModel) getjData().getModel();
        getBtnSave().addActionListener((e) -> {
            simpan();
        });
        btnRefresh.addActionListener((e) -> {
            refresh();
        });
        btnSavediag.addActionListener((e) -> {   
            try {
                ComboItem select = (ComboItem) selectdiag.getSelectedItem();
                String id = select.getValue();
                String price = select.getPrice();
                simpan(regis_id, id);
            } catch (Exception err) {
                
            }
            refresh(regis_id);
        });
        btnSavetind.addActionListener((e) -> {   
            try {
               int edit =  getjData().getSelectedRow();
                String regis_id = getjData().getValueAt(edit, 0).toString();
                ComboItem select = (ComboItem) selecttind.getSelectedItem();
                String id = select.getValue();
                String price = select.getPrice();
                simpan(regis_id, id, price);
            } catch (Exception err) {
                
            }
            refresh(regis_id);
        });
        btnDeldiag.addActionListener((e) -> {
            try {
                int edit =  getJdiag().getSelectedRow();
                String id = getJdiag().getValueAt(edit, 0).toString();
                delete(id, "checkup_diagnosis");
            } catch (Exception err) {
                
            }
            refresh(regis_id);
        });
        btnDeltind.addActionListener((e) -> {
            try {
               int edit =  getJtind().getSelectedRow();
                String id = getJtind().getValueAt(edit, 0).toString();
                delete(id, "checkup_action");
            } catch (Exception err) {
                
            }
            refresh(regis_id);
        });
        
        setComboBoxDiag();
        setComboBoxTind();
    }
     @Override
    public void refresh(){  
        model.setRowCount(0);
        try {
            Connection con = Connect.Open();
            Statement stm = con.createStatement();
            ResultSet rss = stm.executeQuery("select a.*, b.code, b.name, b.phone_number, TO_CHAR( date_register, 'dd/mm/yy HH24:MI:SS' )as datereg from register a inner join patient b on a.patient_id = b.id where trunc(date_register) = trunc(sysdate) order by no_register asc");
           
            while(rss.next()){
                if(rss.getString("id").equals(regis_id)){
                    getjTextField1().setText(rss.getString("price_total"));
                }
               model.addRow(new Object[]{ rss.getInt("id"),rss.getString("no_register")+"("+rss.getString("status")+")", rss.getString("code"), rss.getString("name"), rss.getString("datereg") });
            }  
            stm.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, e);
        }
        
    }
    public void refresh(String id){
            modeldiag.setRowCount(0);
            modeltind.setRowCount(0);
        try {
            Connection con = Connect.Open();
            Statement stm = con.createStatement();
            ResultSet rss = stm.executeQuery("select a.*, b.code, b.name, b.phone_number, TO_CHAR( date_register, 'dd/mm/yy HH24:MI:SS' )as datereg from register a inner join patient b on a.patient_id = b.id where a.id='"+regis_id+"' order by no_register asc");

            rss.next();
               getjTextField1().setText(rss.getString("price_total"));
            stm.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, e);
        }
        try {
            Connection con = Connect.Open();
            Statement stm = con.createStatement();
          
            ResultSet rss = stm.executeQuery("select a.*, b.name from checkup_diagnosis a inner join diagnosis b on a.diagnosis_id = b.id where a.register_id = '"+id+"'");
             int no = 0 ;
            while(rss.next()){
                no++;
               modeldiag.addRow(new Object[]{ rss.getInt("id"), no, rss.getString("name") });
            }  
            stm.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, e);
        }
        try {
            Connection con = Connect.Open();
            Statement stm = con.createStatement();
            ResultSet rss = stm.executeQuery("select a.*, b.name from checkup_action a inner join action b on a.action_id = b.id where a.register_id = '"+id+"'");   
           int no = 0 ;
            while(rss.next()){
                  no++;
               modeltind.addRow(new Object[]{ rss.getInt("id"),no, rss.getString("name"), rss.getString("price") });
            }  
            stm.close();
        } catch (SQLException err) {
            JOptionPane.showMessageDialog(this, err);
        }
    }
     @Override
    public void simpan(){
          try {
            Connection con = Connect.Open();
            Statement stm = con.createStatement();
            ResultSet rss = stm.executeQuery("update register set status = '1' where id = '"+regis_id+"'");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e);
        }
      // refresh();
       refresh(regis_id);
    }
    public void simpan(String regis_id, String id ){
        try {
            Connection con = Connect.Open();
            Statement stm = con.createStatement();
            ResultSet rss = stm.executeQuery("insert into checkup_diagnosis(id, register_id, diagnosis_id) values(id_checkupaction.nextval, '"+regis_id+"', '"+id+"')");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e);
        }
    }
    public void simpan(String regis_id, String id, String price ){
        try {
            Connection con = Connect.Open();
            Statement stm = con.createStatement();
            ResultSet rss = stm.executeQuery("insert into checkup_action values(id_checkupaction.nextval, '"+regis_id+"', '"+id+"', '"+price+"')");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e);
        }
    }
     @Override
    public void edit(){
       
    }
     @Override
    public void batal(){
        
    }
    
    public void setComboBoxDiag(){
        DefaultComboBoxModel  modeldiag = (DefaultComboBoxModel) selectdiag.getModel();
       
        try {
            Connection con = Connect.Open();
            Statement stm = con.createStatement();
            ResultSet rss = stm.executeQuery("select * from diagnosis order by id asc");
           
            while(rss.next()){
                modeldiag.addElement(new ComboItem( rss.getString("name"), rss.getString("id")));
            }  
            stm.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, e);
        }
        
    }
    public void setComboBoxTind(){
        DefaultComboBoxModel  modeltind = (DefaultComboBoxModel) selecttind.getModel();
       
        try {
            Connection con = Connect.Open();
            Statement stm = con.createStatement();
            ResultSet rss = stm.executeQuery("select * from action order by id asc");
           
            while(rss.next()){
                modeltind.addElement(new ComboItem( rss.getString("name"), rss.getString("id"), rss.getString("price")));
            }  
            stm.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, e);
        }
        
    }

    @Override
    public void delete() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public void delete(String id, String table){
        //System.out.print(table);
       try {
            Connection con = Connect.Open();
            Statement stm = con.createStatement();
            ResultSet rss = stm.executeQuery("delete from "+table+" where id ='"+id+"'");
            model.setRowCount(0);
            refresh();
            stm.close();
        } catch (Exception err) {
            JOptionPane.showMessageDialog(this, err);
        }
    }
}
