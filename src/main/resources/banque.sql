-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1:3308
-- Généré le : ven. 06 jan. 2023 à 08:48
-- Version du serveur : 5.7.36
-- Version de PHP : 7.4.26

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données : `banque`
--

-- --------------------------------------------------------

--
-- Structure de la table `releve`
--

DROP TABLE IF EXISTS `releve`;
CREATE TABLE IF NOT EXISTS `releve` (
  `releve_id` int(10) NOT NULL AUTO_INCREMENT,
  `releve_date` varchar(255) NOT NULL,
  `releve_libelle` varchar(255) NOT NULL,
  `releve_debit` int(10) NOT NULL,
  `releve_credit` int(10) NOT NULL,
  `user_id` int(10) NOT NULL,
  PRIMARY KEY (`releve_id`),
  KEY `user_id` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

--
-- Déchargement des données de la table `releve`
--

INSERT INTO `releve` (`releve_id`, `releve_date`, `releve_libelle`, `releve_debit`, `releve_credit`, `user_id`) VALUES
(1, 'd', 'd', 1, 1, 1),
(6, 'd', 'd', 1, 1, 1),
(7, 'd', 'd', 1, 1, 1);

-- --------------------------------------------------------

--
-- Structure de la table `users`
--

DROP TABLE IF EXISTS `users`;
CREATE TABLE IF NOT EXISTS `users` (
  `user_id` int(10) NOT NULL AUTO_INCREMENT,
  `user_email` varchar(255) NOT NULL,
  `user_mdp` varchar(255) NOT NULL,
  `user_prenom` varchar(255) DEFAULT NULL,
  `user_role` varchar(255) NOT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

--
-- Déchargement des données de la table `users`
--

INSERT INTO `users` (`user_id`, `user_email`, `user_mdp`, `user_prenom`, `user_role`) VALUES
(1, 'testMail', 'testMdp', 'François', 'admin'),
(2, 'testMail2', 'testMdp2', 'Alex', 'user'),
(3, 'testMail3', 'testMdp3', 'Autre', 'user');

--
-- Contraintes pour les tables déchargées
--

--
-- Contraintes pour la table `releve`
--
ALTER TABLE `releve`
  ADD CONSTRAINT `releve_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
