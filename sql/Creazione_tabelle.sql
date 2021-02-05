DROP SCHEMA PUBLIC CASCADE;
CREATE SCHEMA PUBLIC;

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
    /*...*/
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
                             ('UUDD', 'Domodedovo', 'Mosca'),
                             ('EHAM', 'Schiphol', 'Amsterdam'),
                             ('EDDB', 'Brandeburgo', 'Berlino'),
                             ('LIRQ', 'Peretola', 'Firenze');

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
                             ('Vueling', 'VLG', 'Spagna', 25.0, 1.0),
                             ('Alitalia', 'AZA', 'Italia', 35.0, 2.0),
                             ('Easyjet', 'EZS', 'Svizzera', 21.0, 0.7),
                             ('Ryanair', 'RYR', 'Irlanda', 26.0, 1.5),
                             ('LinuxFly', 'LXY', 'Finlandia', 32.0, 5.10),
                             ('AppleFly', 'OSX', 'Cupertino', 16.0, 20.0),
                             ('WindowsFly', 'WIN', 'Redmond', 64.0, 10.0),
                             ('JavaAirlines', 'JDK', 'Texas', 32.0, 15.0),
                             ('Lufthansa', 'DLH', 'Germania', 27.0, 1.5);


CREATE TABLE Tratta(
    NumeroVolo VARCHAR(8) PRIMARY KEY CHECK (UPPER(NumeroVolo) = NumeroVolo),
    DataPartenza DATE NOT NULL, 
    OraPartenza TIME NOT NULL, 
    DurataVolo INT NOT NULL CHECK(DurataVolo > 0),
    Ritardo INT DEFAULT 0 CHECK(DurataVolo + Ritardo > 0), /*è possibile che l'aereo ci metta meno tempo del previsto e quindi il ritardo sia negativo*/
    Conclusa BOOLEAN DEFAULT FALSE NOT NULL,
    CodiceGate VARCHAR(4),
    Compagnia VARCHAR(30) NOT NULL,
    AeroportoPartenza VARCHAR(4) NOT NULL, 
    AeroportoArrivo VARCHAR(4) NOT NULL,
    Posti INT NOT NULL,
    CONSTRAINT fk_Gate FOREIGN KEY(CodiceGate) REFERENCES Gate(CodiceGate),
    CONSTRAINT fk_Compagnia FOREIGN KEY(Compagnia) REFERENCES Compagnia(Nome),
    CONSTRAINT fk_AeroportoA FOREIGN KEY(AeroportoArrivo) REFERENCES Aeroporto(Codice),
    CONSTRAINT fk_AeroportoB FOREIGN KEY(AeroportoPartenza) REFERENCES Aeroporto(Codice)
);

ALTER TABLE Gate ADD CONSTRAINT fk_Tratta FOREIGN KEY(Tratta) REFERENCES Tratta(NumeroVolo);

insert into Tratta values
                          ('VLG87937', current_timestamp::DATE, '6:30:00', 150, 0, FALSE, NULL, 'Vueling', 'LIRN', 'LEBL', 100),
                          ('RYRVU948', current_timestamp::DATE, '7:30:00', 120, 0, false, NULL, 'Ryanair', 'EGLC', 'LIRN', 55),

                          ('JDKKROAN', current_timestamp::DATE, '8:18:00', 120, 0, false, NULL, 'JavaAirlines', 'LIRN', 'LEBL', 20),
                          ('JDKBYVWT', current_timestamp::DATE, '9:18:00', 120, 0, false, NULL, 'JavaAirlines', 'LIRN', 'EDDB', 3),
                          ('JDKAECMN', current_timestamp::DATE, '10:18:00', 120, 0, false, NULL, 'JavaAirlines', 'LIRN', 'UUDD', 100),

                          ('OSXHZ069', current_timestamp::DATE, '11:16:00', 120, 0, false, NULL, 'AppleFly', 'UUDD', 'LIRN', 50),
                          ('OSXXN1FX', current_timestamp::DATE, '12:44:00', 120, 0, false, NULL, 'AppleFly', 'EHAM', 'LIRN', 50),
                          ('OSX34Z7C', current_timestamp::DATE, '13:22:00', 120, 0, false, NULL, 'AppleFly', 'EGLC', 'LIRN', 50),

                          ('AZAS35V2', current_timestamp::DATE, '14:15:00', 120, 0, false, NULL, 'Alitalia', 'LIRN', 'LIML', 100),
                          ('AZAINPEF', current_timestamp::DATE, '15:40:00', 120, 0, false, NULL, 'Alitalia', 'LIRN', 'LIRQ', 100),
                          ('AZAB7TIX', current_timestamp::DATE, '16:00:00', 120, 0, false, NULL, 'Alitalia', 'LIRN', 'EGLC', 100),

                          ('RYR8X117', current_timestamp::DATE, '17:50:00', 120, 0, false, NULL, 'Ryanair', 'LIRN', 'EHAM', 70),
                          ('RYR6TCI7', current_timestamp::DATE, '18:05:00', 120, 0, false, NULL, 'Ryanair', 'LIRN', 'EDDB', 70),
                          ('RYR03208', current_timestamp::DATE, '19:18:00', 120, 0, false, NULL, 'Ryanair', 'LIRN', 'EGLC', 70),

                          ('WIN8MJNX', current_timestamp::DATE, '20:18:00', 120, 0, false, NULL, 'WindowsFly', 'LIML','LIRN',150),
                          ('WIN9P95M', current_timestamp::DATE, '21:18:00', 120, 0, false, NULL, 'WindowsFly', 'EDDB','LIRN',150);


