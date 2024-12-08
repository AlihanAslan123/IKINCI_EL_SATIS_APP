package satis;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;

public class GecmisIlan extends JFrame {
    private Connection conn;

    public GecmisIlan(Connection conn){
        this.conn = conn;
        setTitle("Geçmiş ilanlarım");
        setSize(800,600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false); // Pencerinin yeniden boyutlandırılmasını engeller.

        // Kodu Ekranın ortasına getirelim
        int x = Helper.screenCenter("x",getSize());
        int y = Helper.screenCenter("y",getSize());
        setLocation(x,y);

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


        JPanel urunPaneli = new JPanel();
        urunPaneli.setLayout(new BoxLayout(urunPaneli,BoxLayout.Y_AXIS));

        // Scroll paneli
        JScrollPane scrollPane = new JScrollPane(urunPaneli);
        scrollPane.setBounds(70,0,600,getHeight());
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED); // Gerektiğinde kaydırıcıyı göster
        scrollPane.setPreferredSize(new Dimension(600, getHeight())); // Scroll pane boyutunu ayarla
        backgroundPanel.add(scrollPane);

        Helper.paylastigimUrunler(urunPaneli);


        setVisible(true);

    }

}
