## Semestrální projekt z předmětu EAR
## Název aplikace
Aplikace pro agilní správu týmu

## Popis aplikace
Backend aplikace pro agilní tým. Aplikace umožňuje správu úkolů a sledování pokroku.
Základní funkce aplikace:
1. Správa projektů: Aplikace umožňuje vytvářet projekty a přidávat členy týmu do projektů. Každý projekt se skládá ze sprintů, které se skládají ze stories a ty zase z úkolů.
7. Správa uživatelů: Systém také zvládá správu uživatelů, přiřazuje různé role a oprávnění členům týmu na základě jejich odpovědností v týmu.
3. Plánování a sledování sprintů: Aplikace také zvládá sprinty, pomáhá týmu plánovat práci pro každý sprint a sledovat jejich pokrok.
2. Odhad náročnosti úkolů pomocí story points: Tato funkce umožňuje týmu odhadnout úsilí potřebné pro každý úkol pomocí story points, což podporuje efektivní přidělování zdrojů.
4. Správa stavu úkolů: Systém spravuje různé stavy úkolů, jako je READY, IN PROGRESS a DONE, a podporuje změny stavů.
6. Sledování času: Tato funkce umožňuje členům týmu zaznamenávat čas, který stráví na každém úkolu, což pomáhá při budoucích odhadech úkolů a správě pracovního zatížení.

## Struktura aplikace
Aplikace je rozdělena do několika balíčků:
- `controller` - obsahuje třídy, které zpracovávají http requesty a vytvářejí odpovědi
- `dao` - obsahuje třídy, které zajišťují komunikaci s databází, využívá JpaRepository
- `model` - obsahuje třídy, které reprezentují entity v databázi
- `config` - obsahuje třídy, které zajišťují konfiguraci autentizace a autorizace uživatelů
- `exceptions` - obsahuje třídy, které reprezentují výjimky, které mohou nastat při zpracování requestů
- `service` - obsahuje třídy, které zajišťují logiku aplikace
- `security` - obsahuje pomocné třídy pro autentizaci a autorizaci uživatelů
- `EarProjectApplication` - třída, která slouží pro spuštění aplikace

## Používání aplikace
Aplikace nedisponuje uživatelským rozhraním, ale je možné s ní komunikovat pomocí nástroje http requestů (např. Postman).
Sady kolecí pro Postman jsou k dispozici v adresáři `others/`. 

## Postman kolekce
Kolekce obsahuje několik sad requestů pro testování aplikace. Kolekce je dostupná v adresáři `src/test/resources/requests`.
první dělá blahblah
první dělá blahblah
první dělá blahblah


## Technologie
Aplikace je napsaná v jazyce Kotlin a využívá framework Spring Boot. Pro komunikaci s databází je použit framework Hibernate.
Pro testování aplikace je použit framework JUnit 5. Pro data je používána in-memory H2 databáze.

## Obohacení o tři techniky/funkce
1. Ordering (uspořádání kolekce podle atributu - @OrderBy)
Uspořádání je použito u tříd v modelu. Například jej můžete najít u třídy `Project`, která má kolekci `users` a je uspořádána pomocí jména.

2. Pojmenované dotazy (@NamedQuery)
Pojmenované dotazy v našem projektu nemají velké využití, protože používáme JPARepository, které má vlastní metody pro získání dat z databáze.
Ale přesto jsme je ho použili u třídy `User`, kde jsme vytvořili dotaz pro získání všech projektů uživatele ve funkci `findAllUsersProjects`.

3. Kaskádní persist/update/merge/remove
Kaskádní operace jsou použity u tříd v modelu. Například u třídy `Project`, která má kolekci `sprints` a je nastavena kaskáda pro všechny operace.


## Spuštění aplikace
Aplikaci je možné spustit pomocí příkazu `mvn spring-boot:run` v kořenovém adresáři aplikace.

## Autoři
Pavel Smiga a
Vít Nováček