CREATE SCHEMA IF NOT EXISTS rest;

CREATE SEQUENCE IF NOT EXISTS rest.common_id_seq START 1 CACHE 20;
ALTER SEQUENCE rest.common_id_seq OWNER TO postgres;

CREATE DOMAIN rest.PRIMARYKEY AS BIGINT NOT NULL DEFAULT nextval('rest.common_id_seq');

create table if not exists rest.role
(
    id rest.PRIMARYKEY not null,
    name varchar(20) not null,
    CONSTRAINT role_pkey PRIMARY KEY (id)
    );

INSERT INTO rest.role VALUES (1, 'ROLE_USER');

create table if not exists rest.user
(
    id rest.PRIMARYKEY not null,
    login varchar(12) not null,
    password varchar not null,
    role_id bigint not null,
    CONSTRAINT user_pkey PRIMARY KEY (id),
    CONSTRAINT user_login_key UNIQUE (login),
    CONSTRAINT fk_user_role FOREIGN KEY (role_id) REFERENCES role(id)
);