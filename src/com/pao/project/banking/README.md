# Proiect: Sistem de Management Bancar
**Disciplina:** Programare Avansata pe Obiecte (PAO)

---

## 1.1 Lista cu actiuni / interogari posibile in sistem

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

## 1.2 Lista cu tipuri de obiecte din domeniu

- **Persoana** - clasa abstracta de baza pentru entitatile umane/fizice din sistem
- **Client** - clasa abstracta derivata ce reprezinta un client generic al bancii
- **ClientFizic** - reprezentarea unei persoane fizice (asociaza un CNP)
- **ClientJuridic** - reprezentarea unei companii (asociaza un CUI)
- **Angajat** - reprezentarea personalului bancar
- **Cont** - structura ce gestioneaza balanta financiara si istoricul (valuta, sold, IBAN)
- **Card** - entitatea atasata unui cont bancar pentru acces rapid la fonduri
- **Tranzactie** - clasa imutabila ce reprezinta o miscare de fonduri
- **Adresa** - date de localizare pentru persoanele din sistem
