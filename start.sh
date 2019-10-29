#!/bin/bash

mvn clean package spring-boot:repackage
cp target/cooking-app.jar docker/cooking-app.jar
docker-compose -f docker/docker-compose.yml up
rm docker/cooking-app.jar