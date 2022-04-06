package com.ssafy.memo

import android.annotation.SuppressLint
import android.util.Log
import android.view.ContextMenu
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView

class MemoAdapter(val memoList: MutableList<MemoDto>, val onMemoClickListener: OnMemoClickListener) : RecyclerView.Adapter<MemoAdapter.MemoViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MemoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.memo_list_item,parent, false)
        return MemoViewHolder(view)
    }

    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(holder: MemoViewHolder, position: Int) {
        // 구현
        holder.titleTextView.apply{
            when(position%3){
                0 ->{
                    setBackgroundResource(R.color.purple_200)
                }
                1->{
                    setBackgroundResource(R.color.teal_200)
                }
                2->{
                    setBackgroundResource(R.color.yellow)
                }
            }
        }
        holder.titleTextView.text = memoList[position].title
        holder.contentTextView.text = memoList[position].content
        holder.dateTextView.text = memoList[position].date.toString()
        holder.memoLayout.setOnClickListener {
            onMemoClickListener.onMemoClick(position)
        }
    }

    override fun getItemCount(): Int = memoList.size



    class MemoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnCreateContextMenuListener {
        // 작성하기
        val customRecyclerView = itemView.findViewById<LinearLayout>(R.id.customRecyclerViewItem)
        val titleTextView = itemView.findViewById<TextView>(R.id.titleTextView)
        val contentTextView = itemView.findViewById<TextView>(R.id.contentTextView)
        val dateTextView = itemView.findViewById<TextView>(R.id.dateTextView)
        val memoLayout = itemView.findViewById<LinearLayout>(R.id.memoLayout)


        override fun onCreateContextMenu(
            menu: ContextMenu?,
            v: View?,
            menuInfo: ContextMenu.ContextMenuInfo?
        ) {
            menu?.add(0,0,adapterPosition,"삭제하기")
        }
        // Context 메뉴 리스너 등록
        init {
            itemView.setOnCreateContextMenuListener(this)
        }
    }

}
