package ru.netology

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class ChatServiceTest {
    private val service = ChatService()
    private val user1 = User(1, "Alice")
    private val user2 = User(2, "Bob")

    @Test
    fun testCreateMessage() {
        // Создание чата при отправке первого сообщения
        service.createMessage(1, user1, "Hello, Bob!")
        assertEquals(1, service.getChats().size)
    }

    @Test
    fun testCreateMessageWithExistingChat() {
        // Создание чата при отправке первого сообщения
        service.createMessage(1, user1, "Hello, Bob!")
        // Отправка сообщения в существующий чат
        service.createMessage(1, user2, "Hi, Alice!")
        assertEquals(1, service.getChats().size)
    }

    @Test
    fun testCreateMessageWithNonExistingChat() {
        // Отправка сообщения в несуществующий чат
        assertThrows(IllegalArgumentException::class.java) {
            service.createMessage(2, user1, "Hello, Bob!")
        }
    }
}