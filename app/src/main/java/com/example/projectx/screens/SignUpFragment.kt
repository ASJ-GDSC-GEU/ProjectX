package com.example.projectx.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.projectx.databinding.FragmentSignUpBinding


class SignUpFragment : Fragment() {
    private var _binding : FragmentSignUpBinding? = null
    private val binding = _binding!!

//    private lateinit var auth: FirebaseAuth
//    private lateinit var userName: String
//
//    private var selectedUser: Int = 0 // 0 for Student --- 1 for Teacher

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        _binding = FragmentSignUpBinding.inflate(inflater, container, false)
        val view = binding.root


        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        auth = FirebaseAuth.getInstance()
//        val currentUser = auth.currentUser
//
//
//        binding.apply {
//            nameInput.setText(currentUser?.displayName)
//            val userId : String = Firebase.auth.currentUser!!.uid
//
//
//            //Teachers or Student Selection
//            studentOrTeacher.setOnCheckedChangeListener { group, checkedId ->
//                if (checkedId == R.id.teacher) {
//                    binding.teacher.setTextColor(Color.WHITE)
//                    binding.student.setTextColor(Color.GRAY)
//                    selectedUser = 1
//
//                } else if (checkedId == R.id.student) {
//                    binding.teacher.setTextColor(Color.GRAY)
//                    binding.student.setTextColor(Color.WHITE)
//                    selectedUser = 0
//                }
//            }
//
//            //SignUP
//            signUpBtn.setOnClickListener {
//                if (nameInput.text.isNullOrBlank()) {
//                    nameInput.error = "Required"
//                } else {
//                    userName = nameInput.text.toString()
//                    if (selectedUser == 0) {
//                        val student =
//                            Student(
//                                currentUser!!.uid,
//                                userName,
//                                currentUser.photoUrl.toString(),
//
//                            )
//                        val studentDao = StudentDao()
//                        studentDao.addStudent(student)
//                        goToStudentHomeScreen()

//                    } else if (selectedUser == 1) {
//                        val teacher =
//                            Teacher(
//                                currentUser!!.uid,
//                                userName,
//                                currentUser.photoUrl.toString()
//                            )
//                        val teacherDao = TeacherDao()
//                        teacherDao.addTeacher(teacher)
//                        goToTeacherHomeScreen()
//                    }
//                }
//            }
//        }
//
//
//    }
////
//    override fun onDestroyView() {
//        super.onDestroyView()
//        _binding = null
//    }
//
//    private fun goToStudentHomeScreen() {
//    }
//
//    private fun goToTeacherHomeScreen() {
//        val action = DetailsFragmentDirections.actionDetailsFragmentToTeachersFragment()
//        requireView().findNavController().navigate(action)
//    }

}}