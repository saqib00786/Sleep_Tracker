package com.example.sleeptracker.sleeptracker

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sleeptracker.R
import com.example.sleeptracker.adapter.SleepNightAdapter
import com.example.sleeptracker.adapter.SleepNightListner
import com.example.sleeptracker.databinding.FragmentSleepTrackerBinding
import com.example.sleeptracker.model.SleepDatabase
import com.google.android.material.snackbar.Snackbar

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

        val adapter  = SleepNightAdapter(SleepNightListner { ItemID->
            sleepTrackerViewModel.onSleepNightClicked(ItemID)
        })
        mBinding!!.recyclerViewID.adapter = adapter
        val manager = LinearLayoutManager(activity)
       // val manager = GridLayoutManager(activity,3)

        mBinding!!.recyclerViewID.layoutManager = manager

        sleepTrackerViewModel.getAllNight.observe(viewLifecycleOwner){
            it?.let{
//                adapter.data = it
                adapter.submitList(it)
            }
        }

        sleepTrackerViewModel.navigateToSleepDetail.observe(viewLifecycleOwner){ night ->
            night?.let {
               val action = FragmentSleepTrackerDirections.actionFragmentSleepTrackerToFragmentSleepNightDetail(night)
               this.findNavController().navigate(action)
                sleepTrackerViewModel.onSleepDetailNavigationCompeleted()
            }
        }


        sleepTrackerViewModel.navigateToSleepQuality.observe(viewLifecycleOwner,{ night->
            night?.let {
                this.findNavController()
                    .navigate(FragmentSleepTrackerDirections.actionFragmentSleepTrackerToFragmentSleepQuality(night.sleepNightId))
                sleepTrackerViewModel.doneNavigation()
            }
        })

        sleepTrackerViewModel.showSnackBarEvent.observe(viewLifecycleOwner, {
            if (it == true) { // Observed state is true.
                Snackbar.make(
                    requireActivity().findViewById(android.R.id.content),
                    getString(R.string.clear_message),
                    Snackbar.LENGTH_SHORT // How long to display the message.
                ).show()
                sleepTrackerViewModel.doneEvent()
            }
        })

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mBinding = null
    }
}