#!/bin/zsh

docker run --name deepfij-kc --net=local-network -p 8080:8080 \
        -e KEYCLOAK_ADMIN=fijimf -e KEYCLOAK_ADMIN_PASSWORD=mutombo \
        quay.io/keycloak/keycloak:latest \
        start-dev \
        --db=postgres --features=token-exchange \
        --db-url=jdbc:postgresql://deepfij-pgsql:5432/postgres --db-username=postgres --db-password=p@ssw0rd --hostname=localhost
