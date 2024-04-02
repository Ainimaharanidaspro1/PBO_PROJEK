/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package login;
import user.menu_user;
import java.awt.HeadlessException;
import koneksi.*;
import transaksi.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import javax.swing.JOptionPane;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
/**
 *
 * @author Hafizha
 */

public class login_petugas extends javax.swing.JFrame {
Connection con;
Statement stat;
ResultSet rs;
String sql;
    /**
     * Creates new form login
     */
    public login_petugas() {
        initComponents();
        //pemanggilan fungsi koneksi database yang sudah kita buat pada class koneksi.java
//        koneksi DB = new koneksi();
        koneksi.getKoneksi();
    }

  

private void login(){
    try {
        Connection connect = koneksi.getKoneksi();
        Statement sttmnt = connect.createStatement();
        
        // Mengubah nilai password ke dalam bentuk hash SHA-256
        String hashedPassword = hashSHA256(txt_password.getText());
        String query = "SELECT * FROM tb_datauser WHERE username = '"+ txt_username.getText() +"' && password= '"+ hashedPassword +"' ";
        
        ResultSet go = sttmnt.executeQuery(query);
        
        int row = 0;
        while(go.next()){
            row = go.getRow();
        }
        if(row == 1){
            JOptionPane.showMessageDialog(null, "Login Success");
            
            new menu_user().setVisible(true);
            logActivity(txt_username.getText(), "Login");
            dispose();
            // Mengirim username ke dalam log_activity
            
        }else{
            JOptionPane.showMessageDialog(null, "Username atau Password Salah");
            txt_username.setText(null);
            txt_password.setText(null);
        }
        
    } catch(SQLException | HeadlessException e ){
        System.out.println(e);
    }
}

private String hashSHA256(String input) {
    try {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(input.getBytes());

        // Konversi byte array ke dalam bentuk string hex
        StringBuilder hexString = new StringBuilder();
        for (byte b : hash) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();
    } catch (NoSuchAlgorithmException e) {
        throw new RuntimeException(e);
    }
}
        

     // Method untuk mencatat aktivitas ke dalam log_activity
private void logActivity(String username, String activity) {
    try {
        Connection connect = koneksi.getKoneksi();
        if (connect != null) {
            String logQuery = "INSERT INTO log_activity (id_log, username, waktu, aktivitas) VALUES (DEFAULT, ?, ?, ?)";
            PreparedStatement logPst = connect.prepareStatement(logQuery);

            // Mengatur parameter
            logPst.setString(1, username);
            logPst.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
            logPst.setString(3, activity);

            // Melakukan eksekusi query
            int rowsAffected = logPst.executeUpdate();
            if (rowsAffected > 0) {
            } else {
                System.out.println("No activity logged for user " + username);
            }
        } else {
            System.out.println("Failed to establish database connection.");
        }
    } catch (SQLException e) {
        System.out.println("Error logging activity: " + e.getMessage());
        e.printStackTrace(); // Print stack trace for detailed error information
    }
}


  private String getLatestUsername() {
    String latestUsername = null;
    try (Connection connect = koneksi.getKoneksi()) {
        if (connect != null) {
            String query = "SELECT username FROM log_activity ORDER BY waktu DESC LIMIT 1";
            try (PreparedStatement pst = connect.prepareStatement(query)) {
                ResultSet rs = pst.executeQuery();
                if (rs.next()) {
                    latestUsername = rs.getString("username");
                }
            }
        }
    } catch (SQLException e) {
        System.out.println("Error getting latest username: " + e.getMessage());
        e.printStackTrace();
    }
    return latestUsername;
}

  // Menuliskan aktivitas logout ke dalam log_activity
 public void logout() {
    try {
        String username = getLatestUsername();
        logActivity(username, "Logout");
        System.out.println("Logout activity for user " + username + " logged successfully.");
    } catch (Exception ex) {
        System.out.println("Error during logout: " + ex.getMessage());
        ex.printStackTrace();
    }
}
 
