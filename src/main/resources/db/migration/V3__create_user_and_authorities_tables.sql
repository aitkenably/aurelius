CREATE TABLE users
(
    username TEXT NOT NULL PRIMARY KEY,
    password TEXT NOT NULL,
    enabled BOOLEAN not null
);

CREATE TABLE authorities
(
    username TEXT NOT NULL,
    authority TEXT NOT NULL,
    FOREIGN KEY (username) REFERENCES users(username)
);