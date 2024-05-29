CREATE TABLE stock_detail (
                       id BIGINT AUTO_INCREMENT PRIMARY KEY,
                       price INT NOT NULL,
                       previous_price INT NOT NULL,
                       hit_count INT NOT NULL,
                       volume INT NOT NULL,
                       item_id BIGINT NOT NULL
);

CREATE TABLE stock_item (
                      id BIGINT AUTO_INCREMENT PRIMARY KEY,
                      code VARCHAR(255) NOT NULL,
                      name VARCHAR(255) NOT NULL
);

ALTER TABLE stock_detail
    ADD CONSTRAINT fk_item_id FOREIGN KEY (item_id) REFERENCES stock_item(id);