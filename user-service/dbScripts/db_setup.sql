-- Connect to the database
\c user_service_db

-- Create schema (if not exists)
CREATE SCHEMA IF NOT EXISTS user_service;

-- Grant privileges on database & schema
GRANT ALL PRIVILEGES ON DATABASE user_service_db TO user_service_app;
GRANT ALL PRIVILEGES ON SCHEMA user_service TO user_service_app;

-- Set default search_path
SET search_path TO user_service;

ALTER ROLE user_service_app SET search_path TO user_service;
ALTER DATABASE user_service_db SET search_path TO user_service;

-- Grant on existing & future tables and sequences
GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA user_service TO user_service_app;
GRANT ALL PRIVILEGES ON ALL SEQUENCES IN SCHEMA user_service TO user_service_app;

ALTER DEFAULT PRIVILEGES IN SCHEMA user_service
GRANT ALL PRIVILEGES ON TABLES TO user_service_app;

ALTER DEFAULT PRIVILEGES IN SCHEMA user_service
GRANT ALL PRIVILEGES ON SEQUENCES TO user_service_app;

-- ===============================
-- Verification Commands (optional)
-- ===============================
-- \dn         -- list schemas
-- \du         -- list roles
-- \dt         -- list tables