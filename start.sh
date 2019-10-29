#!/bin/bash

mvn clean package spring-boot:repackage
docker-compose -f docker/docker-compose.yml up