package com.example.studentmanager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.studentmanager.databinding.FragmentAddStudentBinding

class AddStudentFragment : Fragment() {

    private var _binding: FragmentAddStudentBinding? = null
    private val binding get() = _binding!!

    private val viewModel: StudentViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddStudentBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnSave.setOnClickListener {
            saveStudent()
        }
    }

    private fun saveStudent() {
        val id = binding.etStudentId.text.toString().trim()
        val name = binding.etStudentName.text.toString().trim()
        val phone = binding.etStudentPhone.text.toString().trim()
        val address = binding.etStudentAddress.text.toString().trim()

        if (id.isEmpty()) {
            Toast.makeText(context, "Vui lòng nhập MSSV", Toast.LENGTH_SHORT).show()
            binding.etStudentId.requestFocus()
            return
        }

        if (name.isEmpty()) {
            Toast.makeText(context, "Vui lòng nhập họ tên", Toast.LENGTH_SHORT).show()
            binding.etStudentName.requestFocus()
            return
        }

        if (viewModel.isStudentIdExists(id)) {
            Toast.makeText(context, "MSSV đã tồn tại", Toast.LENGTH_SHORT).show()
            binding.etStudentId.requestFocus()
            return
        }

        val newStudent = Student(id, name, phone, address)
        viewModel.addStudent(newStudent)

        Toast.makeText(context, "Đã thêm sinh viên thành công", Toast.LENGTH_SHORT).show()
        findNavController().navigateUp()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}