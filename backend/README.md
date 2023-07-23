## Aplikacja backend
#### STACK:
- Java 17
- Spring Boot WebFlux 3.1.1
- Spring Security
- Swagger
- PostgreSQL
- Docker

Konfiguracja bazy w własnym zakresie, czyli konfiguracja serwera bazy oraz utworzenie bazy danych z skryptu `src/main/resources/schema.sql`  

Konfiguracja połączenia aplikacji z bazą danych jest po przez adres url serwera postgres zapisana w zmiennej środowikowej ``DB_URL``
Przykładowa wartość zmiennej w postaci adresu url do połączenia z serwerem baz danych postgresql:  
```
r2dbc:postgresql://postgres:postgres@127.0.0.1:5432/autofleet
```  

Do w plikach źródłowych znajduję się przygotowany plik docker file który po skompilowaniu aplikajci utworzy gotowy obraz aplikacji
Obraz dostępny również na [dockerhub](https://hub.docker.com/repository/docker/grabcio/autofleet-backend/general)  
Uruchomienie aplikacji z obrazu docker:
```
docker run -p 8080:8080 --name autofleet -e DB_URL=r2dbc:postgresql://<username>:<password>@<address>:<port>/<database> grabcio/autofleet-backend:latest
```

Dostępna dokumentacja API z wykorzystaniem Swagger UI pod adresem (wyamgane aby aplikacja została uruchomiona): 
```
http://localhost:8080/webjars/swagger-ui/index.html
```