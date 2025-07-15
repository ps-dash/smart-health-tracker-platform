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
CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(150) NOT NULL UNIQUE,
    password_hash VARCHAR(255),
    mobile VARCHAR(15) NOT NULL,
    date_of_birth DATE,
    height_cm INTEGER,
    weight_kg DOUBLE PRECISION,
    role_id INT NOT NULL REFERENCES roles(id) NOT NULL,
    health_issues TEXT
);

-- Insert sample users
INSERT INTO users (name, email, password_hash, mobile, date_of_birth, height_cm, weight_kg, role_id, health_issues) VALUES
('John Doe', 'john.doe@example.com', 'hashed_password_1', '+1789453116543', '1990-01-15', 175, 85.5, 1, 'Over weight. Bad food habits. Outside food.'),
('Jane Smith', 'jane.smith@example.com', 'hashed_password_2', '+1789453116544','1985-05-20', 160, 78.0, 1, 'Over weight. Under performing thyroid.');

-- ===============================
-- Verification Commands (optional)
-- ===============================
-- SELECT * FROM roles;
-- SELECT * FROM users;