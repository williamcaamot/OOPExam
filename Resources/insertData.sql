USE universityDB;
INSERT INTO studyPrograms (name)
VALUES
    ('Program1'),
    ('Program2'),
    ('Program3'),
    ('Program4'),
    ('Program5');


INSERT INTO students (name, studyProgram)
VALUES
    ('Alice Johnson', 'Program1'),
    ('Bob Smith', 'Program2'),
    ('Charlie Brown', 'Program3'),
    ('Diana Prince', 'Program4'),
    ('Ethan Hunt', 'Program5'),
    ('Fiona Apple', 'Program1'),
    ('George Washington', 'Program2'),
    ('Hannah Montana', 'Program3'),
    ('Isaac Newton', 'Program4'),
    ('Jack Sparrow', 'Program5'),
    ('Kevin McCallister', 'Program1'),
    ('Lara Croft', 'Program2'),
    ('Mario Bros', 'Program3'),
    ('Neo Matrix', 'program4'),
    ('Oliver Twist', 'Program5'),
    ('Peter Parker', 'Program1'),
    ('Quincy Adams', 'Program2'),
    ('Rachael Green', 'Program3'),
    ('Steve Jobs', 'Program4'),
    ('Tony Stark', 'Program5');


INSERT INTO staff (name, role, studyProgram)
VALUES
    ('Professor Robotnik', 'Program Responsible', 'Program1'),
    ('Dr. Code McBinary', 'Program Responsible', 'Program2'),
    ('Astronaut Sally', 'Program Responsible', 'Program3'),
    ('Chef GlaDOS', 'Program Responsible', 'Program4'),
    ('Whisperer McCatface', 'Program Responsible', 'Program5'),
    ('Maxwell Edison', 'Teacher', 'Program1'),
    ('Sarah Connor', 'Teacher', 'Program2'),
    ('Neil Tyson', 'Teacher', 'Program3'),
    ('Tater Tot', 'Teacher', 'Program4'),
    ('Sir Cattington', 'Teacher', 'Program5'),
    ('Agent Smith', 'Teacher', 'Program1'),
    ('Mrs. Kung Fu', 'Teacher', 'Program2');



INSERT INTO eventDB.attendees (name, role, guestof, studyProgram)
SELECT name, role, 'N/A', studyProgram
FROM staff;