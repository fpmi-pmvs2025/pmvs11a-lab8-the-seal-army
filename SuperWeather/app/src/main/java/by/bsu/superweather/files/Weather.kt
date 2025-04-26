package by.bsu.superweather.files

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement.SpaceBetween
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import com.google.accompanist.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Tab
import androidx.compose.material.TabRow
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TabRowDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import by.bsu.superweather.R
import by.bsu.superweather.data.Data
import by.bsu.superweather.ui.theme.Pink40
import by.bsu.superweather.ui.theme.Pink80
import coil.compose.AsyncImage
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.pagerTabIndicatorOffset
import kotlinx.coroutines.launch
import com.google.accompanist.pager.HorizontalPager
import org.json.JSONArray
import org.json.JSONObject


@Composable

fun mainCard(cday:MutableState<Data>, onClickSync:()->Unit, onClickSearch:()-> Unit) {

    Column(modifier = Modifier.padding(7.dp)) {

        Card(
            modifier = Modifier.fillMaxWidth().alpha(0.68f),
            colors = CardDefaults.cardColors(
                containerColor = Pink40,
            ),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 10.dp
            ),
            shape = RoundedCornerShape(12.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = SpaceBetween) {
                        Text(
                            modifier = Modifier.padding(top = 10.dp, start = 7.dp),
                            text = cday.value.time,
                            style = TextStyle(fontSize = 15.sp),
                            color = White

                        )

                        AsyncImage(
                            model = if(cday.value.time in "00:00".."12:00")
                                "https://cdn.weatherapi.com/weather/64x64/night/113.png"
                            else "https://cdn.weatherapi.com/weather/64x64/day/119.png" ,
                            contentDescription = "Vay",
                            modifier = Modifier.padding(top = 1.dp, start = 1.dp).size(40.dp)

                        )

                    }
                    Text(text=cday.value.city,
                        style = TextStyle(fontSize = 30.sp),
                        color = White

                    )
                    Text(text=if(cday.value.currTemp.isNotEmpty())
                        "${cday.value.currTemp.toFloat().toInt()} ℃"
                    else "${cday.value.maxT.toFloat().toInt()}℃/${cday.value.minT.toFloat().toInt()}℃" ,
                        style = TextStyle(fontSize = 50.sp),
                        color = White

                    )
                    Text(text=cday.value.conditional,
                        style = TextStyle(fontSize = 20.sp),
                        color = White

                    )
                    Row(modifier = Modifier.fillMaxWidth(),horizontalArrangement = SpaceBetween){
                        IconButton(onClick = {
                            onClickSearch.invoke()

                        }, modifier = Modifier.testTag("SearchButton"))
                        {
                            Icon(painter = painterResource(id = R.drawable.search),contentDescription= "sky",tint = White)
                        }
                        Text(modifier = Modifier.padding(top = 9.dp, start = 3.dp),
                            text="${cday.value.maxT.toFloat().toInt()}℃/${cday.value.minT.toFloat().toInt()}℃",
                            style = TextStyle(fontSize = 20.sp),
                            color = White

                        )
                        IconButton(onClick = {
                            onClickSync.invoke()

                        }){
                            Icon(painter = painterResource(id = R.drawable.sync),contentDescription= "sky",tint = White)
                        }
                    }

                }
            }
        }
    }
}
@Composable
fun SearchHistoryList(
    searchHistory: List<String>,
    onCitySelected: (String) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()

    ) {
        items(searchHistory) { city ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onCitySelected(city) }
                    .padding(4.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .background(Color(0xFFFFC0CB))
                    .alpha(0.8f)
            ) {
                Text(
                    text = city,
                    color = Color.White,
                    modifier = Modifier
                        .padding(16.dp) // Add padding for text
                        .weight(1f)
                )
            }
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun TabLayout(dayl: MutableState<List<Data>>, cday: MutableState<Data>, searchHistory: List<String>, onCitySelected: (String) -> Unit) {
    val tabL = listOf("HOURS", "DAY", "HISTORY")
    val ps = rememberPagerState()
    val tabI = ps.currentPage
    val cs = rememberCoroutineScope()

    Row(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier
            .weight(1f)
            .padding(4.dp)
            .clip(RoundedCornerShape(4.dp))) {

            TabRow(
                selectedTabIndex = tabI,
                indicator = { pos -> TabRowDefaults.Indicator(modifier = Modifier.pagerTabIndicatorOffset(ps, pos)) },
                backgroundColor = Color(0xFFFFC0CB),
                contentColor = White,
                modifier = Modifier.alpha(0.8f)
            ) {
                tabL.forEachIndexed { index, text ->
                    Tab(
                        selected = tabI == index,
                        onClick = {
                            cs.launch {
                                ps.animateScrollToPage(index)
                            }
                        },
                        text = { Text(text = text, color = White) }
                    )
                }
            }

            HorizontalPager(count = tabL.size, state = ps, modifier = Modifier.weight(1.0f)) { index ->
                when (index) {
                    0 -> Mlist(byHours(cday.value.hours), cday) // Assuming byHours returns List<Data>
                    1 -> Mlist(dayl.value, cday) // Assuming dayl is List<Data>
                    2 -> {
                        Column(modifier = Modifier.fillMaxSize()) {
                            SearchHistoryList(searchHistory) { selectedCity ->
                                onCitySelected(selectedCity)
                            }
                        }
                    }
                }
            }
        }
    }
}

fun byHours(hours:String):List<Data>{
    if(hours.isEmpty())
        return listOf()
    val arrh = JSONArray(hours)
    val arr = ArrayList<Data>()
    for(i in 0 until arrh.length()){
        val it = arrh[i] as JSONObject
        arr.add(
            Data("",it.getString("time"),it.getString("temp_c").toFloat().toInt().toString()+"℃",
                it.getJSONObject("condition").getString("text"),
                it.getJSONObject("condition").getString("icon"),
                "","","")
        )
    }
    return arr
}