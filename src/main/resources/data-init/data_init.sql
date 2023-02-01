insert into roles(id, role_name)
VALUES (1, 'Admin');
insert into roles(id, role_name)
VALUES (2, 'Customer');

insert into users(id, address, email, first_name, image, last_name, password, phone_number, role_id)
VALUES (1, null, 'nurislam@gmail.com', 'Nurislam', 'jds', 'Mamytov',
        '$2a$12$j48u7W0s8v0DBjuE7qY/hOJfe/ivaMQXzFmGkrUHwnWL3s9vGnhRq', '+996222219743', 1);

insert into users(id, address, email, first_name, image, last_name, password, phone_number, role_id)
VALUES (2, null, 'syimyk@gmail.com', 'Syimyk', 'jds', 'Ravshanbekov',
        '$2a$12$j48u7W0s8v0DBjuE7qY/hOJfe/ivaMQXzFmGkrUHwnWL3s9vGnhRq', '+996222219743', 2);

insert into brands(id, brand_name, image)
VALUES (1, 'Apple', 'https://www.meme-arsenal.com/memes/37c2ffed98992565b0596eca5286c7b6.jpg'),
       (2, 'Samsung',
        'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSjORvDnwy5Mm0oAz8NItWzuql0JTMkjmu-wQ&usqp=CAU'),
       (3, 'Redmi', 'https://www.ixbt.com/short/images/2017/Oct/logo-xiaomi.jpg');

insert into categories(id, category_name)
VALUES (1, 'Смартфоны'),
       (2, 'Ноутбуки и планшеты'),
       (3, 'Смарт-часы и браслеты');

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

insert into discounts(id, amount_of_discount, discount_end_date, discount_start_date)
VALUES (1, 5, '2023-01-20', '2023-02-01');



insert into products(id, pdf, screen_diagonal, screen_display, size_of_watch, appointment_of_laptop, battery_capacity,
                     bracelet_material, case_shape, color, description, gender, guarantee, laptopcpu, memory_of_phone,
                     memory_of_smart_watch, memory_of_tablet, order_count, product_count, product_name, product_price,
                     product_rating, product_status, product_vendor_code, ram_of_laptop, ram_of_phone, ram_of_tablet,
                     screen_resolution_laptop, screen_resolution_tablet, screen_size_laptop, screen_size_tablet,
                     sim_card, video_card_memory, video_review, watch_material, water_proof, wireless_interface,
                     brand_id, category_id, discount_id, sub_category_id, create_at)
VALUES ( 1, 'https://softech.kg/image/cache/63ccea961faf68e3d9e4b6f1e4d845c8.jpg', 6.1, 0, 0.0, null, 3200, null, null
       , 'Red', 'hjdkdksk', null, 12, null, 512, 0, 0, 0, 128, 'Iphone 14 pro max', 90990, 0, 0
       , 087456, null, 6
       , 0, null, null, 0.0, 0.0, 'dua sim card', 0, 'https://www.youtube.com/watch?v=FT3ODSg1GFE', null, null, null, 1
       , 1, null, 1, '2011-04-17 18:20:03');


insert into products(id, pdf, screen_diagonal, screen_display, size_of_watch, appointment_of_laptop, battery_capacity,
                     bracelet_material, case_shape, color, description, gender, guarantee, laptopcpu, memory_of_phone,
                     memory_of_smart_watch, memory_of_tablet, order_count, product_count, product_name, product_price,
                     product_rating, product_status, product_vendor_code, ram_of_laptop, ram_of_phone, ram_of_tablet,
                     screen_resolution_laptop, screen_resolution_tablet, screen_size_laptop, screen_size_tablet,
                     sim_card, video_card_memory, video_review, watch_material, water_proof, wireless_interface,
                     brand_id, category_id, discount_id, sub_category_id, create_at)
