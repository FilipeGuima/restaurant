CREATE TABLE CUSTOMER (
                          id BIGSERIAL PRIMARY KEY,
                          first_name VARCHAR(255) NOT NULL,
                          last_name VARCHAR(255) NOT NULL,
                          email VARCHAR(255) NOT NULL,
                          creation_date DATE NOT NULL
);

CREATE TABLE MENU_ITEM (
                           id BIGSERIAL PRIMARY KEY,
                           name VARCHAR(255) NOT NULL,
                           price DOUBLE PRECISION NOT NULL,
                           allergens VARCHAR(255),
                           calories INT,
                           image_name VARCHAR(255),
                           item_type VARCHAR(50)
);

CREATE TABLE ORDERS (
                        id BIGSERIAL PRIMARY KEY,
                        order_date DATE,
                        status VARCHAR(255),
                        customer_id BIGINT,
                        FOREIGN KEY (customer_id) REFERENCES CUSTOMER(id)
);

CREATE TABLE ORDER_MENU_ITEM (
                                 order_id BIGINT,
                                 menu_item_id BIGINT,
                                 FOREIGN KEY (order_id) REFERENCES ORDERS(id),
                                 FOREIGN KEY (menu_item_id) REFERENCES MENU_ITEM(id)
);