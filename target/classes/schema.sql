DROP TABLE IF EXISTS TBL_EMPLOYEES;
 
CREATE TABLE TBL_EMPLOYEES (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  first_name VARCHAR(250) NOT NULL,
  last_name VARCHAR(250) NOT NULL,
  email VARCHAR(250) DEFAULT NULL
);

DROP TABLE IF EXISTS AGENT;
CREATE TABLE AGENT (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  name VARCHAR(250) NOT NULL,
  email VARCHAR(250) NOT NULL UNIQUE,
  count INT DEFAULT 0
);

DROP TABLE IF EXISTS CUSTOMER;
CREATE TABLE CUSTOMER (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  name VARCHAR(250) NOT NULL,
  email VARCHAR(250) NOT NULL UNIQUE
);

DROP TABLE IF EXISTS TICKETS;
 
CREATE TABLE TICKETS (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  type VARCHAR(250) NOT NULL,
  description VARCHAR(250),
  created_agent INT NOT NULL,
  customer INT NOT NULL,
  assigned_agent INT,
  created BIGINT NOT NULL,
  updated BIGINT NOT NULL,
  priority ENUM ('HIGH','MEDIUM','LOW'),
  status ENUM ('OPEN','WAITING_ON_CUSTOMER','CUSTOMER_RESPONDED','RESOLVED','CLOSED'),
  FOREIGN KEY (customer) REFERENCES CUSTOMER(id),
  FOREIGN KEY (created_agent) REFERENCES AGENT(id)
);

