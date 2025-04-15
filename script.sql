CREATE TABLE `users` (
  `id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `email` VARCHAR(255) NOT NULL UNIQUE,
  `password` VARCHAR(255) NOT NULL,
  `firstname` VARCHAR(255) NOT NULL,
  `lastname` VARCHAR(255) NOT NULL,
  `birthdate` DATE NOT NULL,
  `address` LONGTEXT NOT NULL,
  `support` BOOLEAN DEFAULT FALSE
);

CREATE TABLE `conversations` (
  `id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `customer` INT NOT NULL,
  `support` INT NOT NULL,
  FOREIGN KEY (`customer`) REFERENCES `users` (`id`),
  FOREIGN KEY (`support`) REFERENCES `users` (`id`)
);

CREATE TABLE `messages` (
  `id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `conversation` INT NOT NULL,
  `author` INT NOT NULL,
  `content` LONGTEXT NOT NULL,
  `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (`conversation`) REFERENCES `conversations` (`id`) ON DELETE CASCADE,
  FOREIGN KEY (`author`) REFERENCES `users` (`id`)
);

INSERT INTO users (email, password, firstname, lastname, birthdate, address, support) VALUES ('john@test.fr', '$2y$10$grj.Wa0o3T/YVtHtn0Qqd.E46cypTUWn2TobiasSKlxXoTPbY8YHO', 'John', 'Doe', '1990-01-01', 'Rue de condolier, 75001 Paris', FALSE);
INSERT INTO users (email, password, firstname, lastname, birthdate, address, support) VALUES ('support@test.fr', '$2y$10$grj.Wa0o3T/YVtHtn0Qqd.E46cypTUWn2TobiasSKlxXoTPbY8YHO', 'Lara', 'Support', '1999-01-01', 'Rue du piquet, 75012 Paris', TRUE);