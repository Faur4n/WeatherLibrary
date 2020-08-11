package com.example.getweather

import com.example.getweather.dto.Weather
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


private  lateinit var mListener : ResultListener

interface ResultListener{

    fun onResult(weather: Weather)
    fun onErrorOrEmpty(th: Throwable)

}

fun setOnResultListener(myInterface: ResultListener) {
    mListener = myInterface
}

fun getCurrentWeather(location: String){

        try {
            val api = ApiService()

            CoroutineScope(Dispatchers.IO).launch {
                var currentWeather : Weather? = null

                val currentLocation = api.getLocation(location = location)

                if(currentLocation.isNotEmpty()){
                    currentWeather = api.getWeather(currentLocation[0].woeid)
                    withContext(Dispatchers.Main){
                        mListener.onResult(currentWeather)
                    }
                }else{
                    withContext(Dispatchers.Main){
                        mListener.onErrorOrEmpty(Throwable("Can't get location"))
                    }
                }

            }
        }catch (th: Throwable){
            mListener.onErrorOrEmpty(th)

        }

}







