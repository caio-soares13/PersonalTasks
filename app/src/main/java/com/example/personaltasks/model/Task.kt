package com.example.personaltasks.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable
@Entity(tableName = "tasks")
data class Task(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val description: String,
    val deadline: String,
    var isConcluded: Boolean,
    var isDeleted: Boolean = false,
    var priority: String

) : Serializable
