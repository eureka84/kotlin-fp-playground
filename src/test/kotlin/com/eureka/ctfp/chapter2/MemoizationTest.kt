package com.eureka.ctfp.chapter2

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import java.util.concurrent.TimeUnit
import kotlin.system.measureTimeMillis

class MemoizationTest {

    private val longComputation = { i: Int ->
        TimeUnit.SECONDS.sleep(1)
        i * 2
    }

    @Test
    fun memoization() {
        val longComputationMemoized = longComputation.memoize()

        val nonMemoizedTime = measureTimeMillis {
            longComputation(1)
            longComputation(1)
            longComputation(1)
        }

        val memoizedTime = measureTimeMillis {
            longComputationMemoized(1)
            longComputationMemoized(1)
            longComputationMemoized(1)
        }

        assertThat(nonMemoizedTime).isGreaterThanOrEqualTo(3000)
        assertThat(memoizedTime).isLessThan(1100)
        assertThat(longComputation(1)).isEqualTo(longComputationMemoized(1))
    }
}