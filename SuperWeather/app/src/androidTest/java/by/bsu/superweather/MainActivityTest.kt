package by.bsu.superweather

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import by.bsu.superweather.data.Data
import by.bsu.superweather.files.TabLayout
import by.bsu.superweather.files.dSearch
import by.bsu.superweather.files.mainCard
import by.bsu.superweather.ui.theme.SuperWeatherTheme
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.lang.reflect.Modifier

@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()


    @Test
    fun testTabSwitching() {
        composeTestRule.onNodeWithText("DAY").performClick()
        composeTestRule.waitForIdle()

        composeTestRule.onNodeWithText("HOURS").performClick()
        composeTestRule.waitForIdle()

        composeTestRule.onNodeWithText("HISTORY").performClick()
        composeTestRule.waitForIdle()
    }
}