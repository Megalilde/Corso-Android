package com.example.projemanag.adapters

import android.content.Context
import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.projemanag.databinding.ItemTaskBinding
import com.example.projemanag.models.Task

open class TaskListItemsAdapter(private val context: Context, private var list: ArrayList<Task>): RecyclerView.Adapter<TaskListItemsAdapter.MyViewHolder>() {


    inner class MyViewHolder(val binding: ItemTaskBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemTaskBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val layoutParams = LinearLayout.LayoutParams((parent.width * 0.7).toInt(), LinearLayout.LayoutParams.WRAP_CONTENT)
        layoutParams.setMargins(
            (15.toDp()).toPx(),0,(40.toDp()).toPx(),0)
        binding.root.layoutParams = layoutParams
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val model = list[position]
        val binding = holder.binding

        if(position == list.size -1){
            binding.tvAddTaskList.visibility = View.VISIBLE
            binding.llTaskItem.visibility = View.GONE
        } else {
            binding.tvAddTaskList.visibility = View.GONE
            binding.llTaskItem.visibility = View.VISIBLE

        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    // Prende la densita dello schermo e lo converte ad un Intero.
    private fun Int.toDp(): Int = (this / Resources.getSystem().displayMetrics.density).toInt()

    private fun Int.toPx(): Int = (this * Resources.getSystem().displayMetrics.density).toInt()

}