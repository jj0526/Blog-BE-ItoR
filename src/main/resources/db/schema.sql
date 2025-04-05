CREATE TABLE IF NOT EXISTS `user`
(
    id              BIGINT       NOT NULL AUTO_INCREMENT,
    refreshToken    VARCHAR(255),
    email           VARCHAR(255) NOT NULL,
    password        VARCHAR(255),
    name            VARCHAR(255) NOT NULL,
    kakaoId         BIGINT,
    nickname        VARCHAR(255),
    birthDate       DATE,
    profileImageUrl VARCHAR(255),
    introduction    TEXT         NOT NULL,
    createdAt       TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    modifiedAt      TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (id)

);
