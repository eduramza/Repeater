package com.ramattec.repeater.di.domain

import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.ramattec.repeater.domain.login.EmailPasswordLoginUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
class UseCaseModule {

    @Provides
    @ViewModelScoped
    fun provideEmailPasswordLoginUseCase() = EmailPasswordLoginUseCase(Firebase.auth)

    @Provides
    @ViewModelScoped
    fun provideFirebaseAuth() = Firebase.auth
}