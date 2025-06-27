package com.example.personaltasks.view

import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.personaltasks.R
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

        adapter = DeletedTaskAdapter(
            tasks = emptyList(),
            onReactivate = { task ->
                lifecycleScope.launch {
                    controller.reactivateTask(task)
                    loadDeletedTasks()
                    Toast.makeText(this@DeletedTasksActivity, "Tarefa reativada: ${task.title}", Toast.LENGTH_SHORT).show()
                }
            },
            onDetails = { task ->
                Toast.makeText(this@DeletedTasksActivity, "Detalhes: ${task.title}", Toast.LENGTH_SHORT).show()
            }
        )
        binding.recyclerDeletedTasks.layoutManager = LinearLayoutManager(this)
        binding.recyclerDeletedTasks.adapter = adapter

        registerForContextMenu(binding.recyclerDeletedTasks)
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
    override fun onContextItemSelected(item: MenuItem): Boolean {
        val position = adapter.getSelectedPosition()  // você precisa implementar esse método no adapter
        val tasks = adapter.getCurrentTasks()

        if (position == -1 || position >= tasks.size) return super.onContextItemSelected(item)

        val task = tasks[position]

        return when (item.itemId) {
            R.id.menu_reactivate -> {
                lifecycleScope.launch {
                    controller.reactivateTask(task)
                    loadDeletedTasks() // atualizar lista
                }
                Toast.makeText(this, "Tarefa reativada: ${task.title}", Toast.LENGTH_SHORT).show()
                true
            }
            R.id.menu_details -> {
                // Abrir tela de detalhes ou algo similar
                Toast.makeText(this, "Detalhes: ${task.title}", Toast.LENGTH_SHORT).show()
                true
            }
            else -> super.onContextItemSelected(item)
        }
    }

}
