package by.bsu.superweather.files

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.Card
import androidx.compose.material.TextField
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.SemanticsActions.OnClick
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import by.bsu.superweather.data.Data
import by.bsu.superweather.ui.theme.Pink80
import coil.compose.AsyncImage

@Composable
fun LItem(moment: Data, cday:MutableState<Data>) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 3.dp).alpha(0.9f).clickable{
                if(moment.hours.isEmpty()) return@clickable
                cday.value = moment }, backgroundColor = Pink80,
        elevation = 1.dp, shape = RoundedCornerShape(6.dp)
    ) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically) {
            Column(modifier = Modifier.padding(start = 7.dp, top = 10.dp, bottom = 4.dp)) {
                Text(
                    text = moment.time,
                    style = TextStyle(fontSize = 15.sp),
                    color = Color.White

                )
                Text(
                    text = moment.conditional,
                    style = TextStyle(fontSize = 15.sp),
                    color = Color.White

                )
            }
            Text(
                text = moment.currTemp.ifEmpty{"${moment.maxT}/${moment.minT}"},
                style = TextStyle(fontSize = 15.sp),
                color = Color.White
            )
            AsyncImage(
                model = "https:${moment.icon}",
                contentDescription = "Vay2",
                modifier = Modifier.padding( end = 6.dp).size(40.dp)

            )
        }
    }
}
@Composable
fun Mlist(arr: List<Data>, cday:MutableState<Data>) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        itemsIndexed(
            arr
        ) { _, item ->
            LItem(item,cday)

        }
    }
}

@Composable
fun dSearch(dState: MutableState<Boolean>, onSubmit: (String) -> Unit, searchHistory: MutableState<List<String>>) {
    val dText = remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = {
            dState.value = false
        },
        confirmButton = {
            TextButton(onClick = {
                val city = dText.value.trim()
                if (city.isNotEmpty()) {
                    // Submit the city and update the search history
                    onSubmit(city)
                    // Update search history
//                    if (city !in searchHistory.value) {
//                        Log.d("SearchHistoryFromDSearch", "Submitting city: $city")
//                        searchHistory.value += city
//                    }
                }
                dState.value = false
            },
                modifier = Modifier.testTag("OkButton"))
            {
                Text(text = "Ok")
            }
        },
        dismissButton = {
            TextButton(onClick = {
                dState.value = false
            }) {
                Text(text = "Cancel")
            }
        },
        title = {
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(text = "Enter name of city:")
                TextField(
                    value = dText.value,
                    onValueChange = {
                        dText.value = it
                    },
                    modifier = Modifier.testTag("TextField")
                )
            }
        }
    )
}