CREATE TYPE EnumClassi AS ENUM('famiglie', 
    'diversamenteAbili', 
    'priority', 
    'business', 
    'economy'
);

CREATE TYPE EnumCitta AS ENUM(
    'Napoli',
    'Milano',
    'Londra',
    'Barcellona',
    ...
);

CREATE TYPE EnumImpiegati AS ENUM(
    'Amministratore',
    'TicketAgent',
    'AddettoCheck-In',
    'AddettoImbarco',
    'ResponsabileVoli'
);

CREATE TYPE EnumCompagnia AS ENUM(
    'statale',
    'privata',
    'lowcost',
    'charter',
    'cargo'
);

CREATE TABLE Tratta(
    NumeroVolo VARCHAR(30) PRIMARY KEY, 
    DataPartenza DATE, 
    DataArrivo DATE, 
    OraPartenza TIME, 
    OraArrivo TIME, 
    PartenzaEffettiva VARCHAR(30), 
    ArrivoEffettivo VARCHAR(30), 
    CodiceAereo VARCHAR(30), 
    Compagnia VARCHAR(30), 
    AereoportoPartenza VARCHAR(30), 
    AereoportoArrivo VARCHAR(30)
);

CREATE TABLE Gate(
    CodiceGate VARCHAR(30) PRIMARY KEY
);

CREATE TABLE CodaImbarco(
    TempoStimato INT, 
    TempoEffettivo INT, 
    Classe EnumClassi, 
    CodiceGate VARCHAR(30),
    NumeroVolo VARCHAR(30)
);

CREATE TABLE Aereoporto(
    Codice VARCHAR(30) PRIMARY KEY, 
    Nome VARCHAR(30), 
    Citta EnumCitta, 
    Via VARCHAR(30), 
    Piste INT
);

CREATE TABLE CompagniaAerea(
    Nome VARCHAR(30) PRIMARY KEY, 
    Sigla VARCHAR(3),
    Nazione VARCHAR(30), 
    Tipo EnumCompagnia, 
    PesoMassimo REAL,
    MaxLarghezza INT, 
    MaxAltezza INT,
    MaxProfondità INT,
);

CREATE TABLE Risiede(
    Compagnia VARCHAR(30), 
    CodiceAereoporto VARCHAR(30)
);

CREATE TABLE Cliente(
    CF VARCHAR(30) PRIMARY KEY, 
    Nome VARCHAR(30), 
    Cognome VARCHAR(30), 
    Carta VARCHAR(30), 
    Passaporto VARCHAR(30),
    Email VARCHAR(30), 
    Età INT
);

CREATE TABLE Biglietto(
    CodiceBiglietto VARCHAR(30) PRIMARY KEY, 
    Prezzo REAL, 
    Fila CHAR, 
    Posto INT, 
    Classe EnumClassi, 
    CheckIn BOOLEAN, 
    Imbarcato BOOLEAN 
    NumeroVolo VARCHAR(30),
    CF VARCHAR(30)
);

CREATE TABLE Bagaglio(
    CodiceBagaglio VARCHAR(30) PRIMARY KEY,
    Peso REAL, 
    Costo REAL, 
    Larghezza INT, 
    Altezza INT, 
    Profondità INT, 
    CodiceBiglietto VARCHAR(30)
);

CREATE TABLE Aereo(
    CodiceAereo VARCHAR(30) PRIMARY KEY, 
    Compagnia VARCHAR(30)
);

CREATE TABLE Sezione(
    CodiceSezione VARCHAR(30) PRIMARY KEY, 
    RangeFile VARCHAR(2), 
    RangeColonne VARCHAR(2), 
    Classe EnumClassi, 
    CodiceAereo VARCHAR(30)
);

CREATE TABLE UscitaEmergenza(
    CodiceSezione VARCHAR(30), 
    Fila CHAR
);

CREATE TABLE Dipendente(
    CodiceImpiegato VARCHAR(30) PRIMARY KEY, 
    Nome VARCHAR(30), 
    Cognome VARCHAR(30), 
    Email VARCHAR(30), 
    Password VARCHAR(30), 
    Ruolo EnumImpiegati, 
    Compagnia VARCHAR(30)
);