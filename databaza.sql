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
-- DELETE FROM Users WHERE U_ID = 2;

CREATE TABLE Admins (
    A_Name VARCHAR(50),
    A_Surname VARCHAR(50),
    A_Phone VARCHAR(50),
    A_UID INT NOT NULL,
    PRIMARY KEY (A_UID),
    FOREIGN KEY (A_UID) REFERENCES Users(U_ID) ON DELETE CASCADE
);

-- SELECT * FROM Admins
-- drop table Students;
CREATE TABLE Students (
    S_Name VARCHAR(50),
    S_Surname VARCHAR(50),
    S_Birthdate Date,
    S_Phone VARCHAR(50),
    S_Address VARCHAR(50),
    S_GLevel INT,
    S_Paralel INT,
    S_Gender VARCHAR(1),
    S_UID INT NOT NULL,
    PRIMARY KEY (S_UID),
    FOREIGN KEY (S_UID) REFERENCES Users(U_ID) ON DELETE CASCADE
);

-- SELECT * FROM Students
-- drop table teachers;
CREATE TABLE Teachers (
    T_Name VARCHAR(50),
    T_Surname VARCHAR(50),
    T_Birthdate Date,
    T_Phone VARCHAR(50),
    T_Address VARCHAR(50),
    T_Gender VARCHAR(1),
    T_UID INT NOT NULL,
    PRIMARY KEY (T_UID),
    FOREIGN KEY (T_UID) REFERENCES Users(U_ID) ON DELETE CASCADE
);

-- SELECT * FROM Teachers
-- drop table subjects;
CREATE TABLE Subjects(
	Sb_ID INT NOT NULL AUTO_INCREMENT,
    Sb_Name VARCHAR(50),
    Sb_Description VARCHAR(2000),
    Sb_GLevel INT NOT NULL,
    Sb_Obligatory real,
    PRIMARY KEY (Sb_ID)
);

-- SELECT * FROM Subjects
-- drop table classes;
CREATE TABLE Classes (
	C_ID INT NOT NULL AUTO_INCREMENT,
    T_ID INT NOT NULL,
    Sb_ID INT NOT NULL,
    C_Paralel INT,
    PRIMARY KEY (C_ID),
    FOREIGN KEY (T_ID) REFERENCES Teachers(T_UID) ON DELETE CASCADE,
    FOREIGN KEY (Sb_ID) REFERENCES Subjects(Sb_ID) ON DELETE CASCADE
);

-- SELECT * FROM Classes;
-- drop table enrollments;
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
-- drop table grades;
CREATE TABLE Grades (
	G_ID INT NOT NULL AUTO_INCREMENT,
    S_ID INT NOT NULL,
    Sb_ID INT NOT NULL,
    G_Value INT,
    PRIMARY KEY (G_ID),
    FOREIGN KEY (S_ID) REFERENCES Students(S_UID) ON DELETE CASCADE,
	FOREIGN KEY (Sb_ID) REFERENCES Subjects(Sb_ID)
);

-- SELECT * FROM Gradesgrades



-- Dummy data for Enrollments table
-- INSERT INTO Enrollments(S_ID, C_ID, E_Date) VALUES
-- (1, 1, '2022-08-25'),
-- (2, 2, '2022-08-25'),
-- (3, 3, '2022-08-26'),
-- (4, 4, '2022-08-26'),
-- (5, 5, '2022-08-27');


-- Truncate all tables in the database
-- TRUNCATE TABLE students;
-- TRUNCATE TABLE teachers;
-- TRUNCATE TABLE subjects;
-- TRUNCATE TABLE grades;
-- TRUNCATE TABLE enrollments;
-- TRUNCATE TABLE users;
-- TRUNCATE TABLE admins;
-- TRUNCATE TABLE classes;
-- ... add more tables as needed


USE lems;

SET FOREIGN_KEY_CHECKS=0;
TRUNCATE TABLE grades;

SET FOREIGN_KEY_CHECKS=1;



#Enroll Students Trigger 
DELIMITER $$
CREATE TRIGGER auto_enroll AFTER INSERT ON Students
FOR EACH ROW
BEGIN
  INSERT INTO Enrollments (S_ID, C_ID, E_Date)
    SELECT NEW.S_UID, C.C_ID, NOW() 
    FROM Classes C
    JOIN Subjects S ON C.Sb_ID = S.Sb_ID 
    WHERE C.C_Paralel = NEW.S_Paralel 
    AND S.Sb_GLevel = NEW.S_GLevel 
    AND S.Sb_Obligatory = 1;
END$$
DELIMITER ;
-- drop trigger auto_enroll;


#Update Students Enrollments
DELIMITER $$
CREATE TRIGGER update_student_classes
AFTER UPDATE ON Students
FOR EACH ROW
BEGIN
    IF NEW.S_GLevel <> OLD.S_GLevel OR NEW.S_Paralel <> OLD.S_Paralel THEN
        -- Unenroll the student from previous classes
        DELETE FROM Enrollments WHERE S_ID = OLD.S_UID;
        
        -- Enroll the student in new classes based on grade level and paralel
        INSERT INTO Enrollments (S_ID, C_ID, E_Date)
        SELECT NEW.S_UID, C.C_ID, CURDATE()
        FROM Classes C
        INNER JOIN Subjects S ON C.Sb_ID = S.Sb_ID
        WHERE S.Sb_GLevel = NEW.S_GLevel AND C.C_Paralel = NEW.S_Paralel;
    END IF;
END$$
DELIMITER ;
-- drop trigger update_student_classes

#Enroll student to new added subject
DELIMITER $$
CREATE TRIGGER enroll_students_to_class
AFTER INSERT ON Classes
FOR EACH ROW
BEGIN
    -- Enroll students with matching Sb_GLevel and C_Paralel to the new class
    INSERT INTO Enrollments (S_ID, C_ID, E_Date)
    SELECT S.S_UID, NEW.C_ID, CURDATE()
    FROM Students S
    WHERE S.S_GLevel = (SELECT Sb_GLevel FROM Subjects WHERE Sb_ID = NEW.Sb_ID)
    AND S.S_Paralel = NEW.C_Paralel;
END$$
DELIMITER ;
-- drop trigger enroll_students_to_class



