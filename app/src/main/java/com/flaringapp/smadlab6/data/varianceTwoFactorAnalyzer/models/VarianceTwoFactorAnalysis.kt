package com.flaringapp.smadlab6.data.varianceTwoFactorAnalyzer.models

import com.flaringapp.smadlab6.data.varianceTwoFactorAnalyzer.VarianceTwoFactorAnalyzer

data class VarianceTwoFactorAnalysis(
    override val intergroup1Variance: Double,
    override val intergroup2Variance: Double,
    override val residualVariance: Double,
    override val totalVariance: Double,
    override val fisherCriterion1: Double,
    override val fisherCriterion2: Double
): VarianceTwoFactorAnalyzer.IVarianceTwoFactorAnalysis