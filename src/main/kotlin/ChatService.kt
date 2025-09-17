package ru.netology

data class User(val id: Int, val name: String)
data class Message(val id: Int, val sender: User, val text: String, val timestamp: Long, var isRead: Boolean = false)
data class Chat(val id: Int, val user1: User, val user2: User, val messages: MutableList<Message>)

// Сервис сообщений
class ChatService {
    private val chats = mutableListOf<Chat>()

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
        val messages = chat.messages.takeLast(limit)
        messages.onEach { it.isRead = true }
        return messages
    }

    fun createMessage(chatId: Int, sender: User, text: String) {
        val chat = chats.find { it.id == chatId }
        if (chat == null) {
            // Если чат не существует, создаем его
            val newChat = Chat(chats.size + 1, sender, sender, mutableListOf())
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

    private fun createChat(user1: User, user2: User) {
        val existingChat = chats.find { it.user1 == user1 && it.user2 == user2 || it.user1 == user2 && it.user2 == user1 }
        if (existingChat != null) {
            throw IllegalArgumentException("Chat already exists")
        }
        chats.add(Chat(chats.size + 1, user1, user2, mutableListOf()))
    }

    fun deleteChat(chatId: Int) {
        chats.removeIf { it.id == chatId }
    }
}
