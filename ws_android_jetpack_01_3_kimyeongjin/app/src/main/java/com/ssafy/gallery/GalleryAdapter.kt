package com.ssafy.gallery

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.gallery.databinding.ListItemGalleryBinding

class GalleryAdapter(val photoClickListener: PhotoClickListener): ListAdapter<Photo, GalleryAdapter.ItemViewHolder>(diffUtil) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): GalleryAdapter.ItemViewHolder = ItemViewHolder(
        ListItemGalleryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: GalleryAdapter.ItemViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    inner class ItemViewHolder(private val binding: ListItemGalleryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(photo: Photo) {
            binding.photoViewModel = PhotoViewModel(photo)
            binding.imageView.setOnClickListener {
                photoClickListener.onPhotoClickListener(PhotoViewModel(photo))
            }
            Log.d(TAG, "bind_sss: ${binding.photoViewModel}")
        }
    }

    companion object{
        val diffUtil = object : DiffUtil.ItemCallback<Photo>(){
            override fun areItemsTheSame(oldItem: Photo, newItem: Photo): Boolean {
                return oldItem.num == newItem.num
            }

            override fun areContentsTheSame(oldItem: Photo, newItem: Photo): Boolean {
                return oldItem == newItem
            }


        }
    }

}