package by.bsu.superweather

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import by.bsu.superweather.data.Data
import by.bsu.superweather.files.TabLayout
import by.bsu.superweather.files.mainCard
import org.junit.Rule
import org.junit.Test

class WeatherUITest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testMainCardDisplaysCorrectData() {
        val testData = Data(
            city = "Minsk",
            time = "12:00",
            currTemp = "15.0",
            conditional = "Sunny",
            icon = "/sunny.png",
            maxT = "18.0",
            minT = "12.0",
            hours = ""
        )

        val cday = mutableStateOf(testData)
        var syncClicked = false
        var searchClicked = false

        composeTestRule.setContent {
            mainCard(
                cday = cday,
                onClickSync = { syncClicked = true },
                onClickSearch = { searchClicked = true }
            )
        }

        composeTestRule.onNodeWithText("Minsk").assertExists()
        composeTestRule.onNodeWithText("15 ℃").assertExists()
        composeTestRule.onNodeWithText("Sunny").assertExists()
        composeTestRule.onNodeWithText("18℃/12℃").assertExists()

        composeTestRule.onAllNodesWithContentDescription("sky")[1].performClick()
        assert(syncClicked)

        composeTestRule.onAllNodesWithContentDescription("sky")[0].performClick()
        assert(searchClicked)
    }

    @Test
    fun testTabLayoutSwitchesTabs() {
        val testData = Data(
            city = "Minsk",
            time = "12:00",
            currTemp = "15.0",
            conditional = "Sunny",
            icon = "/sunny.png",
            maxT = "18.0",
            minT = "12.0",
            hours = "[{\"time\":\"13:00\",\"temp_c\":16.5,\"condition\":{\"text\":\"Cloudy\",\"icon\":\"/cloudy.png\"}}]"
        )

        val dayList = mutableStateOf(listOf(testData))
        val cday = mutableStateOf(testData)
        val searchHistory = listOf("Minsk", "Brest")
        var selectedCity = ""

        composeTestRule.setContent {
            TabLayout(
                dayl = dayList,
                cday = cday,
                searchHistory = searchHistory,
                onCitySelected = { city -> selectedCity = city }
            )
        }

        composeTestRule.onNodeWithText("HOURS").assertExists()
        composeTestRule.onNodeWithText("DAY").assertExists()
        composeTestRule.onNodeWithText("HISTORY").assertExists()

        composeTestRule.onNodeWithText("HISTORY").performClick()

        composeTestRule.onNodeWithText("Minsk").assertExists()
        composeTestRule.onNodeWithText("Brest").assertExists()

        composeTestRule.onNodeWithText("Brest").performClick()
        assert(selectedCity == "Brest")
    }
}