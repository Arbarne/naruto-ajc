-- phpMyAdmin SQL Dump
-- version 5.2.3
-- https://www.phpmyadmin.net/
--
-- Hôte : localhost:8889
-- Généré le : ven. 10 juil. 2026 à 11:04
-- Version du serveur : 8.0.44
-- Version de PHP : 8.3.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données : `naruto_project`
--

-- --------------------------------------------------------

--
-- Structure de la table `equipe`
--

CREATE TABLE `equipe` (
  `id` int NOT NULL,
  `nom` varchar(50) NOT NULL,
  `leader` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `equipe`
--

INSERT INTO `equipe` (`id`, `nom`, `leader`) VALUES
(1, 'Equipe 7', 2),
(2, 'Equipe 8', 5),
(3, 'Equipe Gai', 8);

-- --------------------------------------------------------

--
-- Structure de la table `mission`
--

CREATE TABLE `mission` (
  `id` int NOT NULL,
  `date_debut` date NOT NULL,
  `date_fin` date NOT NULL,
  `description` varchar(255) NOT NULL,
  `gain_exp` int NOT NULL,
  `nom` varchar(35) NOT NULL,
  `rang` enum('A','B','C','D','S') NOT NULL,
  `recompense` int NOT NULL,
  `statut` enum('Annulee','Disponible','Echouee','EnCours','Terminee') NOT NULL,
  `equipe` int DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `mission`
--

INSERT INTO `mission` (`id`, `date_debut`, `date_fin`, `description`, `gain_exp`, `nom`, `rang`, `recompense`, `statut`, `equipe`) VALUES
(16, '2026-07-10', '2026-07-10', 'Retrouver Tora, le chat du Daimyo.', 150, 'Mission du chat', 'D', 500, 'Terminee', 1),
(17, '2026-07-11', '2026-07-11', 'Escorter un marchand jusqu\'au village voisin.', 350, 'Mission Escorte', 'C', 1200, 'EnCours', 2),
(18, '2026-07-12', '2026-07-13', 'Éliminer une bande de brigands.', 900, 'Bandits de la forêt', 'B', 5000, 'Annulee', 3),
(19, '2026-07-15', '2026-07-17', 'Mission d\'infiltration derrière les lignes ennemies.', 1800, 'Mission Espionnage', 'A', 12000, 'Echouee', 3),
(20, '2026-07-20', '2026-07-25', 'Neutraliser un membre de l\'Akatsuki.', 5000, 'Mission S', 'S', 50000, 'Disponible', NULL);

-- --------------------------------------------------------

--
-- Structure de la table `utilisateur`
--

CREATE TABLE `utilisateur` (
  `user_type` varchar(31) NOT NULL,
  `id` int NOT NULL,
  `argent` int NOT NULL,
  `chakra_actuel` int NOT NULL,
  `chakra_max` int NOT NULL,
  `etat` enum('Decede','Disponible','EnMission') DEFAULT NULL,
  `exp_actuel` int NOT NULL,
  `genre` enum('Femme','Homme') NOT NULL,
  `login` varchar(50) NOT NULL,
  `nb_echecs` int NOT NULL,
  `nb_reussites` int NOT NULL,
  `niveau` int NOT NULL,
  `nom` varchar(35) DEFAULT NULL,
  `password` varchar(255) NOT NULL,
  `prenom` varchar(35) NOT NULL,
  `pv_actuel` int NOT NULL,
  `pv_max` int NOT NULL,
  `rang` enum('Chunin','Genin','Jonin','NinjaLegendaire') DEFAULT NULL,
  `specialite` enum('Aucune','Genjutsu','Medecine','Ninjutsu','Taijutsu') NOT NULL,
  `equipe` int DEFAULT NULL
) ;

--
-- Déchargement des données de la table `utilisateur`
--

INSERT INTO `utilisateur` (`user_type`, `id`, `argent`, `chakra_actuel`, `chakra_max`, `etat`, `exp_actuel`, `genre`, `login`, `nb_echecs`, `nb_reussites`, `niveau`, `nom`, `password`, `prenom`, `pv_actuel`, `pv_max`, `rang`, `specialite`, `equipe`) VALUES
('Ninja', 1, 1200, 100, 100, 'Disponible', 4500, 'Homme', 'naruto', 3, 42, 15, 'Uzumaki', '$2a$12$FS8DZ/zHoN1ZaFakuupcguAtJ939Iny8lXGcNRchKbwOIxmo0VR32', 'Naruto', 100, 100, 'Chunin', 'Ninjutsu', 1),
('Leader', 2, 950, 95, 95, 'Disponible', 4300, 'Homme', 'sasuke', 2, 40, 15, 'Uchiha', '$2a$10$dummy', 'Sasuke', 95, 95, 'Chunin', 'Ninjutsu', NULL),
('Ninja', 3, 850, 90, 90, 'Disponible', 4000, 'Femme', 'sakura', 4, 35, 14, 'Haruno', '$2a$10$dummy', 'Sakura', 90, 90, 'Chunin', 'Medecine', 1),
('Ninja', 4, 2000, 180, 180, 'Disponible', 12000, 'Homme', 'kakashi', 1, 210, 35, 'Hatake', '$2a$10$dummy', 'Kakashi', 180, 180, 'Jonin', 'Ninjutsu', 2),
('Leader', 5, 800, 80, 80, 'Disponible', 3000, 'Homme', 'kiba', 5, 28, 12, 'Inuzuka', '$2a$10$dummy', 'Kiba', 80, 80, 'Genin', 'Taijutsu', NULL),
('Ninja', 6, 780, 82, 82, 'Disponible', 3100, 'Femme', 'hinata', 4, 30, 12, 'Hyuga', '$2a$10$dummy', 'Hinata', 82, 82, 'Genin', 'Taijutsu', 2),
('Leader', 7, 760, 78, 78, 'Disponible', 2950, 'Homme', 'shino', 3, 31, 12, 'Aburame', '$2a$10$dummy', 'Shino', 78, 78, 'Genin', 'Ninjutsu', NULL),
('Leader', 8, 1800, 170, 170, 'Disponible', 11000, 'Homme', 'gai', 2, 205, 34, 'Gai', '$2a$10$dummy', 'Maito', 170, 170, 'Jonin', 'Taijutsu', NULL),
('Ninja', 9, 900, 88, 88, 'Disponible', 3400, 'Homme', 'lee', 3, 33, 13, 'Lee', '$2a$10$dummy', 'Rock', 88, 88, 'Genin', 'Taijutsu', 3),
('Ninja', 10, 850, 84, 84, 'Disponible', 3250, 'Femme', 'tenten', 4, 32, 13, NULL, '$2a$10$dummy', 'Tenten', 84, 84, 'Genin', 'Genjutsu', 3);

--
-- Index pour les tables déchargées
--

--
-- Index pour la table `equipe`
--
ALTER TABLE `equipe`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UKtmpaatkvnqxaa1xpqfah1xhg9` (`nom`),
  ADD UNIQUE KEY `UKao7o0i8m3ggwh95o5lnq6p3ox` (`leader`);

--
-- Index pour la table `mission`
--
ALTER TABLE `mission`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UKkuut39qdah2254xrmqdr0a2w0` (`rang`),
  ADD KEY `FKmuk9e5tse3dvy42bag94pw73k` (`equipe`);

--
-- Index pour la table `utilisateur`
--
ALTER TABLE `utilisateur`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK4r067iyre95uw16fv9iv8j5rw` (`equipe`);

--
-- AUTO_INCREMENT pour les tables déchargées
--

--
-- AUTO_INCREMENT pour la table `equipe`
--
ALTER TABLE `equipe`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT pour la table `mission`
--
ALTER TABLE `mission`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=21;

--
-- AUTO_INCREMENT pour la table `utilisateur`
--
ALTER TABLE `utilisateur`
  MODIFY `id` int NOT NULL AUTO_INCREMENT;

--
-- Contraintes pour les tables déchargées
--

--
-- Contraintes pour la table `equipe`
--
ALTER TABLE `equipe`
  ADD CONSTRAINT `FKi3rwex9ltdwd69d96iyvois9m` FOREIGN KEY (`leader`) REFERENCES `utilisateur` (`id`);

--
-- Contraintes pour la table `mission`
--
ALTER TABLE `mission`
  ADD CONSTRAINT `FKmuk9e5tse3dvy42bag94pw73k` FOREIGN KEY (`equipe`) REFERENCES `equipe` (`id`);

--
-- Contraintes pour la table `utilisateur`
--
ALTER TABLE `utilisateur`
  ADD CONSTRAINT `FK4r067iyre95uw16fv9iv8j5rw` FOREIGN KEY (`equipe`) REFERENCES `equipe` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