VALUES ( 2, 'https://softech.kg/image/cache/63ccea961faf68e3d9e4b6f1e4d845c8.jpg', 6.1, 0, 0.0, null, 3200, null, null
       , 'Green', 'hjdkdksk', null, 12, null, 512, 0, 0, 0, 128, 'Iphone 14 pro ', 90990, 0, 1, 387456
       , null, 6
       , 0, null, null, 0.0, 0.0, 'dua sim card', 0, 'https://www.youtube.com/watch?v=FT3ODSg1GFE', null, null, null, 1
       , 1, 1, 1, '2011-04-17 18:20:03');


insert into products(id, pdf, screen_diagonal, screen_display, size_of_watch, appointment_of_laptop, battery_capacity,
                     bracelet_material, case_shape, color, description, gender, guarantee, laptopcpu, memory_of_phone,
                     memory_of_smart_watch, memory_of_tablet, order_count, product_count, product_name, product_price,
                     product_rating, product_status, product_vendor_code, ram_of_laptop, ram_of_phone, ram_of_tablet,
                     screen_resolution_laptop, screen_resolution_tablet, screen_size_laptop, screen_size_tablet,
                     sim_card, video_card_memory, video_review, watch_material, water_proof, wireless_interface,
                     brand_id, category_id, discount_id, sub_category_id, create_at)
VALUES ( 3, 'https://softech.kg/image/cache/63ccea961faf68e3d9e4b6f1e4d845c8.jpg', 6.1, 0, 0.0, null, 3200, null, null
       , 'Black', 'hjdkdksk', null, 12, null, 512, 0, 0, 0, 128, 'Iphone 14 ', 90990, 0, 1, 081056, null
       , 6
       , 0, null, null, 0.0, 0.0, 'dua sim card', 0, 'https://www.youtube.com/watch?v=FT3ODSg1GFE', null, null, null, 1
       , 1, 1, 1, '2011-04-17 18:20:03');


insert into products(id, pdf, screen_diagonal, screen_display, size_of_watch, appointment_of_laptop, battery_capacity,
                     bracelet_material, case_shape, color, description, gender, guarantee, laptopcpu, memory_of_phone,
                     memory_of_smart_watch, memory_of_tablet, order_count, product_count, product_name, product_price,
                     product_rating, product_status, product_vendor_code, ram_of_laptop, ram_of_phone, ram_of_tablet,
                     screen_resolution_laptop, screen_resolution_tablet, screen_size_laptop, screen_size_tablet,
                     sim_card, video_card_memory, video_review, watch_material, water_proof, wireless_interface,
                     brand_id, category_id, discount_id, sub_category_id, create_at)
VALUES ( 4, 'https://softech.kg/image/cache/63ccea961faf68e3d9e4b6f1e4d845c8.jpg', 6.1, 0, 0.0, null, 3200, null, null
       , 'Purple', 'hjdkdksk', null, 12, null, 512, 0, 0, 0, 128, 'Iphone 13 pro max', 90990, 0, 1
       , 087478, null, 6
       , 0, null, null, 0.0, 0.0, 'dua sim card', 0, 'https://www.youtube.com/watch?v=FT3ODSg1GFE', null, null, null, 1
       , 1, 1, 1, '2011-04-17 18:20:03');


insert into products(id, pdf, screen_diagonal, screen_display, size_of_watch, appointment_of_laptop, battery_capacity,
                     bracelet_material, case_shape, color, description, gender, guarantee, laptopcpu, memory_of_phone,
                     memory_of_smart_watch, memory_of_tablet, order_count, product_count, product_name, product_price,
                     product_rating, product_status, product_vendor_code, ram_of_laptop, ram_of_phone, ram_of_tablet,
                     screen_resolution_laptop, screen_resolution_tablet, screen_size_laptop, screen_size_tablet,
                     sim_card, video_card_memory, video_review, watch_material, water_proof, wireless_interface,
                     brand_id, category_id, discount_id, sub_category_id, create_at)
VALUES ( 5, 'https://softech.kg/image/cache/63ccea961faf68e3d9e4b6f1e4d845c8.jpg', 6.1, 0, 0.0, null, 3200, null, null
       , 'White', 'hjdkdksk', null, 12, null, 512, 0, 0, 0, 128, 'Iphone 13 pro ', 90990, 0, 0, 045456
       , null, 6
       , 0, null, null, 0.0, 0.0, 'dua sim card', 0, 'https://www.youtube.com/watch?v=FT3ODSg1GFE', null, null, null, 1
       , 1, null, 1, '2011-04-17 18:20:03');



