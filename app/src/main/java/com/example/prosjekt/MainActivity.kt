package com.example.prosjekt

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.util.*


class MainActivity : AppCompatActivity() {
    private val viewModel:ViewModel by viewModels()
    private lateinit var  homeFragment: HomeFragment
    private lateinit var  statsFragment: StatsFragment
    private lateinit var  mapsFragment: MapsFragment
    private lateinit var schemeFragment: UserFragment
    private lateinit var card: LocationFragment
    private lateinit var list: ArrayList<FishingPlace>
    private lateinit var favorites: ArrayList<FishingPlace>
    private lateinit var catches: ArrayList<Catch>
    private lateinit var nameArray: ArrayList<String>
    private lateinit var navBar: BottomNavigationView
    private lateinit var progressBar: ProgressBar
    var image: String? = null
    private lateinit var testObject:WeatherObject
    private lateinit var testObject2:SunriseObject
    private lateinit var type: String
    private lateinit var weight: String
    private lateinit var length: String
    private lateinit var date: String
    private lateinit var manefase: String
    private lateinit var soloppgang: String
    private lateinit var solnedgang: String
    private lateinit var waterTemp: String
    private lateinit var tinydb: TinyDB
    //private lateinit var catchesDB: TinyDB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        navBar = findViewById(R.id.bottomNavigationView)
        progressBar = findViewById(R.id.progressBar)
        favorites = ArrayList()
        catches = ArrayList()
        tinydb = TinyDB(applicationContext)
        //catchesDB= TinyDB(applicationContext)
        //load fishing places
        list = ArrayList()
        nameArray = ArrayList()
        card = LocationFragment("home")
        manefase=""
        soloppgang=""
        solnedgang=""
        waterTemp= ""
        loadFishes()
    }
    

    fun hideProgressBar(){
        progressBar.visibility = View.GONE
    }

    fun showNavBar(){
        navBar.visibility = View.VISIBLE
    }

    fun hideNavBar(){
        navBar.visibility = View.GONE
    }


    //on load
    fun setCurrentFragment(fragment: Fragment)=
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flFragment,fragment)
            commit()
        }

    private fun loadFishes(){
        hideNavBar()
        viewModel.getFishingPlaces().observe(this){ places->
            println("VIEWMODEL" + places.size.toString())
            var counter =1
            for(feature: Features in places){
                var place = FishingPlace(feature.attributes?.stedsnavn!!, feature.geometry?.rings?.get(0)?.get(0)?.get(1), feature.geometry?.rings?.get(0)?.get(0)?.get(0)!!)
                list.add(place)
                nameArray.add(feature.attributes?.stedsnavn!!)
            }
            findFavorites()
            findCatches()
            setUp()
        }
    }

    fun getPlaces(): ArrayList<FishingPlace>{
        return list
    }

    fun checkPermission(permission: String, requestCode: Int){
         if(ContextCompat.checkSelfPermission(this@MainActivity, permission)==PackageManager.PERMISSION_DENIED) {
             ActivityCompat.requestPermissions(this@MainActivity, arrayOf(permission), requestCode)

         } else{
             //Toast.makeText(this@MainActivity, "Tillatelse er gitt", Toast.LENGTH_SHORT).show()
             val intent = Intent(Intent.ACTION_PICK)
             intent.type = "image/*"
             startActivityForResult(intent, 100)
         }
    }
    override fun onRequestPermissionsResult(requestCode: Int,
                                             permissions: Array<String>,
                                             grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 101) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this@MainActivity, "Storage Permission Granted", Toast.LENGTH_SHORT).show()
                val intent = Intent(Intent.ACTION_PICK)
                intent.type = "image/*"
                startActivityForResult(intent, 100)
            } else {
                //Toast.makeText(this@MainActivity, "Storage Permission Denied", Toast.LENGTH_SHORT).show()
                checkPermission(
                    Manifest.permission.READ_EXTERNAL_STORAGE, 101
                )
            }
        }
    }

    fun getSunrise(place: FishingPlace){

        viewModel.getAstroData(place.north, place.east).observe(this){
            val moonPhase = it.get(0).moonphase?.value
            val zero = 0
            val quater = 25
            val half = 50
            val almost = 75
            val whole = 100
            var moonphase = ""
            if (moonPhase != null) {
                if ((moonPhase < zero.toString()) || (moonPhase > quater.toString())) {
                    moonphase = "Voksende månesigd"
                } else if ((quater.toString() < moonPhase) || (moonPhase > half.toString())) {
                    moonphase = "Voksende måne"
                } else if ((half.toString() < moonPhase) || (moonPhase > almost.toString())) {
                    moonphase = "Minkende måne"
                } else if ((almost.toString() < moonPhase) || (moonPhase > whole.toString())) {
                    moonphase = "Minkende månesigd"
                }
            }

            setSunrise(moonphase,getSunrise(it)!!,getSunset(it)!!)
        }
    }

    fun getCurrentWeather(place:FishingPlace) {
        var temp: Double = 0.0
        //var windInfo = ""
        //var moonphase = ""
        //var sunrise = ""
        //var sunset = ""
        testObject = WeatherObject(temp, windInfo)
        //testObject2 = SunriseObject(moonphase, sunrise, sunset)


        //LocationForecast - wind, temperature

        viewModel.getWeather(place.north, place.east).observe(this){
            println("TEST TEMP: "+it[0].data.instant.details.air_temperature.toString())
            temp= it[0].data.instant.details.air_temperature
            println("TEMP: "+temp.toString())
            //place.setTemperature(temp)
            var wind = it?.get(0)?.data?.instant?.details?.wind_from_direction
            var windSpeed =  it?.get(0)?.data?.instant?.details?.wind_speed
            if (wind != null) {
                if ((wind < 45.0) || (wind > 315)) {
                    windInfo = "Nordvind: $windSpeed m/s"
                } else if ((45 < wind) || (wind > 135)) {
                    windInfo = "Østvind: $windSpeed m/s"
                } else if ((135 < wind) || (wind > 225)) {
                    windInfo = "Sørvind: $windSpeed m/s"
                } else if ((225 < wind) || (wind > 315)) {
                    windInfo = "Vestvind: $windSpeed m/s"
                }
            }
            //testObject.temperature = temp
            //testObject.wind = windInfo


        }

        //Sunrise - sunsise, sunset, moonphase

        /*viewModel.getAstroData(place.north, place.east).observe(this){
            val moonPhase = it.get(0).moonphase?.value
            val zero = 0
            val quater = 25
            val half = 50
            val almost = 75
            val whole = 100
            if (moonPhase != null) {
                if ((moonPhase!! < zero.toString()) || (moonPhase > quater.toString())) {
                    moonphase = "Voksende månesigd"
                } else if ((quater.toString() < moonPhase) || (moonPhase > half.toString())) {
                    moonphase = "Voksende måne"
                } else if ((half.toString() < moonPhase) || (moonPhase > almost.toString())) {
                    moonphase = "Minkende måne"
                } else if ((almost.toString() < moonPhase) || (moonPhase > whole.toString())) {
                    moonphase = "Minkende månesigd"
                }
            }

            //sunrise

            var sunriseInfo = it?.get(0)?.sunrise?.time

            //"2022-04-05T05:29:41+01:00"

            val riseTime1 = sunriseInfo?.split("T")?.toTypedArray()
            val riseTime2 = riseTime1?.get(1)?.split("+")?.toTypedArray()
            val riseTime = riseTime2?.get(0)?.split(":")?.toTypedArray()

            sunrise = riseTime?.get(0)+":"+riseTime?.get(1)

            //sunset

            var sunsetInfo = it?.get(0)?.sunset?.time

            //"2022-04-05T19:09:18+01:00"

            val setTime1 = sunsetInfo?.split("T")?.toTypedArray()
            val setTime2 = setTime1?.get(1)?.split("+")?.toTypedArray()
            val setTime = setTime2?.get(0)?.split(":")?.toTypedArray()

            sunset = setTime?.get(0)+":"+setTime?.get(1)

            testObject2.moon = moonphase
            testObject2.sunrise = sunrise
            testObject2.sunset = sunset


            //args.putParcelable("place", place)
            args.putParcelable("sunrise", testObject)
            //args.putParcelable("sunrise", sunriseObject)
            card.arguments = args
            setCurrentFragment(card)

        }*/



        //println("TESTING LOCATION: " + place.north.toString()+" , "+place.east.toString())
        //println("TESTER UTENFOR: "+temp.toString())
        var args = Bundle()
        var weatherObject = WeatherObject(temp, windInfo)

        var sunriseObject = SunriseObject(soloppgang, solnedgang, manefase)



    }

    fun getWater(place: FishingPlace){
        viewModel.getWater(place.north, place.east).observe(this){
            if (it != null){
                setWater(it)
            } else{
                setWater(0.0)
            }
        }
    }

    fun setSunrise(dataMoon: String, dataRise: String, dataSet: String){
        manefase = dataMoon
        soloppgang = dataRise
        solnedgang = dataSet
    }

    fun setWater(temp: Double){
        waterTemp = temp.toString()
    }

    fun getSunrise(info: ArrayList<Time>?): String?{
        var sunrise = info?.get(0)?.sunrise?.time

        //"2022-04-05T05:29:41+01:00"

        val riseTime1 = sunrise?.split("T")?.toTypedArray()
        val riseTime2 = riseTime1?.get(1)?.split("+")?.toTypedArray()
        val riseTime = riseTime2?.get(0)?.split(":")?.toTypedArray()

        return riseTime?.get(0)+":"+riseTime?.get(1)

    }

    fun getSunset(info: ArrayList<Time>?) :String? {
        var sunset = info?.get(0)?.sunset?.time

        //"2022-04-05T19:09:18+01:00"

        val setTime1 = sunset?.split("T")?.toTypedArray()
        val setTime2 = setTime1?.get(1)?.split("+")?.toTypedArray()
        val setTime = setTime2?.get(0)?.split(":")?.toTypedArray()

        return setTime?.get(0)+":"+setTime?.get(1)
    }

    fun getMoonphase(info: ArrayList<Time>?) :String {
        var moonPhase : Double? = null

        if (info != null) {
            for (elements in info) {
                moonPhase = elements.moonphase?.value?.toDouble()
            }
        }

        if (moonPhase != null) {
            if ((moonPhase < 0) || (moonPhase > 25)) {
                return "Voksende månesigd"
            } else if ((25 < moonPhase) || (moonPhase > 50)) {
                return "Voksende måne"
            } else if ((50 < moonPhase) || (moonPhase > 75)) {
                return "Minkende måne"
            } else if ((75 < moonPhase) || (moonPhase > 100)) {
                return "Minkende månesigd"
            }
        }

        return "no data"
    }

    fun getNames(): ArrayList<String>{
        return nameArray
    }


    fun findFavorites(){
        for (i in tinydb.all){
            for (place: FishingPlace in list){
                if(place.name == i.key){
                    place.makeFavorite()
                    favorites.add(place)
                }
            }
        }
    }

    fun findCatches(){
        for (i in tinydb.all){
            if (i.key.startsWith("date:")){
                val obj = i.value.toString().split(",")
                var date:String? = null
                var weight:String? = null
                var length:String? = null
                var type:String? = null
                var image:String? = null
                for (j in obj){

                    if (j!=null) {
                        if (j.startsWith("{\"date\"")) {
                            date = j.split(":")[1].removePrefix("\"").removeSuffix("\"")

                        } else if (j.startsWith("\"length\"")) {

                            length = j.split(":")[1].removePrefix("\"").removeSuffix("\"")

                        } else if (j.startsWith("\"weight\"")) {
                            weight = j.split(":")[1].removePrefix("\"").removeSuffix("\"}")


                        } else if (j.startsWith("\"type\"")) {
                            type = j.split(":")[1].removePrefix("\"").removeSuffix("\"")


                        } else if (j.startsWith("\"image\"")) {
                            image = j.removePrefix("\"image\":\"").removeSuffix("\"")
                            println("testing image in find: ")
                            println(image)
                        }
                    }
                }
                var catch = Catch(image, type, weight, length, date)
                catches.add(catch)

            }
        }
    }



    fun addToFavorites(place: FishingPlace){
        place.makeFavorite()
        favorites.add(place)
        tinydb.putObject(place.name, place)
    }

    fun removeFromFavorites(place:FishingPlace){
        place.unmakeFavorite()
        favorites.remove(place)
        tinydb.remove(place.name)

    }

    fun getFavorites():ArrayList<FishingPlace>{
        return favorites
    }

    fun getCatches():ArrayList<Catch>{
        return catches
    }

    fun setUp(){
        //navigation bar
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        //fragments
        hideProgressBar()
        showNavBar()
        homeFragment = HomeFragment()
        statsFragment = StatsFragment()
        mapsFragment = MapsFragment()
        schemeFragment = UserFragment()

        //on load
        setCurrentFragment(homeFragment)

        //if icon is pressed
        bottomNavigationView.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.home->setCurrentFragment(homeFragment)
                R.id.stats->setCurrentFragment(statsFragment)
                R.id.maps->setCurrentFragment(mapsFragment)
            }
            true
        }
    }

    fun getHome():HomeFragment{
        return homeFragment
    }

    fun getMaps():MapsFragment{
        return mapsFragment
    }

    fun getScheme():UserFragment{
        return schemeFragment
    }

    fun getStats():StatsFragment{
        return statsFragment
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == 100){
            // imageView.setImageURI(data?.data) // handle chosen image
            image = data?.data.toString()
            println("TEST IMAGE CREATE: ")
            println(image)
        }
    }

    fun createCatch( type: String, weight: String, length: String, date: String){
        this.type = type
        this.weight = weight
        this.length = length
        this.date = date
        //println("TYPE: "+this.type)
        //println("DATE: "+this.date)
        val obj = Catch(image, this.type, this.weight, this.length, this.date)
        catches.add(obj)
        tinydb.putCatch("date:"+date, obj)
    }

    fun removeCatch(catch: Catch){
        catches.remove(catch)
        tinydb.remove("date:"+catch.date)
    }
}

