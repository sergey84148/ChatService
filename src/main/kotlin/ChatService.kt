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
    private var nextChatId = 1      // Генерируем уникальный ID для чатов
    private var nextMessageId = 1   // Генерируем уникальный ID для сообщений

    fun getUnreadChatsCount(): Int {
        return chats.count { chat -> chat.messages.any { !it.isRead } }
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

    fun createMessage(chatId: Int, sender: User, user2: User, text: String) {
        val chat = chats.find { it.id == chatId }
        if (chat == null) {
            // Если чат не существует, создаем его
            val newChat = Chat(chats.size + 1, sender, sender, mutableListOf()) // <-----
            chats.add(newChat)
            newChat.messages.add(Message(newChat.messages.size + 1, sender, text, System.currentTimeMillis()))
        } else {
            chat.messages.add(Message(chat.messages.size + 1, sender, text, System.currentTimeMillis()))
        }
    }

    fun deleteMessage(chatId: Int, messageId: Int) {
        val chat = chats.find { it.id == chatId } ?: throw IllegalArgumentException("Chat not found")
        chat.messages.removeIf { it.id == messageId }
    }

    fun deleteChat(chatId: Int) {
        chats.removeIf { it.id == chatId }
    }
}