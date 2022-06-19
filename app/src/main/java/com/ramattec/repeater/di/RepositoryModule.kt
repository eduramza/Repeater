package com.ramattec.repeater.di

import com.ramattec.repeater.data.repository.deck.DeckRepositoryImpl
import com.ramattec.repeater.data.repository.login.LoginRepositoryImpl
import com.ramattec.repeater.data.repository.register.RegisterRepositoryImpl
import com.ramattec.repeater.data.repository.user.UserRepositoryImpl
import com.ramattec.repeater.domain.repository.DeckRepository
import com.ramattec.repeater.domain.repository.LoginRepository
import com.ramattec.repeater.domain.repository.RegisterRepository
import com.ramattec.repeater.domain.repository.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
interface RepositoryModule {

    @Binds
    fun provideLoginRepository(loginRepository: LoginRepositoryImpl):
            LoginRepository

    @Binds
    fun provideRegisterRepository(registerRepositoryImpl: RegisterRepositoryImpl):
            RegisterRepository

    @Binds
    fun provideDeckRepository(deckRepository: DeckRepositoryImpl): DeckRepository

    @Binds
    fun provideUserRepository(userRepository: UserRepositoryImpl): UserRepository
}