package com.ssafy.ssafygo.receipt

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.ssafygo.databinding.ListItemReceiptBinding
import com.ssafy.ssafygo.dto.ReceiptDTO

class ReceiptAdapter: ListAdapter<ReceiptDTO, ReceiptAdapter.ItemViewHolder>(diffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder=
        ItemViewHolder(ListItemReceiptBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    inner class ItemViewHolder(private val binding: ListItemReceiptBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(receiptDTO: ReceiptDTO) {
            Log.d("?????", "bind: $receiptDTO")
            with(binding){
                nameTextView.text = receiptDTO.menuName
                dateTextView.text = receiptDTO.orderDate
                priceTextView.text = receiptDTO.price.toString()
            }
        }
    }

    companion object{
        val diffUtil = object : DiffUtil.ItemCallback<ReceiptDTO>(){
            override fun areItemsTheSame(oldItem: ReceiptDTO, newItem: ReceiptDTO): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: ReceiptDTO, newItem: ReceiptDTO): Boolean {
                return oldItem == newItem
            }

        }
    }


}