insert into products(id, pdf, screen_diagonal, screen_display, size_of_watch, appointment_of_laptop, battery_capacity,
                     bracelet_material, case_shape, color, description, gender, guarantee, laptopcpu, memory_of_phone,
                     memory_of_smart_watch, memory_of_tablet, order_count, product_count, product_name, product_price,
                     product_rating, product_status, product_vendor_code, ram_of_laptop, ram_of_phone, ram_of_tablet,
                     screen_resolution_laptop, screen_resolution_tablet, screen_size_laptop, screen_size_tablet,
                     sim_card, video_card_memory, video_review, watch_material, water_proof, wireless_interface,
                     brand_id, category_id, discount_id, sub_category_id, create_at)
values (6, 'https://softech.kg/image/cache/e5913ac2e5f9d99a5c796d3b43fc7b49.jpg', 0.0, 1.9, 44.8, null, 308, 'silicone',
        'square', 'Red', 'dskjkdsjkljsd', 1, 12, null
           , 0, 32, 0, 0, 1200, 'Apple Watch series 8', 42000, 0, 0, 675409, null, 0, 0, null, null, 0.0, 0.0, null,
        0, 'dgjk', 'Aluminium, ceramic, sapphire', 'yes', 'Bluetooth',
        1, 3, null, 17, '2011-04-17 18:20:03');

insert into products(id, pdf, screen_diagonal, screen_display, size_of_watch, appointment_of_laptop, battery_capacity,
                     bracelet_material, case_shape, color, description, gender, guarantee, laptopcpu, memory_of_phone,
                     memory_of_smart_watch, memory_of_tablet, order_count, product_count, product_name, product_price,
                     product_rating, product_status, product_vendor_code, ram_of_laptop, ram_of_phone, ram_of_tablet,
                     screen_resolution_laptop, screen_resolution_tablet, screen_size_laptop, screen_size_tablet,
                     sim_card, video_card_memory, video_review, watch_material, water_proof, wireless_interface,
                     brand_id, category_id, discount_id, sub_category_id, create_at)
values (7, 'https://softech.kg/image/cache/e5913ac2e5f9d99a5c796d3b43fc7b49.jpg', 0.0, 1.9, 12.2, null, 308, 'silicone',
        'square', 'Blue', 'dskjkdsjkljsd', 1, 12, null
           , 0, 32, 0, 0, 1200, 'Apple Watch series 7', 32000, 0, 0, 235409, null, 0, 0, null, null, 0.0, 0.0, null,
        0, 'fdjkh', 'Aluminium, ceramic, sapphire', 'yes', 'Bluetooth',
        1, 3, null, 17, '2011-04-17 18:20:03');


insert into products(id, pdf, screen_diagonal, screen_display, size_of_watch, appointment_of_laptop, battery_capacity,
                     bracelet_material, case_shape, color, description, gender, guarantee, laptopcpu, memory_of_phone,
                     memory_of_smart_watch, memory_of_tablet, order_count, product_count, product_name, product_price,
                     product_rating, product_status, product_vendor_code, ram_of_laptop, ram_of_phone, ram_of_tablet,
                     screen_resolution_laptop, screen_resolution_tablet, screen_size_laptop, screen_size_tablet,
                     sim_card, video_card_memory, video_review, watch_material, water_proof, wireless_interface,
                     brand_id, category_id, discount_id, sub_category_id, create_at)
values (8, 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQd3TIGbiCZ0f1ryquFCYKpcrTuM-wqmW2BCg&usqp=CAU', 13.3,
        0.0, 0.0, 'Office', 53, null, null, 'Green', 'fddfgd',
        1, 12, 'Apple M1', 0, 0, 0, 0, 125, 'Apple Mack Book Air M1', 80999, 0, 0, 457665, 8, 0, 0, '2560x1600 пикс',
        null, 21.4, 0.0, null,
        0, 'sdfsfd', null, null, null, 1, 2, null, 8, '2011-04-17 18:20:03');