CREATE TABLE CodaImbarco(
    CodiceCoda SERIAL PRIMARY KEY,
    OraApertura TIMESTAMP DEFAULT NULL,
    TempoStimato INT NOT NULL DEFAULT 0,
    TempoEffettivo INT DEFAULT NULL,
    Classe EnumCoda  NOT NULL,
    CodiceGate VARCHAR(4),
    NumeroVolo VARCHAR(8) NOT NULL,
    CONSTRAINT codaUnica UNIQUE(CodiceCoda, Classe),
    CONSTRAINT fk_CodiceGate FOREIGN KEY(CodiceGate) REFERENCES Gate(CodiceGate),
    CONSTRAINT fk_NumeroVolo FOREIGN KEY(NumeroVolo) REFERENCES Tratta(NumeroVolo)
);
insert into CodaImbarco (Classe, NumeroVolo) values ('DIVERSAMENTE_ABILI', 'JDKKROAN'), ('BUSINESS', 'JDKKROAN'), ('ECONOMY', 'JDKKROAN'),
                                                    ('DIVERSAMENTE_ABILI', 'JDKBYVWT'), ('ECONOMY', 'JDKBYVWT'),
                                                    ('DIVERSAMENTE_ABILI', 'JDKAECMN'), ('ECONOMY', 'JDKAECMN'),

                                                    ('DIVERSAMENTE_ABILI', 'AZAS35V2'), ('ECONOMY', 'AZAS35V2'),
                                                    ('DIVERSAMENTE_ABILI', 'AZAINPEF'), ('ECONOMY', 'AZAINPEF'),
                                                    ('DIVERSAMENTE_ABILI', 'AZAB7TIX'), ('ECONOMY', 'AZAB7TIX'),

                                                    ('DIVERSAMENTE_ABILI', 'RYR8X117'), ('ECONOMY', 'RYR8X117'),
                                                    ('DIVERSAMENTE_ABILI', 'RYR6TCI7'), ('ECONOMY', 'RYR6TCI7'),
                                                    ('DIVERSAMENTE_ABILI', 'RYR03208'), ('ECONOMY', 'RYR03208'),

                                                    ('DIVERSAMENTE_ABILI', 'VLG87937'), ('ECONOMY', 'VLG87937');

CREATE TABLE Biglietto(
    CodiceBiglietto SERIAL PRIMARY KEY, /*incrementa automaticamente*/
    Prezzo REAL NOT NULL,
    Posto INT NOT NULL,
    CheckIn BOOLEAN DEFAULT FALSE NOT NULL,
    Imbarcato BOOLEAN DEFAULT FALSE NOT NULL CHECK (NOT Imbarcato or CheckIn), /*imbarcato -> checkin*/
    Numerovolo VARCHAR(8) NOT NULL,
    NomeCliente VARCHAR(60) NOT NULL,
    Documento VARCHAR(9) NOT NULL,
    CodiceCoda INT NOT NULL,
    CF VARCHAR(16) CHECK (CF ~* '^[a-zA-Z]{6}[0-9]{2}[abcdehlmprstABCDEHLMPRST]{1}[0-9]{2}([a-zA-Z]{1}[0-9]{3})[a-zA-Z]{1}$'),
    CONSTRAINT fk_CodiceCoda FOREIGN KEY(CodiceCoda) REFERENCES CodaImbarco(CodiceCoda),
    CONSTRAINT fk_NumeroVolo FOREIGN KEY(NumeroVolo) REFERENCES Tratta(NumeroVolo)
);
CREATE TABLE Bagaglio(
    CodiceBagaglio VARCHAR(8) PRIMARY KEY,
    Peso REAL,
    Prezzo REAL,
    CodiceBiglietto INT NOT NULL,
    CONSTRAINT fk_Biglietto FOREIGN KEY(CodiceBiglietto) REFERENCES Biglietto(CodiceBiglietto)
);

