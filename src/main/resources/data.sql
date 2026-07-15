-- INSERT INTO admins (name, email, password, phone, role, status, created_at, modified_at)
-- VALUES ('테스트관리자', 'admin@test.com', 'test1234', '010-1111-1111', 'CS_ADMIN', 'ACTIVE', NOW(), NOW());
--
-- INSERT INTO customers (name, email, phone, status, created_at, modified_at)
-- VALUES ('최정이', 'thal070909@gmail.com', '010-2222-2222', 'ACTIVE', NOW(), NOW());
--
-- INSERT INTO products (name, category, price, stock, status, admin_id, created_at, modified_at)
-- VALUES ('냉동피자', '음식', 29900, 10, 'ON_SALE', 1, NOW(), NOW());
--
-- INSERT INTO orders (order_number, quantity, total_price, status, customer_id, product_id, admin_id, created_at, modified_at)
-- VALUES ('ORD-TEST-0001', 2, 59800, 'PREPARING', 1, 1, 1, NOW(), NOW());
--
-- INSERT INTO reviews (rating, content, order_id, created_at, modified_at)
-- VALUES (5, '배송 빠르고 좋아요!', 1, NOW(), NOW()),
--        (4, '만족합니다', 1, NOW(), NOW()),
--        (2, '생각보다 별로예요', 1, NOW(), NOW());

-- =========================
-- Admins
-- =========================
INSERT INTO admins (name, email, password, phone, role, status, approved_at, rejected_at, reject_reason, created_at, modified_at)
VALUES
-- 승인 완료 계정 1
('김슈퍼', 'aaa@aaa.com', '$2a$04$555HEpsPl8wgvFL87HqcQOkWxxJJRbC93mRq3uDKoXV02dDGtwVIO',
 '010-1111-1111', 'SUPER_ADMIN', 'ACTIVE',
 NOW(), NULL, NULL,
 NOW(), NOW()),
-- 2
('이운영', 'operation@test.com', '$2a$04$555HEpsPl8wgvFL87HqcQOkWxxJJRbC93mRq3uDKoXV02dDGtwVIO',
 '010-2222-2222', 'OPERATION_ADMIN', 'ACTIVE',
 NOW(), NULL, NULL,
 NOW(), NOW()),
--3
('장상담', 'csadmin@test.com', '$2a$04$555HEpsPl8wgvFL87HqcQOkWxxJJRbC93mRq3uDKoXV02dDGtwVIO',
 '010-3333-3333', 'CS_ADMIN', 'ACTIVE',
 NOW(), NULL, NULL,
 NOW(), NOW()),
--4
-- 승인 완료 후 비활성화
('비활성', 'admin3@test.com', '$2a$04$555HEpsPl8wgvFL87HqcQOkWxxJJRbC93mRq3uDKoXV02dDGtwVIO',
 '010-3233-3333', 'CS_ADMIN', 'INACTIVE',
 NOW(), NULL, NULL,
 NOW(), NOW()),
--5
-- 승인 완료 후 정지
('정지', 'admin4@test.com', '$2a$04$555HEpsPl8wgvFL87HqcQOkWxxJJRbC93mRq3uDKoXV02dDGtwVIO',
 '010-4444-4444', 'SUPER_ADMIN', 'SUSPENDED',
 NOW(), NULL, NULL,
 NOW(), NOW()),
--6
-- 승인 대기
('승인대기', 'admin2@test.com', '$2a$04$555HEpsPl8wgvFL87HqcQOkWxxJJRbC93mRq3uDKoXV02dDGtwVIO',
 '010-2232-2222', 'OPERATION_ADMIN', 'PENDING',
 NULL, NULL, NULL,
 NOW(), NOW()),
--7
-- 가입 거부
('거부', 'admin5@test.com', '$2a$04$555HEpsPl8wgvFL87HqcQOkWxxJJRbC93mRq3uDKoXV02dDGtwVIO',
 '010-5555-5555', 'OPERATION_ADMIN', 'REJECTED',
 NULL, NOW(), '관리자 승인 거부',
 NOW(), NOW());
-- =========================
-- Customers
-- =========================
INSERT INTO customers (name, email, phone, status, created_at, modified_at)
VALUES
-- 활성 고객 (6명: 리스트 조회·페이징·검색 테스트용)
    ('최정이', 'wjddl@test.com', '010-5555-1111', 'ACTIVE', NOW(), NOW()),
    ('임경식', 'rudtlr@test.com', '010-5555-2222', 'ACTIVE', NOW(), NOW()),
    ('신형탁', 'anseksthr@test.com', '010-5555-3333', 'ACTIVE', NOW(), NOW()),
    ('최정윤', 'wjddbs@test.com', '010-5555-4444', 'ACTIVE', NOW(), NOW()),
    ('장준혁', 'wnsgur@test.com', '010-5555-5555', 'ACTIVE', NOW(), NOW()),
    ('김혁중', 'gurwnd@test.com', '010-5555-6666', 'ACTIVE', NOW(), NOW()),
-- 비활성 고객 (2명: 상태 필터 테스트용)
    ('비활성', 'inactive@test.com', '010-6666-1111', 'INACTIVE', NOW(), NOW()),
    ('박휴면', 'dormant@test.com', '010-6666-2222', 'INACTIVE', NOW(), NOW()),
-- 정지 고객 (2명)
    ('김정지', 'kim01@test.com', '010-4444-1111', 'SUSPENDED', NOW(), NOW()),
    ('김블랙', 'blacklist@test.com', '010-4444-2222', 'SUSPENDED', NOW(), NOW());

-- =========================
-- Products
-- =========================
INSERT INTO products (name, category, price, stock, status, admin_id, created_at, modified_at)
VALUES ('감자 판매중', '음식', 29900, 10, 'ON_SALE', 1, NOW(), NOW()),
       (' 고구마품절', '음식', 12900, 30, 'SOLD_OUT', 1, NOW(), NOW()),
       ('김 단종', '음료', 2800, 100, 'DISCONTINUED', 2, NOW(), NOW()),
       ('게이밍 마우스', '전자기기', 59000, 15, 'ON_SALE', 2, NOW(), NOW()),
       ('무선 키보드', '전자기기', 89000, 8, 'SOLD_OUT', 3, NOW(), NOW()),
       ('텀블러', '생활용품', 19000, 25, 'ON_SALE', 3, NOW(), NOW());

INSERT INTO orders (order_number, quantity, total_price, status, customer_id, product_id, admin_id, created_at, modified_at)
VALUES
    ('ORD-TEST-0001', 3, 28002, 'PREPARING', 1, 1, 1, NOW(), NOW()),
    ('ORD-TEST-0002', 4, 198020, 'PREPARING', 2, 2, 2, NOW(), NOW()),
    ('ORD-TEST-0003', 5, 69800, 'PREPARING', 3, 3, 3, NOW(), NOW()),
    ('ORD-TEST-0004', 6, 99800, 'PREPARING', 4, 4, 4, NOW(), NOW());

INSERT INTO reviews (rating, content, order_id, created_at, modified_at)
VALUES (5, '배송 빠르고 좋아요!', 1, NOW(), NOW()),
       (4, '만족합니다', 2, NOW(), NOW()),
       (2, '생각보다 별로예요', 3, NOW(), NOW());