package com.example.studentmanager

object StudentManager {
    private val students = mutableListOf(
        Student("20200001", "Nguyễn Văn A", "0901234567", "Hà Nội"),
        Student("20200002", "Trần Thị B", "0912345678", "Hồ Chí Minh"),
        Student("20200003", "Lê Văn C", "0923456789", "Đà Nẵng"),
        Student("20200004", "Phạm Thị D", "0934567890", "Hải Phòng"),
        Student("20200005", "Hoàng Văn E", "0945678901", "Cần Thơ")
    )

    fun getAllStudents(): MutableList<Student> = students

    fun addStudent(student: Student) {
        students.add(student)
    }

    fun updateStudent(oldId: String, updatedStudent: Student) {
        val index = students.indexOfFirst { it.id == oldId }
        if (index != -1) {
            students[index] = updatedStudent
        }
    }

    fun deleteStudent(studentId: String) {
        students.removeAll { it.id == studentId }
    }

    fun isStudentIdExists(id: String): Boolean {
        return students.any { it.id == id }
    }
}