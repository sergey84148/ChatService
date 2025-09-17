package ru.netology

// Модели данных
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
        messages.forEach { it.isRead = true }
        return messages
    }

    fun createMessage(chatId: Int, sender: User, text: String) {
        val chat = chats.find { it.id == chatId } ?: throw IllegalArgumentException("Chat not found")
        chat.messages.add(Message(chat.messages.size + 1, sender, text, System.currentTimeMillis()))
    }

    fun deleteMessage(chatId: Int, messageId: Int) {
        val chat = chats.find { it.id == chatId } ?: throw IllegalArgumentException("Chat not found")
        chat.messages.removeIf { it.id == messageId }
    }

    fun createChat(user1: User, user2: User) {
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

// Пример использования
fun main() {
    val service = ChatService()
    val user1 = User(1, "Alice")
    val user2 = User(2, "Bob")

    // Создание чата
    service.createChat(user1, user2)

    // Создание сообщений
    service.createMessage(1, user1, "Hello, Bob!")
    service.createMessage(1, user2, "Hi, Alice!")

    // Получение количества непрочитанных чатов
    println("Unread chats count: ${service.getUnreadChatsCount()}")

    // Получение списка чатов
    println("Chats: ${service.getChats()}")

    // Получение последних сообщений
    println("Last messages: ${service.getLastMessages(2)}")

    // Получение сообщений из чата
    println("Messages in chat 1: ${service.getMessages(1, 2)}")

    // Удаление сообщения
    service.deleteMessage(1, 1)
    println("Messages in chat 1 after deletion: ${service.getMessages(1, 2)}")

    // Удаление чата
    service.deleteChat(1)
    println("Chats after deletion: ${service.getChats()}")
}