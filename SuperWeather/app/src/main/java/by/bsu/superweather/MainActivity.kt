package by.bsu.superweather

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import by.bsu.superweather.data.CityDatabaseHelper
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import by.bsu.superweather.data.Data
import by.bsu.superweather.files.TabLayout
import by.bsu.superweather.files.dSearch
import by.bsu.superweather.files.mainCard
import by.bsu.superweather.ui.theme.SuperWeatherTheme
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONObject

const val api = "90916526d26c4ab8a2e201046232911"

class MainActivity : ComponentActivity() {
    private lateinit var databaseHelper: CityDatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        databaseHelper = CityDatabaseHelper(this)
        setContent {
            val dayl = remember { mutableStateOf(listOf<Data>()) }
            val cday = remember { mutableStateOf(Data("", "", "0.0", "", "", "0.0", "0.0", "")) }
            val dState = remember { mutableStateOf(false) }
            val searchHistory = remember { mutableStateOf(getSearchHistory()) }

            if (dState.value) {
                dSearch(
                    dState,
                    onSubmit = { cityName ->
                        CoroutineScope(Dispatchers.IO).launch {
                            if (isNetworkAvailable(this@MainActivity)) {
                                if (!searchHistory.value.contains(cityName)) {
                                    searchHistory.value += cityName
                                    saveCityToHistory(cityName)
                                }
                                // Запрос данных о городе
                                gData(cityName, this@MainActivity, dayl, cday)
                            } else {
                                withContext(Dispatchers.Main) {
                                    Toast.makeText(this@MainActivity, "Нет интернет-соединения. Запуск в автономном режиме.", Toast.LENGTH_LONG).show()
                                }
                            }
                        }
                    },
                    searchHistory = searchHistory
                )
            }

            // Default data fetch
            gData("Minsk", this, dayl, cday)

            SuperWeatherTheme {
                Image(
                    painter = painterResource(id = R.drawable.skyy),
                    contentDescription = "sky",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.FillBounds
                )
                Column {
                    mainCard(
                        cday,
                        onClickSync = { gData("Minsk", this@MainActivity, dayl, cday) },
                        onClickSearch = { dState.value = true },

                    )

                    // Pass the search history and onCitySelected function
                    TabLayout(
                        dayl = dayl,
                        cday = cday,
                        searchHistory = searchHistory.value,
                        onCitySelected = { selectedCity ->
                            gData(selectedCity, this@MainActivity, dayl, cday)
                        }
                    )
                }
            }
        }
    }

    private fun getSearchHistory(): List<String> {
        return databaseHelper.getAllCities() // Retrieve the search history from the database
    }

    private fun saveCityToHistory(cityName: String) {
        databaseHelper.insertCity(cityName) // Save the city name to the database
    }
}
private suspend fun isNetworkAvailable(context: Context): Boolean {
    return withContext(Dispatchers.IO) {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkCapabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        networkCapabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) == true
    }
}
private fun gData(town:String,context: Context,dayl:MutableState<List<Data>>,cday:MutableState<Data>){
    val url = "https://api.weatherapi.com/v1/forecast.json?key=$api&q=$town&days=30" +
            "&aqi=no&alerts=no"

    val q = Volley.newRequestQueue(context)
    val sr = StringRequest(
        Request.Method.GET, url,
        { response ->
            val arr = byDay(response)
            cday.value = arr[0]
            dayl.value = arr

        },
        { error ->
            Log.e("Error", error.toString())
        }
    )
    q.add(sr)
}
//@SuppressLint("SuspiciousIndentation")
@SuppressLint("SuspiciousIndentation")
private fun byDay(response:String):List<Data>{
    if(response.isEmpty()){
        return listOf()
    }
    val obj = JSONObject(response)
    val arr = ArrayList<Data>()
    val city = obj.getJSONObject("location").getString("name")
    val days = obj.getJSONObject("forecast").getJSONArray("forecastday")
    for(i in 0 until days.length()){
        val it = days[i] as JSONObject
        arr.add(Data(city,it.getString("date"),"",it.getJSONObject("day").getJSONObject("condition")
            .getString("text"),it.getJSONObject("day").getJSONObject("condition")
            .getString("icon"),it.getJSONObject("day")
            .getString("maxtemp_c"),it.getJSONObject("day")
            .getString("mintemp_c"),it.getJSONArray("hour").toString()))
    }
    arr[0]=arr[0].copy(time=obj.getJSONObject("current").getString("last_updated"),
        currTemp = obj.getJSONObject("current").getString("temp_c"))
    return arr
}