-- phpMyAdmin SQL Dump
-- version 4.7.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jan 20, 2018 at 02:59 PM
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

DELIMITER $$
--
-- Functions
--
CREATE DEFINER=`root`@`localhost` FUNCTION `getConcatList` (`rootId` INT(11)) RETURNS VARCHAR(1000) CHARSET latin1 BEGIN
DECLARE sParentList VARCHAR (1000) ;
DECLARE sParentTemp VARCHAR(1000); 
SET sParentTemp =CAST(rootId AS CHAR); 
WHILE sParentTemp IS NOT NULL DO 
IF (sParentList IS NOT NULL) THEN 
SET sParentList = CONCAT(sParentTemp,'-',sParentList); 
ELSE 
SET sParentList = CONCAT(sParentTemp); 
END IF; 
SELECT GROUP_CONCAT(RCODE) INTO sParentTemp FROM Rights_chart WHERE UCODE = rootId; 
END WHILE; 
RETURN sParentList; 
END$$

CREATE DEFINER=`root`@`localhost` FUNCTION `hierarchy_sys_connect_by_path` (`delimiter` TEXT, `node` INT) RETURNS TEXT CHARSET latin1 READS SQL DATA
BEGIN
     DECLARE _path TEXT;
 DECLARE _cpath TEXT;
        DECLARE _id INT;
    DECLARE EXIT HANDLER FOR NOT FOUND RETURN _path;
    SET _id = COALESCE(node, @id);
      SET _path = _id;
    LOOP
                SELECT  parent
              INTO    _id
         FROM    t_hierarchy
         WHERE   id = _id
                    AND COALESCE(id <> @start_with, TRUE);
              SET _path = CONCAT(_id, delimiter, _path);
  END LOOP;
END$$

DELIMITER ;

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
  `DISCTXT` text
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `email_settings`
--

INSERT INTO `email_settings` (`ECODE`, `HOST`, `EMAIL`, `PASS`, `FSPATH`, `AUTOCHK`, `DISCCHK`, `AUTOTXT`, `DISCTXT`) VALUES
(1, 'burhanisolutions.com.pk', 'sales@burhanisolutions.com.pk', 'burhanisales', 'C:\\Users\\Tahir\\Bits\\CRM\\Files\\', 1, 1, 'Thank you for contacting Burhani Customer Service.\nYour complaint has been successfully registered.\nOur IT department has started working to resolve your issue.\nWe will notify you of any further development.', 'BITS Customer Support.');

-- --------------------------------------------------------

--
-- Table structure for table `email_store`
--

