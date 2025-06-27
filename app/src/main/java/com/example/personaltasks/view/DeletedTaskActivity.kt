package com.example.personaltasks.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.personaltasks.controller.TaskController
import com.example.personaltasks.controller.adapter.DeletedTaskAdapter
import com.example.personaltasks.data.DatabaseProvider
import com.example.personaltasks.data.TaskRepository
import com.example.personaltasks.databinding.ActivityDeletedTasksBinding
import com.example.personaltasks.model.Task
import kotlinx.coroutines.launch

class DeletedTasksActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDeletedTasksBinding
    private lateinit var adapter: DeletedTaskAdapter
    private lateinit var controller: TaskController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDeletedTasksBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = DeletedTaskAdapter(emptyList())
        binding.recyclerDeletedTasks.layoutManager = LinearLayoutManager(this)
        binding.recyclerDeletedTasks.adapter = adapter

        // Inicializa Controller
        val taskDao = DatabaseProvider.getDatabase(this).taskDao()
        val repository = TaskRepository(taskDao)
        controller = TaskController(repository,  null, deletedAdapter = adapter)

        loadDeletedTasks()
    }

    private fun loadDeletedTasks() {
        lifecycleScope.launch {
            val deletedTasks: List<Task> = controller.getDeletedTasks()
            adapter.updateTasks(deletedTasks)
        }
    }
}
