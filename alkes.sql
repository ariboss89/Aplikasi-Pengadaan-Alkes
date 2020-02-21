# Host: localhost  (Version: 5.5.8)
# Date: 2020-02-12 23:06:42
# Generator: MySQL-Front 5.3  (Build 4.81)

/*!40101 SET NAMES utf8 */;

#
# Structure for table "barang"
#

DROP TABLE IF EXISTS `barang`;
CREATE TABLE `barang` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `idbarang` varchar(255) DEFAULT NULL,
  `nama` varchar(255) DEFAULT NULL,
  `satuan` varchar(255) DEFAULT NULL,
  `jumlah` varchar(255) DEFAULT NULL,
  `harga` varchar(255) DEFAULT NULL,
  `hargajual` varchar(255) DEFAULT NULL,
  `tggl` varchar(255) DEFAULT NULL,
  `tglexpired` date DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=MyISAM AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;

#
# Data for table "barang"
#

/*!40000 ALTER TABLE `barang` DISABLE KEYS */;
INSERT INTO `barang` VALUES (1,'STF-0001','Pispot','PCS','110.0','80000','90000','04 October 2019','2020-02-11'),(2,'STF-0002','Stetoskop','SET','1.0','200000','210000','04 October 2019','2020-02-11'),(3,'STF-0003','Pil','PCS','1892.0','1500','2000','04 October 2019','2020-02-13'),(4,'STF-0004','Kucing Goreng','PCS','10','12000','20000','11 February 2020','2020-02-11');
/*!40000 ALTER TABLE `barang` ENABLE KEYS */;

#
# Structure for table "detailpengadaan"
#

