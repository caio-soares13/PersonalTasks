package com.example.personaltasks.view

import android.app.DatePickerDialog
import android.content.Intent
import android.icu.util.Calendar
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.personaltasks.controller.TaskController
import com.example.personaltasks.databinding.ActivityTaskFormBinding
import com.example.personaltasks.model.Task
import java.text.SimpleDateFormat
import java.util.Locale

class TaskFormActivity : AppCompatActivity(){
    private lateinit var binding: ActivityTaskFormBinding
    private lateinit var taskController: TaskController
    private val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    private val calendar = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTaskFormBinding.inflate(layoutInflater)
        setContentView(binding.root)
        

        // Abre o DatePicker ao clicar no campo de data
        binding.editDeadline.setOnClickListener {
            showDatePicker()
        }

        val task = intent.getSerializableExtra("task") as? Task
        val mode = intent.getStringExtra("mode")

        if (task != null) {
            // Preenche os campos com os dados da tarefa
            binding.editTitle.setText(task.title)
            binding.editDescription.setText(task.description)
            binding.editDeadline.setText(task.deadline)

            if (mode == "details") {
                // Desabilita os campos para apenas visualização
                binding.editTitle.isEnabled = false
                binding.editDescription.isEnabled = false
                binding.editDeadline.isEnabled = false

                // Esconde o botão salvar
                binding.buttonSave.visibility = View.GONE
            }
        }

        // Botão Cancelar
        binding.buttonCancel.setOnClickListener {
            finish()
        }

        // Botão Salvar
        binding.buttonSave.setOnClickListener {
            if (mode == "details") {
                // Em modo detalhes, botão salvar está escondido, mas só pra garantir
                return@setOnClickListener
            }

            val title = binding.editTitle.text.toString().trim()
            val description = binding.editDescription.text.toString().trim()
            val deadline = binding.editDeadline.text.toString().trim()

            if (title.isEmpty()) {
                binding.editTitle.error = "Título é obrigatório"
                return@setOnClickListener
            }

            val originalTask = intent.getSerializableExtra("task") as? Task
            val mode = intent.getStringExtra("mode")

            val task = Task(
                id = originalTask?.id ?: 0,
                title = title,
                description = description,
                deadline = deadline
            )

            val intent = Intent()
            intent.putExtra("task", task)
            intent.putExtra("mode", mode)

            if (mode == "edit") {
                Toast.makeText(this, "Tarefa editada com sucesso", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Tarefa criada com sucesso", Toast.LENGTH_SHORT).show()
            }

            setResult(RESULT_OK, intent)
            finish()
        }
    }

    private fun showDatePicker() {
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        DatePickerDialog(this, { _, selectedYear, selectedMonth, selectedDay ->
            calendar.set(selectedYear, selectedMonth, selectedDay)
            binding.editDeadline.setText(dateFormat.format(calendar.time))
        }, year, month, day).show()
    }
}