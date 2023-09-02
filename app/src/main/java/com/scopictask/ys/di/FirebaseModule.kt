package com.scopictask.ys.di

import com.google.firebase.auth.FirebaseAuth
import com.scopictask.ys.data.repository.RepositoryImpl
import com.scopictask.ys.data.repository.firebase.FirebaseRepository
import com.scopictask.ys.data.repository.realm.RealmRepository
import com.scopictask.ys.domain.repository.Repository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class FirebaseModule {

    @Singleton
    @Provides
    fun provideFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()
}