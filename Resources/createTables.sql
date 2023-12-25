use universityDB;

CREATE TABLE studyPrograms(
    id INT AUTO_INCREMENT NOT NULL,
    name VARCHAR(200) UNIQUE,
    PRIMARY KEY(id)
);

CREATE TABLE students(
    id INT AUTO_INCREMENT NOT NULL,
    name VARCHAR(200),
    studyProgram VARCHAR(200),
    PRIMARY KEY (id),
    FOREIGN KEY (studyProgram) REFERENCES studyPrograms(name)
);

CREATE TABLE staff(
    id INT AUTO_INCREMENT NOT NULL,
    name VARCHAR(200),
    role VARCHAR(200),
    studyProgram VARCHAR(200),
    PRIMARY KEY(id),
    FOREIGN KEY (studyProgram) REFERENCES studyPrograms(name)
);


USE eventDB;

CREATE TABLE attendees(
    id INT AUTO_INCREMENT NOT NULL,
    name VARCHAR(200) UNIQUE , -- Instead of  having unique here, an idea would be to have a method that sends a query that groups the results based on name when quering the guests (since every name only appears once)
    role VARCHAR(200),
    guestOf VARCHAR(200),
    studyProgram VARCHAR(200),
    PRIMARY KEY (id)
);