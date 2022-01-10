package com.example.projectx.models

import java.sql.Timestamp

data class ChatPersonData(
    val username : String,
    val timestamp: Timestamp,
    val message : String
)
