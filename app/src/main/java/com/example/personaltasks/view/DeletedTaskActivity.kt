package com.example.personaltasks.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.personaltasks.controller.adapter.DeletedTaskAdapter
import com.example.personaltasks.databinding.ActivityDeletedTasksBinding
import com.example.personaltasks.model.Task

class DeletedTasksActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDeletedTasksBinding
    private lateinit var adapter: DeletedTaskAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDeletedTasksBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val mockTasks = listOf(
            Task(title = "Tarefa 1", description = "Mockada 1", deadline = "01/07/2025", isConcluded = false, isDeleted = true),
            Task(title = "Tarefa 2", description = "Mockada 2", deadline = "05/07/2025", isConcluded = false, isDeleted = true),
            Task(title = "Tarefa 3", description = "Mockada 3", deadline = "10/07/2025", isConcluded = false, isDeleted = true)
        )

        adapter = DeletedTaskAdapter(mockTasks)

        binding.recyclerDeletedTasks.layoutManager = LinearLayoutManager(this)
        binding.recyclerDeletedTasks.adapter = adapter
    }
}
