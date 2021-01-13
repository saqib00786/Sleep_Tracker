package com.example.sleeptracker.sleeptracker

import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.databinding.BindingAdapter
import com.example.sleeptracker.R
import com.example.sleeptracker.convertDurationToFormatted
import com.example.sleeptracker.convertNumericQualityToString
import com.example.sleeptracker.model.SleepEntity

@BindingAdapter("sleepImage")
fun AppCompatImageView.setImageView(item: SleepEntity?){
    item?.let {
        setImageResource(
            when (item.sleepQuality) {
                0 -> R.drawable.ic_sleep_0
                1 -> R.drawable.ic_sleep_1
                2 -> R.drawable.ic_sleep_2
                3 -> R.drawable.ic_sleep_3
                4 -> R.drawable.ic_sleep_4
                5 -> R.drawable.ic_sleep_5
                else -> R.drawable.ic_sleep_active
            })
    }
}

@BindingAdapter("sleepDuration")
fun AppCompatTextView.setSleepDurationFormated(item : SleepEntity?){
    item?.let {
        text = convertDurationToFormatted(item.startTimeMilli,item.endTimeMilli,context.resources)
    }
}

@BindingAdapter("sleepStringQuality")
fun AppCompatTextView.setSleepStringQuality(item : SleepEntity?){
    item?.let {
        text = convertNumericQualityToString(item.sleepQuality,context.resources)
    }
}