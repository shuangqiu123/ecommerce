#!/bin/sh
java -jar -Dspring.profiles.active=prod \
     -Dspring.datasource.url=$url \
     -Dspring.datasource.username=$username \
     -Dspring.datasource.password=$password \
     -Djwt.secret=$secret  app.jar