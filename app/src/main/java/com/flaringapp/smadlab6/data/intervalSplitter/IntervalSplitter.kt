package com.flaringapp.smadlab6.data.intervalSplitter

import io.reactivex.Single

interface IntervalSplitter {

    fun splitToIntervals(vararg numbers: Double): Single<List<IIntervalModel>>

    interface IIntervalModel {
        val leftBound: Double
        val rightBound: Double
        val size: Double
        val frequency: Int
        val average: Double
    }

}