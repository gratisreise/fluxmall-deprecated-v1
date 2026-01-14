-- FluxMall Database Schema
-- MySQL 5.7 / 8.0

-- 데이터베이스 생성 (필요시)
CREATE DATABASE IF NOT EXISTS shopdb DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE shopdb;

-- 1. 회원 테이블 (Members)
CREATE TABLE IF NOT EXISTS members (
    id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '회원 ID',
    email VARCHAR(100) NOT NULL UNIQUE COMMENT '이메일 (로그인 ID)',
    password VARCHAR(255) NOT NULL COMMENT '암호화된 비밀번호',
    name VARCHAR(50) NOT NULL COMMENT '회원 이름',
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '가입일시',
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정일시',
    INDEX idx_email (email)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='회원 정보';

-- 2. 상품 테이블 (Products)
CREATE TABLE IF NOT EXISTS products (
    id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '상품 ID',
    name VARCHAR(200) NOT NULL COMMENT '상품명',
    description TEXT COMMENT '상품 설명',
    price INT NOT NULL COMMENT '상품 가격 (원)',
    stock_quantity INT NOT NULL DEFAULT 0 COMMENT '재고 수량',
    status VARCHAR(20) NOT NULL DEFAULT 'ON_SALE' COMMENT '판매 상태 (ON_SALE, SOLD_OUT)',
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '등록일시',
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정일시',
    INDEX idx_status (status),
    INDEX idx_name (name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='상품 정보';

-- 3. 주문 테이블 (Orders)
CREATE TABLE IF NOT EXISTS orders (
    id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '주문 ID',
    member_id BIGINT NOT NULL COMMENT '회원 ID',
    order_number VARCHAR(50) NOT NULL UNIQUE COMMENT '주문 번호',
    product_id BIGINT NOT NULL COMMENT '상품 ID',
    quantity INT NOT NULL COMMENT '주문 수량',
    total_price INT NOT NULL COMMENT '총 결제 금액',
    status VARCHAR(20) NOT NULL DEFAULT 'PENDING' COMMENT '주문 상태 (PENDING, PAID, CANCELLED)',
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '주문일시',
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정일시',
    FOREIGN KEY (member_id) REFERENCES members(id) ON DELETE CASCADE,
    FOREIGN KEY (product_id) REFERENCES products(id),
    INDEX idx_member_id (member_id),
    INDEX idx_order_number (order_number),
    INDEX idx_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='주문 정보';

-- 샘플 데이터 삽입 (테스트용)
INSERT INTO products (name, description, price, stock_quantity, status) VALUES
('맥북 프로 14인치', 'M3 칩, 18GB RAM, 512GB SSD', 2590000, 10, 'ON_SALE'),
('아이폰 15 Pro', '256GB, 티타늄 블루', 1550000, 20, 'ON_SALE'),
('에어팟 프로 2세대', '액티브 노이즈 캔슬링', 329000, 50, 'ON_SALE'),
('애플워치 시리즈 9', '45mm, 미드나이트 알루미늄', 599000, 15, 'ON_SALE'),
('아이패드 에어 5세대', 'M1 칩, 64GB, 와이파이', 799000, 0, 'SOLD_OUT');
