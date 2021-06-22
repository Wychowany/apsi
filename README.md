# apsi

1. instalujemy dockera
2. instalujemy npm
3. wykonujemy skrypt backend/etc/scripts/copy_properties.sh
4. kopiujmey plik frontend/.env.development-sample na frontend/.env.development.local i ustawiamy zmienne srodowiskowe zgodnie ze swoim srodowiskiem - poki co nic nie trzeba robic
5. Odpalamy backend/etc/database/create_database.sh
6. Odpalamy backend/etc/database/start_database.sh
7. Odpalamy backend z Intellij z przycisku play, jak jest wszystko ok to nei będzie żadnych errorów
8. W katalogu frontend robimy: npm install
9. Dodajemy konfidugracje uruchomieniową dla NPM i wybieramy: command - run , scripts - dev
10. Odpalamy poprzez tą konfiguracje
11. Łączymy się z bazą poprzez skrypt backend/etc/database/baza.sh
12. Wykonujemy na bazie
    INSERT INTO application_users (creation_date, modification_date, version, accepted, active, confirmed, email, name,
    password, surname, system_role, deleted)
    VALUES (current_date, current_date, 0, true, true, true, 'aleksander.nuszel@gmail.com', 'Aleksander',
    '$2a$10$ldaPwd68M09cE418PfBzF.KIAeQboeSpR9MCzUaNVDpNeVY9DQ5gy', 'Nuszel', 'ADMINISTRATOR',
    false);
    
13. Wchodzimy na  http://localhost:3000/  i próbujemy się zalogować na: aleksander.nuszel@gmail.com 123456
