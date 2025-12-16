package com.example.studentmanager

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: StudentAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        // Thiết lập ActionBar
        supportActionBar?.title = "Danh sách sinh viên"

        recyclerView = findViewById(R.id.recyclerView)
        setupRecyclerView()
    }

    override fun onResume() {
        super.onResume()
        // Cập nhật danh sách khi quay lại activity
        adapter.updateData(StudentManager.getAllStudents())
    }

    private fun setupRecyclerView() {
        adapter = StudentAdapter(
            StudentManager.getAllStudents(),
            onItemClick = { student ->
                // Mở activity chi tiết sinh viên
                val intent = Intent(this, StudentDetailActivity::class.java)
                intent.putExtra("STUDENT", student)
                startActivity(intent)
            }
        )

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_add -> {
                // Mở activity thêm sinh viên
                val intent = Intent(this, AddStudentActivity::class.java)
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}