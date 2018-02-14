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
-- Table structure for table `phone_list`
--

CREATE TABLE `phone_list` (
  `PH_ID` int(11) NOT NULL,
  `PH_NUM` varchar(100) NOT NULL,
  `CL_ID` int(11) DEFAULT NULL,
  `UCODE` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `phone_list`
--

INSERT INTO `phone_list` (`PH_ID`, `PH_NUM`, `CL_ID`, `UCODE`) VALUES
(1, '03342132778', 1, NULL),
(2, '  ', 1, NULL);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `phone_list`
--
ALTER TABLE `phone_list`
  ADD PRIMARY KEY (`PH_ID`),
  ADD UNIQUE KEY `PH_ID` (`PH_ID`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
