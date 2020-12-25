CREATE DATABASE `yoga_db`;

CREATE USER 'yoga_user'@'localhost'
IDENTIFIED BY 'yoga_password';

GRANT SELECT,INSERT,UPDATE,DELETE
ON `yoga_db`.*
TO 'yoga_user'@'localhost';

/*
GRANT SELECT,INSERT,UPDATE,DELETE
ON `yoga_db`.*
TO 'yoga_user'@'%';
*/
