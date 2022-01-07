package com.example.projectx.daos

import com.example.projectx.models.Student
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class StudentDao {
    val db = FirebaseFirestore.getInstance()
    val studentCollection = db.collection("student")

    fun addStudent(student: Student?){
        student?.let {
            GlobalScope.launch(Dispatchers.IO) {
                studentCollection.document(student.uid).set(it)
            }
        }
    }

}