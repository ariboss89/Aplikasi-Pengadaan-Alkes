/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Model;

/**
 *
 * @author User
 */
public class ModelLogin {
    private static String username;
    private static String kategori;

    public static String getUsername() {
        return username;
    }

    public static void setUsername(String username) {
        ModelLogin.username = username;
    }

    public static String getKategori() {
        return kategori;
    }

    public static void setKategori(String kategori) {
        ModelLogin.kategori = kategori;
    }
    
}
