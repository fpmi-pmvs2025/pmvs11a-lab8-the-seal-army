package by.bsu.superweather.data

import android.annotation.SuppressLint
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
        val createTable = "CREATE TABLE IF NOT EXISTS $TABLE_NAME (" +
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

    fun insertCity(cityName: String, country: String = "", temperature: String = "", description: String = "") {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_NAME, cityName)
            put(COLUMN_COUNTRY, country)
            put(COLUMN_TEMPERATURE, temperature)
            put(COLUMN_DESCRIPTION, description)
        }
        db.insertWithOnConflict(TABLE_NAME, null, values, SQLiteDatabase.CONFLICT_REPLACE)
        db.close()
    }

    @SuppressLint("Range")
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

}
