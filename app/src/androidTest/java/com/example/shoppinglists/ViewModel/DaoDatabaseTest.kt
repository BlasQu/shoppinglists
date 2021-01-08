package com.example.shoppinglists.ViewModel

import androidx.room.Dao
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.base.MainThread
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.shoppinglists.Data.ListDetails
import com.example.shoppinglists.Data.ListItem
import com.example.shoppinglists.Room.ShoppingListsDao
import com.example.shoppinglists.Room.ShoppingListsDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.equalTo
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class DatabaseTest {

    private lateinit var database: ShoppingListsDatabase
    private lateinit var dao: ShoppingListsDao

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(ApplicationProvider.getApplicationContext(), ShoppingListsDatabase::class.java).allowMainThreadQueries().build()
        dao = database.getDao()
    }

    @After
    fun teardown() {
        database.close()
    }

    @Test
    fun insertList() = runBlocking {
        val listItem = ListItem(0, "das", "05/08", emptyList<ListDetails>(), false)
        dao.insertList(listItem)

        val result = dao.testReadLists()
        assertThat(result.size, equalTo(1))

    }

    @Test
    fun insertCorrectList() = runBlocking {
        val listItem = ListItem(5, "das", "05/08")
        dao.insertList(listItem)

        val result = dao.testReadLists()
        assertThat(result[0], equalTo(listItem))

    }

    @Test
    fun deleteList() = runBlocking {
        val listItem = ListItem(5, "das", "05/08")
        dao.insertList(listItem)
        dao.deleteList(listOf(listItem))

        val result = dao.testReadLists()
        assertThat(result.size, equalTo(0))

    }

    @Test
    fun updateList() = runBlocking {
        val listItem = ListItem(5, "das", "05/08")
        dao.insertList(listItem)
        dao.updateList(ListItem(5, "dasdasasd", "02/01"))

        val result = dao.testReadLists()
        assertThat(result[0], equalTo(ListItem(5, "dasdasasd", "02/01")))

    }

    @Test
    fun toArchiveList() = runBlocking {
        val listItem = ListItem(5, "das", "05/08")
        dao.insertList(listItem)
        dao.updateToArchive(listOf(listItem.itemId))
        listItem.stored = true

        val result = dao.testReadLists()
        assertThat(result[0], equalTo(listItem))

    }


    @Test
    fun toStashList() = runBlocking {
        val listItem = ListItem(5, "das", "05/08", stored = true)
        dao.insertList(listItem)
        dao.updateToArchive(listOf(listItem.itemId))
        listItem.stored = false

        val result = dao.testReadLists()
        assertThat(result[0], equalTo(listItem))

    }


}