DROP TABLE IF EXISTS `detailpengadaan`;
CREATE TABLE `detailpengadaan` (
  `iddetail` varchar(50) NOT NULL DEFAULT '',
  `idpengadaan` varchar(50) DEFAULT NULL,
  `nama` varchar(55) DEFAULT NULL,
  `jumlah` int(11) DEFAULT NULL,
  PRIMARY KEY (`iddetail`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 ROW_FORMAT=COMPACT;

#
# Data for table "detailpengadaan"
#

INSERT INTO `detailpengadaan` VALUES ('DTL-0001','TRC-0001','CV. Manise',10),('DTL-0002','TRC-0002','CV. Manise',30),('DTL-0003','TRC-0003','CV. Manise',3),('DTL-0004','TRC-0004','Pil',7),('DTL-0005','TRC-0005','Pispot',100),('DTL-0006','TRC-0005','Stetoskop',100),('DTL-0007','TRC-0006','Pispot',30);

#
# Structure for table "detailpengeluaran"
#

DROP TABLE IF EXISTS `detailpengeluaran`;
CREATE TABLE `detailpengeluaran` (
  `iddetail` varchar(50) NOT NULL DEFAULT '',
  `idpengeluaran` varchar(50) DEFAULT NULL,
  `nama` varchar(55) DEFAULT NULL,
  `jumlah` int(11) DEFAULT NULL,
  `harga` int(11) DEFAULT NULL,
  `total` int(11) DEFAULT NULL,
  PRIMARY KEY (`iddetail`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

#
# Data for table "detailpengeluaran"
#

INSERT INTO `detailpengeluaran` VALUES ('DTO-0001','OUT-0001','Pil',6,1500,13500),('DTO-0002','OUT-0002','Pil',8,1500,12000),('DTO-0003','OUT-0002','Pil',2,1500,3000),('DTO-0004','OUT-0003','Pil',7,1500,10500),('DTO-0005','OUT-0004','Pil',8,1500,12000),('DTO-0006','OUT-0005','Pil',5,1500,7500),('DTO-0007','OUT-0006','Pil',7,1500,10500),('DTO-0008','OUT-0007','Pil',4,1500,6000),('DTO-0009','OUT-0008','Pil',5,1500,7500),('DTO-0010','OUT-0009','Pispot',1,80000,80000);

#
# Structure for table "detailpengembalian"
#

DROP TABLE IF EXISTS `detailpengembalian`;
CREATE TABLE `detailpengembalian` (
  `iddetail` varchar(50) NOT NULL DEFAULT '',
  `idpengembalian` varchar(50) DEFAULT NULL,
  `nama` varchar(55) DEFAULT NULL,
  `jumlah` int(11) DEFAULT NULL,
  PRIMARY KEY (`iddetail`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 ROW_FORMAT=COMPACT;

#
# Data for table "detailpengembalian"
#

INSERT INTO `detailpengembalian` VALUES ('DTR-0001','RTR-0001','Pil',3);

#
# Structure for table "login"
#

DROP TABLE IF EXISTS `login`;
CREATE TABLE `login` (
  `username` varchar(50) NOT NULL DEFAULT '',
  `password` varchar(255) DEFAULT NULL,
  `confirm` varchar(255) DEFAULT NULL,
  `category` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`username`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

#
# Data for table "login"
#

/*!40000 ALTER TABLE `login` DISABLE KEYS */;
INSERT INTO `login` VALUES ('admin','123456','123456','admin'),('ariboss89','123456','123456','Super Admin');
/*!40000 ALTER TABLE `login` ENABLE KEYS */;

#
# Structure for table "pelanggan"
#

DROP TABLE IF EXISTS `pelanggan`;
CREATE TABLE `pelanggan` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `idpelanggan` varchar(255) DEFAULT NULL,
  `nama` varchar(255) DEFAULT NULL,
  `kontak` varchar(255) DEFAULT NULL,
  `alamat` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

#
# Data for table "pelanggan"
#

/*!40000 ALTER TABLE `pelanggan` DISABLE KEYS */;
INSERT INTO `pelanggan` VALUES (1,'CST-0001','CV. Manise','0771313256','Jl. D.I. Panjaitan Km 8');
/*!40000 ALTER TABLE `pelanggan` ENABLE KEYS */;

#
# Structure for table "supplier"
#

DROP TABLE IF EXISTS `supplier`;
CREATE TABLE `supplier` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `idsupplier` varchar(255) DEFAULT NULL,
  `nama` varchar(255) DEFAULT NULL,
  `kontak` varchar(255) DEFAULT NULL,
  `alamat` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=MyISAM AUTO_INCREMENT=3 DEFAULT CHARSET=latin1 ROW_FORMAT=DYNAMIC;

#
# Data for table "supplier"
#

/*!40000 ALTER TABLE `supplier` DISABLE KEYS */;
INSERT INTO `supplier` VALUES (1,'SPL-0001','CV. Manise','0771313256','Jl. D.I. Panjaitan Km 8'),(2,'SPL-0002','CV. BATU','081278654352','Jl. Manise');
/*!40000 ALTER TABLE `supplier` ENABLE KEYS */;

#
# Structure for table "trpengadaan"
#

DROP TABLE IF EXISTS `trpengadaan`;
CREATE TABLE `trpengadaan` (
  `idpengadaan` varchar(11) NOT NULL DEFAULT '',
  `namapemesan` varchar(50) DEFAULT NULL,
  `tanggal` date DEFAULT NULL,
  `jumlah` int(11) DEFAULT NULL,
  PRIMARY KEY (`idpengadaan`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 ROW_FORMAT=COMPACT;

#
# Data for table "trpengadaan"
#

INSERT INTO `trpengadaan` VALUES ('TRC-0001','CV. Manise','2020-01-14',10),('TRC-0002','CV. Manise','2020-01-14',30),('TRC-0003','CV. Manise','2020-01-14',3),('TRC-0004','CV. Manise','2020-01-14',1),('TRC-0005','CV. BATU','2020-02-04',200),('TRC-0006','CV. Manise','2020-02-12',30);

#
# Structure for table "trpengeluaran"
#

DROP TABLE IF EXISTS `trpengeluaran`;
CREATE TABLE `trpengeluaran` (
  `idpengeluaran` varchar(11) NOT NULL DEFAULT '',
  `namapemesan` varchar(50) DEFAULT NULL,
  `tanggal` date DEFAULT NULL,
  `jumlah` int(11) DEFAULT NULL,
  `total` int(11) DEFAULT NULL,
  `bayar` int(11) DEFAULT NULL,
  `kembalian` int(11) DEFAULT NULL,
  PRIMARY KEY (`idpengeluaran`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

#
# Data for table "trpengeluaran"
#

INSERT INTO `trpengeluaran` VALUES ('OUT-0001','CV. Manise','2020-01-13',9,13500,15000,1500),('OUT-0002','CV. Manise','2020-01-13',10,15000,15000,0),('OUT-0003','CV. Manise','2020-01-14',7,10500,11000,500),('OUT-0004','CV. Manise','2020-01-14',1,12000,12000,0),('OUT-0005','CV. Manise','2020-01-14',1,7500,7500,0),('OUT-0006','CV. Manise','2020-01-15',7,10500,10500,0),('OUT-0007','CV. Manise','2020-01-15',4,6000,6000,0),('OUT-0008','CV. Manise','2020-01-15',5,7500,7500,0);

#
# Structure for table "trpengembalian"
#

DROP TABLE IF EXISTS `trpengembalian`;
CREATE TABLE `trpengembalian` (
  `idpengembalian` varchar(11) NOT NULL DEFAULT '',
  `namapemesan` varchar(50) DEFAULT NULL,
  `tanggal` date DEFAULT NULL,
  `jumlah` int(11) DEFAULT NULL,
  PRIMARY KEY (`idpengembalian`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 ROW_FORMAT=COMPACT;

#
# Data for table "trpengembalian"
#

INSERT INTO `trpengembalian` VALUES ('RTR-0001','CV. Manise','2020-01-13',3);
