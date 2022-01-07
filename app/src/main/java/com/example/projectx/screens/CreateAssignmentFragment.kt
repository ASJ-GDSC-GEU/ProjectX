package com.example.projectx.screens

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.projectx.daos.AssignmentDao
import com.example.projectx.daos.TeacherDao
import com.example.projectx.databinding.FragmentCreateAssignmentBinding
import com.example.projectx.models.Assignment
import com.google.firebase.auth.FirebaseAuth
import java.text.SimpleDateFormat
import java.util.*

class CreateAssignmentFragment : Fragment() {

    private var _binding : FragmentCreateAssignmentBinding? = null
    private val binding get() = _binding!!

    private lateinit var teacherDao: TeacherDao
    private lateinit var assignmentDao: AssignmentDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        _binding = FragmentCreateAssignmentBinding.inflate(inflater, container, false)

        teacherDao = TeacherDao()
        assignmentDao = AssignmentDao()
        binding.apply {


            createBtn.setOnClickListener {
                val course = etCourse.text.toString()
                val section = etSection.text.toString()
                val semester = etSemester.text.toString()
                val subject = etSubject.text.toString()
                val heading = etHeading.text.toString()
                val assign = etAssignment.text.toString()
                val dueDate = etDueDate.text.toString()

                Log.e("****", heading)

                teacherDao.getTeacherById(FirebaseAuth.getInstance().uid!!).addOnCompleteListener {

                    val temp = it.result
                    val name: String = temp.getString("name")!!

                    val sdf = SimpleDateFormat("dd/MM/yyyy hh:mm:ss")
                    val currentDate = sdf.format(Date())

                    val assignment = Assignment(
                        course,
                        subject,
                        semester,
                        section,
                        name,
                        heading,
                        assign,
                        dueDate,
                        currentDate,
                        course + semester + section
                    )

                    assignmentDao.addAssignment(assignment)
                }

                requireView().findNavController().popBackStack()

            }
        }

        return binding.root
    }




}