create database finalProject;
use finalProject;
DROP TABLE fp_user;

CREATE TABLE fp_user(

                        id INT primary key AUTO_INCREMENT,

                        user_id VARCHAR(100),

                        password VARCHAR(200),

                        nickname VARCHAR(30),

                        email VARCHAR(100),

                        gender VARCHAR(1),

                        birth DATETIME,

                        phone VARCHAR(30),

                        created_at DATETIME DEFAULT now()

);
drop table fp_mail_auth;
CREATE TABLE fp_mail_auth(

                             id INT PRIMARY KEY AUTO_INCREMENT,

                             user_id INT,

                             auth_key VARCHAR(100),

                             complete VARCHAR(1),

                             created_at DATETIME DEFAULT now()

);

CREATE TABLE fp_article(
                           id INT PRIMARY KEY AUTO_INCREMENT,
                           user_id INT,
                           title VARCHAR(500),
                           content VARCHAR(4000),
                           read_count INT,
                           created_at DATETIME DEFAULT now()
);

CREATE TABLE fp_hobby_category(

                                  id INT PRIMARY KEY AUTO_INCREMENT,

                                  hobby_name VARCHAR(50),

                                  created_at DATETIME DEFAULT now()

);

CREATE TABLE fp_user_hobby(

                              id INT PRIMARY KEY AUTO_INCREMENT,

                              user_id INT,

                              hobby_id INT,

                              created_at DATETIME DEFAULT now()

);

INSERT INTO fp_hobby_category(hobby_name) VALUES('야구');

INSERT INTO fp_hobby_category(hobby_name) VALUES('축구');

INSERT INTO fp_hobby_category(hobby_name) VALUES('농구');

INSERT INTO fp_hobby_category(hobby_name) VALUES('족구');

INSERT INTO fp_hobby_category(hobby_name) VALUES('당구');