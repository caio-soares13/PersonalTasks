package com.example.personaltasks.controller.adapter


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.personaltasks.R
import com.example.personaltasks.model.Task

class DeletedTaskAdapter(
    private var tasks: List<Task>,
    private val onReactivate: (Task) -> Unit,
    private val onDetails: (Task) -> Unit
) : RecyclerView.Adapter<DeletedTaskAdapter.DeletedTaskViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeletedTaskViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_task, parent, false)
        return DeletedTaskViewHolder(view)
    }

    override fun onBindViewHolder(holder: DeletedTaskViewHolder, position: Int) {
        holder.bind(tasks[position])
    }

    override fun getItemCount(): Int = tasks.size

    fun updateTasks(newTasks: List<Task>) {
        tasks = newTasks
        notifyDataSetChanged()
    }

    inner class DeletedTaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnLongClickListener {
        private val titleText: TextView = itemView.findViewById(R.id.textTitle)
        private val descriptionText: TextView = itemView.findViewById(R.id.textDescription)
        private val deadlineText: TextView = itemView.findViewById(R.id.textDeadline)

        init {
            itemView.setOnLongClickListener(this)
        }

        fun bind(task: Task) {
            titleText.text = task.title
            descriptionText.text = task.description
            deadlineText.text = task.deadline
        }

        override fun onLongClick(v: View?): Boolean {
            val task = tasks[adapterPosition]
            val popup = PopupMenu(itemView.context, itemView)
            popup.menuInflater.inflate(R.menu.menu_deleted_task, popup.menu)
            popup.setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.menu_reactivate -> {
                        onReactivate(task)
                        true
                    }
                    R.id.menu_details -> {
                        onDetails(task)
                        true
                    }
                    else -> false
                }
            }
            popup.show()
            return true
        }
    }
}
