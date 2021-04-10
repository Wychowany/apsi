#!/usr/bin/env bash

# Skrypt pozwalający połączyć się z bazą danych postgresa na dockerze

docker exec -ti apsi-postgres sh -c "export PGPASSWORD=apsi & psql -h localhost -p 5432 -U apsi"
