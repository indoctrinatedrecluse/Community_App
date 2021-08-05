CREATE TABLE comments (
    comment_id NUMBER NOT NULL,
    comment_content VARCHAR2(250) DEFAULT NULL,
    post_id NUMBER NOT NULL,
    timestamp TIMESTAMP NOT NULL,
    user_id NUMBER NOT NULL,
    CONSTRAINT comments_pk PRIMARY KEY (comment_id)
);