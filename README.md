# Сервис обработки заявок (СТО)
О проекте
-

## Запуск проекта с помощью Docker

---------------------------------

## Запустите сервис:

docker-compose up --build -d


# Аутентификация и авторизация

Проект использует Spring Security с аутентификацией через JWT (Bearer token).

Предустановленные пользователи
При запуске автоматически создаются пользователи:

### Администратор (ADMIN):

- Логин: ``admin``

- Пароль: ``adminPassword123!``

### Менеджер (MANAGER):

- Логин: ``manager``

- Пароль: ``adminPassword123!``

Регистрация нового пользователя
Отправьте POST-запрос на http://localhost:8070/auth/register с телом:


    {
        "login": "user",
        "password": "userPassword",
        "name": "user_nickname",
        "mobile": "87078085060"
    }


# Работа с заявками

Получение токена

Отправьте POST-запрос на http://localhost:8070/auth/login с credentials пользователя

В ответе получите ```access_token``` для авторизации последующих запросов

## Создание заявки

Endpoint: POST http://localhost:8070/api/requests

Тело запроса:

    {
        "description": "Ремонт по кузову"
    }


Создает заявку со статусом NEW

Доступно всем аутентифицированным пользователям

## Изменение статуса заявки

Endpoint: PUT http://localhost:8070/api/requests/{ID}/status

Тело запроса:

статус из списка: [ NEW, ACCEPTED, PROCESSING, REPAIRING, DONE ]


    {
        "status": "NEW",
        "reason": "комментарий к изменению статуса"
    }


---------------------------------------------
Пример:

PUT http://localhost:8070/api/requests/1/status

    {
        "status": "NEW",
        "reason": "комментарий к изменению статуса"
    }


### Права доступа изменения статуса только для ролей: MANAGER и ADMIN

Заявка проходит через следующие статусы:
NEW → ACCEPTED → PROCESSING → REPAIRING → DONE

----------------

# Получение списка заявок

Endpoint: GET http://localhost:8070/api/requests/get-all 

Параметры запроса:  

``clientId`` - id клиента 
 
``status`` - статус заявки

 Пример запроса:  

http://localhost:8070/api/requests/get-all?clientId=5&status=DONE&size=2&page=1

Если ничего не указать просто получите весь список.
 
## Получить заявку по ID 

Endpoint: GET http://localhost:8070/api/requests/{ID} <br />

``ID`` - идентификационный номер заявки

-------------------------------------------------

# Получение истории заявок

 Endpoint: http://localhost:8070/api/status-history/get-all <br/>


Параметры запроса:

``requestId`` - id заявки

``changedById`` - кем изменено

```oldStatus``` - старый статус

``newStatus`` - новый статус

``start`` - дата, начиная с 

``end`` - дата

Пример запроса:  <br />

http://localhost:8070/api/status-history/get-all?requestId=6&oldStatus=NEW&changedById=2&size=10&page=0
