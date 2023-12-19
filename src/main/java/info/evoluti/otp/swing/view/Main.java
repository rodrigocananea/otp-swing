/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package info.evoluti.otp.swing.view;

import com.eatthepath.otp.TimeBasedOneTimePasswordGenerator;
import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.themes.FlatMacDarkLaf;
import com.formdev.flatlaf.themes.FlatMacLightLaf;
import com.google.zxing.WriterException;
import info.evoluti.otp.swing.util.Prefs;
import info.evoluti.otp.swing.util.Util;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.security.InvalidKeyException;
import java.time.Instant;
import javax.crypto.SecretKey;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.Timer;

/**
 *
 * @author Rodrigo Cananea <rodrigoaguiar35@gmail.com>
 */
public class Main extends javax.swing.JFrame {

    private Timer timer;

    private static TimeBasedOneTimePasswordGenerator totpGenerator;

    /**
     * Creates new form Main
     */
    public Main() {
        initComponents();

        totpGenerator = new TimeBasedOneTimePasswordGenerator();

        String secretKeyString = Prefs.getState().get(Prefs.SECRET_KEY, Util.generateBase32SecretKey("HmacSHA1", 160));
        SecretKey secretKey = Util.getSecretKeyFromEncodedString(secretKeyString, totpGenerator.getAlgorithm());

        jtfSecretKey.setText(secretKeyString);

        jtfUser.setText(Prefs.getState().get(Prefs.USER, "Test Name"));
        jtfEmail.setText(Prefs.getState().get(Prefs.EMAIL, "test@gmail.com"));

        jbCopy.putClientProperty(FlatClientProperties.STYLE_CLASS, "primary");
        jbVerify.putClientProperty(FlatClientProperties.STYLE_CLASS, "success");

        generateQrCode();

        timer = new Timer(1000, e -> {
            long currentEpochMilli = Instant.now().toEpochMilli();
            int timeRemaining = 30000 - (int) (currentEpochMilli % 30000); // 30 segundos em milissegundos

            int percentage = (int) ((timeRemaining / 30000.0) * 100);
            jpbProgress.setValue(percentage);
            jpbProgress.setString(String.format("%ds", timeRemaining / 1000));

            try {
                final Instant now1 = Instant.now();
                final int otp1 = totpGenerator.generateOneTimePassword(secretKey, now1);
                setCode(otp1);
            } catch (Exception ex) {
                ex.printStackTrace();
            }

        });
        timer.setRepeats(true);
        timer.start();
    }

