# Proiect: Sistem de Management Bancar
**Disciplina:** Programare Avansata pe Obiecte (PAO)

---

## Lista cu actiuni / interogari posibile in sistem

Sistemul implementeaza un set de functionalitati de baza pentru gestionarea operatiunilor bancare, executabile prin intermediul aplicatiei demonstrative:

1. Inregistrare clienti noi (persoane fizice sau juridice)
2. Deschidere conturi noi pentru clienti (cont curent sau de economii)
3. Emitere carduri bancare (de debit sau credit)
4. Depunere numerar intr-un cont
5. Retragere numerar dintr-un cont
6. Transfer bancar intre conturi
7. Blocare / deblocare card
8. Afisare istoric tranzactii pentru un cont
9. Cautare clienti dupa CNP/CUI
10. Afisare conturi detinute de clienti
11. Calcul si aplicare dobanda pentru conturi de economii
12. Inchidere conturi si stergere clienti

---

## Lista cu tipuri de obiecte din domeniu

Arhitectura sistemului reflecta conceptele fundamentale ale Programarii Orientate pe Obiecte, utilizand urmatoarele entitati de domeniu:

- **Persoana** - clasa abstracta de baza pentru entitatile umane/fizice din sistem
- **Client** - interfata ce reprezinta un client generic al bancii
- **ClientFizic** - reprezentarea unei persoane fizice (asociaza un CNP)
- **ClientJuridic** - reprezentarea unei companii (asociaza un CUI)
- **Angajat** - reprezentarea personalului bancar
- **Cont** - structura ce gestioneaza balanta financiara si istoricul (valuta, sold, IBAN)
- **Card** - entitatea atasata unui cont bancar pentru acces rapid la fonduri
- **Tranzactie** - clasa imutabila ce reprezinta o miscare de fonduri
- **Adresa** - date de localizare pentru persoanele din sistem
- **Director** - clasa derivata din Angajat cu atributii suplimentare de management si buget

---

| Criteriu | Implementare (Detalii si Locatie Fisiere) |
| :--- | :--- |
| **README: 10 actiuni + 8 tipuri de obiecte** | **Actiuni (12)**: 1. Inregistrare clienti noi; 2. Deschidere conturi; 3. Emitere carduri; 4. Depunere numerar; 5. Retragere numerar; 6. Transfer bancar; 7. Blocare / deblocare card; 8. Afisare istoric tranzactii; 9. Cautare clienti dupa CNP/CUI; 10. Afisare conturi clienti; 11. Calcul dobanda; 12. Inchidere conturi si stergere clienti. Toate rulate in `Main.java`.<br>**Obiecte (10)**: `Adresa`, `Angajat`, `Card`, `Client`, `ClientFizic`, `ClientJuridic`, `Cont`, `Director`, `Persoana`, `Tranzactie`. Toate in `src/com/pao/proiect/banking/model/`. |
| **>=8 clase cu atribute private, getteri/setteri, toString** | Toate cele 10 clase din pachetul `model/` folosesc incapsulare (atribute private) si ofera getteri/setteri. Cele necesare suprascriu metoda `toString()`, iar clasele cheie (`Angajat`, `ClientFizic`, `ClientJuridic`) suprascriu si `equals()` / `hashCode()`. |
| **Ierarhie mostenire (>=2 niveluri) + clasa abstracta** | **Ierarhie clase (extends):** Nivel 0: `Persoana` (clasa abstracta in `model/Persoana.java`) -> Nivel 1: `Angajat` -> Nivel 2: `Director`.<br>**Interfata:** `Client` (interfata in `model/Client.java`), implementata de `ClientFizic` si `ClientJuridic`. |
| **Clasa imutabila + >=2 exceptii custom** | **Clasa Imutabila**: `Tranzactie.java` (campuri finale setate in constructor, clasa marcata cu `final`).<br>**Exceptii (4)**: `CardBlocatException.java`, `ClientNegasitException.java`, `ContNegasitException.java`, `SoldInsuficientException.java` in pachetul `exception/`. |
| **>=2 colectii diferite (una sortata) + >=1 Map** | **Map**: `LinkedHashMap` in cele 3 servicii.<br>**Colectie sortata**: `TreeSet` (sortare alfabetica in `ClientService.java`) si obiectele care implementeaza `Comparable` (`Tranzactie.java`).<br>**Colectie liniara**: `ArrayList` utilizat in `model/Cont.java`. |
| **>=2 servicii Singleton cu operatii CRUD** | `ClientService.java`, `ContService.java` si `AngajatService.java` din `service/` ascund constructorul (`private`) si expun datele via Singleton (`getInstance()`). |
| **Main demonstrativ care apeleaza cele 10 actiuni** | Fisierul `Main.java` testeaza secvential absolut toate cele 12 scenarii/actiuni descrise in lista de mai sus, cu date predefinite in memorie. |
| **Organizare in pachete, fara duplicat, fara NPE** | Sistemul e arhitecturat curat in: `model`, `service`, `exception`. Posibilele `NullPointerException` sunt prevenite prin logica puternica (ex. verificari de `null` in if-uri, aruncare validata si `try-catch` in Main). |
