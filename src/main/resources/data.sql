INSERT INTO admins (name, email, password, phone, role, status, created_at, modified_at)
VALUES ('테스트관리자', 'admin@test.com', 'test1234', '010-1111-1111', 'CS_ADMIN', 'ACTIVE', NOW(), NOW());

INSERT INTO customers (name, email, phone, status, created_at, modified_at)
VALUES ('최정이', 'thal070909@gmail.com', '010-2222-2222', 'ACTIVE', NOW(), NOW());

INSERT INTO products (name, category, price, stock, status, admin_id, created_at, modified_at)
VALUES ('냉동피자', '음식', 29900, 10, 'ON_SALE', 1, NOW(), NOW());

INSERT INTO orders (order_number, quantity, total_price, status, customer_id, product_id, admin_id, created_at, modified_at)
VALUES ('ORD-TEST-0001', 2, 59800, 'PREPARING', 1, 1, 1, NOW(), NOW());

INSERT INTO reviews (rating, content, order_id, created_at, modified_at)
VALUES (5, '배송 빠르고 좋아요!', 1, NOW(), NOW()),
       (4, '만족합니다', 1, NOW(), NOW()),
       (2, '생각보다 별로예요', 1, NOW(), NOW());