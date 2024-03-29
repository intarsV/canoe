DROP ALL OBJECTS  DELETE FILES;

CREATE TABLE BOAT_CLASS(
ID BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
BOAT_CLASS VARCHAR(20),
CONSTRAINT UC_Boat_Class UNIQUE (BOAT_CLASS)
);

CREATE TABLE COUNTRY(
ID BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
COUNTRY VARCHAR (5),
CONSTRAINT UC_Country_Name UNIQUE (COUNTRY)
);

CREATE TABLE EVENT_FORMAT(
ID BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
EVENT_FORMAT VARCHAR (255),
CONSTRAINT UC_Event_Format UNIQUE (EVENT_FORMAT)
);

CREATE TABLE AGE_GROUP(
ID BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
AGE_GROUP VARCHAR (10),
DISABLED BOOLEAN,
CONSTRAINT UC_Age_Group UNIQUE (AGE_GROUP)
);

CREATE TABLE MCU_DATA(
ID BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
BIB INT,
TIME_STAMP BIGINT,
UNIT_ID INT,
SUB_EVENT INT,
DONE BOOLEAN,
DISABLED BOOLEAN,
CONSTRAINT  UC_Mcu_Controller UNIQUE (BIB, TIME_STAMP, UNIT_ID, SUB_EVENT, DISABLED)
);

CREATE TABLE SUB_EVENT(
ID BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
EVENT_FORMAT_ID BIGINT,
SUB_EVENT VARCHAR (50)
);

CREATE TABLE COMPETITOR(
ID BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
COMPETITOR_NAME VARCHAR (255),
BIRTH_YEAR INT,
CLUB VARCHAR (255),
COUNTRY VARCHAR (5),
DISABLED BOOLEAN,
CONSTRAINT UC_Competitor UNIQUE (COMPETITOR_NAME, BIRTH_YEAR)
);

CREATE TABLE EVENT(
ID BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
EVENT_NAME VARCHAR (255),
EVENT_FORMAT_ID BIGINT, FOREIGN KEY (EVENT_FORMAT_ID) REFERENCES EVENT_FORMAT(ID),
PLACE_DATE VARCHAR (255),
DISABLED BOOLEAN,
CONSTRAINT UC_Event_Name UNIQUE (EVENT_NAME)
);

CREATE TABLE EVENT_REGISTRY(
ID BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
EVENT_ID BIGINT, FOREIGN KEY (EVENT_ID) REFERENCES EVENT(ID),
COMPETITOR_ID BIGINT, FOREIGN KEY (COMPETITOR_ID) REFERENCES COMPETITOR(ID),
TEAM_MATES VARCHAR ,
GROUP_ID BIGINT, FOREIGN KEY (GROUP_ID) REFERENCES AGE_GROUP(ID),
BOAT_CLASS_ID BIGINT, FOREIGN KEY (BOAT_CLASS_ID) REFERENCES BOAT_CLASS(ID),
BIB INT,
TEAM_MODE BOOLEAN,
DISABLED BOOLEAN,
CONSTRAINT U_E_R UNIQUE (EVENT_ID, COMPETITOR_ID, BOAT_CLASS_ID, TEAM_MODE, DISABLED),
CONSTRAINT U_B UNIQUE (EVENT_ID, BIB, TEAM_MODE,DISABLED)
);

CREATE TABLE RACE_CONFIG(
ID BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
EVENT_ID BIGINT, FOREIGN KEY (EVENT_ID) REFERENCES EVENT(ID),
BOAT_CLASS_ID BIGINT, FOREIGN KEY (BOAT_CLASS_ID) REFERENCES BOAT_CLASS(ID),
HEAT_1 INT,
HEAT_2 INT,
SEMI_FINAL INT,
CONSTRAINT U_E_B UNIQUE (EVENT_ID, BOAT_CLASS_ID)
);

