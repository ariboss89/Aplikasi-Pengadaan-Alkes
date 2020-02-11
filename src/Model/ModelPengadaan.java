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
public class ModelPengadaan {
    private String IdDetail;
    private int JumlahStok;
    private int TotalStok;
    private String Nama;

    public String getNama() {
        return Nama;
    }

    public void setNama(String Nama) {
        this.Nama = Nama;
    }

    public int getTotalStok() {
        return TotalStok;
    }

    public void setTotalStok(int TotalStok) {
        this.TotalStok = TotalStok;
    }
    
    public int getJumlahStok() {
        return JumlahStok;
    }

    public void setJumlahStok(int JumlahStok) {
        this.JumlahStok = JumlahStok;
    }
    
    public String getIdDetail() {
        return IdDetail;
    }

    public void setIdDetail(String IdDetail) {
        this.IdDetail = IdDetail;
    }
    
    
    
}
