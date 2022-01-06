package com.example.projectx.daos

import android.util.Log
import com.example.projectx.models.Teacher
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class TeacherDao {
    val db = FirebaseFirestore.getInstance()
    val studentCollection = db.collection("teacher")

    fun addTeacher(teacher: Teacher){
        teacher?.let {
            GlobalScope.launch(Dispatchers.IO) {
                studentCollection.document(teacher.uid).set(it)
            }
        }
    }

    fun isTeacherRegistered(uid : String) : Boolean {
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