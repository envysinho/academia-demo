import { useEffect, useMemo, useState } from 'react'
import PropTypes from 'prop-types'
import { BookOpen, GraduationCap, LayoutDashboard, Plus, RefreshCw, Users } from 'lucide-react'
import {
  createCourse,
  createEnrollment,
  createStudent,
  getCourses,
  getDashboard,
  getEnrollments,
  getStudents,
} from './api'

const emptyStudent = {
  firstName: '',
  lastName: '',
  email: '',
  documentNumber: '',
}

const emptyCourse = {
  code: '',
  name: '',
  credits: 3,
  teacher: '',
}

export default function App() {
  const [dashboard, setDashboard] = useState({ students: 0, courses: 0, enrollments: 0 })
  const [students, setStudents] = useState([])
  const [courses, setCourses] = useState([])
  const [enrollments, setEnrollments] = useState([])
  const [studentForm, setStudentForm] = useState(emptyStudent)
  const [courseForm, setCourseForm] = useState(emptyCourse)
  const [enrollmentForm, setEnrollmentForm] = useState({ studentId: '', courseId: '' })
  const [status, setStatus] = useState({ loading: true, error: '', message: '' })

  const hasEnrollmentOptions = students.length > 0 && courses.length > 0

  async function loadData(message = '') {
    setStatus({ loading: true, error: '', message })
    try {
      const [dashboardData, studentData, courseData, enrollmentData] = await Promise.all([
        getDashboard(),
        getStudents(),
        getCourses(),
        getEnrollments(),
      ])
      setDashboard(dashboardData)
      setStudents(studentData)
      setCourses(courseData)
      setEnrollments(enrollmentData)
      setEnrollmentForm((current) => ({
        studentId: current.studentId || studentData[0]?.id || '',
        courseId: current.courseId || courseData[0]?.id || '',
      }))
      setStatus({ loading: false, error: '', message })
    } catch (error) {
      setStatus({ loading: false, error: error.message, message: '' })
    }
  }

  useEffect(() => {
    loadData()
  }, [])

  async function handleStudentSubmit(event) {
    event.preventDefault()
    await runMutation(() => createStudent(studentForm), 'Estudiante registrado')
    setStudentForm(emptyStudent)
  }

  async function handleCourseSubmit(event) {
    event.preventDefault()
    await runMutation(() => createCourse({ ...courseForm, credits: Number(courseForm.credits) }), 'Curso registrado')
    setCourseForm(emptyCourse)
  }

  async function handleEnrollmentSubmit(event) {
    event.preventDefault()
    await runMutation(() => createEnrollment(enrollmentForm), 'Matricula creada')
  }

  async function runMutation(action, successMessage) {
    setStatus({ loading: true, error: '', message: '' })
    try {
      await action()
      await loadData(successMessage)
    } catch (error) {
      setStatus({ loading: false, error: error.message, message: '' })
    }
  }

  const latestEnrollments = useMemo(() => enrollments.slice(0, 6), [enrollments])

  return (
    <main className="app-shell">
      <section className="topbar">
        <div>
          <p className="eyebrow">Sistema academico</p>
          <h1>Academia Demo</h1>
        </div>
        <button className="icon-button" onClick={() => loadData()} title="Actualizar datos" type="button">
          <RefreshCw size={18} />
        </button>
      </section>

      {status.error && <p className="alert error">{status.error}</p>}
      {status.message && <p className="alert success">{status.message}</p>}

      <section className="metrics-grid" aria-label="Metricas principales">
        <Metric icon={<Users />} label="Estudiantes" value={dashboard.students} />
        <Metric icon={<BookOpen />} label="Cursos" value={dashboard.courses} />
        <Metric icon={<GraduationCap />} label="Matriculas" value={dashboard.enrollments} />
      </section>

      <section className="workspace">
        <div className="panel">
          <div className="panel-heading">
            <LayoutDashboard size={18} />
            <h2>Registro rapido</h2>
          </div>

          <form className="form-grid" onSubmit={handleStudentSubmit}>
            <h3>Estudiante</h3>
            <input placeholder="Nombres" value={studentForm.firstName} onChange={(event) => setStudentForm({ ...studentForm, firstName: event.target.value })} required />
            <input placeholder="Apellidos" value={studentForm.lastName} onChange={(event) => setStudentForm({ ...studentForm, lastName: event.target.value })} required />
            <input placeholder="Email" type="email" value={studentForm.email} onChange={(event) => setStudentForm({ ...studentForm, email: event.target.value })} required />
            <input placeholder="Documento" value={studentForm.documentNumber} onChange={(event) => setStudentForm({ ...studentForm, documentNumber: event.target.value })} required />
            <button className="primary-button" disabled={status.loading} type="submit">
              <Plus size={16} />
              Guardar estudiante
            </button>
          </form>

          <form className="form-grid" onSubmit={handleCourseSubmit}>
            <h3>Curso</h3>
            <input placeholder="Codigo" value={courseForm.code} onChange={(event) => setCourseForm({ ...courseForm, code: event.target.value })} required />
            <input placeholder="Nombre del curso" value={courseForm.name} onChange={(event) => setCourseForm({ ...courseForm, name: event.target.value })} required />
            <input min="1" max="10" placeholder="Creditos" type="number" value={courseForm.credits} onChange={(event) => setCourseForm({ ...courseForm, credits: event.target.value })} required />
            <input placeholder="Docente" value={courseForm.teacher} onChange={(event) => setCourseForm({ ...courseForm, teacher: event.target.value })} required />
            <button className="primary-button" disabled={status.loading} type="submit">
              <Plus size={16} />
              Guardar curso
            </button>
          </form>
        </div>

        <div className="panel">
          <div className="panel-heading">
            <GraduationCap size={18} />
            <h2>Matriculas</h2>
          </div>

          <form className="enrollment-form" onSubmit={handleEnrollmentSubmit}>
            <select value={enrollmentForm.studentId} onChange={(event) => setEnrollmentForm({ ...enrollmentForm, studentId: event.target.value })} disabled={!hasEnrollmentOptions}>
              {students.map((student) => (
                <option key={student.id} value={student.id}>
                  {student.firstName} {student.lastName}
                </option>
              ))}
            </select>
            <select value={enrollmentForm.courseId} onChange={(event) => setEnrollmentForm({ ...enrollmentForm, courseId: event.target.value })} disabled={!hasEnrollmentOptions}>
              {courses.map((course) => (
                <option key={course.id} value={course.id}>
                  {course.code} - {course.name}
                </option>
              ))}
            </select>
            <button className="primary-button" disabled={status.loading || !hasEnrollmentOptions} type="submit">
              <Plus size={16} />
              Matricular
            </button>
          </form>

          <div className="table-wrap">
            <table>
              <thead>
                <tr>
                  <th>Estudiante</th>
                  <th>Curso</th>
                  <th>Estado</th>
                </tr>
              </thead>
              <tbody>
                {latestEnrollments.map((enrollment) => (
                  <tr key={enrollment.id}>
                    <td>{enrollment.student.firstName} {enrollment.student.lastName}</td>
                    <td>{enrollment.course.code}</td>
                    <td><span className="status-pill">{enrollment.status}</span></td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>
        </div>
      </section>
    </main>
  )
}

function Metric({ icon, label, value }) {
  return (
    <article className="metric-card">
      <span className="metric-icon">{icon}</span>
      <div>
        <p>{label}</p>
        <strong>{value}</strong>
      </div>
    </article>
  )
}

Metric.propTypes = {
  icon: PropTypes.node.isRequired,
  label: PropTypes.string.isRequired,
  value: PropTypes.number.isRequired,
}
