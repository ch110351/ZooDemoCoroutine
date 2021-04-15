package com.example.zoointro.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.zoointro.R
import com.example.zoointro.common.Constant
import com.example.zoointro.data.PlantEntity
import com.example.zoointro.databinding.FragmentPlantBinding
import com.example.zoointro.viewmodel.MainViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class PlantFragment : Fragment() {

    private lateinit var fragmentPlantBinding: FragmentPlantBinding
    private val mainViewModel: MainViewModel by sharedViewModel()
    private lateinit var plantEntity: PlantEntity

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentPlantBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_plant, container, false
        )
        val bundle = requireArguments()
        plantEntity = bundle.getParcelable<PlantEntity>(Constant.PlantEntityKey)!!
        fragmentPlantBinding.item = plantEntity
        return fragmentPlantBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initView()
    }

    private fun initView() {
        mainViewModel.updateActionBarTitle(plantEntity.nameCh!!)
    }
}