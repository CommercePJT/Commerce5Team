INSERT INTO admins (name, email, password, phone, role, status, created_at, modified_at)
VALUES ('테스트관리자', 'admin@test.com', 'test1234', '010-1111-1111', 'CS_ADMIN', 'ACTIVE', NOW(), NOW());

INSERT INTO customers (name, email, phone, status, created_at, modified_at)
VALUES ('최정이', 'thal070909@gmail.com', '010-2222-2222', 'ACTIVE', NOW(), NOW());

INSERT INTO products (name, category, price, stock, status, admin_id, created_at, modified_at)
VALUES ('냉동피자', '음식', 29900, 10, 'ON_SALE', 1, NOW(), NOW());