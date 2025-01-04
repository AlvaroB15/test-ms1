package com.retonttdata.authentication.domain.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    void createUser_ValidData_Success() {
        User user = User.builder()
                .id(1L)
                .fullName("John Doe")
                .email("john@example.com")
                .password("Password123")
                .phone("+1234567890")
                .build();

        assertEquals(1L, user.getId());
        assertEquals("John Doe", user.getFullName());
        assertEquals("john@example.com", user.getEmail());
        assertEquals("Password123", user.getPassword());
        assertEquals("+1234567890", user.getPhone());
    }

    @Test
    void equals_SameData_ReturnsTrue() {
        User user1 = User.builder()
                .id(1L)
                .email("john@example.com")
                .build();

        User user2 = User.builder()
                .id(1L)
                .email("john@example.com")
                .build();

        assertEquals(user1, user2);
        assertEquals(user1.hashCode(), user2.hashCode());
    }

    @Test
    void equals_DifferentData_ReturnsFalse() {
        User user1 = User.builder()
                .id(1L)
                .email("john@example.com")
                .build();

        User user2 = User.builder()
                .id(2L)
                .email("luke@example.com")
                .build();

        assertNotEquals(user1, user2);
        assertNotEquals(user1.hashCode(), user2.hashCode());
    }
}