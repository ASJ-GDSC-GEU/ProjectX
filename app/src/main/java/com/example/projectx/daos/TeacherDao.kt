package com.example.projectx.daos

import android.util.Log
import com.example.projectx.models.MyClass
import com.example.projectx.models.Teacher
import com.example.projectx.screens.ClassGroupFragment
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
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

//    fun setDataInGroup(class_id: String) {
//        class_id.let {
//            GlobalScope.launch(Dispatchers.IO) {
//                TopDao().dbRef().collection("classes").document(class_id).get()
//                    .addOnSuccessListener { documentSnapshot ->
//                        val class_obj = documentSnapshot.toObject<MyClass>()
//                        // Toast.makeText(requireView().context, "$class_id", Toast.LENGTH_SHORT).show()
//                        ClassGroupFragment().optionsa(class_obj)
//                    }
//            }
//        }
//    }
}


