DROP SCHEMA public CASCADE;
CREATE SCHEMA public;

CREATE TYPE EnumCoda AS ENUM(
    'famiglie', 
    'diversamenteAbili' , 
    'priority', 
    'business', 
    'economy'
);

CREATE TYPE EnumCitta AS ENUM(
    'Napoli',
    'Milano',
    'Londra',
    'Barcellona',
    'Dubai',
    'Cagliari',
    'Catania',
    'Firenze',
    'Amsterdam',
    'Mosca',
    'Madrid',
    'Zurigo',
    'Tenerife',
    'Berlino',
    'Edimburgo',
    'Nizza'
);

CREATE TYPE EnumImpiegati AS ENUM(
    'Amministratore',
    'TicketAgent',
    'AddettoCheckIn',
    'AddettoImbarco',
    'ResponsabileVoli'
);


CREATE TABLE Aeroporto(
    Codice VARCHAR(4) PRIMARY KEY CHECK (UPPER(Codice) = Codice), /*Codice aeroportuale ICAO*/
    Nome VARCHAR(30) NOT NULL,
    Citta EnumCitta NOT NULL
);
insert into Aeroporto values
                             ('LIRN', 'Capodichino', 'Napoli'),
                             ('LEBL', 'El Prat', 'Barcellona'),
                             ('EGLC', 'City', 'Londra'),
                             ('LIML', 'Linate', 'Milano'),
                             ('UUDD', 'Domodedovo', 'Mosca');

CREATE TABLE Gate(
    CodiceGate VARCHAR(4) PRIMARY KEY CHECK (UPPER(CodiceGate) = CodiceGate) /*tra lettere e numeri sono 36^4 gate possibili... Pattern standard: A1, B3, C20...*/
);
insert into Gate values
                        ('A0'), ('A1'), ('A2'), ('A3'), ('A4'), ('A5'),
                        ('B0'), ('B1'), ('B2'), ('B3'), ('B4'), ('B5'),
                        ('C0'), ('C1'), ('C2'), ('C3'), ('C4'), ('C5');


CREATE TABLE Compagnia(
    Nome VARCHAR(30) PRIMARY KEY, 
    Sigla VARCHAR(3) NOT NULL UNIQUE CHECK (UPPER(Sigla) = Sigla), /*Codice ICAO univoco per ogni compagnia. 26^3 = 17.576 (Per legge è possibile utilizzare anche numeri quindi non c'è bisogno di un vincolo) sicuramente superiore alle (circa) 500 compagnie esistenti nel 2021*/
    Nazione VARCHAR(30) NOT NULL, 
    PesoMassimo REAL NOT NULL CHECK (PesoMassimo > 0),
    PrezzoBagagli REAL NOT NULL CHECK (PrezzoBagagli > 0)
);
insert into Compagnia values
                             ('Vueling', 'VLG', 'Spagna', 32.0, 12.0),
                             ('Alitalia', 'AZA', 'Italia', 32.0, 12.0),
                             ('Easyjet', 'EZS', 'Svizzera', 32.0, 12.0),
                             ('Ryanair', 'RYR', 'Irlanda', 32.0, 12.0);


CREATE TABLE Risiede( /* si potrebbe anche eliminare...*/
    Compagnia VARCHAR(30) NOT NULL, 
    CodiceAeroporto VARCHAR(4) NOT NULL,
    CONSTRAINT fk_Compagnia FOREIGN KEY(Compagnia) REFERENCES Compagnia(Nome),
    CONSTRAINT fk_Aeroporto FOREIGN KEY(CodiceAeroporto) REFERENCES Aeroporto(Codice)
);


CREATE TABLE Aereo(
    CodiceAereo VARCHAR(8) PRIMARY KEY CHECK (UPPER(CodiceAereo) = CodiceAereo),
    Compagnia VARCHAR(30) NOT NULL,
    CONSTRAINT fk_Compagnia FOREIGN KEY(Compagnia) REFERENCES Compagnia(Nome)
);
insert into Aereo values
                         ('RUXZWJB9', 'Vueling'),
                         ('7YSNJ6XD', 'Alitalia'),
                         ('YT72ZY6U', 'Easyjet'),
                         ('LY5NNHJ7', 'Ryanair');

