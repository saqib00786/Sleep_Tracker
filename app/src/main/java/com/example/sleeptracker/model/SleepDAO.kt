package com.example.sleeptracker.model

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface SleepDAO {
    @Insert
    suspend fun insert(night : SleepEntity)

    @Update
    suspend fun update(night: SleepEntity)

    @Query("SELECT * FROM sleep_quality_table WHERE sleepNightId = :key")
    suspend fun get(key:Long) :SleepEntity

    @Query("DELETE FROM sleep_quality_table")
    suspend fun clear()

    @Query("SELECT * FROM sleep_quality_table ORDER BY sleepNightId DESC")
    fun getAllsleepNights(): LiveData<List<SleepEntity>>

    @Query("SELECT * FROM sleep_quality_table ORDER BY sleepNightId DESC LIMIT 1")
    suspend fun getsleepToNight(): SleepEntity?

    @Query("SELECT * from sleep_quality_table WHERE sleepNightId = :key")
    fun getNightWithId(key: Long): LiveData<SleepEntity>
}