#!/usr/bin/env bash
./gradlew clean build
#./gradlew bootBuildImage --imageName=poc/datasource
docker build -t poc/datasource .
docker-compose up -d