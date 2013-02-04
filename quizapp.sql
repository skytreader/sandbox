CREATE TABLE IF NOT EXISTS users(
    userid INTEGER AUTO_INCREMENT,
    username VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    total_score INTEGER DEFAULT 0,
    create_time TIMESTAMP NOT NULL,
    last_updater INTEGER NOT NULL,
    last_update TIMESTAMP NOT NULL,
    can_read BOOLEAN NOT NULL DEFAULT TRUE,
    can_write BOOLEAN NOT NULL DEFAULT TRUE,
    can_exec BOOLEAN NOT NULL DEFAULT FALSE,
    PRIMARY KEY (userid),
    FOREIGN KEY (last_updater) REFERENCES users (userid)
) ENGINE = INNODB;

/**
top_scorer is actually a foreign key referencing a user
in table users. However, since, at the creation of a category,
no one is supposed to be a top-scorer yet, we forgo.
*/
CREATE TABLE IF NOT EXISTS quiz_categories(
    quizcatid INTEGER AUTO_INCREMENT,
    category_name VARCHAR(255) UNIQUE NOT NULL,
    top_scorer INTEGER DEFAULT 0,
    PRIMARY KEY (quizcatid)
) ENGINE = INNODB;

/**
And the quiz items themselves are stored in some non-relational
structure or something...
*/
CREATE TABLE IF NOT EXISTS quizzes(
    quizid INTEGER AUTO_INCREMENT,
    quizcatid INTEGER NOT NULL,
    quiz_title VARCHAR(30) NOT NULL,
    quiz_desc VARCHAR(255) NOT NULL,
    last_updater INTEGER NOT NULL,
    last_update TIMESTAMP NOT NULL,
    PRIMARY KEY (quizid),
    FOREIGN KEY (last_updater) REFERENCES users (userid),
    FOREIGN KEY (quizcatid) REFERENCES quiz_categories (quizcatid)
) ENGINE = INNODB;

CREATE TABLE IF NOT EXISTS tags(
    tagid INTEGER AUTO_INCREMENT,
    taglabel VARCHAR(20) NOT NULL,
    last_updater INTEGER NOT NULL,
    last_update TIMESTAMP NOT NULL,
    PRIMARY KEY (tagid),
    FOREIGN KEY (last_updater) REFERENCES users (userid)
) ENGINE = INNODB;

CREATE TABLE IF NOT EXISTS quiztags(
    tagid INTEGER,
    quizid INTEGER,
    last_updater INTEGER NOT NULL,
    last_update TIMESTAMP NOT NULL,
    PRIMARY KEY (tagid, quizid),
    FOREIGN KEY (tagid) REFERENCES tags (tagid),
    FOREIGN KEY (quizid) REFERENCES quizzes (quizid),
    FOREIGN KEY (last_updater) REFERENCES users (userid)
) ENGINE = INNODB;
