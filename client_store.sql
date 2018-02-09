-- phpMyAdmin SQL Dump
-- version 4.7.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Feb 09, 2018 at 07:01 AM
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
  `CL_BCYCLE` date DEFAULT NULL,
  `CL_TYPE` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `client_store`
--

INSERT INTO `client_store` (`CL_ID`, `CL_NAME`, `CL_OWNER`, `CL_EMAIL`, `CL_PHONE`, `CL_ADDR`, `CL_CITY`, `CL_COUNTRY`, `CL_WEBSITE`, `CL_JOINDATE`, `CL_BCYCLE`, `CL_TYPE`) VALUES
(1, 'Shakir Hussain', 'Burhani IT Solutions', 'info@burhanisolutions.com.pk', '03332132778', 'Sharfabad', 'Karachi', 'Pakistan', 'www.burhanisolutions.com.pk', NULL, NULL, 1),
(2, 'Bo Burnham', 'Make Happy', 'bo@bo.com', '0333333333', '', 'Bo', 'BOOO', 'bo.com.pk', '2018-02-01', NULL, 1),
(3, 'Bo Burnham', 'Make Happy', 'bo@bo.com', '0333333333', 'bo burnham updates', 'Bo', 'BOOO', 'bo.com.pk', '2018-02-01', NULL, 1),
(4, 'Shakir Hussain', 'Burhani IT Solutions', 'info@burhanisolutions.com.pk', '03332132778', 'Sharfabad', 'Karachi', 'Pakistan', 'www.burhanisolutions.com.pk', '2018-02-01', NULL, 1),
(5, 'BITS', NULL, 'b@g.com', '876786', 'Sharfabad', 'khi', 'pak', 'trai.com', '2018-02-01', NULL, 1);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `client_store`
--
ALTER TABLE `client_store`
  ADD PRIMARY KEY (`CL_ID`),
  ADD UNIQUE KEY `CL_ID` (`CL_ID`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
