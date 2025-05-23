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
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.personaltasks.R
import com.example.personaltasks.databinding.ActivityMainBinding
import com.example.personaltasks.model.Task
import com.example.personaltasks.controller.adapter.TaskAdapter

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: TaskAdapter
    private var taskList = mutableListOf<Task>()  // substitua por dados do banco depois
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
                    // atualizar a tarefa na lista
                    val index = taskList.indexOfFirst { t -> t.id == it.id }
                    if (index != -1) {
                        taskList[index] = it
                        adapter.notifyItemChanged(index)
                    }
                } else {
                    // nova tarefa
                    taskList.add(it)
                    adapter.notifyItemInserted(taskList.size - 1)
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Exemplo com tarefas de teste
        taskList.add(Task(1, "Estudar", "Estudar para prova de Kotlin", "25/05/2025"))
        taskList.add(Task(2, "Comprar leite", "Ir ao mercado", "22/05/2025"))

        adapter = TaskAdapter(taskList) { view, task ->
            Log.d("MainActivity", "Clique longo em: ${task.title}")
            selectedTask = task
            selectedTaskPosition = taskList.indexOf(task)
            openContextMenu(view)
        }

        binding.recyclerTasks.layoutManager = LinearLayoutManager(this)
        binding.recyclerTasks.adapter = adapter
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
}