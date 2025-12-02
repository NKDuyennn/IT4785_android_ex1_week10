package com.example.studentmanager

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class AddStudentActivity : AppCompatActivity() {

    private lateinit var etStudentId: EditText
    private lateinit var etStudentName: EditText
    private lateinit var etStudentPhone: EditText
    private lateinit var etStudentAddress: EditText
    private lateinit var btnSave: Button
    private lateinit var btnCancel: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_student)

        // Thiết lập ActionBar
        supportActionBar?.title = "Thêm sinh viên"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Khởi tạo views
        etStudentId = findViewById(R.id.etStudentId)
        etStudentName = findViewById(R.id.etStudentName)
        etStudentPhone = findViewById(R.id.etStudentPhone)
        etStudentAddress = findViewById(R.id.etStudentAddress)
        btnSave = findViewById(R.id.btnSave)
        btnCancel = findViewById(R.id.btnCancel)

        // Xử lý nút Save
        btnSave.setOnClickListener {
            saveStudent()
        }

        // Xử lý nút Cancel
        btnCancel.setOnClickListener {
            finish()
        }
    }

    private fun saveStudent() {
        val id = etStudentId.text.toString().trim()
        val name = etStudentName.text.toString().trim()
        val phone = etStudentPhone.text.toString().trim()
        val address = etStudentAddress.text.toString().trim()

        // Validate dữ liệu
        if (id.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập MSSV", Toast.LENGTH_SHORT).show()
            etStudentId.requestFocus()
            return
        }

        if (name.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập họ tên", Toast.LENGTH_SHORT).show()
            etStudentName.requestFocus()
            return
        }

        if (phone.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập số điện thoại", Toast.LENGTH_SHORT).show()
            etStudentPhone.requestFocus()
            return
        }

        if (address.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập địa chỉ", Toast.LENGTH_SHORT).show()
            etStudentAddress.requestFocus()
            return
        }

        // Kiểm tra trùng MSSV
        if (StudentManager.isStudentIdExists(id)) {
            Toast.makeText(this, "MSSV đã tồn tại", Toast.LENGTH_SHORT).show()
            etStudentId.requestFocus()
            return
        }

        // Thêm sinh viên mới
        val newStudent = Student(id, name, phone, address)
        StudentManager.addStudent(newStudent)

        Toast.makeText(this, "Đã thêm sinh viên thành công", Toast.LENGTH_SHORT).show()
        finish()
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}