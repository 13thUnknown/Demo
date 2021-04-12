DROP TABLE IF EXISTS inner_users;
DROP TABLE IF EXISTS transactions;
DROP TABLE IF EXISTS roles;
DROP TABLE IF EXISTS users_roles;

CREATE TABLE inner_users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    login VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT current_timestamp NOT NULL,
    updated_at TIMESTAMP DEFAULT current_timestamp NOT NULL
);

CREATE TABLE transactions (
    id INT AUTO_INCREMENT PRIMARY KEY,
    value DECIMAL (10,4) NOT NULL,
    currency VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT current_timestamp NOT NULL,
    updated_at TIMESTAMP DEFAULT current_timestamp NOT NULL,
    user_id INT NOT NULL
);

CREATE TABLE roles (
    id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL UNIQUE,
    created_at TIMESTAMP DEFAULT current_timestamp NOT NULL,
    updated_at TIMESTAMP DEFAULT current_timestamp NOT NULL
);

CREATE TABLE users_roles (
    user_id INT NOT NULL,
    role_id INT NOT NULL
);

alter table users_roles add constraint fk_users_roles_role_id foreign key (role_id) references roles;
alter table users_roles add constraint fk_users_roles_user_id foreign key (user_id) references inner_users;
alter table transactions add constraint fk_users_transactions_user_id foreign key (user_id) references inner_users;

insert into roles(id, created_at, updated_at, title) values (1,current_timestamp,current_timestamp,'ROLE_USER');
insert into roles(id, created_at, updated_at, title) values (2,current_timestamp,current_timestamp,'ROLE_ADMIN');