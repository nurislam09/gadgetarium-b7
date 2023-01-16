insert into roles(id, role_name)
VALUES (1, 'Admin');
insert into roles(id, role_name)
VALUES (2, 'Customer');

insert into users(id, address, email, first_name, image, last_name, password, phone_number, role_id)
VALUES (1,null,'nurislam@gmail.com','Nurislam','jds','Mamytov','$2a$12$j48u7W0s8v0DBjuE7qY/hOJfe/ivaMQXzFmGkrUHwnWL3s9vGnhRq','+996222219743',1);



insert into brands(id, brand_name, image)
VALUES (1, 'Apple', 'https://www.meme-arsenal.com/memes/37c2ffed98992565b0596eca5286c7b6.jpg'),
       (2, 'Samsung',
        'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSjORvDnwy5Mm0oAz8NItWzuql0JTMkjmu-wQ&usqp=CAU'),
       (3, 'Redmi', 'https://www.ixbt.com/short/images/2017/Oct/logo-xiaomi.jpg');

insert into categories(id, category_name)
VALUES (1, 'Смартфоны'),
       (2, 'Ноутбуки и планшеты'),
       (3, 'Смарт-часы и планшеты');

insert into subcategories(id, sub_category_name, category_id)
VALUES (1, 'Apple', 1),
       (2, 'Samsung', 1),
       (3, 'Huawei', 1),
       (4, 'Honor', 1),
       (5, 'Xiaomi', 1);

insert into subcategories(id, sub_category_name, category_id)
VALUES (6, 'Acer', 2),
       (7, 'Asus', 2),
       (8, 'Apple', 2),
       (9, 'DELL', 2),
       (10, 'Digma', 2),
       (11, 'Huawei', 2),
       (12, 'HONOR', 2),
       (13, 'Lenovo', 2),
       (14, 'HP', 2),
       (15, 'MSI', 2),
       (16, 'Xiaomi', 2);

insert into subcategories(id, sub_category_name, category_id)
VALUES (17, 'Смарт-часы Apple watch', 3),
       (18, 'Умные часы для взрослых', 3),
       (19, 'Умные часы для детей', 3),
       (20, 'Фитнес браслет', 3);



insert into products(id, pdf, screen_diagonal, screen_display, appointment_of_laptop, battery_capacity,
                     bracelet_material, case_shape, color, description, gender, guarantee, laptopcpu, memory_of_phone,
                     memory_of_smart_watch, memory_of_tablet, order_count, product_count, product_name, product_price,
                     product_rating, product_status, product_vendor_code, ram_of_laptop, ram_of_phone, ram_of_tablet,
                     screen_resolution_laptop, screen_resolution_tablet, screen_size_laptop, screen_size_tablet,
                     sim_card, video_card_memory, video_review, watch_material, water_proof, wireless_interface,
                     brand_id, category_id, discount_id, sub_category_id)
VALUES ( 1, 'https://softech.kg/image/cache/63ccea961faf68e3d9e4b6f1e4d845c8.jpg', 6.1, 0, null, 3200, null, null
       , E'\\320\\170'::bytea, 'hjdkdksk', null, 12, null, 512, 0, 0, 0, 128, 'Iphone 14 pro max', 90990, 0, null
       , 087456, null, 6
       , 0, null, null, 0.0, 0.0, 'dua sim card', 0, 'https://www.youtube.com/watch?v=FT3ODSg1GFE', null, null, null, 1
       , 1, null, 1);


insert into products(id, pdf, screen_diagonal, screen_display, appointment_of_laptop, battery_capacity,
                     bracelet_material, case_shape, color, description, gender, guarantee, laptopcpu, memory_of_phone,
                     memory_of_smart_watch, memory_of_tablet, order_count, product_count, product_name, product_price,
                     product_rating, product_status, product_vendor_code, ram_of_laptop, ram_of_phone, ram_of_tablet,
                     screen_resolution_laptop, screen_resolution_tablet, screen_size_laptop, screen_size_tablet,
                     sim_card, video_card_memory, video_review, watch_material, water_proof, wireless_interface,
                     brand_id, category_id, discount_id, sub_category_id)
