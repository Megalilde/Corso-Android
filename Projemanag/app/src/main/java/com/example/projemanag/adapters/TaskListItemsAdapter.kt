package com.example.projemanag.adapters

import android.content.Context
import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.projemanag.activities.TaskListActivity
import com.example.projemanag.databinding.ItemTaskBinding
import com.example.projemanag.models.Task
import java.util.Collections

open class TaskListItemsAdapter(private val context: Context, private var list: ArrayList<Task>): RecyclerView.Adapter<TaskListItemsAdapter.MyViewHolder>() {

    private var mPositionDraggedFrom = -1
    private var mPositionDraggedTo = -1

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
        binding.tvTaskListTitle.text = model.title
        binding.tvAddTaskList.setOnClickListener {
            binding.tvAddTaskList.visibility = View.GONE
            binding.cvAddTaskListName.visibility = View.VISIBLE
        }

        binding.ibCloseListName.setOnClickListener{
            binding.tvAddTaskList.visibility = View.VISIBLE
            binding.cvAddTaskListName.visibility = View.GONE
        }

        binding.ibDoneListName.setOnClickListener {
            val listName = binding.etTaskListName.text.toString()

            if(listName.isNotEmpty()){
                if(context is TaskListActivity){
                    context.createTaskList(listName)
                }
            }else{
                Toast.makeText(context, "Please Enter List Name.", Toast.LENGTH_SHORT).show()
            }
        }

        binding.ibEditListName.setOnClickListener {
            binding.etEditTaskListName.setText(model.title)
            binding.llTitleView.visibility = View.GONE
            binding.cvEditTaskListName.visibility = View.VISIBLE
        }

        binding.ibCloseEditableView.setOnClickListener {
            binding.llTitleView.visibility = View.VISIBLE
            binding.cvEditTaskListName.visibility = View.GONE
        }
        binding.ibDoneEditListName.setOnClickListener {
            val listName = binding.etEditTaskListName.text.toString()

            if(listName.isNotEmpty()){
                if(context is TaskListActivity){
                    context.updateTaskList(position, listName, model)
                }
            }else{
                Toast.makeText(context, "Please Enter List Name.", Toast.LENGTH_SHORT).show()
            }
        }
        binding.ibDeleteList.setOnClickListener {
            alertDialogForDeleteList(position,model.title)
        }

        binding.tvAddCard.setOnClickListener{
            binding.tvAddCard.visibility = View.GONE
            binding.cvAddCard.visibility = View.VISIBLE
        }

        binding.ibCloseCardName.setOnClickListener {
            binding.tvAddCard.visibility = View.VISIBLE
            binding.cvAddCard.visibility = View.GONE
        }

        binding.ibDoneCardName.setOnClickListener {
            val cardName = binding.etCardName.text.toString()

            if(cardName.isNotEmpty()){
                if(context is TaskListActivity){
                    context.addCardToTaskList(position,cardName)
                }
            }else{
                Toast.makeText(context, "Please Enter Card Name", Toast.LENGTH_SHORT).show()
            }
        }

        // Adapter per la lista delle Card

        binding.rvCardList.layoutManager = LinearLayoutManager(context)

        binding.rvCardList.setHasFixedSize(true)

        val adapter = CardListItemsAdapter(context,model.cards)
        binding.rvCardList.adapter = adapter

        adapter.setOnClickListener(
            object: CardListItemsAdapter.OnClickListener{
                override fun onClick(cardPosition: Int) {
                    if(context is TaskListActivity){
                        context.cardDetails(position, cardPosition)
                    }
                }
            }
        )

        // Parte relativa la Drag and Drop
        val dividerItemDecoration = DividerItemDecoration(context,DividerItemDecoration.VERTICAL)
        binding.rvCardList.addItemDecoration(dividerItemDecoration)
        val helper = ItemTouchHelper(object: ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,0)
            {
                override fun onMove(
                    recyclerView: RecyclerView,
                    dragged: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean {
                    // Quando moviamo l'item
                    val draggedPosition = dragged.adapterPosition
                    val targetPosition = target.adapterPosition
                    if (mPositionDraggedFrom == -1){
                        mPositionDraggedFrom == draggedPosition
                    }
                    mPositionDraggedTo = targetPosition
                    Collections.swap(list[position].cards, draggedPosition, targetPosition)
                    adapter.notifyItemMoved(draggedPosition,targetPosition)
                    return false
                }

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

                }

                override fun clearView(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder
                ) {
                    super.clearView(recyclerView, viewHolder)

                    if(mPositionDraggedFrom != -1 && mPositionDraggedTo != -1 && mPositionDraggedFrom == mPositionDraggedTo){
                        (context as TaskListActivity).updateCardsInTaskList(position, list[position].cards)
                        mPositionDraggedFrom = -1
                        mPositionDraggedTo = -1
                    }
                }

            }
        )
        helper.attachToRecyclerView(binding.rvCardList)



    }

    override fun getItemCount(): Int {
        return list.size
    }


    // Metodo è usato per visualizzare Alert Dialog quando cancelliamo il task dalla lista
    private fun alertDialogForDeleteList(position: Int, title: String) {
        val builder = AlertDialog.Builder(context)

        builder.setTitle("Alert")

        builder.setMessage("Are you sure you want to delete $title.")
        builder.setIcon(android.R.drawable.ic_dialog_alert)

        builder.setPositiveButton("Yes") { dialogInterface, which ->
            dialogInterface.dismiss() // Dialog will be dismissed

            if (context is TaskListActivity) {
                context.deleteTaskList(position)
            }
        }


        builder.setNegativeButton("No") { dialogInterface, which ->
            dialogInterface.dismiss()
        }

        val alertDialog: AlertDialog = builder.create()

        alertDialog.setCancelable(false)
        alertDialog.show()
    }

    // Prende la densita dello schermo e lo converte ad un Intero.
    private fun Int.toDp(): Int = (this / Resources.getSystem().displayMetrics.density).toInt()

    private fun Int.toPx(): Int = (this * Resources.getSystem().displayMetrics.density).toInt()

}