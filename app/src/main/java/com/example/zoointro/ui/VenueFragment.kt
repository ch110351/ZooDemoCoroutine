package com.example.zoointro.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.zoointro.R
import com.example.zoointro.common.Constant.Companion.PlantEntityKey
import com.example.zoointro.common.Constant.Companion.VenueEntityKey
import com.example.zoointro.data.PlantEntity
import com.example.zoointro.data.ResponseResult
import com.example.zoointro.data.VenueEntity
import com.example.zoointro.databinding.FragmentVenueBinding
import com.example.zoointro.ui.adapter.PlantAdapter
import com.example.zoointro.viewmodel.MainViewModel
import com.example.zoointro.viewmodel.VenueViewModel
import com.example.zoointro.widget.ItemClick
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


/**
 * 場館資訊 , 植物列表
 */
class VenueFragment : Fragment() {

    private val venueViewModel: VenueViewModel by viewModel()
    private val mainViewModel: MainViewModel by sharedViewModel()
    private lateinit var fragmentVenueBinding: FragmentVenueBinding
    private lateinit var venueEntity: VenueEntity

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentVenueBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_venue, container, false
        )
        fragmentVenueBinding.lifecycleOwner = this
        val bundle = requireArguments()
        venueEntity = bundle.getParcelable<VenueEntity>(VenueEntityKey)!!
        fragmentVenueBinding.item = venueEntity
        initView()
        return fragmentVenueBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initBind()
    }

    private fun initView() {
        mainViewModel.updateActionBarTitle(venueEntity.name)
        val plantAdapter =
            PlantAdapter(object :
                ItemClick<PlantEntity> {
                override fun onClicked(
                    view: View,
                    plantEntity: PlantEntity
                ) {
                    //跳轉至植物資訊頁
                    val bundle = Bundle()
                    bundle.putParcelable(PlantEntityKey, plantEntity)
                    Navigation.findNavController(view)
                        .navigate(R.id.action_venueFragment_to_plantFragment, bundle)
                }
            })

        //init plant list recycle view
        fragmentVenueBinding.plantList.apply {
            layoutManager = LinearLayoutManager(requireContext())
            addItemDecoration(
                DividerItemDecoration(
                    fragmentVenueBinding.plantList.context,
                    (fragmentVenueBinding.plantList.layoutManager as LinearLayoutManager).orientation
                )
            )
            this.adapter = plantAdapter
        }

        fragmentVenueBinding.urlText.setOnClickListener {
            val intent = Intent()
            intent.action = Intent.ACTION_VIEW
            intent.data = Uri.parse(venueEntity.url)
            startActivity(intent)
        }

        //init page list
        venueViewModel.setLocation(venueEntity.name)
        venueViewModel.clearDataSource()
        venueViewModel.pagedList.observe(viewLifecycleOwner, Observer {
            plantAdapter.submitList(it)
        })
    }

    private fun initBind() {
        venueViewModel.getState().observe(viewLifecycleOwner, Observer { state ->
            when (state) {
                is ResponseResult.Success,
                is ResponseResult.Complete ->
                    fragmentVenueBinding.progressBar.visibility = View.GONE
                is ResponseResult.Failure -> {
                    fragmentVenueBinding.progressBar.visibility = View.GONE
                    state.catchException?.let { showToast(it) }
                }
                is ResponseResult.GenericError -> {
                    fragmentVenueBinding.progressBar.visibility = View.GONE
                    showToast(state.catchException)
                }
                is ResponseResult.Loading ->
                    fragmentVenueBinding.progressBar.visibility = View.VISIBLE
            }
        })
    }
}