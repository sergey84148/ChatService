package ru.netology

data class User(val id: Int, val name: String)
data class Message(
    val id: Int,
    val sender: User,
    val text: String,
    val timestamp: Long,
    var isRead: Boolean = false
)

data class Chat(
    val id: Int,
    val user1: User,
    val user2: User,
    val messages: MutableList<Message>
)

class ChatService {
    private val chats = mutableListOf<Chat>()
    private var nextChatId = 1
    private var nextMessageId = 1

    fun getUnreadChatsCount(): Int {
        return chats.count { chat -> chat.messages.any { !it.isRead } }
    }

    fun getChats(): List<Chat> {
        return chats
    }

    fun getLastMessages(limit: Int): List<String> {
        return chats.flatMap { chat -> chat.messages.takeLast(limit).map { it.text } }
    }

    fun getMessages(chatId: Int, limit: Int): List<Message> =
        chats.find { it.id == chatId }
            ?.let { it.messages.takeLast(limit).onEach { msg -> msg.isRead = true } }
            ?: throw IllegalArgumentException("Chat with id=$chatId not found")

    fun createMessage(chatId: Int, sender: User, receiver: User, text: String) {
        val chat = chats.find { it.id == chatId }
        if (chat == null) {
            val newChat = Chat(nextChatId++, sender, receiver, mutableListOf())
            chats.add(newChat)
            newChat.messages.add(Message(nextMessageId++, sender, text, System.currentTimeMillis()))
        } else {
            chat.messages.add(Message(nextMessageId++, sender, text, System.currentTimeMillis()))
        }
    }

    fun deleteMessage(chatId: Int, messageId: Int) {
        val chat = chats.find { it.id == chatId } ?: throw IllegalArgumentException("Chat with id=$chatId not found")

        val index = chat.messages.indexOfFirst { it.id == messageId }
        if (index == -1) {
            throw IllegalArgumentException("Message with id=$messageId not found in chat with id=$chatId")
        }

        chat.messages.removeAt(index)
    }

    fun deleteChat(chatId: Int) {
        val deleted = chats.removeIf { it.id == chatId }
        if (!deleted) {
            throw IllegalArgumentException("Chat with id=$chatId not found")
        }
    }
}