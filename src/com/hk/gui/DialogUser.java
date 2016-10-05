/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hk.gui;

import com.hk.database.Database;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.swing.JOptionPane;

/**
 *
 * @author hoangkien
 */
public class DialogUser extends javax.swing.JDialog {

    private boolean modify = false;

    /**
     * Creates new form DialogUser
     */
    public DialogUser(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        int year = Calendar.getInstance().get(Calendar.YEAR);
        for (int i = year; i >= year - 100; i--) {
            this.cbBirthYear.addItem(i);
        }
        for (int i = year; i < year + 100; i++) {
            this.cbExYear.addItem(i);
        }
        for (int i = 1; i <= 31; i++) {
            this.cbBirthDate.addItem(i);
            this.cbExDate.addItem(i);
        }
        this.cbBirthMonth.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String month = (String) cbBirthMonth.getSelectedItem();
                int numberOfDay;
                switch (month) {
                    case "April":
                    case "June":
                    case "September":
                    case "November":
                        numberOfDay = 30;
                        break;
                    case "February":
                        if ((int) cbBirthYear.getSelectedItem() % 4 == 0) {
                            numberOfDay = 29;
                        } else {
                            numberOfDay = 28;
                        }
                        break;
                    default:
                        numberOfDay = 31;
                }
                cbBirthDate.removeAllItems();
                for (int i = 1; i <= numberOfDay; i++) {
                    cbBirthDate.addItem(i);
                }
            }
        });
        this.cbExMonth.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String month = (String) cbExMonth.getSelectedItem();
                int numberOfDay;
                switch (month) {
                    case "April":
                    case "June":
                    case "September":
                    case "November":
                        numberOfDay = 30;
                        break;
                    case "February":
                        if ((int) cbExYear.getSelectedItem() % 4 == 0) {
                            numberOfDay = 29;
                        } else {
                            numberOfDay = 28;
                        }
                        break;
                    default:
                        numberOfDay = 31;
                }
                cbExDate.removeAllItems();
                for (int i = 1; i <= numberOfDay; i++) {
                    cbExDate.addItem(i);
                }
            }
        });
        this.cbBirthYear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (((String) cbBirthMonth.getSelectedItem()).equals("February")) {
                    int numberOfDate;
                    if ((int) cbBirthYear.getSelectedItem() % 4 == 0) {
                        numberOfDate = 29;
                    } else {
                        numberOfDate = 28;
                    }
                    cbBirthDate.removeAllItems();
                    for (int i = 1; i <= numberOfDate; i++) {
                        cbBirthDate.addItem(i);
                    }
                }
            }
        });
        this.cbExYear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (((String) cbExMonth.getSelectedItem()).equals("February")) {
                    int numberOfDate;
                    if ((int) cbExYear.getSelectedItem() % 4 == 0) {
                        numberOfDate = 29;
                    } else {
                        numberOfDate = 28;
                    }
                    cbExDate.removeAllItems();
                    for (int i = 1; i <= numberOfDate; i++) {
                        cbExDate.addItem(i);
                    }
                }
            }
        });
    }

    public boolean isModify() {
        return this.modify;
    }

    public void setUsername(String username) {
        this.txtUsername.setText(username);
    }

    public void setPassword(String password) {
        this.txtPassword.setText(password);
    }

    public void setFullname(String fullname) {
        this.txtFullname.setText(fullname);
    }

    public void setBirthDay(Date birthday) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(birthday);
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH) + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        //System.out.println(day + " " + month + " " + year);
        this.cbBirthYear.setSelectedItem(year);
        String strMonth = new DateFormatSymbols().getMonths()[month - 1];
        this.cbBirthMonth.setSelectedItem(strMonth);
        this.cbBirthDate.setSelectedIndex(day - 1);
    }

    public void setAddress(String address) {
        this.txtAddress.setText(address);
    }

    public void setPhone(String phone) {
        this.txtPhone.setText(phone);
    }

    public void setExpirationDate(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH) + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        this.cbExYear.setSelectedItem(year);
        String strMonth = new DateFormatSymbols().getMonths()[month - 1];
        this.cbExMonth.setSelectedItem(strMonth);
        this.cbExDate.setSelectedIndex(day - 1);
    }

    public void setValues(String username, String password, String fullname,
            Date birthday, String address, String phone, Date expirationDate) {
        this.setUsername(username);
        this.setPassword(password);
        this.setFullname(fullname);
        this.setBirthDay(birthday);
        this.setAddress(address);
        this.setPhone(phone);
        this.setExpirationDate(expirationDate);
    }

    public String getUsername() {
        return this.txtUsername.getText();
    }

    public String getPassword() {
        return String.valueOf(this.txtPassword.getPassword());
    }

    public String getFullname() {
        return this.txtFullname.getText();
    }

    public Date getBirthDay() {
        int day = (int) this.cbBirthDate.getSelectedItem();
        int year = (int) this.cbBirthYear.getSelectedItem();
        String month = (String) this.cbBirthMonth.getSelectedItem();
        int monthNum = 0;
        String[] months = new DateFormatSymbols().getMonths();
        for (int i = 0; i < 12; i++) {
            if (months[i].equals(month)) {
                monthNum = i + 1;
                break;
            }
        }
        return Database.newDate(day, monthNum, year);
    }

    public String getAddress() {
        return this.txtAddress.getText();
    }

    public String getPhone() {
        return this.txtPhone.getText();
    }

    public Date getExpirationDate() {
        int day = (int) this.cbExDate.getSelectedItem();
        int year = (int) this.cbExYear.getSelectedItem();
        String month = (String) this.cbExMonth.getSelectedItem();
        int monthNum = 0;
        String[] months = new DateFormatSymbols().getMonths();
        for (int i = 0; i < 12; i++) {
            if (months[i].equals(month)) {
                monthNum = i + 1;
                break;
            }
        }
        return Database.newDate(day, monthNum, year);
    }
    
    public void disableUsername() {
        this.txtUsername.setEnabled(false);
        this.txtUsername.setDisabledTextColor(Color.gray);
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtUsername = new javax.swing.JTextField();
        txtFullname = new javax.swing.JTextField();
        txtAddress = new javax.swing.JTextField();
        txtPhone = new javax.swing.JTextField();
        cbBirthMonth = new javax.swing.JComboBox();
        cbBirthDate = new javax.swing.JComboBox();
        cbBirthYear = new javax.swing.JComboBox();
        cbExMonth = new javax.swing.JComboBox();
        cbExDate = new javax.swing.JComboBox();
        cbExYear = new javax.swing.JComboBox();
        btnOK = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();
        txtPassword = new javax.swing.JPasswordField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setText("Username");

        jLabel2.setText("Password");

        jLabel3.setText("Fullname");

        jLabel4.setText("Birthday");

        jLabel5.setText("Address");

        jLabel6.setText("Phone");

        jLabel7.setText("Expiration Date");

        cbBirthMonth.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December" }));
        cbBirthMonth.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbBirthMonthActionPerformed(evt);
            }
        });

        cbExMonth.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December" }));

        btnOK.setText("OK");
        btnOK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOKActionPerformed(evt);
            }
        });

        btnCancel.setText("Cancel");
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(88, 88, 88)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cbBirthMonth, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbExMonth, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(9, 9, 9)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cbExDate, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbBirthDate, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cbBirthYear, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbExYear, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(134, 134, 134))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnOK, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCancel))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(88, 88, 88)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtPhone, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtFullname, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtUsername, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtAddress, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtPassword))))
                .addGap(10, 10, 10))
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {cbBirthYear, cbExYear});

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {cbBirthMonth, cbExMonth});

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {cbBirthDate, cbExDate});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtUsername, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtFullname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(cbBirthMonth, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbBirthDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbBirthYear, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtAddress, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtPhone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(cbExMonth, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbExDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbExYear, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnOK)
                    .addComponent(btnCancel))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnOKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOKActionPerformed
        if (this.txtUsername.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Username field cannot empty");
        } else if (String.valueOf(this.txtPassword.getPassword()).equals("")) {
            JOptionPane.showMessageDialog(this, "Password field cannot empty");
        } else if (this.txtFullname.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Fullname field cannot empty");
        } else if (this.txtAddress.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Address field cannot empty");
        } else if (this.txtPhone.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Phone field cannot empty");
        } else {
            this.modify = true;
            this.dispose();
        }
    }//GEN-LAST:event_btnOKActionPerformed

    private void cbBirthMonthActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbBirthMonthActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbBirthMonthActionPerformed

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        this.modify = false;
        this.dispose();
    }//GEN-LAST:event_btnCancelActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(DialogUser.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DialogUser.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DialogUser.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DialogUser.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                DialogUser dialog = new DialogUser(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnOK;
    private javax.swing.JComboBox cbBirthDate;
    private javax.swing.JComboBox cbBirthMonth;
    private javax.swing.JComboBox cbBirthYear;
    private javax.swing.JComboBox cbExDate;
    private javax.swing.JComboBox cbExMonth;
    private javax.swing.JComboBox cbExYear;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JTextField txtAddress;
    private javax.swing.JTextField txtFullname;
    private javax.swing.JPasswordField txtPassword;
    private javax.swing.JTextField txtPhone;
    private javax.swing.JTextField txtUsername;
    // End of variables declaration//GEN-END:variables
}
