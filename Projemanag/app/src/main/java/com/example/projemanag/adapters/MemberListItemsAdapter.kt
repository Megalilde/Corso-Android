package com.example.projemanag.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.projemanag.R
import com.example.projemanag.databinding.ItemMemberBinding
import com.example.projemanag.models.User
import com.example.projemanag.utils.Constants

open class MemberListItemsAdapter(private val context: Context, private var list: ArrayList<User>): RecyclerView.Adapter<MemberListItemsAdapter.MyViewHolder>() {


    private var onClickListener: OnClickListener? = null

    inner class MyViewHolder(val binding: ItemMemberBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemMemberBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val model = list[position]
        val binding = holder.binding

        Glide
            .with(context)
            .load(model.image)
            .centerCrop()
            .placeholder(R.drawable.ic_user_place_holder)
            .into(binding.ivMemberImage)

        binding.tvMemberName.text = model.name
        binding.tvMemberEmail.text = model.email

        if(model.selected){
            binding.ivSelectedMember.visibility = View.VISIBLE
        }else{
            binding.ivSelectedMember.visibility = View.GONE
        }

        holder.itemView.setOnClickListener {
            if(onClickListener != null){
                if(model.selected){
                    onClickListener!!.onClick(position, model, Constants.UN_SELECT)
                }else{
                    onClickListener!!.onClick(position, model, Constants.SELECT)
                }
            }
        }
    }

    fun setOnClickListener(onClickListener: OnClickListener){
        this.onClickListener = onClickListener
    }

    interface OnClickListener{
        fun onClick(position: Int, user: User, action: String)
    }
}