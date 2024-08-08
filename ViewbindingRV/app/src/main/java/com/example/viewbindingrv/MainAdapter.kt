package com.example.viewbindingrv

import android.app.AlertDialog
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.viewbindingrv.databinding.RecyclerviewItemBinding
import com.google.android.material.snackbar.Snackbar

class MainAdapter(val taskList:List<Task>, val context: Context):RecyclerView.Adapter<MainAdapter.MainViewHolder>() {


    inner class MainViewHolder(val itemBinding: RecyclerviewItemBinding) : RecyclerView.ViewHolder(itemBinding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        return MainViewHolder(RecyclerviewItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val task = taskList[position]
        holder.itemBinding.tvTitle.text = holder.itemBinding.root.context.getString(R.string.title_label) + task.title
        holder.itemBinding.tvTime.text = holder.itemBinding.root.context.getString(R.string.timestamp_label) + task.timestamp

        holder.itemBinding.root.setOnClickListener {
            Log.d("MainAdapter", "Clicked: ${task.title}")

            // Toast.makeText(context, "Clicked: ${task.title}", Toast.LENGTH_LONG).show()
            // showTaskDialog(task)
            showSnackBar(holder.itemBinding.root, task)
        }

    }

    override fun getItemCount(): Int {
        return taskList.size
    }


    private fun showTaskDialog(task: Task) {
        val dialogBuilder = AlertDialog.Builder(context)
        dialogBuilder.setMessage("Details:\n${task.title} \n${task.timestamp}")
        dialogBuilder.setPositiveButton("OK"){ dialog, _ ->
            dialog.dismiss()
        }
        val alertDialog = dialogBuilder.create()
        alertDialog.show()

    }

    private fun showSnackBar(view: View, task:Task){
        var snackbar = Snackbar.make(view, "${task.title} \n" +
                "${task.timestamp}", Snackbar.LENGTH_SHORT).show()
    }
}