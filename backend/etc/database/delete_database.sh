#!/usr/bin/env bash

if docker rm -f apsi-postgres; then
	echo "Kontener z postgresem został usunięty! Utwórz nowy kontener za pomoca skryptu delete_database.sh";
else
	echo "Kontener apsi-postgres nie jest uruchomiony!";
fi
