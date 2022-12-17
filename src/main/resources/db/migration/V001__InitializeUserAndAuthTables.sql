CREATE TABLE users
(
    id                    BIGSERIAL PRIMARY KEY,
    username              VARCHAR(64) NOT NULL,
    password              VARCHAR(96) NOT NULL,
    email                 VARCHAR(96) NOT NULL,
    locked                BOOLEAN     NOT NULL,
    activated             BOOLEAN     NOT NULL,
    expire_credentials_at TIMESTAMP   NULL
);

CREATE UNIQUE INDEX ON users (username);
CREATE UNIQUE INDEX ON users (email);

CREATE TABLE auth_token
(
    id         BIGSERIAL PRIMARY KEY,
    user_id    BIGINT      NOT NULL,
    token      VARCHAR(32) NOT NULL,
    expires_at TIMESTAMP   NOT NULL
);

CREATE UNIQUE INDEX ON auth_token (token);

CREATE TABLE role
(
    id   BIGSERIAL PRIMARY KEY,
    role VARCHAR(32) NOT NULL
);

CREATE UNIQUE INDEX ON role (role);

CREATE TABLE user_role
(
    id      BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL REFERENCES users (id),
    role_id BIGINT NOT NULL REFERENCES role (id)
);

CREATE UNIQUE INDEX ON user_role (user_id, role_id);

insert into role(role)
values ('ADMIN');
insert into role(role)
values ('USER');
