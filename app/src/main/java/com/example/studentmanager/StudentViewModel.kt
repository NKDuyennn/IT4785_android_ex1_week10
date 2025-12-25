package com.example.studentmanager

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class StudentViewModel(application: Application) : AndroidViewModel(application) {

    private val dataSource: StudentDataSource = StudentDataSource(application)
    private val _students = MutableLiveData<MutableList<Student>>()
    val students: LiveData<MutableList<Student>> = _students

    private val _selectedStudent = MutableLiveData<Student?>()
    val selectedStudent: LiveData<Student?> = _selectedStudent

    init {
        dataSource.open()
        if (dataSource.getAllStudents().isEmpty()) {
            dataSource.addStudent(Student("20200001", "Nguyễn Văn A", "0123456789", "Hà Nội"))
            dataSource.addStudent(Student("20200002", "Trần Thị B", "0987654321", "Hồ Chí Minh"))
            dataSource.addStudent(Student("20200003", "Lê Văn C", "0112233445", "Đà Nẵng"))
        }
        _students.value = dataSource.getAllStudents().toMutableList()
    }

    fun addStudent(student: Student) {
        dataSource.addStudent(student)
        _students.value = dataSource.getAllStudents().toMutableList()
    }

    fun updateStudent(oldStudentId: String, updatedStudent: Student) {
        dataSource.updateStudent(oldStudentId, updatedStudent)
        _students.value = dataSource.getAllStudents().toMutableList()
    }

    fun deleteStudent(studentId: String) {
        dataSource.deleteStudent(studentId)
        _students.value = dataSource.getAllStudents().toMutableList()
    }

    fun isStudentIdExists(studentId: String): Boolean {
        return dataSource.isStudentIdExists(studentId)
    }

    fun selectStudent(student: Student) {
        _selectedStudent.value = student
    }

    fun clearSelectedStudent() {
        _selectedStudent.value = null
    }

    override fun onCleared() {
        dataSource.close()
        super.onCleared()
    }
}
