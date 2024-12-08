package satis;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;

public class KayitUI {

    // Giriş alanları tanımlanıyor
    static JTextField adField, telNoField;
    static JPasswordField sifreField, sifreTekrarField;

    public static void start() {
        // Ana pencere oluşturuluyor ve ayarları yapılıyor
        JFrame frame = new JFrame("Kayıt Ol");
        frame.setSize(400, 700);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);
        frame.setResizable(false);

        // Ekranın ortasına pencereyi konumlandır
        int x = Helper.screenCenter("x", frame.getSize());
        int y = Helper.screenCenter("y", frame.getSize());
        frame.setLocation(x, y);

        // Arka plan paneli oluşturuluyor
        JPanel backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Arka plan renk geçişi ayarlanıyor
                Graphics2D g2d = (Graphics2D) g;
                GradientPaint gradient = new GradientPaint(0, 0, new Color(255, 94, 98),
                        0, getHeight(), new Color(137, 42, 255));
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        backgroundPanel.setBounds(0, 0, 400, 700);
        frame.add(backgroundPanel);
        backgroundPanel.setLayout(null);

        // Kayıt paneli oluşturuluyor
        JPanel registerPanel = new JPanel();
        registerPanel.setLayout(null);
        registerPanel.setBounds(50, 100, 300, 500);
        registerPanel.setBackground(new Color(255, 255, 255, 230)); // Saydam beyaz arka plan
        registerPanel.setBorder(new LineBorder(Color.LIGHT_GRAY, 2, true)); // Kenarlık ekleniyor

        // Başlık etiketi ekleniyor
        JLabel titleLabel = new JLabel("Kayıt Ol", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setBounds(0, 10, 300, 40);
        registerPanel.add(titleLabel);

        // Giriş alanlarının yüksekliği ve aralıkları tanımlanıyor
        int fieldHeight = 40;
        int spacing = 20;
        int yPosition = 70;

        // Kullanıcı adı alanı oluşturuluyor
        JPanel k_adPanel = createInputFieldWithIcon("Kullanıcı Adı", "src/resimler/user-icon.png", 24, 24);
        k_adPanel.setBounds(30, yPosition, 240, fieldHeight);
        registerPanel.add(k_adPanel);

        yPosition += fieldHeight + spacing;

        // Telefon numarası alanı oluşturuluyor
        JPanel telNoPanel = createInputFieldWithIcon("Telefon Numaranız", "src/resimler/user-icon.png", 24, 24);
        telNoPanel.setBounds(30, yPosition, 240, fieldHeight);
        registerPanel.add(telNoPanel);

        yPosition += fieldHeight + spacing;

        // Şifre alanı oluşturuluyor
        JPanel passwordPanel = createPasswordFieldWithIcon("Şifre", "src/resimler/lock-icon.png", 24, 24);
        passwordPanel.setBounds(30, yPosition, 240, fieldHeight);
        registerPanel.add(passwordPanel);

        yPosition += fieldHeight + spacing;

        // Şifre tekrar alanı oluşturuluyor
        JPanel passwordRepeatPanel = createPasswordFieldWithIcon("Şifre Tekrar", "src/resimler/lock-icon.png", 24, 24);
        passwordRepeatPanel.setBounds(30, yPosition, 240, fieldHeight);
        registerPanel.add(passwordRepeatPanel);

        // Kayıt butonu oluşturuluyor
        JButton registerButton = new JButton("Kayıt Ol");
        registerButton.setBounds(30, yPosition + fieldHeight + spacing, 240, 50);
        registerButton.setForeground(Color.WHITE); // Yazı rengi
        registerButton.setFont(new Font("Arial", Font.BOLD, 16));
        registerButton.setFocusPainted(false);
        registerButton.setBackground(new Color(94, 156, 255)); // Buton arka plan rengi
        registerButton.setBorder(new LineBorder(Color.BLUE, 2, true));

        // Kayıt butonuna tıklanma işlemi ekleniyor
        registerButton.addActionListener(e -> {
            // Giriş alanlarındaki veriler alınıyor
            String ad = adField.getText();
            String strtel = telNoField.getText();
            String sifre = new String(sifreField.getPassword());
            String sifreTekrar = new String(sifreTekrarField.getPassword());

            // null değer girilmesin kontrolü
            if (ad.equals(" ") || strtel.equals(" ") || ad.equals("Kullanıcı adınız") || strtel.equals("Telefon Numaranız")) {
                JOptionPane.showMessageDialog(frame, "Lütfen tüm alanları doldurun!");
            }else{
                // Şifreler eşleşiyor mu kontrol ediliyor
                if (!sifre.equals(sifreTekrar)) {
                    JOptionPane.showMessageDialog(frame, "Şifreler eşleşmiyor!"+"Ad:"+ad+" soyad: "+strtel);
                } else {
                    DBbaglanti.KullaniciKaydi(Main.conn,ad,sifre,strtel);
                    frame.dispose();
                    CustomLoginUI.start();
                }
            }




        });

        registerPanel.add(registerButton); // Kayıt butonu panele ekleniyor
        backgroundPanel.add(registerPanel); // Kayıt paneli arka plana ekleniyor
        frame.setVisible(true); // Pencere görünür yapılıyor
    }

