package com.example.projectx.daos

import android.util.Log
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

    fun isStudentRegistered(uid : String) : Boolean {
        var exist : Boolean = false
        val docIdRef = studentCollection.document(uid)
        docIdRef.get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val document = task.result
                exist = document.exists()
            } else {
                exist = false
            }
        }
        Log.e("exist value in student", exist.toString())
        return exist

    }
}