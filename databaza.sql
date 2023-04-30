CREATE DATABASE LEMS;

DROP DATABASE easyeval;
USE LEMS;

CREATE TABLE Students (
	S_ID INT NOT NULL,
    S_Name VARCHAR(50),
    S_Surname VARCHAR(50),
    S_Birthday Date,
    S_Email VARCHAR(100),
    S_Password VARCHAR(50),
    S_Phone VARCHAR(50),
    S_Adresa VARCHAR(50),
    S_GLevel INT,
    PRIMARY KEY (S_ID)
);

CREATE TABLE Teacher (
	T_ID INT NOT NULL,
    T_Name VARCHAR(50),
    T_Surname VARCHAR(50),
    T_Birthday Date,
    T_Email VARCHAR(100),
    T_Password VARCHAR(50),
    T_Phone VARCHAR(50),
    T_Adresa VARCHAR(50),
    PRIMARY KEY (T_ID)
);

CREATE TABLE Enrollment(
	E_ID INT NOT NULL,
    S_ID INT NOT NULL,
    T_ID INT NOT NULL,
    E_Date Date,
    PRIMARY KEY (E_ID),
    FOREIGN KEY (S_ID) REFERENCES Students(S_ID) ON DELETE CASCADE,
    FOREIGN KEY (T_ID) REFERENCES Teacher(T_ID) ON DELETE CASCADE
);

CREATE TABLE Courses(
	Course_ID INT NOT NULL,
    T_ID INT NOT NULL,
    C_Name VARCHAR(50),
    S_GLevel INT NOT NULL,
    PRIMARY KEY (Course_ID),
    FOREIGN KEY (T_ID) REFERENCES Teacher(T_ID) ON DELETE CASCADE
);

CREATE TABLE Assignments (
    A_ID INT NOT NULL,
    Course_ID INT NOT NULL,
    A_Name VARCHAR(255),
    A_Deadline DATE,
    A_MaxPoints INT,
    A_Weight FLOAT,
    PRIMARY KEY (A_ID),
    FOREIGN KEY (Course_ID) REFERENCES Courses (Course_ID)
);

CREATE TABLE Grades(
	G_ID INT NOT NULL,
    S_ID INT NOT NULL,
    A_ID INT NOT NULL,
    G_Value INT,
    PRIMARY KEY (G_ID),
    FOREIGN KEY (S_ID) REFERENCES Students(S_ID) ON DELETE CASCADE,
    FOREIGN KEY (A_ID) REFERENCES Assignments(A_ID) ON DELETE CASCADE
);

CREATE TABLE Attendance(
	Att_ID INT NOT NULL,
    S_ID INT NOT NULL,
    Course_ID INT NOT NULL,
	Att_Date Date,
    Att_Status BOOLEAN,
    PRIMARY KEY (Att_ID),
    FOREIGN KEY (S_ID) REFERENCES Students(S_ID) ON DELETE CASCADE
);