    // Kullanıcı adı, telefon gibi alanları oluşturmak için metot
    private static JPanel createInputFieldWithIcon(String placeholder, String iconPath, int iconWidth, int iconHeight) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(new LineBorder(Color.LIGHT_GRAY, 1, true)); // Kenarlık ayarı

        JLabel iconLabel = new JLabel(resizeIcon(iconPath, iconWidth, iconHeight)); // İkon etiketi
        iconLabel.setBorder(new EmptyBorder(0, 5, 0, 5)); // Boşluk ekleniyor
        panel.add(iconLabel, BorderLayout.WEST); // İkon sola yerleştiriliyor

        JTextField textField = new JTextField();
        textField.setBorder(new EmptyBorder(5, 5, 5, 5));
        textField.setFont(new Font("Arial", Font.PLAIN, 14));
        textField.setText(placeholder); // Placeholder metni
        textField.setForeground(Color.GRAY); // Placeholder rengi
        panel.add(textField, BorderLayout.CENTER);

        // Ad ve soyad alanları değişkenlere atanıyor
        if (placeholder.equals("Kullanıcı Adı")) adField = textField;
        if (placeholder.equals("Telefon Numaranız")) telNoField = textField;

        // Odaklanıldığında placeholder kayboluyor
        textField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (textField.getText().equals(placeholder)) {
                    textField.setText("");
                    textField.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (textField.getText().isEmpty()) {
                    textField.setForeground(Color.GRAY);
                    textField.setText(placeholder);
                }
            }
        });

        return panel;
    }

    // Şifre alanı oluşturmak için metot
    private static JPanel createPasswordFieldWithIcon(String placeholder, String iconPath, int iconWidth, int iconHeight) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(new LineBorder(Color.LIGHT_GRAY, 1, true));

        JLabel iconLabel = new JLabel(resizeIcon(iconPath, iconWidth, iconHeight));
        iconLabel.setBorder(new EmptyBorder(0, 5, 0, 5));
        panel.add(iconLabel, BorderLayout.WEST);

        JPasswordField passwordField = new JPasswordField();
        passwordField.setBorder(new EmptyBorder(5, 5, 5, 5));
        passwordField.setFont(new Font("Arial", Font.PLAIN, 14));
        passwordField.setEchoChar((char) 0); // Placeholder görünür

        passwordField.setText(placeholder); // Placeholder metni
        passwordField.setForeground(Color.GRAY);

        // Şifre alanında placeholder kontrolü
        passwordField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (passwordField.getText().equals(placeholder)) {
                    passwordField.setText("");
                    passwordField.setForeground(Color.BLACK);
                    passwordField.setEchoChar('•'); // Nokta olarak göster
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (passwordField.getPassword().length == 0) {
                    passwordField.setForeground(Color.GRAY);
                    passwordField.setText(placeholder);
                    passwordField.setEchoChar((char) 0); // Placeholder gösterimi
                }
            }
        });

        // Şifre alanları değişkenlere atanıyor
        if (placeholder.equals("Şifre")) sifreField = passwordField;
        if (placeholder.equals("Şifre Tekrar")) sifreTekrarField = passwordField;

        panel.add(passwordField, BorderLayout.CENTER);
        return panel;
    }

    // İkon boyutunu ayarlayan metot
    private static ImageIcon resizeIcon(String path, int width, int height) {
        ImageIcon icon = new ImageIcon(path);
        Image scaledImage = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(scaledImage);
    }
}
