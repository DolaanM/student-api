# Student API

REST API для управления студентами и группами.

## Требования

- Java 17+
- Maven 3+
- PostgreSQL
- Liquibase (встроен в проект)

---

## Настройка PostgreSQL

1. Создайте базу данных и пользователя:

```sql
CREATE DATABASE studentdb;
CREATE USER postgres WITH PASSWORD 'root';
GRANT ALL PRIVILEGES ON DATABASE studentdb TO postgres;
