package com.example.kotlintv.viewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.launch
import androidx.lifecycle.asLiveData

import com.example.kotlinroomdatabase.data.ContinueWatchDatabase
import com.example.kotlintv.model.ContinueWatch
import com.example.kotlintv.repository.ContinueWatchRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

// UserViewModel provides users data to the UI and survive configuration changes.
// A ViewModel acts as a communication center between the Repository and the UI.

class ContinueWatchViewModel(private val repository: WatchRepository) : ViewModel() {
//class ContinueWatchViewModel(application: Application): AndroidViewModel(application) {
   // val readAllData: LiveData<List<ContinueWatch>>
   // private val repository: ContinueWatchRepository

  /*  init {
        val continueWatchDao = ContinueWatchDatabase.getDatabase(application).userWatchDao()
        repository= ContinueWatchRepository(continueWatchDao)
        readAllData = repository.readAllData
    }

    fun addContinueWatch(user: ContinueWatch) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addUser(user)
        }
    }

    fun updateContinueWatch(user: ContinueWatch) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateUser(user)
        }
    }

    fun deleteContinueWatch(user: ContinueWatch) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteUser(user)
        }
    }

    fun deleteAllContinueWatch() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAllUsers()
        }
    }
    fun  readAllContinueWatch(): LiveData<List<ContinueWatch>> {
        var list: LiveData<List<ContinueWatch>>? = null;
        viewModelScope.launch(Dispatchers.IO) {
            list = repository.readAllData
        }
        return list!!
    }*/




    // Using LiveData and caching what allWords returns has several benefits:
    // - We can put an observer on the data (instead of polling for changes) and only update the
    //   the UI when the data actually changes.
    // - Repository is completely separated from the UI through the ViewModel.
    val mContinueWatch: LiveData<List<ContinueWatch>> = repository.mContinueWatch.asLiveData()

    /**
     * Launching a new coroutine to insert the data in a non-blocking way
     */
    fun insert(continueWatch: ContinueWatch) = viewModelScope.launch {
        Log.d("------>","insert inside view model")
        CoroutineScope(Dispatchers.IO).launch {
            repository.insert(continueWatch)
        }
    }
    /*fun getData()= viewModelScope.launch{
        repository.getAllData()

    }*/

}

class WatchViewModelFactory(private val repository: WatchRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ContinueWatchViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ContinueWatchViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
