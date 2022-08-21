package com.ramattec.repeater.ui.login

import android.app.Activity
import android.content.IntentSender
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.gms.common.api.ApiException
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.GoogleAuthProvider
import com.ramattec.repeater.R
import com.ramattec.repeater.databinding.FragmentLoginBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private val loginViewModel: LoginViewModel by viewModels()
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private lateinit var oneTapClient: SignInClient
    private lateinit var signInRequest: BeginSignInRequest

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObservers()
        setupView()

        oneTapClient = Identity.getSignInClient(requireActivity())
        signInRequest = BeginSignInRequest.builder()
            .setPasswordRequestOptions(
                BeginSignInRequest.PasswordRequestOptions.builder()
                    .setSupported(true)
                    .build()
            )
            .setGoogleIdTokenRequestOptions(
                BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                    .setSupported(true)
                    .setServerClientId(getString(R.string.google_web_client_id))
                    .setFilterByAuthorizedAccounts(false)
                    .build()
            )
            .build()
    }

    private fun setupView() {
        binding.passwordInput.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                loginViewModel.onEvent(
                    LoginEvent.LoginWithEmailAndPassword(
                        binding.emailInput.text.toString(),
                        binding.passwordInput.text.toString()
                    )
                )
            }
            false
        }

        binding.login.setOnClickListener {
            loginViewModel.onEvent(
                LoginEvent.LoginWithEmailAndPassword(
                    binding.emailInput.text.toString(),
                    binding.passwordInput.text.toString()
                )
            )
        }

        binding.googleLogin.setOnClickListener {
            oneTapClient.beginSignIn(signInRequest)
                .addOnSuccessListener { result ->
                    try {
                        resultLauncher.launch(
                            IntentSenderRequest.Builder(result.pendingIntent.intentSender).build()
                        )
                    } catch (e: IntentSender.SendIntentException) {
                        Log.e("Google SignIn", e.message.toString())
                    }
                }
                .addOnFailureListener {
                    Log.e("Google SignIn", it.message.toString())
                }
        }

        binding.signup.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }
    }

    private fun setupObservers() {
        lifecycleScope.launchWhenCreated {
            loginViewModel.getLoginState().collect {
                when (it) {
                    LoginState.EmailError -> binding.emailInput.error = "Email inválido"
                    LoginState.NetworkError -> showLoginFailed("Error no login")
                    LoginState.PasswordError -> binding.passwordInput.error = "Senha inválida"
                    LoginState.Progress -> binding.loading.visibility = VISIBLE
                    is LoginState.UserLogged -> loginUser()
                }
            }
        }
    }

    private fun loginUser() {
        findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
    }

    private fun showLoginFailed(errorString: String) {
        binding.password.isErrorEnabled = true
        binding.email.isErrorEnabled = true
        Snackbar.make(binding.root, errorString, Snackbar.LENGTH_SHORT).show()
    }

    private var resultLauncher =
        registerForActivityResult(
            ActivityResultContracts.StartIntentSenderForResult()
        ) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                try {
                    val credential = oneTapClient.getSignInCredentialFromIntent(result.data)
                    val idToken = credential.googleIdToken
                    when {
                        idToken != null -> {
                            val firebaseCredential = GoogleAuthProvider.getCredential(idToken, null)
                            loginViewModel.onEvent(
                                LoginEvent.LoginWithGoogleAccount(
                                    firebaseCredential
                                )
                            )
                        }
                    }
                } catch (e: ApiException) {
                    Log.e("Google SignIn", e.message.toString())
                }
            }
        }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}