package ru.netology

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test

class ChatServiceTest {
    private val service = ChatService()
    private val user1 = User(1, "Alice")
    private val user2 = User(2, "Bob")

    @Test
    fun `test create message creates a new chat`() {
        service.createMessage(1, user1, user2, "Hello, Bob!")
        assertEquals(1, service.getChats().size)
    }

    @Test
    fun `test create message adds to an existing chat`() {
        service.createMessage(1, user1, user2, "Hello, Bob!")
        service.createMessage(1, user2, user1, "Hi, Alice!")
        assertEquals(1, service.getChats().size)
    }

    @Test
    fun `test get messages throws exception for non-existing chat`() {
        assertThrows(IllegalArgumentException::class.java) {
            service.getMessages(999, 2)
        }
    }

    @Test
    fun `test delete message removes the correct message from chat`() {
        service.createMessage(1, user1, user2, "Hello, Bob!")
        service.deleteMessage(1, 1)
        assertEquals(emptyList<Message>(), service.getMessages(1, 2))
    }

    @Test
    fun `test delete chat removes the entire chat`() {
        service.createMessage(1, user1, user2, "Hello, Bob!")
        service.deleteChat(1)
        assertEquals(emptyList<Chat>(), service.getChats())
    }

    @Test
    fun `test delete chat throws exception for non-existing chat`() {
        assertThrows(IllegalArgumentException::class.java) {
            service.deleteChat(999)
        }
    }

    @Test
    fun `test delete message throws exception for non-existing message`() {
        service.createMessage(1, user1, user2, "Hello, Bob!")
        assertThrows(IllegalArgumentException::class.java) {
            service.deleteMessage(1, 999)
        }
    }
}