CREATE OR REPLACE FUNCTION aggiornaStima() RETURNS TRIGGER AS $$
DECLARE
    media int;
    cl EnumCoda;
    tr tratta%rowtype;
    passeggeri int;
BEGIN
    SELECT c.Classe INTO cl FROM codaimbarco c WHERE c.codicecoda = new.CodiceCoda;
    select * into tr from tratta t where t.numerovolo = new.Numerovolo;
    select count(*) into passeggeri from biglietto b where b.codicecoda = new.CodiceCoda;

    WITH tempiEffettivi AS (
        SELECT c.CodiceCoda, c.TempoEffettivo/COUNT(*) as tPerPasseggero
        FROM CodaImbarco c
        JOIN Biglietto B on c.CodiceCoda = B.CodiceCoda
        JOIN Tratta t on c.NumeroVolo = t.NumeroVolo
        WHERE c.classe = cl
          AND t.aeroportoarrivo = tr.AeroportoArrivo
          AND t.compagnia = tr.Compagnia
          AND c.tempoeffettivo <> 0
        GROUP BY c.codicecoda
    )
    SELECT AVG(tmp.tPerPasseggero) into media FROM tempiEffettivi tmp;

    IF media IS NULL THEN
        media = 2;
    END IF;

    UPDATE codaimbarco c set TempoStimato = media*passeggeri  WHERE c.codicecoda = new.CodiceCoda;
    return NEW;
END;
$$ language 'plpgsql';

CREATE OR REPLACE FUNCTION to_seconds(t text) RETURNS integer AS
$BODY$
DECLARE
    hs INTEGER;
    ms INTEGER;
    s INTEGER;
BEGIN
    SELECT (EXTRACT(HOUR FROM  t::time) *60*60) INTO hs;
    SELECT (EXTRACT (MINUTES FROM t::time)*60) INTO ms;
    SELECT (EXTRACT (SECONDS from t::time)) INTO s;
    SELECT (hs + ms + s) INTO s;
    RETURN s;
END;
$BODY$
    LANGUAGE 'plpgsql';

CREATE OR REPLACE FUNCTION chiudiOApriCode() RETURNS TRIGGER AS $$
DECLARE
    clientiCheckin int;
    imbarcati int;
    secondi int;
BEGIN
    select count(*) into clientiCheckin from Biglietto b where b.CodiceCoda = new.codicecoda and b.CheckIn;
    select count(*) into imbarcati from Biglietto b where b.CodiceCoda = new.codicecoda and b.Imbarcato;
    IF imbarcati = 1 THEN
        UPDATE CodaImbarco set OraApertura = (now() at time zone 'CET') WHERE codicecoda = new.CodiceCoda;
    end if;
    IF imbarcati = clientiCheckin THEN
        SELECT to_seconds(cast((now() at time zone 'CET') as text)) - to_seconds(cast(c.OraApertura as text))
        into secondi
        from CodaImbarco c where c.CodiceCoda = new.CodiceCoda;
        UPDATE CodaImbarco set TempoEffettivo = secondi/60 WHERE codicecoda = new.CodiceCoda;
    end IF;
    return NEW;
END;
$$ language 'plpgsql';

CREATE TRIGGER onNewBiglietto AFTER INSERT ON Biglietto FOR EACH ROW EXECUTE PROCEDURE aggiornaStima();
CREATE TRIGGER onImbarco AFTER UPDATE OF Imbarcato ON Biglietto FOR EACH ROW WHEN (not OLD.Imbarcato and new.Imbarcato) EXECUTE PROCEDURE chiudiOApriCode();

CREATE OR REPLACE FUNCTION assegnaPosto() RETURNS TRIGGER AS $$
DECLARE
    mx int;
    posti int;
