create table client (

    id BIGINT NOT NULL AUTO_INCREMENT,
    name CHAR(60) NOT NULL,
    email CHAR(255) NOT NULL,
    phone CHAR(20) NOT NULL,

    PRIMARY KEY(id)
);