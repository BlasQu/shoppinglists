package com.example.shoppinglists.Utils

import org.junit.Assert.*
import org.junit.Test

class checkTextValidTest {

    @Test
    fun `checkIfTextNotValid`() {
        val text = ""
        assertFalse(checkTextValid.add_item_valid(text))
    }

    @Test
    fun `checkIfTextValid`() {
        val text = "TestString"
        assertTrue(checkTextValid.add_item_valid(text))
    }

    @Test
    fun `checkIfTextOutOfSpace`() {
        val text = "TestStringTestStringTestStringTestString"
        assertFalse(checkTextValid.add_item_valid(text))
    }
}