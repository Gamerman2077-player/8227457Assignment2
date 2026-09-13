package com.example.s8227457assignment2.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.s8227457assignment2.R

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
            "$${String.format("%.2f", currentPrice)}"

        dividendYieldText.text =
            "${String.format("%.2f", dividendYield)}%"

        descriptionText.text = description
    }
}