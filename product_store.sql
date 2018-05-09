-- phpMyAdmin SQL Dump
-- version 4.7.4
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: May 09, 2018 at 12:06 PM
-- Server version: 10.1.30-MariaDB
-- PHP Version: 5.6.33

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
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
