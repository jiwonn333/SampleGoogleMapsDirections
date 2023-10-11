package com.example.googlemapsdirections_sample

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.googlemapsdirections_sample.model.DirectionsResponse
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions
import com.google.maps.android.PolyUtil
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var googleMap: GoogleMap
    private lateinit var startLatLng: LatLng
    private lateinit var endLatLng: LatLng

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_maps)

        // Setting start/end address
        startLatLng = LatLng(35.581065044288174, 136.6762702161299) // start address
        endLatLng = LatLng(35.80177861620035, 136.7300097518705) // end address

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        this.googleMap = googleMap

        val startMarker = MarkerOptions()
            .position(startLatLng)
            .title("START")

        val endMarker = MarkerOptions()
            .position(endLatLng)
            .title("END")

        // Add start and end address markers
        this.googleMap.addMarker(startMarker)
        this.googleMap.addMarker(endMarker)
        this.googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(endLatLng, 11.6f))

        val fromStart = startLatLng.latitude.toString() + "," + startLatLng.longitude.toString() // lat, lng
        val toEnd = endLatLng.latitude.toString() + "," + endLatLng.longitude.toString() // lat, lng
        val region = "asia"

        val apiServices = RetrofitClient.apiServices(this)
        apiServices.getDirection(fromStart, toEnd, region, BuildConfig.MAPS_API_KEY)
            .enqueue(object : Callback<DirectionsResponse> {
                override fun onResponse(
                    call: Call<DirectionsResponse>,
                    response: Response<DirectionsResponse>
                ) {
                    drawPolyline(response)
                    Log.d("test", response.message())
                }

                override fun onFailure(call: Call<DirectionsResponse>, t: Throwable) {
                    Log.e("test", t.localizedMessage)
                }
            })

    }

    private fun drawPolyline(response: Response<DirectionsResponse>) {
        val shape = response.body()?.routes?.get(0)?.overviewPolyline?.points
        val polyline = PolylineOptions()
            .addAll(PolyUtil.decode(shape))
            .width(8f)
            .color(Color.RED)
        googleMap.addPolyline(polyline)

    }

    private interface ApiServices {
        @GET("maps/api/directions/json")
        fun getDirection(
            @Query("origin") origin: String,
            @Query("destination") destination: String,
            @Query("region") region: String,
            @Query("key") apiKey: String
        ): Call<DirectionsResponse>
    }

    private object RetrofitClient {
        fun apiServices(context: Context): ApiServices {
            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(context.resources.getString(R.string.base_url))
                .build()

            return retrofit.create(ApiServices::class.java)
        }
    }
}