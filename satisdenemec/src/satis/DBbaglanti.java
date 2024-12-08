package satis;

import javax.swing.*;
import java.sql.*;

public class DBbaglanti {


    public static Kullanici kullanici; // giriş yapan kullanıcının tüm bilgilerini bu değişkende tutucaz.

    // Veri tabanı bağlantısı sağlama
    public static Connection connect_to_db(String dbname, String user, String pass){
        Connection conn=null;
        try{
            Class.forName("org.postgresql.Driver");
            conn= DriverManager.getConnection("jdbc:postgresql://localhost:5432/"+dbname,user,pass);
            if(conn!=null){
                System.out.println("Veritabanı bağlantısı başarılı.");
            }
            else{
                System.out.println("Veritabanı bağlantısı başarısız.");
            }

        }catch (Exception e){
            System.out.println("hata!!\n"+e);
        }
        return conn;
    }


    // Kullanıcı giriş kontrolü
    public static void girisKontrol(Connection conn, String table_name,String ad,String sifre){

        Statement statement;
        ResultSet rs=null;
        try {
            String query=String.format("select * from %s where kullanici_adi= '%s' and sifre= '%s'",table_name,ad,sifre);
            statement=conn.createStatement();
            rs=statement.executeQuery(query);
            while (rs.next()){

                CustomLoginUI.girisBasariliMi = true;
                //kullanıcının profil fotosu bilgisini al
                String profilfoto = rs.getString("profilfoto");
                int kullaniciid = Integer.valueOf(rs.getString("kullanici_id"));
                String kullanici_adi = rs.getString("kullanici_adi");
                String password = rs.getString("sifre");
                String telefon_numara = rs.getString("telefon_numara");
                kullanici = new Kullanici(kullaniciid,kullanici_adi,sifre,telefon_numara,profilfoto);
            }

        }catch (Exception e){
            System.out.println(e);
        }
    }

    // Kayıt ekleme
    public static void KullaniciKaydi(Connection conn,String kullaniciadi,String sifre,String telNo){
        Statement statement;
        try {
            String query = String.format("insert into tbl_kullanici(kullanici_adi,sifre,telefon_numara) values ('%s','%s','%s')",kullaniciadi,sifre,telNo);
            statement = conn.createStatement();
            statement.executeUpdate(query);
            JOptionPane.showMessageDialog(JOptionPane.getRootFrame(),"Kayıt Oluşturuldu.");
        } catch (Exception e) {
            e.getStackTrace();
        }
    }

    public static void setProfilfoto(Connection conn,String path){
        Statement statement;
        try {
            String query = String.format("update tbl_kullanici set profilfoto= '%s' where kullanici_id= '%s' ",path,kullanici.getKullanici_id());
            statement = conn.createStatement();
            statement.executeUpdate(query);
            JOptionPane.showMessageDialog(JOptionPane.getRootFrame(),"Foto eklendi.");
        } catch (Exception e) {
            e.getStackTrace();
        }
    }


    public static void UrunKaldir(Connection conn, String Urunid){
        Statement statement;
        try{
            String query = String.format("Delete from tbl_urun where urun_id = '%s'",Integer.valueOf(Urunid));
            statement = conn.createStatement();
            statement.executeUpdate(query);
            JOptionPane.showMessageDialog(JOptionPane.getRootFrame(),"Urun kaldırıldı.");
        } catch (SQLException e) {
            e.getStackTrace();
        }
    }


    public static void ParolaDegis(Connection conn,String sifre)
    {
        Statement statement;
        try{
            String query = String.format("update tbl_kullanici set sifre='%s' where kullanici_id='%s'",sifre,DBbaglanti.kullanici.getKullanici_id());
            statement = conn.createStatement();
            statement.executeUpdate(query);
        }catch (Exception e){
            e.printStackTrace();
        }
    }



}
