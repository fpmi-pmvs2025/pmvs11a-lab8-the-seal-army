package by.bsu.superweather

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import by.bsu.superweather.data.Data
import by.bsu.superweather.files.LItem
import by.bsu.superweather.files.Mlist
import by.bsu.superweather.files.dSearch
import org.junit.Rule
import org.junit.Test

class UITest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testLItemDisplaysCorrectData() {
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

        composeTestRule.setContent {
            LItem(moment = testData, cday = cday)
        }

        composeTestRule.onNodeWithText("12:00").assertExists()
        composeTestRule.onNodeWithText("Sunny").assertExists()
        composeTestRule.onNodeWithText("15.0").assertExists()
    }

    @Test
    fun testMlistDisplaysItems() {
        val testData1 = Data(
            city = "Minsk",
            time = "12:00",
            currTemp = "15.0",
            conditional = "Sunny",
            icon = "/sunny.png",
            maxT = "18.0",
            minT = "12.0",
            hours = ""
        )

        val testData2 = Data(
            city = "Brest",
            time = "13:00",
            currTemp = "16.0",
            conditional = "Cloudy",
            icon = "/cloudy.png",
            maxT = "19.0",
            minT = "13.0",
            hours = ""
        )

        val testList = listOf(testData1, testData2)
        val cday = mutableStateOf(testData1)

        composeTestRule.setContent {
            Mlist(arr = testList, cday = cday)
        }

        composeTestRule.onNodeWithText("12:00").assertExists()
        composeTestRule.onNodeWithText("Sunny").assertExists()
        composeTestRule.onNodeWithText("13:00").assertExists()
        composeTestRule.onNodeWithText("Cloudy").assertExists()
    }

    @Test
    fun testSearchDialogInteraction() {
        val dialogState = mutableStateOf(true)
        val searchHistory = mutableStateOf(listOf("Minsk", "Brest"))
        var submittedCity = ""

        composeTestRule.setContent {
            dSearch(
                dState = dialogState,
                onSubmit = { city -> submittedCity = city },
                searchHistory = searchHistory
            )
        }

        composeTestRule.onNodeWithText("Enter name of city:").assertExists()

        composeTestRule.onNodeWithTag("TextField").performTextInput("Vitebsk")

        composeTestRule.onNodeWithText("Ok").performClick()

        assert(!dialogState.value)
        assert(submittedCity == "Vitebsk")
    }
}