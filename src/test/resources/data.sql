-- users
INSERT INTO users (user_name,email, password, sex, birth, user_created_date)
VALUES ('user1','user1@gmail.com', '202cb962ac59075b964b07152d234b70', 'F','1999-12-31', '2024-09-10');
INSERT INTO users (user_name,email, password, sex, birth, user_created_date)
VALUES ('user2','user2@gmail.com', '202cb962ac59075b964b07152d234b70', 'M','1999-01-31', '2024-09-10');
-- profile
INSERT INTO profile (user_id,age,height,weight,bmi,activity,bmr,tdee,goal_weight,expected_weight_change,profile_created_date)
VALUES (2,25,178,90,28.4,1.2,1722,2066,72.0,'理想的體重下降速度：0.3 kg/週','2024-09-10');
-- food
INSERT INTO food (food_name,food_cal,food_carbs,food_protein,food_fat,food_location)
VALUES('夯番薯100g',127,28,1,0,'CVS');
INSERT INTO food (food_name,food_cal,food_carbs,food_protein,food_fat,food_location)
VALUES('紐奧良風味烤雞三明治',217,18,13,10,'CVS');
INSERT INTO food (food_name,food_cal,food_carbs,food_protein,food_fat,food_location)
VALUES('醬燒雞肉三明治',182,21,8,7,'CVS');
INSERT INTO food (food_name,food_cal,food_carbs,food_protein,food_fat,food_location)
VALUES('烤蛋白餐盒',513,73,34,10,'CVS');
INSERT INTO food (food_name,food_cal,food_carbs,food_protein,food_fat,food_location)
VALUES('健身Ｇ肉餐盒',500,68,29,12.4,'CVS');
INSERT INTO food (food_name,food_cal,food_carbs,food_protein,food_fat,food_location)
VALUES('腿排沙拉(去皮)',232,15,25,8,'MY_WARM_DAY');
INSERT INTO food (food_name,food_cal,food_carbs,food_protein,food_fat,food_location)
VALUES('豬排蛋餅',212,20,13,9,'MY_WARM_DAY');
INSERT INTO food (food_name,food_cal,food_carbs,food_protein,food_fat,food_location)
VALUES('無糖鮮奶茶中杯',152,12,8,8,'MY_WARM_DAY');
INSERT INTO food (food_name,food_cal,food_carbs,food_protein,food_fat,food_location)
VALUES('鮪魚口袋歐姆蛋',301,17,17,18,'LOUISA');
INSERT INTO food (food_name,food_cal,food_carbs,food_protein,food_fat,food_location)
VALUES('起司口袋歐姆蛋',275,16,15,17,'LOUISA');
INSERT INTO food (food_name,food_cal,food_carbs,food_protein,food_fat,food_location)
VALUES('麥香燻雞三明治',334,44,14,11,'LOUISA');
INSERT INTO food (food_name,food_cal,food_carbs,food_protein,food_fat,food_location)
VALUES('麥香牛肉三明治',310,45,13,9,'LOUISA');
--menu
INSERT INTO menu (user_id,meal_type,food_id,exchange,menu_created_date)
VALUES(2,'B',1,1,'2024-09-11 08:00:00');
INSERT INTO menu (user_id,meal_type,food_id,exchange,menu_created_date)
VALUES(2,'BS',8,1,'2024-09-11 11:00:00');
INSERT INTO menu (user_id,meal_type,food_id,exchange,menu_created_date)
VALUES(2,'L',9,1,'2024-09-11 12:00:00');
INSERT INTO menu (user_id,meal_type,food_id,exchange,menu_created_date)
VALUES(2,'D',4,1,'2024-09-11 17:00:00');
INSERT INTO menu (user_id,meal_type,food_id,exchange,menu_created_date)
VALUES(2,'B',2,1,'2024-09-12 08:00:00');