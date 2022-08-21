package com.ramattec.repeater

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.ramattec.repeater.databinding.ActivityMainBinding
import com.ramattec.repeater.ui.login.LoginEvent
import com.ramattec.repeater.ui.login.LoginState
import com.ramattec.repeater.ui.login.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private var binding: ActivityMainBinding? = null
    private val loginViewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        installSplashScreen().apply {
            setKeepOnScreenCondition {
                var splash = true
                lifecycleScope.launchWhenCreated {
                    loginViewModel.onEvent(LoginEvent.VerifyIfUserIsLogged)
                    loginViewModel.getLoginState().collect {
                        if (it is LoginState.UserLogged) {
                            initNavHost(true)
                            splash = false
                        }
                        else if (it == LoginState.UserNotLogged) {
                            initNavHost(false)
                            splash = false
                        }
                    }
                }
                splash
            }
        }
        setContentView(binding?.root)
    }

    private fun initNavHost(isLogged: Boolean) {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment)
        val graphInflater = navHostFragment!!.findNavController().navInflater
        val navGraph = graphInflater.inflate(R.navigation.repeater_nav_graph)
        val navController = navHostFragment.findNavController()
        val destination = if (isLogged) R.id.homeFragment else R.id.loginFragment
        navGraph.setStartDestination(destination)
        navController.graph = navGraph
    }

}