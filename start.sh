#!/usr/bin/env bash

export DATABASE_CONNECTION_STRING="jdbc:postgresql://127.0.0.1:5432/parking_meter"
export DB_USER=admin
export DB_PASSWORD=admin
export JPA_GENERATE_DDL=false
export HIBERNATE_DDL_AUTO=validate
export CACHE_MAXIMUM_SIZE=10
export CACHE_TIME_IN_SECONDS=10

./gradlew clean bootRun