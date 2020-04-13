package com.flaringapp.smadlab6.data.varianceAnalyzer

import io.reactivex.Single

interface VarianceAnalyzer {

    fun analyze(data: Array<DoubleArray>): Single<IVarianceAnalysis>

    interface IVarianceAnalysis {
        val intergroupVariance: Double
        val residualVariance: Double
        val totalVariance: Double
        val fisherCriterion: Double
    }

}