CREATE TABLE shows(
    id INT NOT NULL AUTO_INCREMENT,
    name TEXT NOT NULL,
    category TEXT NOT NULL,

    PRIMARY KEY (id)
);

CREATE TABLE requests(
    id INT NOT NULL AUTO_INCREMENT,
    showId INT NOT NULL ,
    sourceIRCId INT NOT NULL,

    PRIMARY KEY (id)
);

CREATE TABLE sourceIRC(
    id INT NOT NULL AUTO_INCREMENT,
    server TEXT NOT NULL,
    channel TEXT NOT NULL,
    port INT NOT NULL,
    searchAPI TEXT NOT NULL,

    PRIMARY KEY (id)
);

CREATE TABLE season(
    seasonid INT NOT NULL AUTO_INCREMENT,
    showid INT NOT NULL,
    rootSeason INT NOT NULL,
    subSeason INT,
    name TEXT,
    episodes INT NOT NULL,

    PRIMARY KEY (seasonid)
);

CREATE TABLE showAlias(
    showId INT NOT NULL,
    showAlias VARCHAR(255) NOT NULL,

   	PRIMARY KEY (showId, showAlias)
);