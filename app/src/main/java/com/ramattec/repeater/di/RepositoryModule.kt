package com.ramattec.repeater.di

import com.ramattec.data.repository.DeckRepositoryImpl
import com.ramattec.data.repository.RegisterRepositoryImpl
import com.ramattec.data.repository.UserRepositoryImpl
import com.ramattec.domain.repository.DeckRepository
import com.ramattec.domain.repository.RegisterRepository
import com.ramattec.domain.repository.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
interface RepositoryModule {

    @Binds
    fun provideRegisterRepository(registerRepositoryImpl: RegisterRepositoryImpl):
            RegisterRepository

    @Binds
    fun provideDeckRepository(deckRepository: DeckRepositoryImpl): DeckRepository

    @Binds
    fun provideUserRepository(userRepository: UserRepositoryImpl): UserRepository
}