package com.example.s8227457assignment2.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.s8227457assignment2.R
import com.google.android.material.button.MaterialButtonToggleGroup
import java.util.Locale
import java.util.Random
import kotlin.math.max

class DetailsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(
            R.layout.fragment_details,
            container,
            false
        )
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        val tickerText =
            view.findViewById<TextView>(R.id.tvTicker)

        val assetTypeText =
            view.findViewById<TextView>(R.id.tvAssetType)

        val currentPriceText =
            view.findViewById<TextView>(R.id.tvCurrentPrice)

        val dividendYieldText =
            view.findViewById<TextView>(R.id.tvDividendYield)

        val descriptionText =
            view.findViewById<TextView>(R.id.tvDescription)

        val priceTrendView =
            view.findViewById<PriceTrendView>(
                R.id.price_trend_view
            )

        val timeframeToggleGroup =
            view.findViewById<MaterialButtonToggleGroup>(
                R.id.timeframe_toggle_group
            )

        val ticker =
            arguments?.getString("ticker").orEmpty()

        val assetType =
            arguments?.getString("assetType").orEmpty()

        val currentPrice =
            arguments?.getDouble("currentPrice") ?: 0.0

        val dividendYield =
            arguments?.getDouble("dividendYield") ?: 0.0

        val description =
            arguments?.getString("description").orEmpty()

        tickerText.text = ticker
        assetTypeText.text = assetType

        currentPriceText.text =
            String.format(
                Locale.US,
                "${'$'}%.2f",
                currentPrice
            )

        dividendYieldText.text =
            String.format(
                Locale.US,
                "%.2f%%",
                dividendYield
            )

        descriptionText.text = description

        // Default chart = 1 day
        timeframeToggleGroup.check(R.id.btn_1d)

        updateChart(
            chart = priceTrendView,
            ticker = ticker,
            currentPrice = currentPrice,
            timeframe = Timeframe.ONE_DAY
        )

        timeframeToggleGroup.addOnButtonCheckedListener {
                _,
                checkedId,
                isChecked ->

            if (!isChecked) {
                return@addOnButtonCheckedListener
            }

            val timeframe = when (checkedId) {

                R.id.btn_1d ->
                    Timeframe.ONE_DAY

                R.id.btn_1w ->
                    Timeframe.ONE_WEEK

                R.id.btn_1m ->
                    Timeframe.ONE_MONTH

                R.id.btn_1y ->
                    Timeframe.ONE_YEAR

                R.id.btn_all ->
                    Timeframe.ALL_TIME

                else ->
                    Timeframe.ONE_DAY
            }

            updateChart(
                chart = priceTrendView,
                ticker = ticker,
                currentPrice = currentPrice,
                timeframe = timeframe
            )
        }
    }

    private fun updateChart(
        chart: PriceTrendView,
        ticker: String,
        currentPrice: Double,
        timeframe: Timeframe
    ) {
        val trendData = generateTrendData(
            ticker = ticker,
            currentPrice = currentPrice,
            timeframe = timeframe
        )

        chart.setData(trendData)
    }

    private fun generateTrendData(
        ticker: String,
        currentPrice: Double,
        timeframe: Timeframe
    ): List<Double> {

        if (currentPrice <= 0.0) {
            return List(timeframe.points) { 0.0 }
        }

        val seed =
            ticker.hashCode().toLong() +
                    timeframe.seedOffset

        val random = Random(seed)

        /*
         * Generate backwards from the real API current price.
         * This guarantees the final point always matches the
         * current price supplied by the assignment API.
         */
        val reversedValues =
            mutableListOf(currentPrice)

        var value = currentPrice

        repeat(timeframe.points - 1) {

            val randomMovement =
                (random.nextDouble() - 0.5) *
                        timeframe.volatility

            val denominator =
                1.0 + randomMovement

            value =
                if (denominator > 0.0) {
                    value / denominator
                } else {
                    value
                }

            value = max(
                value,
                currentPrice * 0.1
            )

            reversedValues.add(value)
        }

        return reversedValues.reversed()
    }

    private enum class Timeframe(
        val points: Int,
        val volatility: Double,
        val seedOffset: Long
    ) {

        ONE_DAY(
            points = 24,
            volatility = 0.008,
            seedOffset = 1L
        ),

        ONE_WEEK(
            points = 28,
            volatility = 0.015,
            seedOffset = 2L
        ),

        ONE_MONTH(
            points = 30,
            volatility = 0.025,
            seedOffset = 3L
        ),

        ONE_YEAR(
            points = 52,
            volatility = 0.045,
            seedOffset = 4L
        ),

        ALL_TIME(
            points = 64,
            volatility = 0.065,
            seedOffset = 5L
        )
    }
}