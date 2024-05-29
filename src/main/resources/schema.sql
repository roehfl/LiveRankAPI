CREATE TABLE stock_detail (
                       id BIGINT AUTO_INCREMENT PRIMARY KEY,
                       price INT NOT NULL,
                       previousPrice INT NOT NULL,
                       hitCount INT NOT NULL,
                       volume INT NOT NULL,
                       itemId BIGINT NOT NULL
);

CREATE TABLE stock_item (
                      id BIGINT PRIMARY KEY,
                      code VARCHAR(255) NOT NULL,
                      name VARCHAR(255) NOT NULL
);

ALTER TABLE stock_detail
    ADD CONSTRAINT fk_item_id FOREIGN KEY (itemId) REFERENCES stock_item(id);