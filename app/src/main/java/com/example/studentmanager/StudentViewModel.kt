package com.example.studentmanager

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class StudentViewModel : ViewModel() {

    private val _students = MutableLiveData<MutableList<Student>>(
        mutableListOf(
            Student("20200001", "Nguyễn Văn A", "0123456789", "Hà Nội"),
            Student("20200002", "Trần Thị B", "0987654321", "Hồ Chí Minh"),
            Student("20200003", "Lê Văn C", "0112233445", "Đà Nẵng")
        )
    )
    val students: LiveData<MutableList<Student>> = _students

    private val _selectedStudent = MutableLiveData<Student?>()
    val selectedStudent: LiveData<Student?> = _selectedStudent

    fun addStudent(student: Student) {
        val currentList = _students.value ?: mutableListOf()
        currentList.add(student)
        _students.value = currentList
    }

    fun updateStudent(oldStudentId: String, updatedStudent: Student) {
        val currentList = _students.value ?: return
        val index = currentList.indexOfFirst { it.id == oldStudentId }
        if (index != -1) {
            currentList[index] = updatedStudent
            _students.value = currentList
        }
    }

    fun deleteStudent(studentId: String) {
        val currentList = _students.value ?: return
        currentList.removeAll { it.id == studentId }
        _students.value = currentList
    }

    fun isStudentIdExists(studentId: String): Boolean {
        return _students.value?.any { it.id == studentId } ?: false
    }

    fun selectStudent(student: Student) {
        _selectedStudent.value = student
    }

    fun clearSelectedStudent() {
        _selectedStudent.value = null
    }
}