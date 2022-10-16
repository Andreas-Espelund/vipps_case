package com.example.country_finder

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import com.example.country_finder.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private  lateinit var binding: ActivityMainBinding
    private var first = true
    private val viewModel: MainActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        //views
        val search = binding.searchBar
        val countryName = binding.countryName
        val capitalName = binding.capitalName
        val alternativeSpellings = binding.alternativeSpellings
        val button = binding.searchBtn

        button.setOnClickListener {
            val text = search.query
            if (text.isEmpty()){
                invalidInput(countryName, capitalName, alternativeSpellings, "Empty input")
            }
            else {
                viewModel.setCountry(text.toString())
            }
        }

        viewModel.getCountryLiveData().observe(this) { c ->
            if (c == null){
                invalidInput(countryName, capitalName, alternativeSpellings, "Country not found")
            } else {
                countryName.text = c.name
                "Capital: ${c.capital}".also {capitalName.text = it}
                "Alternative spellings: ${c.altSpellings.size}".also { alternativeSpellings.text = it }
            }
        }
    }

    private fun invalidInput(country: TextView, capital: TextView, spellings: TextView, message: String) {
        if (first) first = false
        else Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

        country.text = getString(R.string.error_message)
        capital.text = ""
        spellings.text = ""
    }




}