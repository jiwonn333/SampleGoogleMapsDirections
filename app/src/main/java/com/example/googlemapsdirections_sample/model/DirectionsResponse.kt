/*
 * Created by Muhammad Utsman on 31/12/2018
 * Copyright (c) 2018 . All rights reserved.
 * Last modified 12/31/18 11:21 PM
 */

package com.example.googlemapsdirections_sample.model

import com.google.gson.annotations.SerializedName

data class DirectionsResponse(
    @SerializedName("geocoded_waypoints")
    var geocodedWaypoints: List<GeocodedWaypoint?>?,
    @SerializedName("routes")
    var routes: List<Route>?,
    @SerializedName("status")
    var status: String?
)