insert into products(id, pdf, screen_diagonal, screen_display, size_of_watch, appointment_of_laptop, battery_capacity,
                     bracelet_material, case_shape, color, description, gender, guarantee, laptopcpu, memory_of_phone,
                     memory_of_smart_watch, memory_of_tablet, order_count, product_count, product_name, product_price,
                     product_rating, product_status, product_vendor_code, ram_of_laptop, ram_of_phone, ram_of_tablet,
                     screen_resolution_laptop, screen_resolution_tablet, screen_size_laptop, screen_size_tablet,
                     sim_card, video_card_memory, video_review, watch_material, water_proof, wireless_interface,
                     brand_id, category_id, discount_id, sub_category_id, create_at)
values (9, 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQtTwyPynpyQ2ozvPKNsNUok_h2tEyM7ZPG2A&usqp=CAU', 13.3,
        0.0, 0.0, 'Office', 53, null, null, 'Yellow', 'fddfgd',
        1, 12, 'Apple M1', 0, 0, 0, 0, 125, 'Apple Mack Book Air M2', 80999, 0, 0, 120665, 8, 0, 0, '2560x1600 пикс',
        null, 21.4, 0.0, null,
        0, 'sdfsfd', null, null, null, 1, 2, null, 8, '2011-04-17 18:20:03');


insert into products(id, pdf, screen_diagonal, screen_display, size_of_watch, appointment_of_laptop, battery_capacity,
                     bracelet_material, case_shape, color, description, gender, guarantee, laptopcpu, memory_of_phone,
                     memory_of_smart_watch, memory_of_tablet, order_count, product_count, product_name, product_price,
                     product_rating, product_status, product_vendor_code, ram_of_laptop, ram_of_phone, ram_of_tablet,
                     screen_resolution_laptop, screen_resolution_tablet, screen_size_laptop, screen_size_tablet,
                     sim_card, video_card_memory, video_review, watch_material, water_proof, wireless_interface,
                     brand_id, category_id, discount_id, sub_category_id, create_at)
values (10, 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSd7zYmmYlxRTw0_r4op0XPsxmN98DFaS0Hmw&usqp=CAU', 13.3,
        0.0, 0.0, 'Office', 53, null, null, 'Dark', 'fddfgd',
        1, 12, 'Apple M1', 0, 0, 0, 0, 125, 'Asus ROG G5', 80999, 0, 0, 452345, 8, 0, 0, '2560x1600 пикс',
        null, 21.4, 0.0, null,
        0, 'sdfsfd', null, null, null, 1, 2, null, 8, '2011-04-17 18:20:03');


insert into products(id, pdf, screen_diagonal, screen_display, size_of_watch, appointment_of_laptop, battery_capacity,
                     bracelet_material, case_shape, color, description, gender, guarantee, laptopcpu, memory_of_phone,
                     memory_of_smart_watch, memory_of_tablet, order_count, product_count, product_name, product_price,
                     product_rating, product_status, product_vendor_code, ram_of_laptop, ram_of_phone, ram_of_tablet,
                     screen_resolution_laptop, screen_resolution_tablet, screen_size_laptop, screen_size_tablet,
                     sim_card, video_card_memory, video_review, watch_material, water_proof, wireless_interface,
                     brand_id, category_id, discount_id, sub_category_id, create_at)
values (11, 'https://mobile-review.com/news/wp-content/uploads/HONOR-MagicBook-Pro.jpg', 13.3, 0.0, 0.0, 'Office', 53,
        null,
        null, 'Blue', 'fddfgd',
        1, 12, 'Apple M1', 0, 0, 0, 0, 125, 'HONOR Magic Book 15', 80999, 0, 0, 452365, 8, 0, 0, '2560x1600 пикс',
        null, 21.4, 0.0, null,
        0, 'sdfsfd', null, null, null, 1, 2, null, 8, '2011-04-17 18:20:03');


