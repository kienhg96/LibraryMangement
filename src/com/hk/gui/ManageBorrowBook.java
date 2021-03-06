/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hk.gui;

import com.hk.authenticate.AdminsAuth;
import com.hk.database.Database;
import com.hk.objs.Books;
import com.hk.objs.BorrowDetails;
import com.hk.objs.Borrows;
import com.hk.objs.Users;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author hoangkien
 */
public class ManageBorrowBook extends javax.swing.JFrame {
    private ArrayList<Users> listUser;
    private ArrayList<Books> listBook;
    private DefaultTableModel userModel;
    private DefaultTableModel bookModel;
    private DefaultTableModel borrowModel;
    private DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
    /**
     * Creates new form BorrowBook
     */
    public ManageBorrowBook() {
        initComponents();
        this.userModel = (DefaultTableModel)this.tbUser.getModel();
        this.bookModel = (DefaultTableModel)this.tbBook.getModel();
        this.borrowModel = (DefaultTableModel)this.tbBorrow.getModel();
        int year = Calendar.getInstance().get(Calendar.YEAR);
        
        for (int i = year; i < year + 100; i++) {
            this.cbExYear.addItem(i);
        }
        for (int i = 1; i <= 31; i++) {
            this.cbExDate.addItem(i);
        }
        this.cbExMonth.addActionListener(new ActionListener() {
            @Override
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
        this.cbExYear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (((String) cbExMonth.getSelectedItem()).equals("February")) {
                    int numberOfDate;
                    int currentDate = (int)cbExDate.getSelectedItem();
                    if ((int) cbExYear.getSelectedItem() % 4 == 0) {
                        numberOfDate = 29;
                    } else {
                        numberOfDate = 28;
                    }
                    cbExDate.removeAllItems();
                    for (int i = 1; i <= numberOfDate; i++) {
                        cbExDate.addItem(i);
                    }
                    if (currentDate <= 28) {
                        cbExDate.setSelectedIndex(currentDate - 1);
                    }
                    else {
                        if ((int) cbExYear.getSelectedItem() % 4 == 0) {
                            cbExDate.setSelectedIndex(28);
                        }
                        else {
                            cbExDate.setSelectedIndex(27);
                        }
                    }
                }
            }
        });
        // Get data
        this.listBook = Database.getAllBookAvailable();
        this.listUser = Database.getAllUsers();
        for (Books book: listBook) {
            bookModel.addRow(new Object[]{
                book.getBookId(), book.getBookName(), book.getAuthor(),
                book.getCategory().getCategoryName(), book.getPublishCom(),
                book.getShelf(), book.getPrice(), book.getPublishYear()
            });
        }
        for (Users user: listUser) {
            userModel.addRow(new Object[]{
                user.getUsername(), user.getFullname(), 
                df.format(user.getBirthday()), user.getAddress(), 
                user.getPhone(), df.format(user.getExpirationDate())
            });
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTextField1 = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jTextField3 = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbUser = new javax.swing.JTable();
        btnFilterName = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbBook = new javax.swing.JTable();
        jTextField4 = new javax.swing.JTextField();
        jTextField5 = new javax.swing.JTextField();
        jTextField6 = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        btnFilterBook = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        tbBorrow = new javax.swing.JTable();
        btnAdd = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();
        btnOK = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        cbExMonth = new javax.swing.JComboBox();
        cbExDate = new javax.swing.JComboBox();
        cbExYear = new javax.swing.JComboBox();
        btnClearAll = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Manage Borrow Books");

        jLabel1.setText("Choose Reader:");

        tbUser.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Username", "Fullname", "Birthday", "Address", "Phone number", "Expiration Date"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tbUser);
        if (tbUser.getColumnModel().getColumnCount() > 0) {
            tbUser.getColumnModel().getColumn(0).setPreferredWidth(80);
            tbUser.getColumnModel().getColumn(1).setResizable(false);
            tbUser.getColumnModel().getColumn(2).setPreferredWidth(60);
        }

        btnFilterName.setText("Filter");

        tbBook.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Book ID", "Book name", "Author", "Category", "Publish Com.", "Shelf", "Price", "Publish Year"
            }
        ));
        jScrollPane2.setViewportView(tbBook);

        jLabel2.setText("Choose Book:");

        btnFilterBook.setText("Filter");

        tbBorrow.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Book ID", "Book Name", "Author", "Borrow Date", "Expiration Date"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane3.setViewportView(tbBorrow);

        btnAdd.setText("Add");
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });

        btnCancel.setText("Cancel");
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });

        btnOK.setText("OK");
        btnOK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOKActionPerformed(evt);
            }
        });

        jLabel3.setText("Filter by Username");

        jLabel4.setText("By Fullname");

        jLabel5.setText("By Phone");

        jLabel6.setText("Expiration Date:");

        jLabel7.setText("Filter By Book ID");

        jLabel8.setText("By Book Name");

        jLabel9.setText("By Author");

        cbExMonth.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December" }));

        btnClearAll.setText("Clear All");
        btnClearAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClearAllActionPerformed(evt);
            }
        });

        btnDelete.setText("Delete");
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
            .addComponent(jScrollPane2)
            .addComponent(jScrollPane3)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jTextField3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnFilterName)
                        .addGap(77, 77, 77))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField6)
                        .addGap(20, 20, 20)
                        .addComponent(btnFilterBook))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addGap(18, 18, 18)
                                .addComponent(cbExMonth, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(9, 9, 9)
                                .addComponent(cbExDate, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cbExYear, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 359, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addComponent(btnDelete)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnClearAll)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnOK)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnCancel)
                .addGap(11, 11, 11))
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {btnCancel, btnOK});

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {btnClearAll, btnDelete});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnFilterName)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 148, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addGap(2, 2, 2)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnFilterBook)
                    .addComponent(jLabel7)
                    .addComponent(jLabel8)
                    .addComponent(jLabel9))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 153, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(cbExMonth, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbExDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbExYear, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAdd))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnClearAll)
                    .addComponent(btnDelete)
                    .addComponent(btnOK)
                    .addComponent(btnCancel))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        int selectedRow = this.tbBook.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "You must choose one row");
        }
        else {
            boolean flag = true;
            int bookId = (int)this.tbBook.getValueAt(selectedRow, 0);
            for (int i = 0; i < borrowModel.getRowCount(); i++) {
                if (((int)this.tbBorrow.getValueAt(i, 0)) == bookId) {
                    flag = false;
                    JOptionPane.showMessageDialog(this, "You have already add this book");
                    break;
                }
            }
            if (flag) {
                Date date = Database.newDate(
                        (int)this.cbExDate.getSelectedItem(), 
                        this.cbExMonth.getSelectedIndex() + 1, 
                        (int)this.cbExYear.getSelectedItem());
                borrowModel.addRow(new Object[]{
                    tbBook.getValueAt(selectedRow, 0), 
                    tbBook.getValueAt(selectedRow, 1),
                    tbBook.getValueAt(selectedRow, 2),
                    df.format(Database.localDateToDate(LocalDate.now())),
                    df.format(date)
                });
            }
        }
    }//GEN-LAST:event_btnAddActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        int selectedRow = tbBorrow.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, 
                    "You must choose one borrow to delete");
        }
        else {
            borrowModel.removeRow(selectedRow);
        }
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnClearAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearAllActionPerformed
        int count = borrowModel.getRowCount();
        for (int i = 0; i < count; i++) {
            borrowModel.removeRow(0);
        }
    }//GEN-LAST:event_btnClearAllActionPerformed

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnCancelActionPerformed

    private void btnOKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOKActionPerformed
        int tbUserSelectedRow = this.tbUser.getSelectedRow();
        int borrowCount = borrowModel.getRowCount();
        if (tbUserSelectedRow == -1) {
            JOptionPane.showMessageDialog(this, "You must choose one user");
        } else if (borrowCount == 0) {
            JOptionPane.showMessageDialog(this, 
                    "You must add at least one book");
        } else {
            String username = (String)tbUser.getValueAt(tbUserSelectedRow, 0);
            Users user = null;
            for (Users item: this.listUser) {
                if (item.getUsername().equals(username)) {
                    user = item;
                    break;
                }
            }
            Borrows borrow = new Borrows();
            borrow.setBorrowUser(user);
            borrow.setBorrowDate(Database.localDateToDate(LocalDate.now()));
            ArrayList<BorrowDetails> listDetail = new ArrayList<>();
            for (int i = 0; i < borrowCount; i++) {
                int bookId = (int)this.tbBorrow.getValueAt(i, 0);
                Books book = null;
                BorrowDetails detail = new BorrowDetails();
                for (Books item: listBook){
                    if (item.getBookId() == bookId) {
                        book = item;
                        break;
                    }
                }
                detail.setBook(book);
                try {
                    detail.setExpirationDate(df.parse((String)this.tbBorrow.getValueAt(i, 4)));
                } catch (ParseException ex) {
                    Logger.getLogger(ManageBorrowBook.class.getName()).log(Level.SEVERE, null, ex);
                }
                listDetail.add(detail);
            }
            borrow.setBorrowDetailList(listDetail);
            if (borrow.save()) {
                System.out.println("OK");
                this.dispose();
            }
            else {
                System.out.println("Failed");
            }
        }
    }//GEN-LAST:event_btnOKActionPerformed

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
            java.util.logging.Logger.getLogger(ManageBorrowBook.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ManageBorrowBook.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ManageBorrowBook.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ManageBorrowBook.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ManageBorrowBook().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnClearAll;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnFilterBook;
    private javax.swing.JButton btnFilterName;
    private javax.swing.JButton btnOK;
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
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField6;
    private javax.swing.JTable tbBook;
    private javax.swing.JTable tbBorrow;
    private javax.swing.JTable tbUser;
    // End of variables declaration//GEN-END:variables
}
