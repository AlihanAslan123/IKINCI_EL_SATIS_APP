package satis;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class UrunEkleFormu extends JFrame {

    private JTextField txtUrunAdi, txtFiyat, txtUrunBilgisi, txtTelefonNumarasi;
    private JComboBox<String> cmbKategori;
    private JLabel lblResim;
    private String resimYolu = null; // Resim yolunu tutacak
    private Connection conn;

    public UrunEkleFormu(Connection conn) {
        this.conn = conn; // Veritabanı bağlantısı
        setTitle("Ürün Ekle");
        setSize(400, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);

        // Ekranın ortasına konumlandırma
        int x = Helper.screenCenter("x", getSize());
        int y = Helper.screenCenter("y", getSize());
        setLocation(x, y);

        // Arka plan paneli oluşturuluyor
        JPanel backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Gradient renk geçişi
                Graphics2D g2d = (Graphics2D) g;
                GradientPaint gradient = new GradientPaint(0, 0, new Color(255, 94, 98),
                        0, getHeight(), new Color(137, 42, 255));
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        backgroundPanel.setBounds(0, 0, 400, 600);
        backgroundPanel.setLayout(null);
        add(backgroundPanel);

        // Ürün ekle paneli
        JPanel formPanel = new JPanel();
        formPanel.setLayout(null);
        formPanel.setBounds(50, 50, 300, 500);
        formPanel.setBackground(new Color(255, 255, 255, 230)); // Saydam beyaz arka plan
        formPanel.setBorder(new LineBorder(Color.LIGHT_GRAY, 2, true)); // Panel kenarlığı
        backgroundPanel.add(formPanel);

        // Ürün Adı
        JLabel lblUrunAdi = new JLabel("Ürün Adı:");
        lblUrunAdi.setBounds(20, 20, 100, 30);
        formPanel.add(lblUrunAdi);

        txtUrunAdi = new JTextField();
        txtUrunAdi.setBounds(120, 20, 150, 30);
        formPanel.add(txtUrunAdi);

        // Fiyat
        JLabel lblFiyat = new JLabel("Fiyat:");
        lblFiyat.setBounds(20, 70, 100, 30);
        formPanel.add(lblFiyat);

        txtFiyat = new JTextField();
        txtFiyat.setBounds(120, 70, 150, 30);
        formPanel.add(txtFiyat);

        // Ürün Bilgisi
        JLabel lblUrunBilgisi = new JLabel("Ürün Bilgisi:");
        lblUrunBilgisi.setBounds(20, 120, 100, 30);
        formPanel.add(lblUrunBilgisi);

        txtUrunBilgisi = new JTextField();
        txtUrunBilgisi.setBounds(120, 120, 150, 30);
        formPanel.add(txtUrunBilgisi);

        // Telefon Numarası
        JLabel lblTelefonNumarasi = new JLabel("Telefon No:");
        lblTelefonNumarasi.setBounds(20, 170, 100, 30);
        formPanel.add(lblTelefonNumarasi);

        txtTelefonNumarasi = new JTextField();
        txtTelefonNumarasi.setBounds(120, 170, 150, 30);
        formPanel.add(txtTelefonNumarasi);

        // Kategori Seçimi
        JLabel lblKategori = new JLabel("Kategori:");
        lblKategori.setBounds(20, 220, 100, 30);
        formPanel.add(lblKategori);

        cmbKategori = new JComboBox<>(new String[]{"Ev Eşyası", "Araba", "Motor", "Telefon", "Tablet", "Bilgisayar", "Diğer"});
        cmbKategori.setBounds(120, 220, 150, 30);
        formPanel.add(cmbKategori);

        // Resim Ekle
        JButton btnResimEkle = new JButton("Resim Ekle");
        btnResimEkle.setBounds(20, 270, 120, 30);
        formPanel.add(btnResimEkle);

        lblResim = new JLabel();
        lblResim.setBounds(150, 270, 120, 100);
        lblResim.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        formPanel.add(lblResim);

        btnResimEkle.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Resim Seç");
                fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Resim Dosyaları", "jpg", "png", "jpeg"));
                int result = fileChooser.showOpenDialog(null);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    resimYolu = selectedFile.getAbsolutePath();

                    ImageIcon originalIcon = new ImageIcon(resimYolu);
                    Image scaledImage = originalIcon.getImage().getScaledInstance(lblResim.getWidth(), lblResim.getHeight(), Image.SCALE_SMOOTH);
                    lblResim.setIcon(new ImageIcon(scaledImage));
                }
            }
        });

        // Gönder Butonu
        JButton btnGonder = new JButton("Gönder");
        btnGonder.setBounds(90, 380, 120, 30);
        formPanel.add(btnGonder);

        btnGonder.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TextField'lardan ve ComboBox'tan verileri al
                String urunAdi = txtUrunAdi.getText();
                double fiyat = Double.parseDouble(txtFiyat.getText());
                String urunBilgisi = txtUrunBilgisi.getText();
                String telefonNumarasi = txtTelefonNumarasi.getText();
                String kategoriAdi = (String) cmbKategori.getSelectedItem();

                // Giriş yapan kullanıcının ID'sini al
                // Bu ID'yi giriş işlemi yapan kullanıcından almalısınız
                int kullaniciId = DBbaglanti.kullanici.getKullanici_id();

                // Veritabanına kaydetme
                kaydetUrun(kullaniciId, urunAdi, fiyat, urunBilgisi, resimYolu, telefonNumarasi, kategoriAdi);
            }
        });

        setVisible(true);
    }

    private void kaydetUrun(int kullaniciId, String urunAdi, double fiyat, String urunBilgisi, String resimYolu, String telefonNumarasi, String kategoriAdi) {
        try {
            // Kategori ID'sini almak için sorgu
            String sqlKategoriId = "SELECT kategori_id FROM tbl_kategori WHERE kategori_adi = ?";
            PreparedStatement psKategori = conn.prepareStatement(sqlKategoriId);
            psKategori.setString(1, kategoriAdi); // Kategori ismini parametre olarak veriyoruz
            ResultSet rs = psKategori.executeQuery();

            // Kategori ID'sini alıyoruz
            int kategoriId = -1;
            if (rs.next()) {
                kategoriId = rs.getInt("kategori_id");
            }
            rs.close(); // Sonuçları kapatıyoruz.

            // Kategori ID'si bulunamazsa hata mesajı verilir
            if (kategoriId == -1) {
                JOptionPane.showMessageDialog(null, "Kategori bulunamadı!");
                return;
            }

            // Ürünü veritabanına ekliyoruz
            String sql = "INSERT INTO tbl_urun (kullanici_id, kategori_id, baslik, aciklama, fiyat, resim_url, telefon_numara) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, kullaniciId); // Giriş yapan kullanıcı ID'si
            ps.setInt(2, kategoriId); // Kategori ID'si
            ps.setString(3, urunAdi); // Ürün adı
            ps.setString(4, urunBilgisi); // Ürün açıklaması
            ps.setDouble(5, fiyat); // Ürün fiyatı
            ps.setString(6, resimYolu); // Resim URL'si
            ps.setString(7, telefonNumarasi); // Telefon numarası

            // Veriyi ekle
            ps.executeUpdate();
            JOptionPane.showMessageDialog(null, "Ürün başarıyla kaydedildi.");

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Hata: " + e.getMessage());
        }
    }
}
