package com.example.country_finder


import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Test
import org.junit.Assert.*
import org.junit.Before

@ExperimentalCoroutinesApi
class MainActivityViewModelTest{
    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setUp() = Dispatchers.setMain(testDispatcher)

    @After
    fun tearDown() = Dispatchers.resetMain()

    @Test
    fun `countryLiveData is null before fetching`() {
        val viewModel = MainActivityViewModel()
        val result = viewModel.getCountryLiveData().value
        assertNull(result)
    }

    @Test
    fun `test getCountryLiveData is not null`() = runTest {
        val viewModel = MainActivityViewModel()
        suspend {
            viewModel.setCountry("norway")
            val result = viewModel.getCountryLiveData().value
            assertNotNull(result)
        }
    }

    @Test
    fun `test country name`() = runTest {
        val viewModel = MainActivityViewModel()
        suspend {
            viewModel.setCountry("norway")
            val result = viewModel.getCountryLiveData().value?.name
            assertEquals("norway", result)
        }
    }

    @Test
    fun `test capital name`() = runTest {
        val viewModel = MainActivityViewModel()
        suspend {
            viewModel.setCountry("norway")
            val result = viewModel.getCountryLiveData().value?.capital
            assertEquals("oslo", result)
        }
    }

    @Test
    fun `test alternative spellings`() = runTest {
        val viewModel = MainActivityViewModel()
        suspend {
            viewModel.setCountry("norway")
            val result = viewModel.getCountryLiveData().value?.altSpellings?.size
            assertEquals(6, result)
        }
    }
}