package com.flaringapp.smadlab6.presentation.fragments.home.impl

import com.flaringapp.smadlab6.R
import com.flaringapp.smadlab6.data.varianceTwoFactorAnalyzer.VarianceTwoFactorAnalyzer
import com.flaringapp.smadlab6.presentation.fragments.home.HomeContract
import com.flaringapp.smadlab6.presentation.mvp.BasePresenter
import io.reactivex.disposables.Disposable
import java.text.DecimalFormat

class HomePresenter(
    private val varianceTwoFactorAnalyzer: VarianceTwoFactorAnalyzer
) : BasePresenter<HomeContract.ViewContract>(), HomeContract.PresenterContract {

    companion object {
        private const val SPACE = " "

        private val decimalFormat: DecimalFormat = DecimalFormat("0.0000")

        private const val DEFINED_INPUT_1 = "0.32 0.36 0.37 0.37 0.37 0.30 0.38 0.42"
        private const val DEFINED_INPUT_2 = "0.38 0.37 0.38 0.36 0.40 0.36 0.48 0.32"
        private const val DEFINED_INPUT_3 = "0.32 0.31 0.36 0.33 0.40 0.42 0.43 0.41"
        private const val DEFINED_INPUT_4 = "0.29 0.35 0.34 0.42 0.47 0.60 0.91 0.45"
    }

    private var numbers1: String = ""
    private var numbers2: String = ""
    private var numbers3: String = ""
    private var numbers4: String = ""

    private var calculationRequest: Disposable? = null

    override fun onStart() {
        super.onStart()

        view?.initInput1(DEFINED_INPUT_1)
        view?.initInput2(DEFINED_INPUT_2)
        view?.initInput3(DEFINED_INPUT_3)
        view?.initInput4(DEFINED_INPUT_4)

        tryToCalculate()
    }

    override fun release() {
        calculationRequest?.dispose()
        super.release()
    }

    override fun onInput1(input: String) {
        numbers1 = input
        clearErrors()
        tryToCalculate()
    }

    override fun onInput2(input: String) {
        numbers2 = input
        clearErrors()
        tryToCalculate()
    }

    override fun onInput3(input: String) {
        numbers3 = input
        clearErrors()
        tryToCalculate()
    }

    override fun onInput4(input: String) {
        numbers4 = input
        clearErrors()
        tryToCalculate()
    }

    private fun clearErrors() {
        view?.setInput1Error(null)
        view?.setInput2Error(null)
        view?.setInput3Error(null)
        view?.setInput4Error(null)
    }

    private fun tryToCalculate() {
        if (!validateInput()) return

        calculationRequest?.dispose()
        calculationRequest = varianceTwoFactorAnalyzer.analyze(
            arrayOf(
                parseNumbers(numbers1),
                parseNumbers(numbers2),
                parseNumbers(numbers3),
                parseNumbers(numbers4)
            )
        )
            .subscribe(
                {
                    view?.setIntergroupVariance(
                        "${decimalFormat.format(it.intergroup1Variance)} ; ${decimalFormat.format(it.intergroup2Variance)}"
                    )
                    view?.setResidualVariance(decimalFormat.format(it.residualVariance))
                    view?.setTotalVariance(decimalFormat.format(it.totalVariance))
                    view?.setFisherCriterion(
                        "${decimalFormat.format(it.fisherCriterion1)} ; ${decimalFormat.format(it.fisherCriterion2)}"
                    )
                },
                { view?.handleError(it) }
            )
    }

    private fun parseNumbers(numbers: String) = numbers.trim().split(SPACE)
        .map { it.trim() }
        .filter { it.isNotEmpty() }
        .map { it.toDouble() }
        .toDoubleArray()


    private fun validateInput(): Boolean {
        var isOk = true

        if (!validateNumbers(numbers1) { view?.setInput1Error(it) }) isOk = false
        if (!validateNumbers(numbers2) { view?.setInput2Error(it) }) isOk = false
        if (!validateNumbers(numbers3) { view?.setInput3Error(it) }) isOk = false
        if (!validateNumbers(numbers4) { view?.setInput4Error(it) }) isOk = false

        if (!isOk) return false

        val numbers1 = parseNumbers(this.numbers1)
        val numbers2 = parseNumbers(this.numbers2)
        val numbers3 = parseNumbers(this.numbers3)
        val numbers4 = parseNumbers(this.numbers4)

        val maxLength = listOf(numbers1.size, numbers2.size, numbers3.size, numbers4.size).max()!!

        if (numbers1.size < maxLength) {
            view?.setInput1Error(R.string.input_numbers_less_than_max)
            isOk = false
        }
        if (numbers2.size < maxLength) {
            view?.setInput2Error(R.string.input_numbers_less_than_max)
            isOk = false
        }
        if (numbers3.size < maxLength) {
            view?.setInput3Error(R.string.input_numbers_less_than_max)
            isOk = false
        }
        if (numbers4.size < maxLength) {
            view?.setInput4Error(R.string.input_numbers_less_than_max)
            isOk = false
        }

        return isOk
    }

    private fun validateNumbers(numbers: String, errorAction: (Int) -> Unit): Boolean {
        if (numbers.trim().isEmpty()) {
            errorAction(R.string.input_too_short)
            return false
        }

        val numbersSplit = numbers.trim().split(SPACE)
            .map { it.trim() }
            .filter { it.isNotEmpty() }

        if (numbersSplit.isEmpty()) {
            errorAction(R.string.input_too_short)
            return false
        }

        if (numbersSplit.find { !it.isDecimal() } != null) {
            errorAction(R.string.input_invalid_only_decimal)
            return false
        }

        if (numbersSplit.find { it == "." } != null) {
            errorAction(R.string.input_invalid_dot)
            return false
        }

        return true
    }

    private fun String.isDecimal(): Boolean {
        return let {
            if (startsWith('-')) {
                if (length == 1) return false
                substring(1)
            } else this
        }
            .find { !it.isDigit() && it != '.' } == null
    }
}
