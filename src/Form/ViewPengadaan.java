/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Form;

import Koneksi.Database;
import Model.ModelPengadaan;
import Model.ModelPengeluaran;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
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
public class ViewPengadaan extends javax.swing.JFrame {

    private DefaultTableModel OrderTable, ItemTable;
    private String SQL;
    ModelPengadaan mdl = new ModelPengadaan();

    /**
     * Creates new form ViewPengadaan
     */
    public ViewPengadaan() {
        initComponents();
        Refresh();
        ShowKontak();
        Id();
        ViewData();
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (dim.width - getWidth()) / 2;
        int y = (dim.height - getHeight()) / 2;
        setLocation(x, y);
    }

    private void ShowKontak() {
        java.sql.Connection conn = new Koneksi.Database().connect();
        try {
            java.sql.Statement stmt = conn.createStatement();
            java.sql.ResultSet res = stmt.executeQuery("select *from supplier");
            while (res.next()) {
                cbNamaPemesan.addItem(res.getString("nama"));
            }
        } catch (SQLException ex) {

        }
    }

    private void Refresh() {
        IdDetail();
        tanggal();
        ViewData();
        searchItem();
        txtNamaBarang.setText("");
        txtJumlahBeli.setText("0");
        txtSearchNamaBarang.setText("");
        jButton1.setEnabled(true);
        txtNamaBarang.setEditable(true);
        txtJumlahBeli.setEditable(true);
        ViewData();
    }

    private void tanggal() {
        Date tgl = new Date();
        SimpleDateFormat tglFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat blnFormat = new SimpleDateFormat("MMMM");
        SimpleDateFormat thnFormat = new SimpleDateFormat("yyyy");
        txtTanggal.setText(tglFormat.format(tgl));
    }

    public void ViewData() {

        OrderTable = new DefaultTableModel();
        OrderTable.addColumn("Id Detail");
        OrderTable.addColumn("Id Pengadaan");
        OrderTable.addColumn("Nama Barang");
        OrderTable.addColumn("Jumlah");

        jTable3.setModel(OrderTable);
        java.sql.Connection conn = new Database().connect();
        try {
            java.sql.Statement stmt = conn.createStatement();
            SQL = " select *from detailpengadaan where idpengadaan = '" + txtNoPengadaan.getText() + "'";
            java.sql.ResultSet res = stmt.executeQuery(SQL);
            while (res.next()) {
                OrderTable.addRow(new Object[]{
                    res.getString("iddetail"),
                    res.getString("idpengadaan"),
                    res.getString("nama"),
                    res.getString("jumlah")
                });
            }
        } catch (Exception e) {
        }
    }

