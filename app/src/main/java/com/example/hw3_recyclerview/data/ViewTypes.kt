package com.example.hw3_recyclerview.data

sealed class ViewTypes {
    data class LeftMessage(val messageId: Int, val messageText: String) : ViewTypes()
    data class RightMessage(val messageId: Int, val messageText: String) : ViewTypes()
    class ChangeUserButtons(var messageCount1: Int, var messageCount2: Int) : ViewTypes()
}
