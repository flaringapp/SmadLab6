package com.flaringapp.smadlab6.data.varianceAnalyzer

import com.flaringapp.app.utils.rxCalculation
import com.flaringapp.smadlab6.data.varianceAnalyzer.VarianceAnalyzer.IVarianceAnalysis
import com.flaringapp.smadlab6.data.varianceAnalyzer.models.VarianceAnalysis
import io.reactivex.Single
import kotlin.math.pow

class VarianceAnalyzerImpl : VarianceAnalyzer {

    override fun analyze(data: Array<DoubleArray>): Single<IVarianceAnalysis> = rxCalculation {
        val n = data.size
        val m = data[0].size

        val totalVariance = totalVariance(q(data), n, m)
        val intergroupVariance = intergroupVariance(q1(data), n)
        val residualVariance = residualVariance(q2(data), n, m)
        val fisherCriterion = fisherCriterion(intergroupVariance, residualVariance)

        VarianceAnalysis(
            intergroupVariance,
            residualVariance,
            totalVariance,
            fisherCriterion
        )
    }

    private fun fisherCriterion(s1: Double, s2: Double): Double {
        return s1 / s2
    }

    private fun totalVariance(q: Double, n: Int, m: Int): Double {
        return q / (n * m)
    }

    private fun intergroupVariance(q1: Double, n: Int): Double {
        return q1 / n
    }

    private fun residualVariance(q2: Double, n: Int, m: Int): Double {
        return q2 / (n * (m - 1))
    }

    private fun q(data: Array<DoubleArray>): Double {
        val width = data[0].size

        val xAverage = getXAverage(data)

        var s = 0.0

        for (i in data.indices) {
            var si = 0.0
            for (j in 0 until width) {
                si += (data[i][j] - xAverage).pow(2)
            }
            s += si
        }

        return s
    }

    private fun q1(data: Array<DoubleArray>): Double {
        val xAverage = getXAverage(data)

        var s = 0.0

        for (i in data.indices) {
            s += (getXiAverage(i, data) - xAverage).pow(2)
        }

        return data[0].size * s
    }

    private fun q2(data: Array<DoubleArray>): Double {
        var s = 0.0

        for (i in data.indices) {
            var s1 = 0.0
            for (j in data[0].indices) {
                s1 += (data[i][j] - getXiAverage(i, data)).pow(2)
            }
            s += s1
        }

        return s
    }

    private fun getXAverage(data: Array<DoubleArray>): Double {
        val width = data[0].size

        var s = 0.0
        for (element in data) {
            var si = 0.0
            for (j in 0 until width) {
                si += element[j]
            }
            s += si
        }

        return (1.0 / (width * data.size)) * s
    }

    private fun getXiAverage(j: Int, data: Array<DoubleArray>): Double {
        return (1.0 / data[0].size) * data[j].sum()
    }
}