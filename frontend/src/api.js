const API_URL = import.meta.env.VITE_API_URL ?? 'http://localhost:8081/api'

async function request(path, options = {}) {
  const response = await fetch(`${API_URL}${path}`, {
    headers: {
      'Content-Type': 'application/json',
      ...options.headers,
    },
    ...options,
  })

  if (!response.ok) {
    const error = await response.json().catch(() => ({ message: 'Error inesperado' }))
    throw new Error(error.message ?? 'Error inesperado')
  }

  return response.json()
}

export function getDashboard() {
  return request('/dashboard')
}

export function getStudents() {
  return request('/students')
}

export function createStudent(payload) {
  return request('/students', {
    method: 'POST',
    body: JSON.stringify(payload),
  })
}

export function getCourses() {
  return request('/courses')
}

export function createCourse(payload) {
  return request('/courses', {
    method: 'POST',
    body: JSON.stringify(payload),
  })
}

export function getEnrollments() {
  return request('/enrollments')
}

export function createEnrollment(payload) {
  return request('/enrollments', {
    method: 'POST',
    body: JSON.stringify(payload),
  })
}
