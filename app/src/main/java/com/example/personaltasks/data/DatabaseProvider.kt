package com.example.personaltasks.data

import android.content.Context
import androidx.room.Room

object DatabaseProvider {
    private var INSTANCE: TaskDatabase? = null

    fun getDatabase(context: Context): TaskDatabase {
        return INSTANCE ?: synchronized(this) {
            val instance = Room.databaseBuilder(
                context.applicationContext,
                TaskDatabase::class.java,
                "task_database"
            )
                .fallbackToDestructiveMigration()
                .build()
            INSTANCE = instance
            instance
        }
    }
}