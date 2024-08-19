package com.example.weatherapp.models

import java.io.Serializable


// Serializable convertito in byte_stream che può essere facilemente salvato a trasmesso attraverso
// la rete, utile quando si passa oggetti tra le varie componenti (fragment o activity)
// intent non passa oggetti! Con serializable si!
data class Coord(
    val lon: Double,
    val lat: Double
): Serializable