VALUES ( 2, 'https://softech.kg/image/cache/63ccea961faf68e3d9e4b6f1e4d845c8.jpg', 6.1, 0, null, 3200, null, null
       , E'\\320\\170'::bytea, 'hjdkdksk', null, 12, null, 512, 0, 0, 0, 128, 'Iphone 14 pro ', 90990, 0, null, 387456
       , null, 6
       , 0, null, null, 0.0, 0.0, 'dua sim card', 0, 'https://www.youtube.com/watch?v=FT3ODSg1GFE', null, null, null, 1
       , 1, null, 1);


insert into products(id, pdf, screen_diagonal, screen_display, appointment_of_laptop, battery_capacity,
                     bracelet_material, case_shape, color, description, gender, guarantee, laptopcpu, memory_of_phone,
                     memory_of_smart_watch, memory_of_tablet, order_count, product_count, product_name, product_price,
                     product_rating, product_status, product_vendor_code, ram_of_laptop, ram_of_phone, ram_of_tablet,
                     screen_resolution_laptop, screen_resolution_tablet, screen_size_laptop, screen_size_tablet,
                     sim_card, video_card_memory, video_review, watch_material, water_proof, wireless_interface,
                     brand_id, category_id, discount_id, sub_category_id)
VALUES ( 3, 'https://softech.kg/image/cache/63ccea961faf68e3d9e4b6f1e4d845c8.jpg', 6.1, 0, null, 3200, null, null
       , E'\\320\\170'::bytea, 'hjdkdksk', null, 12, null, 512, 0, 0, 0, 128, 'Iphone 14 ', 90990, 0, null, 081056, null
       , 6
       , 0, null, null, 0.0, 0.0, 'dua sim card', 0, 'https://www.youtube.com/watch?v=FT3ODSg1GFE', null, null, null, 1
       , 1, null, 1);


insert into products(id, pdf, screen_diagonal, screen_display, appointment_of_laptop, battery_capacity,
                     bracelet_material, case_shape, color, description, gender, guarantee, laptopcpu, memory_of_phone,
                     memory_of_smart_watch, memory_of_tablet, order_count, product_count, product_name, product_price,
                     product_rating, product_status, product_vendor_code, ram_of_laptop, ram_of_phone, ram_of_tablet,
                     screen_resolution_laptop, screen_resolution_tablet, screen_size_laptop, screen_size_tablet,
                     sim_card, video_card_memory, video_review, watch_material, water_proof, wireless_interface,
                     brand_id, category_id, discount_id, sub_category_id)
VALUES ( 4, 'https://softech.kg/image/cache/63ccea961faf68e3d9e4b6f1e4d845c8.jpg', 6.1, 0, null, 3200, null, null
       , E'\\320\\170'::bytea, 'hjdkdksk', null, 12, null, 512, 0, 0, 0, 128, 'Iphone 13 pro max', 90990, 0, null
       , 087478, null, 6
       , 0, null, null, 0.0, 0.0, 'dua sim card', 0, 'https://www.youtube.com/watch?v=FT3ODSg1GFE', null, null, null, 1
       , 1, null, 1);


insert into products(id, pdf, screen_diagonal, screen_display, appointment_of_laptop, battery_capacity,
                     bracelet_material, case_shape, color, description, gender, guarantee, laptopcpu, memory_of_phone,
                     memory_of_smart_watch, memory_of_tablet, order_count, product_count, product_name, product_price,
                     product_rating, product_status, product_vendor_code, ram_of_laptop, ram_of_phone, ram_of_tablet,
                     screen_resolution_laptop, screen_resolution_tablet, screen_size_laptop, screen_size_tablet,
                     sim_card, video_card_memory, video_review, watch_material, water_proof, wireless_interface,
                     brand_id, category_id, discount_id, sub_category_id)
