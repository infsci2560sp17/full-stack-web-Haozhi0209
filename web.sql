-- phpMyAdmin SQL Dump
-- version 4.6.4
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Apr 28, 2017 at 03:42 PM
-- Server version: 5.7.14
-- PHP Version: 7.0.10

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `web`
--

-- --------------------------------------------------------

--
-- Table structure for table `t_comment`
--

CREATE TABLE `t_comment` (
  `cid` int(10) NOT NULL,
  `uid` int(10) NOT NULL,
  `to_uid` int(10) NOT NULL,
  `tid` int(10) NOT NULL,
  `content` text CHARACTER SET utf8mb4 NOT NULL,
  `device` varchar(255) DEFAULT 'pc',
  `create_time` int(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `t_openid`
--

CREATE TABLE `t_openid` (
  `id` int(11) UNSIGNED NOT NULL,
  `type` varchar(50) DEFAULT NULL,
  `open_id` int(11) DEFAULT NULL,
  `uid` int(11) DEFAULT NULL,
  `create_time` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `t_openid`
--

INSERT INTO `t_openid` (`id`, `type`, `open_id`, `uid`, `create_time`) VALUES
(1, 'github', 9302093, 14, 1460809033),
(5, 'github', 3849072, 1, 1460885906);

-- --------------------------------------------------------

--
-- Table structure for table `t_topic`
--

CREATE TABLE `t_topic` (
  `tid` int(10) NOT NULL,
  `uid` int(10) NOT NULL,
  `title` varchar(50) CHARACTER SET utf8mb4 DEFAULT '',
  `content` text CHARACTER SET utf8mb4,
  `is_top` tinyint(2) DEFAULT '0',
  `is_essence` tinyint(2) DEFAULT '0',
  `create_time` int(10) NOT NULL,
  `update_time` int(10) NOT NULL,
  `status` tinyint(2) NOT NULL DEFAULT '1'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `t_topiccount`
--

CREATE TABLE `t_topiccount` (
  `tid` int(10) NOT NULL,
  `views` int(10) DEFAULT '0',
  `loves` int(10) DEFAULT '0',
  `favorites` int(10) DEFAULT '0',
  `comments` int(10) DEFAULT '0',
  `sinks` int(10) DEFAULT '0',
  `create_time` int(10) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `t_user`
--

CREATE TABLE `t_user` (
  `uid` int(10) NOT NULL,
  `login_name` varchar(50) NOT NULL,
  `pass_word` varchar(32) DEFAULT NULL,
  `avatar` varchar(100) DEFAULT NULL,
  `email` varchar(100) NOT NULL,
  `create_time` int(10) NOT NULL,
  `update_time` int(10) NOT NULL,
  `role_id` tinyint(2) DEFAULT '5',
  `status` tinyint(2) NOT NULL DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `t_user`
--

INSERT INTO `t_user` (`uid`, `login_name`, `pass_word`, `avatar`, `email`, `create_time`, `update_time`, `role_id`, `status`) VALUES
(1, 'biezhi', '26b2010cd2028d742b283da54a304584', 'avatar/biezhi/PR6L/7294.jpg', 'biezhi.me@gmail.com', 1410944818, 1410944818, 1, 1),
(2, 'jacks', 'ba152812e0625a7b3f06d874c76a4227', 'avatar/default/2.png', 'jacks@java-china.org', 1410944818, 1410944818, 5, 1),
(3, 'kiyoumi', '95d2cd9213ac2c8f477e3fa704099ff5', 'avatar/default/1.png', 'kiyoumi@java-china.org', 1410944818, 1410944818, 5, 1),
(4, 'lala', '32068ed247a0fa371d162185d3f51861', 'avatar/default/3.png', 'lala@java-china.org', 1410944818, 1410944818, 5, 1),
(5, 'doubi', '7b503366dbaeb4bbdb542c49c5ece63d', 'avatar/default/4.png', 'doubi@java-china.org', 1410944818, 1410944818, 5, 1),
(6, 'im_wangjue_NO1', '7ed715e7fe818860a212646c1da830f2', 'avatar/default/2.png', '351711778@qq.com', 1460639261, 1460639261, 5, 1),
(7, 'lichee', '3808575efd4d0ae4cb43f535e2c14cb1', 'avatar/lichee/kiKO/6394.jpg', 'z@lichee.me', 1460639488, 1460639488, 5, 1),
(8, 'XingFly', '56bdfa42ac2c35fbe992f5c6190be0d6', 'avatar/default/1.png', '549052145@qq.com', 1460650525, 1460650525, 5, 1),
(9, 'kexun', '2da754a873d09ef5bd0086fd507ad9f8', 'avatar/default/1.png', 'qianchj@163.com', 1460681929, 1460681929, 5, 1),
(10, 'uilzzw', 'b375ab93242ef366ac1b2d4b8d05cffd', 'avatar/default/1.png', 'uilzzw@gmail.com', 1460682181, 1460682181, 5, 1),
(11, 'hezhezhiyu', 'e5abfe520f6582676da2ab34790981a2', 'avatar/default/3.png', '84785027@qq.com', 1460728178, 1460728178, 5, 1),
(12, 'ssnoodles', 'e3c4e1401b3162c47ec7a42162a46602', 'avatar/default/0.png', 'ssnoodles0226@gmail.com', 1460769332, 1460769332, 5, 1),
(13, 'lvdong5830', '1072b5ba593f578e30d018895a03dc73', 'avatar/lvdong5830/RxI2/8751.png', '425280895@qq.com', 1460786636, 1460786636, 5, 1),
(14, 'Shildon', '9d53aa31eabb5d3897ed3e514c4ed976', 'avatar/default/4.png', 'shildondu@gmail.com', 1460809033, 1460809033, 5, 1),
(15, 'DamagedBoy', 'fe858b0d10667237df5b53ebbe91c300', 'avatar/default/1.png', 'lwt_workmail@163.com', 1460960047, 1460960047, 5, 1),
(16, 'wuyun', 'ca4dc20ccc1497c834edd76e211e3b34', 'avatar/wuyun/cyf3/4366.jpeg', 'tao0329@126.com', 1460960985, 1460960985, 5, 1),
(17, 'Zyt1026', '293d3d3a475c455c243aa349a0ec61bf', 'avatar/Zyt1026/3YRH/7129.jpg', '9059322@qq.com', 1460992863, 1460992863, 5, 1),
(18, 'yoryor', '3d609e6f239ca2d84651e37d34f59e77', 'avatar/default/1.png', 'yoryor@lovexwf.me', 1461044100, 1461044100, 5, 1),
(19, 'rex_8090', 'cecacbd07e7fb7a92233825c2d7f62eb', 'avatar/default/0.png', 'rex_8090@sina.com', 1461054229, 1461054229, 5, 1),
(20, 'jack', '06feedfb754b5d0e3d778481839f8215', 'avatar/default/0.png', '244258241@qq.com', 1461059206, 1461059206, 5, 1),
(21, 'Levan', 'b1ef1d78c557b4f99bf3e8ff90a91925', 'avatar/default/4.png', '1094955064@qq.com', 1461070401, 1461070401, 5, 1),
(22, 'winry', '3fbc1e7a9c2eb8c5b3eeb9ebd7964d18', 'avatar/default/3.png', 'fwrq41251@gmail.com', 1461120033, 1461120033, 5, 1),
(23, 'dingguitao', '6626c6d4d66c3e5c5282c2769766cf89', 'avatar/default/4.png', 'haitao19892006@126.com', 1461147164, 1461147164, 5, 1),
(24, 'mask_9523', '174b4e336c7bccae4a3cdc48a235cf97', 'avatar/default/1.png', '18510855440@sina.cn', 1461287493, 1461287493, 5, 1),
(25, 'Jack', 'ec1dddc5f0231aa52b6980e56810526f', 'avatar/default/1.png', '244258241@qq.com', 1461293422, 1461293422, 5, 1);

-- --------------------------------------------------------

--
-- Table structure for table `t_userinfo`
--

CREATE TABLE `t_userinfo` (
  `uid` int(10) NOT NULL,
  `nick_name` varchar(30) DEFAULT NULL,
  `jobs` varchar(100) DEFAULT NULL,
  `web_site` varchar(255) DEFAULT NULL,
  `github` varchar(20) DEFAULT NULL,
  `weibo` varchar(50) DEFAULT NULL,
  `location` varchar(100) DEFAULT NULL,
  `signature` varchar(255) CHARACTER SET utf8mb4 DEFAULT NULL,
  `instructions` text CHARACTER SET utf8mb4
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `t_userinfo`
--

INSERT INTO `t_userinfo` (`uid`, `nick_name`, `jobs`, `web_site`, `github`, `weibo`, `location`, `signature`, `instructions`) VALUES
(4, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(5, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(6, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(7, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(8, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(9, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(10, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(11, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(12, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(13, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(14, NULL, NULL, 'https://github.com/XiaodongDu', 'XiaodongDu', NULL, NULL, NULL, NULL),
(15, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(16, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(17, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(18, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(19, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(20, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(21, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(22, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(23, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(24, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(25, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `t_userlog`
--

CREATE TABLE `t_userlog` (
  `id` int(10) NOT NULL,
  `uid` int(10) NOT NULL,
  `action` varchar(100) NOT NULL,
  `content` text,
  `ip_addr` varchar(50) DEFAULT NULL,
  `create_time` int(10) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `t_comment`
--
ALTER TABLE `t_comment`
  ADD PRIMARY KEY (`cid`);

--
-- Indexes for table `t_openid`
--
ALTER TABLE `t_openid`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `u_openid` (`open_id`,`type`,`uid`);

--
-- Indexes for table `t_topic`
--
ALTER TABLE `t_topic`
  ADD PRIMARY KEY (`tid`);

--
-- Indexes for table `t_topiccount`
--
ALTER TABLE `t_topiccount`
  ADD PRIMARY KEY (`tid`);

--
-- Indexes for table `t_user`
--
ALTER TABLE `t_user`
  ADD PRIMARY KEY (`uid`);

--
-- Indexes for table `t_userinfo`
--
ALTER TABLE `t_userinfo`
  ADD PRIMARY KEY (`uid`);

--
-- Indexes for table `t_userlog`
--
ALTER TABLE `t_userlog`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `t_comment`
--
ALTER TABLE `t_comment`
  MODIFY `cid` int(10) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `t_openid`
--
ALTER TABLE `t_openid`
  MODIFY `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;
--
-- AUTO_INCREMENT for table `t_topic`
--
ALTER TABLE `t_topic`
  MODIFY `tid` int(10) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `t_user`
--
ALTER TABLE `t_user`
  MODIFY `uid` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=26;
--
-- AUTO_INCREMENT for table `t_userlog`
--
ALTER TABLE `t_userlog`
  MODIFY `id` int(10) NOT NULL AUTO_INCREMENT;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
