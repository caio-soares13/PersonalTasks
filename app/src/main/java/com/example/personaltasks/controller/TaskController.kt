package com.example.personaltasks.controller

import android.util.Log
import com.example.personaltasks.controller.adapter.DeletedTaskAdapter
import com.example.personaltasks.controller.adapter.TaskAdapter
import com.example.personaltasks.data.TaskRepository
import com.example.personaltasks.model.Task

class TaskController(private val taskRepository: TaskRepository,
                     private val adapter: TaskAdapter,
                     private val deletedAdapter: DeletedTaskAdapter?) {

    suspend fun addTask(task: Task) {
        Log.d("TaskRepository", "Salvando task no banco: $task")
        taskRepository.insertTask(task)
        refreshActiveTasks()
    }

    suspend fun updateTask(updatedTask: Task) {
        Log.d("TaskRepository", "Alterando task no banco: $updatedTask")
        taskRepository.updateTask(updatedTask)
        refreshActiveTasks()
    }

    suspend fun deleteTask(task: Task) {
        Log.d("TaskRepository", "Deletando task: $task")
        val deletedTask = task.copy(isDeleted = true)
        taskRepository.updateTask(deletedTask)
        refreshActiveTasks()
    }


    suspend fun getActiveTasks(): List<Task> {
        Log.d("TaskRepository", "Tasks retornadas do banco")
        return taskRepository.getAllActiveTasks()
    }

    suspend fun getDeletedTasks(): List<Task> {
        Log.d("TaskRepository", "Tasks retornadas do banco")
        return taskRepository.getAllDeletedTasks()
    }

    suspend fun refreshActiveTasks() {
        val tasks = taskRepository.getAllActiveTasks()
        adapter.updateTasks(tasks)
    }


}