package com.example.country_finder

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivityViewModel: ViewModel() {

    private val data = DataSource()
    private var selectedCountry = ""

    fun setCountry(name: String){
        selectedCountry = name
        loadCountries()
    }

    private val countryLiveData: MutableLiveData<CountriesItem?> by lazy {
        MutableLiveData<CountriesItem?>().also {
            loadCountries()
        }
    }

    fun getCountryLiveData() : LiveData<CountriesItem?>{
        return countryLiveData
    }

    private fun loadCountries() {
        viewModelScope.launch(Dispatchers.IO) {
            val country = data.fetchCountry(selectedCountry)
            countryLiveData.postValue(country)
        }
    }


}