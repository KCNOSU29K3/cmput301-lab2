package com.eternal_blue.countryapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.eternal_blue.countryapp.ui.theme.CountryAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val repo = Repository()
        setContent {
            CountryAppTheme() {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    CityListScreen(
                        cities = repo.cities,
                        onAddCity = {repo.addCity(it) },
                        deleteCity = { repo.deleteCity(it) },
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun CityListScreen(
    cities:List<String>,
    onAddCity: (String) -> Unit,
    deleteCity: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var newCityName by remember { mutableStateOf("") }

    Column(modifier = modifier.fillMaxSize()) {
        Row(modifier = modifier.padding(all=16.dp)) {
            OutlinedTextField(
                value = newCityName,
                onValueChange = {newCityName = it },
                label = { Text("City Name") },
                modifier = Modifier.weight(1f)
            )

            Spacer(modifier = Modifier.width(8.dp))

            Button(
                onClick = {
                    if (newCityName.isNotBlank()){
                        onAddCity(newCityName)
                        newCityName = ""
                    }
                }
            ) { Text ("Add City") }
        }
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(cities) { city ->
                CityRow(city = city, deleteCity)
            }
        }
    }


}

@Composable
fun CityRow(city:String, deleteFunc:(String) -> Unit) {
//    var mod by remember { mutableIntStateOf(0) }
    Row(
        modifier = Modifier.fillMaxSize(),
    ) {
        Text(
            text = city,
            fontSize = 28.sp,
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 18.dp, vertical = 14.dp)
        )
        Button(
            onClick = {
                deleteFunc(city)
//                mod++
            }) {
                Text("X")
            }
    }
}

class Repository {
    private var _cities = mutableStateListOf(
        "Edmonton",
        "Moscow",
        "Vancouver",
        "Sydney",
        "Berlin",
        "Vienna",
        "Tokyo",
        "Beijing",
        "Night City",
        "New Delhi"
    )

    val cities: List<String>
            get() = _cities

    fun addCity(city:String) {
        _cities.add(city)
    }

    fun deleteCity(removedCity:String) {
        if (_cities.contains(removedCity)) {
            _cities.remove(removedCity)
        }
    }
}