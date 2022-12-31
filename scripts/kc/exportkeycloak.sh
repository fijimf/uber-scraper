#!/bin/zsh

docker run --name export-deepfij-kc --net=local-network -p 8443:8080 \
        -e KEYCLOAK_ADMIN=fijimf -e KEYCLOAK_ADMIN_PASSWORD=mutombo \
        -e KC_DB=postgres \
        -e KC_DB_URL=jdbc:postgresql://deepfij-pgsql:5432/keycloak \
        -e KC_DB_USERNAME=postgres \
        -e KC_DB_PASSWORD=p@ssw0rd \
        quay.io/keycloak/keycloak:latest \
        export --realm=deepfij --file=/tmp/kc.json\

docker rm export-deepfij-kc

