package com.example.personaltasks.view

import android.content.Intent
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.personaltasks.controller.TaskController
import com.example.personaltasks.controller.adapter.TaskAdapter
import com.example.personaltasks.data.DatabaseProvider
import com.example.personaltasks.model.Task
import com.example.personaltasks.data.TaskRepository
import com.example.personaltasks.R
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

class DeletedTasksActivity : AppCompatActivity(){
    private lateinit var binding: ActivityDeletedTasksBinding
    private lateinit var adapter: TaskAdapter
    private lateinit var controller: TaskController

    private var selectedTask: Task? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDeletedTasksBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = TaskAdapter { view, task ->
            selectedTask = task
            openContextMenu(view)
        }

        binding.recyclerDeletedTasks.layoutManager = LinearLayoutManager(this)
        binding.recyclerDeletedTasks.adapter = adapter

        val taskDao = DatabaseProvider.getDatabase(this).taskDao()
        val repository = TaskRepository(taskDao)
        controller = TaskController(repository, adapter)

        loadDeletedTasks()
    }

    private fun loadDeletedTasks() {
        lifecycleScope.launch {
            val deletedTasks = controller.getDeletedTasks()
            adapter.updateTasks(deletedTasks)
        }
    }

    override fun onCreateContextMenu(menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?) {
        super.onCreateContextMenu(menu, v, menuInfo)
        menuInflater.inflate(R.menu.menu_context_deleted_task, menu)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        if (selectedTask == null) return super.onContextItemSelected(item)

        return when (item.itemId) {
            R.id.menu_reactivate -> {
                selectedTask?.let {
                    it.isDeleted = false
                    lifecycleScope.launch {
                        controller.updateTask(it)
                        loadDeletedTasks()
                    }
                }
                true
            }
            R.id.menu_details -> {
                val intent = Intent(this, TaskFormActivity::class.java)
                intent.putExtra("task", selectedTask)
                intent.putExtra("mode", "details")
                startActivity(intent)
                true
            }
            else -> super.onContextItemSelected(item)
        }
    }
}