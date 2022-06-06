-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jun 06, 2022 at 07:48 PM
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
-- Table structure for table `history`
--

CREATE TABLE `history` (
  `id` int(11) NOT NULL,
  `user_name` varchar(56) NOT NULL,
  `item_name` varchar(56) NOT NULL,
  `location` varchar(56) NOT NULL,
  `image` varchar(256) NOT NULL,
  `updatedAt` varchar(256) NOT NULL,
  `createdAt` varchar(256) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `history`
--

INSERT INTO `history` (`id`, `user_name`, `item_name`, `location`, `image`, `updatedAt`, `createdAt`) VALUES
(3, 'Guido Aryasatya', 'Apple', 'bekasi', 'https://storage.googleapis.com/freshsnap-image/freshsnap-1654537534351kisspng-broccoli-food-rapini-spring-greens-mustards-brocoli-5b4c00dd25c699.8035569615317076131548.jpg', '2022-06-06 17:45:34', '2022-06-06 17:45:34');

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
(43, 'Apple', 'Fruit', 'https://storage.googleapis.com/freshsnap-image/freshsnap-1654506846449apple.png', 'Keep them cool, Place them in the crisper drawer in a plastic bag with holes in it or cover the apples with a damp paper towel', '2022-06-06 09:14:06', '2022-06-06 09:14:06'),
(44, 'Banana', 'Fruit', 'https://storage.googleapis.com/freshsnap-image/freshsnap-1654506939987banana.png', 'Hang them away from other produce and exert pressure, Keep them cool and protected from the light, Pop them into the fridge', '2022-06-06 09:15:39', '2022-06-06 09:15:39'),
(45, 'Cucumber', 'Vegetable', 'https://storage.googleapis.com/freshsnap-image/freshsnap-1654507145642cucumber.png', 'Wash the cucumbers and wrap the cucumbers in paper towels, Place the cucumbers in a storage container, Keep cucumbers toward the front of the refrigerator, Keep cucumbers away from other fruits and vegetables because certain fruits and veggies give off ethylene gas', '2022-06-06 09:19:05', '2022-06-06 09:19:05'),
(46, 'Guava', 'Fruit', 'https://storage.googleapis.com/freshsnap-image/freshsnap-1654507324868guava.png', 'Begin by rinsing your guava under the cold tap, Then pat it dry with a paper towel and place it on a chopping board, Peel your guava using a sharp knife and then cut it into smaller pieces, Place your guava pieces into a freezer-safe bag and press out as much air as possible', '2022-06-06 09:22:04', '2022-06-06 09:22:04'),
(47, 'Lime', 'Fruit', 'https://storage.googleapis.com/freshsnap-image/freshsnap-1654507396477lime.png', 'reezing slice or whole limes into Refrigerator, Keeping them in an airtight bag is going to slow down the process of the moisture leaving the lime', '2022-06-06 09:23:16', '2022-06-06 09:23:16'),
(48, 'Okra', 'Vegetable', 'https://storage.googleapis.com/freshsnap-image/freshsnap-1654507463524okra.png', 'Place fresh okra in a paper bag or wrap it in a paper towel and place the paper towel in a perforated plastic bag, Store okra in the refrigerator for 2 to 3 days or, For longer storage okra may be frozen', '2022-06-06 09:24:23', '2022-06-06 09:24:23'),
(49, 'Orange', 'Fruit', 'https://storage.googleapis.com/freshsnap-image/freshsnap-1654507549103mandarin_orange.png', 'Unpeeled mandarin oranges stored at room temperature in a cool, Store oranges loosely in a paper bag in the refrigerator,  You can store segmented mandarin pieces with the skin intact in an airtight container, Peel and segment the mandarin oranges and place them in a single layer in a freezer bag to prevent the segments from freezing together in one big mass', '2022-06-06 09:25:49', '2022-06-06 09:25:49'),
(50, 'Pomegranate', 'Fruit', 'https://storage.googleapis.com/freshsnap-image/freshsnap-1654507650320pomegranate.png', 'You can store your pomegranates by spreading them out on a counter or shelf, away from direct sunlight, Make sure they are in a cool, You can store them in the fridge which will also keep them fresher for longer.', '2022-06-06 09:27:30', '2022-06-06 09:27:30'),
(51, 'Potato', 'Vegetable', 'https://storage.googleapis.com/freshsnap-image/freshsnap-1654507785936potato.png', 'Don’t wash before storing they will last longer if you keep them dry,  If you carried potatoes home from the store in a plastic bag it’s best to remove them for longer-term storage, Avoid storing potatoes near onions or etc because produce ethylene gas', '2022-06-06 09:29:45', '2022-06-06 09:29:45'),
(52, 'Tomato', 'Fruit', 'https://storage.googleapis.com/freshsnap-image/freshsnap-1654507885325tomato.png', 'They need to stay at room temperature, ideally in a single layer out of direct sunlight, And most importantly for keeping them fresher longer, Store them stem side down while they finish ripening', '2022-06-06 09:31:25', '2022-06-06 09:31:25');

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
(12, 'Apple Juice', 'Apple', 'https://storage.googleapis.com/freshsnap-image/freshsnapRef-1654536451207apple_juice.png', '2022-06-06 17:27:31', '2022-06-06 17:27:31'),
(13, 'Apple Pie', 'Apple', 'https://storage.googleapis.com/freshsnap-image/freshsnapRef-1654536474508apple_pie.png', '2022-06-06 17:27:54', '2022-06-06 17:27:54'),
(14, 'Apple Jam', 'Apple', 'https://storage.googleapis.com/freshsnap-image/freshsnapRef-1654536497453apple_jam.png', '2022-06-06 17:28:17', '2022-06-06 17:28:17'),
(15, 'Banana Flour', 'Banana', 'https://storage.googleapis.com/freshsnap-image/freshsnapRef-1654536525321banana_flour.png', '2022-06-06 17:28:45', '2022-06-06 17:28:45'),
(16, 'Banana Pure', 'Banana', 'https://storage.googleapis.com/freshsnap-image/freshsnapRef-1654536538449banana_puree.png', '2022-06-06 17:28:58', '2022-06-06 17:28:58'),
(17, 'Banana Pastillas', 'Banana', 'https://storage.googleapis.com/freshsnap-image/freshsnapRef-1654536551172banana_pastillas.png', '2022-06-06 17:29:11', '2022-06-06 17:29:11'),
(18, 'Cucumber Juice', 'Cucumber', 'https://storage.googleapis.com/freshsnap-image/freshsnapRef-1654536580196juice cucumber.png', '2022-06-06 17:29:40', '2022-06-06 17:29:40'),
(19, 'Sauted Cucumber', 'Cucumber', 'https://storage.googleapis.com/freshsnap-image/freshsnapRef-1654536599844sauted cucumber.png', '2022-06-06 17:29:59', '2022-06-06 17:29:59'),
(20, 'Guava Juice', 'Guava', 'https://storage.googleapis.com/freshsnap-image/freshsnapRef-1654536623020guava juice.png', '2022-06-06 17:30:23', '2022-06-06 17:30:23'),
(21, 'Guava Jam', 'Guava', 'https://storage.googleapis.com/freshsnap-image/freshsnapRef-1654536633519guava_jam.png', '2022-06-06 17:30:33', '2022-06-06 17:30:33'),
(22, 'Lime Juice', 'Lime', 'https://storage.googleapis.com/freshsnap-image/freshsnapRef-1654536656696lime_juice.png', '2022-06-06 17:30:56', '2022-06-06 17:30:56'),
(23, 'Mandarin Orange Snack', 'Orange', 'https://storage.googleapis.com/freshsnap-image/freshsnapRef-1654536683168mandarin_orange_snack.png', '2022-06-06 17:31:23', '2022-06-06 17:31:23'),
(24, 'Okra Sauteed', 'Okra', 'https://storage.googleapis.com/freshsnap-image/freshsnapRef-1654536707810okra_sauteed.png', '2022-06-06 17:31:47', '2022-06-06 17:31:47'),
(25, 'Pomegranate Juice', 'Pomegranate', 'https://storage.googleapis.com/freshsnap-image/freshsnapRef-1654536756271pomegranate_juice.png', '2022-06-06 17:32:36', '2022-06-06 17:32:36'),
(26, 'French Fries', 'Potato', 'https://storage.googleapis.com/freshsnap-image/freshsnapRef-1654536788440french_fries.png', '2022-06-06 17:33:08', '2022-06-06 17:33:08'),
(27, 'Mashed Potato', 'Potato', 'https://storage.googleapis.com/freshsnap-image/freshsnapRef-1654536800573french_fries.png', '2022-06-06 17:33:20', '2022-06-06 17:33:20'),
(28, 'Tomato Ketchup', 'Tomato', 'https://storage.googleapis.com/freshsnap-image/freshsnapRef-1654536829053tomato_ketchup.png', '2022-06-06 17:33:49', '2022-06-06 17:33:49'),
(29, 'Tomato Pasta', 'Tomato', 'https://storage.googleapis.com/freshsnap-image/freshsnapRef-1654536838778tomato_pasta.png', '2022-06-06 17:33:58', '2022-06-06 17:33:58');

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `id` int(11) NOT NULL,
  `name` varchar(56) NOT NULL,
  `email` varchar(56) NOT NULL,
  `password` varchar(255) NOT NULL,
  `image` varchar(255) NOT NULL,
  `createdAt` varchar(255) NOT NULL,
  `updatedAt` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`id`, `name`, `email`, `password`, `image`, `createdAt`, `updatedAt`) VALUES
(12, 'admin', 'admin@bangkit.academy', '$2b$10$XfctktpkTVLFqA0DCRMyLuBdKCb.5B9MenRv/zEy7czQp00i/Qwf2', '', '2022-06-06 13:22:05', '2022-06-06 13:22:05');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `history`
--
ALTER TABLE `history`
  ADD PRIMARY KEY (`id`);

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
-- AUTO_INCREMENT for table `history`
--
ALTER TABLE `history`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `item`
--
ALTER TABLE `item`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=53;

--
-- AUTO_INCREMENT for table `reference-table`
--
ALTER TABLE `reference-table`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=30;

--
-- AUTO_INCREMENT for table `user`
--
ALTER TABLE `user`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
