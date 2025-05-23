package com.example.personaltasks.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.personaltasks.model.Task

@Database(entities = [Task::class], version = 1)
abstract class TaskDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDao
}