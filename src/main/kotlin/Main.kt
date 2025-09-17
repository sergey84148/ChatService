package ru.netology

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