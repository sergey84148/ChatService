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

    fun getUnreadChatsCount(): Int {
        return chats.sumBy { chat -> chat.messages.count { !it.isRead } }
    }

    fun getChats(): List<Chat> {
        return chats
    }

    fun getLastMessages(limit: Int): List<String> {
        return chats.flatMap { chat -> chat.messages.takeLast(limit).map { it.text } }
    }

    fun getMessages(chatId: Int, limit: Int): List<Message> {
        val chat = chats.find { it.id == chatId } ?: throw IllegalArgumentException("Chat not found")
        return chat.messages.takeLast(limit).onEach { it.isRead = true }
    }

    fun createMessage(chatId: Int, sender: User, receiver: User, text: String) {
        val chat = chats.find { it.id == chatId }
        if (chat == null) {
            val newChat = Chat(chats.size + 1, sender, receiver, mutableListOf())
            chats.add(newChat)
            newChat.messages.add(Message(1, sender, text, System.currentTimeMillis()))
        } else {
            chat.messages.add(Message(chat.messages.size + 1, sender, text, System.currentTimeMillis()))
        }
    }

    fun deleteMessage(chatId: Int, messageId: Int) {
        val chat = chats.find { it.id == chatId } ?: throw IllegalArgumentException("Chat with id=$chatId not found")
        val messageIndex = chat.messages.indexOfFirst { it.id == messageId }
        if (messageIndex == -1) {
            throw IllegalArgumentException("Message with id=$messageId not found in chat with id=$chatId")
        }
        chat.messages.removeAt(messageIndex)
    }

    fun deleteChat(chatId: Int) {
        val removed = chats.removeIf { it.id == chatId }
        if (!removed) {
            throw IllegalArgumentException("Chat with id=$chatId not found")
        }
    }
}