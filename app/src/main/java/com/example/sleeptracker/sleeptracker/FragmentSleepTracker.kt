package com.example.sleeptracker.sleeptracker

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.sleeptracker.R
import com.example.sleeptracker.databinding.FragmentSleepTrackerBinding
import com.example.sleeptracker.model.SleepDatabase

class FragmentSleepTracker : Fragment() {
    private var mBinding: FragmentSleepTrackerBinding? = null
    private val binding get() = mBinding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentSleepTrackerBinding.inflate(inflater,container,false)

        val application = requireNotNull(this.activity).application
        val datasource = SleepDatabase.getInstance(application).sleepDatabaseDao
        val viewModelFactory = SleepTrackerViewModelFactory(datasource,application)
        val sleepTrackerViewModel = ViewModelProvider(this,viewModelFactory)
            .get(SleepTrackerViewModel::class.java)
        mBinding!!.sleepTrackerViewModel = sleepTrackerViewModel
        mBinding!!.lifecycleOwner = this

        sleepTrackerViewModel.navigateToSleepQuality.observe(viewLifecycleOwner,{ night->
            night?.let {
                this.findNavController()
                    .navigate(FragmentSleepTrackerDirections.actionFragmentSleepTrackerToFragmentSleepQuality(night.sleepNightId))
                sleepTrackerViewModel.doneNavigation()
            }
        })

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mBinding = null
    }
}