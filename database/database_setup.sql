-- ====================================================================
-- Career Compass - Database Setup Script
-- Project: MCA Object-Oriented Programming Assignment
-- Database: MySQL 8.0+
-- ====================================================================

-- 1. Create the database schema if it does not already exist
CREATE DATABASE IF NOT EXISTS career_compass
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

USE career_compass;

-- 2. Optional: Create dedicated application user and grant permissions
-- You can either use root or this dedicated user for the application
CREATE USER IF NOT EXISTS 'career_user'@'localhost' IDENTIFIED BY 'career_pass123';
CREATE USER IF NOT EXISTS 'career_user'@'%' IDENTIFIED BY 'career_pass123';

GRANT ALL PRIVILEGES ON career_compass.* TO 'career_user'@'localhost';
GRANT ALL PRIVILEGES ON career_compass.* TO 'career_user'@'%';

FLUSH PRIVILEGES;

-- Verification message
SELECT 'Database career_compass created and configured successfully!' AS Status;