 //melalukan aktivitas tambah barang
         public void TambahData() {
    try {
        String username =getLatestUsername();
        logActivity(username, "Menambah Barang");

        System.out.println("Add item activity for user " + username + " successfull.");
    } catch (Exception ex) {
        System.out.println("Error during add item activity: " + ex.getMessage());
        ex.printStackTrace(); 
    }
}
      
          //melalukan aktivitas hapus barang
    public void HapusData() {
    try {
        String username = getLatestUsername();
        logActivity(username, "Menghapus Barang");
        System.out.println("Delete item activity for user " + username + " successfull.");
    } catch (Exception ex) {
        System.out.println("Error during delete item activity: " + ex.getMessage());
        ex.printStackTrace(); 
    }
}
        //melalukan aktivitas edit barang  
    public void EditData() {
    try {
        String username = getLatestUsername();
        logActivity(username, "Mengedit Barang");
        System.out.println("Edit item activity for user " + username + " successfull.");
    } catch (Exception ex) {
        System.out.println("Error during edit item activity: " + ex.getMessage());
        ex.printStackTrace();
    }
}
              
     //melalukan aktivitas transaksi
            public void RiwayatTransaksi() {
    try {
        String username = getLatestUsername();
        logActivity(username, "Melakukan transaksi");
        System.out.println("Add transaction activity for user " + username + " successfull.");
    } catch (Exception ex) {
        System.out.println("Error during Add transaction activity: " + ex.getMessage());
        ex.printStackTrace(); 
    }
}

    /**
     * This method is called from withi
  

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        txt_username = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        txt_password = new javax.swing.JPasswordField();
        jLabel2 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        RegisterBtn = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(255, 204, 204));

        jLabel1.setBackground(new java.awt.Color(255, 204, 204));
        jLabel1.setFont(new java.awt.Font("Dialog", 1, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("ADMIN");

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        txt_username.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        txt_username.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txt_username.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_usernameActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("Username");

        jLabel5.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("Password");

        jButton1.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_unlock_30px.png"))); // NOI18N
        jButton1.setText("  LOGIN");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        txt_password.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        txt_password.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txt_password.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_passwordActionPerformed(evt);
            }
        });

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_registration_100px_1.png"))); // NOI18N

        jButton2.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_back_arrow_30px_5.png"))); // NOI18N
        jButton2.setText("  BACK");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        RegisterBtn.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        RegisterBtn.setText("Register Now");
        RegisterBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RegisterBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(165, 165, 165)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txt_username, javax.swing.GroupLayout.DEFAULT_SIZE, 320, Short.MAX_VALUE)
                    .addComponent(txt_password))
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(270, 270, 270)
                        .addComponent(jButton1))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addComponent(jButton2)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(0, 243, Short.MAX_VALUE)
                .addComponent(RegisterBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(227, 227, 227))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addComponent(jLabel2)
                .addGap(50, 50, 50)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txt_username, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txt_password, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton1)
                .addGap(32, 32, 32)
                .addComponent(RegisterBtn)
                .addGap(5, 5, 5)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
//       cekLogin();
        login();
       
       
//       dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        new menu_login().setVisible(true);
        dispose();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void txt_passwordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_passwordActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_passwordActionPerformed

    private void txt_usernameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_usernameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_usernameActionPerformed

    private void RegisterBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RegisterBtnActionPerformed
     new user.formRegister().setVisible(true);
    }//GEN-LAST:event_RegisterBtnActionPerformed

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
            java.util.logging.Logger.getLogger(login_petugas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(login_petugas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(login_petugas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(login_petugas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
       

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new login_petugas().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton RegisterBtn;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPasswordField txt_password;
    private javax.swing.JTextField txt_username;
    // End of variables declaration//GEN-END:variables

    private void log_activity(String text, String login) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    private Object formRegister() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}


