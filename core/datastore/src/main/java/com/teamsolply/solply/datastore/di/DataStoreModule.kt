package com.teamsolply.solply.datastore.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.dataStoreFile
import com.teamsolply.solply.datastore.SolplySecureDataStoreSerializer
import com.teamsolply.solply.datastore.SolplyTokenData
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {
    @Provides
    @Singleton
    fun providesDataStore(
        @ApplicationContext context: Context,
        solplySecureDataStoreSerializer: SolplySecureDataStoreSerializer
    ): DataStore<SolplyTokenData> =
        DataStoreFactory.create(
            serializer = solplySecureDataStoreSerializer
        ) {
            context.dataStoreFile(DATASTORE_PREFERENCES)
        }

    private const val DATASTORE_PREFERENCES = "com.teamosolply.solply.datastore"
}
