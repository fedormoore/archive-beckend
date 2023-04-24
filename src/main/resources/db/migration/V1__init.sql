CREATE TABLE ar_roles
(
    id   bigserial    not null
        constraint ar_roles_table_pk primary key,
    name VARCHAR(250) NOT NULL
);

INSERT INTO ar_roles (name)
VALUES ('ROLE_ACQUISITION'), -- Отдел комплектования
       ('ROLE_STORAGE'),     -- Заведующий хранилищем
       ('ROLE_SCANNING'),    -- Отдел сканирования
              ('ROLE_LOADING'),    -- Отдел загрузки
                     ('ROLE_DIRECTOR'),    -- Отдел руководителей
       ('ROLE_ADMIN');

CREATE TABLE IF NOT EXISTS ar_accounts
(
    id           bigserial          not null
        constraint ar_accounts_table_pk primary key,

    login        VARCHAR(50) UNIQUE not null,
    password     VARCHAR(500)       not null,

    last_name    VARCHAR(255)       not null,
    first_name   VARCHAR(255)       not null,
    middle_names VARCHAR(255),

    created_at   timestamp default current_timestamp,
    update_at    timestamp default current_timestamp,
    deleted_at   timestamp,
    deleted      BOOLEAN   DEFAULT false
);

INSERT INTO ar_accounts (login, password, last_name, first_name)
VALUES ('acquisition', '$2a$10$P1k9Kz7oyjsOQnlaYPlnTOx/g/kdBu8333lmifkM5G1u5KmS7KkEi', 'acquisition', 'acquisition');
INSERT INTO ar_accounts (login, password, last_name, first_name)
VALUES ('storage', '$2a$10$P1k9Kz7oyjsOQnlaYPlnTOx/g/kdBu8333lmifkM5G1u5KmS7KkEi', 'storage', 'storage');
INSERT INTO ar_accounts (login, password, last_name, first_name)
VALUES ('scanning', '$2a$10$P1k9Kz7oyjsOQnlaYPlnTOx/g/kdBu8333lmifkM5G1u5KmS7KkEi', 'scanning', 'scanning');
INSERT INTO ar_accounts (login, password, last_name, first_name)
VALUES ('loading', '$2a$10$P1k9Kz7oyjsOQnlaYPlnTOx/g/kdBu8333lmifkM5G1u5KmS7KkEi', 'loading', 'loading');
INSERT INTO ar_accounts (login, password, last_name, first_name)
VALUES ('director', '$2a$10$P1k9Kz7oyjsOQnlaYPlnTOx/g/kdBu8333lmifkM5G1u5KmS7KkEi', 'director', 'director');
INSERT INTO ar_accounts (login, password, last_name, first_name)
VALUES ('admin', '$2a$10$P1k9Kz7oyjsOQnlaYPlnTOx/g/kdBu8333lmifkM5G1u5KmS7KkEi', 'admin', 'admin');

CREATE TABLE ar_users_roles
(
    user_id bigint REFERENCES ar_accounts (id),
    role_id bigint REFERENCES ar_roles (id),
    PRIMARY KEY (user_id, role_id)
);

INSERT INTO ar_users_roles (user_id, role_id)
VALUES (1, 1);
INSERT INTO ar_users_roles (user_id, role_id)
VALUES (2, 2);
INSERT INTO ar_users_roles (user_id, role_id)
VALUES (3, 3);
INSERT INTO ar_users_roles (user_id, role_id)
VALUES (4, 4);
INSERT INTO ar_users_roles (user_id, role_id)
VALUES (5, 5);
INSERT INTO ar_users_roles (user_id, role_id)
VALUES (6, 6);

CREATE TABLE IF NOT EXISTS ar_fond
(
    id          bigserial           not null
        constraint ar_fond_table_pk primary key,

    number_fond integer UNIQUE      not null,
    name        VARCHAR(255) UNIQUE not null,
    author      bigint REFERENCES ar_accounts (id), -- автор

    created_at  timestamp default current_timestamp,
    update_at   timestamp default current_timestamp,
    deleted_at  timestamp,
    deleted     BOOLEAN   DEFAULT false
);

