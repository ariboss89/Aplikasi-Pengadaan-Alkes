/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Form;

import Koneksi.Database;
import Model.ModelPengeluaran;
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
public class ViewPengeluaran extends javax.swing.JFrame {

    private DefaultTableModel OrderTable, ItemTable;
    private String SQL;
    ModelPengeluaran mdl = new ModelPengeluaran();

    /**
     * Creates new form ViewPengadaan
     */
    public ViewPengeluaran() {
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
            java.sql.ResultSet res = stmt.executeQuery("select *from pelanggan");
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
        txtHargaBarang.setText("");
        txtStok.setText("0");
        txtJumlahBeli.setText("0");
        txtTotalBeli.setText("");
        txtSearchNamaBarang.setText("");
        txtCatatan.setText("");
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
        OrderTable.addColumn("Id Pengeluaran");
        OrderTable.addColumn("Nama Pemesan");
        OrderTable.addColumn("Jumlah");
        OrderTable.addColumn("Harga");
        OrderTable.addColumn("Total");

        jTable3.setModel(OrderTable);
        java.sql.Connection conn = new Database().connect();
        try {
            java.sql.Statement stmt = conn.createStatement();
            SQL = " select *from detailpengeluaran where idpengeluaran = '" + txtNoPengeluaran.getText() + "'";
            java.sql.ResultSet res = stmt.executeQuery(SQL);
            while (res.next()) {
                OrderTable.addRow(new Object[]{
                    res.getString("iddetail"),
                    res.getString("idpengeluaran"),
                    res.getString("nama"),
                    res.getString("jumlah"),
                    res.getString("harga"),
                    res.getString("total")
                });
            }
        } catch (Exception e) {
        }
    }

    private void searchItem() {

        ItemTable = new DefaultTableModel();
        ItemTable.addColumn("Nama");
        ItemTable.addColumn("Jumlah");
        ItemTable.addColumn("Harga");

        jTable2.setModel(ItemTable);
        java.sql.Connection conn = new Database().connect();
        try {
            java.sql.Statement stmt = conn.createStatement();
            SQL = " select *from barang where nama like '%" + txtSearchNamaBarang.getText() + "%' and jumlah != 0";
            java.sql.ResultSet res = stmt.executeQuery(SQL);
            while (res.next()) {
                ItemTable.addRow(new Object[]{
                    res.getString("nama"),
                    res.getString("jumlah"),
                    res.getString("hargajual")
                });
            }
        } catch (Exception e) {
        }
    }

