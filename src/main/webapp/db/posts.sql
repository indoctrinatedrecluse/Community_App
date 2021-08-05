CREATE TABLE posts (
    post_id NUMBER NOT NULL,
    title VARCHAR2(30) DEFAULT NULL,
    content VARCHAR2(2000) DEFAULT NULL,
    timestamp TIMESTAMP NOT NULL,
    category_id NUMBER NOT NULL,
    user_id NUMBER NOT NULL,
    CONSTRAINT posts_pk PRIMARY KEY (post_id)
);