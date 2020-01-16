/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Form;

import Koneksi.Database;
import Model.ModelPengadaan;
import Model.ModelPengeluaran;
import Model.ModelPengembalian;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author User
 */
public class ViewPengembalian extends javax.swing.JFrame {

    private DefaultTableModel OrderTable, ItemTable;
    private String SQL;
    ModelPengembalian mdl = new ModelPengembalian();

    /**
     * Creates new form ViewPengadaan
     */
    public ViewPengembalian() {
        initComponents();
        Refresh();
        ShowIdPengeluaran();
        Id();
        ViewData();
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (dim.width - getWidth()) / 2;
        int y = (dim.height - getHeight()) / 2;
        setLocation(x, y);
    }

    private void ShowIdPengeluaran() {
        java.sql.Connection conn = new Koneksi.Database().connect();
        try {
            java.sql.Statement stmt = conn.createStatement();
            java.sql.ResultSet res = stmt.executeQuery("select *from trpengeluaran");
            while (res.next()) {
                cbNoPengeluaran.addItem(res.getString("idpengeluaran"));
            }
        } catch (SQLException ex) {

        }
    }
    
    private void Refresh() {
        IdDetail();
        tanggal();
        ViewData();
        ViewDetail();
        txtNamaBarang.setText("");
        txtJumlahBeli.setText("0");
        txtJumlahRetur.setText("0");
    }

    private void tanggal() {
        Date tgl = new Date();
        SimpleDateFormat tglFormat = new SimpleDateFormat("yyyy-MM-DD");
        SimpleDateFormat blnFormat = new SimpleDateFormat("MMMM");
        SimpleDateFormat thnFormat = new SimpleDateFormat("yyyy");
        mdl.setDate(tglFormat.format(tgl));
    }

    public void ViewData() {

        OrderTable = new DefaultTableModel();
        OrderTable.addColumn("Id Detail Barang");
        OrderTable.addColumn("Nama Barang");
        OrderTable.addColumn("Jumlah");

        jTable3.setModel(OrderTable);
        java.sql.Connection conn = new Database().connect();
        try {
            java.sql.Statement stmt = conn.createStatement();
            SQL = " select *from detailpengeluaran where idpengeluaran = '" + cbNoPengeluaran.getSelectedItem() + "'";
            java.sql.ResultSet res = stmt.executeQuery(SQL);
            while (res.next()) {
                OrderTable.addRow(new Object[]{
                    res.getString("iddetail"),
                    res.getString("nama"),
                    res.getString("jumlah"),
                });
                
                mdl.setHarga(res.getInt("harga"));
                mdl.setTotal(res.getInt("total"));
            }
        } catch (Exception e) {
        }
    }

    private void ViewDetail() {

        ItemTable = new DefaultTableModel();
        ItemTable.addColumn("Id Detail");
        ItemTable.addColumn("Id Pengembalian");
        ItemTable.addColumn("Nama Item");
        ItemTable.addColumn("Jumlah");

        jTable2.setModel(ItemTable);
        java.sql.Connection conn = new Database().connect();
        try {
            java.sql.Statement stmt = conn.createStatement();
            SQL = " select *from detailpengembalian where idpengembalian = '" + txtNoPengembalian.getText() + "'";
            java.sql.ResultSet res = stmt.executeQuery(SQL);
            while (res.next()) {
                ItemTable.addRow(new Object[]{
                    res.getString("iddetail"),
                    res.getString("idpengembalian"),
                    res.getString("nama"),
                    res.getString("jumlah")
                });
            }
        } catch (Exception e) {
        }
    }