insert into banners(id)
values (1);

insert into banner_images(id, image_url)
values (1, 'https://www.budetsdelano.ru/upload/iblock/be0/sale_web.jpg');

insert into product_images(id, image_url)
VALUES (1,
        'https://asiastore.kg/image/cache/catalog/iphone/iphone14/iphone14promax/deeppurple/wwen_iphone14promax_q422_deep-purple_pdp-images_position-1a-1920x1080.jpg');
insert into product_images(id, image_url)
VALUES (1, 'https://www.apple.com/v/iphone-14-pro/c/images/meta/iphone-14-pro_overview__e414c54gtu6a_og.png');
insert into product_images(id, image_url)
VALUES (1, 'https://www.apple.com/my/iphone-14-pro/images/overview/design/design_startframe__f5z6xj9zkgmu_large.jpg');
insert into product_images(id, image_url)
VALUES (1,
        'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTR6qtAmQ_lkxOYMlhIQTfW8ptx4a80WpMQe5kmmTZzsab5tsP0cpkFzfmn8GMV0pekMB4&usqp=CAU');
insert into product_images(id, image_url)
VALUES (1,
        'https://www.apple.com/newsroom/images/product/iphone/standard/Apple-iPhone-14-Pro-iPhone-14-Pro-Max-hero-220907.jpg.news_app_ed.jpg');


insert into product_images(id, image_url)
VALUES (2, 'https://softech.kg/image/cache/41298c8e9b905465003380f38105bb38.jpg');
insert into product_images(id, image_url)
VALUES (2,
        'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRPmRQPFRPpRRQ5jjI6FlzO6aD3H5TAz2jcKsdzC39D2aT40Hkx6vBdHTH7i0mMCVGZ6Hc&usqp=CAU');
insert into product_images(id, image_url)
VALUES (2,
        'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTDEA9QiSRYXr4me3Xcs-O0h8Kuj1LK302R8nMEO-8fwUgnQr6iAEub8VuAUESmh3QVdkY&usqp=CAU');
insert into product_images(id, image_url)
VALUES (2, 'https://www.tabletowo.pl/wp-content/uploads/2022/09/iPhone-14-Pro-zrodlo-Apple.jpg');
insert into product_images(id, image_url)
VALUES (2,
        'https://cdn.shopify.com/s/files/1/0079/5602/products/iPhone_14_Pro_Space_Black_PDP_Image_Position-1a_CAEN_1024x1024.jpg?v=1662610399');


insert into product_images(id, image_url)
VALUES (3, 'https://media.wired.com/photos/6332360740fe1e8870aa3bc0/master/pass/iPhone-14-Review-Gear.jpg');
insert into product_images(id, image_url)
VALUES (3,
        'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRmhnRNlS3Sf46Dft1h0z0jxOm2qcxT_VpuYykREg5trichD2S95ATRlpOQ1RcAYO-TEic&usqp=CAU');
insert into product_images(id, image_url)
VALUES (3,
        'https://www.apple.com/newsroom/images/product/iphone/geo/Apple-iPhone-14-iPhone-14-Plus-2up-blue-220907-geo_inline.jpg.large.jpg');
insert into product_images(id, image_url)
VALUES (3, 'https://cdn.eraspace.com/media/catalog/product/i/p/iphone_14_purple_1_3.jpg');
insert into product_images(id, image_url)
VALUES (3, 'https://m.media-amazon.com/images/I/61BGE6iu4AL._SX466_.jpg');

