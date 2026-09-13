package com.example.s8227457assignment2.ui.login

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
import com.example.s8227457assignment2.R
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private val viewModel: LoginViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(
            R.layout.fragment_login,
            container,
            false
        )
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        val usernameLayout =
            view.findViewById<TextInputLayout>(R.id.usernameLayout)

        val passwordLayout =
            view.findViewById<TextInputLayout>(R.id.passwordLayout)

        val usernameEditText =
            view.findViewById<TextInputEditText>(R.id.etUsername)

        val passwordEditText =
            view.findViewById<TextInputEditText>(R.id.etPassword)

        val loginButton =
            view.findViewById<View>(R.id.btnLogin)

        val progressBar =
            view.findViewById<View>(R.id.progressBar)

        val errorText =
            view.findViewById<TextView>(R.id.tvError)

        loginButton.setOnClickListener {

            usernameLayout.error = null
            passwordLayout.error = null

            val username =
                usernameEditText.text?.toString()?.trim().orEmpty()

            val password =
                passwordEditText.text?.toString()?.trim().orEmpty()

            var hasValidationError = false

            if (username.isBlank()) {
                usernameLayout.error = "Username is required"
                hasValidationError = true
            }

            if (password.isBlank()) {
                passwordLayout.error = "Password is required"
                hasValidationError = true
            }

            if (!hasValidationError) {
                viewModel.login(
                    username = username,
                    password = password
                )
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(
                Lifecycle.State.STARTED
            ) {
                viewModel.loginState.collect { state ->

                    when (state) {

                        LoginState.Idle -> {
                            progressBar.isVisible = false
                            errorText.isVisible = false
                            loginButton.isEnabled = true
                        }

                        LoginState.Loading -> {
                            progressBar.isVisible = true
                            errorText.isVisible = false
                            loginButton.isEnabled = false
                        }

                        is LoginState.Success -> {
                            progressBar.isVisible = false
                            errorText.isVisible = false
                            loginButton.isEnabled = true

                            val bundle = Bundle().apply {
                                putString(
                                    "keypass",
                                    state.keypass
                                )
                            }

                            findNavController().navigate(
                                R.id.action_loginFragment_to_dashboardFragment,
                                bundle
                            )
                        }

                        is LoginState.Error -> {
                            progressBar.isVisible = false
                            loginButton.isEnabled = true

                            errorText.text = state.message
                            errorText.isVisible = true
                        }
                    }
                }
            }
        }
    }
}