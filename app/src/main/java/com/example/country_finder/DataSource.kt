package com.example.country_finder

import io.ktor.client.*
import io.ktor.client.features.json.*
import io.ktor.client.request.*
import java.lang.Exception

class DataSource {
    private var cache = HashMap<String, CountriesItem>()
    private val basePath = "https://restcountries.com/v2/name/"
    private val jsonClient = HttpClient{
        install(JsonFeature)
    }

    suspend fun fetchCountry(path: String):CountriesItem?{
        val fullPath = basePath + path
        var item = cache[fullPath]
        if (item == null)
            try {
                item = jsonClient.get<Countries>(fullPath)[0]
                cache[fullPath] = item
            } catch (e: Exception){
                return null
            }

        return item
    }
}
