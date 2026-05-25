DROP TABLE IF EXISTS tranzactie;
DROP TABLE IF EXISTS card;
DROP TABLE IF EXISTS cont;
DROP TABLE IF EXISTS director;
DROP TABLE IF EXISTS angajat;
DROP TABLE IF EXISTS client_juridic;
DROP TABLE IF EXISTS client_fizic;

CREATE TABLE client_fizic (
    id_client BIGINT AUTO_INCREMENT PRIMARY KEY,
    nume VARCHAR(100),
    prenume VARCHAR(100),
    cnp VARCHAR(13) UNIQUE,
    data_nasterii DATE,
    telefon VARCHAR(20),
    email VARCHAR(100),
    adresa VARCHAR(255),
    is_student BOOLEAN
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE cont (
    iban VARCHAR(34) PRIMARY KEY,
    sold DOUBLE,
    tip_cont VARCHAR(20),
    moneda VARCHAR(10),
    id_proprietar BIGINT,
    FOREIGN KEY (id_proprietar) REFERENCES client_fizic(id_client)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE card (
    numar_card VARCHAR(20) PRIMARY KEY,
    iban_cont VARCHAR(34),
    tip_card VARCHAR(20),
    stare VARCHAR(20),
    data_expirare VARCHAR(10),
    cvv VARCHAR(4),
    FOREIGN KEY (iban_cont) REFERENCES cont(iban)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE tranzactie (
    id_tranzactie VARCHAR(50) PRIMARY KEY,
    iban_sursa VARCHAR(34),
    iban_destinatie VARCHAR(34),
    suma DOUBLE,
    tip_tranzactie VARCHAR(30),
    data_ora TIMESTAMP,
    descriere VARCHAR(255)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE client_juridic (
    id_client BIGINT AUTO_INCREMENT PRIMARY KEY,
    nume_firma VARCHAR(150),
    tip_firma VARCHAR(30),
    cui VARCHAR(20) UNIQUE,
    domeniu_activitate VARCHAR(100),
    reprezentant_nume VARCHAR(100),
    reprezentant_prenume VARCHAR(100),
    telefon VARCHAR(20),
    email VARCHAR(100),
    adresa VARCHAR(255)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE angajat (
    id_angajat BIGINT AUTO_INCREMENT PRIMARY KEY,
    nume VARCHAR(100),
    prenume VARCHAR(100),
    cnp VARCHAR(13) UNIQUE,
    data_nasterii DATE,
    telefon VARCHAR(20),
    email VARCHAR(100),
    adresa VARCHAR(255),
    functie VARCHAR(100),
    salariu DOUBLE,
    departament VARCHAR(100)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE director (
    id_director BIGINT PRIMARY KEY,
    departament_condus VARCHAR(100),
    bonus_anual DOUBLE,
    FOREIGN KEY (id_director) REFERENCES angajat(id_angajat) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
