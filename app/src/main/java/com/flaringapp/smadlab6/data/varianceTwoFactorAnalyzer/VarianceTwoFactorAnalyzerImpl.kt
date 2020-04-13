package com.flaringapp.smadlab6.data.varianceTwoFactorAnalyzer

import com.flaringapp.app.utils.rxCalculation
import com.flaringapp.smadlab6.data.varianceTwoFactorAnalyzer.VarianceTwoFactorAnalyzer.IVarianceTwoFactorAnalysis
import com.flaringapp.smadlab6.data.varianceTwoFactorAnalyzer.models.VarianceTwoFactorAnalysis
import io.reactivex.Single
import kotlin.math.pow

class VarianceTwoFactorAnalyzerImpl : VarianceTwoFactorAnalyzer {

    override fun analyze(data: Array<DoubleArray>): Single<IVarianceTwoFactorAnalysis> =
        rxCalculation {
            val n = data.size
            val m = data[0].size

            val totalVariance = totalVariance(q(data), n, m)
            val intergroup1Variance = intergroup1Variance(q1(data), n)
            val intergroup2Variance = intergroup2Variance(q2(data), m)
            val residualVariance = residualVariance(q3(data), n, m)
            val fisher1Criterion = fisherCriterion(intergroup1Variance, residualVariance)
            val fisher2Criterion = fisherCriterion(intergroup2Variance, residualVariance)

            VarianceTwoFactorAnalysis(
                intergroup1Variance,
                intergroup2Variance,
                residualVariance,
                totalVariance,
                fisher1Criterion,
                fisher2Criterion
            )
        }

    private fun fisherCriterion(s1: Double, s2: Double): Double {
        return s1 / s2
    }

    private fun totalVariance(q: Double, n: Int, m: Int): Double {
        return q / (n * m - 1)
    }

    private fun intergroup1Variance(q1: Double, n: Int): Double {
        return q1 / (n - 1)
    }

    private fun intergroup2Variance(q2: Double, m: Int): Double {
        return q2 / (m - 1)
    }

    private fun residualVariance(q3: Double, n: Int, m: Int): Double {
        return q3 / ((n - 1) * (m - 1))
    }

    private fun q(data: Array<DoubleArray>): Double {
        val xAverage = getXAverage(data)

        var s = 0.0

        for (i in data.indices) {
            var si = 0.0
            for (j in data[0].indices) {
                si += (data[i][j] - xAverage).pow(2)
            }
            s += si
        }

        return s
    }

    private fun q1(data: Array<DoubleArray>): Double {
        var s = 0.0

        for (i in data.indices) {
            s += (getXiAverage(i, data) - getXAverage(data)).pow(2)
        }

        return data[0].size * s
    }

    private fun q2(data: Array<DoubleArray>): Double {
        var s = 0.0

        for (i in data[0].indices) {
            s += (getXjAverage(i, data) - getXAverage(data)).pow(2)
        }

        return data.size * s
    }

    private fun q3(data: Array<DoubleArray>): Double {
        val xAverage = getXAverage(data)

        var s = 0.0

        for (i in data.indices) {
            var si = 0.0
            for (j in data[0].indices) {
                si += (data[i][j] - getXiAverage(i, data) - getXjAverage(j, data) + xAverage).pow(2)
            }
            s += si
        }

        return s
    }

    private fun getXAverage(data: Array<DoubleArray>): Double {
        val width = data[0].size

        var s = 0.0

        for (i in data.indices) {
            var si = 0.0
            for (j in 0 until width) {
                si += data[i][j]
            }
            s += si
        }

        return (1.0 / (data.size * width)) * s
    }

    private fun getXiAverage(i: Int, data: Array<DoubleArray>): Double {
        return (1.0 / data[0].size) * data[i].sum()
    }

    private fun getXjAverage(j: Int, data: Array<DoubleArray>): Double {
        var s = 0.0

        for (i in data.indices) {
            s += data[i][j]
        }

        return (1.0 / data.size) * s
    }
}