BEGIN
    select max(posto) into mx from Biglietto where numerovolo = new.Numerovolo;
    select t.posti into posti from tratta t where t.NumeroVolo = NEW.numeroVolo;

    IF mx IS NULL THEN
        NEW.Posto := 1;
    ELSEIF mx < posti THEN
        NEW.posto := mx + 1;
    ELSE
        RAISE EXCEPTION 'I posti sulla tratta sono terminati. Max posti: %', posti;
    END IF;
    RETURN NEW;
END;
$$ language 'plpgsql';

CREATE TRIGGER newBiglietto BEFORE INSERT ON Biglietto FOR EACH ROW EXECUTE PROCEDURE assegnaPosto();


CREATE TABLE Dipendente(
           CodiceImpiegato SERIAL PRIMARY KEY,
           Nome VARCHAR(30) NOT NULL,
           Cognome VARCHAR(30) NOT NULL,
           Email VARCHAR(40) NOT NULL CHECK (Email ~* '^[A-Za-z0-9._%-]+@[A-Za-z0-9.-]+[.][A-Za-z]+$'),
           Password VARCHAR(30) NOT NULL,
           Ruolo EnumImpiegati NOT NULL,
           Compagnia VARCHAR(30) CHECK (Compagnia IS NOT NULL or Ruolo = 'Amministratore'),
           CONSTRAINT fk_Company FOREIGN KEY(Compagnia) REFERENCES Compagnia(Nome)
);

insert into Dipendente (nome, cognome, email, password, ruolo, compagnia) values
            ('Matteo Richard', 'Gaudino', 'matteogaudino@goAirlines.com','password', 'Amministratore', null),
            ('Francesco', 'De Stasio', 'destasiofrancesco@goAirlines.com','password', 'Amministratore', null),

            ('Luca', 'Abete', 'lucabete@ryanair.com','password', 'Amministratore', 'Ryanair'),
            ('Johan', 'Bach', 'bach@ryanair.com','password', 'TicketAgent', 'Ryanair'),
            ('Frederich', 'Handel', 'handel@ryanair.com','password', 'AddettoCheckIn', 'Ryanair'),
            ('Isac', 'Asimov', 'asimov@ryanair.com','password', 'AddettoImbarco', 'Ryanair'),
            ('George', 'Orwell', 'orwell@ryanair.com','password', 'ResponsabileVoli', 'Ryanair'),

            ('Alan', 'Turing', 'alanturing@javaAirlines.com','password', 'Amministratore', 'JavaAirlines'),
            ('Kurt', 'Godel', 'kurt@javaAirlines.com','password', 'TicketAgent', 'JavaAirlines'),
            ('Gottlob', 'Frege', 'frege@javaAirlines.com','password', 'AddettoCheckIn', 'JavaAirlines'),
            ('Ludwig', 'Wittgenstein', 'ludwitt@javaAirlines.com','password', 'AddettoImbarco', 'JavaAirlines'),
            ('Friedrich', 'Gauss', 'gauss@javaAirlines.com','password', 'ResponsabileVoli', 'JavaAirlines'),

            ('David', 'Gilmour', 'pinkFloyd@vueling.com','password', 'Amministratore', 'Vueling'),
            ('Paolo', 'Rossi', 'paoloRossi@alitalia.it','password', 'Amministratore', 'Alitalia'),
            ('Jimmy', 'Page', 'ledZeppelin@easyjet.com','password', 'Amministratore', 'Easyjet'),
            ('Steve', 'Jobs', 'steve@apple.com','password', 'Amministratore', 'AppleFly'),
            ('Linus', 'Torvald', 'linus@linux.com','password', 'Amministratore', 'LinuxFly'),
            ('Bill', 'Gates', 'billGates@windows.com','password', 'Amministratore', 'WindowsFly'),
            ('Eren', 'Jaeger', 'eren@lufthansa.com','password', 'Amministratore', 'Lufthansa');


CREATE TABLE AeroportoGestito( /* l'aeroporto gestito è uno ed uno solo*/
    CodiceAeroporto VARCHAR(4) NOT NULL REFERENCES Aeroporto(Codice),
    constr BOOLEAN NOT NULL DEFAULT TRUE PRIMARY KEY CHECK(constr)
);
insert into AeroportoGestito values ('LIRN'); /*Aeroporto di napoli*/

CREATE OR REPLACE VIEW PasseggeriTotali(NumeroVolo, Passeggeri) AS
    SELECT t.numeroVolo, COUNT(*) AS Passeggeri
    FROM Biglietto b
    NATURAL JOIN Tratta t
    GROUP BY t.NumeroVolo;