CREATE TABLE RACE(
ID BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
START_TIME BIGINT,
FINISH_TIME BIGINT,
G1 INT,
G2 INT,
G3 INT,
G4 INT,
G5 INT,
G6 INT,
G7 INT,
G8 INT,
G9 INT,
G10 INT,
G11 INT,
G12 INT,
G13 INT,
G14 INT,
G15 INT,
G16 INT,
G17 INT,
G18 INT,
G19 INT,
G20 INT,
G21 INT,
G22 INT,
G23 INT,
G24 INT,
EVENT_REGISTRY_ID BIGINT, FOREIGN KEY (EVENT_REGISTRY_ID) REFERENCES EVENT_REGISTRY(ID),
EVENT_ID BIGINT, FOREIGN KEY (EVENT_ID) REFERENCES EVENT(ID),
SUB_EVENT_ID BIGINT, FOREIGN KEY (SUB_EVENT_ID) REFERENCES SUB_EVENT(ID),
DSQR BOOLEAN,
DONE BOOLEAN,
TEAM_MODE BOOLEAN,
DISABLED BOOLEAN,
CONSTRAINT U_RA UNIQUE (EVENT_REGISTRY_ID, SUB_EVENT_ID, DISABLED)
);

INSERT INTO BOAT_CLASS VALUES(1,'C1W');
INSERT INTO BOAT_CLASS VALUES(2,'C1M');
INSERT INTO BOAT_CLASS VALUES(3,'K1W');
INSERT INTO BOAT_CLASS VALUES(4,'K1M');
INSERT INTO BOAT_CLASS VALUES(5,'XC2');

INSERT INTO AGE_GROUP VALUES(1, 'U-8', 0);
INSERT INTO AGE_GROUP VALUES(2, 'U-10', 0);
INSERT INTO AGE_GROUP VALUES(3, 'U-12', 0);
INSERT INTO AGE_GROUP VALUES(4, 'U-14', 0);
INSERT INTO AGE_GROUP VALUES(5, 'U-16', 0);
INSERT INTO AGE_GROUP VALUES(6, 'U-18', 0);

INSERT INTO COUNTRY VALUES (1, 'LVA');
INSERT INTO COUNTRY VALUES (2, 'LTU');
INSERT INTO COUNTRY VALUES (3, 'EST');

-- INSERT INTO MCU_DATA VALUES(1, 2, 1000, 1, 1, 0, 0);
-- INSERT INTO MCU_DATA VALUES(2, 3, 1800, 2, 1, 0, 0);
-- INSERT INTO MCU_DATA VALUES(3, 4, 1500, 1, 1, 0, 0);

INSERT INTO EVENT_FORMAT VALUES (1, 'H1&H2->best');
INSERT INTO EVENT_FORMAT VALUES (2, 'H1->H2->Final');
INSERT INTO EVENT_FORMAT VALUES (3, 'H1->H2->SF->Final');
INSERT INTO EVENT_FORMAT VALUES (4, 'Sum of H1&H2');
INSERT INTO EVENT_FORMAT VALUES (5, 'H1&&H2->best->Final');
INSERT INTO EVENT_FORMAT VALUES (6, 'Sum of H1&H2->Final');
-- INSERT INTO EVENT_FORMAT VALUES (7, 'H1->Final');

INSERT INTO EVENT VALUES (1, 'Latvijas čempis Format3', 3, 'Riga, 21.08.2020', false);
INSERT INTO EVENT VALUES (2, 'Jaunatnes čempis Format2', 2, 'Valmiera, 22.10.2020', false);
INSERT INTO EVENT VALUES (3, 'Latvijas čempis Format1', 1, 'Valmiera, 23.10.2020', false);
INSERT INTO EVENT VALUES (4, 'Latvijas čempis Format4', 4, 'Valmiera, 24.10.2020', false);
INSERT INTO EVENT VALUES (5, 'Latvijas čempis Format5', 5, 'Valmiera, 24.10.2020', false);
INSERT INTO EVENT VALUES (6, 'Latvijas čempis Format6', 6, 'Valmiera, 24.10.2020', false);

INSERT INTO SUB_EVENT VALUES (1, 3, 'Heat1');
INSERT INTO SUB_EVENT VALUES (2, 3, 'Heat2');
INSERT INTO SUB_EVENT VALUES (3, 3, 'Semi-final');
INSERT INTO SUB_EVENT VALUES (4, 3, 'Final');
INSERT INTO SUB_EVENT VALUES (5, 2, 'Heat1');
INSERT INTO SUB_EVENT VALUES (6, 2, 'Heat2');
INSERT INTO SUB_EVENT VALUES (7, 2, 'Final');
INSERT INTO SUB_EVENT VALUES (8, 1, 'Heat1');
INSERT INTO SUB_EVENT VALUES (9, 1, 'Heat2');
INSERT INTO SUB_EVENT VALUES (10, 4, 'Heat1');
INSERT INTO SUB_EVENT VALUES (11, 4, 'Heat2');
INSERT INTO SUB_EVENT VALUES (12, 5, 'Heat1');
INSERT INTO SUB_EVENT VALUES (13, 5, 'Heat2');
INSERT INTO SUB_EVENT VALUES (14, 5, 'Final');
INSERT INTO SUB_EVENT VALUES (15, 6, 'Heat1');
INSERT INTO SUB_EVENT VALUES (16, 6, 'Heat2');
INSERT INTO SUB_EVENT VALUES (17, 6, 'Final');

