# Title: FinalTask_Training
# Название: Тренировки по йоге


### Роли пользователей: администратор, тренер, посетитель

**Администратор:** назначает тренера  посетителю, добавляет/редактирует тренировки, добавляет/редактирует упражнения в базе, редактирует  подписки посетителей.

**Тренер:** назначает тренировки посетителю, составляет расписание тренировок, разрабатывает индивидуальные тренировки, просматривает упражнения. 

**Посетитель:** покупает подписки, выполняет назначенные тренировки, может выбрать стандартные тренировку или запросить индивидуальную у тренера, добавить отзыв о тренировке, отказаться от упражнения, внести свои данные (вес, размеры) и отслеживать изменения.

Ведется журнал выполненных тренировок.


### Использованные технологии.

Приложение реализовано применяя технологии Servlet и JSP, при реализации страниц JSP используется JSTL, Bootstrap3; для валидации форм JavaScript. База данных MySQL. Технология доступа к БД – JDBC. При разработке тестов использована TestNJ. Ведется лог событий с помощью Log4J2. Технология Maven для сборки проекта.
Интерфейс локализован: RU|EN. 


### Схема базы данных.

![DB_Sheme](https://user-images.githubusercontent.com/61784810/106379595-b2819800-63b5-11eb-8047-ae8e39a9728d.png)


### Установка приложения.

Чтобы развернуть приложение, необходимо создать базу данных MySQL, используя скрипты в папке sql в корне проекта (в папке test_sql хранятся скрипты для создания тестовой бд). Разместить проект на сервере.
