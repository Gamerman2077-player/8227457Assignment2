package com.example.s8227457assignment2.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.s8227457assignment2.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DashboardFragment : Fragment() {

    private val viewModel: DashboardViewModel by viewModels()

    private lateinit var investmentAdapter: InvestmentAdapter

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
            view.findViewById<RecyclerView>(R.id.recyclerViewInvestments)

        val progressBar =
            view.findViewById<View>(R.id.progressBar)

        val errorText =
            view.findViewById<TextView>(R.id.tvError)

        val entityCount =
            view.findViewById<TextView>(R.id.tvEntityCount)

        investmentAdapter = InvestmentAdapter { investment ->

            val bundle = Bundle().apply {
                putString("assetType", investment.assetType)
                putString("ticker", investment.ticker)
                putDouble("currentPrice", investment.currentPrice)
                putDouble("dividendYield", investment.dividendYield)
                putString("description", investment.description)
            }

            findNavController().navigate(
                R.id.action_dashboardFragment_to_detailsFragment,
                bundle
            )
        }

        recyclerView.layoutManager =
            LinearLayoutManager(requireContext())

        recyclerView.adapter =
            investmentAdapter

        val keypass =
            arguments?.getString("keypass").orEmpty()

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(
                Lifecycle.State.STARTED
            ) {
                viewModel.dashboardState.collect { state ->

                    when (state) {

                        DashboardState.Idle -> {
                            progressBar.isVisible = false
                            errorText.isVisible = false
                        }

                        DashboardState.Loading -> {
                            progressBar.isVisible = true
                            errorText.isVisible = false
                            recyclerView.isVisible = false
                        }

                        is DashboardState.Success -> {
                            progressBar.isVisible = false
                            errorText.isVisible = false
                            recyclerView.isVisible = true

                            entityCount.text =
                                "${state.entityTotal} investments"

                            investmentAdapter.submitList(
                                state.investments
                            )
                        }

                        is DashboardState.Error -> {
                            progressBar.isVisible = false
                            recyclerView.isVisible = false

                            errorText.text = state.message
                            errorText.isVisible = true
                        }
                    }
                }
            }
        }

        viewModel.loadDashboard(keypass)
    }
}