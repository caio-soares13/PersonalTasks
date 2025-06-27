package com.example.personaltasks.controller.adapter


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.personaltasks.R
import com.example.personaltasks.model.Task

class DeletedTaskAdapter(
    private var tasks: List<Task> = listOf()
) : RecyclerView.Adapter<DeletedTaskAdapter.DeletedTaskViewHolder>() {

    inner class DeletedTaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textTitle: TextView = itemView.findViewById(R.id.textTitle)
        val textDescription: TextView = itemView.findViewById(R.id.textDescription)
        val textDeadline: TextView = itemView.findViewById(R.id.textDeadline)
        val textIsConcluded: TextView = itemView.findViewById(R.id.textIsConcluded)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeletedTaskViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_task, parent, false)
        return DeletedTaskViewHolder(view)
    }

    override fun onBindViewHolder(holder: DeletedTaskViewHolder, position: Int) {
        val task = tasks[position]
        holder.textTitle.text = task.title
        holder.textDescription.text = task.description
        holder.textDeadline.text = "Data limite: ${task.deadline}"
        holder.textIsConcluded.text = if (task.isConcluded) "Concluída" else "Pendente"
    }

    override fun getItemCount(): Int = tasks.size

    fun updateTasks(newTasks: List<Task>) {
        tasks = newTasks
        notifyDataSetChanged()
    }

    fun getCurrentTasks(): List<Task> = tasks
}
