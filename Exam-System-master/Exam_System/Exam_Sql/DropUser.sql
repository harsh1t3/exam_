drop user 'user'@'localhost';
flush privileges;
CREATE USER 'user'@'localhost' IDENTIFIED BY '1316';
GRANT ALL PRIVILEGES ON examsystem.* TO 'user'@'localhost';
