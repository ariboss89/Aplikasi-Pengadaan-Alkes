/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Koneksi;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author User
 */
public class Database {
    
    public static Object getConnection() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    public Connection koneksi;
    public Connection connect(){
    //koneksi ke driver mysql
    try{
       Class.forName("com.mysql.jdbc.Driver");
       System.out.println("Berhasil koneksi ke JDBC Driver MySQL");
    }catch(ClassNotFoundException ex){
        System.out.println("Tidak Berhasil Koneksi ke JDBC Driver MySQL");
    }
    //koneksi ke data base
    try{
        String url="jdbc:mysql://localhost:3306/alkes";
        koneksi=DriverManager.getConnection(url,"root","");
        System.out.println("Berhasil koneksi ke Database");
    }catch(SQLException e){
        System.out.println("Tidak Berhasil Koneksi ke Database");
    }
    return koneksi;
    
}
    public static void main(String[]args){
    
    }
}
    
