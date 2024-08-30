package com.example.projemanag.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.projemanag.R
import com.example.projemanag.databinding.ItemCardSelectedMemberBinding
import com.example.projemanag.models.SelectedMembers
import com.example.projemanag.models.User

open class CardMemberListItemsAdapter(private val context: Context, private val list: ArrayList<SelectedMembers>, private val assignMembers: Boolean): RecyclerView.Adapter<CardMemberListItemsAdapter.MyViewHolder>() {

    private var onClickListener: OnClickListener? = null


    inner class MyViewHolder(val binding: ItemCardSelectedMemberBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemCardSelectedMemberBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val model = list[position]
        val binding = holder.binding

        if(position == list.size - 1 && assignMembers){
            binding.ivAddMember.visibility = View.VISIBLE
            binding.ivSelectedMemberImage.visibility = View.GONE
        }else{
            binding.ivAddMember.visibility = View.GONE
            binding.ivSelectedMemberImage.visibility = View.VISIBLE

            Glide
                .with(context)
                .load(model.image)
                .centerCrop()
                .placeholder(R.drawable.ic_user_place_holder)
                .into(binding.ivSelectedMemberImage)
        }
        holder.itemView.setOnClickListener {
            if(onClickListener != null){
                onClickListener!!.onClick()
            }
        }
    }

    fun setOnClickListener(onClickListener: OnClickListener){
        this.onClickListener = onClickListener
    }


    interface OnClickListener{
        fun onClick()
    }

}