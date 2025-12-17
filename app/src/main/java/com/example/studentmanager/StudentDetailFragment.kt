package com.example.studentmanager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.studentmanager.databinding.FragmentStudentDetailBinding

class StudentDetailFragment : Fragment() {

    private var _binding: FragmentStudentDetailBinding? = null
    private val binding get() = _binding!!

    private val viewModel: StudentViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStudentDetailBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnUpdate.setOnClickListener {
            updateStudent()
        }

        binding.btnDelete.setOnClickListener {
            confirmDelete()
        }
    }

    private fun updateStudent() {
        val currentStudent = viewModel.selectedStudent.value ?: return

        val id = binding.etStudentId.text.toString().trim()
        val name = binding.etStudentName.text.toString().trim()
        val phone = binding.etStudentPhone.text.toString().trim()
        val address = binding.etStudentAddress.text.toString().trim()

        if (name.isEmpty()) {
            Toast.makeText(context, "Vui lòng nhập họ tên", Toast.LENGTH_SHORT).show()
            binding.etStudentName.requestFocus()
            return
        }

        if (id.isEmpty()) {
            Toast.makeText(context, "Vui lòng nhập MSSV", Toast.LENGTH_SHORT).show()
            binding.etStudentId.requestFocus()
            return
        }

        if (id != currentStudent.id && viewModel.isStudentIdExists(id)) {
            Toast.makeText(context, "MSSV đã tồn tại", Toast.LENGTH_SHORT).show()
            binding.etStudentId.requestFocus()
            return
        }

        val updatedStudent = Student(id, name, phone, address)
        viewModel.updateStudent(currentStudent.id, updatedStudent)

        Toast.makeText(context, "Đã cập nhật sinh viên thành công", Toast.LENGTH_SHORT).show()
        findNavController().navigateUp()
    }

    private fun confirmDelete() {
        val student = viewModel.selectedStudent.value ?: return
        AlertDialog.Builder(requireContext())
            .setTitle("Xác nhận xóa")
            .setMessage("Bạn có chắc chắn muốn xóa sinh viên ${student.name}?")
            .setPositiveButton("Xóa") { _, _ ->
                viewModel.deleteStudent(student.id)
                Toast.makeText(context, "Đã xóa sinh viên", Toast.LENGTH_SHORT).show()
                findNavController().navigateUp()
            }
            .setNegativeButton("Hủy", null)
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.clearSelectedStudent()
        _binding = null
    }
}