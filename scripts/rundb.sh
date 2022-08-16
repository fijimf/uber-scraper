#!/bin/zsh

docker run -e POSTGRES_PASSWORD=p@ssw0rd  -p 5432:5432 postgres:13-alpine -c synchronous_commit=off -c default_transaction_isolation='read uncommitted'