package com.example.shoppinglists.Hilt

import android.content.Context
import androidx.room.Dao
import androidx.room.Room
import com.example.shoppinglists.Room.ShoppingListsDao
import com.example.shoppinglists.Room.ShoppingListsDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideDatabase(
            @ApplicationContext context: Context
    ) = Room.databaseBuilder(context,
            ShoppingListsDatabase::class.java,
            "shopping_lists_database"
    ).build()

    @Singleton
    @Provides
    fun provideDao(database: ShoppingListsDatabase
    ) : ShoppingListsDao = database.getDao()
}