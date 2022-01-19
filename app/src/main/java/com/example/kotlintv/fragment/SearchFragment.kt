package com.example.kotlintv.fragment

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.speech.RecognizerIntent
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import androidx.leanback.app.SearchSupportFragment
import androidx.leanback.widget.*
import com.example.example.SearchMovieResult
import com.example.kotlintv.R
import com.example.kotlintv.presenter.SearchPortraitPresenter
import com.example.kotlintv.retrofit.ApiEndpoints
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class SearchFragment : SearchSupportFragment(), SearchSupportFragment.SearchResultProvider {
    private val TAG = "SearchFragment"
    private lateinit var arrayObjectAdapter: ArrayObjectAdapter
    private lateinit var mQuery: String
    private val handler: Handler = Handler()
    private val BASE_URL = "http://api.themoviedb.org"
    private val PAGE = 1
    private val LANGUAGE = "en-US"
    private val API_KEY = "fee68e44aa6c118c8eee47f3b9cf04fa"
    private lateinit var service: ApiEndpoints
    private var mResultsFound = false
    private lateinit var editText: EditText


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arrayObjectAdapter = ArrayObjectAdapter(ListRowPresenter())
        title = "Movies"
        setSearchResultProvider(this)
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        service = retrofit.create<ApiEndpoints>(ApiEndpoints::class.java)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        editText = requireView().findViewById<EditText>(R.id.lb_search_text_editor)
        requireView().findViewById<View>(R.id.lb_search_bar_speech_orb).setOnClickListener {
            startVoiceRecognitionActivity()
        }

    }

    override fun getResultsAdapter(): ObjectAdapter {
        Log.d("Search", "getResultsAdapter: " + arrayObjectAdapter?.size())
        return arrayObjectAdapter
    }

    fun hasResults(): Boolean {
        return arrayObjectAdapter.size() > 0 && mResultsFound
    }

    fun focusOnSearch() {
        requireView().findViewById<View>(R.id.lb_search_bar_speech_orb).requestFocus()
    }

    override fun onQueryTextChange(newQuery: String?): Boolean {
        Log.d("Search", "onQueryTextChange: " + newQuery)
        arrayObjectAdapter.clear()
        if (newQuery != null && newQuery.length >= 3) {
            loadQuery(newQuery, "")
        }
        return true
    }

    fun loadQuery(s: String, entryPoint: String) {
        if (entryPoint == "Voice") {
            editText.setText(s)
            editText.setSelection(s.length)
            hideKeyboard(activity)
        }
        mQuery = s
        handler.removeCallbacks(mDelayedLoad)
        if (!TextUtils.isEmpty(mQuery) && mQuery.length > 0 && !mQuery.equals(" ")) {
            // mDelayedLoad.setSearchQuery(mQuery);
            Log.d("mQuery", "loadQuery: " + mQuery)
            handler.postDelayed(mDelayedLoad, 1000)
        }

    }

    private fun loadRows(response: Response<SearchMovieResult>) {
        hideKeyboard(activity)
        val listRowAdapter = ArrayObjectAdapter(SearchPortraitPresenter())
        if (response?.body()?.results?.isEmpty() != true) {
            val results = response?.body()?.results
            if (results != null && results.size > 0) {
                mResultsFound = true
                for (i in 0..results.size - 1) {
                    listRowAdapter.add(results[i])
                }
                val header = HeaderItem("Here is your search Result  for : $mQuery")
                arrayObjectAdapter.add(ListRow(header, listRowAdapter))
            } else {
                mResultsFound = false
            }
        }


    }

    fun hideKeyboard(activity: FragmentActivity?) {
        val imm: InputMethodManager =
            activity?.getSystemService(AppCompatActivity.INPUT_METHOD_SERVICE) as InputMethodManager
        //Find the currently focused view, so we can grab the correct window token from it.
        var view: View? = activity.currentFocus
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = View(activity)
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        Log.d("Search", "onQueryTextSubmit: " + query)
        arrayObjectAdapter.clear()
        if (query != null && query.length >= 3) {
            loadQuery(query, "")
        }
        return true
    }

    private var mDelayedLoad = Runnable {
        Log.d(TAG, "Runnable: ")
        val call = service.getMovies(API_KEY, LANGUAGE, mQuery, PAGE, false)
        arrayObjectAdapter.clear()
        call.enqueue(object : Callback<SearchMovieResult> {
            override fun onResponse(
                call: Call<SearchMovieResult>,
                response: Response<SearchMovieResult>
            ) {
                Log.d(TAG, "onResponse:= " + response.toString())
                if (response?.body()?.results?.isEmpty() == true) {
                    Toast.makeText(activity, "No Result Found", Toast.LENGTH_LONG).show()
                } else {
                    loadRows(response)
                }
            }

            override fun onFailure(call: Call<SearchMovieResult>, t: Throwable) {
                Log.d(TAG, "onFailure:= " + t.localizedMessage)
                Toast.makeText(activity, "Api Failure", Toast.LENGTH_LONG).show()
            }

        })
    }

    override fun onResume() {
        super.onResume()
        focusOnSearch()
    }

    /**
     * Fire an intent to start the voice recognition activity.
     */
    fun startVoiceRecognitionActivity() {
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        intent.putExtra(
            RecognizerIntent.EXTRA_LANGUAGE_MODEL,
            RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
        )
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Voice searching...")
        startActivityForResult(intent, SearchFragment.REQUEST_CODE)
    }

    /**
     * Handle the results from the voice recognition activity.
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_CODE && resultCode == AppCompatActivity.RESULT_OK) {
            // Populate the wordsList with the String values the recognition engine thought it heard
            val matches = data!!.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
            if (matches!!.isNotEmpty()) {
                val query = matches[0]
                loadQuery(query,"Voice")
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    companion object {
        private const val REQUEST_CODE = 1234
    }
}


