package com.ramattec.repeater

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.ramattec.repeater.databinding.ActivityMainBinding
import com.ramattec.repeater.domain.entity.user.UserEntity
import com.ramattec.repeater.ui.login.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private var binding: ActivityMainBinding? = null
    private val loginViewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        installSplashScreen().apply {
            setKeepOnScreenCondition{
                var splash = true
                lifecycleScope.launchWhenCreated {
                    loginViewModel.verifyIfUserIsLogged()
                    loginViewModel.uiState
                        .collect {
                            if (it.loggedUser != null) initNavHost(it.loggedUser.isLogged)
                            splash = it.isLoadingInitialUser
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
        val destination = if(isLogged) R.id.homeFragment else R.id.loginFragment
        navGraph.setStartDestination(destination)
        navController.graph = navGraph
    }

}