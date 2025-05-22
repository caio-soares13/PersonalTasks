package com.example.personaltasks.model

import java.io.Serializable

data class Task(
    val id: Int = 0,
    val title: String,
    val description: String,
    val deadline: String

) : Serializable