INSERT INTO user_product(user_id, product_source_identifier)
VALUES
((SELECT id FROM `user` WHERE email = 'hedzaprog@gmail.com' LIMIT 1), '67'),
((SELECT id FROM `user` WHERE email = 'jack.doe@test.com' LIMIT 1), '34');