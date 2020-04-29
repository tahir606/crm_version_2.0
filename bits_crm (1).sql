-- phpMyAdmin SQL Dump
-- version 4.7.4
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Apr 25, 2020 at 12:11 PM
-- Server version: 10.1.28-MariaDB
-- PHP Version: 7.1.11

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
  `CL_EMAIL` varchar(100) DEFAULT NULL,
  `CL_PHONE` varchar(50) DEFAULT NULL,
  `CL_ADDR` varchar(500) DEFAULT NULL,
  `CL_CITY` varchar(100) DEFAULT NULL,
  `CL_COUNTRY` varchar(100) NOT NULL,
  `CL_WEBSITE` varchar(200) DEFAULT NULL,
  `CL_JOINDATE` date DEFAULT NULL,
  `CL_BCYCLE` date DEFAULT NULL,
  `CL_TYPE` int(11) NOT NULL,
  `FROM_LEAD` int(11) NOT NULL DEFAULT '0',
  `CREATEDBY` int(11) NOT NULL,
  `CREATEDON` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `client_store`
--

INSERT INTO `client_store` (`CL_ID`, `CL_NAME`, `CL_OWNER`, `CL_EMAIL`, `CL_PHONE`, `CL_ADDR`, `CL_CITY`, `CL_COUNTRY`, `CL_WEBSITE`, `CL_JOINDATE`, `CL_BCYCLE`, `CL_TYPE`, `FROM_LEAD`, `CREATEDBY`, `CREATEDON`) VALUES
(0, 'No Client', NULL, '', '', NULL, '', '', NULL, NULL, NULL, 1, 0, 0, '0000-00-00 00:00:00'),
(1, 'Noor Distributor Karachi', 'Shehzad Noor', 'info@noor.com.pk', '34135811 - 34135814 Ext 105 (s.noor)', 'Plot# 29/3 block 3, road # 2, Bahaduryar jang Society, Bahadurabad', 'Karachi', 'Pakistan', 'www.noor.com.pk', NULL, NULL, 1, 0, 0, '0000-00-00 00:00:00'),
(2, 'Noor Distributor Hyd', 'Shezad Noor', 'Shehriyar.hyd@noor.com.pk', '3413312 - 3413318', '\"Plot # A/10-B1, Couft kaat, site area hyderabad', 'hyderabad', 'Pakistan', 'www.noorhyd.com.pk', NULL, NULL, 1, 0, 0, '0000-00-00 00:00:00'),
(3, 'Noor & Company  Karachi', 'Javeed Noor', 'waqar@noorandcompany.com', '34521416 - 17', '\"Banglow#59, Block B, S.M.C.H.S opposite Gulistan boys secondary School', 'Karachi', 'Pakistan', 'www.noorandcompany.com', NULL, NULL, 1, 0, 0, '0000-00-00 00:00:00'),
(4, 'A. G. Moosa & Co. Karachi', 'Haroon Moosa', 'haroon@agmoosa.com', '36346613 - 15', '\"D-35, block-6,near bana palace,main sharah E-Pakistan, FB-Area\"', 'Karachi', 'Pakistan', 'www.agmoosa.com.pk', NULL, NULL, 1, 0, 0, '0000-00-00 00:00:00'),
(5, 'Apex Distributor Karachi', 'Amir Madni', 'info@apex.com.pk', '34120807 - 09', 'Plot#D-5, Al-hilal society, main University Road.', 'Karachi', 'Pakistan', 'www.apexgroup.com.pk', NULL, NULL, 1, 0, 0, '0000-00-00 00:00:00'),
(6, 'Hamza Distributor Karachi', 'Irfan Norani', 'hamza-traders@live.com', '-', '\"Plot#D-5, Al-hilal society, main University Road.\"', 'Karachi', 'Pakistan', 'www.apexgroup.com.pk', NULL, NULL, 1, 0, 0, '0000-00-00 00:00:00'),
(7, 'Bright Pharma Karachi', 'Aslam bakrani', 'pharmakhi@brightgroup.com.pk', '34960408 , 34810185', 'c-99, block-6, gulshan e iqbal', 'Karachi', 'Pakistan', 'www.brightgroup.com.pk', NULL, NULL, 1, 0, 0, '0000-00-00 00:00:00'),
(8, 'Bright & Bright Health Care ', 'Rafiq Dhedhi', 'imrandhedhi@brightgroup.com.pk', '34960428 , 34832927 , 34821211', 'B-43, Block 4-A, Gulshan e Iqbal.', 'Karachi', 'Pakistan', 'www.brightgroup.com.pk', NULL, NULL, 1, 0, 0, '0000-00-00 00:00:00'),
(9, 'Abdullah Brothers Karachi', 'Farhan', 'm.abdullah@abdullahbrothers.com', '35121000', 'Plot#C-155-156, Sector 6f, Mehran Town, Industrial Area, Korangi.', 'Karachi', 'Pakistan', 'www.abdullahbrothers.com', NULL, NULL, 1, 0, 0, '0000-00-00 00:00:00'),
(10, 'F. Abdullah Distributor Karachi', 'Faizan Ladha', 'f.abdullah.enterprise@gmail.com', '34383000 - 01', 'Plot# 166, Block 7/8, Justice Inamullah road, Karachi.', 'Karachi', 'Pakistan', 'www.fabdullah.com', NULL, NULL, 1, 0, 0, '0000-00-00 00:00:00'),
(11, 'TNM Distributor Karachi', 'Muneer', 'tnmdistributors@outlook.com', '34981752, 34832247', '-', 'Karachi', 'Pakistan', 'www.tnm.com.pk', NULL, NULL, 1, 0, 0, '0000-00-00 00:00:00'),
(12, 'Polka Dots', 'M. Ashfaq', NULL, NULL, '', 'Karachi', 'Pakistan', '', NULL, NULL, 1, 0, 1, '2018-09-28 12:44:36'),
(13, 'Pak Channels', 'Maroof Hussain', NULL, NULL, NULL, 'Karachi', 'Pakistan', 'www.dummy.com', NULL, NULL, 0, 0, 1, '2018-09-28 18:07:36'),
(14, 'Noorani Corner', 'Jamshed Noorani', NULL, NULL, NULL, 'Karachi', 'Pakistan', 'www.nooraniCorner.com', NULL, NULL, 0, 0, 1, '2018-09-28 18:09:10'),
(15, 'The Mughal Empire', 'Akbar Jodha', NULL, NULL, NULL, 'Karachi', 'Pakistan', 'www.mughal.com', NULL, NULL, 0, 1, 6, '2018-10-03 17:31:35');

-- --------------------------------------------------------

--
-- Table structure for table `client_type`
--

