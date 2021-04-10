#!/usr/bin/env bash

if docker stop apsi-postgres; then

	echo "Kontener z postgresem został zatrzymany";

else

	echo "Kontener apsi-postgres nie jest uruchomiony lub nie został utworzony!";

fi