INSERT INTO COMPETITOR VALUES (1, 'Jānis Kļaviņš', 2000, 'VSA-VALMEIRA', 'LVA', 0);
INSERT INTO COMPETITOR VALUES (2, 'Anna Buša', 2001, 'VSA-VALMEIRA', 'LVA', 0);
INSERT INTO COMPETITOR VALUES (3, 'Andris Kalniņš', 2000, 'VSA-VALMEIRA', 'LVA', 0);
INSERT INTO COMPETITOR VALUES (4, 'Jānis Kalniņš', 2002, 'Baldone', 'LVA', 0);
INSERT INTO COMPETITOR VALUES (5, 'Emīls Tuncis', 2000, 'Straume', 'LTU', 0);
INSERT INTO COMPETITOR VALUES (6, 'Andris Bbb', 2000, 'Straume', 'LTU', 0);
INSERT INTO COMPETITOR VALUES (7, 'Liene Asbest', 2000, 'Liepaja', 'LTU', 0);
INSERT INTO COMPETITOR VALUES (8, 'Zane Liepa', 2000, 'Adaži', 'LTU', 0);
INSERT INTO COMPETITOR VALUES (9, 'Niks Plaudis', 2000, 'Adaži', 'LTU', 0);
INSERT INTO COMPETITOR VALUES (10, 'Žanis Ozlos', 2000, 'VBSS', 'LTU', 0);

--format3
INSERT INTO RACE_CONFIG VALUES (1, 1, 1, 2, 1, 2);
INSERT INTO RACE_CONFIG VALUES (2, 1, 2, 2, 1, 2);
--format2
INSERT INTO RACE_CONFIG VALUES (3, 2, 1, 2, 2, 0);
--format5
INSERT INTO RACE_CONFIG VALUES (4, 5, 1, 0, 2, 0);
INSERT INTO RACE_CONFIG VALUES (5, 5, 2, 0, 2, 0);
--format6
INSERT INTO RACE_CONFIG VALUES (6, 6, 1, 0, 2, 0);

-- format3
INSERT INTO EVENT_REGISTRY VALUES (1, 1, 1, '', 1, 1, 1, 0, 0);
INSERT INTO EVENT_REGISTRY VALUES (2, 1, 2, '', 1, 1, 2, 0, 0);
INSERT INTO EVENT_REGISTRY VALUES (3, 1, 3, '', 3, 1, 3, 0, 0);
INSERT INTO EVENT_REGISTRY VALUES (4, 1, 1, 'Andris Kalniņš, Emīls Tuncis', 1, 1, 1, 1, 0);
INSERT INTO EVENT_REGISTRY VALUES (5, 1, 6, '', 1, 2, 4, 0, 0);
INSERT INTO EVENT_REGISTRY VALUES (6, 1, 7, '', 1, 2, 5, 0, 0);
INSERT INTO EVENT_REGISTRY VALUES (7, 1, 8, '', 1, 2, 6, 0, 0);
INSERT INTO EVENT_REGISTRY VALUES (8, 1, 9, '', 3, 1, 7, 0, 0);
INSERT INTO EVENT_REGISTRY VALUES (9, 1, 10, '', 4, 1, 8, 0, 0);
-- format2
INSERT INTO EVENT_REGISTRY VALUES (10, 2, 1, '', 1, 1, 1, 0, 0);
INSERT INTO EVENT_REGISTRY VALUES (11, 2, 2, '', 1, 1, 2, 0, 0);
INSERT INTO EVENT_REGISTRY VALUES (12, 2, 3, '', 2, 1, 3, 0, 0);
INSERT INTO EVENT_REGISTRY VALUES (13, 2, 4, '', 2, 1, 4, 0, 0);
INSERT INTO EVENT_REGISTRY VALUES (14, 2, 5, '', 3, 1, 5, 0, 0);
INSERT INTO EVENT_REGISTRY VALUES (15, 2, 6, '', 4, 1, 6, 0, 0);
--format1
INSERT INTO EVENT_REGISTRY VALUES (16, 3, 1, '', 1, 1, 1, 0, 0);
INSERT INTO EVENT_REGISTRY VALUES (17, 3, 2, '', 1, 1, 2, 0, 0);
INSERT INTO EVENT_REGISTRY VALUES (18, 3, 3, '', 2, 1, 3, 0, 0);

