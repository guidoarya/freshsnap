-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: May 25, 2022 at 02:47 PM
-- Server version: 10.4.22-MariaDB
-- PHP Version: 8.1.2

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `freshsnap`
--

-- --------------------------------------------------------

--
-- Table structure for table `item`
--

CREATE TABLE `item` (
  `id` int(11) NOT NULL,
  `name` varchar(56) NOT NULL,
  `type` varchar(36) NOT NULL,
  `image` varchar(255) NOT NULL,
  `howtokeep` varchar(1000) NOT NULL,
  `createdAt` varchar(256) NOT NULL,
  `updatedAt` varchar(256) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `item`
--

INSERT INTO `item` (`id`, `name`, `type`, `image`, `howtokeep`, `createdAt`, `updatedAt`) VALUES
(33, 'Apple', 'Fruit', 'uploads/1653469746404.png', 'Apples will last longer in the refrigerator, For long-term storage, wrap apples separately ', '2022-05-25 09:09:06', '2022-05-25 09:09:06'),
(34, 'Broccoli', 'Vegetable', 'uploads/1653469869686.jpg', 'Make a broccoli bouquet, Wrap with a damp paper towel, Freeze the broccoli', '2022-05-25 09:11:09', '2022-05-25 09:11:09');

-- --------------------------------------------------------

--
-- Table structure for table `reference-table`
--

CREATE TABLE `reference-table` (
  `id` int(11) NOT NULL,
  `reference_name` varchar(126) NOT NULL,
  `name` varchar(126) NOT NULL,
  `image` varchar(255) NOT NULL,
  `createdAt` varchar(256) NOT NULL,
  `updatedAt` varchar(256) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `reference-table`
--

INSERT INTO `reference-table` (`id`, `reference_name`, `name`, `image`, `createdAt`, `updatedAt`) VALUES
(3, 'Apple Juice', 'Apple', 'uploads/1653469931699.png', '2022-05-25 09:12:11', '2022-05-25 09:12:11'),
(4, 'Fried Apples', 'Apple', 'uploads/1653469985737.jpg', '2022-05-25 09:13:06', '2022-05-25 09:13:06'),
(5, 'Apple Pudding', 'Apple', 'uploads/1653470029109.jpeg', '2022-05-25 09:13:49', '2022-05-25 09:13:49'),
(6, 'Grilled Broccoli', 'Broccoli', 'uploads/1653470097991.jpg', '2022-05-25 09:14:58', '2022-05-25 09:14:58'),
(7, 'Broccoli Juice', 'Broccoli', 'uploads/1653470172694.jpg', '2022-05-25 09:16:12', '2022-05-25 09:16:12'),
(8, 'Broccoli Cheese Soup', 'Broccoli', 'uploads/1653470311766.jpg', '2022-05-25 09:18:32', '2022-05-25 09:18:32');

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `id` int(11) NOT NULL,
  `name` varchar(56) NOT NULL,
  `email` varchar(56) NOT NULL,
  `password` varchar(56) NOT NULL,
  `image` varchar(255) NOT NULL,
  `refresh_token` text NOT NULL,
  `createdAt` varchar(255) NOT NULL,
  `updatedAt` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`id`, `name`, `email`, `password`, `image`, `refresh_token`, `createdAt`, `updatedAt`) VALUES
(2, 'Guido', 'guido@gmail.com', '$2b$10$.2qEWECqTGhoWVpcUwEOkufFBG6/c0732XFSFK3A77mZki9DL', 'uploads/1652800369886.jpeg', '', '2022-05-17 15:11:50', '2022-05-17 15:12:49'),
(3, 'Aryasatya', 'guidoarya@gmail.com', '$2b$10$uD7rpp1yeALvIpuaJ0LJl.1Ef81ItnkpkKxHVQLsxvOE5ubye', '', '', '2022-05-17 15:12:29', '2022-05-17 15:12:29');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `item`
--
ALTER TABLE `item`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `reference-table`
--
ALTER TABLE `reference-table`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `item`
--
ALTER TABLE `item`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=35;

--
-- AUTO_INCREMENT for table `reference-table`
--
ALTER TABLE `reference-table`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT for table `user`
--
ALTER TABLE `user`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
