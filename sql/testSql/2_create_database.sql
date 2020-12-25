CREATE DATABASE `test_yoga_db`;

CREATE USER 'yoga_user'@'localhost'
IDENTIFIED BY 'yoga_password';

GRANT SELECT,INSERT,UPDATE,DELETE
ON `test_yoga_db`.*
TO 'yoga_user'@'localhost';





