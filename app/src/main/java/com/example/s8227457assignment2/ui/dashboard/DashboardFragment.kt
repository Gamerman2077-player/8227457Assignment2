package com.example.s8227457assignment2.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.s8227457assignment2.R
import com.example.s8227457assignment2.data.model.Investment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DashboardFragment : Fragment() {

    private val viewModel: DashboardViewModel by viewModels()

    private lateinit var adapter: InvestmentAdapter

    private var portfolioVisible = true
    private var availableFundsVisible = true

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(
            R.layout.fragment_dashboard,
            container,
            false
        )
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView =
            view.findViewById<RecyclerView>(
                R.id.recyclerViewInvestments
            )

        val investmentCount =
            view.findViewById<TextView>(
                R.id.tvInvestmentCount
            )

        val progressBar =
            view.findViewById<ProgressBar>(
                R.id.progressBar
            )

        val errorText =
            view.findViewById<TextView>(
                R.id.tvError
            )

        val portfolioValue =
            view.findViewById<TextView>(
                R.id.tvPortfolioValue
            )

        val availableFundsValue =
            view.findViewById<TextView>(
                R.id.tvAvailableFundsValue
            )

        val portfolioVisibilityButton =
            view.findViewById<ImageButton>(
                R.id.btnPortfolioVisibility
            )

        val availableFundsVisibilityButton =
            view.findViewById<ImageButton>(
                R.id.btnAvailableFundsVisibility
            )

        adapter = InvestmentAdapter { investment ->
            openInvestmentDetails(investment)
        }

        recyclerView.layoutManager =
            LinearLayoutManager(requireContext())

        recyclerView.adapter = adapter

        portfolioVisibilityButton.setOnClickListener {

            portfolioVisible = !portfolioVisible

            if (portfolioVisible) {
                portfolioValue.text =
                    getString(R.string.portfolio_placeholder)

                portfolioVisibilityButton.setImageResource(
                    R.drawable.ic_visibility
                )

                portfolioVisibilityButton.contentDescription =
                    getString(R.string.hide_balances)
            } else {
                portfolioValue.text =
                    getString(R.string.hidden_balance)

                portfolioVisibilityButton.setImageResource(
                    R.drawable.ic_visibility_closed
                )

                portfolioVisibilityButton.contentDescription =
                    getString(R.string.show_balances)
            }
        }

        availableFundsVisibilityButton.setOnClickListener {

            availableFundsVisible =
                !availableFundsVisible

            if (availableFundsVisible) {
                availableFundsValue.text =
                    getString(R.string.portfolio_placeholder)

                availableFundsVisibilityButton.setImageResource(
                    R.drawable.ic_visibility
                )

                availableFundsVisibilityButton.contentDescription =
                    getString(R.string.hide_balances)
            } else {
                availableFundsValue.text =
                    getString(R.string.hidden_balance)

                availableFundsVisibilityButton.setImageResource(
                    R.drawable.ic_visibility_closed
                )

                availableFundsVisibilityButton.contentDescription =
                    getString(R.string.show_balances)
            }
        }

        val keypass =
            arguments?.getString("keypass").orEmpty()

        viewLifecycleOwner.lifecycleScope.launch {

            viewLifecycleOwner.repeatOnLifecycle(
                Lifecycle.State.STARTED
            ) {

                viewModel.dashboardState.collect { state ->

                    when (state) {

                        DashboardState.Idle -> {
                            progressBar.visibility = View.GONE
                            errorText.visibility = View.GONE
                        }

                        DashboardState.Loading -> {
                            progressBar.visibility = View.VISIBLE
                            errorText.visibility = View.GONE
                        }

                        is DashboardState.Success -> {
                            progressBar.visibility = View.GONE
                            errorText.visibility = View.GONE

                            investmentCount.text =
                                "${state.entityTotal} investments"

                            adapter.submitList(
                                state.investments
                            )
                        }

                        is DashboardState.Error -> {
                            progressBar.visibility = View.GONE
                            errorText.visibility = View.VISIBLE
                            errorText.text = state.message
                        }
                    }
                }
            }
        }

        viewModel.loadDashboard(keypass)
    }

    private fun openInvestmentDetails(
        investment: Investment
    ) {

        val bundle = Bundle().apply {

            putString(
                "assetType",
                investment.assetType
            )

            putString(
                "ticker",
                investment.ticker
            )

            putDouble(
                "currentPrice",
                investment.currentPrice
            )

            putDouble(
                "dividendYield",
                investment.dividendYield
            )

            putString(
                "description",
                investment.description
            )
        }

        findNavController().navigate(
            R.id.action_dashboardFragment_to_detailsFragment,
            bundle
        )
    }
}