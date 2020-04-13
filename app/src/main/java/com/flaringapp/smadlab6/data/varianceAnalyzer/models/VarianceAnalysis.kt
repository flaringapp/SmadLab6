package com.flaringapp.smadlab6.data.varianceAnalyzer.models

import com.flaringapp.smadlab6.data.varianceAnalyzer.VarianceAnalyzer

data class VarianceAnalysis(
    override val intergroupVariance: Double,
    override val residualVariance: Double,
    override val totalVariance: Double,
    override val fisherCriterion: Double
): VarianceAnalyzer.IVarianceAnalysis