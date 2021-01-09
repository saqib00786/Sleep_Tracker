package com.example.sleeptracker.sleepquality

import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.sleeptracker.databinding.FragmentSleepQualityBinding
import com.example.sleeptracker.model.SleepDatabase


class FragmentSleepQuality : Fragment() {
    private var mBinding: FragmentSleepQualityBinding? = null
    private val binding get() = mBinding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentSleepQualityBinding.inflate(inflater, container, false)
        val application = requireNotNull(this.activity).application
        val datasource = SleepDatabase.getInstance(application).sleepDatabaseDao
        val arguments = FragmentSleepQualityArgs.fromBundle(requireArguments())
        val viewModelFactory = SleepQualityViewModelFactory(arguments.sleepNightKey, datasource)
        val sleepQualityViewModel = ViewModelProvider(this, viewModelFactory)
            .get(SleepQualityViewModel::class.java)

        mBinding!!.sleepQualityViewModel = sleepQualityViewModel
        mBinding!!.lifecycleOwner = this

        sleepQualityViewModel.navigateToSleepTracker.observe(viewLifecycleOwner,{
            if (it == true) { // Observed state is true.
                this.findNavController().navigate(
                    FragmentSleepQualityDirections.actionFragmentSleepQualityToFragmentSleepTracker())
                sleepQualityViewModel.doneNavigating()
            }
        })

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mBinding = null
    }
}