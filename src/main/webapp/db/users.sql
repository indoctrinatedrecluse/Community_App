CREATE TABLE users (
    user_id NUMBER NOT NULL,
    fname VARCHAR2(20) DEFAULT NULL,
    lname VARCHAR2(20) NOT NULL,
    gender VARCHAR2(10) NOT NULL,
    CONSTRAINT users_pk PRIMARY KEY (user_id)
);