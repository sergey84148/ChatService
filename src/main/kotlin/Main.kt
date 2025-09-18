package ru.netology

fun main() {
    val service = ChatService()
    val user1 = User(1, "Alice")
    val user2 = User(2, "Bob")

    // Создание чата
    service.createMessage(1, user1, user2, "Hello, Bob!")

    // Получение количества непрочитанных чатов
    println("Unread chats count: ${service.getUnreadChatsCount()}")

    // Получение списка чатов
    println("Chats: ${service.getChats()}")

    // Получение последних сообщений
    println("Last messages: ${service.getLastMessages(2)}")

    // Получение сообщений из чата
    val lastTwoMessages = service.getMessages(1, minOf(service.getMessages(1, Int.MAX_VALUE).size, 2)) // ограничение на два сообщения
    println("Messages in chat 1: $lastTwoMessages")

    // Удаление сообщения
    service.deleteMessage(1, 1)
    println("Messages in chat 1 after deletion: ${service.getMessages(1, 2)}")

    // Удаление чата
    service.deleteChat(1)
    println("Chats after deletion: ${service.getChats()}")
}