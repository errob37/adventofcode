package days.impl

import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class DayOneTest {

    val underTest = DayOne()

    @Nested
    inner class ExtractCalibrationValue {
        @Test
        fun `given no digit with digit only search then calibration value is 0`() {
            val value = "abcdef"
            val result = underTest.extractCalibrationValue(value, CalibrationSearchStrategy.DIGIT_ONLY)
            assertEquals(0L, result)
        }

        @Test
        fun `given only one digit with digit only search then calibration value is a number with same digit`() {
            val value = "abc1def"
            val result = underTest.extractCalibrationValue(value, CalibrationSearchStrategy.DIGIT_ONLY)
            assertEquals(11L, result)
        }

        @Test
        fun `given two digits with digit only search then calibration value is a number with these two digits`() {
            val value = "abc1de2f"
            val result = underTest.extractCalibrationValue(value, CalibrationSearchStrategy.DIGIT_ONLY)
            assertEquals(12L, result)
        }

        @Test
        fun `given more than two digits with digit only search then calibration value is a number with first and last digits`() {
            val value = "123456789"
            val result = underTest.extractCalibrationValue(value, CalibrationSearchStrategy.DIGIT_ONLY)
            assertEquals(19L, result)
        }

        @Test
        fun `given no digit with digit or word search then calibration value is 0`() {
            val value = "abcdef"
            val result = underTest.extractCalibrationValue(value, CalibrationSearchStrategy.DIGIT_OR_WORD)
            assertEquals(0L, result)
        }

        @Test
        fun `given only one digit in letter with digit or word search then calibration value is a number with same digit`() {
            val value = "abcfiveabc"
            val result = underTest.extractCalibrationValue(value, CalibrationSearchStrategy.DIGIT_OR_WORD)
            assertEquals(55L, result)
        }

        @Test
        fun `given two digits in letter with digit or word search then calibration value is a number with these two digits`() {
            val value = "sixabceightdef"
            val result = underTest.extractCalibrationValue(value, CalibrationSearchStrategy.DIGIT_OR_WORD)
            assertEquals(68L, result)
        }

        @Test
        fun `given more than two digits in letter with digit or word search then calibration value is a number with first and last digits`() {
            val value = "sixabctwone"
            val result = underTest.extractCalibrationValue(value, CalibrationSearchStrategy.DIGIT_OR_WORD)
            assertEquals(61L, result)
        }

        @Test
        fun `given a mix of digit and number in letter with digit or word search then calibration value is a number with first and last digits`() {
            val value = "123eightwothreeone456seven"
            val result = underTest.extractCalibrationValue(value, CalibrationSearchStrategy.DIGIT_OR_WORD)
            assertEquals(17, result)
        }
    }

    @Nested
    inner class ConvertDigitsInLetter {

        @Test
        fun `given no digit in letter then stays the same`() {
            val value = "onitwithee"
            val result = underTest.convertDigitsInLetter(value)
            assertEquals(value, result)
        }

        @Test
        fun `given one digit in letter then replaces without changing semantic`() {
            val value = "onitwoithee"
            val result = underTest.convertDigitsInLetter(value)
            assertEquals("onitwo2twoithee", result)
        }

        @Test
        fun `given more than one digit in letter then replaces without changing semantic`() {
            val value = "onitwoithree"
            val result = underTest.convertDigitsInLetter(value)
            assertEquals("onitwo2twoithree3three", result)
        }

        @Test
        fun `given more than one digit in letter and common digit between them then replaces without changing semantic`() {
            val value = "oneighthree"
            val result = underTest.convertDigitsInLetter(value)
            assertEquals("one1oneight8eighthree3three", result)
        }
    }
}
