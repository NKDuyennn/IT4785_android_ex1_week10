package com.example.studentmanager

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase

class StudentDataSource(context: Context) {

    private val dbHelper: DatabaseHelper = DatabaseHelper(context)
    private var database: SQLiteDatabase? = null

    fun open() {
        database = dbHelper.writableDatabase
    }

    fun close() {
        dbHelper.close()
    }

    fun addStudent(student: Student) {
        val values = ContentValues().apply {
            put(DatabaseHelper.COLUMN_ID, student.id)
            put(DatabaseHelper.COLUMN_NAME, student.name)
            put(DatabaseHelper.COLUMN_PHONE, student.phone)
            put(DatabaseHelper.COLUMN_ADDRESS, student.address)
        }
        database?.insert(DatabaseHelper.TABLE_STUDENTS, null, values)
    }

    fun getAllStudents(): List<Student> {
        val students = mutableListOf<Student>()
        val cursor: Cursor? = database?.query(
            DatabaseHelper.TABLE_STUDENTS,
            null, null, null, null, null, null
        )

        cursor?.let {
            it.moveToFirst()
            while (!it.isAfterLast) {
                val student = cursorToStudent(it)
                students.add(student)
                it.moveToNext()
            }
            it.close()
        }
        return students
    }

    fun updateStudent(oldStudentId: String, updatedStudent: Student) {
        val values = ContentValues().apply {
            put(DatabaseHelper.COLUMN_ID, updatedStudent.id)
            put(DatabaseHelper.COLUMN_NAME, updatedStudent.name)
            put(DatabaseHelper.COLUMN_PHONE, updatedStudent.phone)
            put(DatabaseHelper.COLUMN_ADDRESS, updatedStudent.address)
        }
        database?.update(
            DatabaseHelper.TABLE_STUDENTS, values,
            "${DatabaseHelper.COLUMN_ID} = ?", arrayOf(oldStudentId)
        )
    }

    fun deleteStudent(studentId: String) {
        database?.delete(
            DatabaseHelper.TABLE_STUDENTS,
            "${DatabaseHelper.COLUMN_ID} = ?", arrayOf(studentId)
        )
    }

    fun isStudentIdExists(studentId: String): Boolean {
        val cursor = database?.query(
            DatabaseHelper.TABLE_STUDENTS,
            arrayOf(DatabaseHelper.COLUMN_ID),
            "${DatabaseHelper.COLUMN_ID} = ?",
            arrayOf(studentId), null, null, null
        )
        val exists = (cursor?.count ?: 0) > 0
        cursor?.close()
        return exists
    }

    private fun cursorToStudent(cursor: Cursor): Student {
        val id = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_ID))
        val name = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_NAME))
        val phone = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_PHONE))
        val address = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_ADDRESS))
        return Student(id, name, phone, address)
    }
}
