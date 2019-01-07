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
            (1..5).forEach { longComputation(1) }
        }

        val memoizedTime = measureTimeMillis {
            (1..100).forEach { longComputationMemoized(1) }
        }

        assertThat(nonMemoizedTime).isGreaterThanOrEqualTo(5 * SLEEP_MILLIS)
        assertThat(memoizedTime).isLessThan((1.1 * SLEEP_MILLIS).toLong())
        assertThat(longComputation(1)).isEqualTo(longComputationMemoized(1))
    }
}