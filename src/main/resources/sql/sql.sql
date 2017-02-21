CREATE DATABASE IF NOT EXISTS default_database;

DROP DATABASE IF EXISTS voting;

CREATE DATABASE IF NOT EXISTS voting;

USE voting;

DROP TABLE IF EXISTS vote;
DROP TABLE IF EXISTS polling_schedule;
DROP TABLE IF EXISTS polling;
DROP TABLE IF EXISTS person;
DROP TABLE IF EXISTS account;

CREATE TABLE account
(
    id       INT PRIMARY KEY AUTO_INCREMENT,
    email    VARCHAR(50) NOT NULL UNIQUE ,
    password VARCHAR(50) NOT NULL,
    role     VARCHAR(50) NOT NULL
);

CREATE TABLE person
(
    id			    INT PRIMARY KEY AUTO_INCREMENT,
    email		    VARCHAR(50) NOT NULL UNIQUE ,
    first_name 	VARCHAR(50),
    last_name 	VARCHAR(50),
    birthday 	  DATE,
    FOREIGN KEY (id) REFERENCES account(id) ON UPDATE CASCADE ON DELETE CASCADE,
    FOREIGN KEY (email) REFERENCES account(email) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE polling
(
	  id			    INT PRIMARY KEY AUTO_INCREMENT,
    poll_name	  VARCHAR(50) NOT NULL,
    description varchar(255),
    owner_id	  INT,
    start_time	TIMESTAMP NULL DEFAULT NULL,
    end_time 	  TIMESTAMP NULL DEFAULT NULL,
    INDEX(owner_id),
    FOREIGN KEY (owner_id) REFERENCES person(id) ON UPDATE CASCADE ON DELETE SET NULL
);

CREATE TABLE polling_schedule
(
	  id			   INT PRIMARY KEY AUTO_INCREMENT,
    polling_id INT NOT NULL,
    poll_var	 VARCHAR(255) NOT NULL,
    INDEX(polling_id),
    FOREIGN KEY (polling_id) REFERENCES polling(id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE vote
(
	  id			    INT PRIMARY KEY AUTO_INCREMENT,
    voter_id	  INT,
    polling_id	INT,
    poll_var_id INT,
	  date_time	  TIMESTAMP NOT NULL,
    INDEX(voter_id),
    INDEX(polling_id),
    INDEX(poll_var_id),
    FOREIGN KEY (voter_id) REFERENCES person(id) ON DELETE SET NULL ON UPDATE CASCADE,
    FOREIGN KEY (polling_id) REFERENCES polling(id) ON DELETE SET NULL ON UPDATE CASCADE,
    FOREIGN KEY (poll_var_id) REFERENCES polling_schedule(id) ON DELETE SET NULL ON UPDATE CASCADE
);

INSERT INTO account (email, password, role)
VALUES ("jack.morris@gmail.com", "root", "ADMIN"),
("john.mock@gmail.com", "mypass", "USER"),
("joe.morris@gmail.com", "root", "USER"),
("joe1.morris@gmail.com", "root", "USER"),
("joe2.morris@gmail.com", "root", "USER"),
("joe3.morris@gmail.com", "root", "USER"),
("joe4.morris@gmail.com", "root", "USER"),
("joe5.morris@gmail.com", "root", "USER"),
("joe6.morris@gmail.com", "root", "USER");

INSERT INTO person (first_name, last_name, email, birthday)
VALUES ("Jack", "Morris", "jack.morris@gmail.com", "1988-10-11"),
("John", "LOL", "john.mock@gmail.com", "1997-10-09"),
("Joe", "Mock", "joe.morris@gmail.com", "1994-09-16"),
("Joe1", "Mock", "joe1.morris@gmail.com", "1994-09-11"),
("Joe2", "Mock", "joe2.morris@gmail.com", "1994-09-12"),
("Joe3", "Mock", "joe3.morris@gmail.com", "1994-09-13"),
("Joe4", "Mock", "joe4.morris@gmail.com", "1994-09-14"),
("Joe5", "Mock", "joe5.morris@gmail.com", "1994-09-15"),
("Joe6", "Mock", "joe6.morris@gmail.com", "1994-09-16");

INSERT INTO polling (poll_name, description, owner_id, start_time, end_time)
VALUES ("2*2=?", "Very easy question", 1, NULL, NULL), ("2*3=?", "Is it easy question?", 1, current_timestamp(), NULL),
("To be or not to be?", "Are you seriously", 2, "2016-05-06", current_timestamp());

INSERT INTO polling_schedule (polling_id, poll_var)
VALUES (1, "1"), (1, "3"), (1, "4"), (2, "3"), (2, "6"), (3, "To be"), (3, "...or not to be"); 

INSERT INTO vote (voter_id, polling_id, poll_var_id, date_time)
VALUES (1, 2, 4, current_timestamp()), (1, 3, 6, "2016-06-07"), (2, 3, 7, current_timestamp()),
(3, 3, 6, current_timestamp()), (4, 3, 7, current_timestamp()), (5, 3, 6, current_timestamp()),
(6, 3, 6, current_timestamp()), (7, 3, 6, current_timestamp()), (8, 3, 6, current_timestamp()),
(9, 3, 7, current_timestamp());
