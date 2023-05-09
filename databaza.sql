CREATE DATABASE LEMS;

-- DROP DATABASE LEMS;

USE LEMS;

CREATE TABLE Users(
	U_ID INT NOT NULL AUTO_INCREMENT,
    Email VARCHAR (100),
    Salted_Password VARCHAR(256),
    Salt VARCHAR(44) NOT NULL,
    U_Position VARCHAR (50),
	U_ProfileImg VARCHAR(100),
    PRIMARY KEY (U_ID)
);

-- SELECT * FROM Users

CREATE TABLE Admins (
    A_Name VARCHAR(50),
    A_Surname VARCHAR(50),
    A_Phone VARCHAR(50),
    A_UID INT NOT NULL,
    PRIMARY KEY (A_UID),
    FOREIGN KEY (A_UID) REFERENCES Users(U_ID) ON DELETE CASCADE
);

-- SELECT * FROM Admins
drop table Students;
CREATE TABLE Students (
    S_Name VARCHAR(50),
    S_Surname VARCHAR(50),
    S_Birthdate Date,
    S_Phone VARCHAR(50),
    S_Address VARCHAR(50),
    S_GLevel INT,
    S_UID INT NOT NULL,
    PRIMARY KEY (S_UID),
    FOREIGN KEY (S_UID) REFERENCES Users(U_ID) ON DELETE CASCADE
);

-- SELECT * FROM Students
drop table teachers;
CREATE TABLE Teachers (
    T_Name VARCHAR(50),
    T_Surname VARCHAR(50),
    T_Birthdate Date,
    T_Phone VARCHAR(50),
    T_Address VARCHAR(50),
    T_UID INT NOT NULL,
    PRIMARY KEY (T_UID),
    FOREIGN KEY (T_UID) REFERENCES Users(U_ID) ON DELETE CASCADE
);

-- SELECT * FROM Teachers

CREATE TABLE Subjects(
	Sb_ID INT NOT NULL AUTO_INCREMENT,
    Sb_Name VARCHAR(50),
    Sb_Description VARCHAR(2000),
    Sb_GLevel INT NOT NULL,
    PRIMARY KEY (Sb_ID)
);

-- SELECT * FROM Subjects
drop table classes;
CREATE TABLE Classes (
	C_ID INT NOT NULL AUTO_INCREMENT,
    T_ID INT NOT NULL,
    Sb_ID INT NOT NULL,
    PRIMARY KEY (C_ID),
    FOREIGN KEY (T_ID) REFERENCES Teachers(T_UID) ON DELETE CASCADE,
    FOREIGN KEY (Sb_ID) REFERENCES Subjects(Sb_ID) ON DELETE CASCADE
);

-- SELECT * FROM Classes
drop table enrollments;
CREATE TABLE Enrollments (
	E_ID INT NOT NULL AUTO_INCREMENT,
    S_ID INT NOT NULL,
    C_ID INT NOT NULL,
    E_Date Date,
    PRIMARY KEY (E_ID),
    FOREIGN KEY (S_ID) REFERENCES Students(S_UID) ON DELETE CASCADE,
    FOREIGN KEY (C_ID) REFERENCES Classes(C_ID) ON DELETE CASCADE
);

-- SELECT * FROM Enrollments
drop table grades;
CREATE TABLE Grades (
	G_ID INT NOT NULL AUTO_INCREMENT,
    S_ID INT NOT NULL,
    Sb_ID INT NOT NULL,
    G_Value INT,
    PRIMARY KEY (G_ID),
    FOREIGN KEY (S_ID) REFERENCES Students(S_UID) ON DELETE CASCADE,
	FOREIGN KEY (Sb_ID) REFERENCES Subjects(Sb_ID)
);

-- SELECT * FROM Grades


-- Insert 5 rows into Users table
INSERT INTO Users (Email, Salted_Password, Salt, U_Position, U_ProfileImg)
VALUES
    ('teacher1@example.com', 'password1', 'salt1', 'Teacher', 'teacher1.jpg'),
    ('teacher2@example.com', 'password2', 'salt2', 'Teacher', 'teacher2.jpg'),
    ('student1@example.com', 'password3', 'salt3', 'Student', 'student1.jpg'),
    ('student2@example.com', 'password4', 'salt4', 'Student', 'student2.jpg'),
    ('admin1@example.com', 'password5', 'salt5', 'Admin', 'admin1.jpg');

-- Insert 5 rows into Teachers table
INSERT INTO Teachers (T_Name, T_Surname, T_Birthdate, T_Phone, T_Address, T_UID)
VALUES
    ('John', 'Doe', '1990-01-01', '123456789', '123 Main St', 1),
    ('Michael', 'Smith', '1985-05-10', '987654321', '456 Elm St', 2);

-- Insert 5 rows into Students table
INSERT INTO Students (S_Name, S_Surname, S_Birthdate, S_Phone, S_Address, S_GLevel, S_UID)
VALUES
    ('Emma', 'Brown', '2003-02-05', '333333333', '987 Walnut St', 9, 3),
    ('Ethan', 'Miller', '2004-06-15', '444444444', '741 Cedar St', 10, 4);

-- Insert 5 rows into Admins table
INSERT INTO Admins (A_Name, A_Surname, A_Phone, A_UID)
VALUES
    ('David', 'Johnson', '888888888', 5);

-- Insert 5 rows into Subjects table
INSERT INTO Subjects (Sb_Name, Sb_Description, Sb_GLevel)
VALUES
    ('Math', 'Advanced Mathematics', 10),
    ('Science', 'Physics and Chemistry', 8),
    ('History', 'World History', 9),
    ('English', 'English Language and Literature', 11),
    ('Geography', 'Geographic Studies', 9);

-- Insert 5 rows into Classes table
INSERT INTO Classes (T_ID, Sb_ID)
VALUES
(1, 1),
(1, 2),
(2, 3),
(2, 4),
(2, 5);

-- Insert 5 rows into Enrollments table
INSERT INTO Enrollments (S_ID, C_ID, E_Date)
VALUES
(1, 1, CURDATE()),
(2, 2, CURDATE()),
(1, 3, CURDATE()),
(2, 4, CURDATE()),
(1, 5, CURDATE());

-- Insert 5 rows into Grades table
INSERT INTO Grades (S_ID, Sb_ID, G_Value)
VALUES
(1, 1, 9),
(1, 2, 8),
(2, 3, 10),
(2, 4, 7),
(1, 5, 9);

SELECT s.S_ID, s.S_Name, s.S_Surname, s.S_Birthdate, s.S_Phone, s.S_Address, s.S_GLevel, sb.Sb_Name
FROM Students s 
INNER JOIN Enrollments e ON e.S_ID = s.S_ID 
INNER JOIN Classes c ON c.C_ID = e.C_ID
INNER JOIN Subjects sb ON sb.Sb_ID = c.Sb_ID
WHERE c.T_ID = 1;




USE lems;

SET FOREIGN_KEY_CHECKS=0;

-- Truncate all tables in the database
TRUNCATE TABLE students;
TRUNCATE TABLE teachers;
TRUNCATE TABLE subjects;
TRUNCATE TABLE grades;
TRUNCATE TABLE enrollments;
TRUNCATE TABLE users;
TRUNCATE TABLE admins;
TRUNCATE TABLE classes;
-- ... add more tables as needed

SET FOREIGN_KEY_CHECKS=1;
