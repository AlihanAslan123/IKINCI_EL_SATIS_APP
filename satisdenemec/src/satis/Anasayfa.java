package satis;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Enumeration;
import java.util.List;


public class Anasayfa extends JFrame {

    private JPanel pnl_sol;


    public Anasayfa() {

        setSize(1200, 760);
        setTitle("YENİ GİBİ");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Çarpıya basınca uygulamayı durdurur.

        // Pencereyi ekrana ortala
        int x = Helper.screenCenter("x", getSize());
        int y = Helper.screenCenter("y", getSize());
        setLocation(x, y);



        // Arka plan paneli renkli bir background
        JPanel backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                GradientPaint gradient = new GradientPaint(0, 0, new Color(255, 94, 98),
                        0, getHeight(), new Color(137, 42, 255));
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        backgroundPanel.setLayout(null);
        add(backgroundPanel);

        // Yan panel
        pnl_sol = new JPanel();
        //pnl_orta.setBackground(Color.WHITE);
        pnl_sol.setOpaque(true);  // Panelin opak olduğundan emin oluyoruz.
        pnl_sol.setLayout(null);
        pnl_sol.setBounds(0, 0, 200, 10000); // Sola hizalanmış şekilde konumlandır
        backgroundPanel.add(pnl_sol);


        // Resim ekliyoruz.
        ImageIcon imageIcon = new ImageIcon("src/resimler/logo.png"); // Resmin yolunu belirliyoruz..
        JLabel lbl_resim = new JLabel(imageIcon);
        lbl_resim.setBounds(19, 40, 120, 120); // Resmin doğru boyutlarını al
        pnl_sol.add(lbl_resim);

        // Filtreler Başlığı
        JLabel lbl_Flt = new JLabel("FİLTRELER");
        lbl_Flt.setFont(new Font("Georgia",Font.ITALIC,28)); // Yazı Tipini belirlem
        lbl_Flt.setForeground(new Color(0, 148, 141)); // yazı rengi
        lbl_Flt.setBounds(15,180,200,60); // labelin pozisyonunu belirtir.
        pnl_sol.add(lbl_Flt);

        // Radio butonları ekleme
        String[] filtreler = {"Ev Eşyası","Araba","Motor","Telefon","Tablet","Bilgisayar","Diğer"};
        ButtonGroup grup = new ButtonGroup(); // Radio butonları gruplandırıyoruz.İki tane aynı anda seçilmesin diye

        int yOffset = 240; //ilk radiobutonun y eksenindeki konumu

        for (String filtre : filtreler) {
            JRadioButton radioButton = new JRadioButton(filtre);
            radioButton.setBounds(15, yOffset, 150, 30); // Radio butonunun konumunu ayarla
            radioButton.setBackground(Color.WHITE); // Arka plan rengi
            radioButton.setFont(new Font("Arial", Font.PLAIN, 16)); // Yazı fontu
            radioButton.setForeground(new Color(0, 148, 141)); // Yazı rengi
            radioButton.setFocusPainted(false); // Seçim sırasında kenarlık görünümünü kaldır
            radioButton.setContentAreaFilled(false); // Butonun arka planını görünmez yap

            // Seçim durumunu kontrol et ve rengi değiştir
            radioButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Tüm radio butonlarının rengini eski haline getir
                    Enumeration<AbstractButton> buttons = grup.getElements(); // Enumeration koleksiyonları gezmek için kullanılan arayüzdür.
                    while (buttons.hasMoreElements()) {
                        AbstractButton button = buttons.nextElement();
                        button.setForeground(new Color(0, 148, 141)); // Eski rengi ayarla
                    }
                    // Seçili olan butonun rengini değiştir
                    radioButton.setForeground(new Color(255, 94, 98)); // Seçildiğinde rengi değiştir
                }
            });

            grup.add(radioButton); // Radio butonunu gruba ekle
            pnl_sol.add(radioButton); // Radio butonunu panelde göster
            yOffset += 35; // Bir sonraki radio butonu için y konumunu güncelle
        }

        // Min Tutar label ve text field ekle
        JLabel lbl_MinTutar = new JLabel("Min Tutar:");
        lbl_MinTutar.setFont(new Font("Arial", Font.PLAIN, 16));
        lbl_MinTutar.setBounds(15, yOffset, 100, 30);
        pnl_sol.add(lbl_MinTutar);

        JTextField txt_MinTutar = new JTextField();
        txt_MinTutar.setBounds(15, yOffset + 30, 150, 30);
        pnl_sol.add(txt_MinTutar);

        yOffset += 70; // Max Tutar için boşluk bırak

        // Max Tutar label ve text field ekle
        JLabel lbl_MaxTutar = new JLabel("Max Tutar:");
        lbl_MaxTutar.setFont(new Font("Arial", Font.PLAIN, 16));
        lbl_MaxTutar.setBounds(15, yOffset, 100, 30);
        pnl_sol.add(lbl_MaxTutar);

        JTextField txt_MaxTutar = new JTextField();
        txt_MaxTutar.setBounds(15, yOffset + 30, 150, 30);
        pnl_sol.add(txt_MaxTutar);



        // Ürünleri gösterecek panel (sağ tarafta)
        JPanel urunPaneli = new JPanel();
        urunPaneli.setLayout(new BoxLayout(urunPaneli,BoxLayout.Y_AXIS));

        // Scroll paneli
        JScrollPane scrollPane = new JScrollPane(urunPaneli);
        scrollPane.setBounds(220,0,750,getHeight());
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED); // Gerektiğinde kaydırıcıyı göster
        scrollPane.setPreferredSize(new Dimension(750, getHeight())); // Scroll pane boyutunu ayarla
        backgroundPanel.add(scrollPane);




        //Buton ekleme
        JButton btn_ara = new JButton("Tamam");
        btn_ara.setBounds(15, yOffset+70, 150, 30);
        btn_ara.setFont(new Font("Arial", Font.PLAIN, 16));
        btn_ara.setBackground(new Color(0, 148, 141));
        btn_ara.setForeground(Color.WHITE);
        btn_ara.setFocusPainted(false); // Kenarlık görünümünü kaldır
        btn_ara.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String seciliFiltre = null;
                Enumeration<AbstractButton> buttons = grup.getElements();
                while(buttons.hasMoreElements()){
                    AbstractButton button = buttons.nextElement();
                    if (button.isSelected()) {
                        seciliFiltre = button.getText(); // Seçili butonun metni (filtre)
                        break;
                    }
                }

                String minTutarStr = txt_MinTutar.getText().trim();
                String maxTutarStr = txt_MaxTutar.getText().trim();
                Double minTutar = null;
                Double maxTutar = null;


                try {
                    if (!minTutarStr.isEmpty()) minTutar = Double.parseDouble(minTutarStr);
                    if (!maxTutarStr.isEmpty()) maxTutar = Double.parseDouble(maxTutarStr);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Lütfen geçerli bir tutar giriniz.", "Hata", JOptionPane.ERROR_MESSAGE);
                    return; // İşlemi durdur
                }
                if(minTutarStr.equals("")){
                    minTutar = 0.0;
                }if(maxTutarStr.equals("")){maxTutar = 9999999999999999999999999.9;}

                urunPaneli.removeAll(); // Paneli temizle
                Helper.getirFiltreli(urunPaneli, seciliFiltre, minTutar, maxTutar); // Filtreli ürünleri getir
                urunPaneli.revalidate();
                urunPaneli.repaint();
            }
        });
        pnl_sol.add(btn_ara);
        yOffset += 50; // Max Tutar için boşluk bırak

        //Buton ekleme
        JButton btn_siparis = new JButton("Ürün Ekle");
        btn_siparis.setBounds(15, yOffset+70, 150, 30);
        btn_siparis.setFont(new Font("Arial", Font.PLAIN, 16));
        btn_siparis.setBackground(new Color(0, 148, 141));
        btn_siparis.setForeground(Color.WHITE);
        btn_siparis.setFocusPainted(false); // Kenarlık görünümünü kaldır
        btn_siparis.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // UrunEkleFormu oluştur ve aç
                new UrunEkleFormu(Main.conn);
            }
        });
        pnl_sol.add(btn_siparis);



        // anasayfadaki ürünleri getiricek.
        Helper.getir(urunPaneli);



        // Sağ panel
        JPanel pnl_sag = new JPanel();
        pnl_sag.setLayout(null); // Özel konumlandırma için null layout
        pnl_sag.setBounds(970, 0, 200, getHeight()); // Sağ tarafa hizalanmış şekilde konumlandır
        pnl_sag.setBackground(new Color(200, 200, 200)); // Arka plan rengini gri yap
        backgroundPanel.add(pnl_sag);

        // Sağ panele foto ekleme
        String imagePath = DBbaglanti.kullanici.getProfilfoto();// resim yolunu alalım.
        if(imagePath == null || imagePath.isEmpty()){ // eğere kullanici profil fotosu eklemezse varsayılan bir pp ekle
            imagePath = "src/resimler/hesabim.png";
        }
        ImageIcon imageIcon1 = new ImageIcon(imagePath); // Resmi yükle
        Image originalImage = imageIcon1.getImage(); // Image nesnesi al
        // JLabel'in boyutlarına göre resmi ölçeklendirdim.
        Image scaledImage = originalImage.getScaledInstance(100, 150, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);
        // JLabel oluşturdum ve boyutlandırılmış resmi ekledim
        JLabel lblPP = new JLabel(scaledIcon);
        lblPP.setBounds(50, 20, 100, 150); // Pozisyon ve boyut ayarla
        lblPP.setHorizontalAlignment(SwingConstants.CENTER); // Ortala (isteğe bağlı)
        // JLabel'i panele ekle
        pnl_sag.add(lblPP);


        // Sağ panele profil değişme butonu ekleme
        JButton btnPP = new JButton("Profil fotosu değiştir");
        btnPP.setBounds(15, 190, 160, 30);
        btnPP.setFont(new Font("Arial", Font.PLAIN, 14));
        btnPP.setBackground(new Color(0, 148, 141));
        btnPP.setForeground(Color.WHITE);
        btnPP.setFocusPainted(false); // Kenarlık görünümünü kaldır
        btnPP.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Kullanıcıdan resim dosyası seçmesini iste
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Profil Fotoğrafı Seç");
                fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Resim Dosyaları", "jpg", "png", "jpeg"));

                int result = fileChooser.showOpenDialog(null);
                if (result == JFileChooser.APPROVE_OPTION) {
                    // Seçilen dosya
                    String imagePath = fileChooser.getSelectedFile().getAbsolutePath();
                    // Resmi yükle ve boyutlandır
                    ImageIcon originalIcon = new ImageIcon(imagePath);
                    Image scaledImage = originalIcon.getImage().getScaledInstance(lblPP.getWidth(), lblPP.getHeight(), Image.SCALE_SMOOTH);
                    lblPP.setIcon(new ImageIcon(scaledImage));
                    DBbaglanti.setProfilfoto(Main.conn,imagePath);
                }

            }
        });
        pnl_sag.add(btnPP);

        // Sağ panele hesap ayarları butonu ekleme
        JButton btnpass = new JButton("Yeni parola ayarla");
        btnpass.setBounds(20, 240, 150, 30);
        btnpass.setFont(new Font("Arial", Font.PLAIN, 14));
        btnpass.setBackground(new Color(0, 148, 141));
        btnpass.setForeground(Color.WHITE);
        btnpass.setFocusPainted(false); // Kenarlık görünümünü kaldır
        btnpass.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ChangePassword changePassword = new ChangePassword(Main.conn);
                changePassword.setVisible(true);
            }
        });
        pnl_sag.add(btnpass);

        // geçmiş ilanlarımı gösteren buton
        JButton btn_ilan = new JButton("Paylaştığım ürünler");
        btn_ilan.setBounds(20, 290, 160, 30);
        btn_ilan.setFont(new Font("Arial", Font.PLAIN, 14));
        btn_ilan.setBackground(new Color(0, 148, 141));
        btn_ilan.setForeground(Color.WHITE);
        btn_ilan.setFocusPainted(false); // Kenarlık görünümünü kaldır
        btn_ilan.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GecmisIlan d = new GecmisIlan(Main.conn);
            }
        });
        pnl_sag.add(btn_ilan);



        // Yeniden çizim zorlaması pnl_yana resim ekledik. yeniden boyutlandırılmasını sağlıyoruz.
        pnl_sol.revalidate();
        pnl_sol.repaint();

        setVisible(true);
    }

}