VALUES ( 5, 'https://softech.kg/image/cache/63ccea961faf68e3d9e4b6f1e4d845c8.jpg', 6.1, 0, null, 3200, null, null
       , E'\\320\\170'::bytea, 'hjdkdksk', null, 12, null, 512, 0, 0, 0, 128, 'Iphone 13 pro ', 90990, 0, null, 045456
       , null, 6
       , 0, null, null, 0.0, 0.0, 'dua sim card', 0, 'https://www.youtube.com/watch?v=FT3ODSg1GFE', null, null, null, 1
       , 1, null, 1);



insert into products(id, pdf, screen_diagonal, screen_display, appointment_of_laptop, battery_capacity,
                     bracelet_material, case_shape, color, description, gender, guarantee, laptopcpu, memory_of_phone,
                     memory_of_smart_watch, memory_of_tablet, order_count, product_count, product_name, product_price,
                     product_rating, product_status, product_vendor_code, ram_of_laptop, ram_of_phone, ram_of_tablet,
                     screen_resolution_laptop, screen_resolution_tablet, screen_size_laptop, screen_size_tablet,
                     sim_card, video_card_memory, video_review, watch_material, water_proof, wireless_interface,
                     brand_id, category_id, discount_id, sub_category_id)
values (6, 'https://softech.kg/image/cache/e5913ac2e5f9d99a5c796d3b43fc7b49.jpg', 0.0, 1.9, null, 308, 'silicone',
        'square', E'\\320\\170'::bytea, 'dskjkdsjkljsd', 1, 12, null
           , 0, 32, 0, 0, 1200, 'Apple Watch series 8', 42000, 0, null, 675409, null, 0, 0, null, null, 0.0, 0.0, null,
        0, 'dgjk', 'Aluminium, ceramic, sapphire', 'yes', 'Bluetooth',
        1, 3, null, 17);

insert into products(id, pdf, screen_diagonal, screen_display, appointment_of_laptop, battery_capacity,
                     bracelet_material, case_shape, color, description, gender, guarantee, laptopcpu, memory_of_phone,
                     memory_of_smart_watch, memory_of_tablet, order_count, product_count, product_name, product_price,
                     product_rating, product_status, product_vendor_code, ram_of_laptop, ram_of_phone, ram_of_tablet,
                     screen_resolution_laptop, screen_resolution_tablet, screen_size_laptop, screen_size_tablet,
                     sim_card, video_card_memory, video_review, watch_material, water_proof, wireless_interface,
                     brand_id, category_id, discount_id, sub_category_id)
values (7, 'https://softech.kg/image/cache/e5913ac2e5f9d99a5c796d3b43fc7b49.jpg', 0.0, 1.9, null, 308, 'silicone',
        'square', E'\\320\\170'::bytea, 'dskjkdsjkljsd', 1, 12, null
           , 0, 32, 0, 0, 1200, 'Apple Watch series 7', 32000, 0, null, 235409, null, 0, 0, null, null, 0.0, 0.0, null,
        0, 'fdjkh', 'Aluminium, ceramic, sapphire', 'yes', 'Bluetooth',
        1, 3, null, 17);


insert into products(id, pdf, screen_diagonal, screen_display, appointment_of_laptop, battery_capacity,
                     bracelet_material, case_shape, color, description, gender, guarantee, laptopcpu, memory_of_phone,
                     memory_of_smart_watch, memory_of_tablet, order_count, product_count, product_name, product_price,
                     product_rating, product_status, product_vendor_code, ram_of_laptop, ram_of_phone, ram_of_tablet,
                     screen_resolution_laptop, screen_resolution_tablet, screen_size_laptop, screen_size_tablet,
                     sim_card, video_card_memory, video_review, watch_material, water_proof, wireless_interface,
                     brand_id, category_id, discount_id, sub_category_id)
