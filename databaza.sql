CREATE DATABASE LEMS;


USE LEMS;

CREATE TABLE Users(
	U_ID INT NOT NULL AUTO_INCREMENT,
    Email VARCHAR (100),
    Salted_Password VARCHAR(256),
    Salt VARCHAR(44) NOT NULL,
    U_Position VARCHAR (50),
	U_ProfileImg VARCHAR(50),
    PRIMARY KEY (U_ID)
);

CREATE TABLE Admins (
	A_ID INT NOT NULL AUTO_INCREMENT,
    A_Name VARCHAR(50),
    A_Surname VARCHAR(50),
    A_Phone VARCHAR(50),
    A_UID INT NOT NULL,
    PRIMARY KEY (A_ID),
    FOREIGN KEY (A_UID) REFERENCES Users(U_ID) ON DELETE CASCADE
);

CREATE TABLE Students (
	S_ID INT NOT NULL AUTO_INCREMENT,
    S_Name VARCHAR(50),
    S_Surname VARCHAR(50),
    S_Birthdate Date,
    S_Phone VARCHAR(50),
    S_Address VARCHAR(50),
    S_GLevel INT,
    S_UID INT NOT NULL,
    PRIMARY KEY (S_ID),
    FOREIGN KEY (S_UID) REFERENCES Users(U_ID) ON DELETE CASCADE
);


CREATE TABLE Teachers (
	T_ID INT NOT NULL AUTO_INCREMENT,
    T_Name VARCHAR(50),
    T_Surname VARCHAR(50),
    T_Birthdate Date,
    T_Phone VARCHAR(50),
    T_Address VARCHAR(50),
    T_UID INT NOT NULL,
    PRIMARY KEY (T_ID),
    FOREIGN KEY (T_UID) REFERENCES Users(U_ID) ON DELETE CASCADE
);
CREATE TABLE Subjects(
	Sb_ID INT NOT NULL auto_increment,
    Sb_Name VARCHAR(50),
    Sb_Description VARCHAR(2000),
    Sb_GLevel INT NOT NULL,
    PRIMARY KEY (Sb_ID)
);

CREATE TABLE Classes(
	C_ID INT NOT NULL auto_increment,
    T_ID INT NOT NULL,
    Sb_ID INT NOT NULL,
    PRIMARY KEY (C_ID),
    FOREIGN KEY (T_ID) REFERENCES Teachers(T_ID) ON DELETE CASCADE,
    FOREIGN KEY (Sb_ID) REFERENCES Subjects(Sb_ID) ON DELETE CASCADE
);

CREATE TABLE Enrollments(
	E_ID INT NOT NULL,
    S_ID INT NOT NULL,
    C_ID INT NOT NULL,
    E_Date Date,
    PRIMARY KEY (E_ID),
    FOREIGN KEY (S_ID) REFERENCES Students(S_ID) ON DELETE CASCADE,
    FOREIGN KEY (C_ID) REFERENCES Classes(C_ID) ON DELETE CASCADE
);



CREATE TABLE Grades(
	G_ID INT NOT NULL,
    S_ID INT NOT NULL,
    Sb_ID INT NOT NULL,
    G_Value INT,
    PRIMARY KEY (G_ID),
    FOREIGN KEY (S_ID) REFERENCES Students(S_ID) ON DELETE CASCADE,
	FOREIGN KEY (Sb_ID) REFERENCES Subjects(Sb_ID)
);