CREATE TABLE `client_type` (
  `CT_CODE` int(11) NOT NULL,
  `CT_NAME` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `client_type`
--

INSERT INTO `client_type` (`CT_CODE`, `CT_NAME`) VALUES
(1, 'Active'),
(2, 'Inactive ');

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
  `FREZE` tinyint(1) DEFAULT NULL,
  `CL_ID` int(11) DEFAULT NULL,
  `CREATEDBY` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `contact_store`
--

INSERT INTO `contact_store` (`CS_ID`, `CS_FNAME`, `CS_LNAME`, `CS_DOB`, `CS_ADDR`, `CS_CITY`, `CS_COUNTRY`, `CS_NOTE`, `CREATEDON`, `FREZE`, `CL_ID`, `CREATEDBY`) VALUES
(1, 'Tahir', 'Shakir', '1998-08-12 00:00:00', 'Plot 80, Block 7/8', 'karachi', 'Pakistan', 'BRO', '2018-04-06 18:36:29', 0, 2, 1),
(2, 'Mufaddal', 'Shakir', '1995-12-12 00:00:00', '', 'Karachi', 'Pakistan', '', '2018-06-23 10:32:12', 0, 0, 1),
(3, 'Shakir', 'Hussain', '1968-01-03 00:00:00', '', 'Karachi', 'Pakistan', '', '2018-04-06 11:48:17', 0, 0, 1),
(4, 'Kashif', 'Dhedhi', '1950-12-05 00:00:00', '', 'Karachi', 'Pakistan', 'Kashif dhedhi rocks', '2018-04-09 15:09:26', 0, 0, 1),
(5, 'Shahzad', 'Noor', '1934-12-19 00:00:00', '', 'Karachi', 'Pakistan', '', '2018-04-14 16:03:49', 0, 1, 6),
(6, 'Shakir', 'Shaikh', NULL, '', 'Karachi', 'Pakistan', '', '2018-04-14 16:04:39', 0, 1, 6);

-- --------------------------------------------------------

--
-- Table structure for table `document_store`
--

CREATE TABLE `document_store` (
  `DCODE` int(11) NOT NULL,
  `DNAME` varchar(500) NOT NULL,
  `DFILE` mediumblob NOT NULL
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

--
-- Dumping data for table `domain_list`
--

INSERT INTO `domain_list` (`DCODE`, `DNAME`, `DWB`) VALUES
(1, 'noor.com.pk', 1),
(2, 'live.com', 1),
(3, 'noorandcompany.com', 1),
(4, 'agmoosa.com', 1),
(5, 'apex.com.pk', 1),
(6, 'brightgroup.com.pk', 1),
(7, 'abdullahbrothers.com', 1),
(8, 'gmail.com', 1),
(9, 'yahoo.com', 1),
(10, 'polkadots.com', 1),
(11, 'yahoo.co.uk', 1),
(12, 'highqpharma.com', 1),
(13, 'apexgroup.com.pk', 1),
(14, '@hotmail.com', 1),
(15, 'anovaenterprises.com', 1),
(16, 'outlook.com', 1),
(17, 'hotmail.com', 1);

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
  `UCODE` int(11) NOT NULL,
  `CS_ID` int(11) NOT NULL,
  `LS_ID` int(11) NOT NULL DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `email_list`
--

INSERT INTO `email_list` (`EM_ID`, `EM_NAME`, `CL_ID`, `UCODE`, `CS_ID`, `LS_ID`) VALUES
(302, '\"aamirmadni@gmail.com\" <aamirmadni@gmail.com>', 0, 0, 0, 0),
(326, '\"abdul.basit@anovaenterprises.com\" <abdul.basit@anovaenterprises.com>', 0, 0, 0, 0),
(315, '\"Abdullah Bro.\" <purchase@abdullahbrothers.com>', 0, 0, 0, 0),
(330, '\"asad.abbas@anovaenterprises.com\" <asad.abbas@anovaenterprises.com>', 0, 0, 0, 0),
(303, '\"atif@highq.pk\" <atif@highq.pk>', 0, 0, 0, 0),
(286, '\"cPanel on agmoosa.com.pk\" <cpanel@agmoosa.com.pk>', 0, 0, 0, 0),
(262, '\"umer.jawed\" <umer.jawed@abdullahbrothers.com>', 0, 0, 0, 0),
(227, '\'Amjad Noor\' <amjad@noor.com.pk>', 0, 0, 0, 0),
(337, '\'Asad Abbas\' <asad.abbas@anovaenterprises.com>', 0, 0, 0, 0),
(250, '\'Ashraf Khanzada\' <ashraf@noor.com.pk>', 0, 0, 0, 0),
(243, '\'Bright Pharma\' <pharmabright@yahoo.com>', 0, 0, 0, 0),
(342, '\'Bright Pharma-Karachi\' <pharmabright@yahoo.com>', 0, 0, 0, 0),
(285, '\'General Information\' <info@burhanisolutions.com.pk>', 0, 0, 0, 0),
(50, '\'Imran Dhedhi\' <imrandhedhi@brightgroup.com.pk>', NULL, 0, 0, 0),
(54, '\'Imran Dhedhi\' <imrandhedhi@brightgroup.com.pk>', NULL, 0, 0, 0),
(197, '\'Imran Dhedhi\' <imrandhedhi@brightgroup.com.pk>', 0, 0, 0, 0),
(260, '\'Ishaq Abdul Qadir\' <ishaq@noor.com.pk>', 0, 0, 0, 0),
(294, '\'Ishtiaq Ahmed\' <ishtiaq@amarantpharma.com>', 0, 0, 0, 0),
(238, '\'jawed\' <jawed@noorandcompany.com>', 0, 0, 0, 0),
(190, '\'Kashif Dhedhi\' <kashifdhedhi@burhanisolutions.com.pk>', 0, 0, 0, 0),
(239, '\'Mohsin Butt\' <mohsin@noor.com.pk>', 0, 0, 0, 0),
(233, '\'Mufaddal Shakir\' <mufaddalshakir@burhanisolutions.com.pk>', 0, 0, 0, 0),
(334, '\'Saad Ahmed\' <support@amarantpharma.com>', 0, 0, 0, 0),
(335, '\'Saad Khan\' <saad.khan@anovaenterprises.com>', 0, 0, 0, 0),
(241, '\'Sadiq Patel\' <safupk@hotmail.com>', 0, 0, 0, 0),
(35, '\'Shahzad Noor\' <shahzad@noor.com.pk>', NULL, 0, 0, 0),
(38, '\'Shahzad Noor\' <shahzad@noor.com.pk>', NULL, 0, 0, 0),
(42, '\'Shahzad Noor\' <shahzad@noor.com.pk>', NULL, 0, 0, 0),
(187, '\'Shahzad Noor\' <shahzad@noor.com.pk>', 0, 0, 0, 0),
(193, '\'Shahzad\' <shahzad@noor.com.pk>', 0, 0, 0, 0),
(189, '\'Shakir Hussain\' <shakirhussain@burhanisolutions.com.pk>', 0, 0, 0, 0),
(263, '\'Shakir Shaikh\' <shakir@noor.com.pk>', 0, 0, 0, 0),
(336, '\'Shamshad Ahmed\' <shamshad@amarantpharma.com>', 0, 0, 0, 0),
(274, '\'Sheheryar Zai\' <shehriyar.hyd@noor.com.pk>', 0, 0, 0, 0),
(277, '\'Syed Fayyaz Ahmed\' <fayyaz@noor.com.pk>', 0, 0, 0, 0),
(264, '\'Tahir Shakir\' <tahir60652@gmail.com>', 0, 0, 0, 0),
(305, '\'Usman Fayyaz\' <claims@abdullahbrothers.com>', 0, 0, 0, 0),
(237, '\'Wahid Hussain\' <noorhyd@noor.com.pk>', 0, 0, 0, 0),
(287, '18-11-19', 0, 0, 0, 0),
(293, 'ABDUL.BASIT@ANOVAENTERPRISES.COM', 0, 0, 0, 0),
(232, 'abdul.wahab@noor.com.pk', 0, 0, 0, 0),
(320, 'accounts 1 <accounts@abdullahbrothers.com>', 0, 0, 0, 0),
(213, 'adil@noor.com.pk', 0, 0, 0, 0),
(206, 'Ali Shah <ali.shah@apexgroup.com.pk>', 0, 0, 0, 0),
(300, 'ali.shah@apexgroup.com.pk', 0, 0, 0, 0),
(245, 'Amar Azad <amar.rabazad@gmail.com>', 0, 0, 0, 0),
(201, 'Amir Viyani <info@noorandcompany.com>', 0, 0, 0, 0),
(221, 'Amjad Noor <amjad@noor.com.pk>', 0, 0, 0, 0),
(255, 'amjad@noor.com.pk', 0, 0, 0, 0),
(327, 'Ammad Amarant Pharma <ammad@amarantpharma.com>', 0, 0, 0, 0),
(204, 'Apex Distributor <aamirmadni@gmail.com>', 0, 0, 0, 0),
(203, 'Apex Distributor <apexdistributor@yahoo.com>', 0, 0, 0, 0),
(278, 'Aqeel Ur Rehman PDL Branch Manager <bm.pharmadlab@gmail.com>', 0, 0, 0, 0),
(333, 'Asad Abbas <asad.abbas@anovaenterprises.com>', 0, 0, 0, 0),
(338, 'asad.abbas@anovaenterprises.com', 0, 0, 0, 0),
(235, 'ashraf@noor.com.pk', 0, 0, 0, 0),
(175, 'babur@gmail.com', 0, 0, 0, 0),
(251, 'BITS <mufaddalshakir@burhanisolutions.com.pk>', 0, 0, 0, 0),
(226, 'Bright Distributors <info@brightgroup.com.pk>', 0, 0, 0, 0),
(341, 'Bright Linkers IT <pharmakhi@brightgroup.com.pk>', 0, 0, 0, 0),
(219, 'Bright Pharma <pharmabright@yahoo.com>', 0, 0, 0, 0),
(242, 'Bright Pharma <pharmakhi@brightgroup.com.pk>', 0, 0, 0, 0),
(318, 'Burhanisolutions Com Info <info@burhanisolutions.com.pk>', 0, 0, 0, 0),
(319, 'Burhanisolutions Com Info <support@burhanisolutions.com.pk>', 0, 0, 0, 0),
(307, 'claims@abdullahbrothers.com', 0, 0, 0, 0),
(312, 'Danial Nini <danialdkpharma@gmail.com>', 0, 0, 0, 0),
(299, 'Danish Taheem <danish@apexgroup.com.pk>', 0, 0, 0, 0),
(304, 'DISTRIBUTION <distribution@abdullahbrothers.com.pk>', 0, 0, 0, 0),
(23, 'f.abdullah.enterprise@gmail.com', 10, 0, 0, 0),
(64, 'FABD <f.abdullah.enterprise@gmail.com>', NULL, 0, 0, 0),
(67, 'FABD <f.abdullah.enterprise@gmail.com>', NULL, 0, 0, 0),
(79, 'FABD <f.abdullah.enterprise@gmail.com>', NULL, 0, 0, 0),
(198, 'FABD <f.abdullah.enterprise@gmail.com>', 0, 0, 0, 0),
(244, 'fahad <fahad@brightgroup.com.pk>', 0, 0, 0, 0),
(234, 'Fahad Jangardh Rafiq Jangardh <fahad.jangardh@gmail.com>', 0, 0, 0, 0),
(282, 'Faizan Rafiq Ladha <faizanladha@gmail.com>', 0, 0, 0, 0),
(228, 'faizan.siddiqui@burhanisolutions.com.pk', 0, 0, 0, 0),
(223, 'fajawed <fajawed@yahoo.com>', 0, 0, 0, 0),
(222, 'Farhan Haider <farhan.haider@abdullahbrothers.com>', 0, 0, 0, 0),
(259, 'fayyaz@noor.com.pk', 0, 0, 0, 0),
(306, 'FAZAL TRADING CORPORATION <ftc_qta@yahoo.co.uk>', 0, 0, 0, 0),
(236, 'Furqan Noor <furqan@noor.com.pk>', 0, 0, 0, 0),
(309, 'furqan@noor.com.pk', 0, 0, 0, 0),
(19, 'hamza-traders@live.com', 6, 0, 0, 0),
(17, 'haroon@agmoosa.com', 4, 0, 0, 0),
(58, 'haroon@agmoosa.com.pk', NULL, 0, 0, 0),
(339, 'hussain ali <hussainali6687@outlook.com>', 0, 0, 0, 0),
(266, 'Imran Baig <imran@noor.com.pk>', 0, 0, 0, 0),
(48, 'Imran Dhedhi <imrandhedhi@brightgroup.com.pk>', NULL, 0, 0, 0),
(52, 'Imran Dhedhi <imrandhedhi@brightgroup.com.pk>', NULL, 0, 0, 0),
(196, 'Imran Dhedhi <imrandhedhi@brightgroup.com.pk>', 0, 0, 0, 0),
(273, 'imrandhedhi@brightgroup.com.pk', 0, 0, 0, 0),
(21, 'imrandhedhi@brightgroup.com.pk', 8, 0, 0, 0),
(288, 'Info Abdullah Brothers <info@abdullahbrothers.com>', 0, 0, 0, 0),
(18, 'info@apex.com.pk', 5, 0, 0, 0),
(252, 'info@burhanisolutions.com.pk', 0, 0, 0, 0),
(132, 'info@noor.com.pk', 1, 0, 0, 0),
(202, 'info@noorandcompany.com', 0, 0, 0, 0),
(217, 'irfan chottani <hamza-traders@live.com>', 0, 0, 0, 0),
(192, 'Ishaq Abdul Qadir <ishaq@noor.com.pk>', 0, 0, 0, 0),
(313, 'Javed DK Pharma <javedDkpharma@gmail.com>', 0, 0, 0, 0),
(261, 'jawed@noorandcompany.com', 0, 0, 0, 0),
(229, 'Kashif Dhedhi <kashifdhedhi@burhanisolutions.com.pk>', 0, 0, 0, 0),
(247, 'kashif.idrees@abbott.com', 0, 0, 0, 0),
(179, 'kashif@burhanisloutions.com.pk', 0, 0, 0, 0),
(186, 'kashifdhedhi@burhanisolutions.com.pk', 0, 0, 0, 0),
(27, 'm.abdullah@abdullahbrothers.com', 9, 0, 0, 0),
(331, 'maroof mubashir <maroof_mubashir@hotmail.com>', 0, 0, 0, 0),
(289, 'maroof_mubashir@hotmail.com', 0, 0, 0, 0),
(225, 'memon.hyd@noor.com.pk', 0, 0, 0, 0),
(29, 'Mohsin Butt <mohsin@noor.com.pk>', NULL, 0, 0, 0),
(37, 'Mohsin Butt <mohsin@noor.com.pk>', NULL, 0, 0, 0),
(40, 'Mohsin Butt <mohsin@noor.com.pk>', NULL, 0, 0, 0),
(44, 'Mohsin Butt <mohsin@noor.com.pk>', NULL, 0, 0, 0),
(74, 'Mohsin Butt <mohsin@noor.com.pk>', NULL, 0, 0, 0),
(78, 'Mohsin Butt <mohsin@noor.com.pk>', NULL, 0, 0, 0),
(195, 'Mohsin Butt <mohsin@noor.com.pk>', 0, 0, 0, 0),
(180, 'mohsin@noor.com.pk', 0, 0, 0, 0),
(129, 'mohsin@noor.com.pk', 1, 0, 0, 0),
(325, 'mufaddal mustafa <muffi606@gmail.com>', 0, 0, 0, 0),
(209, 'Mufaddal Shakir <mufaddalshakir@burhanisolutions.com.pk>', 0, 0, 0, 0),
(322, 'muffi606@GMAIL.COM', 0, 0, 0, 0),
(149, 'muffi606@GMAIL.COM', 11, 0, 0, 0),
(181, 'Muhammad Abdullah <m.abdullah@abdullahbrothers.com>', 0, 0, 0, 0),
(340, 'Muhammad Abid <abid.hyd@noor.com.pk>', 0, 0, 0, 0),
(216, 'Muhammad Adil Hanif <adil@noor.com.pk>', 0, 0, 0, 0),
(199, 'Muhammad Ameen <m.ameenghaffar@yahoo.com>', 0, 0, 0, 0),
(254, 'Muhammad Fahad <m.fahad2016@gmail.com>', 0, 0, 0, 0),
(240, 'Muhammad Owais Kamani <owais@noor.com.pk>', 0, 0, 0, 0),
(211, 'mustoopk@gmail.com', 0, 0, 0, 0),
(314, 'naf_sab@yahoo.com', 0, 0, 0, 0),
(253, 'najamuddinghori3@gmail.com', 0, 0, 0, 0),
(275, 'Nasir Lakhani <nasir@noor.com.pk>', 0, 0, 0, 0),
(218, 'nasir@noor.com.pk', 0, 0, 0, 0),
(298, 'Nazish Khan <nazish@noor.com.pk>', 0, 0, 0, 0),
(183, 'Noor Distributor <info@noor.com.pk>', 0, 0, 0, 0),
(280, 'noorandcompany774@gmail.com', 0, 0, 0, 0),
(246, 'noorhyd@noor.com.pk', 0, 0, 0, 0),
(177, 'null', 0, 0, 0, 0),
(249, 'owais@noor.com.pk', 0, 0, 0, 0),
(296, 'PDL ACCOUNTS <finance.pharmadlab@gmail.com>', 0, 0, 0, 0),
(297, 'Pharma Dlab <pharmadlab@gmail.com>', 0, 0, 0, 0),
(20, 'pharmakhi@brightgroup.com.pk', 7, 0, 0, 0),
(295, 'polkadots.pk4@gmail.com', 0, 0, 0, 0),
(323, 'polkadots.pk4@gmail.com', 12, 0, 0, 0),
(324, 'polkadots2006@gmail.com', 12, 0, 0, 0),
(281, 'Proactive Linkers <proactivelinkers@gmail.com>', 0, 0, 0, 0),
(316, 'Rafique Dhedhi <dhedhirafique@gmail.com>', 0, 0, 0, 0),
(272, 'reporting pdl <reporting.pdl@gmail.com>', 0, 0, 0, 0),
(224, 's haider <farhan.haider@abdullahbrothers.com>', 0, 0, 0, 0),
(328, 'Saad Ahmed <support@amarantpharma.com>', 0, 0, 0, 0),
(291, 'Saad Khan <saad.khan@anovaenterprises.com>', 0, 0, 0, 0),
(248, 'Sadiq Patel <safupk@hotmail.com>', 0, 0, 0, 0),
(207, 'Sajid Raheem <sajid@noor.com.pk>', 0, 0, 0, 0),
(311, 'saleem memon <polkadots.pk4@gmail.com>', 0, 0, 0, 0),
(56, 'Sales Department <sales@agmoosa.com.pk>', NULL, 0, 0, 0),
(279, 'samreen sarfaraz <samreensarfaraz0@gmail.com>', 0, 0, 0, 0),
(265, 'Shahid Iqbal Bhatti <shahid@noor.com.pk>', 0, 0, 0, 0),
(267, 'Shahjahan Dagiya <shahjahan@noor.com.pk>', 0, 0, 0, 0),
(31, 'Shahzad Noor <shahzad@noor.com.pk>', NULL, 0, 0, 0),
(46, 'Shahzad Noor <shahzad@noor.com.pk>', NULL, 0, 0, 0),
(76, 'Shahzad Noor <shahzad@noor.com.pk>', NULL, 0, 0, 0),
(184, 'Shahzad Noor <shahzad@noor.com.pk>', 0, 0, 0, 0),
(220, 'shahzad@noor.com.pk', 0, 0, 0, 0),
(130, 'shahzad@noor.com.pk', 1, 0, 0, 0),
(208, 'Shajahan Dagya <shahjahan@noor.com.pk>', 0, 0, 0, 0),
(185, 'Shakir Hussain <shakirhussain@burhanisolutions.com.pk>', 0, 0, 0, 0),
(33, 'Shakir Shaikh <shakir@noor.com.pk>', NULL, 0, 0, 0),
(258, 'SHAKIR SHAIKH <shakir@noor.com.pk>', 0, 0, 0, 0),
(72, 'Shakir@noor.com.pk', NULL, 0, 0, 0),
(256, 'shakir@noor.com.pk', 0, 0, 0, 0),
(131, 'shakir@noor.com.pk', 1, 0, 0, 0),
(178, 'shakirhussain@burhanisolutions.com.pk', 0, 0, 0, 0),
(332, 'Shamshad Ahmed <shamshad@amarantpharma.com>', 0, 0, 0, 0),
(191, 'Sheheryar <shehriyar.hyd@noor.com.pk>', 0, 0, 0, 0),
(214, 'Sheheryar Zai <shehriyar.hyd@noor.com.pk>', 0, 0, 0, 0),
(271, 'shehriyar.hyd@noor.com.pk', 0, 0, 0, 0),
(15, 'Shehriyar.hyd@noor.com.pk', 2, 0, 0, 0),
(290, 'SOHAIB AKHTAR <purchase@abdullahbrothers.com>', 0, 0, 0, 0),
(28, 'support@abdullahbrothers.com', 9, 0, 0, 0),
(292, 'support@amarantpharma.com', 0, 0, 0, 0),
(308, 'support@burhanisolutions.com.pk', 0, 0, 0, 0),
(70, 'Syed Fayyaz Ahmed <fayyaz@noor.com.pk>', NULL, 0, 0, 0),
(176, 'Syed Fayyaz Ahmed <fayyaz@noor.com.pk>', 0, 0, 0, 0),
(329, 'Syed Khurram <syed.khurram@amarantpharma.com>', 0, 0, 0, 0),
(276, 'szl dsr <schazoozaka.dsr@gmail.com>', 0, 0, 0, 0),
(205, 'Tahir Ahmed Khan <tahir@noor.com.pk>', 0, 0, 0, 0),
(317, 'Tahir Shakir <tahir60652@gmail.com>', 0, 0, 0, 0),
(39, 'tahir60652@gmail.com', NULL, 0, 0, 0),
(210, 'tahir60652@gmail.com', 0, 0, 0, 0),
(212, 'tahir606@burhanisolutions.com.pk', 0, 0, 0, 0),
(269, 'tahir@noor.com.pk', 0, 0, 0, 0),
(200, 'umer jawed <umer.jawed@abdullahbrothers.com>', 0, 0, 0, 0),
(283, 'Usama Bhakrani <usamabhakrani@gmail.com>', 0, 0, 0, 0),
(194, 'Usman Fayyaz <claims@abdullahbrothers.com>', 0, 0, 0, 0),
(321, 'Uzair Javed <polkadots2006@gmail.com>', 0, 0, 0, 0),
(270, 'Wahid Hussain <noorhyd@noor.com.pk>', 0, 0, 0, 0),
(60, 'Waqar <waqar@noorandcompany.com>', NULL, 0, 0, 0),
(61, 'Waqar <waqar@noorandcompany.com>', NULL, 0, 0, 0),
(182, 'Waqar <waqar@noorandcompany.com>', 0, 0, 0, 0),
(257, 'WAQAR AHMED <waqar@noorandcompany.com>', 0, 0, 0, 0),
(284, 'waqar ahmed in me no know you <waqar.ahmed774@gmail.com>', 0, 0, 0, 0),
(231, 'waqar@noorandcompany.com', 0, 0, 0, 0),
(16, 'waqar@noorandcompany.com', 3, 0, 0, 0),
(188, 'Waqas Khan <waqas.hyd@noor.com.pk>', 0, 0, 0, 0),
(215, 'waseem Nagori <waseem@noor.com.pk>', 0, 0, 0, 0),
(268, 'waseem@noor.com.pk', 0, 0, 0, 0),
(301, 'Zohaib Chottani <zohaibchottani335@highqpharma.com>', 0, 0, 0, 0),
(230, 'ZubairDhedhi <zubairdhedhi@brightgroup.com.pk>', 0, 0, 0, 0),
(310, 'zubairdhedhi@brightgroup.com.pk', 0, 0, 0, 0);

-- --------------------------------------------------------

--
-- Table structure for table `email_relation`
--

CREATE TABLE `email_relation` (
  `EMNO` int(11) NOT NULL,
  `EMTYPE` int(11) NOT NULL,
  `EM_ID` int(11) NOT NULL,
  `CL_ID` int(11) DEFAULT NULL,
  `UCODE` int(11) DEFAULT NULL,
  `CS_ID` int(11) DEFAULT NULL
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
  `UPLD_ATTCH` varchar(500) DEFAULT NULL,
  `TSTMP` datetime NOT NULL,
  `UCODE` int(11) NOT NULL,
  `ESNO` int(11) NOT NULL DEFAULT '0' COMMENT 'Connects Email Store',
  `SENT` tinyint(1) NOT NULL DEFAULT '1',
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
  `ES_GEN_EMAIL` varchar(100) DEFAULT NULL,
  `AUTOCHK` tinyint(1) DEFAULT NULL,
  `DISCCHK` tinyint(1) DEFAULT NULL,
  `AUTOTXT` text,
  `DISCTXT` text,
  `SOLVCHK` tinyint(1) DEFAULT NULL,
  `SOLVTXT` text
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `email_settings`
--

INSERT INTO `email_settings` (`ECODE`, `HOST`, `EMAIL`, `PASS`, `FSPATH`, `ES_GEN_EMAIL`, `AUTOCHK`, `DISCCHK`, `AUTOTXT`, `DISCTXT`, `SOLVCHK`, `SOLVTXT`) VALUES
(1, 'burhanisolutions.com.pk', 'support@burhanisolutions.com.pk', 'burhanisupport', '\\\\192.168.100.210\\crm\\Files', 'bits-noreply@burhanisolutions.com.pk', 1, 0, 'Thank you for contacting Burhani Customer Service.', '', 1, 'Your Ticket has been closed. \n\nPlease do not reply to this email.\n\nThank you,\n\nBITS Customer Relations Department.');

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
  `SOLVTIME` datetime DEFAULT NULL,
  `LOCKD` int(11) DEFAULT NULL,
  `TSTMP` datetime DEFAULT NULL,
  `MANUAL` int(11) DEFAULT NULL,
  `FREZE` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `email_store_related`
--

CREATE TABLE `email_store_related` (
  `ESR_ID` int(11) NOT NULL,
  `EMNO` int(11) NOT NULL,
  `MSGNO` int(11) DEFAULT NULL,
  `SBJCT` varchar(5000) DEFAULT NULL,
  `TOADD` varchar(5000) NOT NULL,
  `FRADD` varchar(5000) NOT NULL,
  `CCADD` varchar(5000) DEFAULT NULL,
  `EBODY` text,
  `ATTCH` varchar(5000) DEFAULT NULL,
  `TSTMP` datetime NOT NULL,
  `FREZE` tinyint(1) NOT NULL DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `event_store`
--

CREATE TABLE `event_store` (
  `ES_ID` int(11) NOT NULL,
  `ES_TITLE` varchar(500) NOT NULL,
  `ES_LOCATION` varchar(1000) NOT NULL,
  `ES_ALLDAY` tinyint(1) NOT NULL,
  `ES_FROM` datetime DEFAULT NULL,
  `ES_TO` datetime DEFAULT NULL,
  `ES_DESC` text NOT NULL,
  `NOTIFIED` int(11) NOT NULL DEFAULT '0',
  `CL_ID` int(11) NOT NULL DEFAULT '0',
  `LS_ID` int(11) NOT NULL DEFAULT '0',
  `PS_ID` int(11) NOT NULL DEFAULT '0',
  `CREATEDBY` int(11) NOT NULL,
  `CREATEDON` datetime NOT NULL,
  `FREZE` int(11) NOT NULL DEFAULT '0',
  `ES_CLOSEDON` datetime DEFAULT NULL,
  `ES_STATUS` tinyint(1) NOT NULL DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `event_store`
--

INSERT INTO `event_store` (`ES_ID`, `ES_TITLE`, `ES_LOCATION`, `ES_ALLDAY`, `ES_FROM`, `ES_TO`, `ES_DESC`, `NOTIFIED`, `CL_ID`, `LS_ID`, `PS_ID`, `CREATEDBY`, `CREATEDON`, `FREZE`, `ES_CLOSEDON`, `ES_STATUS`) VALUES
(1, 'Sample Event', 'Sample Location', 0, '2018-07-18 12:17:00', '2018-07-20 18:17:00', 'Sample Description', 0, 0, 2, 0, 6, '2018-07-16 18:18:05', 0, NULL, 0),
(2, 'khana', 'at kbc', 0, '2018-07-17 20:15:00', '2018-07-17 22:31:00', 'mufta\n', 0, 1, 0, 0, 2, '2018-07-16 18:31:22', 0, NULL, 0),
(3, 'gdhgfdhdfhgdfh', 'tgfrgh', 0, '2018-07-11 20:53:00', '2018-07-01 17:54:00', 'hgfdfh', 0, 11, 0, 0, 6, '2018-07-21 13:54:35', 0, '2018-07-21 13:54:50', 1),
(4, 'shahzad noor', 'enu ghar', 0, '2018-08-08 09:00:00', '2018-08-08 12:33:00', 'paisa ne wath karwees', 1, 0, 0, 0, 2, '2018-08-08 13:33:52', 0, NULL, 0);

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
  `LS_NOTE` text,
  `S_ID` int(11) DEFAULT NULL,
  `S_OTHER` text,
  `CONVERTED` tinyint(1) NOT NULL DEFAULT '0',
  `CREATEDBY` int(11) NOT NULL,
  `CREATEDON` datetime NOT NULL,
  `FREZE` tinyint(1) NOT NULL DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `lead_store`
--

INSERT INTO `lead_store` (`LS_ID`, `LS_FNAME`, `LS_LNAME`, `LS_CNAME`, `LS_TITLE`, `LS_WEBSITE`, `LS_CITY`, `LS_COUNTRY`, `LS_NOTE`, `S_ID`, `S_OTHER`, `CONVERTED`, `CREATEDBY`, `CREATEDON`, `FREZE`) VALUES
(1, 'Shakir', 'Hussain', 'Burhani IT Solutions', NULL, 'www.burhanisolutions.com.pk', 'Karachi', 'Pakistan', 'Some Description', NULL, NULL, 0, 1, '2018-07-04 16:22:15', 0),
(2, 'Maroof', 'Hussain', 'Pak Channels', NULL, 'www.dummy.com', 'Karachi', 'Pakistan', '', NULL, NULL, 0, 1, '2018-07-04 16:16:24', 1),
(3, 'Jamshed', 'Noorani', 'Noorani Corner', NULL, 'www.nooraniCorner.com', 'Karachi', 'Pakistan', '', NULL, NULL, 0, 1, '2018-07-04 16:22:01', 1),
(4, 'Akbar', 'Jodha', 'The Mughal Empire', NULL, 'www.mughal.com', 'Karachi', 'Pakistan', '', 0, NULL, 1, 6, '2018-10-03 17:31:25', 0);

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

--
-- Dumping data for table `module_locking`
--

INSERT INTO `module_locking` (`PM_ID`, `UCODE`, `LOCKEDTIME`, `UNLOCKEDTIME`, `DESCRIPTION`, `PS_ID`) VALUES
(1, 1, '2018-05-21 15:37:22', '2018-06-05 10:14:12', 'Done ', 2),
(1, 1, '2018-05-21 15:44:01', '2018-05-21 15:44:30', 'dummy', 1),
(2, 2, '2018-05-21 15:56:17', '2018-05-21 15:56:50', '.\n', 2),
(8, 5, '2018-06-05 10:15:44', '2018-07-14 17:57:22', '.', 2),
(3, 1, '2018-06-20 17:31:17', NULL, NULL, 1),
(2, 1, '2018-06-27 11:46:45', NULL, NULL, 1),
(1, 2, '2018-07-03 18:40:12', '2018-07-13 11:39:13', 'ok', 2),
(4, 5, '2018-07-04 13:21:09', '2018-08-07 14:40:53', '', 2),
(8, 5, '2018-07-09 12:04:30', '2018-07-14 17:57:22', '.', 2),
(4, 6, '2018-07-13 11:39:39', '2018-07-13 17:23:50', 'eeessgfsd', 2),
(2, 5, '2018-07-13 15:31:42', '2018-09-17 15:12:05', '', 2),
(3, 5, '2018-07-13 15:31:43', '2018-07-14 12:07:42', '.', 2),
(2, 5, '2018-07-13 17:23:17', '2018-09-17 15:12:05', '', 2),
(3, 5, '2018-07-13 17:23:20', '2018-07-14 12:07:42', '.', 2),
(9, 6, '2018-07-13 17:23:53', '2018-07-19 17:54:27', 'rsdfs', 2),
(8, 5, '2018-07-14 12:08:30', '2018-07-14 17:57:22', '.', 2),
(4, 5, '2018-07-16 12:50:33', '2018-08-07 14:40:53', '', 2),
(2, 5, '2018-07-23 11:42:30', '2018-09-17 15:12:05', '', 2),
(3, 6, '2018-07-27 11:05:02', '2018-07-27 12:33:12', 'done	', 2),
(4, 5, '2018-07-27 11:29:24', '2018-08-07 14:40:53', '', 2),
(1, 5, '2018-07-27 11:52:45', '2018-07-28 15:12:59', '.', 2),
(9, 5, '2018-07-28 15:13:28', '2018-07-28 15:19:32', '.', 2),
(12, 5, '2018-07-28 15:24:52', '2018-07-28 15:56:25', '.	', 2),
(4, 5, '2018-07-28 15:59:05', '2018-08-07 14:40:53', '', 2),
(2, 5, '2018-08-02 14:16:41', '2018-09-17 15:12:05', '', 2),
(4, 5, '2018-08-04 15:56:53', '2018-08-07 14:40:53', '', 2),
(2, 5, '2018-08-08 12:31:04', '2018-09-17 15:12:05', '', 2),
(7, 5, '2018-08-27 12:32:33', '2018-08-27 14:57:04', '', 2),
(2, 5, '2018-08-27 14:57:07', '2018-09-17 15:12:05', '', 2),
(10, 5, '2018-09-17 15:12:18', '2019-03-12 15:24:16', '', 2);

-- --------------------------------------------------------

--
-- Table structure for table `note_store`
--

CREATE TABLE `note_store` (
  `N_TEXT` text NOT NULL,
  `EMNO` int(11) NOT NULL DEFAULT '0',
  `CS_ID` int(11) NOT NULL DEFAULT '0',
  `CL_ID` int(11) NOT NULL DEFAULT '0',
  `PS_ID` int(11) NOT NULL DEFAULT '0',
  `LS_ID` int(11) NOT NULL DEFAULT '0',
  `CREATEDBY` int(11) NOT NULL,
  `CREATEDON` datetime NOT NULL,
  `N_ID` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `note_store`
--

INSERT INTO `note_store` (`N_TEXT`, `EMNO`, `CS_ID`, `CL_ID`, `PS_ID`, `LS_ID`, `CREATEDBY`, `CREATEDON`, `N_ID`) VALUES
('Tahir Note 1', 0, 1, 0, 0, 0, 1, '2018-06-05 09:47:25', 1),
('MS Note 1', 0, 2, 0, 0, 0, 1, '2018-06-05 09:48:28', 1),
('Note NDK 3', 0, 0, 1, 0, 0, 1, '2018-06-05 12:42:23', 2),
('Died on 6th Dec', 0, 1, 0, 0, 0, 1, '2018-06-05 12:42:56', 2),
('Tahir ko call karo', 0, 1, 0, 0, 0, 1, '2018-06-27 11:45:11', 3),
('sample note', 0, 0, 1, 0, 0, 2, '2018-07-13 18:16:12', 3);

-- --------------------------------------------------------

--
-- Table structure for table `notification_settings`
--

CREATE TABLE `notification_settings` (
  `NS_ID` int(11) NOT NULL,
  `NS_TASK1` tinyint(1) NOT NULL COMMENT 'Notification on due date',
  `NS_EVENT1` tinyint(1) NOT NULL COMMENT 'Notification on From Date'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `notification_settings`
--

INSERT INTO `notification_settings` (`NS_ID`, `NS_TASK1`, `NS_EVENT1`) VALUES
(1, 1, 1);

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

--
-- Dumping data for table `phone_list`
--

INSERT INTO `phone_list` (`PH_ID`, `PH_NUM`, `CL_ID`, `UCODE`, `CS_ID`, `LS_ID`) VALUES
(12, '090078601', 0, 0, 0, 0),
(11, '123131', 1, 0, 0, 0);

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

--
-- Dumping data for table `product_module`
--

INSERT INTO `product_module` (`PM_ID`, `PS_ID`, `PM_NAME`, `PM_DESC`, `CREATEDBY`, `CREATEDON`) VALUES
(1, 1, 'Email', 'Email Handler', 1, '2018-06-20 17:31:08'),
(2, 1, 'Contacts', 'Contacts and clients', 1, '2018-06-20 17:31:08'),
(3, 1, 'Task', 'Task Creation and closure', 1, '2018-06-20 17:31:08'),
(1, 2, 'Codes', 'List of Codes', 1, '2018-07-04 16:24:21'),
(2, 2, 'General', 'general operations. Summary', 1, '2018-07-04 16:24:21'),
(3, 2, 'Purchase', 'Deals with purchases	', 1, '2018-07-04 16:24:21'),
(4, 2, 'Sales', '', 1, '2018-07-04 16:24:21'),
(5, 2, 'Summaries', '', 1, '2018-07-04 16:24:21'),
(6, 2, 'Sale Return', '', 1, '2018-07-04 16:24:21'),
(7, 2, 'Claims', '', 1, '2018-07-04 16:24:21'),
(8, 2, 'Receipts', '', 1, '2018-07-04 16:24:21'),
(9, 2, 'Accounts', '', 1, '2018-07-04 16:24:21'),
(10, 2, 'Employee', '', 1, '2018-07-04 16:24:21'),
(11, 2, 'Manager', '', 1, '2018-07-04 16:24:21'),
(12, 2, 'Devices', '', 1, '2018-07-04 16:24:21'),
(13, 2, 'Closing', '', 1, '2018-07-04 16:24:21'),
(14, 2, 'Portal', '', 1, '2018-07-04 16:24:21');

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
  `CREATEDBY` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `product_store`
--

INSERT INTO `product_store` (`PS_ID`, `PS_NAME`, `PS_PRICE`, `PS_DESC`, `PS_STATUS`, `PS_TYPE`, `PS_STARTED`, `PS_PRIORITY`, `CREATEDON`, `CREATEDBY`) VALUES
(1, 'CRM', 30000, 'Best CRM ever ', 0, 0, '2018-01-01 00:00:00', 0, '2018-06-20 17:31:08', 1),
(2, 'Distribution Management Software', 0, 'Distrib', 0, 0, '2005-05-01 00:00:00', 0, '2018-07-04 16:24:21', 1);

-- --------------------------------------------------------

--
-- Table structure for table `rights_chart`
--

CREATE TABLE `rights_chart` (
  `RCODE` int(11) NOT NULL,
  `UCODE` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `rights_chart`
--

INSERT INTO `rights_chart` (`RCODE`, `UCODE`) VALUES
(1, 3),
(2, 3),
(1, 2),
(2, 2),
(1, 4),
(1, 7);

-- --------------------------------------------------------

--
-- Table structure for table `rights_list`
--

CREATE TABLE `rights_list` (
  `RCODE` int(11) NOT NULL,
  `RNAME` varchar(100) NOT NULL,
  `FREZE` char(1) NOT NULL DEFAULT 'N'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `rights_list`
--

INSERT INTO `rights_list` (`RCODE`, `RNAME`, `FREZE`) VALUES
(1, 'Email Viewer', 'N'),
(2, 'Clients', 'N'),
(3, 'Products', 'N'),
(4, 'Leads', 'N'),
(5, 'Activity', 'N'),
(6, 'Reports', 'N'),
(7, 'Documents', 'N'),
(8, 'General Setting', 'N');

-- --------------------------------------------------------

--
-- Table structure for table `source_list`
--

CREATE TABLE `source_list` (
  `S_ID` int(11) NOT NULL,
  `S_NAME` varchar(500) NOT NULL,
  `S_DESC` text
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `source_list`
--

INSERT INTO `source_list` (`S_ID`, `S_NAME`, `S_DESC`) VALUES
(1, 'Referall', ''),
(2, 'Website', '');

-- --------------------------------------------------------

--
-- Table structure for table `task_store`
--

CREATE TABLE `task_store` (
  `TS_ID` int(11) NOT NULL,
  `TS_SUBJECT` varchar(1000) NOT NULL,
  `TS_EDATE` datetime DEFAULT NULL,
  `TS_DDATE` datetime NOT NULL,
  `TS_REPEAT` tinyint(1) NOT NULL,
  `TS_DESC` text NOT NULL,
  `TS_STATUS` tinyint(1) NOT NULL DEFAULT '0' COMMENT 'Open/Closed',
  `TS_CLOSEDON` datetime DEFAULT NULL,
  `NOTIFIED` tinyint(1) NOT NULL DEFAULT '0',
  `CS_ID` int(11) NOT NULL DEFAULT '0',
  `CL_ID` int(11) NOT NULL DEFAULT '0',
  `PS_ID` int(11) NOT NULL DEFAULT '0',
  `LS_ID` int(11) NOT NULL DEFAULT '0',
  `CREATEDBY` int(11) NOT NULL,
  `CREATEDON` datetime NOT NULL,
  `FREZE` tinyint(1) NOT NULL DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `task_store`
--

INSERT INTO `task_store` (`TS_ID`, `TS_SUBJECT`, `TS_EDATE`, `TS_DDATE`, `TS_REPEAT`, `TS_DESC`, `TS_STATUS`, `TS_CLOSEDON`, `NOTIFIED`, `CS_ID`, `CL_ID`, `PS_ID`, `LS_ID`, `CREATEDBY`, `CREATEDON`, `FREZE`) VALUES
(1, 'File for NTN', NULL, '2018-06-22 00:00:00', 0, 'Detailed Description', 1, NULL, 0, 0, 1, 0, 0, 1, '2018-06-19 10:23:11', 0),
(2, 'File for NTN 2nd Time', NULL, '2018-06-21 00:00:00', 0, 'Detailed Description', 0, NULL, 0, 0, 1, 0, 0, 1, '2018-06-19 12:05:19', 1),
(3, 'Task Number 2', NULL, '2018-06-21 00:00:00', 1, 'This is another task ', 0, NULL, 0, 0, 1, 0, 0, 1, '2018-06-19 12:06:52', 1),
(4, 'This is the third task', NULL, '2018-06-28 00:00:00', 1, 'Why is this', 1, '2018-06-19 12:37:50', 0, 0, 1, 0, 0, 1, '2018-06-19 12:12:59', 0),
(5, 'Task no 3', NULL, '2018-06-30 00:00:00', 0, 'Details for Task no 1', 0, NULL, 0, 0, 0, 0, 1, 1, '2018-06-22 11:14:41', 1),
(6, 'Lead Task 1', NULL, '2018-06-30 00:00:00', 0, 'This is what', 1, '2018-07-05 18:11:22', 0, 0, 0, 0, 1, 1, '2018-06-22 12:07:48', 0),
(7, 'CRM Task 1', NULL, '2018-06-30 00:00:00', 0, 'Detailed Description', 0, NULL, 0, 0, 0, 1, 0, 1, '2018-06-22 12:10:31', 0),
(8, 'Do not delete this . NTN register ', NULL, '2018-06-24 00:00:00', 0, 'Approximately offically company launched. ', 0, NULL, 0, 0, 0, 0, 0, 6, '2018-07-05 18:10:36', 0),
(9, 'Bank account open 6016820311714175858', NULL, '2018-07-07 00:00:00', 0, 'New Bank account of burhani IT solution:  6016820311714175858', 0, NULL, 0, 0, 0, 0, 0, 6, '2018-07-07 13:57:52', 0),
(10, 'Sample Task', NULL, '2018-07-20 00:00:00', 0, 'Sample Task Description', 0, NULL, 0, 0, 0, 0, 2, 6, '2018-07-16 18:18:35', 0),
(11, 'dfhdhgdhdgfh', NULL, '2018-07-02 00:00:00', 1, 'gfdhhdgfdhgdfhgdghdhdgfh', 0, NULL, 0, 0, 11, 0, 0, 6, '2018-07-21 13:55:36', 0),
(12, 'Notify Task', NULL, '2018-08-07 00:00:00', 0, 'Notify task description', 0, NULL, 0, 0, 0, 0, 0, 1, '2018-08-06 17:37:56', 0),
(13, 'P&L', NULL, '2018-08-08 00:00:00', 0, 'cvb', 0, NULL, 1, 0, 0, 0, 0, 6, '2018-08-08 13:25:35', 0),
(14, 'P & L  ', NULL, '2018-08-06 00:00:00', 0, 'Finalized the P & L  report', 0, NULL, 0, 0, 0, 0, 0, 6, '2018-08-08 13:28:14', 0),
(15, 'P & L  updated at A G Moosa ', NULL, '2018-08-08 00:00:00', 0, 'P & L  updated at A G Moosa ', 0, NULL, 1, 0, 0, 0, 0, 6, '2018-08-08 13:31:22', 0),
(16, 'noor', NULL, '2018-08-08 00:00:00', 0, 'noor nu kai kam', 0, NULL, 1, 0, 0, 0, 0, 2, '2018-08-08 13:30:54', 0),
(17, 'Accounts , Income statement update', NULL, '2018-08-16 00:00:00', 0, 'Purchases and sales almost done. \nStarted working now on expenses.\n', 0, NULL, 1, 0, 0, 0, 0, 6, '2018-08-16 19:14:07', 0),
(18, 'Income Statement expense updated', NULL, '2018-08-17 00:00:00', 0, 'Income Statement expense updated , Danyal Nini. ', 0, NULL, 0, 0, 0, 0, 0, 6, '2018-08-17 18:59:00', 0),
(19, 'balance sheet started 18/08/2018', NULL, '2018-08-18 00:00:00', 0, 'started balance sheet in making : started 18/08/2018', 0, NULL, 0, 0, 0, 0, 0, 6, '2018-08-18 15:25:58', 0),
(20, 'Entry Date Task', NULL, '2018-08-31 00:00:00', 0, 'This is what it is', 0, NULL, 0, 0, 0, 0, 0, 1, '2018-08-25 16:00:24', 1),
(21, 'Entry Date Again', '2018-08-01 00:00:00', '2018-08-31 00:00:00', 0, 'This is what it is', 0, NULL, 0, 0, 0, 0, 0, 1, '2018-08-25 16:04:32', 1),
(22, 'Noor Task Dummy', '2018-08-20 00:00:00', '2018-08-31 00:00:00', 0, 'This is a dummy task for developer', 0, NULL, 0, 0, 1, 0, 0, 1, '2018-08-25 16:28:08', 1),
(23, 'Balance sheet ', '2018-08-25 00:00:00', '2018-08-25 00:00:00', 0, '25% done\n', 0, NULL, 1, 0, 0, 0, 0, 6, '2018-08-25 16:46:58', 0),
(24, 'Update HR Program At PolkaDots V1', '2018-09-04 00:00:00', '2018-09-04 00:00:00', 1, 'Update HR Program At PolkaDots V1', 0, NULL, 0, 0, 0, 0, 0, 6, '2018-09-05 14:25:33', 0),
(25, 'Nini Updated Ledger ', '2019-01-26 00:00:00', '2019-01-26 00:00:00', 0, '26-jan-2018 \nWorked on Drawing master, run an automated script \n', 0, NULL, 0, 0, 0, 0, 0, 4, '2019-01-26 11:48:24', 0);

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
-- Dumping data for table `users`
--

INSERT INTO `users` (`UCODE`, `FNAME`, `UNAME`, `Email`, `UPASS`, `NOTE`, `URIGHT`, `FREZE`, `SOLV`, `LOCKD`, `ISLOG`, `ISEMAIL`) VALUES
(1, 'Tahir Shakir', 'tahir606', 'tahir60652@gmail.com', 'pin123', 'Super Admin', 'Admin', 'N', 19, 0, 1, 'N'),
(2, 'Shakir Hussain', 'mustoopk', 'mustoopk@gmail.com', 'pin123', 'Admin', 'Admin', 'N', 33, 0, 1, 'N'),
(4, 'Mufaddal ', 'muffi', 'm@g.com', 'pin123', NULL, 'Admin', 'N', 5, NULL, 1, 'N'),
(5, 'Kashif Dhedhi', 'KD', 'kashifdhedhi@gmail.com', 'pin123', NULL, 'Admin', 'N', 40, NULL, 1, 'N'),
(6, 'Test User', 'MS', 'muffi606@gmail.com', 'pin123', NULL, 'Admin', 'N', 4, NULL, 1, 'Y'),
(7, 'Syed Muhummad Amir Hussain', 'Amir', 'syedamir595@gmail.com', 'pin123', NULL, 'Admin', 'Y', NULL, NULL, 0, 'N'),
(8, 'Moiz Mustafa', 'moiz', 'moizmsj@gmail.com', 'pin123', NULL, 'Admin', 'Y', NULL, NULL, 1, 'N'),
(9, 'Jahanzaib Altaf', 'Jahanzaib', 'jahnzaibjani@gmail.com', 'pin123', NULL, 'Admin', 'N', NULL, NULL, 1, 'N'),
(10, 'Fahad Jangardh', 'fahad', 'fahad.jangardh@gmail.com', 'pin123', NULL, 'Admin', 'N', NULL, NULL, 1, 'N');

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
  ADD UNIQUE KEY `EM_ID` (`EM_ID`,`EMNO`,`CL_ID`,`UCODE`,`CS_ID`);

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
  ADD UNIQUE KEY `PH_NUM` (`PH_NUM`,`CL_ID`,`UCODE`,`CS_ID`,`LS_ID`);

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
-- Indexes for table `source_list`
--
ALTER TABLE `source_list`
  ADD PRIMARY KEY (`S_ID`);

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
