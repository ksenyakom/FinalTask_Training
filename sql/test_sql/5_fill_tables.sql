USE `test_yoga_db`;

INSERT INTO `user`
    (`id`, `login`, `password`, `email`, `role`)
VALUES (2, "visitor1", "$2a$10$RA5aLuulm3YgXpEPpQKoU.NjsKybP5QKxY7ipqddMVeqPUDeOpr0u", "lexPopovich@mail.ru",
        2), /* bcrypt хэш пароля "visitor1" */
       (3, "visitor2", "$2a$10$kfQwDHqryMl1m6ueRqbLau0BOZifm7W5Di7LXgkCecGL3RjAg06HK", "dobrynya@mail.ru",
        2), /* bcrypt хэш пароля "visitor2" */
       (4, "trainer1", "$2a$10$mb9tYJzMQzzSiYOf9Out6.LDruh2qnNIViNASXFwrilJ2nioHKpgK", "ivan@mail.ru",
        1), /* bcrypt хэш пароля "trainer1" */
       (5, "trainer2", "$2a$10$OCLUi8aOjhON2wR/5D.uhueTiQcaMhEfuklwPzsFRvehJJZQvG8KG", "petr@gmai.com",
        1); /* bcrypt хэш пароля "trainer2" */

INSERT INTO `subscription`
    (`id`, `visitor_id`, `begin_date`, `end_date`, `price`)
VALUES (1, 2, "2020-10-02", "2021-03-01", 100),
       (2, 3, "2020-10-12", "2021-03-11", 100);

INSERT INTO `person`
(`id`, `surname`, `name`, `patronymic`, `date_of_birth`, `address`, `phone`, `achievements`)
VALUES (2, "Попович", "Алексей", "Леонтьевич", "1600-12-16",
        "деревня Селище д.1, Ростовского района Ярославской области, Русь", "+75555555555", "true"),
       (3, "Бесфамильный", "Добрыня", "Никитич", "1650-01-01", "г.Рязань д.1, Рязанского района, Русь", "+72222222222",
        "true"),
       (4, "Иванов", "Иван", "Иванович", "2006-09-11", "ул. Покровского, 13-3", "375291234567", "Мастер спорта"),
       (5, "Петров", "Пётр", "Петрович", "2008-12-15", "пр-т Будёного, 3А-43", "375292345678", "Победитель чемпионата");

INSERT INTO `exercise_type`
    (`id`, `type`)
VALUES (1, "common"),
       (2, "yoga");

INSERT INTO `exercise`
(`id`, `title`, `adjusting`, `mistakes`, `picture_path`, `audio_path`, `type_id`)
VALUES (1, "Сукхасана. Удобная поза.", "Тут длинный текст с описанием отстройки",
        "Тут длинный текст с описанием основных ошибок", "ссылка 1 на картинку позы", "ссылка на аудио", 2),
       (2, "Тадасана. Поза горы.", "Тут длинный текст с описанием отстройки",
        "Тут длинный текст с описанием основных ошибок", "ссылка 2 на картинку позы", "ссылка на аудио", 2),
       (3, "Врикшасана. Поза дерева.", "Тут длинный текст с описанием отстройки",
        "Тут длинный текст с описанием основных ошибок", "ссылка 3 на картинку позы", "ссылка на аудио", 2),
       (4, "Уткатасана. Поза стула.", "Тут длинный текст с описанием отстройки",
        "Тут длинный текст с описанием основных ошибок", "ссылка 4 на картинку позы", "ссылка на аудио", 2),
       (5, "Вирабхадрасана 1. Поза воина 1.", "Тут длинный текст с описанием отстройки",
        "Тут длинный текст с описанием основных ошибок", "ссылка 5 на картинку позы", "ссылка на аудио", 2),
       (6, "Шавасана. Поза трупа", "Тут длинный текст с описанием отстройки",
        "Тут длинный текст с описанием основных ошибок", "ссылка 6 на картинку позы", "ссылка на аудио", 2);

INSERT INTO `complex`
    (`id`, `title`, `trainer_id`, `visitor_id`, `rating`)
VALUES (1, "Общеукрепляющая тренировка", 4, null, 5),
       (2, "Фитнес", null, null, 4.5),
       (3, "Индивидуальная", 5, 2, 5);

INSERT INTO `exercise_in_complex`
    (`complex_id`, `serial_number`, `exercise_id`, `repeat`, `group`)
VALUES (1, 1, 1, 1, 0),
       (1, 2, 2, 1, 0),
       (1, 3, 3, 2, 0),
       (1, 4, 4, 2, 1),
       (1, 5, 5, 2, 1),
       (1, 6, 6, 1, 0);

INSERT INTO `assigned_trainer`
    (`id`, `trainer_id`, `visitor_id`, `begin_date`, `end_date`)
VALUES (1, 4, 2, "2020-10-01", null),
       (2, 5, 3, "2020-10-12", null);

INSERT INTO `assigned_complex`
    (`id`, `visitor_id`, `complex_id`, `date_expected`, `date_executed`)
VALUES (1, 2, 1, "2020-10-03", "2020-10-03"),
       (2, 2, 1, "2020-10-03", "2020-10-01"),
       (3, 2, 1, "2020-10-03", "2020-10-10"),
       (4, 2, 1, "2020-10-13", null),
       (5, 2, 1, "2020-10-20", null),
       (6, 3, 1, "2020-10-12", "2020-10-12"),
       (7, 3, 1, "2020-10-16", null),
       (8, 3, 1, "2020-10-20", null),
       (9, 3, 1, "2020-10-20", null),
       (10, 3, 1, "2020-10-20", "2020-01-12"),
       (11, 3, 1, "2020-10-20", "2020-02-12"),
       (12, 3, 1, "2020-10-20", "2020-03-12"),
       (13, 3, 1, "2020-10-20", "2020-04-12"),
       (14, 3, 1, "2020-10-20", "2020-05-12"),
       (15, 3, 1, "2020-10-20", "2020-06-12"),
       (16, 3, 1, "2020-10-20", "2020-07-12"),
       (17, 3, 1, "2020-10-20", "2020-08-12"),
       (18, 3, 1, "2020-10-20", "2020-09-12"),
       (19, 3, 1, "2020-10-20", "2020-09-15"),
       (20, 3, 1, "2020-10-20", "2020-10-12");

INSERT INTO `comment`
    (`id`, `user_id`, `complex_id`, `rating`, `content`, `date`)
VALUES (1, 2, 1, 5, "Отличная тренировка", "2020-10-05"),
       (2, 3, 1, 2, "Не понравилось", "2020-10-12");