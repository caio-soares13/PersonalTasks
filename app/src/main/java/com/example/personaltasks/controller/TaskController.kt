package com.example.personaltasks.controller

import android.util.Log
import com.example.personaltasks.controller.adapter.TaskAdapter
import com.example.personaltasks.data.TaskRepository
import com.example.personaltasks.model.Task

class TaskController(private val taskRepository: TaskRepository, private val adapter: TaskAdapter) {

    suspend fun addTask(task: Task) {
        Log.d("TaskRepository", "Salvando task no banco: $task")
        taskRepository.insertTask(task)
        refreshTasks()
    }

    suspend fun updateTask(updatedTask: Task) {
        Log.d("TaskRepository", "Alterando task no banco: $updatedTask")
        taskRepository.updateTask(updatedTask)
        refreshTasks()
    }

    suspend fun deleteTask(task: Task) {
        Log.d("TaskRepository", "Deletando task no banco: $task")
        taskRepository.deleteTask(task)
        refreshTasks()
    }

    suspend fun getTasks(): List<Task> {
        Log.d("TaskRepository", "Tasks retornadas do banco")
        return taskRepository.getAllTasks()
    }

    suspend fun refreshTasks() {
        val tasks = taskRepository.getAllTasks()
        adapter.updateTasks(tasks)
    }
}