package com.example.sleeptracker.sleepquality

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.sleeptracker.model.SleepDAO
import kotlin.IllegalArgumentException

class SleepQualityViewModelFactory (
    private val sleepNightKey: Long,
    private val dataSource: SleepDAO  ) : ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(SleepQualityViewModel::class.java))
        {
            return SleepQualityViewModel(sleepNightKey,dataSource) as T
        }
        throw IllegalArgumentException("Unknown View Model Class")
    }
}