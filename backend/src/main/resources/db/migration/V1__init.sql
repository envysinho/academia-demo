CREATE EXTENSION IF NOT EXISTS pgcrypto;

CREATE TABLE students (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    first_name VARCHAR(80) NOT NULL,
    last_name VARCHAR(80) NOT NULL,
    email VARCHAR(160) NOT NULL UNIQUE,
    document_number VARCHAR(40) NOT NULL UNIQUE,
    created_at TIMESTAMPTZ NOT NULL DEFAULT now()
);

CREATE TABLE courses (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    code VARCHAR(20) NOT NULL UNIQUE,
    name VARCHAR(140) NOT NULL,
    credits INTEGER NOT NULL CHECK (credits > 0),
    teacher VARCHAR(140) NOT NULL,
    created_at TIMESTAMPTZ NOT NULL DEFAULT now()
);

CREATE TABLE enrollments (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    student_id UUID NOT NULL REFERENCES students(id),
    course_id UUID NOT NULL REFERENCES courses(id),
    status VARCHAR(20) NOT NULL,
    enrolled_at TIMESTAMPTZ NOT NULL DEFAULT now(),
    UNIQUE (student_id, course_id)
);

INSERT INTO students (first_name, last_name, email, document_number)
VALUES
    ('Lucia', 'Ramos', 'lucia.ramos@academia.test', 'DNI-1001'),
    ('Mateo', 'Vargas', 'mateo.vargas@academia.test', 'DNI-1002'),
    ('Camila', 'Torres', 'camila.torres@academia.test', 'DNI-1003');

INSERT INTO courses (code, name, credits, teacher)
VALUES
    ('MAT-101', 'Matematica Basica', 4, 'Ana Flores'),
    ('COM-201', 'Comunicacion Academica', 3, 'Jorge Salazar'),
    ('PRO-110', 'Programacion I', 5, 'Elena Castro');

INSERT INTO enrollments (student_id, course_id, status)
SELECT s.id, c.id, 'ACTIVE'
FROM students s
JOIN courses c ON c.code IN ('MAT-101', 'PRO-110')
WHERE s.document_number = 'DNI-1001';
