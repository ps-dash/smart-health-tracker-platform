-- ===============================
-- Tables & Seed Data
-- ===============================

-- Make sure we’re in correct schema
SET search_path TO user_service;

-- Drop tables if they already exist (optional, dev only)
DROP TABLE IF EXISTS users CASCADE;
DROP TABLE IF EXISTS roles CASCADE;

-- Create roles table
CREATE TABLE roles (
    id SERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE
);

-- Insert roles
INSERT INTO roles (name) VALUES
('CLIENT'),
('HABIT_COACH'),
('DIETITIAN'),
('DOCTOR'),
('ADMIN');

-- Create users table
-- TODO: Implement a column login_type.
-- TODO: If login_type is OTP or OAUTH2 then only password_hash column can be null.
CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(150) UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    mobile VARCHAR(15) UNIQUE,
    date_of_birth DATE,
    height_cm INTEGER,
    weight_kg DOUBLE PRECISION,
    role_id INT NOT NULL REFERENCES roles(id) NOT NULL,
    health_issues TEXT,
    CONSTRAINT chk_email_or_mobile CHECK (
            email IS NOT NULL OR mobile IS NOT NULL
        )
);

-- Insert sample users
--John: +1789453116543/hashed_password_1
--Jane: +1789453116544/hashed_password_2
--Parth: +919876543210/hashed_password_3
--Sana: +919876543211/hashed_password_4
INSERT INTO users (name, email, password_hash, mobile, date_of_birth, height_cm, weight_kg, role_id, health_issues) VALUES
('John Doe', 'john.doe@example.com', '$2a$12$xf61Np3rBfDc3RSHlaFslOQ8QJLgiqHeOErLqI4MQiFPgdhrg38o2', '+1789453116543', '1990-01-15', 175, 85.5, 1, 'Over weight. Bad food habits. Outside food.'),
('Jane Smith', 'jane.smith@example.com', '$2a$12$jpJwNa14pl.mKU6bq0Q0HOkapI/xa2NHZMaYWxjvT0BWojLFctCEq', '+1789453116544','1985-05-20', 160, 78.0, 1, 'Over weight. Under performing thyroid.'),
('Parth', 'parth@example.com', '$2a$12$Hx3eCDZ3sW2dASSkQOd40OPFsNUCkZEw42TIOL7aNfigSYQn.nUWC', '+919876543210','1985-05-20', 174, 67.0, 2, ''),
('Sana', 'sana@rusticwisdom.in', '$2a$12$vncW2E9/ZWq7trFlnth/i.qjepcy6ZEY8nvBbCHgdHD3mBAB39Cya', '+919876543211','1985-05-20', 160, 60.0, 3, '');

-- ===============================
-- Verification Commands (optional)
-- ===============================
-- SELECT * FROM roles;
-- SELECT * FROM users;