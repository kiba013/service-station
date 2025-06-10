# Сервис обработки заявок (СТО)

## Запуск проекта с помощью Docker

---------------------------------

Соберите проект:<br />
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
<br />
{<br />
    "login": "user",<br />
    "password": "userPassword",<br />
    "name": "user_nickname",<br />
    "mobile": "87078085060"<br />
}<br />


# Работа с заявками

Получение токена<br />
Отправьте POST-запрос на http://localhost:8070/auth/login с credentials пользователя

В ответе получите access_token для авторизации последующих запросов

## Создание заявки
Endpoint: POST http://localhost:8070/api/requests<br />

Тело запроса:<br />

{<br />
    "description": "Ремонт по кузову"<br />
}<br />


Создает заявку со статусом NEW

Доступно всем аутентифицированным пользователям

# Изменение статуса заявки

Endpoint: PATCH http://localhost:8070/api/requests/{ID}/status

Тело запроса:<br />
статус из списка: [NEW, ACCEPTED, PROCESSING, REPAIRING, DONE]
<br />
{<br />
    "status": "NEW",<br />
    "reason": "комментарий к изменению статуса"<br />
}<br />


---------------------------------------------

Пример:
http://localhost:8070/api/requests/1/status<br />
<br />
{<br />
    "status": "NEW",<br />
    "reason": "комментарий к изменению статуса"<br />
}

## Права доступа изменения статуса:
## Только для ролей MANAGER и ADMIN

Жизненный цикл заявки
Заявка проходит через следующие статусы:
NEW → ACCEPTED → PROCESSING → REPAIRING → DONE

----------------

# Получение списка заявок

Endpoint: GET http://localhost:8070/api/requests/get-all <br />

Параметры запроса:  <br />

clientId - id клиента <br />
 
status - статус заявки

 <br />
 Пример запроса:  <br />

http://localhost:8070/api/requests/get-all?clientId=5&status=DONE&size=2&page=1

Если ничего не указать просто получите весь список.
 
## Получить заявку по ID <br />

Endpoint: GET http://localhost:8070/api/requests/{ID} <br />
ID - идентификационный номер заявки