    public void IdDetail() {
        java.sql.Connection conn = new Database().connect();
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("select *from detailpengembalian ORDER BY iddetail DESC");
            if (rs.first() == false) {
                mdl.setIdDetail("DTR-0001");
            } else {
                rs.first();
                System.out.println("COT: " + rs.getString("iddetail").substring(7, 8));
                int nokirim = Integer.valueOf(rs.getString("iddetail").substring(7, 8)) + 1;
                System.out.println(nokirim);
                if (nokirim < 10) {
                    mdl.setIdDetail("DTR-000" + nokirim);
                } else if (nokirim >= 10 && nokirim < 100) {
                    mdl.setIdDetail("DTR-00" + nokirim);
                } else if (nokirim >= 100 && nokirim < 1000) {
                    mdl.setIdDetail("DTR-0" + nokirim);
                } else if (nokirim >= 1000 && nokirim < 9999) {
                    mdl.setIdDetail("DTR-" + nokirim);
                }
            }
            rs.close();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Data tidak ditemukan");
        }
    }

    public void Id() {
        java.sql.Connection conn = new Database().connect();
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("select *from trpengembalian ORDER BY idpengembalian DESC");
            if (rs.first() == false) {
                txtNoPengembalian.setText("RTR-0001");
            } else {
                rs.first();
                System.out.println("COT: " + rs.getString("idpengembalian").substring(7, 8));
                int nokirim = Integer.valueOf(rs.getString("idpengembalian").substring(7, 8)) + 1;
                System.out.println(nokirim);
                if (nokirim < 10) {
                    txtNoPengembalian.setText("RTR-000" + nokirim);
                } else if (nokirim >= 10 && nokirim < 100) {
                    txtNoPengembalian.setText("RTR-00" + nokirim);
                } else if (nokirim >= 100 && nokirim < 1000) {
                    txtNoPengembalian.setText("RTR-0" + nokirim);
                } else if (nokirim >= 1000 && nokirim < 9999) {
                    txtNoPengembalian.setText("RTR-" + nokirim);
                }
            }
            rs.close();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Data tidak ditemukan");
        }
    }

    private void SumJumlah() {
        java.sql.Connection conn = new Database().connect();
        try {
            java.sql.Statement stmt = conn.createStatement();
            java.sql.ResultSet res = stmt.executeQuery("select sum(jumlah) as jumlahitem from detailpengembalian where idpengembalian = '" + txtNoPengembalian.getText() + "'");
            if (res.next()) {
                txtJumlahItem.setText(res.getString("jumlahitem"));
            }
        } catch (SQLException ex) {
        }
    }

    private void cetak() {
        //ShowAlamat();
        java.sql.Connection conn = new Koneksi.Database().connect();

        try {
            HashMap parameter = new HashMap();
            File file = new File("src/Report/StrukPengembalian.jasper");
            java.sql.Date tanggal = java.sql.Date.valueOf(txtTanggal.getText());
            String nota = txtNoPengembalian.getText();
            String pelanggan = txtNamaPemesan.getText();
            int jumlah = Integer.parseInt(txtJumlahItem.getText());
            int kontak = Integer.parseInt(txtKontak.getText().trim());
            String alamat = txtAlamat.getText().trim();
            parameter.put("notapengadaan", nota);
            parameter.put("tanggal", tanggal);
            parameter.put("namapemesan", pelanggan);
            parameter.put("jumlahitem", jumlah);
            parameter.put("kontak", kontak);
            parameter.put("alamat", alamat);
            JasperReport jp = (JasperReport) JRLoader.loadObject(file);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jp, parameter, conn);
            JasperViewer.viewReport(jasperPrint, false);
            JasperViewer.setDefaultLookAndFeelDecorated(true);
            Refresh();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    private void ShowPengeluaran(){
        java.sql.Connection conn = new Koneksi.Database().connect();
        try {
            java.sql.Statement stmt = conn.createStatement();
            java.sql.ResultSet res = stmt.executeQuery("select *from trpengeluaran where idpengeluaran = '"+cbNoPengeluaran.getSelectedItem()+"'");
            while (res.next()) {
                txtTanggal.setText(res.getString("tanggal"));
                txtNamaPemesan.setText(res.getString("namapemesan"));
                //txtJumlahItem.setText(res.getString("jumlah"));
                //txtTotal.setText(res.getString("total"));
            }
        } catch (SQLException ex) {

        }
    }
    
    private void ShowDetailPemesan(){
        java.sql.Connection conn = new Koneksi.Database().connect();
        try {
            java.sql.Statement stmt = conn.createStatement();
            java.sql.ResultSet res = stmt.executeQuery("select *from supplier where nama = '"+txtNamaPemesan.getText()+"'");
            while (res.next()) {
                txtAlamat.setText(res.getString("alamat"));
                txtKontak.setText(res.getString("kontak"));
            }
        } catch (SQLException ex) {

        }
    }
    
    private void GetStokBarang(){
        java.sql.Connection conn = new Koneksi.Database().connect();
        try {
            java.sql.Statement stmt = conn.createStatement();
            java.sql.ResultSet res = stmt.executeQuery("select *from barang where nama = '"+txtNamaBarang.getText()+"'");
            while (res.next()) {
                mdl.setStokNow(res.getDouble("jumlah"));
            }
        } catch (SQLException ex) {

        }
    }
    
    private void HitungStok() {
        Double jumlah, stok, sisa;
        jumlah = Double.parseDouble(txtJumlahRetur.getText());
        stok = mdl.getStokNow();
        sisa = stok + jumlah;
        mdl.setSisaStok(sisa);
        Double a = mdl.getSisaStok();
    }

    private void UpdateStok() {
        java.sql.Connection conn = new Database().connect();
        try {

            java.sql.PreparedStatement stmt = conn.prepareStatement("update barang set jumlah=? where nama =? ");

            try {
                stmt.setDouble(1, mdl.getSisaStok());
                stmt.setString(2, txtNamaBarang.getText());
                stmt.executeUpdate();
            } catch (SQLException ex) {
            }
        } catch (Exception e) {
        }
    }
    
    private void HitungTotalDetail() {
        Double jumlahRetur, jumlah, sisa;
        jumlahRetur = Double.parseDouble(txtJumlahRetur.getText());
        jumlah = Double.parseDouble(txtJumlahBeli.getText());
        sisa = jumlah - jumlahRetur;
        mdl.setSisaBeli(sisa);
//        harga = mdl.getHarga();
//        subTotal = harga*jumlah; 
    }
    
    private void UpdateDetailBarang() {
        java.sql.Connection conn = new Database().connect();
        try {

            java.sql.PreparedStatement stmt = conn.prepareStatement("update detailpengeluaran set jumlah=? where iddetail =? ");

            try {
                stmt.setDouble(1, mdl.getSisaBeli());
                stmt.setString(2, txtIdDetail.getText());
                stmt.executeUpdate();
            } catch (SQLException ex) {
            }
        } catch (Exception e) {
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        gambar1 = new Gambar.Gambar();
        jPanel1 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtAlamat = new javax.swing.JTextArea();
        jLabel7 = new javax.swing.JLabel();
        txtTanggal = new javax.swing.JTextField();
        txtKontak = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        txtJumlahItem = new javax.swing.JTextField();
        jButton3 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jLabel14 = new javax.swing.JLabel();
        txtNamaBarang = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        txtJumlahBeli = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTable3 = new javax.swing.JTable();
        jLabel15 = new javax.swing.JLabel();
        txtIdDetail = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        txtJumlahRetur = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        cbNoPengeluaran = new javax.swing.JComboBox();
        jLabel10 = new javax.swing.JLabel();
        txtNoPengembalian = new javax.swing.JTextField();
        txtNamaPemesan = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(0, 153, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Pengembalian", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12))); // NOI18N

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel4.setText("Tanggal");

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel5.setText("Nama Pemesan");

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel6.setText("Alamat");

        txtAlamat.setEditable(false);
        txtAlamat.setColumns(20);
        txtAlamat.setRows(5);
        jScrollPane1.setViewportView(txtAlamat);

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel7.setText("Kontak");

        txtTanggal.setEditable(false);
        txtTanggal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTanggalActionPerformed(evt);
            }
        });

        txtKontak.setEditable(false);

        jPanel2.setBackground(new java.awt.Color(0, 153, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Total", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12))); // NOI18N

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel9.setText("Jumlah Item");

        txtJumlahItem.setEditable(false);

        jButton3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButton3.setText("RETUR");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, 196, Short.MAX_VALUE)
                    .addComponent(txtJumlahItem))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(txtJumlahItem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(33, Short.MAX_VALUE))
        );

        jPanel3.setBackground(new java.awt.Color(0, 153, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12))); // NOI18N

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jTable2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable2MouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(jTable2);

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel14.setText("Nama Barang");

        txtNamaBarang.setEditable(false);
        txtNamaBarang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNamaBarangActionPerformed(evt);
            }
        });

        jLabel16.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel16.setText("Jumlah");

        txtJumlahBeli.setEditable(false);
        txtJumlahBeli.setText("0");
        txtJumlahBeli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtJumlahBeliActionPerformed(evt);
            }
        });
        txtJumlahBeli.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtJumlahBeliKeyReleased(evt);
            }
        });

        jButton1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButton1.setText("ADD");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButton2.setText("CANCEL");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jTable3.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jTable3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable3MouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(jTable3);

        jLabel15.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel15.setText("Id Detail");

        txtIdDetail.setEditable(false);
        txtIdDetail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtIdDetailActionPerformed(evt);
            }
        });

        jLabel17.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel17.setText("Jumlah Retur");

        txtJumlahRetur.setEditable(false);
        txtJumlahRetur.setText("0");
        txtJumlahRetur.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtJumlahReturActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel16)
                            .addComponent(jLabel15))
                        .addGap(37, 37, 37)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(txtIdDetail, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel14)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtNamaBarang, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(txtJumlahBeli, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel17)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtJumlahRetur, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton2)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(txtIdDetail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14)
                    .addComponent(txtNamaBarang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtJumlahBeli, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel16)
                    .addComponent(jLabel17)
                    .addComponent(txtJumlahRetur, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel8.setText("No Pengeluaran");

        cbNoPengeluaran.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        cbNoPengeluaran.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Pilih" }));
        cbNoPengeluaran.setToolTipText("");
        cbNoPengeluaran.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbNoPengeluaranItemStateChanged(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel10.setText("No Pengembalian");

        txtNoPengembalian.setEditable(false);

        txtNamaPemesan.setEditable(false);
        txtNamaPemesan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNamaPemesanActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel8)
                                    .addComponent(jLabel10))
                                .addGap(46, 46, 46)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(cbNoPengeluaran, javax.swing.GroupLayout.PREFERRED_SIZE, 213, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtNoPengembalian, javax.swing.GroupLayout.PREFERRED_SIZE, 213, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtTanggal, javax.swing.GroupLayout.PREFERRED_SIZE, 213, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtNamaPemesan, javax.swing.GroupLayout.PREFERRED_SIZE, 213, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(102, 102, 102)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtKontak)
                                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 214, Short.MAX_VALUE)))))
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel10)
                            .addComponent(txtNoPengembalian, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(13, 13, 13)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cbNoPengeluaran, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(txtTanggal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtNamaPemesan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 103, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtKontak, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("APLIKASI PENGADAAN ALAT KESEHATAN");

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("CV. MUARA PUTERA MANDIRI");

        javax.swing.GroupLayout gambar1Layout = new javax.swing.GroupLayout(gambar1);
        gambar1.setLayout(gambar1Layout);
        gambar1Layout.setHorizontalGroup(
            gambar1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(gambar1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(gambar1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 1017, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        gambar1Layout.setVerticalGroup(
            gambar1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, gambar1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addGap(18, 18, 18)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(gambar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(gambar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtTanggalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTanggalActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTanggalActionPerformed

    private void jTable2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable2MouseClicked
        // TODO add your handling code here:
        
    }//GEN-LAST:event_jTable2MouseClicked

    private void txtNamaBarangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNamaBarangActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNamaBarangActionPerformed

    private void txtJumlahBeliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtJumlahBeliActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtJumlahBeliActionPerformed

    private void jTable3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable3MouseClicked
        // TODO add your handling code here:
        int baris = jTable3.getSelectedRow();
        txtIdDetail.setText(OrderTable.getValueAt(baris, 0).toString());
        txtNamaBarang.setText(OrderTable.getValueAt(baris, 1).toString());
        txtJumlahBeli.setText(OrderTable.getValueAt(baris, 2).toString());
        
        txtJumlahRetur.setEditable(true);
        txtJumlahRetur.requestFocus();      
        GetStokBarang();
    }//GEN-LAST:event_jTable3MouseClicked

    private void txtJumlahBeliKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtJumlahBeliKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtJumlahBeliKeyReleased

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        Double jml = Double.parseDouble(txtJumlahBeli.getText());
        Double jmlRetur = Double.parseDouble(txtJumlahRetur.getText());
        
        if(jml<=jmlRetur){
            JOptionPane.showMessageDialog(null, "Silahkan Isi Jumlah Retur Dengan Benar");
            txtJumlahRetur.setEditable(true);
            txtJumlahRetur.requestFocus();
        }
        else if (txtNamaBarang.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Silahkan Pilih Barang");
            txtNamaBarang.requestFocus();
        } else if (cbNoPengeluaran.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(null, "Silahkan Pilih Nomor Pengeluaran");
            cbNoPengeluaran.requestFocus();
        } else {
            java.sql.Connection conn = new Database().connect();
            try {
                java.sql.PreparedStatement stmt = conn.prepareStatement("insert into detailpengembalian (iddetail, idpengembalian, nama,jumlah) values (?,?,?,?)");
                try {
                    stmt.setString(1, mdl.getIdDetail());
                    stmt.setString(2, txtNoPengembalian.getText().trim());
                    stmt.setString(3, txtNamaBarang.getText().trim());
                    stmt.setString(4, txtJumlahRetur.getText().trim());

                    stmt.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Data Added", "Pesan", JOptionPane.INFORMATION_MESSAGE);
                    HitungStok();
                    HitungTotalDetail();
                    UpdateStok();
                    
                    UpdateDetailBarang();
                    Refresh();
                    SumJumlah();

                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Failed, Try Again", "Pesan", JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (SQLException e) {

            }
        }
        
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        java.sql.Date tanggal = java.sql.Date.valueOf(txtTanggal.getText().trim());
        java.sql.Connection conn = new Database().connect();
        try {
            java.sql.PreparedStatement stmt = conn.prepareStatement("insert into trpengembalian (idpengembalian, namapemesan, tanggal, jumlah) values ( ?, ?, ?, ?)");
            try {
                stmt.setString(1, txtNoPengembalian.getText().trim());
                stmt.setString(2, txtNamaPemesan.getText().trim());
                stmt.setDate(3, tanggal);
                stmt.setString(4, txtJumlahItem.getText());

                stmt.executeUpdate();
                JOptionPane.showMessageDialog(null, " DI Proses", "Pesan", JOptionPane.INFORMATION_MESSAGE);
                cetak();
                Refresh();
                cbNoPengeluaran.setSelectedIndex(0);
                txtAlamat.setText("");
                txtKontak.setText("");
                txtJumlahItem.setText("");
                Id();

            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Failed, Try Again", "Pesan", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (SQLException e) {

        }

    }//GEN-LAST:event_jButton3ActionPerformed

    private void cbNoPengeluaranItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbNoPengeluaranItemStateChanged
        // TODO add your handling code here:
        ShowPengeluaran();
        ShowDetailPemesan();
        ViewData();
    }//GEN-LAST:event_cbNoPengeluaranItemStateChanged

    private void txtNamaPemesanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNamaPemesanActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNamaPemesanActionPerformed

    private void txtIdDetailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtIdDetailActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtIdDetailActionPerformed

    private void txtJumlahReturActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtJumlahReturActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtJumlahReturActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        dispose();
    }//GEN-LAST:event_jButton2ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ViewPengembalian.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ViewPengembalian.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ViewPengembalian.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ViewPengembalian.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ViewPengembalian().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox cbNoPengeluaran;
    private Gambar.Gambar gambar1;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTable jTable2;
    private javax.swing.JTable jTable3;
    private javax.swing.JTextArea txtAlamat;
    private javax.swing.JTextField txtIdDetail;
    private javax.swing.JTextField txtJumlahBeli;
    private javax.swing.JTextField txtJumlahItem;
    private javax.swing.JTextField txtJumlahRetur;
    private javax.swing.JTextField txtKontak;
    private javax.swing.JTextField txtNamaBarang;
    private javax.swing.JTextField txtNamaPemesan;
    private javax.swing.JTextField txtNoPengembalian;
    private javax.swing.JTextField txtTanggal;
    // End of variables declaration//GEN-END:variables
}
