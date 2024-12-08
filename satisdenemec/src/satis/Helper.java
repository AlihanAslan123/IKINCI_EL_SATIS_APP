package satis;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class Helper {

    // Formun pozisyonunu ekranın ortasına getiren method
    public static int screenCenter(String eksen, Dimension size){
        int point = 0;
        switch (eksen){
            case "x":
                point = (Toolkit.getDefaultToolkit().getScreenSize().width - size.width) / 2;
                break;
            case "y":
                point = (Toolkit.getDefaultToolkit().getScreenSize().height - size.height) / 2;
        }

        return point;
    }

    // Tema değiştiren metot
    public static void setLayout()
    {
        for(UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()){
            if ("Nimbus".equals(info.getName())){
                try {
                    UIManager.setLookAndFeel(info.getClassName());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            }
        }
    }



    public static void getir(JPanel urunPaneli){

        //Veritabanından ürünleri alıyoruz.
        List<Urun> urunler = Urun.getUrun();

        //Ürünleri panele ekliyoruz.
        for(Urun urun : urunler){

            // Ürün başlığı
            JLabel titleLabel = new JLabel(urun.getBaslik());
            titleLabel.setFont(new Font("Arial",Font.BOLD,22));
            titleLabel.setForeground(new Color(0, 148, 141));
            urunPaneli.add(titleLabel);

            //Ürün resmi
            ImageIcon orjinalIcon = new ImageIcon(urun.getResim_yolu());
            Image image = orjinalIcon.getImage().getScaledInstance(200,150,Image.SCALE_SMOOTH);
            JLabel imageLabel = new JLabel(new ImageIcon(image));
            urunPaneli.add(imageLabel);

            urunPaneli.add(Box.createRigidArea(new Dimension(0, 15))); // 15 piksel boşluk ekle

            // Fiyat
            String[] fiyat = String.valueOf(urun.getFiyat()).split("\\.");
            JLabel fiyatLabel = new JLabel("Fiyat: "+fiyat[0]+" TL");
            fiyatLabel.setFont(new Font("Arial",Font.ITALIC,18));
            fiyatLabel.setForeground(new Color(0, 148, 141));
            urunPaneli.add(fiyatLabel);

            urunPaneli.add(Box.createRigidArea(new Dimension(0, 15))); // 15 piksel boşluk ekle

            // Ürün açıklaması
            JTextArea descArea = new JTextArea(urun.getAciklama());
            descArea.setFont(new Font("Arial", Font.PLAIN, 12));
            descArea.setLineWrap(true); // Satırları sar
            descArea.setWrapStyleWord(true); // Kelime sarmayı etkinleştir
            descArea.setOpaque(true); // Arka planı beyaz yap
            descArea.setEditable(false); // Düzenlenemez yap
            descArea.setPreferredSize(new Dimension(60, 60)); // Kutu boyutunu sabitle

            JScrollPane sp = new JScrollPane(descArea,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
            sp.setPreferredSize(new Dimension(60, 60));
            urunPaneli.add(sp);



            urunPaneli.add(Box.createRigidArea(new Dimension(0, 10))); // 15 piksel boşluk ekle
            // Telefon numarası
            JLabel phoneLabel = new JLabel("İletişim: +90 "+urun.getTelNo());
            phoneLabel.setFont(new Font("Arial",Font.ITALIC,17));
            phoneLabel.setForeground(new Color(0, 148, 141));
            urunPaneli.add(phoneLabel);
            urunPaneli.add(Box.createRigidArea(new Dimension(0, 35))); // 15 piksel boşluk ekle

            // Paneli Kenarlıkla ayır
            urunPaneli.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.GRAY));


        }

    }


    public static void getirFiltreli(JPanel panel, String filtre, Double minTutar, Double maxTutar){

        List<Urun> urunler = Urun.getUrunFiltreli(filtre);
        for(Urun urun : urunler){
            boolean uygun=true;

            if (urun.getFiyat() < minTutar){uygun = false;}
            if (urun.getFiyat() > maxTutar){uygun = false;}

            if(uygun){

                // Ürün başlığı
                JLabel titleLabel = new JLabel(urun.getBaslik());
                titleLabel.setFont(new Font("Arial",Font.BOLD,22));
                panel.add(titleLabel);

                //Ürün resmi
                ImageIcon orjinalIcon = new ImageIcon(urun.getResim_yolu());
                Image image = orjinalIcon.getImage().getScaledInstance(200,150,Image.SCALE_SMOOTH);
                JLabel imageLabel = new JLabel(new ImageIcon(image));
                panel.add(imageLabel);

                panel.add(Box.createRigidArea(new Dimension(0, 15))); // 15 piksel boşluk ekle

                // Fiyat
                String[] fiyat = String.valueOf(urun.getFiyat()).split("\\.");
                JLabel fiyatLabel = new JLabel("Fiyat: "+fiyat[0]+" TL");
                fiyatLabel.setFont(new Font("Arial",Font.ITALIC,18));
                fiyatLabel.setForeground(new Color(0, 148, 141));
                panel.add(fiyatLabel);

                panel.add(Box.createRigidArea(new Dimension(0, 15))); // 15 piksel boşluk ekle

                // Ürün açıklaması
                JTextArea descArea = new JTextArea(urun.getAciklama());
                descArea.setFont(new Font("Arial", Font.PLAIN, 12));
                descArea.setLineWrap(true); // Satırları sar
                descArea.setWrapStyleWord(true); // Kelime sarmayı etkinleştir
                descArea.setOpaque(true); // Arka planı beyaz yap
                descArea.setEditable(false); // Düzenlenemez yap
                descArea.setPreferredSize(new Dimension(60, 60)); // Kutu boyutunu sabitle

                JScrollPane sp = new JScrollPane(descArea,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
                sp.setPreferredSize(new Dimension(60, 60));
                panel.add(sp);



                panel.add(Box.createRigidArea(new Dimension(0, 10))); // 15 piksel boşluk ekle
                // Telefon numarası
                JLabel phoneLabel = new JLabel("İletişim: +90 "+urun.getTelNo());
                phoneLabel.setFont(new Font("Arial",Font.ITALIC,17));
                phoneLabel.setForeground(new Color(0, 148, 141));
                panel.add(phoneLabel);
                panel.add(Box.createRigidArea(new Dimension(0, 35))); // 15 piksel boşluk ekle

                // Paneli Kenarlıkla ayır
                panel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.GRAY));

            }

        }

    }


    public static void paylastigimUrunler(JPanel jpanel){

        List<Urun> urunler = Urun.getPaylasimlarim(); // Geçmişte programda paylaştığım ürünleri getirir.
        for(Urun urun: urunler){

            JButton btnRemove = new JButton("Paylaşımı Kaldır");
            btnRemove.setBounds(40,20,100,30);
            btnRemove.setFont(new Font("Arial",Font.PLAIN, 16));
            btnRemove.setBackground(new Color(153, 15, 29));
            btnRemove.setForeground(Color.WHITE);
            btnRemove.setFocusPainted(false);
            btnRemove.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // User bu butona basınca o paylaştığı ürün kalkar.
                    DBbaglanti.UrunKaldir(Main.conn, urun.getUrunID());
                }
            });
            jpanel.add(btnRemove);

            JLabel titleLabel = new JLabel(urun.getBaslik()); // başlık
            titleLabel.setFont(new Font("Arial",Font.BOLD,22));
            jpanel.add(titleLabel);

            ImageIcon orijinalIcon = new ImageIcon(urun.getResim_yolu()); // resim
            Image image = orijinalIcon.getImage().getScaledInstance(200,150,Image.SCALE_SMOOTH);
            JLabel imageLabel = new JLabel(new ImageIcon(image));
            jpanel.add(imageLabel);

            jpanel.add(Box.createRigidArea(new Dimension(0,15))); // 15 pisksel boşkuk

            //Fiyat
            String[] fiyat = String.valueOf(urun.getFiyat()).split("\\.");
            JLabel fiyatLabel = new JLabel("Fiyat: "+fiyat[0]+" TL");
            fiyatLabel.setFont(new Font("Arial",Font.ITALIC,18));
            fiyatLabel.setForeground(new Color(0, 148, 141));
            jpanel.add(fiyatLabel);

            jpanel.add(Box.createRigidArea(new Dimension(0,15))); // 15 pisksel boşkuk

            // Ürün açıklaması
            JTextArea descArea = new JTextArea(urun.getAciklama());
            descArea.setFont(new Font("Arial", Font.PLAIN, 12));
            descArea.setLineWrap(true); // Satırları sar
            descArea.setWrapStyleWord(true); // Kelime sarmayı etkinleştir
            descArea.setOpaque(true); // Arka planı beyaz yap
            descArea.setEditable(false); // Düzenlenemez yap
            descArea.setPreferredSize(new Dimension(60, 60)); // Kutu boyutunu sabitle

            JScrollPane sp = new JScrollPane(descArea,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
            sp.setPreferredSize(new Dimension(60, 60));
            jpanel.add(sp);



            jpanel.add(Box.createRigidArea(new Dimension(0, 10))); // 15 piksel boşluk ekle
            // Telefon numarası
            JLabel phoneLabel = new JLabel("İletişim: +90 "+urun.getTelNo());
            phoneLabel.setFont(new Font("Arial",Font.ITALIC,17));
            phoneLabel.setForeground(new Color(0, 148, 141));
            jpanel.add(phoneLabel);
            jpanel.add(Box.createRigidArea(new Dimension(0, 35))); // 15 piksel boşluk ekle

            // Paneli Kenarlıkla ayır
            jpanel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.GRAY));


        }

    }



}
