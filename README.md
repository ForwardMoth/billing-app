# Billing-app (Биллинговая система)

## Спецификация находится в процессе разработки!

## [Быстрый запуск](./specifications/quick_start.md)

## Структура репозитория

1. Все микросервисы находятся в директориях с суффиксом "app".
2. Директории с префиксом "lib" являются библиотечными
3. В директории settings находится checkstyle проекта
4. В директории specifications находится документация

## Структура микросервисов

Каждый микросервис имеет 2 части: 

- Директория с суффиксом "service", где содержится основная кодовая база
- Директория с префиксом "lib" является библиотечной для использования другими микросервисами

## Микросервисы: 

- [cdr-generator-app](./specifications/CDR_specification.md)
- [billing-real-time-app](./specifications/BRT_specification.md)

## Используемые технологии

- Java 17
- Spring Boot 3
- Rabbit MQ
- H2 
- PostgreSQL
- Liquibase
- Lombok
- Mapstruct
- Maven
- Docker & Docker-compose