values (8,'pdf',13.3,0.0,'Ofice',53,null,null,E'\\320\\170'::bytea,'fddfgd',
                                                  1,12,'Apple M1',0,0,0,0,125,'Apple Mack Book Air M1',80999,0,0,457665,8,0,0,'2560x1600 пикс',null,21.4,0.0,null,
                                                                                  0,'sdfsfd',null,null,null,1,2,null,8);


insert into products(id, pdf, screen_diagonal, screen_display, appointment_of_laptop, battery_capacity,
                     bracelet_material, case_shape, color, description, gender, guarantee, laptopcpu, memory_of_phone,
                     memory_of_smart_watch, memory_of_tablet, order_count, product_count, product_name, product_price,
                     product_rating, product_status, product_vendor_code, ram_of_laptop, ram_of_phone, ram_of_tablet,
                     screen_resolution_laptop, screen_resolution_tablet, screen_size_laptop, screen_size_tablet,
                     sim_card, video_card_memory, video_review, watch_material, water_proof, wireless_interface,
                     brand_id, category_id, discount_id, sub_category_id)
values (9,'pdf',13.3,0.0,'Ofice',53,null,null,E'\\320\\170'::bytea,'fddfgd',
        1,12,'Apple M1',0,0,0,0,125,'Apple Mack Book Air M2',80999,0,0,120665,8,0,0,'2560x1600 пикс',null,21.4,0.0,null,
        0,'sdfsfd',null,null,null,1,2,null,8);


insert into products(id, pdf, screen_diagonal, screen_display, appointment_of_laptop, battery_capacity,
                     bracelet_material, case_shape, color, description, gender, guarantee, laptopcpu, memory_of_phone,
                     memory_of_smart_watch, memory_of_tablet, order_count, product_count, product_name, product_price,
                     product_rating, product_status, product_vendor_code, ram_of_laptop, ram_of_phone, ram_of_tablet,
                     screen_resolution_laptop, screen_resolution_tablet, screen_size_laptop, screen_size_tablet,
                     sim_card, video_card_memory, video_review, watch_material, water_proof, wireless_interface,
                     brand_id, category_id, discount_id, sub_category_id)
values (10,'pdf',13.3,0.0,'Ofice',53,null,null,E'\\320\\170'::bytea,'fddfgd',
        1,12,'Apple M1',0,0,0,0,125,'Apple Mack Book Air M2',80999,0,0,452345,8,0,0,'2560x1600 пикс',null,21.4,0.0,null,
        0,'sdfsfd',null,null,null,1,2,null,8);


insert into products(id, pdf, screen_diagonal, screen_display, appointment_of_laptop, battery_capacity,
                     bracelet_material, case_shape, color, description, gender, guarantee, laptopcpu, memory_of_phone,
                     memory_of_smart_watch, memory_of_tablet, order_count, product_count, product_name, product_price,
                     product_rating, product_status, product_vendor_code, ram_of_laptop, ram_of_phone, ram_of_tablet,
                     screen_resolution_laptop, screen_resolution_tablet, screen_size_laptop, screen_size_tablet,
                     sim_card, video_card_memory, video_review, watch_material, water_proof, wireless_interface,
                     brand_id, category_id, discount_id, sub_category_id)
values (11,'pdf',13.3,0.0,'Ofice',53,null,null,E'\\320\\170'::bytea,'fddfgd',
        1,12,'Apple M1',0,0,0,0,125,'Apple Mack Book Air M2',80999,0,0,452365,8,0,0,'2560x1600 пикс',null,21.4,0.0,null,
        0,'sdfsfd',null,null,null,1,2,null,8);


insert into banners(id) values (1);

insert into banner_images(id, image_url)  values (1,'https://www.budetsdelano.ru/upload/iblock/be0/sale_web.jpg');




