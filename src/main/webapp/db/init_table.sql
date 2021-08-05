CREATE OR REPLACE PROCEDURE create_table_if_doesnt_exist(
  p_table_name VARCHAR2,
  create_table_query VARCHAR2
) AUTHID CURRENT_USER IS
  n NUMBER;
BEGIN
  SELECT COUNT(*) INTO n FROM user_tables WHERE table_name = UPPER(p_table_name);
  IF (n = 0) THEN
    EXECUTE IMMEDIATE create_table_query;
  END IF;
END;
/