CREATE TABLE policies (
  id          INTEGER PRIMARY KEY,
  name VARCHAR(128) NOT NULL,
  current_value VARCHAR(64) NULL);


CREATE TABLE resources (
  id          INTEGER PRIMARY KEY,
  name VARCHAR(64) NOT NULL);

  CREATE TABLE statistics (
    id          INTEGER PRIMARY KEY auto_increment,
    visitor_city VARCHAR(128) NULL,
    visit_date_time TIMESTAMP NOT NULL);