    private void updateSecretKeyAndRestartTimer(String secretKeyString) {
        SecretKey secretKey = Util.getSecretKeyFromEncodedString(secretKeyString, totpGenerator.getAlgorithm());

        // Reinicia o timer para começar a gerar novos códigos OTP com a nova chave
        if (timer != null) {
            timer.stop();
        }

        timer = new Timer(1000, e -> {
            long currentEpochMilli = Instant.now().toEpochMilli();
            int timeRemaining = 30000 - (int) (currentEpochMilli % 30000); // 30 segundos em milissegundos

            int percentage = (int) ((timeRemaining / 30000.0) * 100);
            jpbProgress.setValue(percentage);
            jpbProgress.setString(String.format("%ds", timeRemaining / 1000));
            
            try {
                final int otp = totpGenerator.generateOneTimePassword(secretKey, Instant.ofEpochMilli(currentEpochMilli));
                setCode(otp);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        timer.setRepeats(true);
        timer.start();
    }

    private void generateQrCode() {
        try {
            String secretKeyString = Prefs.getState().get(Prefs.SECRET_KEY, Util.generateBase32SecretKey("HmacSHA1", 160));
            SecretKey secretKey = Util.getSecretKeyFromEncodedString(secretKeyString, totpGenerator.getAlgorithm());

            String uri = Util.generateUri(secretKey, Prefs.getState().get(Prefs.EMAIL, "test@gmail.com"), Prefs.getState().get(Prefs.USER, "Test Name"));
            jlQRCode.setIcon(new ImageIcon(Util.generateQRCode(uri)));

            final Instant now = Instant.now();
            final int otp = totpGenerator.generateOneTimePassword(secretKey, now);
            setCode(otp);

        } catch (WriterException | InvalidKeyException ex) {
            JOptionPane.showMessageDialog(this, "Problemas ao gerar QrCode, motivo:\n" + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void setCode(final int otp1) {
        jlCode.setText(String.format("%03d %03d", otp1 / 1000, otp1 % 1000));
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jpbProgress = new info.evoluti.otp.swing.components.spinner.SpinnerProgress();
        jlCode = new javax.swing.JLabel();
        jbCopy = new info.evoluti.otp.swing.components.Button();
        jtfVerifyCode = new javax.swing.JTextField();
        jbVerify = new info.evoluti.otp.swing.components.Button();
        jlInfo = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jlQRCode = new javax.swing.JLabel();
        jtfSecretKey = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jtfUser = new javax.swing.JTextField();
        jtfEmail = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jbGenerateSecret = new info.evoluti.otp.swing.components.Button();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("OTP 2FA");

        jPanel1.setBorder(javax.swing.BorderFactory.createEmptyBorder(12, 12, 12, 12));

        jpbProgress.setFont(new java.awt.Font("Segoe UI Semilight", 0, 18)); // NOI18N
        jpbProgress.setString("0s");
        jpbProgress.setStringPainted(true);

        jlCode.setFont(new java.awt.Font("Segoe UI Semibold", 0, 36)); // NOI18N
        jlCode.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlCode.setText("000 000");
        jlCode.setMinimumSize(new java.awt.Dimension(130, 40));
        jlCode.setPreferredSize(new java.awt.Dimension(130, 46));

        jbCopy.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/Copy.png"))); // NOI18N
        jbCopy.setText("Copiar");
        jbCopy.setPreferredSize(new java.awt.Dimension(120, 30));
        jbCopy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbCopyActionPerformed(evt);
            }
        });

        jtfVerifyCode.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jtfVerifyCode.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jtfVerifyCode.setPreferredSize(new java.awt.Dimension(100, 55));
        jtfVerifyCode.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jtfVerifyCodeFocusGained(evt);
            }
        });

        jbVerify.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/Verified Account.png"))); // NOI18N
        jbVerify.setPreferredSize(new java.awt.Dimension(26, 55));
        jbVerify.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbVerifyActionPerformed(evt);
            }
        });

        jlInfo.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(8, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jSeparator1)
                    .addComponent(jlInfo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jpbProgress, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jlCode, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(26, 26, 26)
                                .addComponent(jbCopy, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(9, 9, 9))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jtfVerifyCode, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jbVerify, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(15, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(jlCode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbCopy, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jpbProgress, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 12, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jbVerify, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jtfVerifyCode, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jlInfo, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(109, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        jPanel2.setBorder(javax.swing.BorderFactory.createEmptyBorder(12, 12, 12, 12));
        jPanel2.setPreferredSize(new java.awt.Dimension(320, 320));

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createEmptyBorder(6, 6, 6, 6));
        jPanel3.setLayout(new java.awt.BorderLayout());

        jlQRCode.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jPanel3.add(jlQRCode, java.awt.BorderLayout.CENTER);

        jtfSecretKey.setText("SECRET_KEY_HERE");

        jLabel1.setText("Chave:");

        jLabel2.setText("Usuário:");

        jtfUser.setText("Test Name");

        jtfEmail.setText("test@gmail.com");

        jLabel3.setText("E-mail:");

        jbGenerateSecret.setText("Gerar Secret");
        jbGenerateSecret.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbGenerateSecretActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jtfUser, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jtfEmail, javax.swing.GroupLayout.DEFAULT_SIZE, 176, Short.MAX_VALUE)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addComponent(jtfSecretKey)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jbGenerateSecret, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jtfUser, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addComponent(jtfEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jtfSecretKey, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jbGenerateSecret, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(17, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel2, java.awt.BorderLayout.WEST);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jbVerifyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbVerifyActionPerformed
        try {
            String userInput = jtfVerifyCode.getText().replaceAll("\\D", "");
            String secretKey = Prefs.getState().get(Prefs.SECRET_KEY, Util.generateBase32SecretKey("HmacSHA1", 160));
            final Instant now = Instant.now();
            final String otp = totpGenerator.generateOneTimePasswordString(Util.getSecretKeyFromEncodedString(secretKey, totpGenerator.getAlgorithm()), now);

            if (otp.equals(userInput)) {
                jlInfo.setText("Verification Successful");
            } else {
                jlInfo.setText("Verification Failed");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }//GEN-LAST:event_jbVerifyActionPerformed

    private void jbCopyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbCopyActionPerformed
        String code = jlCode.getText();
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        Transferable transferable = new StringSelection(code);
        clipboard.setContents(transferable, null);
    }//GEN-LAST:event_jbCopyActionPerformed

    private void jtfVerifyCodeFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jtfVerifyCodeFocusGained
        jtfVerifyCode.selectAll();
    }//GEN-LAST:event_jtfVerifyCodeFocusGained

    private void jbGenerateSecretActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbGenerateSecretActionPerformed
        try {
            // Gerar uma chave secreta codificada em Base32
            String secretKeyString = Util.generateBase32SecretKey("HmacSHA1", 160);

            // Atualizar o campo de texto da chave secreta na interface do usuário
            jtfSecretKey.setText(secretKeyString);

            // Salvar a chave secreta nas preferências
            Prefs.getState().put(Prefs.SECRET_KEY, secretKeyString);

            // Atualizar a chave secreta e reiniciar o timer
            updateSecretKeyAndRestartTimer(secretKeyString);

            // Atualizar o QR Code
            generateQrCode();

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erro ao gerar a chave secreta: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_jbGenerateSecretActionPerformed

    public static void main(String[] args) {
        Prefs.init("TwoFactorAuthenticator-Swing");

        FlatLaf.registerCustomDefaultsSource("tema");

        String tema = Prefs.getState().get(Prefs.TEMA, "claro");
        if ("claro".equals(tema)) {
            FlatMacLightLaf.setup();
        } else {
            FlatMacDarkLaf.setup();
        }

        try {
            EventQueue.invokeLater(() -> {
                new Main().setVisible(true);
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JSeparator jSeparator1;
    private info.evoluti.otp.swing.components.Button jbCopy;
    private info.evoluti.otp.swing.components.Button jbGenerateSecret;
    private info.evoluti.otp.swing.components.Button jbVerify;
    private javax.swing.JLabel jlCode;
    private javax.swing.JLabel jlInfo;
    private javax.swing.JLabel jlQRCode;
    private info.evoluti.otp.swing.components.spinner.SpinnerProgress jpbProgress;
    private javax.swing.JTextField jtfEmail;
    private javax.swing.JTextField jtfSecretKey;
    private javax.swing.JTextField jtfUser;
    private javax.swing.JTextField jtfVerifyCode;
    // End of variables declaration//GEN-END:variables
}
