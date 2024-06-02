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
