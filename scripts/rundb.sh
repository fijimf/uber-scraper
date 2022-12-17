#!/bin/zsh

docker run --name deepfij-pgsql --net local-network -e POSTGRES_PASSWORD=p@ssw0rd  -p 5432:5432 postgres:15.1-alpine -c synchronous_commit=off -c default_transaction_isolation='read uncommitted'