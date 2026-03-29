-- SQL Script for eCommerce Microservices (XAMPP - Local MySQL)
-- Please run this script manual in phpMyAdmin or MySQL Client

---------------------------------------------------------
-- 1. Create Databases
---------------------------------------------------------
CREATE DATABASE IF NOT EXISTS ecommerce_user CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE DATABASE IF NOT EXISTS ecommerce_product CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE DATABASE IF NOT EXISTS ecommerce_category CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE DATABASE IF NOT EXISTS ecommerce_analytics CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

---------------------------------------------------------
-- 2. User Service Tables
---------------------------------------------------------
USE ecommerce_user;

CREATE TABLE IF NOT EXISTS users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    full_name VARCHAR(100),
    role VARCHAR(20) DEFAULT 'USER',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

---------------------------------------------------------
-- 3. Product Service Tables
---------------------------------------------------------
USE ecommerce_product;

CREATE TABLE IF NOT EXISTS products (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    description TEXT,
    price DECIMAL(10, 2) NOT NULL,
    category_id BIGINT,
    view_count INT DEFAULT 0,
    state VARCHAR(50) DEFAULT 'PENDING',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

---------------------------------------------------------
-- 4. Category Service Tables
---------------------------------------------------------
USE ecommerce_category;

CREATE TABLE IF NOT EXISTS categories (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE,
    description TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

---------------------------------------------------------
-- 5. Analytics Service Tables
---------------------------------------------------------
USE ecommerce_analytics;

CREATE TABLE IF NOT EXISTS analytics_data (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    service_name VARCHAR(100),
    event_type VARCHAR(50),
    event_data TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