INSERT INTO EVENT_REGISTRY VALUES (19, 3, 4, '', 1, 2, 4, 0, 0);
INSERT INTO EVENT_REGISTRY VALUES (20, 3, 5, '', 1, 2, 5, 0, 0);
INSERT INTO EVENT_REGISTRY VALUES (21, 3, 6, '', 2, 2, 6, 0, 0);
--format4
INSERT INTO EVENT_REGISTRY VALUES (22, 4, 1, '', 1, 1, 1, 0, 0);
INSERT INTO EVENT_REGISTRY VALUES (23, 4, 2, '', 1, 1, 2, 0, 0);
INSERT INTO EVENT_REGISTRY VALUES (24, 4, 3, '', 2, 1, 3, 0, 0);
--format5
INSERT INTO EVENT_REGISTRY VALUES (25, 5, 1, '', 1, 1, 1, 0, 0);
INSERT INTO EVENT_REGISTRY VALUES (26, 5, 2, '', 1, 1, 2, 0, 0);
INSERT INTO EVENT_REGISTRY VALUES (27, 5, 3, '', 2, 1, 3, 0, 0);

INSERT INTO EVENT_REGISTRY VALUES (28, 5, 4, '', 1, 2, 4, 0, 0);
INSERT INTO EVENT_REGISTRY VALUES (29, 5, 5, '', 1, 2, 5, 0, 0);
INSERT INTO EVENT_REGISTRY VALUES (30, 5, 6, '', 2, 2, 6, 0, 0);
--format6
INSERT INTO EVENT_REGISTRY VALUES (31, 6, 1, '', 1, 1, 1, 0, 0);
INSERT INTO EVENT_REGISTRY VALUES (32, 6, 2, '', 1, 1, 2, 0, 0);
INSERT INTO EVENT_REGISTRY VALUES (33, 6, 3, '', 2, 1, 3, 0, 0);

-- format3 race data
INSERT INTO RACE VALUES (1, 1000, 2000, 0,0,0,2,0,0,0,50,0,0,2,0,0,0,0,0,0,0,0,0,0,0,0,0, 1, 1, 1, 0, 0, 0, 0);
INSERT INTO RACE VALUES (2, 2000, 3020, 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0, 2, 1, 1, 0, 1, 0, 0);
INSERT INTO RACE VALUES (3, 3000, 4010, 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0, 3, 1, 1, 0, 1, 0, 0);
INSERT INTO RACE VALUES (4, 4000, 5070, 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0, 4, 1, 1, 0, 1, 1, 0);
INSERT INTO RACE VALUES (5, 1000, 2000, 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0, 5, 1, 1, 0, 1, 0, 0);
INSERT INTO RACE VALUES (6, 2000, 3023, 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0, 6, 1, 1, 0, 1, 0, 0);
INSERT INTO RACE VALUES (7, 3000, 4011, 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0, 7, 1, 1, 0, 1, 0, 0);
-- INSERT INTO RACE VALUES (8, 1000, 2002, 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0, 5, 1, 2, 0, 1, 0, 0);
INSERT INTO RACE VALUES (9, 2000, 3027, 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0, 6, 1, 2, 0, 1, 0, 0);
-- INSERT INTO RACE VALUES (10, 3000, 4015, 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0, 7, 1, 2, 0, 1, 0, 0);
INSERT INTO RACE VALUES (11, 1000, 2002, 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0, 1, 1, 2, 0, 1, 0, 0);
INSERT INTO RACE VALUES (12, 2000, 3027, 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0, 2, 1, 2, 0, 1, 0, 0);
INSERT INTO RACE VALUES (13, 3000, 0, 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0, 9, 1, 1, 0, 1, 0, 0);

INSERT INTO RACE VALUES (14, 1000, 2008, 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0, 1, 1, 3, 0, 1, 0, 0);
INSERT INTO RACE VALUES (15, 2000, 3022, 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0, 2, 1, 3, 0, 1, 0, 0);
INSERT INTO RACE VALUES (16, 3000, 4012, 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0, 3, 1, 3, 0, 1, 0, 0);

