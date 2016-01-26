CREATE TABLE roles ( 
  id_role VARCHAR(20) NOT NULL PRIMARY KEY, 
  grant VARCHAR(10) NOT NULL
); 

CREATE TABLE users ( 
  id_user VARCHAR(20) NOT NULL PRIMARY KEY, 
  password VARCHAR(500) NOT NULL,
  name VARCHAR(50) NOT NULL,
  id_role VARCHAR(20) NOT NULL,
  FOREIGN KEY(id_role) REFERENCES roles(id_role)
); 

CREATE TABLE players (
  id_player VARCHAR(20) NOT NULL PRIMARY KEY,
  id_user VARCHAR(20) NOT NULL,
  position VARCHAR(20) DEFAULT NULL,
  status VARCHAR(20) DEFAULT NULL,
  FOREIGN KEY(id_user) REFERENCES users(id_user)
);

CREATE TABLE statistics (
  id_player VARCHAR(20) NOT NULL,
  score INT DEFAULT 0,
  matchesplayed INT DEFAULT 0,
  goals INT DEFAULT 0,
  FOREIGN KEY(id_player) REFERENCES players(id_player)
);

CREATE TABLE transfers (
  id_player VARCHAR(20) NOT NULL,
  amount INT DEFAULT 0,
  teamfrom VARCHAR(20) DEFAULT NULL,
  teamto VARCHAR(20) DEFAULT NULL,
  FOREIGN KEY(id_player) REFERENCES players(id_player)
);

CREATE TABLE board (
  id_user VARCHAR(20) NOT NULL,
  job VARCHAR(20) NOT NULL,
  FOREIGN KEY(id_user) REFERENCES users(id_user)  
);