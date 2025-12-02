package com.example.studentmanager

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class StudentDetailActivity : AppCompatActivity() {

    private lateinit var etStudentId: EditText
    private lateinit var etStudentName: EditText
    private lateinit var etStudentPhone: EditText
    private lateinit var etStudentAddress: EditText
    private lateinit var btnUpdate: Button
    private lateinit var btnDelete: Button
    private lateinit var btnCancel: Button

    private lateinit var currentStudent: Student

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_detail)

        // Thiết lập ActionBar
        supportActionBar?.title = "Chi tiết sinh viên"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Khởi tạo views
        etStudentId = findViewById(R.id.etStudentId)
        etStudentName = findViewById(R.id.etStudentName)
        etStudentPhone = findViewById(R.id.etStudentPhone)
        etStudentAddress = findViewById(R.id.etStudentAddress)
        btnUpdate = findViewById(R.id.btnUpdate)
        btnDelete = findViewById(R.id.btnDelete)
        btnCancel = findViewById(R.id.btnCancel)

        // Lấy thông tin sinh viên từ Intent
        currentStudent = intent.getParcelableExtra("STUDENT") ?: run {
            Toast.makeText(this, "Lỗi: Không tìm thấy thông tin sinh viên", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        // Hiển thị thông tin sinh viên
        displayStudentInfo()

        // Xử lý nút Update
        btnUpdate.setOnClickListener {
            updateStudent()
        }

        // Xử lý nút Delete
        btnDelete.setOnClickListener {
            confirmDelete()
        }

        // Xử lý nút Cancel
        btnCancel.setOnClickListener {
            finish()
        }
    }

    private fun displayStudentInfo() {
        etStudentId.setText(currentStudent.id)
        etStudentName.setText(currentStudent.name)
        etStudentPhone.setText(currentStudent.phone)
        etStudentAddress.setText(currentStudent.address)
    }

    private fun updateStudent() {
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

        // Kiểm tra nếu đổi MSSV thì không được trùng với MSSV khác
        if (id != currentStudent.id && StudentManager.isStudentIdExists(id)) {
            Toast.makeText(this, "MSSV đã tồn tại", Toast.LENGTH_SHORT).show()
            etStudentId.requestFocus()
            return
        }

        // Cập nhật sinh viên
        val updatedStudent = Student(id, name, phone, address)
        StudentManager.updateStudent(currentStudent.id, updatedStudent)

        Toast.makeText(this, "Đã cập nhật sinh viên thành công", Toast.LENGTH_SHORT).show()
        finish()
    }

    private fun confirmDelete() {
        AlertDialog.Builder(this)
            .setTitle("Xác nhận xóa")
            .setMessage("Bạn có chắc chắn muốn xóa sinh viên ${currentStudent.name}?")
            .setPositiveButton("Xóa") { _, _ ->
                StudentManager.deleteStudent(currentStudent.id)
                Toast.makeText(this, "Đã xóa sinh viên", Toast.LENGTH_SHORT).show()
                finish()
            }
            .setNegativeButton("Hủy", null)
            .show()
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}