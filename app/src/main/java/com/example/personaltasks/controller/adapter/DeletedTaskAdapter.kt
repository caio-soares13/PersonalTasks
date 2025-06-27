package com.example.personaltasks.controller.adapter


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.personaltasks.R
import com.example.personaltasks.model.Task

class DeletedTaskAdapter(private var tasks: List<Task>) : RecyclerView.Adapter<DeletedTaskAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textTitle: TextView = itemView.findViewById(R.id.textTitle)
        val textDescription: TextView = itemView.findViewById(R.id.textDescription)
        val textDeadline: TextView = itemView.findViewById(R.id.textDeadline)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_task, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val task = tasks[position]
        holder.textTitle.text = task.title
        holder.textDescription.text = task.description
        holder.textDeadline.text = "Data limite: ${task.deadline}"
    }

    override fun getItemCount(): Int = tasks.size
}
