package util

import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals


class ReadersTest {
    @Nested
    inner class ReadInput {
        @Test
        fun `given a day then read`() {
            val result = Readers.readInput(1)
            assertEquals(result, listOf("random_1", "random_2"))
        }
    }

    @Nested
    inner class ReadTestInput {
        @Test
        fun `given a day then read`() {
            val result = Readers.readTestInput(1, 1)
            assertEquals(result, listOf("test_1", "test_2"))
        }
    }
}
