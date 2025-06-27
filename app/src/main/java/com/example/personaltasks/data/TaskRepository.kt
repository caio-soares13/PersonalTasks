package com.example.personaltasks.data

import com.example.personaltasks.model.Task

class TaskRepository(private val taskDao: TaskDao) {

    suspend fun getAllActiveTasks(): List<Task> = taskDao.getAllActiveTasks()

    suspend fun getAllDeletedTasks(): List<Task> = taskDao.getAllDeletedTasks()

    suspend fun insertTask(task: Task): Long = taskDao.insertTask(task)

    suspend fun updateTask(task: Task) = taskDao.updateTask(task)

    suspend fun deleteTask(task: Task) = taskDao.deleteTask(task)
}