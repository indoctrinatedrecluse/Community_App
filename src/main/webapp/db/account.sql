CREATE TABLE account (
    account_id NUMBER NOT NULL,
    username VARCHAR2(20) NOT NULL,
    password VARCHAR2(20) NOT NULL,
    user_id NUMBER NOT NULL,
    CONSTRAINT account_pk PRIMARY KEY (account_id)
);