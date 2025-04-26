package by.bsu.superweather

import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import androidx.test.ext.junit.runners.AndroidJUnit4
import by.bsu.superweather.data.CityDatabaseHelper
import io.mockk.*
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.any
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mockito.*

class CityDatabaseHelperTest {

    private val mockDb = mockk<SQLiteDatabase>(relaxed = true)
    private val mockCursor = mockk<Cursor>(relaxed = true)
    private lateinit var databaseHelper: CityDatabaseHelper

    @Before
    fun setup() {
        databaseHelper = spyk(CityDatabaseHelper(mockk(relaxed = true)))

        every { databaseHelper.readableDatabase } returns mockDb
        every { databaseHelper.writableDatabase } returns mockDb
        every { mockDb.query(any(), any(), any(), any(), any(), any(), any()) } returns mockCursor
        every { mockDb.insert(any(), any(), any()) } returns 1L
        every { mockCursor.moveToFirst() } returns true
        every { mockCursor.moveToNext() } returnsMany listOf(true, false)
        every { mockCursor.getColumnIndex(CityDatabaseHelper.COLUMN_NAME) } returns 0
        every { mockCursor.getString(0) } returnsMany listOf("Minsk", "Brest")
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun testGetAllCities() {
        val expectedCities = listOf("Minsk", "Brest")

        val cities = databaseHelper.getAllCities()

        assertEquals(expectedCities, cities)

        verify { databaseHelper.readableDatabase }
        verify { mockDb.query(any(), any(), any(), any(), any(), any(), any()) }
        verify { mockCursor.close() }
    }

}