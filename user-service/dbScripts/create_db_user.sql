-- Drop if needed
DROP DATABASE IF EXISTS user_service_db;
DROP USER IF EXISTS user_service_app;

-- Create user
CREATE USER user_service_app WITH PASSWORD 'postgres@123';

-- Create DB
CREATE DATABASE user_service_db OWNER user_service_app;

-- Grant privileges on DB
GRANT ALL PRIVILEGES ON DATABASE user_service_db TO user_service_app;