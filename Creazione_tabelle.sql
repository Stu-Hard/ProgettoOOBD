CREATE TYPE EnumClassi AS ENUM('famiglie', 
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
    DataPartenza DATE NOT NULL, 
    DataArrivo DATE NOT NULL, 
    OraPartenza TIME NOT NULL, 
    OraArrivo TIME NOT NULL, 
    PartenzaEffettiva VARCHAR(30), 
    ArrivoEffettivo VARCHAR(30), 
    CodiceAereo VARCHAR(30)  NOT NULL, 
    Compagnia VARCHAR(30) NOT NULL, 
    AereoportoPartenza VARCHAR(30) NOT NULL, 
    AereoportoArrivo VARCHAR(30) NOT NULL
);

CREATE TABLE Gate(
    CodiceGate VARCHAR(30) PRIMARY KEY
);

CREATE TABLE CodaImbarco(
    TempoStimato INT  NOT NULL, 
    TempoEffettivo INT, 
    Classe EnumClassi  NOT NULL, 
    CodiceGate VARCHAR(30)  NOT NULL,
    NumeroVolo VARCHAR(30) NOT NULL
);

CREATE TABLE Aereoporto(
    Codice VARCHAR(30) PRIMARY KEY, 
    Nome VARCHAR(30) NOT NULL, 
    Citta EnumCitta NOT NULL, 
    Via VARCHAR(30), 
    Piste INT  NOT NULL
);

CREATE TABLE CompagniaAerea(
    Nome VARCHAR(30) PRIMARY KEY, 
    Sigla VARCHAR(3),
    Nazione VARCHAR(30) NOT NULL, 
    Tipo EnumCompagnia NOT NULL, 
    PesoMassimo REAL NOT NULL,
    MaxLarghezza INT NOT NULL, 
    MaxAltezza INT NOT NULL,
    MaxProfondità INT NOT NULL
);

CREATE TABLE Risiede(
    Compagnia VARCHAR(30), 
    CodiceAereoporto VARCHAR(30)
);

CREATE TABLE Cliente(
    CF VARCHAR(30) PRIMARY KEY, 
    Nome VARCHAR(30) NOT NULL, 
    Cognome VARCHAR(30) NOT NULL, 
    Carta VARCHAR(30) NOT NULL, 
    Passaporto VARCHAR(30),
    Email VARCHAR(30) NOT NULL, 
    Età INT NOT NULL
);

CREATE TABLE Biglietto(
    CodiceBiglietto VARCHAR(30) PRIMARY KEY, 
    Prezzo REAL NOT NULL, 
    Fila CHAR NOT NULL, 
    Posto INT NOT NULL, 
    Classe EnumClassi NOT NULL, 
    CheckIn BOOLEAN, 
    Imbarcato BOOLEAN,
    Numerovolo VARCHAR(30) NOT NULL,
    CF VARCHAR(30)
);

CREATE TABLE Bagaglio(
    CodiceBagaglio VARCHAR(30) PRIMARY KEY,
    Peso REAL NOT NULL, 
    Costo REAL NOT NULL, 
    Larghezza INT NOT NULL, 
    Altezza INT NOT NULL, 
    Profondità INT NOT NULL, 
    CodiceBiglietto VARCHAR(30) NOT NULL
);

CREATE TABLE Aereo(
    CodiceAereo VARCHAR(30) PRIMARY KEY, 
    Compagnia VARCHAR(30) NOT NULL
);

CREATE TABLE Sezione(
    CodiceSezione VARCHAR(30) PRIMARY KEY, 
    RangeFile VARCHAR(2) NOT NULL, 
    RangeColonne VARCHAR(2) NOT NULL, 
    Classe EnumClassi NOT NULL, 
    CodiceAereo VARCHAR(30) NOT NULL
);

CREATE TABLE UscitaEmergenza(
    CodiceSezione VARCHAR(30), 
    Fila CHAR
);

CREATE TABLE Dipendente(
    CodiceImpiegato VARCHAR(30) PRIMARY KEY, 
    Nome VARCHAR(30) NOT NULL, 
    Cognome VARCHAR(30) NOT NULL, 
    Email VARCHAR(30) NOT NULL, 
    Password VARCHAR(30) NOT NULL, 
    Ruolo EnumImpiegati NOT NULL, 
    Compagnia VARCHAR(30) NOT NULL
); 