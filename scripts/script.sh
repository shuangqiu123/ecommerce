#!/bin/sh
java -Dspring.profiles.active=prod \
     -Dspring.datasource.url=$url \
     -Dspring.datasource.username=$username \
     -Dspring.datasource.password=$password \
     -Djwt.secret=$secret -cp app:app/lib/* $applicationName