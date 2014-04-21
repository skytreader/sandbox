CREATE TABLE users(
    userid INTEGER AUTO_INCREMENT,
    username VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    create_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    last_updater INTEGER NOT NULL,
    last_update TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    can_read BOOLEAN NOT NULL DEFAULT TRUE,
    can_write BOOLEAN NOT NULL DEFAULT FALSE,
    can_exec BOOLEAN NOT NULL DEFAULT FALSE,
    PRIMARY KEY (userid),
) ENGINE = INNODB;

INSERT INTO users (username, password, last_updater, can_read, can_write, can_exec)
VALUES ('root', 'root', 0, 1, 1, 1);
