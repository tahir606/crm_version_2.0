-- phpMyAdmin SQL Dump
-- version 4.7.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Feb 14, 2018 at 01:29 PM
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
-- Table structure for table `email_list`
--

CREATE TABLE `email_list` (
  `EM_ID` int(11) NOT NULL,
  `EM_NAME` varchar(500) NOT NULL,
  `CL_ID` int(11) DEFAULT NULL,
  `UCODE` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `email_list`
--

INSERT INTO `email_list` (`EM_ID`, `EM_NAME`, `CL_ID`, `UCODE`) VALUES
(5, 'haroon@agmoosa.com.pk', 2, NULL),
(6, 'info@burhanisolutions.com.pk', 1, NULL),
(7, 'shakirhussain@burhanisolutions.com.pk', 1, NULL),
(8, 'tahir606@burhanisolutions.com.pk', 1, NULL),
(9, ' ', 1, NULL);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `email_list`
--
ALTER TABLE `email_list`
  ADD PRIMARY KEY (`EM_ID`),
  ADD UNIQUE KEY `EM_ID` (`EM_ID`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
