package com.example.data.di

import com.example.data.dataSource.AuthOnlineDataSourceImpl
import com.example.data.dataSource.ChatOnlineDataSourceImpl
import com.example.data.repository.AuthRepositoryImpl
import com.example.data.repository.ChatRepositoryImpl
import com.example.domain.repository.AuthOnlineDataSource
import com.example.domain.repository.AuthRepository
import com.example.domain.repository.ChatOnlineDataSource
import com.example.domain.repository.ChatRepository
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoriesModule {

    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth {
        return Firebase.auth
    }

    @Provides
    @Singleton
    fun provideFirebaseFirestore(): FirebaseFirestore {
        return Firebase.firestore
    }


    @Provides
    @Singleton
    fun provideAuthOnlineDataSource(
        firebaseAuth: FirebaseAuth,
        firestore: FirebaseFirestore
    ): AuthOnlineDataSource {
        return AuthOnlineDataSourceImpl(firebaseAuth,firestore)
    }

    @Provides
    @Singleton
    fun provideAuthOnlineRepository(onlineDataSource: AuthOnlineDataSource): AuthRepository {
        return AuthRepositoryImpl(onlineDataSource)
    }

    @Provides
    @Singleton
    fun provideChatRepository(onlineDataSource: ChatOnlineDataSource):ChatRepository{
        return ChatRepositoryImpl(onlineDataSource)
    }

    @Provides
    @Singleton
    fun provideChatOnlineDataSource(firestore: FirebaseFirestore):ChatOnlineDataSource{
        return ChatOnlineDataSourceImpl(firestore)
    }
}