-- Customers
INSERT INTO CUSTOMER (first_name, last_name, email, creation_date) VALUES ('John', 'Doe', 'john@gmail.com', '2025-05-23');
INSERT INTO CUSTOMER (first_name, last_name, email, creation_date) VALUES ('Jane', 'Stewart', 'jane@gmail.com', '2025-06-14');
INSERT INTO CUSTOMER (first_name, last_name, email, creation_date) VALUES ('Jasmine', 'Tadeu', 'jasmine@gmail.com', '2024-07-28');

-- Menu Items
INSERT INTO MENU_ITEM (name, price, allergens, calories, image_name, item_type) VALUES ('chicken_poke_bowl', 13.50, 'SOY,SESAME,GLUTEN', 650, 'chicken.png', 'FOOD');
INSERT INTO MENU_ITEM (name, price, allergens, calories, image_name, item_type) VALUES ('salmon_poke_bowl', 15.00, 'FISH,SOY,SESAME', 700, 'salmon.png', 'FOOD');
INSERT INTO MENU_ITEM (name, price, allergens, calories, image_name, item_type) VALUES ('shrimp_poke_bowl', 14.50, 'CRUSTACEANS,SOY,SESAME', 620, 'shrimp.png', 'FOOD');
INSERT INTO MENU_ITEM (name, price, allergens, calories, image_name, item_type) VALUES ('tofu_poke_bowl', 12.50, 'SOY,SESAME,GLUTEN', 580, 'tofu.png', 'FOOD');
INSERT INTO MENU_ITEM (name, price, allergens, calories, image_name, item_type) VALUES ('tuna_poke_bowl', 15.50, 'FISH,SOY,SESAME', 680, 'tuna.png', 'FOOD');
INSERT INTO MENU_ITEM (name, price, allergens, calories, image_name, item_type) VALUES ('sushi_combo_1', 18.00, 'FISH,CRUSTACEANS,SOY', 800, 'combo1.png', 'FOOD');
INSERT INTO MENU_ITEM (name, price, allergens, calories, image_name, item_type) VALUES ('sushi_combo_2', 22.00, 'FISH,CRUSTACEANS,SOY', 950, 'combo2.png', 'FOOD');
INSERT INTO MENU_ITEM (name, price, allergens, calories, image_name, item_type) VALUES ('caipirinha', 9.00, '', 250, 'caipirinha.png', 'DRINK');
INSERT INTO MENU_ITEM (name, price, allergens, calories, image_name, item_type) VALUES ('espresso_martini', 11.00, '', 280, 'espresso_martini.png', 'DRINK');
INSERT INTO MENU_ITEM (name, price, allergens, calories, image_name, item_type) VALUES ('paloma', 10.00, '', 200, 'paloma.png', 'DRINK');
INSERT INTO MENU_ITEM (name, price, allergens, calories, image_name, item_type) VALUES ('pina_colada', 10.50, 'DAIRY', 350, 'pina.png', 'DRINK');

-- Orders
INSERT INTO ORDERS (order_date, status, customer_id) VALUES (CURRENT_DATE, 'PENDING', 1);
INSERT INTO ORDERS (order_date, status, customer_id) VALUES (CURRENT_DATE - 1, 'DELIVERED', 2);
INSERT INTO ORDERS (order_date, status, customer_id) VALUES (CURRENT_DATE - 2, 'PREPARING', 1);
INSERT INTO ORDERS (order_date, status, customer_id) VALUES (CURRENT_DATE - 1, 'DELIVERED', 3);

-- Order Menu Items
INSERT INTO ORDER_MENU_ITEM (order_id, menu_item_id) VALUES (1, 1);
INSERT INTO ORDER_MENU_ITEM (order_id, menu_item_id) VALUES (1, 10);
INSERT INTO ORDER_MENU_ITEM (order_id, menu_item_id) VALUES (2, 6);
INSERT INTO ORDER_MENU_ITEM (order_id, menu_item_id) VALUES (2, 7);
INSERT INTO ORDER_MENU_ITEM (order_id, menu_item_id) VALUES (3, 5);
INSERT INTO ORDER_MENU_ITEM (order_id, menu_item_id) VALUES (3, 11);
INSERT INTO ORDER_MENU_ITEM (order_id, menu_item_id) VALUES (4, 2);
INSERT INTO ORDER_MENU_ITEM (order_id, menu_item_id) VALUES (4, 9);