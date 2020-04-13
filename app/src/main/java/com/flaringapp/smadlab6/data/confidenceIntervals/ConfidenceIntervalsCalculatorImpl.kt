package com.flaringapp.smadlab6.data.confidenceIntervals

import com.flaringapp.app.utils.rxCalculation
import com.flaringapp.smadlab6.data.calculator.CharacteristicsCalculator
import com.flaringapp.smadlab6.data.confidenceIntervals.ConfidenceIntervalsCalculator.IConfidenceInterval
import com.flaringapp.smadlab6.data.confidenceIntervals.enums.SignificanceLevel
import com.flaringapp.smadlab6.data.confidenceIntervals.models.ConfidenceInterval
import io.reactivex.Single
import kotlin.math.sqrt

class ConfidenceIntervalsCalculatorImpl(
    private val calculator: CharacteristicsCalculator
) : ConfidenceIntervalsCalculator {

    override fun mathExpectationVarianceKnown(
        significanceLevel: SignificanceLevel,
        vararg numbers: Double
    ): Single<IConfidenceInterval> = rxCalculation {
        val average = calculator.averageEmpirical(*numbers).blockingGet()
        val s = calculator.meanSquareDeviation(*numbers).blockingGet()
        val count = numbers.size

        val t = significanceLevel.laplace

        val left = average - (s / sqrt(count.toDouble()) * t)
        val right = average + (s / sqrt(count.toDouble()) * t)

        ConfidenceInterval(left, right)
    }

    override fun mathExpectationVarianceUnknown(
        significanceLevel: SignificanceLevel,
        vararg numbers: Double
    ): Single<IConfidenceInterval> = rxCalculation {
        val average = calculator.averageEmpirical(*numbers).blockingGet()
        val s = calculator.correctedMeanSquareDeviation(*numbers).blockingGet()
        val count = numbers.size

        val r = significanceLevel.student

        val left = average - (s / sqrt(count.toDouble()) * r)
        val right = average + (s / sqrt(count.toDouble()) * r)

        ConfidenceInterval(left, right)
    }

    override fun meanSquaredDivision(
        significanceLevel: SignificanceLevel,
        vararg numbers: Double
    ): Single<IConfidenceInterval> = rxCalculation {
        val s = calculator.correctedMeanSquareDeviation(*numbers).blockingGet()
        val q =  significanceLevel.pirson

        val left: Double
        val right: Double

        if (q < 1) {
            left = s * (1 - q)
            right = s * (1 + q)
        } else {
            left = 0.0
            right = s * (1 + q)
        }

        ConfidenceInterval(left, right)
    }
}