CREATE TABLE IF NOT EXISTS ar_status_record (
    id          bigserial           not null
        constraint ar_status_record_table_pk primary key,
    status VARCHAR(100) UNIQUE NOT NULL,
    visible VARCHAR(100) NOT NULL,
    selection VARCHAR(100) NOT NULL
);

INSERT INTO ar_status_record (status, visible, selection) VALUES ('Черновик', 'ACQUISITION, DIRECTOR', 'ACQUISITION');
INSERT INTO ar_status_record (status, visible, selection) VALUES ('Передано в хранилище', 'ACQUISITION, STORAGE, DIRECTOR', 'ACQUISITION');
INSERT INTO ar_status_record (status, visible, selection) VALUES ('Принято на хранение', 'ACQUISITION, DIRECTOR', 'ACQUISITION');

INSERT INTO ar_status_record (status, visible, selection) VALUES ('На доработке от заведующего хранилищем', 'ACQUISITION, STORAGE, DIRECTOR', 'STORAGE');
INSERT INTO ar_status_record (status, visible, selection) VALUES ('Передано на сканирование', 'ACQUISITION, STORAGE, SCANNING, DIRECTOR', 'STORAGE');

INSERT INTO ar_status_record (status, visible, selection) VALUES ('На доработке от отдела сканирования', 'ACQUISITION, STORAGE, SCANNING, DIRECTOR', 'SCANNING');
INSERT INTO ar_status_record (status, visible, selection) VALUES ('Отсканировано', 'ACQUISITION, STORAGE, SCANNING, DIRECTOR', 'SCANNING');
INSERT INTO ar_status_record (status, visible, selection) VALUES ('Передано на загрузку', 'ACQUISITION, STORAGE, SCANNING, DIRECTOR, LOADING', 'SCANNING');

INSERT INTO ar_status_record (status, visible, selection) VALUES ('На доработке от отдела загрузки', 'ACQUISITION, STORAGE, SCANNING, LOADING, DIRECTOR', 'LOADING');
INSERT INTO ar_status_record (status, visible, selection) VALUES ('Загруженно', 'ACQUISITION, STORAGE, SCANNING, LOADING, DIRECTOR', 'LOADING');

INSERT INTO ar_status_record (status, visible, selection) VALUES ('На доработке от проверяющего', 'ACQUISITION, STORAGE, SCANNING, LOADING, DIRECTOR', 'DIRECTOR');
INSERT INTO ar_status_record (status, visible, selection) VALUES ('Проверенно', 'ACQUISITION, STORAGE, SCANNING, LOADING, DIRECTOR', 'DIRECTOR');

CREATE TABLE IF NOT EXISTS ar_status_inventory (
    id          bigserial           not null
        constraint ar_status_inventory_table_pk primary key,
    status VARCHAR(100) UNIQUE NOT NULL
);

INSERT INTO ar_status_inventory (status) VALUES ('Новая');
INSERT INTO ar_status_inventory (status) VALUES ('Дозагрузка');
INSERT INTO ar_status_inventory (status) VALUES ('Переработка');

CREATE TABLE IF NOT EXISTS ar_inventory
(
    id               bigserial    not null
        constraint ar_inventory_table_pk primary key,

    status_id bigint REFERENCES ar_status_record (id) ON UPDATE CASCADE ON DELETE RESTRICT, -- статус

--Отдел комплектования
    fond_id          bigint REFERENCES ar_fond (id),             -- фонд
    number_inventory VARCHAR(50) not null,                    -- номер описи
    status_inventory_id bigint REFERENCES ar_status_inventory (id) ON UPDATE CASCADE ON DELETE RESTRICT,                   -- статус описи
    date_income      date,                                    -- дата поступления
    numbers_case_from    integer not null,                -- номера дел с
    numbers_case_to    integer not null,                  -- номера дел по
    count_litter integer, -- количество литерныйх дел
    date_from        date         not null,                   -- дата с
    date_to          date         not null,                   -- дата по

-- Заведующий хранилищем
    count_sheets integer, -- количество листов

-- Отдел сканирования
    folder_scan VARCHAR(255),                    -- папка со сканами
    count_sheet_scan integer,                    -- количество скан листов
    count_pdf integer, -- количество pdf
    count_jpg integer, -- количество jpg

-- Отдел загрузки
    created_cards_case integer, -- Создано карточек дел

    created_at       timestamp             default current_timestamp,
    update_at        timestamp             default current_timestamp,
    deleted_at       timestamp,
    deleted          BOOLEAN               DEFAULT false
);

