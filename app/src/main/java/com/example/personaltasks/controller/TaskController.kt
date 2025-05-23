package com.example.personaltasks.controller

import com.example.personaltasks.controller.adapter.TaskAdapter
import com.example.personaltasks.model.Task

class TaskController(private val taskList: MutableList<Task>, private val adapter: TaskAdapter) {

    fun addTask(task: Task) {
        taskList.add(task)
        adapter.notifyItemInserted(taskList.size - 1)
    }

    fun updateTask(updatedTask: Task) {
        val index = taskList.indexOfFirst { it.id == updatedTask.id }
        if (index != -1) {
            taskList[index] = updatedTask
            adapter.notifyItemChanged(index)
        }
    }

    fun deleteTask(task: Task) {
        val index = taskList.indexOf(task)
        if (index != -1) {
            taskList.removeAt(index)
            adapter.notifyItemRemoved(index)
        }
    }

    fun getTasks() = taskList.toList()
}