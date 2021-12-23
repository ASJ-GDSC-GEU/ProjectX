package com.example.projectx.screens

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.view.View
import android.widget.RadioButton
import androidx.appcompat.app.AppCompatActivity
import com.example.projectx.MainActivity
import com.example.projectx.R
import com.example.projectx.daos.StudentDao
import com.example.projectx.daos.TeacherDao
import com.example.projectx.databinding.ActivityDetailsBinding
import com.example.projectx.models.Student
import com.example.projectx.models.Teacher
import com.google.firebase.auth.FirebaseAuth

class DetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailsBinding

    private lateinit var auth: FirebaseAuth
    private lateinit var userName: String

    private var selectedUser: Int = 0 // 0 for Student --- 1 for Teacher

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

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
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun goToTeacherHomeScreen(){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }


}