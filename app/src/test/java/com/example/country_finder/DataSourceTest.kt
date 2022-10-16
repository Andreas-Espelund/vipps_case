package com.example.country_finder

import io.ktor.util.reflect.*
import junit.framework.Assert.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test


@ExperimentalCoroutinesApi
class DatasourceTest {
    private val datasource = DataSource()
    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `fetch data is not null`() = runTest {
        val res = datasource.fetchCountry("norway")
        assertNotNull(res)
    }

    @Test
    fun `fetch data is correct return type`() = runTest {
        val res = datasource.fetchCountry("norway") as CountriesItem
        assert(res.instanceOf(CountriesItem::class))
    }

    @Test
    fun `fetch data invalid country`() = runTest {
        val res = datasource.fetchCountry("narnia")
        assertNull(res)
    }

}