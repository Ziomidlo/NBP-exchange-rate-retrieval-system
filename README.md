# System Pobierania Kursów NBP - Zadanie Rekrutacyjne (Uniwersytet Gdański)

Aplikacja webowa zrealizowana w oparciu o architekturę Spring Boot, 
służąca do pobierania kursów walut z API NBP, 
przeliczania wartości sprzętu komputerowego, 
zapisu do bazy PostgreSQL oraz generowania pliku XML.

## Wykorzystane technologie
* **Java 21**
* **Spring Boot 4.1.0 (SNAPSHOT)** (Web, Data JPA)
* **PostgreSQL** (Baza danych)
* **Thymeleaf & HTML/CSS** (Warstwa widoku / UI)
* **Jackson/DOM** (Generowanie XML)

## Wymagania wstępne
Aby uruchomić aplikację, na komputerze muszą być zainstalowane:
1. Java Development Kit (JDK) 17 lub nowsze.
2. Maven.
3. PostgreSQL (działający lokalnie na porcie 5432).

## Instrukcja uruchomienia

### Krok 1: Konfiguracja Bazy Danych
1. Utwórz pustą bazę danych w PostgreSQL o nazwie `nbp_db` (lub innej preferowanej).
2. Otwórz plik `src/main/resources/application.properties`.
3. Zaktualizuj dane logowania do swojej lokalnej bazy danych:
   ```properties
   spring.datasource.url=jdbc:postgresql://localhost:5432/nbp_db
   spring.datasource.username=postgres
   spring.datasource.password=TWOJE_HASLO
   
### Krok 2: Uruchomienie aplikacji

Aplikację można uruchomić z poziomu środowiska IDE (np. IntelliJ / Eclipse) uruchamiając główną klasę DemoApplication.java lub za pomocą terminala komendą:
```properties
    mvn spring-boot:run 
```
### Funkcjonalności i użytkowanie

Po uruchomieniu aplikacji: 

* **Automatyczny Bootstrap (DataLoader):** Aplikacja automatycznie sprawdzi, czy baza jest pusta. Jeśli tak, pobierze kursy z API NBP dla 3 wymaganych komputerów, przeliczy koszt na PLN i zapisze je w bazie.

* **Generowanie XML:** Plik faktura.xml wygeneruje się automatycznie w głównym katalogu projektu podczas startu aplikacji.

* **Interfejs Webowy:** Otwórz przeglądarkę i wejdź pod adres: http://localhost:8080/

* **Wyszukiwarka:** Na stronie głównej znajduje się pole wyszukiwania, które pozwala na filtrowanie komputerów po nazwie (np. "ACER") - wyszukiwanie ignoruje wielkość liter (Case Insensitive).