CREATE TABLE IF NOT EXISTS ar_case
(
    id               bigserial    not null
        constraint ar_case_table_pk primary key,
inventory_id bigint REFERENCES ar_inventory (id),

    status_id bigint REFERENCES ar_status_record (id) ON UPDATE CASCADE ON DELETE RESTRICT, -- статус

--Отдел комплектования
    date_income      date,                                    -- дата поступления
    numbers_case    integer not null,                -- номер дела
    numbers_storage    integer not null,                -- номер хранилища

-- Заведующий хранилищем
    count_sheets integer, -- количество листов

-- Отдел сканирования
count_doc integer, -- количество документов
sheet_usage integer,                    -- лист использования
sheet_witness integer,                    -- лист заверитель
    folder_scan VARCHAR(255),                    -- папка со сканами
    count_sheet_scan integer,                    -- количество скан листов
    count_pdf integer, -- количество pdf
    count_jpg integer, -- количество jpg

-- Отдел загрузки
    created_cards_case integer, -- Создано карточек дел
    load_sheet integer, -- Загруженно листов
    load_file integer, -- Загруженно файлов

    created_at       timestamp             default current_timestamp,
    update_at        timestamp             default current_timestamp,
    deleted_at       timestamp,
    deleted          BOOLEAN               DEFAULT false
);

CREATE TABLE IF NOT EXISTS ar_comments_inventory
(
    id          bigserial           not null
        constraint ar_comments_inventory_table_pk primary key,

    inventory_id bigint REFERENCES ar_inventory (id),

    text        VARCHAR(255) not null,
    author      bigint REFERENCES ar_accounts (id), -- автор
    department VARCHAR(255) not null,

    created_at  timestamp default current_timestamp,
    update_at   timestamp default current_timestamp,
    deleted_at  timestamp,
    deleted     BOOLEAN   DEFAULT false
);

CREATE TABLE IF NOT EXISTS ar_comments_case
(
    id          bigserial           not null
        constraint ar_comments_case_table_pk primary key,

    case_id bigint REFERENCES ar_case (id),

    text        VARCHAR(255) not null,
    author      bigint REFERENCES ar_accounts (id), -- автор
    department VARCHAR(255) not null,

    created_at  timestamp default current_timestamp,
    update_at   timestamp default current_timestamp,
    deleted_at  timestamp,
    deleted     BOOLEAN   DEFAULT false
);

CREATE TABLE IF NOT EXISTS ar_history_inventor
(
    id           bigserial not null
        constraint ar_history_inventor_table_pk primary key,

    inventory_id bigint REFERENCES ar_inventory (id),
    date_record         date      not null,
    status_id bigint REFERENCES ar_status_record (id) ON UPDATE CASCADE ON DELETE RESTRICT, -- статус
    text         VARCHAR(500),
    author       bigint REFERENCES ar_accounts (id)

);

CREATE TABLE IF NOT EXISTS ar_history_case
(
    id           bigserial not null
        constraint ar_history_case_table_pk primary key,

    case_id bigint REFERENCES ar_case (id),
    date_record         date      not null,
    status_id bigint REFERENCES ar_status_record (id) ON UPDATE CASCADE ON DELETE RESTRICT, -- статус
    text         VARCHAR(500),
    author       bigint REFERENCES ar_accounts (id)

);

-- INSERT INTO ar_fond (number_fond, name, author) VALUES (1, 'Фонд', 6);
-- INSERT INTO ar_users_roles (user_id, role_id)
-- VALUES (6, 1);
-- INSERT INTO ar_users_roles (user_id, role_id)
-- VALUES (6, 2);
-- INSERT INTO ar_users_roles (user_id, role_id)
-- VALUES (6, 3);
-- INSERT INTO ar_users_roles (user_id, role_id)
-- VALUES (6, 4);
-- INSERT INTO ar_users_roles (user_id, role_id)
-- VALUES (6, 5);