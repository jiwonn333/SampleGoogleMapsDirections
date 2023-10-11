# SampleGoogleMapsDirections
GoogleMapsDirection Example (간단히 사용해보기)

<img src = "https://github.com/jiwonn333/SampleGoogleMapsDirections/assets/84057628/a99b94b9-9df5-4b83-8f3a-afaa127c7277" width="30%" height="30%">

---
### Directions API 요청의 형식
```
https://maps.googleapis.com/maps/api/directions/outputFormat?parameters
```

### Retrofit을 사용하여 JSON 결과에서overview_polyline을 얻은 후 디코딩
```kotlin
val polyline = PolylineOptions()
              .addAll(PolyUtil.decode(shape))
              .width(8f)
              .color(Color.RED)
googleMap.addPolyline(polyline)

```

### Using Library
```build.gradle
// maps library
implementation 'com.google.android.gms:play-services-maps:18.1.0'
implementation 'com.google.maps.android:maps-utils-ktx:3.4.0'

// retrofit 2
implementation 'com.squareup.retrofit2:retrofit:2.9.0'
implementation 'com.squareup.retrofit2:converter-gson:2.9.0'

```


sample code : https://github.com/jiwonn333/SampleGoogleMapsDirections/tree/master/app/src/main
