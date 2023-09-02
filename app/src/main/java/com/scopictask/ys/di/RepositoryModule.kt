package com.scopictask.ys.di

import com.scopictask.ys.data.repository.RepositoryImpl
import com.scopictask.ys.data.repository.firebase.FirebaseRepository
import com.scopictask.ys.data.repository.realm.RealmRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Singleton
    @Provides
    fun provideRepositoryImpl(): RepositoryImpl = RepositoryImpl(
        provideFirebaseRepository(),
        provideRealmRepository()
    )

    @Singleton
    @Provides
    fun provideFirebaseRepository(): FirebaseRepository = FirebaseRepository()

    @Singleton
    @Provides
    fun provideRealmRepository(): RealmRepository = RealmRepository()
}