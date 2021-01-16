package com.example.sleeptracker.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.sleeptracker.databinding.ItemTextViewBinding
import com.example.sleeptracker.model.SleepEntity

class SleepNightAdapter(val clickListener: SleepNightListner) :
    ListAdapter<SleepEntity, SleepNightAdapter.ViewHolder>(SleepNightDiffCallback()) {

    /* var data = listOf<SleepEntity>()
         set(value) {
             field = value
             notifyDataSetChanged()
         }
 */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.layoutInflater(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        val item = data[position]
//        val item = getItem(position)
//        holder.bind(item)
        holder.bind(getItem(position)!!,clickListener)
    }

//    override fun getItemCount() = data.size

    class ViewHolder private constructor(val mBinding: ItemTextViewBinding) : RecyclerView.ViewHolder(mBinding.root) {

//        private var mBinding: ItemTextViewBinding? = null
//        private val binding get() = mBinding!!

        fun bind(item: SleepEntity, clickListener: SleepNightListner){
            mBinding.sleepListItemViewModel = item
            mBinding.sleepNightListner = clickListener
            mBinding.executePendingBindings()
        }

        companion object {
            fun layoutInflater(parent: ViewGroup): ViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val mBinding = ItemTextViewBinding.inflate(inflater,parent,false)

                return ViewHolder(mBinding)
            }
        }
    }
}

class SleepNightDiffCallback : DiffUtil.ItemCallback<SleepEntity>() {
    override fun areItemsTheSame(oldItem: SleepEntity, newItem: SleepEntity): Boolean {
        return oldItem.sleepNightId == newItem.sleepNightId
    }

    override fun areContentsTheSame(oldItem: SleepEntity, newItem: SleepEntity): Boolean {
        return oldItem == newItem
    }

}

class SleepNightListner(val clickListener: (sleepId : Long) -> Unit){
    fun onClick (night : SleepEntity) = clickListener(night.sleepNightId)
}