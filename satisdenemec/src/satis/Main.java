package satis;

import java.sql.Connection;

public class Main {
    public static Connection conn = DBbaglanti.connect_to_db("DBSatis","postgres","freddy");

    public static void main(String[] args) {

        CustomLoginUI.start();
        //GirisArayuz x = new GirisArayuz();

    }

}