INSERT INTO RACE VALUES (17, 1000, 2023, 0,0,0,2,0,0,0,2,0,0,2,0,0,0,0,0,0,0,0,0,0,0,0,0, 1, 1, 4, 0, 1, 0, 0);
INSERT INTO RACE VALUES (18, 3000, 4010, 0,0,0,0,0,0,0,0,0,0,0,2,0,0,0,0,0,2,0,0,0,0,0,0, 3, 1, 4, 0, 1, 0, 0);

--format2
INSERT INTO RACE VALUES (19, 1000, 2000, 0,0,0,2,0,0,0,50,0,0,2,0,0,0,0,0,0,0,0,0,0,0,0,0, 10, 2, 1, 0, 1, 0, 0);
INSERT INTO RACE VALUES (20, 2000, 3020, 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0, 11, 2, 1, 0, 1, 0, 0);
INSERT INTO RACE VALUES (21, 3000, 4010, 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0, 12, 2, 1, 0, 1, 0, 0);
INSERT INTO RACE VALUES (22, 4000, 5070, 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0, 13, 2, 1, 0, 1, 0, 0);
INSERT INTO RACE VALUES (23, 1000, 2000, 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0, 14, 2, 1, 0, 1, 0, 0);
INSERT INTO RACE VALUES (24, 3000, 4011, 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0, 15, 2, 1, 0, 1, 0, 0);

INSERT INTO RACE VALUES (25, 4000, 5070, 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0, 10, 2, 2, 0, 1, 0, 0);
INSERT INTO RACE VALUES (26, 1000, 2000, 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0, 11, 2, 2, 0, 1, 0, 0);
INSERT INTO RACE VALUES (27, 3000, 4011, 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0, 13, 2, 2, 0, 1, 0, 0);

INSERT INTO RACE VALUES (28, 1000, 2030, 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0, 11, 2, 4, 0, 1, 0, 0);
INSERT INTO RACE VALUES (29, 3000, 4021, 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0, 13, 2, 4, 0, 1, 0, 0);
INSERT INTO RACE VALUES (30, 3000, 4010, 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0, 12, 2, 4, 0, 1, 0, 0);
INSERT INTO RACE VALUES (31, 1000, 2000, 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0, 14, 2, 4, 0, 1, 0, 0);
INSERT INTO RACE VALUES (32, 3000, 4081, 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0, 15, 2, 4, 0, 1, 0, 0);

--format1
INSERT INTO RACE VALUES (33, 1000, 2000, 0,0,0,2,0,0,0,50,0,0,2,0,0,0,0,0,0,0,0,0,0,0,0,0, 16, 3, 1, 0, 1, 0, 0);
INSERT INTO RACE VALUES (34, 2000, 3020, 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0, 17, 3, 1, 0, 1, 0, 0);
INSERT INTO RACE VALUES (35, 3000, 4070, 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0, 18, 3, 1, 0, 1, 0, 0);

INSERT INTO RACE VALUES (36, 1000, 2030, 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0, 16, 3, 2, 0, 1, 0, 0);
INSERT INTO RACE VALUES (37, 3000, 4051, 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0, 17, 3, 2, 0, 1, 0, 0);
-- INSERT INTO RACE VALUES (38, 3000, 4010, 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0, 18, 3, 2, 0, 1, 0, 0);

INSERT INTO RACE VALUES (39, 1000, 2000, 0,0,0,2,0,0,0,50,0,0,2,0,0,0,0,0,0,0,0,0,0,0,0,0, 19, 3, 1, 0, 1, 0, 0);
INSERT INTO RACE VALUES (40, 2000, 3020, 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0, 20, 3, 1, 0, 1, 0, 0);
INSERT INTO RACE VALUES (41, 3000, 4070, 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0, 21, 3, 1, 0, 1, 0, 0);

INSERT INTO RACE VALUES (42, 1000, 2030, 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0, 19, 3, 2, 0, 1, 0, 0);
INSERT INTO RACE VALUES (43, 3000, 4051, 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0, 20, 3, 2, 0, 1, 0, 0);
INSERT INTO RACE VALUES (44, 3000, 4010, 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0, 21, 3, 2, 0, 1, 0, 0);

