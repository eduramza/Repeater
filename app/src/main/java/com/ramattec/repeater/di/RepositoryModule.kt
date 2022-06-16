package com.ramattec.repeater.di

import com.ramattec.repeater.data.repository.login.LoginRepositoryImpl
import com.ramattec.repeater.data.repository.register.RegisterRepositoryImpl
import com.ramattec.repeater.domain.RegisterRepository
import com.ramattec.repeater.domain.repository.LoginRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
interface RepositoryModule {

    @Binds
    fun provideLoginRepository(loginRepository: LoginRepositoryImpl):
            LoginRepository

    @Binds
    fun provideRegisterRepository(registerRepositoryImpl: RegisterRepositoryImpl):
            RegisterRepository
}