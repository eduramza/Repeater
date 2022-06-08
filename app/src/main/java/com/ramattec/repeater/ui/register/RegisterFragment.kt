package com.ramattec.repeater.ui.register

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ramattec.repeater.R
import com.ramattec.repeater.databinding.FragmentRegisterBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment : Fragment() {

    private val registerViewModel: RegisterViewModel by viewModels()
    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObservers()
        setupView()
    }

    private fun setupView() {
        binding.signup.setOnClickListener {
            registerViewModel.doRegister(
                binding.emailInput.text.toString(),
                binding.passwordInput.text.toString(),
                binding.nameInput.text.toString()
            )
        }
    }

    private fun setupObservers() {
        registerViewModel.registerResult.observe(viewLifecycleOwner){ register ->
            register.error?.let {
                showRegisterError(it)
            }
            register.isLoading.apply {
                if (this) showLoading() else hideLoading()
            }
            register.success?.let {
                loginNewUser()
            }
        }
    }

    private fun loginNewUser() {
        findNavController().navigate(R.id.action_registerFragment_to_homeFragment)
    }

    private fun hideLoading() {
        binding.loading.visibility = GONE
    }

    private fun showLoading() {
        binding.loading.visibility = VISIBLE
    }

    private fun showRegisterError(error: String) {
        hideLoading()
        Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}