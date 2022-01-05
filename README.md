# cycling-route-service

docker run --name cycling-service-postgres -d -p 5432:5432 -e POSTGRES_PASSWORD=abc123 postgres

Onze postgres db heeft een probleem waardoor de user altijd eerst in de container aangemaakt moet worden
Hoe doet u dit:
1: Open Docker Desktop
2: Ga naar Containers / Apps
3: Klik op de cycling-service-postgres container
4: Klik op CLI rechtsboven (De > - ) knop
5: In de prompt die tevoorschijn komt geeft u volgende commando's in:
6: su - postgres
7: createuser --interactive --pwprompt
8: Nu vraagt hij een naam, typ: root
9: Nu vraagt hij een passwoord, typ: abc123
10: Herhaal het paswoord
11: Is de user een superuser? Typ: y
12: Nu is de user gemaakt en kan u zonder problemen de Java micro service runnen
