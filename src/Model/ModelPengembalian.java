/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Model;

import java.util.Date;

/**
 *
 * @author User
 */
public class ModelPengembalian {
    private String date;
    private String idDetail;
    private int harga;
    private int total;
    private int bayar;
    private int kembali;
    private Double sisaStok;
    private Double stokNow;
    private Double sisaBeli;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getIdDetail() {
        return idDetail;
    }

    public void setIdDetail(String idDetail) {
        this.idDetail = idDetail;
    }

    public int getHarga() {
        return harga;
    }

    public void setHarga(int harga) {
        this.harga = harga;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public Double getSisaStok() {
        return sisaStok;
    }

    public void setSisaStok(Double sisaStok) {
        this.sisaStok = sisaStok;
    }

    public Double getStokNow() {
        return stokNow;
    }

    public void setStokNow(Double stokNow) {
        this.stokNow = stokNow;
    }

    public Double getSisaBeli() {
        return sisaBeli;
    }

    public void setSisaBeli(Double sisaBeli) {
        this.sisaBeli = sisaBeli;
    }

    public int getBayar() {
        return bayar;
    }

    public void setBayar(int bayar) {
        this.bayar = bayar;
    }

    public int getKembali() {
        return kembali;
    }

    public void setKembali(int kembali) {
        this.kembali = kembali;
    }

    
}
