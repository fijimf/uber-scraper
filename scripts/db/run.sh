#!/bin/zsh
docker build -t local-postgres-db ./

docker run --name deepfij-pgsql --net local-network -p 5432:5432 local-postgres-db -c synchronous_commit=off -c default_transaction_isolation='read uncommitted'

docker rm deepfij-pgsql