insert into product_images(id, image_url)
VALUES (4, 'https://login.kg/image/cache/catalog/new/Phones/Apple/IPhone%2013/Pro/1-500x500.jpg');
insert into product_images(id, image_url)
VALUES (4, 'https://appleinsider.ru/wp-content/uploads/2021/09/iPhone_13_pro_max_00004.jpg');
insert into product_images(id, image_url)
VALUES (4, 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRSzKo-3DrlP7IwnV9tHOJqW_c7w9wHK0PFrw&usqp=CAU');
insert into product_images(id, image_url)
VALUES (4, 'https://myphone.kg/files/media/13/13453.jpg');
insert into product_images(id, image_url)
VALUES (4, 'https://static.toiimg.com/thumb/resizemode-4,msid-92431103,imgsize-14570,width-720/92431103.jpg');

insert into product_images(id, image_url)
VALUES (5,
        'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTaFe1qsBKsdOGu8ifPTUOuhIhvr0p_o-v0QfPJNqmnJUYk2k-cfhHLOL0HetTdHK-KfDI&usqp=CAU');
insert into product_images(id, image_url)
VALUES (5,
        'https://www.apple.com/newsroom/images/product/iphone/standard/Apple_iPhone-13-Pro_iPhone-13-Pro-Max_09142021_inline.jpg.large.jpg');
insert into product_images(id, image_url)
VALUES (5, 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRWGYeM9nHLsIjYyQmZe6ahCt_VsyJAxgUO9w&usqp=CAU');
insert into product_images(id, image_url)
VALUES (5, 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSV1bl0kdltTApSYPldlNlHfuC6FC0ljDUEeQ&usqp=CAU');
insert into product_images(id, image_url)
VALUES (5, 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSfYykZCRCNOciq2t6fqvLfgxYV-8ClqZ_Xqw&usqp=CAU');

insert into product_images(id, image_url)
VALUES (6, 'https://softech.kg/image/catalog/Products/Acsessuary/Apple/Smart-Watch/8%20Series/1a.jpg');
insert into product_images(id, image_url)
VALUES (6,
        'https://store.storeimages.cdn-apple.com/4982/as-images.apple.com/is/MPH43ref_VW_34FR+watch-45-alum-midnight-nc-8s_VW_34FR_WF_CO+watch-face-45-nike7s-desertberry-spruceaura_VW_34FR_WF_CO?wid=1400&hei=1400&trim=1%2C0&fmt=p-jpg&qlt=95&.v=1660757976267%2C1661968921009%2C1662143432353');
insert into product_images(id, image_url)
VALUES (6, 'https://softech.kg/image/cache/d48ec199cd27d8038c31ee71f2909f36.jpg');
insert into product_images(id, image_url)
VALUES (6, 'https://appleinsider.ru/wp-content/uploads/2022/09/new_watch-1.jpg');
insert into product_images(id, image_url)
VALUES (6,
        'https://store.storeimages.cdn-apple.com/4668/as-images.apple.com/is/MP7F3ref_VW_34FR+watch-45-alum-midnight-nc-8s_VW_34FR_WF_CO?wid=1400&hei=1400&trim=1%2C0&fmt=p-jpg&qlt=95&.v=1660778102078%2C1661968921009');

insert into product_images(id, image_url)
VALUES (7, 'https://login.kg/image/catalog/new/Aksessuary/Apple/Chasy/Watch%207/86956.jpg');
insert into product_images(id, image_url)
VALUES (7, 'https://softech.kg/image/cache/d0355fba0ca0fbfadb8bf55b74b3c8fa.jpg');
insert into product_images(id, image_url)
VALUES (7, 'https://svetofor.info/images/detailed/235/umnye-chasy-apple-watch-series-7-41mm-chernye-1.jpg');
insert into product_images(id, image_url)
VALUES (7, 'https://cdn.shopify.com/s/files/1/0568/5942/7015/products/MKJP3HN_A_1.png?v=1633758334');
insert into product_images(id, image_url)
VALUES (7,
        'https://store.storeimages.cdn-apple.com/4982/as-images.apple.com/is/FKN63?wid=2000&hei=2000&fmt=jpeg&qlt=90&.v=1647901207355');

insert into product_images(id, image_url)
VALUES (8, 'https://login.kg/image/cache/catalog/new/Notebook/Apple/MacBook%20Air%202020%20M1/1-500x500.jpeg');
insert into product_images(id, image_url)
VALUES (8, 'https://max.kg/nal/img/22565/b_tov_11442656_7caea5c6.jpg');
insert into product_images(id, image_url)
VALUES (8, 'https://www.apple.com/v/macbook-air/m/images/overview/hero_startframe__m19qweu56hea_large_2x.jpg');
insert into product_images(id, image_url)
VALUES (8,
        'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS5zQQ4NV4PanZRVX12IY3fS_zOdxkrRjWPcy9sQmlbWbsOXTR34YRPT_3kOcPV8rl1nS0&usqp=CAU');
insert into product_images(id, image_url)
VALUES (8,
        'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRXrz1s5_SNh2nSW5QHLCNhoNrwhsr9Bw14xHJJcK1Qe5PeZFyzCck02WfwoThJCVDdZss&usqp=CAU');

insert into product_images(id, image_url)
VALUES (9, 'https://softech.kg/image/cache/8a7c19189ebe9e7677b52c471760dae5.jpg');
insert into product_images(id, image_url)
VALUES (9,
        'https://www.apple.com/v/macbook-air-m2/d/images/overview/design/design_top_midnight__codbsd86bjxy_large.jpg');
insert into product_images(id, image_url)
VALUES (9, 'https://softech.kg/image/cache/892e41e2d275f2ecaa911e3e81630ea9.jpg');
insert into product_images(id, image_url)
VALUES (9, 'https://softech.kg/image/cache/8049bd076698c20ebbb6e6d2c0732073.jpg');
insert into product_images(id, image_url)
VALUES (9,
        'https://store.storeimages.cdn-apple.com/4668/as-images.apple.com/is/HQ8W2?wid=1200&hei=630&fmt=jpeg&qlt=95&.v=1666990110712');

insert into product_images(id, image_url)
VALUES (10, 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRtuvzKi94e3_8XzztzjnZt4jJ3i8ZQj9gvOQ&usqp=CAU');
insert into product_images(id, image_url)
VALUES (10, 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcShTTNaVuYtwnkTUIpWq64BZui2a6GjNURM1Q&usqp=CAU');
insert into product_images(id, image_url)
VALUES (10, 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSUSnQ1psLB-GurpVKraoLFWS8GDYjczV2Diw&usqp=CAU');
insert into product_images(id, image_url)
VALUES (10, 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR8PtsgvMju7yhcsgb7sJftMQvIJr6EmQb2lQ&usqp=CAU');
insert into product_images(id, image_url)
VALUES (10, 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQ4lDkVtW8QYjmsVpqSjosg7Nrw_LsWu1XVKw&usqp=CAU');

insert into product_images(id, image_url)
VALUES (11, 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSfwYRMZXK_YMesmgQ1QEyAvc_DIgFCotHeEw&usqp=CAU');
insert into product_images(id, image_url)
VALUES (11, 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQ9IWIdmyqbhRdE7vMymlHuNq9589CgRd2C_g&usqp=CAU');
insert into product_images(id, image_url)
VALUES (11, 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSysO-r6qeen8sCC_HJyAnXchWE0oiCITAdQw&usqp=CAU');
insert into product_images(id, image_url)
VALUES (11, 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQ9tR3imJ1fH7O6MzARMXsJyJuIYfeqtPzVqQ&usqp=CAU');
insert into product_images(id, image_url)
VALUES (11, 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSXaTI2JEyoxUDdvj94c-FmKBIaaKdkxe-_vQ&usqp=CAU');

insert into orders(id, address, count_of_product, date_of_order, delivery_status, email, first_name, last_name,
                   order_number, order_status, order_type, payment, phone_number, total_discount, total_sum, user_id)
values (1, 'Чуй 30', 0, '2011-04-17', 'WAITING', 'nurislam@gmail.com', 'Nurislam', 'Mamytov', 12345, 'WAITING',
        'DELIVERY', 'CASH', '+996222219743', 0, 0, 1);

insert into users_basket_list(user_id, basket_list_id)
values (1, 10);
insert into users_basket_list(user_id, basket_list_id)
values (1, 11);

insert into orders_products(product_id, order_id)
values (10, 1);
insert into orders_products(product_id, order_id)
values (11, 1);




