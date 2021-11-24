package com.example.hw3_recyclerview

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.hw3_recyclerview.adapter.ChatAdapter
import com.example.hw3_recyclerview.adapter.OnUserClick
import com.example.hw3_recyclerview.data.ViewTypes
import com.example.hw3_recyclerview.databinding.ActivityMainBinding
import com.example.hw3_recyclerview.databinding.ButtonsItemBinding
import com.example.hw3_recyclerview.decorator.MessageDecorator

class MainActivity : AppCompatActivity(), OnUserClick {
    private lateinit var bindingActivity: ActivityMainBinding
    private val chatAdapter = ChatAdapter(this as OnUserClick)
    override var currentUserId = 0
    private var leftMessageCount = 0
    private var rightMessageCount = 0
    private var messagesList = mutableListOf<ViewTypes>(ViewTypes.ChangeUserButtons(0, 0))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingActivity = ActivityMainBinding.inflate(layoutInflater)

        bindingActivity.btnSendMessage.setOnClickListener {
            onClickSubmit()
        }

        setContentView(bindingActivity.root)
        initAdapter()
        chatAdapter.dataSet = messagesList
    }

    private fun initAdapter() {
        bindingActivity.recyclerView.apply {
            adapter = chatAdapter
            addItemDecoration(MessageDecorator(15))
        }
    }

    private fun onClickSubmit() {
        if (bindingActivity.txtSendMessage.text.isEmpty()
            && currentUserId != ChatAdapter.VIEW_TYPE_MESSAGE_LEFT
            && currentUserId != ChatAdapter.VIEW_TYPE_MESSAGE_RIGHT
        ) {
            Toast.makeText(
                applicationContext,
                "Enter some message or choose user",
                Toast.LENGTH_SHORT
            ).show()
        } else {
            when (currentUserId) {
                ChatAdapter.VIEW_TYPE_MESSAGE_LEFT -> {
                    if (bindingActivity.txtSendMessage.text.isNotEmpty()) {
                        messagesList.add(
                            ViewTypes.LeftMessage(
                                leftMessageCount++,
                                bindingActivity.txtSendMessage.text.toString()
                            )
                        )
                        bindingActivity.txtSendMessage.text.clear()
                        updateCounters()
                        chatAdapter.dataSet = messagesList
                    } else if (bindingActivity.txtSendMessage.text.isEmpty()) {
                        Toast.makeText(
                            applicationContext,
                            "Enter some message",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
                ChatAdapter.VIEW_TYPE_MESSAGE_RIGHT -> {
                    if (bindingActivity.txtSendMessage.text.isNotEmpty()) {
                        messagesList.add(
                            ViewTypes.RightMessage(
                                rightMessageCount++,
                                bindingActivity.txtSendMessage.text.toString()
                            )
                        )
                        bindingActivity.txtSendMessage.text.clear()
                        updateCounters()
                        chatAdapter.dataSet = messagesList
                    } else if (bindingActivity.txtSendMessage.text.isEmpty()) {
                        Toast.makeText(
                            applicationContext,
                            "Enter some message",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
                else -> Toast.makeText(
                    applicationContext,
                    "Enter some message or choose user",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    @SuppressLint("ResourceAsColor")
    override fun onClick(userId: Int, binding: ButtonsItemBinding) {
        currentUserId = userId

        when (currentUserId) {
            ChatAdapter.VIEW_TYPE_MESSAGE_LEFT -> {
                binding.button1.setBackgroundColor(Color.parseColor("#009688"))
                binding.button2.setBackgroundColor(Color.parseColor("#2196F3"))
            }
            ChatAdapter.VIEW_TYPE_MESSAGE_RIGHT -> {
                binding.button2.setBackgroundColor(Color.parseColor("#009688"))
                binding.button1.setBackgroundColor(Color.parseColor("#2196F3"))
            }
        }
    }

    private fun updateCounters() {
        (messagesList[0] as ViewTypes.ChangeUserButtons).apply {
            messageCount1 = leftMessageCount
            messageCount2 = rightMessageCount
        }
    }
}