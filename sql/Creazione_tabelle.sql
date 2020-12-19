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
    NumeroVolo VARCHAR(8) PRIMARY KEY, 
    DataPartenza DATE NOT NULL, 
    DataArrivo DATE NOT NULL, 
    OraPartenza TIME NOT NULL, 
    OraArrivo TIME NOT NULL, 
    PartenzaEffettiva VARCHAR(16),  /*17:30-17-12-2077*/
    ArrivoEffettivo VARCHAR(16), 
    CodiceAereo VARCHAR(8)  NOT NULL, 
    Compagnia VARCHAR(30) NOT NULL, 
    AereoportoPartenza VARCHAR(30) NOT NULL, 
    AereoportoArrivo VARCHAR(30) NOT NULL,
    CONSTRAINT fk_Aereo FOREIGN KEY(CodiceAereo) REFERENCES Aereo(CodiceAereo),
    CONSTRAINT fk_Compagnia FOREIGN KEY(Compagnia) REFERENCES Compagnia(Nome),
    CONSTRAINT fk_AereoportoA FOREIGN KEY(AereoportoArrivo) REFERENCES Aereoporto(Codice),
    CONSTRAINT fk_AereoportoB FOREIGN KEY(AereoportoPartenza) REFERENCES Aereoporto(Codice)
);

CREATE TABLE Gate(
    CodiceGate VARCHAR(4) PRIMARY KEY /*tra lettere e numeri sono 35^4 gate possibili...*/
);

CREATE TABLE CodaImbarco(
    TempoStimato INT, 
    TempoEffettivo INT, 
    Classe EnumClassi  NOT NULL, 
    CodiceGate VARCHAR(4)  NOT NULL,
    NumeroVolo VARCHAR(8) NOT NULL,
    CONSTRAINT fk_CodiceGate FOREIGN KEY(CodiceGate) REFERENCES Gate(CodiceGate),
    CONSTRAINT fk_NumeroVolo FOREIGN KEY(NumeroVolo) REFERENCES Tratta(NumeroVolo)
);

CREATE TABLE Aereoporto(
    Codice VARCHAR(8) PRIMARY KEY,
    Nome VARCHAR(30) NOT NULL, 
    Citta EnumCitta NOT NULL, 
    Via VARCHAR(30) NOT NULL, 
    Piste INT NOT NULL
);

CREATE TABLE CompagniaAerea(
    Nome VARCHAR(30) PRIMARY KEY, 
    Sigla VARCHAR(3) NOT NULL, /*17000 combinazioni bastano considerando che al mondo ci sono sulle 500 compagnie*/
    Nazione VARCHAR(30) NOT NULL, 
    Tipo EnumCompagnia NOT NULL, 
    PesoMassimo REAL NOT NULL,
    MaxLarghezza INT NOT NULL, 
    MaxAltezza INT NOT NULL,
    MaxProfondità INT NOT NULL
);

CREATE TABLE Risiede(
    Compagnia VARCHAR(30) NOT NULL, 
    CodiceAereoporto VARCHAR(8) NOT NULL,
    CONSTRAINT fk_Compagnia FOREIGN KEY(Compagnia) REFERENCES Compagnia(Nome),
    CONSTRAINT fk_Aereoporto FOREIGN KEY(CodiceAereoporto) REFERENCES Aereoporto(Codice)
);

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
    Classe EnumClassi NOT NULL, 
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

CREATE TABLE Aereo(
    CodiceAereo VARCHAR(8) PRIMARY KEY, 
    Compagnia VARCHAR(30) NOT NULL
    CONSTRAINT fk_Compagnia FOREIGN KEY(CodiceAereo) REFERENCES Aereo(CodiceAereo),
);

CREATE TABLE Sezione(
    CodiceSezione VARCHAR(10) PRIMARY KEY, 
    RangeFile VARCHAR(2) NOT NULL, 
    RangeColonne VARCHAR(2) NOT NULL, 
    Classe EnumClassi NOT NULL, 
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
); 