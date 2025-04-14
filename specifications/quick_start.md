## Инструкция по запуску микросервисов через docker-compose

Для запуска микросервисов необходимо:

1. Добавить переменные среды окружения (файл .env в корне репозитория):

```
    CDR_DATABASE_URL=jdbc:h2:mem:testdb
    CDR_DATABASE_USERNAME=testdb
    CDR_DATABASE_PASSWORD=testdb
    
    BRT_DATABASE_DOCKER_HOST=billing-real-time-app-db
    BRT_DATABASE_NAME=brt-app
    BRT_DATABASE_USERNAME=brt-app
    BRT_DATABASE_PASSWORD=brt-app

    RABBIT_MQ_DOCKER_HOST=rabbitmq
    RABBIT_MQ_PORT=5672
    RABBIT_MQ_USERNAME=guest
    RABBIT_MQ_PASSWORD=guest
    RABBIT_MQ_QUEUE_NAME=message.queue
    RABBIT_MQ_EXCHANGE_NAME=message-exchange
    RABBIT_MQ_ROUTING_KEY=message-routing-key
```

2. Собрать jar-файлы для сборки докер-образов (через maven)

```
  mvn clean install
```

3. Запустить микросервисы

```
  docker compose up
```