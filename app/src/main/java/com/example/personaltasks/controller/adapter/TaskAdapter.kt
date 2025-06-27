package com.example.personaltasks.controller.adapter

import android.view.LayoutInflater
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.personaltasks.R
import com.example.personaltasks.model.Task

class TaskAdapter(
    private var tasks: MutableList<Task> = mutableListOf(),
    private val onItemLongClick: (View, Task) -> Unit
) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    inner class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textTitle: TextView = itemView.findViewById(R.id.textTitle)
        val textDescription: TextView = itemView.findViewById(R.id.textDescription)
        val textDeadline: TextView = itemView.findViewById(R.id.textDeadline)
        val textIsConcluded: TextView = itemView.findViewById(R.id.textIsConcluded)
        val textPriority: TextView = itemView.findViewById(R.id.textPriority)

        init {
            itemView.setOnLongClickListener {
                onItemLongClick(it, tasks[adapterPosition])
                true
            }
            itemView.setOnCreateContextMenuListener { menu, _, _ ->
                val inflater = MenuInflater(itemView.context)
                inflater.inflate(R.menu.menu_context_task, menu)
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_task, parent, false)
        return TaskViewHolder(view)
    }

    override fun getItemCount() = tasks.size

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = tasks[position]
        holder.textTitle.text = task.title
        holder.textDescription.text = task.description
        holder.textDeadline.text = "Data limite: ${task.deadline}"
        if (task.isConcluded){
            holder.textIsConcluded.text = "Concluída"
        } else{
            holder.textIsConcluded.text = "Pendente"
        }
        holder.textPriority.text = task.priority

    }

    fun updateTasks(newTasks: List<Task>) {
        tasks.clear()
        tasks.addAll(newTasks)
        notifyDataSetChanged()
    }

    fun getCurrentTasks(): List<Task> = tasks
}