ALTER TABLE account ADD CONSTRAINT account_users_fk FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE;

ALTER TABLE comments ADD CONSTRAINT comments_users_fk FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE;
ALTER TABLE comments ADD CONSTRAINT comments_posts_fk FOREIGN KEY (post_id) REFERENCES posts(post_id) ON DELETE CASCADE;

ALTER TABLE posts ADD CONSTRAINT posts_category_fk FOREIGN KEY (category_id) REFERENCES category(category_id) ON DELETE CASCADE;

COMMIT;