-- users
INSERT INTO users (user_name,email, password, sex, birth, user_created_date)
VALUES ('user1','user1@gmail.com', '202cb962ac59075b964b07152d234b70', 'F','1999-12-31', '2024-09-10');
INSERT INTO users (user_name,email, password, sex, birth, user_created_date)
VALUES ('user2','user2@gmail.com', '202cb962ac59075b964b07152d234b70', 'M','1999-01-31', '2024-09-10');
-- profile
INSERT INTO profile (user_id,age,height,weight,bmi,activity,bmr,tdee,goal_weight,expected_weight_change,profile_created_date)
VALUES (2,25,178,90,28.4,1.2,1722,2066,72.0,'理想的體重下降速度：0.3 kg/週','2024-09-10');
