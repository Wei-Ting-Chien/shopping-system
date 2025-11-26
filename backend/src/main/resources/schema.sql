-- 開發階段：重置資料表
DROP TABLE IF EXISTS order_item;
DROP TABLE IF EXISTS orders;
DROP TABLE IF EXISTS product;

-- 1. Product Table
CREATE TABLE product (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    category VARCHAR(100),
    price INT NOT NULL,
    stock INT NOT NULL DEFAULT 0,
    status BOOLEAN DEFAULT TRUE,
    description TEXT,
    image_url VARCHAR(500),
    create_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 2. Order Table
CREATE TABLE orders (
    id SERIAL PRIMARY KEY,
    customer_name VARCHAR(100),
    customer_phone VARCHAR(50),
    customer_location VARCHAR(255),
    total_amount INT,
    status VARCHAR(50),
    create_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 3. Order_item Table
CREATE TABLE order_item (
    id SERIAL PRIMARY KEY,
    order_id INT REFERENCES orders(id),
    product_id INT REFERENCES product(id),
    quantity INT NOT NULL, -- 這裡是「購買數量」
    price INT NOT NULL
);

-- 4. Fake Data
INSERT INTO product (name, category, price, stock, status, description) VALUES 
('iPhone 15', '手機', 30000, 10, true, '蘋果最新手機'), -- 庫存 10
('Sony 耳機', '影音', 8000, 5, true, '降噪耳機'),    -- 庫存 5
('過季外套', '衣物', 500, 0, false, '已經下架');      -- 庫存 0