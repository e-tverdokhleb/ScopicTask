package com.scopictask.ys.di

import com.scopictask.ys.data.repository.RepositoryImpl
import com.scopictask.ys.data.repository.firebase.FirebaseDataSource
import com.scopictask.ys.data.repository.realm.RealmDataSource
import com.scopictask.ys.domain.repository.Repository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Singleton
    @Provides
    @Named("repo")
    fun provideRepository(
        @Named("firebase") firebaseRepository: Repository,
        @Named("realm") realmRepository: Repository,
    ): Repository = RepositoryImpl(
        firebaseRepository, realmRepository
    )

    @Singleton
    @Provides
    @Named("firebase")
    fun provideFirebaseRepository(): Repository = FirebaseDataSource()

    @Singleton
    @Provides
    @Named("realm")
    fun provideRealmRepository(): Repository = RealmDataSource()
}