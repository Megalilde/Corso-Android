package com.example.projemanag.adapters

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.projemanag.databinding.ItemCardBinding
import com.example.projemanag.models.Card

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