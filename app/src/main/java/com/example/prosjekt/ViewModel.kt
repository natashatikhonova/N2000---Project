package com.example.prosjekt

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ViewModel: ViewModel() {
    private val dataSource = DataSource()

    var isLoading = MutableLiveData<Boolean>()

    private val fishingPlaces: MutableLiveData<ArrayList<Features>> by lazy{
        MutableLiveData<ArrayList<Features>>().also {
            loadFishingPlaces()
        }
    }

    private fun downloadFinished() {
        isLoading.value = true
    }

    //returning a list with fishing places
    fun getFishingPlaces(): LiveData<ArrayList<Features>>{
        return fishingPlaces
    }

    //loading fishing places from datasource via viewmodel
    private fun loadFishingPlaces(){
        viewModelScope.launch(Dispatchers.IO){
            dataSource.fetchFishingPlaces().also{
                fishingPlaces.postValue(it)
            }
        }
    }

    //returning info about weather at a given spot
    fun getWeather(lat: Double, long: Double): LiveData<List<Timesery>> {
        val weather: MutableLiveData<List<Timesery>> by lazy{
            MutableLiveData<List<Timesery>>().also {
                loadWeather(it, lat, long)
            }
        }
        return weather
    }

    //loading weather forecast from datasource via viewmodel
    private fun loadWeather(weather: MutableLiveData<List<Timesery>>, lat: Double, long: Double) {
        viewModelScope.launch(Dispatchers.IO){
            dataSource.fetchWeather(lat, long).also{
                weather.postValue(it)
            }
        }
    }

    //returning info about weather at a given spot
    fun getWater(lat: Double, long: Double): LiveData<Double> {
        val water: MutableLiveData<Double> by lazy{
            MutableLiveData<Double>().also {
                loadWater(it, lat, long)
            }
        }
        return water
    }

    //loading weather forecast from datasource via viewmodel
    private fun loadWater(water: MutableLiveData<Double>, lat: Double, long: Double) {
        viewModelScope.launch(Dispatchers.IO){
            dataSource.fetchWater(lat, long).also{
                water.postValue(it)
            }
        }
    }

    //returning info about weather at a given spot
    fun getAstroData(lat: Double, long: Double): LiveData<ArrayList<Time>> {
        val astrodata: MutableLiveData<ArrayList<Time>> by lazy{
            MutableLiveData<ArrayList<Time>>().also {
                loadAstrodata(it, lat, long)
            }
        }
        return astrodata
    }

    //loading weather forecast from datasource via viewmodel
    private fun loadAstrodata(astrodata: MutableLiveData<ArrayList<Time>>, lat: Double, long: Double) {
        viewModelScope.launch(Dispatchers.IO){
            dataSource.fetchSunriseData(lat, long).also{
                astrodata.postValue(it)
            }
        }
    }


}