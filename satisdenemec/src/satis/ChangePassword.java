package satis;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;

public class ChangePassword extends JFrame {
    private Connection conn;

    public ChangePassword(Connection conn) {
        this.conn = conn;
        // Frame özellikleri
        setTitle("Parola Değiştirme");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Ana panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel lblCurrentPassword = new JLabel("Mevcut Parola:");
        JLabel lblNewPassword = new JLabel("Yeni Parola:");
        JLabel lblConfirmPassword = new JLabel("Parolayı Onayla:");

        JPasswordField txtCurrentPassword = new JPasswordField(20);
        JPasswordField txtNewPassword = new JPasswordField(20);
        JPasswordField txtConfirmPassword = new JPasswordField(20);

        // Butonlar için ayrı bir panel
        JPanel buttonPanel = new JPanel();
        JButton btnChange = new JButton("Parolayı Değiştir");
        JButton btnCancel = new JButton("İptal");

        buttonPanel.add(btnChange);
        buttonPanel.add(btnCancel);

        // Her bir satırı panel olarak ekle
        mainPanel.add(createRowPanel(lblCurrentPassword, txtCurrentPassword));
        mainPanel.add(createRowPanel(lblNewPassword, txtNewPassword));
        mainPanel.add(createRowPanel(lblConfirmPassword, txtConfirmPassword));

        // Ana paneli ve buton panelini ekle
        add(mainPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        // Buton olayları
        btnChange.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String currentPassword = new String(txtCurrentPassword.getPassword());
                String newPassword = new String(txtNewPassword.getPassword());
                String confirmPassword = new String(txtConfirmPassword.getPassword());

                if (currentPassword.isEmpty() || newPassword.isEmpty() || confirmPassword.isEmpty()) {
                    JOptionPane.showMessageDialog(ChangePassword.this,
                            "Lütfen tüm alanları doldurun!", "Hata", JOptionPane.ERROR_MESSAGE);
                } else if (!newPassword.equals(confirmPassword)) {
                    JOptionPane.showMessageDialog(ChangePassword.this,
                            "Yeni parola ve onay parola eşleşmiyor!", "Hata", JOptionPane.ERROR_MESSAGE);
                } else {
                    DBbaglanti.ParolaDegis(Main.conn,newPassword);
                    JOptionPane.showMessageDialog(ChangePassword.this,
                            "Parola başarıyla değiştirildi!", "Başarılı", JOptionPane.INFORMATION_MESSAGE);
                    dispose(); // Pencereyi kapat
                }
            }
        });

        btnCancel.addActionListener(e -> dispose()); // İptal butonu pencereyi kapatır
    }

    private JPanel createRowPanel(JLabel label, JPasswordField textField) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));

        label.setPreferredSize(new Dimension(120, label.getPreferredSize().height));
        panel.add(label);
        panel.add(Box.createHorizontalStrut(10)); // Araya boşluk ekle
        panel.add(textField);

        return panel;
    }
}
