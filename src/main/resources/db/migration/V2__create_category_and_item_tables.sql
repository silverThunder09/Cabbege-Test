CREATE TABLE category (
    id BIGINT NOT NULL AUTO_INCREMENT,
    parent_id BIGINT,
    name VARCHAR(100) NOT NULL,
    sort_order INT NOT NULL,
    is_active BOOLEAN NOT NULL,
    created_at DATETIME NOT NULL,
    updated_at DATETIME NOT NULL,
    CONSTRAINT pk_category PRIMARY KEY (id),
    CONSTRAINT uk_category_name UNIQUE (name),
    CONSTRAINT fk_category_parent FOREIGN KEY (parent_id) REFERENCES category (id)
);

CREATE TABLE item (
    id BIGINT NOT NULL AUTO_INCREMENT,
    seller_id BIGINT NOT NULL,
    category_id BIGINT NOT NULL,
    trade_type VARCHAR(30) NOT NULL,
    title VARCHAR(200) NOT NULL,
    description TEXT NOT NULL,
    initial_price BIGINT NOT NULL,
    condition_type VARCHAR(30) NOT NULL,
    trade_status VARCHAR(30) NOT NULL,
    view_count BIGINT NOT NULL,
    like_count BIGINT NOT NULL,
    inquiry_count BIGINT NOT NULL,
    is_draft BOOLEAN NOT NULL,
    created_at DATETIME NOT NULL,
    updated_at DATETIME NOT NULL,
    is_deleted BOOLEAN NOT NULL,
    CONSTRAINT pk_item PRIMARY KEY (id),
    CONSTRAINT fk_item_seller FOREIGN KEY (seller_id) REFERENCES client (id),
    CONSTRAINT fk_item_category FOREIGN KEY (category_id) REFERENCES category (id)
);
