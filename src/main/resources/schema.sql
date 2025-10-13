
CREATE TABLE IF NOT EXISTS franchises (
    id   BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE
);


CREATE TABLE IF NOT EXISTS branches (
    id           BIGINT AUTO_INCREMENT PRIMARY KEY,
    name         VARCHAR(255) NOT NULL,
    franchise_id BIGINT NOT NULL,
    CONSTRAINT fk_branch_to_franchise
    FOREIGN KEY (franchise_id) REFERENCES franchises (id)
    ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS products (
    id         BIGINT AUTO_INCREMENT PRIMARY KEY,
    name       VARCHAR(255) NOT NULL,
    stock      INT NOT NULL,
    branch_id  BIGINT NOT NULL,
    CONSTRAINT fk_product_to_branch
    FOREIGN KEY (branch_id) REFERENCES branches (id)
    ON DELETE CASCADE
);