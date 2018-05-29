-- phpMyAdmin SQL Dump
-- version 4.7.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: May 29, 2018 at 09:29 AM
-- Server version: 10.1.24-MariaDB
-- PHP Version: 7.1.6

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `bits_crm`
--

-- --------------------------------------------------------

--
-- Table structure for table `client_store`
--

CREATE TABLE `client_store` (
  `CL_ID` int(11) NOT NULL,
  `CL_NAME` varchar(200) NOT NULL,
  `CL_OWNER` varchar(200) DEFAULT NULL,
  `CL_EMAIL` varchar(100) NOT NULL,
  `CL_PHONE` varchar(50) NOT NULL,
  `CL_ADDR` varchar(500) DEFAULT NULL,
  `CL_CITY` varchar(100) NOT NULL,
  `CL_COUNTRY` varchar(100) NOT NULL,
  `CL_WEBSITE` varchar(200) DEFAULT NULL,
  `CL_JOINDATE` date DEFAULT NULL,
  `CL_NOTE` text,
  `CL_BCYCLE` date DEFAULT NULL,
  `CREATEDON` datetime DEFAULT NULL,
  `CREATEDBY` int(11) NOT NULL,
  `CL_TYPE` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `client_type`
--

CREATE TABLE `client_type` (
  `CT_CODE` int(11) NOT NULL,
  `CT_NAME` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `contact_store`
--

CREATE TABLE `contact_store` (
  `CS_ID` int(11) NOT NULL,
  `CS_FNAME` varchar(200) DEFAULT NULL,
  `CS_LNAME` varchar(200) DEFAULT NULL,
  `CS_DOB` datetime DEFAULT NULL,
  `CS_ADDR` text,
  `CS_CITY` varchar(200) DEFAULT NULL,
  `CS_COUNTRY` varchar(200) DEFAULT NULL,
  `CS_NOTE` text,
  `CREATEDON` datetime DEFAULT NULL,
  `CREATEDBY` int(11) NOT NULL,
  `FREZE` tinyint(1) DEFAULT NULL,
  `CL_ID` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `domain_list`
--

CREATE TABLE `domain_list` (
  `DCODE` int(11) NOT NULL,
  `DNAME` varchar(200) NOT NULL,
  `DWB` int(11) NOT NULL COMMENT 'Whitelist/Blacklist'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `email_general`
--

CREATE TABLE `email_general` (
  `EMNO` int(11) NOT NULL,
  `MSGNO` int(11) NOT NULL,
  `SBJCT` varchar(10000) DEFAULT NULL,
  `TOADD` varchar(1000) DEFAULT NULL,
  `FRADD` varchar(1000) DEFAULT NULL,
  `CCADD` varchar(1000) DEFAULT NULL,
  `BCCADD` varchar(1000) DEFAULT NULL,
  `EBODY` text,
  `ATTCH` varchar(10000) DEFAULT NULL,
  `TSTMP` datetime DEFAULT NULL,
  `FREZE` tinyint(1) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `email_list`
--

CREATE TABLE `email_list` (
  `EM_ID` int(11) NOT NULL,
  `EM_NAME` varchar(500) NOT NULL,
  `CL_ID` int(11) DEFAULT NULL,
  `CS_ID` int(11) DEFAULT NULL,
  `UCODE` int(11) DEFAULT NULL,
  `LS_ID` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `email_relation`
--

CREATE TABLE `email_relation` (
  `EMNO` int(11) NOT NULL COMMENT 'Ticket Number',
  `EM_ID` int(11) NOT NULL,
  `EMTYPE` int(11) NOT NULL,
  `CL_ID` int(11) NOT NULL,
  `UCODE` int(11) NOT NULL,
  `CS_ID` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `email_sent`
--

CREATE TABLE `email_sent` (
  `EMNO` int(11) NOT NULL,
  `SBJCT` varchar(10000) DEFAULT NULL,
  `TOADD` varchar(1000) DEFAULT NULL,
  `FRADD` varchar(1000) DEFAULT NULL,
  `CCADD` varchar(1000) DEFAULT NULL,
  `BCCADD` varchar(1000) DEFAULT NULL,
  `EBODY` text,
  `ATTCH` varchar(10000) DEFAULT NULL,
  `TSTMP` datetime NOT NULL,
  `UCODE` int(11) NOT NULL,
  `FREZE` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `email_settings`
--

CREATE TABLE `email_settings` (
  `ECODE` int(11) NOT NULL,
  `HOST` varchar(500) NOT NULL,
  `EMAIL` varchar(500) NOT NULL,
  `PASS` varchar(500) NOT NULL,
  `FSPATH` varchar(2000) DEFAULT NULL,
  `AUTOCHK` tinyint(1) DEFAULT NULL,
  `DISCCHK` tinyint(1) DEFAULT NULL,
  `AUTOTXT` text,
  `DISCTXT` text,
  `SOLVTXT` text,
  `SOLVCHK` tinyint(1) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `email_store`
--

CREATE TABLE `email_store` (
  `EMNO` int(11) NOT NULL,
  `MSGNO` int(11) DEFAULT NULL,
  `SBJCT` varchar(10000) DEFAULT NULL,
  `TOADD` varchar(1000) NOT NULL,
  `FRADD` varchar(1000) NOT NULL,
  `EBODY` text,
  `ATTCH` varchar(10000) DEFAULT NULL,
  `CCADD` varchar(5000) DEFAULT NULL,
  `ESOLV` char(1) DEFAULT NULL,
  `SOLVBY` int(11) DEFAULT NULL,
  `LOCKD` int(11) DEFAULT NULL,
  `TSTMP` datetime NOT NULL,
  `SOLVTIME` datetime DEFAULT NULL,
  `MANUAL` tinyint(1) DEFAULT NULL,
  `FREZE` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `lead_store`
--

CREATE TABLE `lead_store` (
  `LS_ID` int(11) NOT NULL,
  `LS_FNAME` varchar(500) NOT NULL COMMENT 'First Name',
  `LS_LNAME` varchar(500) NOT NULL COMMENT 'Last Name',
  `LS_CNAME` varchar(500) NOT NULL COMMENT 'Company Name',
  `LS_TITLE` varchar(500) DEFAULT NULL,
  `LS_WEBSITE` varchar(500) DEFAULT NULL,
  `LS_CITY` varchar(500) NOT NULL,
  `LS_COUNTRY` varchar(500) NOT NULL,
  `LS_ADDR` text,
  `LS_NOTE` text,
  `CREATEDBY` int(11) NOT NULL,
  `CREATEDON` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `module_locking`
--

CREATE TABLE `module_locking` (
  `PM_ID` int(11) NOT NULL,
  `UCODE` int(11) NOT NULL,
  `LOCKEDTIME` datetime DEFAULT NULL,
  `UNLOCKEDTIME` datetime DEFAULT NULL,
  `DESCRIPTION` text,
  `PS_ID` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `phone_list`
--

CREATE TABLE `phone_list` (
  `PH_ID` int(11) NOT NULL,
  `PH_NUM` varchar(100) NOT NULL,
  `CL_ID` int(11) DEFAULT NULL,
  `UCODE` int(11) DEFAULT NULL,
  `CS_ID` int(11) DEFAULT NULL,
  `LS_ID` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `product_entry`
--

CREATE TABLE `product_entry` (
  `PE_ID` int(11) NOT NULL,
  `PS_ID` int(11) NOT NULL,
  `CREATEDBY` int(11) NOT NULL,
  `SUBJ` varchar(1000) NOT NULL,
  `BODY` text NOT NULL,
  `CREATEDON` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `product_module`
--

CREATE TABLE `product_module` (
  `PM_ID` int(11) NOT NULL,
  `PS_ID` int(11) NOT NULL,
  `PM_NAME` varchar(500) NOT NULL,
  `PM_DESC` text NOT NULL,
  `CREATEDBY` int(11) NOT NULL,
  `CREATEDON` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `product_store`
--

CREATE TABLE `product_store` (
  `PS_ID` int(11) NOT NULL,
  `PS_NAME` varchar(1000) NOT NULL,
  `PS_PRICE` int(11) NOT NULL,
  `PS_DESC` text NOT NULL,
  `PS_STATUS` int(11) NOT NULL,
  `PS_TYPE` int(11) NOT NULL,
  `PS_STARTED` datetime NOT NULL,
  `PS_PRIORITY` int(11) NOT NULL,
  `CREATEDON` datetime NOT NULL,
  `CREATEDBY` int(11) NOT NULL,
  `FREZE` tinyint(1) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `rights_chart`
--

CREATE TABLE `rights_chart` (
  `RCODE` int(11) NOT NULL,
  `UCODE` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `rights_list`
--

CREATE TABLE `rights_list` (
  `RCODE` int(11) NOT NULL,
  `RNAME` varchar(100) NOT NULL,
  `FREZE` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `rights_list`
--

INSERT INTO `rights_list` (`RCODE`, `RNAME`, `FREZE`) VALUES
(1, 'Email Viewer', 0),
(2, 'Clients', 0),
(3, 'Products', 0),
(4, 'General Settings', 0);

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `UCODE` int(11) NOT NULL,
  `FNAME` varchar(200) NOT NULL,
  `UNAME` varchar(300) NOT NULL,
  `Email` varchar(500) NOT NULL,
  `UPASS` varchar(500) NOT NULL,
  `NOTE` varchar(500) DEFAULT NULL,
  `URIGHT` varchar(100) NOT NULL,
  `FREZE` varchar(1) NOT NULL,
  `SOLV` int(11) DEFAULT NULL,
  `LOCKD` int(11) DEFAULT NULL,
  `ISLOG` tinyint(1) DEFAULT NULL,
  `ISEMAIL` char(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `client_store`
--
ALTER TABLE `client_store`
  ADD PRIMARY KEY (`CL_ID`),
  ADD UNIQUE KEY `CL_ID` (`CL_ID`);

--
-- Indexes for table `contact_store`
--
ALTER TABLE `contact_store`
  ADD PRIMARY KEY (`CS_ID`);

--
-- Indexes for table `domain_list`
--
ALTER TABLE `domain_list`
  ADD PRIMARY KEY (`DCODE`),
  ADD UNIQUE KEY `DNAME` (`DNAME`);

--
-- Indexes for table `email_list`
--
ALTER TABLE `email_list`
  ADD PRIMARY KEY (`EM_ID`),
  ADD UNIQUE KEY `EM_ID` (`EM_ID`),
  ADD UNIQUE KEY `EM_NAME` (`EM_NAME`,`CL_ID`,`UCODE`,`CS_ID`,`LS_ID`);

--
-- Indexes for table `email_relation`
--
ALTER TABLE `email_relation`
  ADD UNIQUE KEY `EMNO` (`EMNO`,`EM_ID`,`EMTYPE`,`CL_ID`,`UCODE`,`CS_ID`);

--
-- Indexes for table `email_store`
--
ALTER TABLE `email_store`
  ADD PRIMARY KEY (`EMNO`);

--
-- Indexes for table `lead_store`
--
ALTER TABLE `lead_store`
  ADD PRIMARY KEY (`LS_ID`);

--
-- Indexes for table `phone_list`
--
ALTER TABLE `phone_list`
  ADD PRIMARY KEY (`PH_ID`),
  ADD UNIQUE KEY `PH_ID` (`PH_ID`),
  ADD UNIQUE KEY `PH_NUM` (`PH_NUM`,`CL_ID`,`UCODE`,`CS_ID`);

--
-- Indexes for table `product_module`
--
ALTER TABLE `product_module`
  ADD UNIQUE KEY `PS_ID` (`PS_ID`,`PM_NAME`);

--
-- Indexes for table `product_store`
--
ALTER TABLE `product_store`
  ADD PRIMARY KEY (`PS_ID`);

--
-- Indexes for table `rights_list`
--
ALTER TABLE `rights_list`
  ADD PRIMARY KEY (`RCODE`),
  ADD UNIQUE KEY `RCODE` (`RCODE`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`UCODE`),
  ADD UNIQUE KEY `UNAME` (`UNAME`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
