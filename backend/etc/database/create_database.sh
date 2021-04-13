#!/usr/bin/env bash

if docker create --name apsi-postgres -p 4000:5432 -e POSTGRES_DB=apsi -e POSTGRES_USER=apsi -e POSTGRES_PASSWORD=apsi postgres:11.1 ; then
	echo "Utworzony został kontener z postgresem! Aby go uruchomić wykonaj skrypt start_database.sh"
else
	echo "Kontener o nazwie apsi-postgres już istnieje! Usuń go za pomocą skryptu delete_database.sh"
fi
