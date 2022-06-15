package com.ramattec.repeater.di

import com.google.firebase.auth.FirebaseAuth
import com.ramattec.repeater.data.repository.login.LoginRepository
import com.ramattec.repeater.data.repository.register.RegisterRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
class RepositoryModule {

    @Provides
    @ViewModelScoped
    fun provideAuthRepository(
        auth: FirebaseAuth,
        fireStoreUserRepository: RegisterRepositoryImpl
    ) = LoginRepository(auth, fireStoreUserRepository)
}