package com.example.productservice;

import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RandomTest {

    @Test
    void testAddition() {
        int i = 1 + 1;

        assertTrue(i == 2);
        assertEquals(2, i);
    }
}
