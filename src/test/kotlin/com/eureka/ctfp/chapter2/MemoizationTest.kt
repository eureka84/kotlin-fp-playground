package com.eureka.ctfp.chapter2

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import java.util.concurrent.TimeUnit
import kotlin.system.measureTimeMillis

const val SLEEP_MILLIS: Long = 400

class MemoizationTest {

    private val longComputation = { i: Int ->
        TimeUnit.MILLISECONDS.sleep(SLEEP_MILLIS)
        i * 2
    }

    @Test
    fun memoization() {
        val longComputationMemoized = longComputation.memoize()

        val nonMemoizedTime = measureTimeMillis {
            repeat(5) { longComputation(1) }
        }

        val memoizedTime = measureTimeMillis {
            repeat(100) { longComputationMemoized(1) }
        }

        assertThat(nonMemoizedTime).isGreaterThanOrEqualTo(5 * SLEEP_MILLIS)
        assertThat(memoizedTime).isLessThan((1.1 * SLEEP_MILLIS).toLong())
        assertThat(longComputation(1)).isEqualTo(longComputationMemoized(1))
    }
}