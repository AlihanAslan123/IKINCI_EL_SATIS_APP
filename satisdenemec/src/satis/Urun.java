package satis;

import javax.swing.plaf.nimbus.State;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Urun {
    private String UrunID;
    private String baslik;
    private String resim_yolu;
    private double fiyat;
    private String aciklama;
    private String telNo;
    private String kategori;

    public Urun(String baslik, String resim_yolu, double fiyat, String aciklama, String telNo, String kategori,String UrunID) {
        this.baslik = baslik;
        this.resim_yolu = resim_yolu;
        this.fiyat = fiyat;
        this.aciklama = aciklama;
        this.telNo = telNo;
        this.kategori = kategori;
        this.UrunID = UrunID;
    }

    public static List<Urun> getUrun(){
        List<Urun> urunler = new ArrayList<>();

        try {
            Statement stmt = Main.conn.createStatement();
            String sql = "Select * FROM tbl_urun";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()){
                String urun_id = String.valueOf(rs.getInt("urun_id"));
                String kategori_id = String.valueOf(rs.getInt("kategori_id"));
                String baslik = rs.getString("baslik");
                String resim_yolu = rs.getString("resim_url");
                double fiyat = rs.getDouble("fiyat");
                String aciklama = rs.getString("aciklama");
                String telNo = rs.getString("telefon_numara");
                urunler.add(new Urun(baslik,resim_yolu,fiyat,aciklama,telNo,kategori_id,urun_id));
            }
            rs.close();

        }catch (SQLException e){
            e.getStackTrace();
        }
        return urunler;
    }



    public static List<Urun> getUrunFiltreli(String filtre){
        List<Urun> urunler = new ArrayList<>();
        int kategoriid = 0;
        try {
            Statement stmt = Main.conn.createStatement();
            String sql = String.format("Select kategori_id FROM tbl_kategori where kategori_adi = '%s'",filtre);
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()){
                kategoriid = rs.getInt("kategori_id");
            }
            rs.close();
        } catch (Exception e) {
            e.getStackTrace();
        }



        try {
            Statement stmt = Main.conn.createStatement();
            String sql = String.format("Select * FROM tbl_urun where kategori_id = '%s'",kategoriid);
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()){
                String urun_id = String.valueOf(rs.getInt("urun_id"));
                String kategori_id = String.valueOf(rs.getInt("kategori_id"));
                String baslik = rs.getString("baslik");
                String resim_yolu = rs.getString("resim_url");
                double fiyat = rs.getDouble("fiyat");
                String aciklama = rs.getString("aciklama");
                String telNo = rs.getString("telefon_numara");
                urunler.add(new Urun(baslik,resim_yolu,fiyat,aciklama,telNo,kategori_id,urun_id));
            }
            rs.close();

        }catch (SQLException e){
            e.getStackTrace();
        }
        return urunler;
    }


    public static List<Urun> getPaylasimlarim(){
        List<Urun> urunler = new ArrayList<>();
        try{
            Statement stmt = Main.conn.createStatement();
            String sql = String.format("Select * From tbl_urun where kullanici_id = '%s'",DBbaglanti.kullanici.getKullanici_id());
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()){
                String urun_id = String.valueOf(rs.getInt("urun_id"));
                String kategori_id = String.valueOf(rs.getInt("kategori_id"));
                String baslik = rs.getString("baslik");
                String resim_yolu = rs.getString("resim_url");
                double fiyat = rs.getDouble("fiyat");
                String aciklama = rs.getString("aciklama");
                String telNo = rs.getString("telefon_numara");
                urunler.add(new Urun(baslik,resim_yolu,fiyat,aciklama,telNo,kategori_id,urun_id));
            }
            rs.close();
        }catch (Exception e){
            e.getStackTrace();
        }
        return urunler;
    }



    public String getUrunID(){
        return UrunID;
    }

    public String getKategori(){
        return kategori;
    }

    public String getBaslik() {
        return baslik;
    }

    public String getResim_yolu() {
        return resim_yolu;
    }

    public double getFiyat() {
        return fiyat;
    }

    public String getAciklama() {
        return aciklama;
    }

    public String getTelNo() {
        return telNo;
    }
}
