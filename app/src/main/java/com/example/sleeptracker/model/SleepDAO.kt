package com.example.sleeptracker.model

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface SleepDAO {
    @Insert
    fun insert(night : SleepEntity)

    @Update
    fun update(night: SleepEntity)

    @Query("SELECT * FROM sleep_quality_table WHERE sleepNightId = :key")
    fun get(key:Long) :SleepEntity

    @Query("DELETE FROM sleep_quality_table")
    fun clear()

    @Query("SELECT * FROM sleep_quality_table ORDER BY sleepNightId DESC")
    fun getAllsleepNights(): LiveData<List<SleepEntity>>

    @Query("SELECT * FROM sleep_quality_table ORDER BY sleepNightId DESC LIMIT 1")
    fun getsleepToNight(): SleepEntity?
}