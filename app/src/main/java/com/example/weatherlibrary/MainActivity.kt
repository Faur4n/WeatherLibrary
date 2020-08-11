package com.example.weatherlibrary

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.getweather.ResultListener
import com.example.getweather.dto.Weather
import com.example.getweather.getCurrentWeather
import com.example.getweather.setOnResultListener
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        resultButton.setOnClickListener {
            val text = locationEditText.text
            if (text.isNotEmpty()){
                getCurrentWeather(text.toString())
                progressBar.visibility = View.VISIBLE
            }
        }

        setOnResultListener(object : ResultListener{
            override fun onResult(weather: Weather) {
                showAlertDialog(weather)
                progressBar.visibility = View.GONE
            }

            override fun onErrorOrEmpty(th: Throwable) {
                progressBar.visibility = View.GONE
                Toast.makeText(this@MainActivity, "ERROR ${th.message}", Toast.LENGTH_SHORT).show()
            }
        })


    }

    // Метод отображает полученные данные в alertDialog
    private fun showAlertDialog(weather: Weather){

        val temp = weather.consolidatedWeather[0].theTemp
        val humidity = weather.consolidatedWeather[0].humidity.toString()
        val windSpeed = weather.consolidatedWeather[0].windSpeed
        val title=  weather.title
        val iconAbr = weather.consolidatedWeather[0].weatherStateAbbr
        val visibility =weather.consolidatedWeather[0].visibility
        val windDirection =weather.consolidatedWeather[0].windDirectionCompass

        AlertDialog.Builder(this)

                .setTitle(title)
                .setMessage(resources.getString(
                        R.string.temperature,
                        temp,
                        humidity,
                        windSpeed,
                        visibility,
                        windDirection)
                )
                .setIcon(
                        findIcon(iconAbr)
                )
                .setPositiveButton("OK") {
                    dialog, _ ->
                        dialog?.dismiss()
                }
                .setCancelable(false)
                .show()

    }

    private fun findIcon(iconAbr: String): Int {
        return when(iconAbr){
            "c" -> R.drawable.c
            "h" -> R.drawable.h
            "hc" -> R.drawable.hc
            "hr" -> R.drawable.hr
            "lc" -> R.drawable.lc
            "lr" -> R.drawable.lr
            "s" -> R.drawable.s
            "sl" -> R.drawable.sl
            "sn" -> R.drawable.sn
            "t" -> R.drawable.t
            else -> 1
        }
    }


}