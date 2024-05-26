-- Crear la base de datos SMATDB
CREATE DATABASE IF NOT EXISTS SMATDB;

-- Usar la base de datos SMATDB
USE SMATDB;

-- Crear la tabla stars
CREATE TABLE IF NOT EXISTS stars (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    temperature DOUBLE NULL,
    radius DOUBLE NULL,
    density DOUBLE NULL,
    mass DOUBLE NULL,
    x DOUBLE NULL,
    y DOUBLE NULL
);

-- Crear la tabla solarSystem
CREATE TABLE IF NOT EXISTS solarSystem (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NULL,
    starId INT NOT NULL,
    CONSTRAINT fk_star FOREIGN KEY (starId) REFERENCES stars (id)
);

-- Crear la tabla planet
CREATE TABLE IF NOT EXISTS planet (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    temperature DOUBLE NULL,
    radius DOUBLE NULL,
    density DOUBLE NULL,
    mass DOUBLE NULL,
    x DOUBLE NULL,
    y DOUBLE NULL,
    xSpeed DOUBLE NULL,
    ySpeed DOUBLE NULL,
    solarSystemId INT NOT NULL,
    CONSTRAINT fk_solarSystem_planet FOREIGN KEY (solarSystemId) REFERENCES solarSystem (id)
);

-- Crear la tabla satellite
CREATE TABLE IF NOT EXISTS satellite (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    temperature DOUBLE NULL,
    radius DOUBLE NULL,
    density DOUBLE NULL,
    mass DOUBLE NULL,
    x DOUBLE NULL,
    y DOUBLE NULL,
    xSpeed DOUBLE NULL,
    ySpeed DOUBLE NULL,
    solarSystemId INT NOT NULL,
    CONSTRAINT fk_solarSystem_satellite FOREIGN KEY (solarSystemId) REFERENCES solarSystem (id)
);
