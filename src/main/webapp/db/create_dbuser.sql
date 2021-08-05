conn system/password;
CREATE USER tester IDENTIFIED BY password;
GRANT CONNECT TO tester;
GRANT ALL PRIVILEGES TO tester;
disconn;