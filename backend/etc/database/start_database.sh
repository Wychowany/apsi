#!/usr/bin/env bash

if docker start apsi-postgres ; then
	echo "Uruchomienie kontenera z postgresem na porcie 4000 zakończone sukcesem"

else
	echo "Brak kontenera z postgresem! Aby utworzyć kontener wykonaj skrypt create_database.sh"
fi
