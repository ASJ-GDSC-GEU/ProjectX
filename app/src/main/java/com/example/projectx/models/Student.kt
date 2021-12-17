package com.example.projectx.models

data class Student (val uid : String,
               val name : String,
               val subjects : List<Subject>,
               val imageUrl : String
               ){

}