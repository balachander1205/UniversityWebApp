CREATE TABLE appointments (
  id INTEGER NOT NULL,
  studentname varchar(100) DEFAULT NULL,
  repname varchar(100) DEFAULT NULL,
  universityname varchar(100) DEFAULT NULL,
  location varchar(500) DEFAULT NULL,
  appointmentdate date DEFAULT NULL,
  appointmentslot varchar(50) DEFAULT NULL,
  createdatetime timestamp NULL DEFAULT current_timestamp
);

CREATE TABLE hibernate_sequence (
  next_val bigint DEFAULT NULL
);

CREATE TABLE roles (
  id INTEGER NOT NULL,
  name varchar(50) DEFAULT NULL
);

CREATE TABLE students (
  id INTEGER NOT NULL,
  studentname varchar(100) DEFAULT NULL,
  phonenumber varchar(20) DEFAULT NULL,
  email varchar(100) DEFAULT NULL,
  studentlocation varchar(500) DEFAULT NULL,
  universityname varchar(500) DEFAULT NULL,
  location varchar(500) DEFAULT NULL,
  feedback varchar(500) DEFAULT NULL,
  createdatetime timestamp NULL DEFAULT NULL,
  activestatus INTEGER DEFAULT 1
);


CREATE TABLE university (
  id INTEGER NOT NULL,
  universityname varchar(500) DEFAULT NULL,
  description varchar(500) DEFAULT NULL,
  location varchar(500) DEFAULT NULL,
  repname varchar(500) DEFAULT NULL,
  position varchar(500) DEFAULT NULL,
  admissionintake varchar(500) DEFAULT NULL,
  username varchar(50) DEFAULT NULL,
  password varchar(50) DEFAULT NULL,
  state varchar(200) DEFAULT NULL,
  countrycode varchar(5) DEFAULT NULL
);

CREATE TABLE users (
  id bigint NOT NULL,
  createddatetime timestamp DEFAULT NULL,
  email varchar(255) DEFAULT NULL,
  enabled bit(1) DEFAULT NULL,
  locked bit(1) DEFAULT NULL,
  mobile varchar(255) DEFAULT NULL,
  password varchar(255) DEFAULT NULL,
  role varchar(255) DEFAULT NULL,
  updateddatetime timestamp DEFAULT NULL,
  createtimestamp timestamp DEFAULT NULL,
  username varchar(255) DEFAULT NULL
);


CREATE TABLE users_sequence (
  next_val bigint DEFAULT NULL
);

ALTER TABLE appointments ADD PRIMARY KEY (id);
ALTER TABLE roles ADD PRIMARY KEY (id);
ALTER TABLE students ADD PRIMARY KEY (id);
ALTER TABLE university ADD PRIMARY KEY (id);


ALTER TABLE appointments MODIFY id int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;
ALTER TABLE roles MODIFY id int(11) NOT NULL AUTO_INCREMENT;
ALTER TABLE students MODIFY id int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;
ALTER TABLE university MODIFY id int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12336;

INSERT INTO users (id, createddatetime, email, enabled, locked, mobile, password, role, updateddatetime, createtimestamp, username) VALUES
(6, '2024-05-02 21:43:55', 'admin@gmail.com', '1', '0', '1234567890', '$2a$10$9Tk6MQGttWeAizvD52QlreneOYntv4MZxkCXnY7NQY5Hjnz4kqMQK', 'ADMIN', '2024-05-02 21:43:55', NULL, NULL);

INSERT INTO users (id, createddatetime, email, enabled, locked, mobile, password, role, updateddatetime, createtimestamp, username) VALUES
(5, '2024-05-02 21:43:55', 'balachander1205@gmail.com', '1', '0', '8919180283', '$2a$10$9Tk6MQGttWeAizvD52QlreneOYntv4MZxkCXnY7NQY5Hjnz4kqMQK', 'ADMIN', '2024-05-02 21:43:55', NULL, NULL);

COMMIT;

