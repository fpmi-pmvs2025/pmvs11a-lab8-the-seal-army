package by.bsu.superweather.data

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class CityDatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "cities.db"
        private const val DATABASE_VERSION = 4
        const val TABLE_NAME = "cities"
        const val COLUMN_NAME = "name"
        const val COLUMN_COUNTRY = "country"
        const val COLUMN_TEMPERATURE = "temperature"
        const val COLUMN_DESCRIPTION = "description"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTable = "CREATE TABLE $TABLE_NAME (" +
                "$COLUMN_NAME TEXT PRIMARY KEY," +
                "$COLUMN_COUNTRY TEXT," +
                "$COLUMN_TEMPERATURE TEXT," +
                "$COLUMN_DESCRIPTION TEXT)"
        db.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }
    fun insertCity(cityName: String) {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_NAME, cityName)
        }
        db.insert(TABLE_NAME, null, values)
        db.close()
    }

    // Retrieve all cities from the database
    fun getAllCities(): List<String> {
        val cities = mutableListOf<String>()
        val db = this.readableDatabase
        val cursor: Cursor = db.query(TABLE_NAME, arrayOf(COLUMN_NAME), null, null, null, null, null)

        if (cursor.moveToFirst()) {
            do {
                cities.add(cursor.getString(cursor.getColumnIndex(COLUMN_NAME)))
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return cities
    }
    fun dropDatabase(context: Context) {
        context.deleteDatabase(CityDatabaseHelper.DATABASE_NAME)
    }
}