CREATE TABLE `email_store` (
  `EMNO` int(11) NOT NULL,
  `MSGNO` int(11) NOT NULL,
  `SBJCT` varchar(10000) NOT NULL,
  `TOADD` varchar(1000) NOT NULL,
  `FRADD` varchar(1000) NOT NULL,
  `EBODY` text NOT NULL,
  `ATTCH` varchar(10000) NOT NULL,
  `CCADD` varchar(5000) NOT NULL,
  `ESOLV` char(1) NOT NULL,
  `SOLVBY` int(11) DEFAULT NULL,
  `LOCKD` int(11) NOT NULL,
  `TSTMP` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `email_store`
--

INSERT INTO `email_store` (`EMNO`, `MSGNO`, `SBJCT`, `TOADD`, `FRADD`, `EBODY`, `ATTCH`, `CCADD`, `ESOLV`, `SOLVBY`, `LOCKD`, `TSTMP`) VALUES
(12, 20, 'Tesat', '^sales@burhanisolutions.com.pk', '^tahir shakir <tahirshakir606@gmail.com>', '\ntesla\r\n', 'No Attachments', '', 'S', 0, 1, '2018-01-02 11:40:31'),
(13, 21, 'Test', '^sales@burhanisolutions.com.pk', '^tahir shakir <tahirshakir606@gmail.com>', '\nTest with different attach\r\n', '.\\files\\companyprofile.pdf^', '', 'S', 0, 1, '2018-01-02 12:49:01'),
(14, 25, 'mail', '^sales@burhanisolutions.com.pk', '^tahir shakir <tahirshakir606@gmail.com>', '\nconnect exception\r\n', 'No Attachments', '', 'S', 0, 1, '2018-01-04 02:06:21'),
(15, 26, 'Check Ticket', '^sales@burhanisolutions.com.pk', '^tahir shakir <tahirshakir606@gmail.com>', '\nReply added\r\n', 'No Attachments', '', 'S', 0, 1, '2018-01-04 03:58:16'),
(16, 27, 'MAIL TEST', '^sales@burhanisolutions.com.pk', '^tahir shakir <tahirshakir606@gmail.com>', '\nTEST MAIL\r\n', 'No Attachments', '', 'N', 0, 1, '2018-01-04 10:17:08'),
(17, 28, 'MAIL test', '^sales@burhanisolutions.com.pk', '^tahir shakir <tahirshakir606@gmail.com>', '\ntest 2 mail\r\n', 'No Attachments', '', 'N', 0, 1, '2018-01-04 10:22:08'),
(18, 11, 'Checking Reply', '^sales@burhanisolutions.com.pk', '^tahir606@burhanisolutions.com.pk', '\nEmail to Check Reply\r\n\r\n', 'No Attachments', '^tahir60652@gmail.com', 'S', 0, 1, '2018-01-05 11:33:58'),
(19, 10, 'What', '^sales@burhanisolutions.com.pk', '^tahir shakir <tahirshakir606@gmail.com>', '\nTesting the smol btns\r\n', 'No Attachments', '', 'N', 0, 0, '2018-01-05 03:28:11'),
(20, 11, 'Testing', '^sales@burhanisolutions.com.pk', '^tahir shakir <tahirshakir606@gmail.com>', '\nTesting load\r\n', 'No Attachments', '', 'N', 0, 1, '2018-01-05 03:29:04'),
(21, 12, 'NO CLUE', '^sales@burhanisolutions.com.pk', '^tahir shakir <tahirshakir606@gmail.com>', '\nNO CLUE WHATS HAPPENING\r\n', 'No Attachments', '', 'N', 0, 1, '2018-01-05 04:09:31'),
(22, 13, 'I AM', '^sales@burhanisolutions.com.pk', '^tahir shakir <tahirshakir606@gmail.com>', '\nLet it gooooo let it\r\n\r\n\r\ngooooo\r\n', 'No Attachments', '', 'N', 0, 1, '2018-01-05 04:28:55'),
(23, 14, 'check', '^sales@burhanisolutions.com.pk', '^tahir shakir <tahirshakir606@gmail.com>', '\ncheck imdex\r\n', 'No Attachments', '', 'N', 0, 1, '2018-01-05 04:30:11'),
(24, 15, 'w', '^sales@burhanisolutions.com.pk', '^tahir shakir <tahirshakir606@gmail.com>', '\nwhasd\r\n', 'No Attachments', '', 'N', 0, 0, '2018-01-05 04:32:03'),
(25, 1, 'No', '^\"sales@burhanisolutions.com.pk\" <sales@burhanisolutions.com.pk>', '^Tahir Shakir <tahir60652@gmail.com>', '\nJust just\r\nNo\r\n', 'No Attachments', '', 'N', NULL, 0, '2018-01-11 01:08:21'),
(26, 2, 'Teset', '^sales@burhanisolutions.com.pk', '^tahir shakir <tahirshakir606@gmail.com>', '\ntest email\r\n', 'No Attachments', '', 'N', NULL, 0, '2018-01-11 04:28:06'),
(27, 4, 'Attachment Check Again', '^sales@burhanisolutions.com.pk', '^tahir shakir <tahirshakir606@gmail.com>', '\nWhat the thel\r\n', 'No Attachments', '', 'N', NULL, 0, '2018-01-13 07:48:31'),
(28, 5, 'Attahm', '^sales@burhanisolutions.com.pk', '^tahir shakir <tahirshakir606@gmail.com>', '\nforgot to\r\n', 'C:/Users/Tahir/Bits/CRM/Files/HPS.pdf^', '', 'N', NULL, 0, '2018-01-13 07:49:25'),
(29, 9, 'After', '^sales@burhanisolutions.com.pk', '^Tahir Shakir <tahir60652@gmail.com>', '\nI hjave no idea\r\n', 'C:/Users/Tahir/Bits/CRM/Files/HPS.pdf^', '', 'N', NULL, 0, '2018-01-16 11:56:20'),
(30, 15, 'VARnish', '^sales@burhanisolutions.com.pk', '^Tahir Shakir <tahir60652@gmail.com>', '\nI have no idea\r\n', 'C:\\Users\\Tahir\\Bits\\CRM\\Files\\2nd-Hourly-Sun.pdf^', '', 'S', NULL, 0, '2018-01-17 09:52:58'),
(31, 11, 'Testing', '^sales@burhanisolutions.com.pk', '^Tahir Shakir <tahir60652@gmail.com>', '\nTesting email file\r\n', 'C:\\Users\\Tahir\\Bits\\CRM\\Files\\2nd-Hourly-Mon.pdf^', '', 'N', NULL, 0, '2018-01-20 06:04:40');

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
(1, 4);

-- --------------------------------------------------------

--
-- Table structure for table `rights_list`
--

CREATE TABLE `rights_list` (
  `RCODE` int(11) NOT NULL,
  `RNAME` varchar(100) NOT NULL,
  `FREZE` char(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `rights_list`
--

INSERT INTO `rights_list` (`RCODE`, `RNAME`, `FREZE`) VALUES
(1, 'Email Viewer', 'N'),
(2, 'General Settings', 'N');

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
  `ISLOG` char(1) DEFAULT NULL,
  `ISEMAIL` char(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`UCODE`, `FNAME`, `UNAME`, `Email`, `UPASS`, `NOTE`, `URIGHT`, `FREZE`, `SOLV`, `LOCKD`, `ISLOG`, `ISEMAIL`) VALUES
(1, 'Tahir Shakir', 'tahir606', 'tahir60652@gmail.com', 'pin123', 'Super Admin', 'Admin', 'N', 4, 0, '', 'Y'),
(2, 'Shakir Hussain', 'mustoopk', 'mustoopk@gmail.com', 'pin123', 'Admin', 'Admin', 'N', NULL, 0, '', 'N'),
(4, 'Mufaddal ', 'Muffi', 'm@g.com', 'pin123', NULL, 'Not Admin', 'N', NULL, NULL, NULL, 'N');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `email_store`
--
ALTER TABLE `email_store`
  ADD PRIMARY KEY (`EMNO`);

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