    public void IdDetail() {
        java.sql.Connection conn = new Database().connect();
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("select *from detailpengeluaran ORDER BY iddetail DESC");
            if (rs.first() == false) {
                mdl.setIdDetail("DTO-0001");
            } else {
                rs.first();
                System.out.println("COT: " + rs.getString("iddetail").substring(4, 8));
                int nokirim = Integer.valueOf(rs.getString("iddetail").substring(4, 8)) + 1;
                System.out.println(nokirim);
                if (nokirim < 10) {
                    mdl.setIdDetail("DTO-000" + nokirim);
                } else if (nokirim >= 10 && nokirim < 100) {
                    mdl.setIdDetail("DTO-00" + nokirim);
                } else if (nokirim >= 100 && nokirim < 1000) {
                    mdl.setIdDetail("DTO-0" + nokirim);
                } else if (nokirim >= 1000 && nokirim < 9999) {
                    mdl.setIdDetail("DTO-" + nokirim);
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
            ResultSet rs = st.executeQuery("select *from trpengeluaran ORDER BY idpengeluaran DESC");
            if (rs.first() == false) {
                txtNoPengeluaran.setText("TRC-0001");
            } else {
                rs.first();
                System.out.println("COT: " + rs.getString("idpengeluaran").substring(7, 8));
                int nokirim = Integer.valueOf(rs.getString("idpengeluaran").substring(7, 8)) + 1;
                System.out.println(nokirim);
                if (nokirim < 10) {
                    txtNoPengeluaran.setText("OUT-000" + nokirim);
                } else if (nokirim >= 10 && nokirim < 100) {
                    txtNoPengeluaran.setText("OUT-00" + nokirim);
                } else if (nokirim >= 100 && nokirim < 1000) {
                    txtNoPengeluaran.setText("OUT-0" + nokirim);
                } else if (nokirim >= 1000 && nokirim < 9999) {
                    txtNoPengeluaran.setText("OUT-" + nokirim);
                }
            }
            rs.close();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Data tidak ditemukan");
        }
    }

    private void HitungStok() {
        double jumlah, stok, sisa;
        jumlah = Double.parseDouble(txtJumlahBeli.getText());
        stok = Double.parseDouble(txtStok.getText());
        sisa = stok - jumlah;
        mdl.setSisaStok(sisa);
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

    private void SumJumlah() {
        java.sql.Connection conn = new Database().connect();
        try {
            java.sql.Statement stmt = conn.createStatement();
            java.sql.ResultSet res = stmt.executeQuery("select sum(jumlah) as jumlahitem from detailpengeluaran where idpengeluaran = '" + txtNoPengeluaran.getText() + "'");
            if (res.next()) {
                txtJumlahItem.setText(res.getString("jumlahitem"));
            }
        } catch (SQLException ex) {
        }
    }

    private void SumTotal() {
        java.sql.Connection conn = new Database().connect();
        try {
            java.sql.Statement stmt = conn.createStatement();
            java.sql.ResultSet res = stmt.executeQuery("select sum(total) as total from detailpengeluaran where idpengeluaran = '" + txtNoPengeluaran.getText() + "'");
            if (res.next()) {
                txtSubTotal.setText(res.getString("total"));
            }
        } catch (SQLException ex) {
        }
    }

    private void SumKembalian() {
        int total = Integer.parseInt(txtSubTotal.getText());
        int bayar = Integer.parseInt(txtBayar.getText());
        int kembalian = bayar - total;
        txtKembalian.setText(String.valueOf(kembalian));
    }

    private void cetak() {
        //ShowAlamat();
        java.sql.Connection conn = new Koneksi.Database().connect();

        try {
            HashMap parameter = new HashMap();
            File file = new File("src/Report/StrukPengeluaran.jasper");
            java.sql.Date tanggal = java.sql.Date.valueOf(txtTanggal.getText());
            String nota = txtNoPengeluaran.getText();
            String pelanggan = cbNamaPemesan.getSelectedItem().toString();
            int total1 = Integer.parseInt(txtSubTotal.getText());
            int bayar1 = Integer.parseInt(txtBayar.getText());
            int kembalian = Integer.parseInt(txtKembalian.getText());
            int jumlah = Integer.parseInt(txtJumlahItem.getText());
            int kontak = Integer.parseInt(txtKontak.getText().trim());
            String alamat = txtAlamat.getText().trim();
            
            String catatan = txtCatatan.getText();
            parameter.put("notapengeluaran", nota);
            parameter.put("tanggal", tanggal);
            parameter.put("namapemesan", pelanggan);
            parameter.put("total", total1);
            parameter.put("bayar", bayar1);
            parameter.put("kembalian", kembalian);
            parameter.put("jumlahitem", jumlah);
            parameter.put("kontak", kontak);
            parameter.put("alamat", alamat);
            parameter.put("catatan", catatan);
            JasperReport jp = (JasperReport) JRLoader.loadObject(file);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jp, parameter, conn);
            JasperViewer.viewReport(jasperPrint, false);
            JasperViewer.setDefaultLookAndFeelDecorated(true);
            Refresh();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
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
        txtNoPengeluaran = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        txtJumlahItem = new javax.swing.JTextField();
        txtSubTotal = new javax.swing.JTextField();
        txtBayar = new javax.swing.JTextField();
        txtKembalian = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        txtSearchNamaBarang = new javax.swing.JTextField();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jLabel14 = new javax.swing.JLabel();
        txtNamaBarang = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        txtHargaBarang = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        txtJumlahBeli = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        txtTotalBeli = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTable3 = new javax.swing.JTable();
        jLabel18 = new javax.swing.JLabel();
        txtStok = new javax.swing.JTextField();
        cbNamaPemesan = new javax.swing.JComboBox();
        jPanel7 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtCatatan = new javax.swing.JTextArea();
        jButton3 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(0, 153, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Pengeluaran", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12))); // NOI18N

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

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel8.setText("No Pengeluaran");

        txtNoPengeluaran.setEditable(false);

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel9.setText("Jumlah Item");

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel10.setText("Sub Total");

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel11.setText("Bayar");

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel12.setText("Kembalian");

        txtJumlahItem.setEditable(false);

        txtSubTotal.setEditable(false);

        txtBayar.setText("0");
        txtBayar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBayarKeyReleased(evt);
            }
        });

        txtKembalian.setEditable(false);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtNoPengeluaran, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel12)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtKembalian, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtJumlahItem, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtSubTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel11)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtBayar, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(txtNoPengeluaran, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(txtJumlahItem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(txtSubTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(txtBayar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(txtKembalian, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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

        jLabel15.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel15.setText("Harga");

        txtHargaBarang.setEditable(false);
        txtHargaBarang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtHargaBarangActionPerformed(evt);
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

        jLabel17.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel17.setText("Total");

        txtTotalBeli.setEditable(false);
        txtTotalBeli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTotalBeliActionPerformed(evt);
            }
        });

        jButton1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButton1.setText("SIMPAN");
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

        jLabel18.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel18.setText("Stok");

        txtStok.setEditable(false);
        txtStok.setText("0");
        txtStok.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtStokActionPerformed(evt);
            }
        });

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
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 598, Short.MAX_VALUE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                    .addComponent(jLabel14)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED))
                                .addGroup(jPanel3Layout.createSequentialGroup()
                                    .addComponent(jLabel16)
                                    .addGap(44, 44, 44)))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel18)
                                .addGap(59, 59, 59)))
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtStok)
                            .addComponent(txtJumlahBeli, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 202, Short.MAX_VALUE)
                            .addComponent(txtNamaBarang, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel15)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtHargaBarang, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel17)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jButton2))
                                    .addComponent(txtTotalBeli, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.Alignment.TRAILING))
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
                    .addComponent(jLabel15)
                    .addComponent(txtHargaBarang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNamaBarang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtStok, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel18))
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel17)
                        .addComponent(txtTotalBeli, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2)
                    .addComponent(txtJumlahBeli, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel16))
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

        jPanel7.setBackground(new java.awt.Color(0, 153, 255));
        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Catatan", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12))); // NOI18N

        txtCatatan.setColumns(20);
        txtCatatan.setLineWrap(true);
        txtCatatan.setRows(5);
        jScrollPane2.setViewportView(txtCatatan);

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 206, Short.MAX_VALUE)
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2)
        );

        jButton3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButton3.setText("SELESAI");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
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
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addGap(103, 103, 103))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtKontak)
                            .addComponent(jScrollPane1)
                            .addComponent(cbNamaPemesan, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtTanggal, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
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
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton3)))
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
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(gambar1Layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
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
            .addGroup(layout.createSequentialGroup()
                .addComponent(gambar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
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
        txtStok.setText(ItemTable.getValueAt(baris, 1).toString());
        txtHargaBarang.setText(ItemTable.getValueAt(baris, 2).toString());
        txtJumlahBeli.requestFocus();
        txtJumlahBeli.setEnabled(true);
    }//GEN-LAST:event_jTable2MouseClicked

    private void txtNamaBarangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNamaBarangActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNamaBarangActionPerformed

    private void txtHargaBarangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtHargaBarangActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtHargaBarangActionPerformed

    private void txtJumlahBeliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtJumlahBeliActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtJumlahBeliActionPerformed

    private void txtTotalBeliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTotalBeliActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTotalBeliActionPerformed

    private void jTable3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable3MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jTable3MouseClicked

    private void cbNamaPemesanItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbNamaPemesanItemStateChanged
        // TODO add your handling code here:
        java.sql.Connection conn = new Koneksi.Database().connect();
        try {
            java.sql.Statement stmt = conn.createStatement();
            java.sql.ResultSet res = stmt.executeQuery("select *from pelanggan where nama = '" + cbNamaPemesan.getSelectedItem() + "'");
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

    private void txtStokActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtStokActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtStokActionPerformed

    private void txtJumlahBeliKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtJumlahBeliKeyReleased
        // TODO add your handling code here:
        int harga = Integer.parseInt(txtHargaBarang.getText());
        int jumlahbeli = Integer.parseInt(txtJumlahBeli.getText());
        int total = jumlahbeli * harga;
        txtTotalBeli.setText(String.valueOf(total));
    }//GEN-LAST:event_txtJumlahBeliKeyReleased

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        Double stok;
        stok = Double.valueOf(txtStok.getText());
        Double jml = Double.parseDouble(txtJumlahBeli.getText());
        
         if (stok < jml) {
            JOptionPane.showMessageDialog(null, "Stok Tidak Cukup");
            txtJumlahBeli.setText("0");
            txtJumlahBeli.requestFocus();
            txtTotalBeli.setText("");
        } else if (txtNamaBarang.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Silahkan Pilih Barang");
            txtSearchNamaBarang.requestFocus();
        }
        
        else if (cbNamaPemesan.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(null, "Silahkan Pilih Nama Pemesan");
            cbNamaPemesan.requestFocus();
        } else {
            java.sql.Connection conn = new Database().connect();
            try {
                java.sql.PreparedStatement stmt = conn.prepareStatement("insert into detailpengeluaran (iddetail, idpengeluaran, nama,jumlah, harga, total) values (?,?,?,?,?,?)");
                try {
                    stmt.setString(1, mdl.getIdDetail());
                    stmt.setString(2, txtNoPengeluaran.getText().trim());
                    stmt.setString(3, txtNamaBarang.getText().trim());
                    stmt.setString(4, txtJumlahBeli.getText().trim());
                    stmt.setString(5, txtHargaBarang.getText().trim());
                    stmt.setString(6, txtTotalBeli.getText().trim());

                    stmt.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Data Added", "Pesan", JOptionPane.INFORMATION_MESSAGE);
                    HitungStok();
                    UpdateStok();
                    Refresh();
                    SumJumlah();
                    SumTotal();
                    txtBayar.requestFocus();

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
        int Kembalian = Integer.parseInt(txtKembalian.getText().trim());

        if (Kembalian < 0) {
            JOptionPane.showMessageDialog(null, "Silahkan Isi Field Bayar dengan Format Yang Tepat");
            txtBayar.setText("0");
            txtBayar.requestFocus();
        } else {
            java.sql.Connection conn = new Database().connect();
            try {
                java.sql.PreparedStatement stmt = conn.prepareStatement("insert into trpengeluaran (idpengeluaran, namapemesan, tanggal, jumlah, total, bayar, kembalian) values (?, ?, ?, ?, ?, ?, ?)");
                try {
                    stmt.setString(1, txtNoPengeluaran.getText().trim());
                    stmt.setString(2, cbNamaPemesan.getSelectedItem().toString());
                    stmt.setDate(3, tanggal);
                    stmt.setString(4, txtJumlahItem.getText());
                    stmt.setString(5, txtSubTotal.getText());
                    stmt.setString(6, txtBayar.getText());
                    stmt.setString(7, txtKembalian.getText());

                    stmt.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Transaksi di Proses", "Pesan", JOptionPane.INFORMATION_MESSAGE);
                    cetak();
                    Refresh();
                    cbNamaPemesan.setSelectedIndex(0);
                    txtAlamat.setText("");
                    txtKontak.setText("");
                    txtJumlahItem.setText("");
                    txtSubTotal.setText("");
                    txtBayar.setText("0");
                    txtKembalian.setText("");
                    Id();
                    ViewData();

                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Failed, Try Again", "Pesan", JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (SQLException e) {

            }
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void txtBayarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBayarKeyReleased
        // TODO add your handling code here:
        SumKembalian();
    }//GEN-LAST:event_txtBayarKeyReleased

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
            java.util.logging.Logger.getLogger(ViewPengeluaran.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ViewPengeluaran.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ViewPengeluaran.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ViewPengeluaran.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ViewPengeluaran().setVisible(true);
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
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
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
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTable jTable2;
    private javax.swing.JTable jTable3;
    private javax.swing.JTextArea txtAlamat;
    private javax.swing.JTextField txtBayar;
    private javax.swing.JTextArea txtCatatan;
    private javax.swing.JTextField txtHargaBarang;
    private javax.swing.JTextField txtJumlahBeli;
    private javax.swing.JTextField txtJumlahItem;
    private javax.swing.JTextField txtKembalian;
    private javax.swing.JTextField txtKontak;
    private javax.swing.JTextField txtNamaBarang;
    private javax.swing.JTextField txtNoPengeluaran;
    private javax.swing.JTextField txtSearchNamaBarang;
    private javax.swing.JTextField txtStok;
    private javax.swing.JTextField txtSubTotal;
    private javax.swing.JTextField txtTanggal;
    private javax.swing.JTextField txtTotalBeli;
    // End of variables declaration//GEN-END:variables
}
