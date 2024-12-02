import java.io.File
import java.math.BigInteger
import java.security.MessageDigest

/**
 * Reads lines from the given input txt file.
 */
fun readInputTwentyTwo(name: String) = File("src/twentytwo/$name.txt")
    .readLines()

/**
 * Reads lines from the given input txt file.
 */
fun readInputTwentyThree(name: String) = File("src/twentythree/$name.txt")
    .readLines()

/**
 * Reads lines from the given input txt file.
 */
fun readInputTwentyFour(name: String) = File("src/twentyfour/$name.txt")
    .readLines()

/**
 * Converts string to md5 hash.
 */
fun String.md5() = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray()))
    .toString(16)
    .padStart(32, '0')
