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

CREATE TABLE IF NOT EXISTS post (
                      id BIGINT PRIMARY KEY AUTO_INCREMENT,
                      user_id BIGINT NOT NULL,
                      title VARCHAR(255),
                      comment_count BIGINT DEFAULT 0,
                      created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                      updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                      FOREIGN KEY (user_id) REFERENCES user(id)
);

CREATE TABLE IF NOT EXISTS content (
                         id BIGINT PRIMARY KEY AUTO_INCREMENT,
                         post_id BIGINT NOT NULL,
                         content_data TEXT,
                         content_type VARCHAR(50),
                         created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                         updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                         FOREIGN KEY (post_id) REFERENCES post(id)
);
