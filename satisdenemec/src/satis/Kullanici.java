package satis;

import javax.swing.*;
import satis.Kullanici; // Kullanici sınıfını doğru paketten import edin   eklenmezse hata veriyor sonradan bak

public class Kullanici {
    private int kullanici_id;
    private String kullanici_adi;
    private String sifre;
    private String telefon_numara;
    private String profilfoto;

    public Kullanici(int kullanici_id, String kullanici_adi, String sifre, String telefon_numara, String profilfoto) {
        this.kullanici_id = kullanici_id;
        this.kullanici_adi = kullanici_adi;
        this.sifre = sifre;
        this.telefon_numara = telefon_numara;
        this.profilfoto = profilfoto;
    }

    public int getKullanici_id() {
        return kullanici_id;
    }

    public void setKullanici_id(int kullanici_id) {
        this.kullanici_id = kullanici_id;
    }

    public String getKullanici_adi() {
        return kullanici_adi;
    }

    public void setKullanici_adi(String kullanici_adi) {
        this.kullanici_adi = kullanici_adi;
    }

    public String getSifre() {
        return sifre;
    }

    public void setSifre(String sifre) {
        if(sifre == "" || sifre.isEmpty() || sifre.length()<4){
            JOptionPane.showMessageDialog(null,"Hatalı şifre denemesi");
        }else{
            this.sifre = sifre.trim();
        }
    }

    public String getTelefon_numara() {
        return telefon_numara;
    }

    public void setTelefon_numara(String telefon_numara) {
        this.telefon_numara = telefon_numara;
    }

    public String getProfilfoto() {
        return profilfoto;
    }

    public void setProfilfoto(String profilfoto) {
        this.profilfoto = profilfoto;
    }
}
