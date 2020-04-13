package com.flaringapp.smadlab6.data.varianceTwoFactorAnalyzer

import io.reactivex.Single

interface VarianceTwoFactorAnalyzer {

    fun analyze(data: Array<DoubleArray>): Single<IVarianceTwoFactorAnalysis>

    interface IVarianceTwoFactorAnalysis {
        val intergroup1Variance: Double
        val intergroup2Variance: Double
        val residualVariance: Double
        val totalVariance: Double
        val fisherCriterion1: Double
        val fisherCriterion2: Double
    }

}