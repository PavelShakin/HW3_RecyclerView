package com.example.hw3_recyclerview.adapter

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.hw3_recyclerview.data.ViewTypes
import com.example.hw3_recyclerview.databinding.ButtonsItemBinding
import com.example.hw3_recyclerview.databinding.LeftMessageItemBinding
import com.example.hw3_recyclerview.databinding.RightMessageItemBinding

interface OnUserClick {

    var currentUserId: Int

    fun onClick(userId: Int, binding: ButtonsItemBinding)
}

class ChatAdapter(val userClick: OnUserClick) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var dataSet: List<ViewTypes> = emptyList()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount() = dataSet.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when (viewType) {
            VIEW_TYPE_BUTTON -> {
                val binding = ButtonsItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                return ViewHolderUserButton(binding)
            }
            VIEW_TYPE_MESSAGE_LEFT -> {
                val binding = LeftMessageItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                return ViewHolderLeftMessage(binding)
            }
            VIEW_TYPE_MESSAGE_RIGHT -> {
                val binding = RightMessageItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                return ViewHolderRightMessage(binding)
            }
            else -> throw IllegalArgumentException()
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when {
            dataSet[position] is ViewTypes.ChangeUserButtons -> VIEW_TYPE_BUTTON
            dataSet[position] is ViewTypes.LeftMessage -> VIEW_TYPE_MESSAGE_LEFT
            dataSet[position] is ViewTypes.RightMessage -> VIEW_TYPE_MESSAGE_RIGHT
            else -> throw IllegalArgumentException()
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ViewHolderUserButton -> holder.bind(dataSet[position] as ViewTypes.ChangeUserButtons)
            is ViewHolderLeftMessage -> holder.bind(dataSet[position] as ViewTypes.LeftMessage)
            is ViewHolderRightMessage -> holder.bind(dataSet[position] as ViewTypes.RightMessage)
        }
    }

    inner class ViewHolderUserButton(private val binding: ButtonsItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(item: ViewTypes.ChangeUserButtons) {
            if (userClick.currentUserId == VIEW_TYPE_MESSAGE_LEFT) {
                binding.button1.setBackgroundColor(Color.parseColor("#009688"))
                binding.button2.setBackgroundColor(Color.parseColor("#2196F3"))
            } else if (userClick.currentUserId == VIEW_TYPE_MESSAGE_RIGHT) {
                binding.button2.setBackgroundColor(Color.parseColor("#009688"))
                binding.button1.setBackgroundColor(Color.parseColor("#2196F3"))
            }

            binding.button1.text = "User1: " + item.messageCount1
            binding.button2.text = "User2: " + item.messageCount2

            binding.button1.setOnClickListener {
                userClick.onClick(VIEW_TYPE_MESSAGE_LEFT, binding)
            }

            binding.button2.setOnClickListener {
                userClick.onClick(VIEW_TYPE_MESSAGE_RIGHT, binding)
            }
        }
    }

    inner class ViewHolderLeftMessage(private val binding: LeftMessageItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ViewTypes.LeftMessage) {
            binding.txtLeftMessage.text = item.messageText
        }
    }

    inner class ViewHolderRightMessage(private val binding: RightMessageItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ViewTypes.RightMessage) {
            binding.txtRightMessage.text = item.messageText
        }
    }

    companion object {
        const val VIEW_TYPE_MESSAGE_LEFT = 1
        const val VIEW_TYPE_MESSAGE_RIGHT = 2
        const val VIEW_TYPE_BUTTON = 3
    }
}