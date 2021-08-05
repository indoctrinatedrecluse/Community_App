CREATE TABLE liked_comments (
    post_id NUMBER NOT NULL,
    comment_id NUMBER NOT NULL,
    user_id NUMBER NOT NULL,
    CONSTRAINT liked_comments_pk PRIMARY KEY (post_id, comment_id, user_id)
);