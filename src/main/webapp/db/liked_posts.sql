CREATE TABLE liked_posts (
    post_id NUMBER NOT NULL,
    user_id NUMBER NOT NULL,
    CONSTRAINT liked_posts_pk PRIMARY KEY (post_id, user_id)
);