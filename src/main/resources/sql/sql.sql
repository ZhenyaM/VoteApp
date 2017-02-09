CREATE DATABASE IF NOT EXISTS default_database;

CREATE DATABASE IF NOT EXISTS voting;

USE voting;

DROP TABLE IF EXISTS vote;
DROP TABLE IF EXISTS polling_schedule;
DROP TABLE IF EXISTS polling;
DROP TABLE IF EXISTS person;

CREATE TABLE person
(
	  id			INT PRIMARY KEY AUTO_INCREMENT,
    first_name 	VARCHAR(50) NOT NULL,
    last_name	VARCHAR(50) NOT NULL,
    email		VARCHAR(50) NOT NULL,
    birthday 	DATE
);

CREATE TABLE polling
(
	id			INT PRIMARY KEY AUTO_INCREMENT,
    poll_name	VARCHAR(50) NOT NULL,
    description varchar(255),
    owner_id	INT,
    start_time	TIMESTAMP NULL DEFAULT NULL,
    end_time 	TIMESTAMP NULL DEFAULT NULL,
    INDEX(owner_id),
    FOREIGN KEY (owner_id) REFERENCES person(id) ON UPDATE CASCADE ON DELETE SET NULL
);

CREATE TABLE polling_schedule
(
	id			INT PRIMARY KEY AUTO_INCREMENT,
    polling_id	INT NOT NULL,
    poll_var	VARCHAR(255) NOT NULL,
    INDEX(polling_id),
    FOREIGN KEY (polling_id) REFERENCES polling(id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE vote
(
	id			INT PRIMARY KEY AUTO_INCREMENT,
    voter_id	INT,
    polling_id	INT,
    poll_var_id INT,
	date_time	TIMESTAMP NOT NULL,
    INDEX(voter_id),
    INDEX(polling_id),
    INDEX(poll_var_id),
    FOREIGN KEY (voter_id) REFERENCES person(id) ON DELETE SET NULL ON UPDATE CASCADE,
    FOREIGN KEY (polling_id) REFERENCES polling(id) ON DELETE SET NULL ON UPDATE CASCADE,
    FOREIGN KEY (poll_var_id) REFERENCES polling_schedule(id) ON DELETE SET NULL ON UPDATE CASCADE
);

INSERT INTO person (first_name, last_name, email, birthday)
VALUES ("Jack", "Morris", "jack.morris@gmail.com", "1988-10-11"),
("John", "Mock", "john.mock@gmail.com", "1994-09-16");

INSERT INTO polling (poll_name, description, owner_id, start_time, end_time)
VALUES ("2*2=?", "", 1, NULL, NULL), ("2*3=?", "Is it easy question?", 1, current_timestamp(), NULL),
("To be or not to be?", "Are you seriously", 2, "2016-05-06", current_timestamp());

INSERT INTO polling_schedule (polling_id, poll_var)
VALUES (1, "1"), (1, "3"), (1, "4"), (2, "3"), (2, "6"), (3, "To be"), (3, "...or not to be"); 

INSERT INTO vote (voter_id, polling_id, poll_var_id, date_time)
VALUES (1, 2, 1, current_timestamp()), (1, 3, 2, "2016-06-07"), (2, 3, 1, current_timestamp());

SELECT * FROM vote;
SELECT * FROM person;
SELECT * FROM polling;
SELECT * FROM polling_schedule;
