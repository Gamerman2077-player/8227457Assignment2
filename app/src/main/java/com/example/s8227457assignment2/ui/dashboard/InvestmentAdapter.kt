package com.example.s8227457assignment2.ui.dashboard

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.s8227457assignment2.R
import com.example.s8227457assignment2.data.model.Investment

class InvestmentAdapter(
    private val onInvestmentClick: (Investment) -> Unit
) : RecyclerView.Adapter<InvestmentViewHolder>() {

    private var investments: List<Investment> = emptyList()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): InvestmentViewHolder {

        val view = LayoutInflater
            .from(parent.context)
            .inflate(
                R.layout.item_investment,
                parent,
                false
            )

        return InvestmentViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: InvestmentViewHolder,
        position: Int
    ) {
        holder.bind(
            investment = investments[position],
            onClick = onInvestmentClick
        )
    }

    override fun getItemCount(): Int {
        return investments.size
    }

    fun submitList(
        newInvestments: List<Investment>
    ) {
        investments = newInvestments
        notifyDataSetChanged()
    }
}