    private void searchItem() {

        ItemTable = new DefaultTableModel();
        ItemTable.addColumn("Nama");
        ItemTable.addColumn("Jumlah");

        jTable2.setModel(ItemTable);
        java.sql.Connection conn = new Database().connect();
        try {
            java.sql.Statement stmt = conn.createStatement();
            SQL = " select *from barang where nama like '%" + txtSearchNamaBarang.getText() + "%'";
            java.sql.ResultSet res = stmt.executeQuery(SQL);
            while (res.next()) {
                ItemTable.addRow(new Object[]{
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
            ResultSet rs = st.executeQuery("select *from detailpengadaan ORDER BY iddetail DESC");
            if (rs.first() == false) {
                mdl.setIdDetail("DTL-0001");
            } else {
                rs.first();
                System.out.println("COT: " + rs.getString("iddetail").substring(4, 8));
                int nokirim = Integer.valueOf(rs.getString("iddetail").substring(4, 8)) + 1;
                System.out.println(nokirim);
                if (nokirim < 10) {
                    mdl.setIdDetail("DTL-000" + nokirim);
                } else if (nokirim >= 10 && nokirim < 100) {
                    mdl.setIdDetail("DTL-00" + nokirim);
                } else if (nokirim >= 100 && nokirim < 1000) {
                    mdl.setIdDetail("DTL-0" + nokirim);
                } else if (nokirim >= 1000 && nokirim < 9999) {
                    mdl.setIdDetail("DTL-" + nokirim);
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
            ResultSet rs = st.executeQuery("select *from trpengadaan ORDER BY idpengadaan DESC");
            if (rs.first() == false) {
                txtNoPengadaan.setText("TRC-0001");
            } else {
                rs.first();
                System.out.println("COT: " + rs.getString("idpengadaan").substring(7, 8));
                int nokirim = Integer.valueOf(rs.getString("idpengadaan").substring(7, 8)) + 1;
                System.out.println(nokirim);
                if (nokirim < 10) {
                    txtNoPengadaan.setText("TRC-000" + nokirim);
                } else if (nokirim >= 10 && nokirim < 100) {
                    txtNoPengadaan.setText("TRC-00" + nokirim);
                } else if (nokirim >= 100 && nokirim < 1000) {
                    txtNoPengadaan.setText("TRC-0" + nokirim);
                } else if (nokirim >= 1000 && nokirim < 9999) {
                    txtNoPengadaan.setText("TRC-" + nokirim);
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
            java.sql.ResultSet res = stmt.executeQuery("select sum(jumlah) as jumlahitem from detailpengadaan where idpengadaan = '" + txtNoPengadaan.getText() + "'");
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
            File file = new File("src/Report/StrukPengadaan.jasper");
            java.sql.Date tanggal = java.sql.Date.valueOf(txtTanggal.getText());
            String nota = txtNoPengadaan.getText();
            String pelanggan = cbNamaPemesan.getSelectedItem().toString();
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
    
    private void HitungStok(){
        int currenStok = mdl.getJumlahStok();
        int tambah = Integer.parseInt(txtJumlahBeli.getText());
        int total = currenStok+tambah;
        mdl.setTotalStok(total);
    }
    
    private void KurangStok(){
        int currenStok = mdl.getJumlahStok();
        int kurang = Integer.parseInt(txtJumlahBeli.getText());
        int total = currenStok-kurang;
        mdl.setTotalStok(total);
    }

    private void UpdateStok() {
        java.sql.Connection conn = new Database().connect();
        try {

            java.sql.PreparedStatement stmt = conn.prepareStatement("update barang set jumlah=? where nama =? ");

            try {
                stmt.setDouble(1, mdl.getTotalStok());
                stmt.setString(2, txtNamaBarang.getText());
                stmt.executeUpdate();
            } catch (SQLException ex) {
            }
        } catch (Exception e) {
        }
    }
    
    private void GetJumlahStok() {
        java.sql.Connection conn = new Koneksi.Database().connect();
        try {
            java.sql.Statement stmt = conn.createStatement();
            java.sql.ResultSet res = stmt.executeQuery("select *from barang where nama = '"+ txtNamaBarang.getText().trim()+"'");
            while (res.next()) {
                mdl.setJumlahStok(res.getInt("jumlah"));
            }
        } catch (SQLException ex) {

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
        jLabel8 = new javax.swing.JLabel();
        txtNoPengadaan = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        txtJumlahItem = new javax.swing.JTextField();
        jButton3 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        txtSearchNamaBarang = new javax.swing.JTextField();
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
        cbNamaPemesan = new javax.swing.JComboBox();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(0, 153, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Pengadaan", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12))); // NOI18N

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel4.setText("Tanggal");

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel5.setText("Nama Penyedia");

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

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel8.setText("No Pengadaan");

        txtNoPengadaan.setEditable(false);

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel9.setText("Jumlah Item");

        txtJumlahItem.setEditable(false);

        jButton3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButton3.setText("SELESAI");
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
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9)
                    .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 161, Short.MAX_VALUE)
                    .addComponent(txtNoPengadaan)
                    .addComponent(txtJumlahItem)))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(txtNoPengadaan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(txtJumlahItem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton3)
                .addContainerGap(42, Short.MAX_VALUE))
        );

        jPanel3.setBackground(new java.awt.Color(0, 153, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12))); // NOI18N

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel13.setText("Search Nama Barang");

        txtSearchNamaBarang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSearchNamaBarangActionPerformed(evt);
            }
        });
        txtSearchNamaBarang.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtSearchNamaBarangKeyReleased(evt);
            }
        });

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
        jTable3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTable3KeyPressed(evt);
            }
        });
        jScrollPane4.setViewportView(jTable3);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel13)
                        .addGap(18, 18, 18)
                        .addComponent(txtSearchNamaBarang))
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 607, Short.MAX_VALUE)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                        .addComponent(jLabel14)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED))
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addComponent(jLabel16)
                                        .addGap(44, 44, 44)))
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtJumlahBeli, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(txtNamaBarang, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jButton2)))))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(txtSearchNamaBarang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(txtNamaBarang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtJumlahBeli, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel16))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );

        cbNamaPemesan.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        cbNamaPemesan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Pilih" }));
        cbNamaPemesan.setToolTipText("");
        cbNamaPemesan.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbNamaPemesanItemStateChanged(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addComponent(jLabel5)
                                    .addGap(103, 103, 103))
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addGap(145, 145, 145)))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtTanggal)
                            .addComponent(cbNamaPemesan, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtKontak, javax.swing.GroupLayout.Alignment.TRAILING))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(txtTanggal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(cbNamaPemesan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtKontak, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                    .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 1017, Short.MAX_VALUE))
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
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(gambar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(gambar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtTanggalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTanggalActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTanggalActionPerformed

    private void txtSearchNamaBarangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSearchNamaBarangActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSearchNamaBarangActionPerformed

    private void jTable2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable2MouseClicked
        // TODO add your handling code here:
        int baris = jTable2.getSelectedRow();
        txtNamaBarang.setText(ItemTable.getValueAt(baris, 0).toString());
        mdl.setJumlahStok(Integer.parseInt(ItemTable.getValueAt(baris, 1).toString()));
        txtJumlahBeli.requestFocus();
        txtJumlahBeli.setEnabled(true);
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
        txtNamaBarang.setText(OrderTable.getValueAt(baris, 2).toString());
        txtJumlahBeli.setText(OrderTable.getValueAt(baris, 3).toString());
        jButton1.setEnabled(false);
        txtNamaBarang.setEditable(false);
        txtJumlahBeli.setEditable(false);
        mdl.setIdDetail(OrderTable.getValueAt(baris, 0).toString());
        GetJumlahStok();
    }//GEN-LAST:event_jTable3MouseClicked

    private void cbNamaPemesanItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbNamaPemesanItemStateChanged
        // TODO add your handling code here:
        java.sql.Connection conn = new Koneksi.Database().connect();
        try {
            java.sql.Statement stmt = conn.createStatement();
            java.sql.ResultSet res = stmt.executeQuery("select *from supplier where nama = '" + cbNamaPemesan.getSelectedItem() + "'");
            while (res.next()) {
                txtAlamat.setText(res.getString("alamat"));
                txtKontak.setText(res.getString("kontak"));
            }
        } catch (SQLException ex) {

        }
    }//GEN-LAST:event_cbNamaPemesanItemStateChanged

    private void txtSearchNamaBarangKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchNamaBarangKeyReleased
        // TODO add your handling code here:
        if (txtSearchNamaBarang.getText().equals("")) {
            Refresh();
            searchItem();
        } else {
            searchItem();
        }
    }//GEN-LAST:event_txtSearchNamaBarangKeyReleased

    private void txtJumlahBeliKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtJumlahBeliKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtJumlahBeliKeyReleased

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        Double jml = Double.parseDouble(txtJumlahBeli.getText());
        
        if (txtNamaBarang.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Silahkan Pilih Barang");
            txtSearchNamaBarang.requestFocus();
        } else if (cbNamaPemesan.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(null, "Silahkan Pilih Nama Pemesan");
            cbNamaPemesan.requestFocus();
        } else {
            java.sql.Connection conn = new Database().connect();
            try {
                java.sql.PreparedStatement stmt = conn.prepareStatement("insert into detailpengadaan (iddetail, idpengadaan, nama,jumlah) values (?,?,?,?)");
                try {
                    stmt.setString(1, mdl.getIdDetail());
                    stmt.setString(2, txtNoPengadaan.getText().trim());
                    stmt.setString(3, txtNamaBarang.getText().trim());
                    stmt.setString(4, txtJumlahBeli.getText().trim());

                    stmt.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Data Added", "Pesan", JOptionPane.INFORMATION_MESSAGE);
                    HitungStok();
                    UpdateStok();
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
            java.sql.PreparedStatement stmt = conn.prepareStatement("insert into trpengadaan (idpengadaan, namapemesan, tanggal, jumlah) values ( ?, ?, ?, ?)");
            try {
                stmt.setString(1, txtNoPengadaan.getText().trim());
                stmt.setString(2, cbNamaPemesan.getSelectedItem().toString().trim());
                stmt.setDate(3, tanggal);
                stmt.setString(4, txtJumlahItem.getText());

                stmt.executeUpdate();
                JOptionPane.showMessageDialog(null, "Transaksi di Proses", "Pesan", JOptionPane.INFORMATION_MESSAGE);
                cetak();
                Refresh();
                cbNamaPemesan.setSelectedIndex(0);
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

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        dispose();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jTable3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTable3KeyPressed
        // TODO add your handling code here:
        int current = mdl.getJumlahStok();
        int total = mdl.getTotalStok();
        
        if (evt.getKeyCode() == KeyEvent.VK_DELETE) {
            java.sql.Connection conn = new Database().connect();
            int ok = JOptionPane.showConfirmDialog(null, "Are You Sure Want to Remove This Data?", "Confirmation", JOptionPane.YES_NO_OPTION);
            if (ok == 0) {
                try {
                    SQL = "delete from detailpengadaan where iddetail ='" + mdl.getIdDetail() + "'";
                    java.sql.PreparedStatement stmt = conn.prepareStatement(SQL);
                    stmt.executeUpdate();
                    KurangStok();
                    UpdateStok();
                    JOptionPane.showMessageDialog(null, "Data Removed");
                    Refresh();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Data Failed to Removed ");
                }
            } else {
                Refresh();
            }
        }
    }//GEN-LAST:event_jTable3KeyPressed

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
            java.util.logging.Logger.getLogger(ViewPengadaan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ViewPengadaan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ViewPengadaan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ViewPengadaan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ViewPengadaan().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox cbNamaPemesan;
    private Gambar.Gambar gambar1;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel16;
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
    private javax.swing.JTextField txtJumlahBeli;
    private javax.swing.JTextField txtJumlahItem;
    private javax.swing.JTextField txtKontak;
    private javax.swing.JTextField txtNamaBarang;
    private javax.swing.JTextField txtNoPengadaan;
    private javax.swing.JTextField txtSearchNamaBarang;
    private javax.swing.JTextField txtTanggal;
    // End of variables declaration//GEN-END:variables
}
