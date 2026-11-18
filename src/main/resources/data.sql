INSERT INTO products (title, description, price, seller_id, created_at, updated_at)
VALUES ('아이폰 15', '상태 좋은 아이폰입니다.', 800000, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO products (title, description, price, seller_id, created_at, updated_at)
VALUES ('기계식 키보드', '청축 기계식 키보드입니다.', 50000, 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO products (title, description, price, seller_id, created_at, updated_at)
VALUES ('무선 이어폰', '사용감 적은 무선 이어폰입니다.', 70000, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO trades (product_id, buyer_id, seller_id, price, status, created_at, updated_at)
VALUES (1, 2, 1, 800000, 'PENDING', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO trades (product_id, buyer_id, seller_id, price, status, created_at, updated_at)
VALUES (2, 1, 2, 50000, 'COMPLETED', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
