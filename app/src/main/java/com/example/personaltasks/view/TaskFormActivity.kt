package com.example.personaltasks.view

import android.app.DatePickerDialog
import android.icu.util.Calendar
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.personaltasks.databinding.ActivityTaskFormBinding
import java.text.SimpleDateFormat
import java.util.Locale

class TaskFormActivity : AppCompatActivity(){
    private lateinit var binding: ActivityTaskFormBinding
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

        // Botão Cancelar
        binding.buttonCancel.setOnClickListener {
            finish()
        }

        // Botão Salvar (ainda vamos implementar o retorno de dados)
        binding.buttonSave.setOnClickListener {
            // TODO: Validar campos e devolver dados para a tela anterior
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