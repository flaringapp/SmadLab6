package com.flaringapp.smadlab6.app.di

import com.flaringapp.smadlab6.data.calculator.CharacteristicsCalculator
import com.flaringapp.smadlab6.data.calculator.CharacteristicsCalculatorImpl
import com.flaringapp.smadlab6.data.confidenceIntervals.ConfidenceIntervalsCalculator
import com.flaringapp.smadlab6.data.confidenceIntervals.ConfidenceIntervalsCalculatorImpl
import com.flaringapp.smadlab6.data.intervalSplitter.IntervalSplitter
import com.flaringapp.smadlab6.data.intervalSplitter.IntervalSplitterImpl
import com.flaringapp.smadlab6.data.varianceAnalyzer.VarianceAnalyzer
import com.flaringapp.smadlab6.data.varianceAnalyzer.VarianceAnalyzerImpl
import com.flaringapp.smadlab6.data.varianceTwoFactorAnalyzer.VarianceTwoFactorAnalyzer
import com.flaringapp.smadlab6.data.varianceTwoFactorAnalyzer.VarianceTwoFactorAnalyzerImpl
import org.koin.dsl.module

val dataModule = module {

    single { IntervalSplitterImpl() as IntervalSplitter }

    single { CharacteristicsCalculatorImpl() as CharacteristicsCalculator }

    single { ConfidenceIntervalsCalculatorImpl(get()) as ConfidenceIntervalsCalculator }

    single { VarianceAnalyzerImpl() as VarianceAnalyzer }

    single { VarianceTwoFactorAnalyzerImpl() as VarianceTwoFactorAnalyzer }
}