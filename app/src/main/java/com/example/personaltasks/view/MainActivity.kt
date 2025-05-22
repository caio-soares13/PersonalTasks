package com.example.personaltasks.view

import android.os.Bundle
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.personaltasks.R
import com.example.personaltasks.databinding.ActivityMainBinding
import com.example.personaltasks.model.Task
import com.example.personaltasks.view.adapter.TaskAdapter

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: TaskAdapter
    private var taskList = mutableListOf<Task>()  // substitua por dados do banco depois
    private var selectedTask: Task? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Exemplo com tarefas de teste
        taskList.add(Task(1, "Estudar", "Estudar para prova de Kotlin", "25/05/2025"))
        taskList.add(Task(2, "Comprar leite", "Ir ao mercado", "22/05/2025"))

        adapter = TaskAdapter(taskList) { view, task ->
            selectedTask = task
            registerForContextMenu(view)  // necessário antes de abrir menu
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
                Toast.makeText(this, "Nova tarefa (abrir Tela 2)", Toast.LENGTH_SHORT).show()
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
        return when (item.itemId) {
            R.id.menu_edit -> {
                Toast.makeText(this, "Editar: ${selectedTask?.title}", Toast.LENGTH_SHORT).show()
                true
            }
            R.id.menu_delete -> {
                Toast.makeText(this, "Excluir: ${selectedTask?.title}", Toast.LENGTH_SHORT).show()
                true
            }
            R.id.menu_details -> {
                Toast.makeText(this, "Detalhes: ${selectedTask?.title}", Toast.LENGTH_SHORT).show()
                true
            }
            else -> super.onContextItemSelected(item)
        }
    }
}