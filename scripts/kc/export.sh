#!/bin/zsh

docker exec -it deepfij-kc \
  bash -c "export KC_DB=postgres && \
             export KC_DB_URL=jdbc:postgresql://deepfij-pgsql:5432/keycloak && \
             export KC_DB_USERNAME=postgres && \
             export KC_DB_PASSWORD=p@ssw0rd  && \
             /opt/keycloak/bin/kc.sh export --realm deepfij --file /tmp/kc.json && \
             cat /tmp/kc.json"