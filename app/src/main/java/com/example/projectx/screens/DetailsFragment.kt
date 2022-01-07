package com.example.projectx.screens

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.projectx.R
import com.example.projectx.daos.StudentDao
import com.example.projectx.daos.TeacherDao
import com.example.projectx.databinding.FragmentDetailsBinding
import com.example.projectx.models.Student
import com.example.projectx.models.Teacher
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class DetailsFragment : Fragment() {
    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!

    private lateinit var auth: FirebaseAuth
    private lateinit var userName: String
    private lateinit var course: String
    private lateinit var semester: String
    private lateinit var section: String

    private var selectedUser: Int = 0 // 0 for Student --- 1 for Teacher

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        val view = binding.root

        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser
        val studentDao = StudentDao()
        val teacherDao = TeacherDao()




        binding.apply {
            nameInput.setText(currentUser?.displayName)
            val userId : String = Firebase.auth.currentUser!!.uid

            //User with these Id's are only allowed for teachersFragment

            //Teachers or Student Selection
            studentOrTeacher.setOnCheckedChangeListener { group, checkedId ->
                if (checkedId == R.id.teacher) {
                    binding.teacher.setTextColor(Color.WHITE)
                    binding.student.setTextColor(Color.GRAY)
                    selectedUser = 1
                    constraintLayout2.visibility = View.INVISIBLE

                } else if (checkedId == R.id.student) {
                    binding.teacher.setTextColor(Color.GRAY)
                    binding.student.setTextColor(Color.WHITE)
                    selectedUser = 0
                    constraintLayout2.visibility = View.VISIBLE
                }
            }

            //SignUP
            signUpBtn.setOnClickListener {
                if (nameInput.text.isNullOrBlank()) {
                    nameInput.error = "Required"
                }else {


                    if (selectedUser == 0) {
                        if(etCourse.text.isNullOrBlank()){
                            etCourse.error = "Required"
                        }else if(etSemester.text.isNullOrBlank()){
                            etSemester.error = "Required"
                        }else if(etSection.text.isNullOrBlank()){
                            etSection.error = "Required"
                        }else {
                            userName = nameInput.text.toString()
                            course = etCourse.text.toString()
                            semester = etSemester.text.toString()
                            section = etSection.text.toString()
                            val student =
                                Student(
                                    currentUser!!.uid,
                                    userName,
                                    currentUser.photoUrl.toString(),
                                    course,
                                    semester,
                                    section
                                )
                            studentDao.addStudent(student)
                            goToStudentHomeScreen()
                        }
                    } else if (selectedUser == 1) {
                        userName = nameInput.text.toString()
                        val teacher =
                            Teacher(
                                currentUser!!.uid,
                                userName,
                                currentUser.photoUrl.toString()
                            )
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

    private fun goToStudentHomeScreen() {
        val action = DetailsFragmentDirections.actionDetailsFragmentToStudentFragment()
        requireView().findNavController().navigate(action)
    }

    private fun goToTeacherHomeScreen() {
        val action = DetailsFragmentDirections.actionDetailsFragmentToTeachersFragment()
        requireView().findNavController().navigate(action)
    }

}