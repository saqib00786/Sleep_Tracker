package com.example.sleeptracker.sleepnightdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.sleeptracker.model.SleepDAO
import com.example.sleeptracker.model.SleepEntity

class SleepNightDetailViewModelFactory(
    private val sleepNightKey: Long,
    private val dataSource: SleepDAO
) :ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(SleepNightDetailViewModel::class.java)){
            return SleepNightDetailViewModel(sleepNightKey,dataSource) as T
        }
        throw IllegalArgumentException("Unknown Model Class")
    }

}