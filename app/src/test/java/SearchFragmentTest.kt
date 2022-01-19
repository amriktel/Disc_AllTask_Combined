package com.example.kotlintv.search

import com.example.kotlintv.fragment.SearchFragment
import junit.framework.TestCase
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class SearchFragmentTest : TestCase() {

    @Mock
    private lateinit var searchFragment: SearchFragment

    @Before
    fun init() {
        MockitoAnnotations.initMocks(this)
    }
    override fun setUp() {
        super.setUp()
        searchFragment=Mockito.mock(SearchFragment::class.java)
    }
   @Test
    fun testOnQueryTextChange() {
        val textchanged=searchFragment.onQueryTextChange("Avatar")
       Assert.assertNotNull(textchanged)
    }

    @Test
    fun testOnQueryTextSubmit() {
        val testsubmitted=searchFragment.onQueryTextSubmit("Avatar")
        Assert.assertNotNull(testsubmitted)

    }

    @Test
    fun testStartVoiceRecognitionActivity() {
        val startVoiceSearch=searchFragment.startVoiceRecognitionActivity()
        Assert.assertNotNull(startVoiceSearch)

    }

}