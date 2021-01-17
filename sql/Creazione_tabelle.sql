
DROP SCHEMA public CASCADE;
CREATE SCHEMA public;

CREATE TYPE EnumCoda AS ENUM(
    'DIVERSAMENTE_ABILI',
    'FAMIGLIE',
    'BUSINESS',
    'PRIORITY',
    'ECONOMY'
);

CREATE TYPE GateStatus AS ENUM(
    'LIBERO',
    'OCCUPATO',
    'CHIUSO'
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
    CodiceGate VARCHAR(4) PRIMARY KEY CHECK (UPPER(CodiceGate) = CodiceGate), /*tra lettere e numeri sono 36^4 gate possibili... Pattern standard: A1, B3, C20...*/
    Stato GateStatus DEFAULT 'LIBERO' NOT NULL,
    Tratta VARCHAR(8)
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

CREATE TABLE Risiede( /* si potrebbe anche eliminare e dare per scontato che tutte le compagnie possano organizzare tratte per tutti gli aeroporti...*/
    Compagnia VARCHAR(30) NOT NULL, 
    CodiceAeroporto VARCHAR(4) NOT NULL,
    CONSTRAINT fk_Compagnia FOREIGN KEY(Compagnia) REFERENCES Compagnia(Nome),
    CONSTRAINT fk_Aeroporto FOREIGN KEY(CodiceAeroporto) REFERENCES Aeroporto(Codice)
);


CREATE TABLE Aereo(
    CodiceAereo VARCHAR(8) PRIMARY KEY CHECK (UPPER(CodiceAereo) = CodiceAereo),
    Compagnia VARCHAR(30) NOT NULL,
    File INT NOT NULL CHECK (File > 0),
    Colonne INT NOT NULL CHECK (File > 0),
    CONSTRAINT fk_Compagnia FOREIGN KEY(Compagnia) REFERENCES Compagnia(Nome)
);
insert into Aereo values
                         ('RUXZWJB9', 'Vueling', 26, 6),
                         ('7YSNJ6XD', 'Alitalia', 25, 6),
                         ('YT72ZY6U', 'Easyjet', 26, 6),
                         ('LY5NNHJ7', 'Ryanair', 33, 6);

CREATE TABLE Tratta(
    NumeroVolo VARCHAR(8) PRIMARY KEY CHECK (UPPER(NumeroVolo) = NumeroVolo),
    DataPartenza DATE NOT NULL, 
    OraPartenza TIME NOT NULL, 
    DurataVolo INT NOT NULL CHECK(DurataVolo > 0),
    Ritardo INT DEFAULT 0 CHECK(DurataVolo + Ritardo > 0), /*è possibile che l'aereo ci metta meno tempo del previsto e quindi il ritardo sia negativo*/
    Conclusa BOOLEAN DEFAULT FALSE NOT NULL,
    CodiceGate VARCHAR(4),
    CodiceAereo VARCHAR(8) NOT NULL CHECK (UPPER(CodiceAereo) = CodiceAereo),
    Compagnia VARCHAR(30) NOT NULL, 
    AeroportoPartenza VARCHAR(4) NOT NULL, 
    AeroportoArrivo VARCHAR(4) NOT NULL,
    CONSTRAINT fk_Gate FOREIGN KEY(CodiceGate) REFERENCES Gate(CodiceGate),
    CONSTRAINT fk_Aereo FOREIGN KEY(CodiceAereo) REFERENCES Aereo(CodiceAereo),
    CONSTRAINT fk_Compagnia FOREIGN KEY(Compagnia) REFERENCES Compagnia(Nome),
    CONSTRAINT fk_AeroportoA FOREIGN KEY(AeroportoArrivo) REFERENCES Aeroporto(Codice),
    CONSTRAINT fk_AeroportoB FOREIGN KEY(AeroportoPartenza) REFERENCES Aeroporto(Codice)
);

ALTER TABLE Gate ADD CONSTRAINT fk_Tratta FOREIGN KEY(Tratta) REFERENCES Tratta(NumeroVolo);

insert into Tratta values
                          ('VLG87937', '2021-10-11', '6:30:00', 150, 5, FALSE, NULL, 'RUXZWJB9', 'Vueling', 'LIRN', 'LEBL' ),
                          ('RYRVU948', '2020-05-23', '18:00:00', 120, 10, TRUE, 'A0', 'LY5NNHJ7', 'Ryanair', 'EGLC', 'LIRN'),
                          ('VLGUAZ84', '2021-01-05', '21:18:00', 111, 0, false, NULL, 'RUXZWJB9', 'Vueling', 'LIRN', 'LEBL');


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

CREATE TABLE Cliente(
      CF VARCHAR(16) PRIMARY KEY CHECK (CF ~* '^[a-zA-Z]{6}[0-9]{2}[abcdehlmprstABCDEHLMPRST]{1}[0-9]{2}([a-zA-Z]{1}[0-9]{3})[a-zA-Z]{1}$' ),
      Nome VARCHAR(30) NOT NULL,
      Cognome VARCHAR(30) NOT NULL,
      Carta VARCHAR(9) NOT NULL UNIQUE,
      Email VARCHAR(30) NOT NULL UNIQUE CHECK (Email ~* '^[A-Za-z0-9._%-]+@[A-Za-z0-9.-]+[.][A-Za-z]+$'),
      Eta INT NOT NULL
);

insert into Cliente values
                            ('GNNPNI00A01F839L', 'Francesco', 'De Stasio', 'F00f00f01', 'destasiofran1@libero.it', 20);

CREATE TABLE Biglietto(
    CodiceBiglietto VARCHAR(8) PRIMARY KEY,
    Prezzo REAL NOT NULL,
    Fila INT NOT NULL,
    Posto INT NOT NULL,
    Classe EnumCoda NOT NULL, /* foreign key su code */
    CheckIn BOOLEAN DEFAULT FALSE NOT NULL,
    Imbarcato BOOLEAN DEFAULT FALSE NOT NULL CHECK (NOT Imbarcato or CheckIn), /*imbarcato -> checkin*/
    Numerovolo VARCHAR(8) NOT NULL,
    CF VARCHAR(16) NOT NULL,
    CONSTRAINT fk_NumeroVolo FOREIGN KEY(NumeroVolo) REFERENCES Tratta(NumeroVolo),
    CONSTRAINT fk_CF FOREIGN KEY(CF) REFERENCES Cliente(CF),
    UNIQUE(Fila, Posto)
);

insert into Biglietto values
                             ('ABCDEFGH', 20, 1, 1, 'PRIORITY',FALSE, FALSE, 'VLG87937', 'DSTFNC00R06F839I');
insert into Biglietto values
('ABCDEFGA', 20, 1, 1, 'PRIORITY',TRUE, FALSE, 'VLG87937', 'DSTFNC00R06F839I');
insert into Biglietto values
('ABCDEFGB', 20, 1, 1, 'PRIORITY',TRUE, TRUE, 'VLG87937', 'DSTFNC00R06F839I');

CREATE TABLE Dipendente(
           CodiceImpiegato VARCHAR(8) PRIMARY KEY,
           Nome VARCHAR(30) NOT NULL,
           Cognome VARCHAR(30) NOT NULL,
           Email VARCHAR(30) NOT NULL CHECK (Email LIKE '^[\w-\.]+@([\w-]+\.)+[\w-]{2,4}$'),
           Password VARCHAR(30) NOT NULL,
           Ruolo EnumImpiegati NOT NULL,
           Compagnia VARCHAR(30) CHECK (Compagnia IS NOT NULL or Ruolo = 'Amministratore'),
           CONSTRAINT fk_Company FOREIGN KEY(Compagnia) REFERENCES Compagnia(Nome)
);

insert into Dipendente values
            ('1','Francesco', 'De Stasio', 'destasiofrancesco_@libero.it','password', 'Amministratore', 'Alitalia'),
            ('2','Matteo', 'Gaudino', 'matteogaudino_@libero.it','password', 'Amministratore', 'Alitalia'),
            ('3','Luca', 'Abete', 'lucabete@libero.it','password', 'TicketAgent', 'Ryanair');

CREATE TABLE AeroportoGestito( /* l'aeroporto gestito è uno ed uno solo*/
    CodiceAeroporto VARCHAR(4) NOT NULL REFERENCES Aeroporto(Codice),
    isSingleton BOOLEAN NOT NULL DEFAULT TRUE PRIMARY KEY CHECK(isSingleton)
);
insert into AeroportoGestito values ('LIRN'); /*Aeroporto di napoli*/

CREATE OR REPLACE VIEW PasseggeriTotali(NumeroVolo, Passeggeri) AS
    SELECT t.numeroVolo, COUNT(*) AS Passeggeri
    FROM Biglietto b
    NATURAL JOIN Tratta t
    GROUP BY t.NumeroVolo;

CREATE OR REPLACE VIEW PasseggeriCheckIn(NumeroVolo, PasseggeriCheckin) AS
    SELECT t.NumeroVolo, COUNT(*)
    FROM Biglietto b
    NATURAL JOIN Tratta t
    WHERE b.CheckIn
    GROUP BY t.NumeroVolo;

CREATE OR REPLACE VIEW PasseggeriImbarcati(NumeroVolo, PasseggeriImbarcati) AS
    SELECT t.NumeroVolo, COUNT(*)
    FROM Biglietto b
    NATURAL JOIN Tratta t
    WHERE b.imbarcato
    GROUP BY t.NumeroVolo;
/*

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

 */