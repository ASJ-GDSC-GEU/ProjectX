package com.example.projectx.screens

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import com.example.projectx.MainActivity
import com.example.projectx.R
import com.example.projectx.daos.StudentDao
import com.example.projectx.daos.TeacherDao
import com.example.projectx.databinding.FragmentDetailsBinding
import com.example.projectx.models.Student
import com.example.projectx.models.Teacher
import com.google.firebase.auth.FirebaseAuth


class DetailsFragment : Fragment() {
    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!

    private lateinit var auth: FirebaseAuth
    private lateinit var userName: String

    private var selectedUser: Int = 0 // 0 for Student --- 1 for Teacher

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentDetailsBinding.inflate(inflater,container, false)
        val view = binding.root
        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser

        binding.apply {
            nameInput.setText(currentUser?.displayName)
            signUpBtn.setOnClickListener {
                if (nameInput.text.isNullOrBlank()) {
                    nameInput.error = "Required"
                } else {
                    userName = nameInput.text.toString()
                    if (selectedUser == 0) {
                        val student =
                            Student(currentUser!!.uid, userName, currentUser.photoUrl.toString())
                        val studentDao = StudentDao()
                        studentDao.addStudent(student)
                        goToStudentHomeScreen()

                    } else {
                        val teacher =
                            Teacher(currentUser!!.uid, userName, currentUser.photoUrl.toString())
                        val teacherDao = TeacherDao()
                        teacherDao.addTeacher(teacher)
                        goToTeacherHomeScreen()
                    }
                }
            }
        }


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun onRadioButtonClicked(view: View) {
        if (view is RadioButton) {
            // Is the button now checked?
            val checked = view.isChecked

            // Check which radio button was clicked
            when (view.getId()) {
                R.id.student ->
                    if (checked) {
                        binding.student.setTextColor(Color.WHITE)
                        binding.teacher.setTextColor(Color.GRAY)
                        selectedUser = 0
                    }
                R.id.teacher ->
                    if (checked) {
                        binding.teacher.setTextColor(Color.WHITE)
                        binding.student.setTextColor(Color.GRAY)
                        selectedUser = 1
                    }
            }
        }
    }

    private fun goToStudentHomeScreen(){
    }

    private fun goToTeacherHomeScreen(){

    }

}