CREATE TABLE Tratta(
    NumeroVolo VARCHAR(8) PRIMARY KEY, 
    DataPartenza DATE NOT NULL, 
    OraPartenza TIME NOT NULL, 
    DurataVolo INT NOT NULL CHECK(DurataVolo > 0),
    Ritardo INT DEFAULT 0 CHECK(DurataVolo + Ritardo > 0), /*è possibile che l'aereo ci metta meno tempo del previsto e quindi il ritardo sia negativo*/
    CodiceAereo VARCHAR(8) NOT NULL, 
    Compagnia VARCHAR(30) NOT NULL, 
    AeroportoPartenza VARCHAR(4) NOT NULL, 
    AeroportoArrivo VARCHAR(4) NOT NULL,
    CONSTRAINT fk_Aereo FOREIGN KEY(CodiceAereo) REFERENCES Aereo(CodiceAereo),
    CONSTRAINT fk_Compagnia FOREIGN KEY(Compagnia) REFERENCES Compagnia(Nome),
    CONSTRAINT fk_AeroportoA FOREIGN KEY(AeroportoArrivo) REFERENCES Aeroporto(Codice),
    CONSTRAINT fk_AeroportoB FOREIGN KEY(AeroportoPartenza) REFERENCES Aeroporto(Codice)
);

insert into Tratta values
                          ('VLG87937', '2021-10-11', '6:30:00', 150, 5, 'RUXZWJB9', 'Vueling', 'LIRN', 'LEBL'),
                          ('RYRVU948', '2020-05-23', '18:00:00', 120, 10, 'LY5NNHJ7', 'Ryanair', 'EGLC', 'LIRN');

CREATE TABLE CodaImbarco(
    CodiceCoda VARCHAR(8) PRIMARY KEY,
    TempoStimato INT NOT NULL, 
    TempoEffettivo INT, 
    Classe EnumCoda  NOT NULL, 
    CodiceGate VARCHAR(4)  NOT NULL,
    NumeroVolo VARCHAR(8) NOT NULL,
    CONSTRAINT fk_CodiceGate FOREIGN KEY(CodiceGate) REFERENCES Gate(CodiceGate),
    CONSTRAINT fk_NumeroVolo FOREIGN KEY(NumeroVolo) REFERENCES Tratta(NumeroVolo)
);

/*
CREATE TABLE Cliente(
    CF VARCHAR(16) PRIMARY KEY, 
    Nome VARCHAR(30) NOT NULL, 
    Cognome VARCHAR(30) NOT NULL, 
    Carta VARCHAR(9) NOT NULL, 
    Passaporto VARCHAR(9),
    Email VARCHAR(30) NOT NULL, 
    Età INT NOT NULL
);

CREATE TABLE Biglietto(
    CodiceBiglietto VARCHAR(8) PRIMARY KEY, 
    Prezzo REAL NOT NULL, 
    Fila CHAR NOT NULL, 
    Posto INT NOT NULL, 
    Classe EnumCoda NOT NULL, // foreign key su code
    CheckIn BOOLEAN, 
    Imbarcato BOOLEAN,
    Numerovolo VARCHAR(8) NOT NULL,
    CF VARCHAR(16) NOT NULL,
    CONSTRAINT fk_NumeroVolo FOREIGN KEY(NumeroVolo) REFERENCES Tratta(NumeroVolo),
    CONSTRAINT fk_CF FOREIGN KEY(CF) REFERENCES Cliente(CF)
);

CREATE TABLE Bagaglio(
    CodiceBagaglio VARCHAR(8) PRIMARY KEY,
    Peso REAL NOT NULL, 
    Costo REAL NOT NULL, 
    Larghezza INT NOT NULL, 
    Altezza INT NOT NULL, 
    Profondità INT NOT NULL, 
    CodiceBiglietto VARCHAR(8) NOT NULL,
    CONSTRAINT fk_Biglietto FOREIGN KEY(CodiceBiglietto) REFERENCES Biglietto(CodiceBiglietto),
);

CREATE TABLE Sezione(
    CodiceSezione VARCHAR(10) PRIMARY KEY, 
    RangeFile VARCHAR(2) NOT NULL, 
    RangeColonne VARCHAR(2) NOT NULL, 
    Classe EnumCoda NOT NULL, 
    CodiceAereo VARCHAR(8) NOT NULL,
    CONSTRAINT fk_Aereo FOREIGN KEY(CodiceAereo) REFERENCES Aereo(CodiceAereo)
);

CREATE TABLE UscitaEmergenza(
    CodiceSezione VARCHAR(10) NOT NULL, 
    Fila CHAR NOT NULL,
    CONSTRAINT fk_Sezione FOREIGN KEY(CodiceSezione) REFERENCES Sezione(CodiceSezione)
);

CREATE TABLE Dipendente(
    CodiceImpiegato VARCHAR(8) PRIMARY KEY, 
    Nome VARCHAR(30) NOT NULL, 
    Cognome VARCHAR(30) NOT NULL, 
    Email VARCHAR(30) NOT NULL, 
    Password VARCHAR(30) NOT NULL, 
    Ruolo EnumImpiegati NOT NULL, 
    Compagnia VARCHAR(30) NOT NULL
    CONSTRAINT fk_Compagnia FOREIGN KEY(Compagnia) REFERENCES Compagnia(Nome)
); */