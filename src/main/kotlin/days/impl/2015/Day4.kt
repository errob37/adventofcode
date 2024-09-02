package days.impl.`2015`

import days.impl.AdventOfCodeDayImpl
import java.math.BigInteger
import java.security.MessageDigest

class Day4 : AdventOfCodeDayImpl(2015, 4, 609043L, 6742839L, 254575L) {
    override fun partOne(input: List<String>): Long {
        val secretKey = input[0]

        var id = 1L;
        var s = secretKey + id.toString();
        var md5 = s.md5();
        while (!md5.startsWith("00000")) {
            id++
            s = secretKey + id.toString();
            md5 = s.md5()
        }

        return id;
    }

    override fun partTwo(input: List<String>): Long {
        val secretKey = input[0]

        var id = 254575L;
        var s = secretKey + id.toString();
        var md5 = s.md5();
        while (!md5.startsWith("000000")) {
            id++
            s = secretKey + id.toString();
            md5 = s.md5()
        }

        return id;
    }

    private fun String.md5(): String =
        String.format("%032x", BigInteger(1, MessageDigest.getInstance("MD5").digest(this.toByteArray(Charsets.UTF_8))))
}
