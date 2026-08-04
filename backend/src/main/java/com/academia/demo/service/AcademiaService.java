package com.academia.demo.service;

import com.academia.demo.domain.Course;
import com.academia.demo.domain.Enrollment;
import com.academia.demo.domain.EnrollmentStatus;
import com.academia.demo.domain.Student;
import com.academia.demo.repository.CourseRepository;
import com.academia.demo.repository.EnrollmentRepository;
import com.academia.demo.repository.StudentRepository;
import com.academia.demo.web.dto.CourseRequest;
import com.academia.demo.web.dto.CourseResponse;
import com.academia.demo.web.dto.DashboardResponse;
import com.academia.demo.web.dto.EnrollmentRequest;
import com.academia.demo.web.dto.EnrollmentResponse;
import com.academia.demo.web.dto.StudentRequest;
import com.academia.demo.web.dto.StudentResponse;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AcademiaService {

    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;
    private final EnrollmentRepository enrollmentRepository;

    public AcademiaService(
            StudentRepository studentRepository,
            CourseRepository courseRepository,
            EnrollmentRepository enrollmentRepository
    ) {
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
        this.enrollmentRepository = enrollmentRepository;
    }

    @Transactional(readOnly = true)
    public List<StudentResponse> listStudents() {
        return studentRepository.findAll().stream().map(this::toStudentResponse).toList();
    }

    @Transactional
    public StudentResponse createStudent(StudentRequest request) {
        Student student = new Student();
        student.setFirstName(request.firstName());
        student.setLastName(request.lastName());
        student.setEmail(request.email());
        student.setDocumentNumber(request.documentNumber());
        return toStudentResponse(studentRepository.save(student));
    }

    @Transactional(readOnly = true)
    public List<CourseResponse> listCourses() {
        return courseRepository.findAll().stream().map(this::toCourseResponse).toList();
    }

    @Transactional
    public CourseResponse createCourse(CourseRequest request) {
        Course course = new Course();
        course.setCode(request.code());
        course.setName(request.name());
        course.setCredits(request.credits());
        course.setTeacher(request.teacher());
        return toCourseResponse(courseRepository.save(course));
    }

    @Transactional(readOnly = true)
    public List<EnrollmentResponse> listEnrollments() {
        return enrollmentRepository.findAll().stream().map(this::toEnrollmentResponse).toList();
    }

    @Transactional
    public EnrollmentResponse createEnrollment(EnrollmentRequest request) {
        Student student = studentRepository.findById(request.studentId())
                .orElseThrow(() -> new EntityNotFoundException("Student not found"));
        Course course = courseRepository.findById(request.courseId())
                .orElseThrow(() -> new EntityNotFoundException("Course not found"));

        Enrollment enrollment = new Enrollment();
        enrollment.setStudent(student);
        enrollment.setCourse(course);
        enrollment.setStatus(EnrollmentStatus.ACTIVE);
        return toEnrollmentResponse(enrollmentRepository.save(enrollment));
    }

    @Transactional(readOnly = true)
    public DashboardResponse dashboard() {
        return new DashboardResponse(
                studentRepository.count(),
                courseRepository.count(),
                enrollmentRepository.count()
        );
    }

    private StudentResponse toStudentResponse(Student student) {
        return new StudentResponse(
                student.getId(),
                student.getFirstName(),
                student.getLastName(),
                student.getEmail(),
                student.getDocumentNumber(),
                student.getCreatedAt()
        );
    }

    private CourseResponse toCourseResponse(Course course) {
        return new CourseResponse(
                course.getId(),
                course.getCode(),
                course.getName(),
                course.getCredits(),
                course.getTeacher(),
                course.getCreatedAt()
        );
    }

    private EnrollmentResponse toEnrollmentResponse(Enrollment enrollment) {
        return new EnrollmentResponse(
                enrollment.getId(),
                toStudentResponse(enrollment.getStudent()),
                toCourseResponse(enrollment.getCourse()),
                enrollment.getStatus(),
                enrollment.getEnrolledAt()
        );
    }
}
