package com.ramattec.repeater.di

import com.ramattec.repeater.data.repository.login.LoginRepository
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
    fun provideEmailPasswordLoginUseCase(
        repository: LoginRepository
    ) = EmailPasswordLoginUseCase(repository)
}