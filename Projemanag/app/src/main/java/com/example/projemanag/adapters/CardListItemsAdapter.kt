package com.example.projemanag.adapters

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.projemanag.activities.TaskListActivity
import com.example.projemanag.databinding.ItemCardBinding
import com.example.projemanag.models.Card
import com.example.projemanag.models.SelectedMembers

open class CardListItemsAdapter(private val context: Context, private var list: ArrayList<Card>): RecyclerView.Adapter<CardListItemsAdapter.MyViewHolder>() {

    private var onClickListener: OnClickListener? = null

    inner class MyViewHolder(val binding: ItemCardBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val model = list[position]
        val binding = holder.binding

        if(model.labelColor.isNotEmpty()){
            binding.viewLabelColor.visibility = View.VISIBLE
            binding.viewLabelColor.setBackgroundColor(Color.parseColor(model.labelColor))
        }else{
            binding.viewLabelColor.visibility = View.GONE
        }
        binding.tvCardName.text = model.name

        // Per visualizzare i membri sotto le card!
        if((context as TaskListActivity).mAssignedMemberDetailsList.size > 0){
            val selectedMembersList: ArrayList<SelectedMembers> =  ArrayList()
            for(i in context.mAssignedMemberDetailsList.indices){
                for(j in model.assignedTo){
                    if(context.mAssignedMemberDetailsList[i].id == j){
                        val selectedMembers = SelectedMembers(
                            context.mAssignedMemberDetailsList[i].id,
                            context.mAssignedMemberDetailsList[i].image
                        )
                        selectedMembersList.add(selectedMembers)
                    }
                }
            }
            if(selectedMembersList.size > 0){
                if (selectedMembersList.size == 1 && selectedMembersList[0].id == model.createdBy){
                    binding.rvCardSelectedMembersList.visibility = View.GONE
                }else{
                    binding.rvCardSelectedMembersList.visibility = View.VISIBLE

                    // Visualizzami solo 4 immagini.
                    binding.rvCardSelectedMembersList.layoutManager = GridLayoutManager(context,4)
                    val adapter = CardMemberListItemsAdapter(context,selectedMembersList,false)
                    binding.rvCardSelectedMembersList.adapter = adapter

                    adapter.setOnClickListener(object : CardMemberListItemsAdapter.OnClickListener{
                        override fun onClick() {
                            if(onClickListener != null){
                                onClickListener!!.onClick(position)
                            }
                        }
                    })
                }
            }else{
                binding.rvCardSelectedMembersList.visibility = View.GONE
            }
        }

        holder.itemView.setOnClickListener{
            if(onClickListener != null){
                onClickListener!!.onClick(position)
            }
        }

    }

    fun setOnClickListener(onClickListener: OnClickListener) {
        this.onClickListener = onClickListener
    }


    interface OnClickListener {
        fun onClick(position: Int)
    }

}