package com.example.personaltasks.data

import com.example.personaltasks.model.Task
import com.google.firebase.firestore.FirebaseFirestore

class TaskRepository(private val taskDao: TaskDao) {
    private val firestore = FirebaseFirestore.getInstance()
    private val collection = firestore.collection("tasks")

    suspend fun getAllActiveTasks(): List<Task> {
        return taskDao.getAllActiveTasks()
    }

    suspend fun getAllDeletedTasks(): List<Task> {
        return taskDao.getAllDeletedTasks()
    }

    suspend fun insertTask(task: Task) {
        taskDao.insertTask(task)
        collection.document(task.id.toString()).set(task)
    }

    suspend fun updateTask(task: Task) {
        taskDao.updateTask(task)
        collection.document(task.id.toString()).set(task)
    }

    suspend fun deleteTask(task: Task) {
        taskDao.deleteTask(task)
        collection.document(task.id.toString()).delete()
    }
}