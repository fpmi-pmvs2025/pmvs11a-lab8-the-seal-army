package by.bsu.superweather

import by.bsu.superweather.data.Data
import by.bsu.superweather.files.byHours
import org.json.JSONArray
import org.json.JSONObject
import org.junit.Assert.assertEquals
import org.junit.Test

class WeatherTest {

    @Test
    fun testByHoursWithEmptyString() {
        val result = byHours("")
        assertEquals(0, result.size)
    }

    @Test
    fun testByHoursWithValidJSON() {
        val jsonHour1 = JSONObject()
        jsonHour1.put("time", "12:00")
        jsonHour1.put("temp_c", "15.5")

        val condition1 = JSONObject()
        condition1.put("text", "Sunny")
        condition1.put("icon", "/sunny.png")
        jsonHour1.put("condition", condition1)

        val jsonHour2 = JSONObject()
        jsonHour2.put("time", "13:00")
        jsonHour2.put("temp_c", "16.3")

        val condition2 = JSONObject()
        condition2.put("text", "Cloudy")
        condition2.put("icon", "/cloudy.png")
        jsonHour2.put("condition", condition2)

        val jsonArray = JSONArray()
        jsonArray.put(jsonHour1)
        jsonArray.put(jsonHour2)

        val result = byHours(jsonArray.toString())

        assertEquals(2, result.size)

        assertEquals("", result[0].city)
        assertEquals("12:00", result[0].time)
        assertEquals("15℃", result[0].currTemp)
        assertEquals("Sunny", result[0].conditional)
        assertEquals("/sunny.png", result[0].icon)

        assertEquals("", result[1].city)
        assertEquals("13:00", result[1].time)
        assertEquals("16℃", result[1].currTemp)
        assertEquals("Cloudy", result[1].conditional)
        assertEquals("/cloudy.png", result[1].icon)
    }
}