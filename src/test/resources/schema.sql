CREATE TABLE IF NOT EXISTS users
(
    user_id             INT                   PRIMARY KEY       AUTO_INCREMENT  NOT NULL ,
    user_name           VARCHAR(64)           NOT NULL ,
    email               VARCHAR(128)          NOT NULL          UNIQUE,
    password            VARCHAR(64)           NOT NULL ,
    sex                 CHAR(1)              ,
    birth               DATE                 ,
    user_created_date   TIMESTAMP            NOT NULL
);

CREATE TABLE IF NOT EXISTS profile
(
    profile_id              INT                  PRIMARY KEY     AUTO_INCREMENT      NOT NULL ,
    user_id                 INT                  NOT NULL ,
    age                     INT                  NOT NULL ,
    height                  FLOAT                NOT NULL ,
    weight                  FLOAT                NOT NULL ,
    bmi                     FLOAT                NOT NULL ,
    activity                FLOAT                DEFAULT 1.2,
    bmr                     INT                  NOT NULL ,
    tdee                    INT                  NOT NULL ,
    goal_weight             FLOAT,
    expected_weight_change  VARCHAR(64),
    profile_created_date    TIMESTAMP            NOT NULL
);
-- 食材表
CREATE TABLE IF NOT EXISTS food
(
    food_id             INT         PRIMARY KEY         AUTO_INCREMENT      NOT NULL ,
    food_name           VARCHAR(64) NOT NULL ,
    food_cal            INT         DEFAULT (0) ,
    food_carbs          INT         DEFAULT (0),
    food_protein        INT         DEFAULT (0),
    food_fat            INT         DEFAULT (0),
    food_location       VARCHAR(64) DEFAULT ('OTHER')
);

-- 菜單
CREATE TABLE IF NOT EXISTS menu
(
    menu_id             INT         PRIMARY KEY     AUTO_INCREMENT      NOT NULL ,
    user_id             INT         NOT NULL ,
    meal_type           VARCHAR(64) NOT NULL ,
    food_id             INT,
    exchange            INT         DEFAULT (1),
    menu_created_date   TIMESTAMP   NOT NULL
);