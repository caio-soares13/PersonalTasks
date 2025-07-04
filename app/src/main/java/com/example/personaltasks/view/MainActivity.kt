package com.example.personaltasks.view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.personaltasks.R
import com.example.personaltasks.controller.TaskController
import com.example.personaltasks.controller.adapter.DeletedTaskAdapter
import com.example.personaltasks.databinding.ActivityMainBinding
import com.example.personaltasks.model.Task
import com.example.personaltasks.controller.adapter.TaskAdapter
import com.example.personaltasks.data.DatabaseProvider
import com.example.personaltasks.data.TaskRepository
import com.google.firebase.FirebaseApp
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: TaskAdapter
    private lateinit var controller: TaskController

    private var selectedTask: Task? = null
    private var selectedTaskPosition: Int = -1

    private val taskFormLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val task = result.data?.getSerializableExtra("task") as? Task
            val mode = result.data?.getStringExtra("mode")
            task?.let {
                if (mode == "edit") {
                    lifecycleScope.launch {
                        controller.updateTask(it)
                        loadTasks()
                    }

                } else {
                    lifecycleScope.launch {
                        controller.addTask(it)
                        loadTasks()
                    }
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        FirebaseApp.initializeApp(this)

        // Verifica se inicializou
        if (FirebaseApp.getApps(this).isNotEmpty()) {
            Toast.makeText(this, "Firebase inicializado com sucesso!", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Erro na inicialização do Firebase!", Toast.LENGTH_SHORT).show()
        }

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = TaskAdapter { view, task ->
            selectedTask = task
            selectedTaskPosition = adapter.getCurrentTasks().indexOf(task)
            openContextMenu(view)
        }

        val taskDao = DatabaseProvider.getDatabase(this).taskDao()
        val repository = TaskRepository(taskDao)

        controller = TaskController(repository, adapter, null)

        binding.recyclerTasks.layoutManager = LinearLayoutManager(this)
        binding.recyclerTasks.adapter = adapter

        loadTasks()

    }

    private fun loadTasks() {
        lifecycleScope.launch {
            val tasksFromDb = controller.getActiveTasks()
            adapter.updateTasks(tasksFromDb)
        }
    }

    // Menu de Opções (Topo)
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_new_task -> {
                val intent = Intent(this, TaskFormActivity::class.java)
                taskFormLauncher.launch(intent)
                true
            }
            R.id.menu_deleted_tasks -> {
                val intent = Intent(this, DeletedTasksActivity::class.java)
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    // Menu de Contexto (Clique Longo)
    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        menuInflater.inflate(R.menu.menu_context_task, menu)
    }


    override fun onContextItemSelected(item: MenuItem): Boolean {
        if (selectedTask == null) return super.onContextItemSelected(item)

        return when (item.itemId) {
            R.id.menu_edit -> {
                Log.d("MainActivity", "Editar clicado: ${selectedTask?.title}")
                val intent = Intent(this, TaskFormActivity::class.java)
                intent.putExtra("task", selectedTask)
                intent.putExtra("mode", "edit")
                taskFormLauncher.launch(intent)
                true
            }
            R.id.menu_delete -> {
                Log.d("MainActivity", "Excluir clicado: ${selectedTask?.title}")
                selectedTask?.let {
                    lifecycleScope.launch {
                        controller.deleteTask(it)
                        loadTasks()
                    }
                }

                selectedTaskPosition = -1
                Toast.makeText(this, "Tarefa excluída", Toast.LENGTH_SHORT).show()
                true
            }
            R.id.menu_details -> {
                Log.d("MainActivity", "Detalhes clicado: ${selectedTask?.title}")
                selectedTask?.let { task ->
                    val intent = Intent(this, TaskFormActivity::class.java)
                    intent.putExtra("task", task)
                    intent.putExtra("mode", "details")  // indicar que é modo detalhes
                    startActivity(intent)
                }
                true
            }
            else -> super.onContextItemSelected(item)
        }
    }
    override fun onResume() {
        super.onResume()
        lifecycleScope.launch {
            controller.refreshActiveTasks()
        }
    }
}