package com.example.googlemapsdirections_sample.model

import com.google.gson.annotations.SerializedName

data class Polyline(
    @SerializedName("points")
    var points: String?
)