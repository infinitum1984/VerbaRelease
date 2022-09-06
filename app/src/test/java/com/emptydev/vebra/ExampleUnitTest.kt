package com.emptydev.verba

import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun testArrayToStr() {
        print("TEST")
        val arrTest=stringToArray("test1\ntest2\ntest3\ntest4\ntest4\ntest5\ntest6\ntest7\ntest8\n")
        print(arrTest)
    }
}