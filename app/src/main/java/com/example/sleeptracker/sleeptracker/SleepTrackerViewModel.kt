package com.example.sleeptracker.sleeptracker

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.example.sleeptracker.formatNights
import com.example.sleeptracker.model.SleepDAO
import com.example.sleeptracker.model.SleepEntity
import kotlinx.coroutines.*

class SleepTrackerViewModel(
    val database: SleepDAO,
    application: Application
) : AndroidViewModel(application) {

    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private var toNight = MutableLiveData<SleepEntity?>()
    val getAllNight = database.getAllsleepNights()

    val nightsString = Transformations.map(getAllNight) { nights ->
        formatNights(nights, application.resources)
    }

    private var _navigateToSleepDetail = MutableLiveData<Long>()
    val navigateToSleepDetail: LiveData<Long>
        get() = _navigateToSleepDetail

    fun onSleepNightClicked(id : Long){
        _navigateToSleepDetail.value = id
    }

    fun onSleepDetailNavigationCompeleted() {
        _navigateToSleepDetail.value = null
    }

    private var _showSnackbarEvent = MutableLiveData<Boolean>()

    /**
     * If this is true, immediately `show()` a toast and call `doneShowingSnackbar()`.
     */
    val showSnackBarEvent: LiveData<Boolean>
        get() = _showSnackbarEvent

    fun doneEvent() {
        _showSnackbarEvent.value = false
    }

    private val _navigateToSleepQuality = MutableLiveData<SleepEntity>()

    val navigateToSleepQuality: LiveData<SleepEntity>
        get() = _navigateToSleepQuality

    fun doneNavigation() {
        _navigateToSleepQuality.value = null
    }

    init {
        initializeTonight()
    }

    private fun initializeTonight() {
        uiScope.launch {
            toNight.value = getTonightFromDatabase()
        }
    }

    private suspend fun getTonightFromDatabase(): SleepEntity? {
        return withContext(Dispatchers.IO) {
            var night = database.getsleepToNight()

            if (night?.endTimeMilli != night?.startTimeMilli) {
                night = null
            }
            night
        }
    }

    fun onStartTracking() {
        uiScope.launch {
            val newNight = SleepEntity()

            insert(newNight)

            toNight.value = getTonightFromDatabase()
        }
    }

    private suspend fun insert(newNight: SleepEntity) {
        withContext(Dispatchers.IO) {
            database.insert(newNight)
        }
    }

    fun onStopTracking() {
        uiScope.launch {
            val oldNight = toNight.value ?: return@launch
            oldNight.endTimeMilli = System.currentTimeMillis()
            update(oldNight)
            _navigateToSleepQuality.value = oldNight
        }
    }

    private suspend fun update(oldNight: SleepEntity) {
        withContext(Dispatchers.IO) {
            database.update(oldNight)
        }
    }

    fun onClear() {
        uiScope.launch {
            clear()
            toNight.value = null
        }
        _showSnackbarEvent.value = true
    }

    private suspend fun clear() {
        withContext(Dispatchers.IO) {
            database.clear()
        }
    }


    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}