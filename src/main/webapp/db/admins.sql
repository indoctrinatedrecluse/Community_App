CREATE TABLE admins (
    id NUMBER NOT NULL,
    username VARCHAR2(20) NOT NULL,
    pass VARCHAR2(20) NOT NULL,
    CONSTRAINT admins_pk PRIMARY KEY (id)
);