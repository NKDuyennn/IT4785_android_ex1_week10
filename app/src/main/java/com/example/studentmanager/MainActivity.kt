package com.example.studentmanager

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var etStudentId: EditText
    private lateinit var etStudentName: EditText
    private lateinit var btnAdd: Button
    private lateinit var btnUpdate: Button
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: StudentAdapter

    private var selectedStudentId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Khởi tạo views
        etStudentId = findViewById(R.id.etStudentId)
        etStudentName = findViewById(R.id.etStudentName)
        btnAdd = findViewById(R.id.btnAdd)
        btnUpdate = findViewById(R.id.btnUpdate)
        recyclerView = findViewById(R.id.recyclerView)

        // Khởi tạo danh sách sinh viên mẫu
        val studentList = mutableListOf(
            Student("20200001", "Nguyễn Văn A"),
            Student("20200002", "Trần Thị B"),
            Student("20200003", "Lê Văn C"),
            Student("20200004", "Phạm Thị D"),
            Student("20200005", "Hoàng Văn E"),
            Student("20200006", "Vũ Thị F"),
            Student("20200007", "Đặng Văn G"),
            Student("20200008", "Bùi Thị H"),
            Student("20200009", "Hồ Văn I")
        )

        // Thiết lập RecyclerView
        adapter = StudentAdapter(
            studentList,
            onItemClick = { student ->
                // Khi click vào item, hiển thị thông tin vào EditText
                etStudentId.setText(student.id)
                etStudentName.setText(student.name)
                selectedStudentId = student.id
            },
            onDeleteClick = { position ->
                // Xóa sinh viên
                adapter.removeStudent(position)
                clearInputs()
                Toast.makeText(this, "Đã xóa sinh viên", Toast.LENGTH_SHORT).show()
            }
        )

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        // Xử lý nút Add
        btnAdd.setOnClickListener {
            val id = etStudentId.text.toString().trim()
            val name = etStudentName.text.toString().trim()

            if (id.isEmpty() || name.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Kiểm tra trùng MSSV
            val existingPosition = adapter.findStudentPosition(id)
            if (existingPosition != -1) {
                Toast.makeText(this, "MSSV đã tồn tại", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Thêm sinh viên mới
            val newStudent = Student(id, name)
            adapter.addStudent(newStudent)
            clearInputs()
            Toast.makeText(this, "Đã thêm sinh viên", Toast.LENGTH_SHORT).show()
        }

        // Xử lý nút Update
        btnUpdate.setOnClickListener {
            val id = etStudentId.text.toString().trim()
            val name = etStudentName.text.toString().trim()

            if (id.isEmpty() || name.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (selectedStudentId == null) {
                Toast.makeText(this, "Vui lòng chọn sinh viên cần cập nhật", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Tìm vị trí của sinh viên cần update
            val position = adapter.findStudentPosition(selectedStudentId!!)
            if (position != -1) {
                val updatedStudent = Student(id, name)
                adapter.updateStudent(position, updatedStudent)
                clearInputs()
                Toast.makeText(this, "Đã cập nhật sinh viên", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Không tìm thấy sinh viên", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun clearInputs() {
        etStudentId.text.clear()
        etStudentName.text.clear()
        selectedStudentId = null
    }
}