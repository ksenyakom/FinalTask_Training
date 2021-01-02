USE `yoga_db`;

CREATE TABLE `user`
(
    `id`       INTEGER      NOT NULL AUTO_INCREMENT,
    `login`    VARCHAR(255) NOT NULL UNIQUE,
    `password` CHAR(64)     NOT NULL,
    `email`    VARCHAR(255) NOT NULL,

    /*
     * 0 - администратор (Role.ADMINISTRATOR)
     * 1 - тренер (Role.TRAINER)
     * 2 - посетитель (Role.VISITOR)
     */
    `role`     TINYINT      NOT NULL CHECK (`role` IN (0, 1, 2)),
    CONSTRAINT PK_user PRIMARY KEY (`id`),
    CONSTRAINT UC_user UNIQUE (`login`)
);

CREATE TABLE `subscription`
(
    `id`         INTEGER        NOT NULL AUTO_INCREMENT,
    `visitor_id` INTEGER,
    `begin_date` DATE           NOT NULL,
    `end_date`   DATE           NOT NULL,
    `price`      DECIMAL(10, 2) NULL,
    CONSTRAINT PK_subscription PRIMARY KEY (`id`),
    CONSTRAINT FK_subscription_visitor_id FOREIGN KEY (`visitor_id`) REFERENCES user (`id`)
        ON UPDATE CASCADE
        ON DELETE RESTRICT
);

CREATE TABLE `person`
(
    `id`            INTEGER,
    `surname`       VARCHAR(255) NULL,
    `name`          VARCHAR(255) NOT NULL,
    `patronymic`    VARCHAR(255) NULL,
    `date_of_birth` DATE         NULL,
    `address`       VARCHAR(255) NULL,
    `phone`         VARCHAR(20)  NULL, /*погуглить*/
    `achievements`  VARCHAR(500) NULL, /*для visitor true, false; для trainer - достижения String*/

    CONSTRAINT PK_person PRIMARY KEY (`id`),
    CONSTRAINT FK_person FOREIGN KEY (`id`) REFERENCES user (`id`)
        ON UPDATE CASCADE
        ON DELETE RESTRICT
);

CREATE TABLE `exercise_type`/* тип упраажнения Yoga, Common*/
(
    `id`   TINYINT NOT NULL AUTO_INCREMENT,
    `type` VARCHAR(100),
    CONSTRAINT PK_exercise_type PRIMARY KEY (`id`)
) ENGINE = INNODB;

CREATE TABLE `exercise`
(
    `id`           INTEGER      NOT NULL AUTO_INCREMENT,
    `title`        VARCHAR(255) NOT NULL,
    `adjusting`    TEXT         NOT NULL,
    `mistakes`     TEXT         NOT NULL,
    `picture_path` VARCHAR(255) NOT NULL, /* ссылка на картинку*/
    `audio_path`   VARCHAR(255) NOT NULL, /* ссылка на аудиофайл*/
    `type_id`      TINYINT      NULL,

    CONSTRAINT PK_exercise PRIMARY KEY (`id`),
    CONSTRAINT FK_exercise_type FOREIGN KEY (`type_id`) REFERENCES exercise_type (`id`)
        ON UPDATE CASCADE
        ON DELETE RESTRICT
) ENGINE = INNODB;



CREATE TABLE `complex` /* тренировки*/
(
    `id`         INTEGER      NOT NULL AUTO_INCREMENT,
    `title`      VARCHAR(255) NOT NULL UNIQUE, /* название тренировки*/
    `visitor_id` INTEGER      NULL, /* для какого visitor разработана, null - стандартная тренировка */
    `trainer_id` INTEGER      NULL, /* тренер разработавший тренировку*/
    `rating`     FLOAT, /* средний рейтинг тренировки*/

    CONSTRAINT PK_complex PRIMARY KEY (`id`),
    CONSTRAINT FK_complex_trainer_id FOREIGN KEY (`trainer_id`) REFERENCES user (`id`)
        ON UPDATE CASCADE
        ON DELETE RESTRICT,
    CONSTRAINT FK_complex_visitor_id FOREIGN KEY (`visitor_id`) REFERENCES user (`id`)
        ON UPDATE CASCADE
        ON DELETE RESTRICT
);

CREATE TABLE `exercise_in_complex`
(
    `complex_id`    INTEGER,
    `serial_number` INTEGER NOT NULL, /*порядковый номер упражнения в тренировке*/
    `exercise_id`   INTEGER NULL,
    `repeat`        TINYINT,
    `group`         TINYINT,

    CONSTRAINT PK_eic PRIMARY KEY (`complex_id`, `serial_number`),
    CONSTRAINT FK_eic_exercise_id FOREIGN KEY (`exercise_id`) REFERENCES exercise (`id`)
        ON UPDATE CASCADE
        ON DELETE RESTRICT,
    CONSTRAINT FK_eic_complex_id FOREIGN KEY (`complex_id`) REFERENCES complex (`id`)
        ON UPDATE CASCADE
        ON DELETE RESTRICT
);

/*
CREATE INDEX index_complex
    ON complex (`complex_id`, `serial_number`);
# бд сама создает такой индекс
*/
/*тренер закрепленный за посетителем*/
CREATE TABLE `assigned_trainer`
(
    `id`         INTEGER NOT NULL AUTO_INCREMENT,
    `trainer_id` INTEGER NOT NULL,
    `visitor_id` INTEGER NOT NULL,
    `begin_date` DATE    NOT NULL,
    `end_date`   DATE    NULL,

    CONSTRAINT PK_assigned_trainer PRIMARY KEY (`id`),
    CONSTRAINT FK_assigned_trainer_id FOREIGN KEY (`trainer_id`) REFERENCES user (`id`)
        ON UPDATE CASCADE
        ON DELETE RESTRICT,
    CONSTRAINT FK_assigned_visitor_id FOREIGN KEY (`visitor_id`) REFERENCES user (`id`)
        ON UPDATE CASCADE
        ON DELETE RESTRICT
);

/* журнал выполнений тренировок*/
CREATE TABLE `assigned_complex`
(
    `id`            INTEGER NOT NULL AUTO_INCREMENT,
    `visitor_id`    INTEGER NULL,
    `complex_id`    INTEGER NULL,
    `date_expected` DATE    NOT NULL,
    `date_executed` DATE,

    CONSTRAINT PK_assigned_complex PRIMARY KEY (`id`),
    CONSTRAINT FK_assigned_complex_id FOREIGN KEY (`complex_id`) REFERENCES complex (`id`)
        ON UPDATE CASCADE
        ON DELETE RESTRICT,
    CONSTRAINT FK_assigned_complex_visitor_id FOREIGN KEY (`visitor_id`) REFERENCES user (`id`)
        ON UPDATE CASCADE
        ON DELETE RESTRICT
);

/* отзывы о тренировках*/
CREATE TABLE `comment`
(
    `id`         INTEGER      NOT NULL AUTO_INCREMENT,
    `user_id`    INTEGER      NOT NULL,
    `complex_id` INTEGER      NOT NULL,
    `rating`     TINYINT      NOT NULL,
    `content`    VARCHAR(255) NOT NULL,
    `date`       DATE         NOT NULL,

    CONSTRAINT PK_comment PRIMARY KEY (`id`),
    CONSTRAINT FK_comment_user_id FOREIGN KEY (`user_id`) REFERENCES user (`id`)
        ON UPDATE CASCADE
        ON DELETE RESTRICT,
    CONSTRAINT FK_comment_complex_id FOREIGN KEY (`complex_id`) REFERENCES complex (`id`)
        ON UPDATE CASCADE
        ON DELETE RESTRICT
);