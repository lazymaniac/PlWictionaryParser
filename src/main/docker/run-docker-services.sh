#!/bin/bash
docker-compose -f consul.yml up -d
docker-compose -f kafka.yml up -d
docker-compose -f mongodb.yml up -d
