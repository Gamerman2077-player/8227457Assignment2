package com.example.s8227457assignment2.ui.dashboard

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.s8227457assignment2.R
import com.example.s8227457assignment2.data.model.Investment

class InvestmentViewHolder(
    itemView: View
) : RecyclerView.ViewHolder(itemView) {

    private val tickerTextView: TextView =
        itemView.findViewById(R.id.tvTicker)

    private val assetTypeTextView: TextView =
        itemView.findViewById(R.id.tvAssetType)

    private val currentPriceTextView: TextView =
        itemView.findViewById(R.id.tvCurrentPrice)

    private val dividendYieldTextView: TextView =
        itemView.findViewById(R.id.tvDividendYield)

    fun bind(
        investment: Investment,
        onClick: (Investment) -> Unit
    ) {
        tickerTextView.text = investment.ticker
        assetTypeTextView.text = investment.assetType

        currentPriceTextView.text =
            "$${String.format("%.2f", investment.currentPrice)}"

        dividendYieldTextView.text =
            "Dividend: ${String.format("%.2f", investment.dividendYield)}%"

        itemView.setOnClickListener {
            onClick(investment)
        }
    }
}