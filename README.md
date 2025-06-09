# Сервис обработки заявок (СТО)

## Запуск проекта с помощью Docker
---------------------------------
Соберите проект:
./mvnw clean package
-----------------------------
Запустите сервис:

docker-compose up --build -d


## Аутентификация и авторизация

Проект использует Spring Security с аутентификацией через JWT (Bearer token).

Предустановленные пользователи
При запуске автоматически создаются пользователи:

Администратор (ADMIN):

Логин: admin

Пароль: adminPassword123!

Менеджер (MANAGER):

Логин: manager

Пароль: adminPassword123!

Регистрация нового пользователя
Отправьте POST-запрос на http://localhost:8070/auth/register с телом:

{
    "login": "user",
    "password": "userPassword",
    "name": "user_nickname",
    "mobile": "87078085060"
}


Работа с заявками

Получение токена
Отправьте POST-запрос на http://localhost:8070/auth/login с credentials пользователя

В ответе получите access_token для авторизации последующих запросов

Создание заявки
Endpoint: POST http://localhost:8070/api/requests

Тело запроса:

{
    "description": "Ремонт по кузову"
}


Создает заявку со статусом NEW

Доступно всем аутентифицированным пользователям


Изменение статуса заявки

Endpoint: PATCH http://localhost:8070/api/requests/{ID}/status

Параметры:

status - новый статус из списка: [NEW, ACCEPTED, PROCESSING, REPAIRING, DONE]

reason - комментарий к изменению статуса

Пример:
http://localhost:8070/api/requests/1/status?status=ACCEPTED&reason=repair

Права доступа изменения статуса:
Только для ролей MANAGER и ADMIN

Жизненный цикл заявки
Заявка проходит через следующие статусы:
NEW → ACCEPTED → PROCESSING → REPAIRING → DONE
