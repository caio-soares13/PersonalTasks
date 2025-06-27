package com.example.personaltasks.controller.adapter


import android.view.ContextMenu
import android.view.LayoutInflater
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.personaltasks.R
import com.example.personaltasks.model.Task
import android.view.View.OnCreateContextMenuListener

class DeletedTaskAdapter(
    private var tasks: List<Task> = listOf(),
    private val onReactivate: (Task) -> Unit,
    private val onDetails: (Task) -> Unit
) : RecyclerView.Adapter<DeletedTaskAdapter.DeletedTaskViewHolder>() {

    private var selectedPosition = -1

    inner class DeletedTaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnCreateContextMenuListener  {
        val textTitle: TextView = itemView.findViewById(R.id.textTitle)
        val textDescription: TextView = itemView.findViewById(R.id.textDescription)
        val textDeadline: TextView = itemView.findViewById(R.id.textDeadline)
        val textIsConcluded: TextView = itemView.findViewById(R.id.textIsConcluded)
        val textPriority: TextView = itemView.findViewById(R.id.textPriority)

        init {
            // Salva a posição do item clicado para o menu de contexto
            itemView.setOnLongClickListener {
                selectedPosition = adapterPosition
                itemView.showContextMenu()
                true
            }
            // Associa o menu de contexto à view
            itemView.setOnCreateContextMenuListener(this)
        }

        override fun onCreateContextMenu(menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?) {
            val inflater = MenuInflater(itemView.context)
            inflater.inflate(R.menu.menu_deleted_task, menu)
        }
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
        holder.textPriority.text = task.priority
    }

    override fun getItemCount(): Int = tasks.size

    fun updateTasks(newTasks: List<Task>) {
        tasks = newTasks
        notifyDataSetChanged()
    }

    fun getCurrentTasks(): List<Task> = tasks
    fun getSelectedPosition() = selectedPosition
}
