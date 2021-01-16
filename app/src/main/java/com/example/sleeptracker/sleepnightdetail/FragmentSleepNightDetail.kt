package com.example.sleeptracker.sleepnightdetail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.sleeptracker.R
import com.example.sleeptracker.databinding.FragmentSleepNightDetailBinding
import com.example.sleeptracker.model.SleepDatabase
import com.example.sleeptracker.sleeptracker.SleepTrackerViewModel
import com.example.sleeptracker.sleeptracker.SleepTrackerViewModelFactory

class FragmentSleepNightDetail : Fragment() {
    private var mBinding : FragmentSleepNightDetailBinding? = null
    private val binding get() = mBinding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentSleepNightDetailBinding.inflate(inflater,container,false)

        val application = requireNotNull(this.activity).application
        val datasource = SleepDatabase.getInstance(application).sleepDatabaseDao
        val arguments = FragmentSleepNightDetailArgs.fromBundle(requireArguments())

        val sleepNightDetailViewModelFactory = SleepNightDetailViewModelFactory(arguments.sleepNightKey,datasource)
        val sleepNightDetailViewModel = ViewModelProvider(this,sleepNightDetailViewModelFactory)
            .get(SleepNightDetailViewModel::class.java)

        mBinding!!.sleepDetailViewModel = sleepNightDetailViewModel
        mBinding!!.lifecycleOwner = this

        sleepNightDetailViewModel.navigateToSleepTracker.observe(viewLifecycleOwner){
            if(it==true){
                val action = FragmentSleepNightDetailDirections.actionFragmentSleepNightDetailToFragmentSleepTracker()
                this.findNavController().navigate(action)
                sleepNightDetailViewModel.doneNavigation()
            }
        }

        return binding.root
    }

}