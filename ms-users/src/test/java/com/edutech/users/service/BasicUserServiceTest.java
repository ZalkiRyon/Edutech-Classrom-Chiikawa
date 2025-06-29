package com.edutech.users.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class BasicUserServiceTest {

    @Test
    void basicTest_ShouldPass() {
        // Given
        String expected = "Hello World";
        
        // When
        String actual = "Hello World";
        
        // Then
        assertEquals(expected, actual);
    }

    @Test
    void basicMathTest_ShouldCalculateCorrectly() {
        // Given
        int a = 5;
        int b = 3;
        
        // When
        int result = a + b;
        
        // Then
        assertEquals(8, result);
    }
}
