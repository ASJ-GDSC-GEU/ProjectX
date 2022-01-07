package com.example.projectx.daos

import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

class TopDao {
    private val db = FirebaseFirestore.getInstance()
    private val userId = Firebase.auth.currentUser!!.uid


    fun userId(): String {
        return userId
    }
    fun dbRef(): FirebaseFirestore {
        return db
    }
}
