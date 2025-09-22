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
        return chats.asSequence()
            .count { chat -> chat.messages.any { !it.isRead } }
    }

    fun getChats(): Sequence<Chat> {
        return chats.asSequence()
    }

    fun getLastMessages(limit: Int): Sequence<String> {
        return chats.asSequence()
            .flatMap { chat -> chat.messages.asSequence().toList().takeLast(limit).map { it.text }.asSequence() }
    }

    fun getMessages(chatId: Int, limit: Int): Sequence<Message> {
        val chat = chats.firstOrNull { it.id == chatId } ?: throw IllegalArgumentException("Chat not found")
        return chat.messages.asSequence().toList().takeLast(limit).asSequence().onEach { it.isRead = true }
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
        val chat = chats.find { it.id == chatId } ?: throw IllegalArgumentException("Chat not found")
        chat.messages.removeIf { it.id == messageId }
    }

    fun deleteChat(chatId: Int) {
        chats.removeIf { it.id == chatId }
    }
}