--format4
INSERT INTO RACE VALUES (45, 1000, 2000, 0,0,0,2,0,0,0,50,0,0,2,0,0,0,0,0,0,0,0,0,0,0,0,0, 22, 4, 1, 0, 1, 0, 0);
INSERT INTO RACE VALUES (46, 2000, 3020, 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0, 23, 4, 1, 0, 1, 0, 0);
INSERT INTO RACE VALUES (47, 3000, 4070, 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0, 24, 4, 1, 0, 1, 0, 0);

INSERT INTO RACE VALUES (48, 1000, 2030, 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0, 22, 4, 2, 0, 1, 0, 0);
INSERT INTO RACE VALUES (49, 3000, 4051, 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0, 23, 4, 2, 0, 1, 0, 0);
INSERT INTO RACE VALUES (50, 3000, 4010, 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0, 24, 4, 2, 0, 1, 0, 0);

--format5
INSERT INTO RACE VALUES (51, 1000, 2000, 0,0,0,2,0,0,0,50,0,0,2,0,0,0,0,0,0,0,0,0,0,0,0,0, 25, 5, 1, 0, 1, 0, 0);
INSERT INTO RACE VALUES (52, 2000, 3020, 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0, 26, 5, 1, 0, 1, 0, 0);
INSERT INTO RACE VALUES (53, 3000, 4070, 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0, 27, 5, 1, 0, 1, 0, 0);

INSERT INTO RACE VALUES (54, 1000, 2030, 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0, 25, 5, 2, 0, 1, 0, 0);
INSERT INTO RACE VALUES (55, 3000, 4051, 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0, 26, 5, 2, 0, 1, 0, 0);
-- INSERT INTO RACE VALUES (56, 3000, 4010, 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0, 27, 5, 2, 0, 1, 0, 0);

INSERT INTO RACE VALUES (56, 1000, 2000, 0,0,0,2,0,0,0,50,0,0,2,0,0,0,0,0,0,0,0,0,0,0,0,0, 28, 5, 1, 0, 1, 0, 0);
INSERT INTO RACE VALUES (57, 2000, 3020, 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0, 29, 5, 1, 0, 1, 0, 0);
INSERT INTO RACE VALUES (58, 3000, 4070, 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0, 30, 5, 1, 0, 1, 0, 0);

INSERT INTO RACE VALUES (59, 1000, 2030, 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0, 28, 5, 2, 0, 1, 0, 0);
INSERT INTO RACE VALUES (60, 3000, 4051, 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0, 29, 5, 2, 0, 1, 0, 0);
INSERT INTO RACE VALUES (62, 3000, 4010, 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0, 30, 5, 2, 0, 1, 0, 0);

INSERT INTO RACE VALUES (63, 1000, 2030, 0,0,0,0,0,0,0,0,0,0,0,2,0,0,0,0,0,0,0,0,0,0,0,0, 29, 5, 4, 0, 1, 0, 0);
INSERT INTO RACE VALUES (65, 3000, 4010, 0,0,0,0,2,0,0,0,0,0,0,0,0,0,0,0,0,2,0,0,0,0,0,0, 30, 5, 4, 0, 1, 0, 0);

--format6
INSERT INTO RACE VALUES (66, 1000, 2000, 0,0,0,2,0,0,0,50,0,0,2,0,0,0,0,0,0,0,0,0,0,0,0,0, 31, 6, 1, 0, 1, 0, 0);
INSERT INTO RACE VALUES (67, 2000, 3020, 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0, 32, 6, 1, 0, 1, 0, 0);
INSERT INTO RACE VALUES (68, 3000, 4070, 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0, 33, 6, 1, 0, 1, 0, 0);

INSERT INTO RACE VALUES (69, 1000, 2000, 0,0,0,2,0,0,0,50,0,0,2,0,0,0,0,0,0,0,0,0,0,0,0,0, 31, 6, 2, 0, 1, 0, 0);
INSERT INTO RACE VALUES (70, 2000, 3020, 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0, 32, 6, 2, 0, 1, 0, 0);
INSERT INTO RACE VALUES (72, 3000, 4085, 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0, 33, 6, 2, 0, 1, 0, 0);

INSERT INTO RACE VALUES (73, 2000, 3020, 0,0,0,0,0,0,0,0,0,0,0,0,0,2,0,0,0,0,0,2,0,0,0,0, 32, 6, 4, 0, 1, 0, 0);
INSERT INTO RACE VALUES (74, 3000, 4085, 0,0,0,0,0,0,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0, 33, 6, 4, 0, 1, 0, 0);