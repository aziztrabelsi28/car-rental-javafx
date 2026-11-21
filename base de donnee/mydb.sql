-- phpMyAdmin SQL Dump
-- version 5.2.3
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1:3306
-- Generation Time: Nov 21, 2026 at 01:57 AM
-- Server version: 8.4.7
-- PHP Version: 8.3.28

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `mydb`
--

-- --------------------------------------------------------

--
-- Table structure for table `admin`
--

DROP TABLE IF EXISTS `admin`;
CREATE TABLE IF NOT EXISTS `admin` (
  `idadmin` int NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`idadmin`),
  UNIQUE KEY `idadmin_UNIQUE` (`idadmin`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `client`
--

DROP TABLE IF EXISTS `client`;
CREATE TABLE IF NOT EXISTS `client` (
  `idClient` int NOT NULL AUTO_INCREMENT,
  `nom` varchar(45) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `prenom` varchar(45) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`idClient`),
  UNIQUE KEY `idclient_UNIQUE` (`idClient`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `client`
--

INSERT INTO `client` (`idClient`, `nom`, `prenom`) VALUES
(2, 'trabelsi', 'aziz');

-- --------------------------------------------------------

--
-- Table structure for table `demandelocation`
--

DROP TABLE IF EXISTS `demandelocation`;
CREATE TABLE IF NOT EXISTS `demandelocation` (
  `idClient` int NOT NULL,
  `idVoiture` int NOT NULL,
  `dateDebutLocation` date NOT NULL,
  `dateFinLocation` date NOT NULL,
  `idDemande` int NOT NULL AUTO_INCREMENT,
  `typeLocation` enum('week-end','premium','standard') COLLATE utf8mb4_unicode_ci NOT NULL,
  `prix` double NOT NULL,
  `typePaiement` enum('espèce','carte') COLLATE utf8mb4_unicode_ci NOT NULL,
  `accepted` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`idDemande`),
  UNIQUE KEY `idDemande_UNIQUE` (`idDemande`),
  KEY `idClient_idx` (`idClient`),
  KEY `idVoiture_idx` (`idVoiture`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `demandelocation`
--

INSERT INTO `demandelocation` (`idClient`, `idVoiture`, `dateDebutLocation`, `dateFinLocation`, `idDemande`, `typeLocation`, `prix`, `typePaiement`, `accepted`) VALUES
(2, 1, '2026-11-15', '2026-11-17', 1, 'standard', 378, 'carte', 1);

-- --------------------------------------------------------

--
-- Table structure for table `facture`
--

DROP TABLE IF EXISTS `facture`;
CREATE TABLE IF NOT EXISTS `facture` (
  `idFacture` int NOT NULL AUTO_INCREMENT,
  `idLocation` int NOT NULL,
  `prixTotal` double NOT NULL,
  `typePaiement` enum('carte','espèces') COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`idFacture`),
  UNIQUE KEY `idFacture_UNIQUE` (`idFacture`),
  KEY `idLocation_idx` (`idLocation`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `facture`
--

INSERT INTO `facture` (`idFacture`, `idLocation`, `prixTotal`, `typePaiement`) VALUES
(1, 1, 378, 'carte');

-- --------------------------------------------------------

--
-- Table structure for table `location`
--

DROP TABLE IF EXISTS `location`;
CREATE TABLE IF NOT EXISTS `location` (
  `idLocation` int NOT NULL AUTO_INCREMENT,
  `idDemande` int NOT NULL,
  PRIMARY KEY (`idLocation`),
  UNIQUE KEY `idLocation_UNIQUE` (`idLocation`),
  KEY `FK5_idx` (`idDemande`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `location`
--

INSERT INTO `location` (`idLocation`, `idDemande`) VALUES
(1, 1);

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
CREATE TABLE IF NOT EXISTS `user` (
  `idUser` int NOT NULL AUTO_INCREMENT,
  `username` varchar(45) COLLATE utf8mb4_unicode_ci NOT NULL,
  `password` varchar(45) COLLATE utf8mb4_unicode_ci NOT NULL,
  `role` enum('client','admin') COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT 'client',
  PRIMARY KEY (`idUser`),
  UNIQUE KEY `idUtilisateur_UNIQUE` (`idUser`),
  UNIQUE KEY `username_UNIQUE` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`idUser`, `username`, `password`, `role`) VALUES
(1, 'admin', 'admin', 'admin'),
(2, 'aziz', 'aziz', 'client');

-- --------------------------------------------------------

--
-- Table structure for table `voiture`
--

DROP TABLE IF EXISTS `voiture`;
CREATE TABLE IF NOT EXISTS `voiture` (
  `idVoiture` int NOT NULL AUTO_INCREMENT,
  `marque` varchar(45) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `isDispo` tinyint NOT NULL,
  `typeVoiture` enum('suv','sedan') COLLATE utf8mb4_unicode_ci NOT NULL,
  `prixJour` double NOT NULL,
  PRIMARY KEY (`idVoiture`),
  UNIQUE KEY `idVoiture_UNIQUE` (`idVoiture`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `voiture`
--

INSERT INTO `voiture` (`idVoiture`, `marque`, `isDispo`, `typeVoiture`, `prixJour`) VALUES
(1, 'volkswagen', 0, 'sedan', 120);

--
-- Constraints for dumped tables
--

--
-- Constraints for table `admin`
--
ALTER TABLE `admin`
  ADD CONSTRAINT `FK2` FOREIGN KEY (`idadmin`) REFERENCES `user` (`idUser`);

--
-- Constraints for table `client`
--
ALTER TABLE `client`
  ADD CONSTRAINT `FK1` FOREIGN KEY (`idClient`) REFERENCES `user` (`idUser`);

--
-- Constraints for table `demandelocation`
--
ALTER TABLE `demandelocation`
  ADD CONSTRAINT `FK3` FOREIGN KEY (`idClient`) REFERENCES `client` (`idClient`),
  ADD CONSTRAINT `FK4` FOREIGN KEY (`idVoiture`) REFERENCES `voiture` (`idVoiture`);

--
-- Constraints for table `facture`
--
ALTER TABLE `facture`
  ADD CONSTRAINT `FK6` FOREIGN KEY (`idLocation`) REFERENCES `location` (`idLocation`);

--
-- Constraints for table `location`
--
ALTER TABLE `location`
  ADD CONSTRAINT `FK5` FOREIGN KEY (`idDemande`) REFERENCES `demandelocation` (`idDemande`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
