package by.bsu.superweather

import by.bsu.superweather.data.Data
import org.junit.Assert.assertEquals
import org.junit.Test

class DataTest {

    @Test
    fun testDataClass() {
        val data = Data(
            city = "Minsk",
            time = "2025-04-24",
            currTemp = "10.5",
            conditional = "Cloudy",
            icon = "/cloudy.png",
            maxT = "12.0",
            minT = "8.0",
            hours = "[]"
        )

        assertEquals("Minsk", data.city)
        assertEquals("2025-04-24", data.time)
        assertEquals("10.5", data.currTemp)
        assertEquals("Cloudy", data.conditional)
        assertEquals("/cloudy.png", data.icon)
        assertEquals("12.0", data.maxT)
        assertEquals("8.0", data.minT)
        assertEquals("[]", data.hours)
    }

    @Test
    fun testDataClassCopy() {
        val originalData = Data(
            city = "Minsk",
            time = "2025-04-24",
            currTemp = "10.5",
            conditional = "Cloudy",
            icon = "/cloudy.png",
            maxT = "12.0",
            minT = "8.0",
            hours = "[]"
        )

        val copiedData = originalData.copy(
            city = "Brest",
            currTemp = "15.0"
        )

        assertEquals("Brest", copiedData.city)
        assertEquals("15.0", copiedData.currTemp)

        assertEquals(originalData.time, copiedData.time)
        assertEquals(originalData.conditional, copiedData.conditional)
        assertEquals(originalData.icon, copiedData.icon)
        assertEquals(originalData.maxT, copiedData.maxT)
        assertEquals(originalData.minT, copiedData.minT)
        assertEquals(originalData.hours, copiedData.hours)
    }
}