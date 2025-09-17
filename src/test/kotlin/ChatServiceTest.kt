package ru.netology

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class ChatServiceTest {
    private val service = ChatService()
    private val user1 = User(1, "Alice")
    private val user2 = User(2, "Bob")

    @Test
    fun testCreateChat() {
        service.createChat(user1, user2)
        assertEquals(1, service.getChats().size)
    }

    @Test
    fun testCreateMessage() {
        service.createChat(user1, user2)
        service.createMessage(1, user1, "Hello, Bob!")
        assertEquals(1, service.getMessages(1, 1).size)
    }

    @Test
    fun testDeleteMessage() {
        service.createChat(user1, user2)
        service.createMessage(1, user1, "Hello, Bob!")
        service.deleteMessage(1, 1)
        assertTrue(service.getMessages(1, 1).isEmpty())
    }

    @Test
    fun testDeleteChat() {
        service.createChat(user1, user2)
        service.deleteChat(1)
        assertTrue(service.getChats().isEmpty())
    }
}