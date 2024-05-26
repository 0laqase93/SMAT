-- Usar la base de datos SMATDB
USE SMATDB;

-- Insertar una estrella (el Sol)
INSERT INTO stars (name, temperature, radius, density, mass, x, y)
VALUES ('Sol', 5778, 696340, 1.41, 1.989e30, 0, 0);

-- Insertar un sistema solar (Sistema Solar)
INSERT INTO solarSystem (name, starId)
VALUES ('Sistema Solar', 1);

-- Insertar todos los planetas del Sistema Solar
INSERT INTO planet (name, temperature, radius, density, mass, x, y, xSpeed, ySpeed, solarSystemId)
VALUES
    ('Mercurio', 340, 2439.7, 5.43, 3.3011e23, 57909175, 0, 47.87, 0, 1),
    ('Venus', 737, 6051.8, 5.24, 4.8675e24, 108208930, 0, 35.02, 0, 1),
    ('Tierra', 288, 6371.0, 5.51, 5.97237e24, 149597870, 0, 29.78, 0, 1),
    ('Marte', 210, 3389.5, 3.93, 6.4171e23, 227939200, 0, 24.077, 0, 1),
    ('Júpiter', 165, 69911, 1.33, 1.8982e27, 778299000, 0, 13.07, 0, 1),
    ('Saturno', 134, 58232, 0.69, 5.6834e26, 1429394000, 0, 9.69, 0, 1),
    ('Urano', 76, 25362, 1.27, 8.6810e25, 2870658186, 0, 6.81, 0, 1),
    ('Neptuno', 72, 24622, 1.64, 1.02413e26, 4498396441, 0, 5.43, 0, 1);

-- Insertar algunos satélites naturales (Luna, Fobos, Deimos, etc.)
INSERT INTO satellite (name, temperature, radius, density, mass, x, y, xSpeed, ySpeed, solarSystemId)
VALUES
    ('Luna', 250, 1737.1, 3.34, 7.342e22, 384400, 0, 1.022, 0, 1),
    ('Fobos', -40, 11.267, 1.876, 1.0659e16, 9377, 0, 2.138, 0, 1),
    ('Deimos', -40, 6.2, 1.471, 1.4762e15, 23458, 0, 1.3513, 0, 1),
    ('Io', 110, 1821.6, 3.53, 8.9319e22, 421700, 0, 17.334, 0, 1),
    ('Europa', 102, 1560.8, 3.01, 4.7998e22, 670900, 0, 13.740, 0, 1),
    ('Ganimedes', 110, 2634.1, 1.94, 1.4819e23, 1070400, 0, 10.880, 0, 1),
    ('Calisto', 134, 2410.3, 1.83, 1.0759e23, 1882700, 0, 8.204, 0, 1),
    ('Titán', 94, 2574.7, 1.88, 1.3452e23, 1221870, 0, 5.57, 0, 1),
    ('Encélado', 75, 252.1, 1.61, 1.08022e20, 238020, 0, 12.64, 0, 1),
    ('Miranda', 60, 235.8, 1.2, 6.59e19, 129390, 0, 6.66, 0, 1),
    ('Ariel', 60, 578.9, 1.66, 1.35e21, 191020, 0, 5.51, 0, 1),
    ('Umbriel', 60, 584.7, 1.39, 1.17e21, 266300, 0, 4.67, 0, 1),
    ('Titania', 60, 788.9, 1.71, 3.4e21, 436300, 0, 3.64, 0, 1),
    ('Oberón', 60, 761.4, 1.63, 3.1e21, 583520, 0, 3.15, 0, 1),
    ('Tritón', 38, 1353.4, 2.061, 2.14e22, 354760, 0, 4.39, 0, 1);

-- Insertar una estrella (Alfa Centauri A)
INSERT INTO stars (name, temperature, radius, density, mass, x, y)
VALUES ('Alfa Centauri A', 5790, 696340, 1.518, 1.1e30, 0, 0);

-- Insertar una estrella (TRAPPIST-1)
INSERT INTO stars (name, temperature, radius, density, mass, x, y)
VALUES ('TRAPPIST-1', 2559, 116000, 51.0, 0.089e30, 0, 0);

-- Insertar sistemas solares
INSERT INTO solarSystem (name, starId)
VALUES ('Sistema Alfa Centauri', 2),
       ('Sistema TRAPPIST-1', 3);

-- Insertar exoplanetas de Alfa Centauri A
INSERT INTO planet (name, temperature, radius, density, mass, x, y, xSpeed, ySpeed, solarSystemId)
VALUES
    ('Alfa Centauri Bb', 1200, 7160, 5.6, 1.3e24, 57909175, 0, 47.87, 0, 2);

-- Insertar exoplanetas de TRAPPIST-1
INSERT INTO planet (name, temperature, radius, density, mass, x, y, xSpeed, ySpeed, solarSystemId)
VALUES
    ('TRAPPIST-1b', 400, 1166, 4.5, 1.02e24, 1597857, 0, 80.88, 0, 3),
    ('TRAPPIST-1c', 342, 1160, 4.8, 1.16e24, 2268412, 0, 69.44, 0, 3),
    ('TRAPPIST-1d', 288, 1170, 4.2, 0.388e24, 3326190, 0, 55.37, 0, 3),
    ('TRAPPIST-1e', 251, 1160, 5.6, 0.692e24, 4383968, 0, 47.16, 0, 3),
    ('TRAPPIST-1f', 219, 1130, 4.1, 0.683e24, 5811097, 0, 41.96, 0, 3),
    ('TRAPPIST-1g', 198, 1160, 3.4, 1.148e24, 7451340, 0, 34.67, 0, 3),
    ('TRAPPIST-1h', 169, 880, 3.2, 0.086e24, 9770584, 0, 27.00, 0, 3);

-- Insertar algunos satélites naturales ficticios para Alfa Centauri A y TRAPPIST-1
INSERT INTO satellite (name, temperature, radius, density, mass, x, y, xSpeed, ySpeed, solarSystemId)
VALUES
-- Satélites de Alfa Centauri Bb
('Alfa Centauri Bb I', 500, 1100, 3.5, 8.93e22, 384400, 0, 1.022, 0, 2),
('Alfa Centauri Bb II', 400, 900, 2.8, 4.8e22, 670900, 0, 0.88, 0, 2),

-- Satélites de TRAPPIST-1d
('TRAPPIST-1d I', 100, 500, 2.3, 2.0e21, 33261, 0, 1.5, 0, 3),
('TRAPPIST-1d II', 90, 600, 2.5, 2.5e21, 66523, 0, 1.3, 0, 3);
