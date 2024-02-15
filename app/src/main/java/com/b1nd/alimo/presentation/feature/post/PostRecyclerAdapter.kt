package com.b1nd.alimo.presentation.feature.post

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.b1nd.alimo.R
import com.b1nd.alimo.data.model.NotificationModel
import com.b1nd.alimo.databinding.ItemPostBinding
import com.b1nd.alimo.presentation.utiles.loadImage

class PostRecyclerAdapter constructor(
    private val onClick: (NotificationModel) -> Unit
): PagingDataAdapter<NotificationModel, PostRecyclerAdapter.ViewHolder>(diffCallback) {

    inner class ViewHolder(binding: ItemPostBinding): RecyclerView.ViewHolder(binding.root) {
        val binding = binding
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val binding = holder.binding
        val item = getItem(position)
        item?.let {
            with(binding) {
                if (item.memberProfile != null) {
                    imageProfile.loadImage(item.memberProfile)
                }
                if (item.image != null) {
                    imageContent.visibility = View.VISIBLE
                    imageContent.loadImage(item.image)
                }
                if (item.isNew) {
                    imageNewBadge.visibility = View.VISIBLE
                }
                if (item.isBookmark) {
                    imageBookmark.setImageResource(R.drawable.ic_bookmark)
                }

                textAuthor.text = item.member
                textDate.text = item.createdAt.toString()
                textContent.text = item.content
            }
            binding.layoutPost.setOnClickListener {
                onClick(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemPostBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<NotificationModel>() {
            override fun areItemsTheSame(oldItem: NotificationModel, newItem: NotificationModel): Boolean =
                oldItem.notificationId == newItem.notificationId

            override fun areContentsTheSame(oldItem: NotificationModel, newItem: NotificationModel): Boolean =
                oldItem == newItem
        }
    }
}
