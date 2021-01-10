package com.example.sleeptracker.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.example.sleeptracker.R
import com.example.sleeptracker.convertDurationToFormatted
import com.example.sleeptracker.convertNumericQualityToString
import com.example.sleeptracker.model.SleepEntity
import kotlinx.android.synthetic.main.item_text_view.view.*

class SleepNightAdapter : RecyclerView.Adapter<SleepNightAdapter.ViewHolder>() {
    var data = listOf<SleepEntity>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.layoutInflater(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]
        holder.bind(item)
    }

    override fun getItemCount() = data.size

    class ViewHolder private constructor (view: View) : RecyclerView.ViewHolder(view) {
        private val sleepQualityImg: AppCompatImageView = view.sleepQualityImg
        private val sleepQualityString: AppCompatTextView = view.sleepQualityStringId
        private val sleepLengId: AppCompatTextView = view.sleepLengthId

        fun bind(item: SleepEntity) {
            val res = itemView.context.resources
            sleepLengId.text =
                convertDurationToFormatted(item.startTimeMilli, item.endTimeMilli, res)
            sleepQualityString.text = convertNumericQualityToString(item.sleepQuality, res)
            sleepQualityImg.setImageResource(
                when (item.sleepQuality) {
                    0 -> R.drawable.ic_sleep_0
                    1 -> R.drawable.ic_sleep_1
                    2 -> R.drawable.ic_sleep_2
                    3 -> R.drawable.ic_sleep_3
                    4 -> R.drawable.ic_sleep_4
                    5 -> R.drawable.ic_sleep_5
                    else -> R.drawable.ic_sleep_active
                }
            )
        }

        companion object {
            fun layoutInflater(parent: ViewGroup): ViewHolder {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_text_view, parent, false)
                return ViewHolder(view)
            }
        }
    }

}