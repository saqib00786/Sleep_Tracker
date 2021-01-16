package com.example.sleeptracker.sleepnightdetail

import androidx.lifecycle.*
import com.example.sleeptracker.model.SleepDAO
import com.example.sleeptracker.model.SleepEntity
import javax.sql.CommonDataSource

class SleepNightDetailViewModel(
    nightKey :Long = 0L,
    dataSource: SleepDAO): ViewModel() {
    val database = dataSource

    private val night = MediatorLiveData<SleepEntity>()
    fun getNight() = night

    init {
        night.addSource(database.getNightWithId(nightKey),night::setValue)
    }

    private val _navigateToSleepTracker = MutableLiveData<Boolean?>()
    val navigateToSleepTracker : LiveData<Boolean?>
    get() = _navigateToSleepTracker

    fun doneNavigation(){
        _navigateToSleepTracker.value = null
    }

    fun onClose(){
        _navigateToSleepTracker.value = true
    }
}