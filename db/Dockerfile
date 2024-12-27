FROM mysql:8.0

ENV MYSQL_ROOT_PASSWORD=nekipass123
ENV MYSQL_DATABASE=reservation_service

COPY init.sql /docker-entrypoint-initdb.d/


EXPOSE 3306
