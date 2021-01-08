package com.example.sleeptracker.sleeptracker

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.sleeptracker.model.SleepDAO
import com.example.sleeptracker.model.SleepDatabase

class SleepTrackerViewModel(
    val database: SleepDAO,
    application: Application
) : AndroidViewModel(application) {


}