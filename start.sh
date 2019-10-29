#!/bin/bash

mvn clean package spring-boot:repackage
docker-compose up