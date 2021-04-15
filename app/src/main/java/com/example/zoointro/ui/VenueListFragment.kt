package com.example.zoointro.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.zoointro.widget.ItemClick
import com.example.zoointro.R
import com.example.zoointro.common.Constant.Companion.VenueEntityKey
import com.example.zoointro.data.ResponseResult
import com.example.zoointro.data.VenueEntity
import com.example.zoointro.databinding.FragmentVenueListBinding
import com.example.zoointro.ui.adapter.VenueAdapter
import com.example.zoointro.viewmodel.MainViewModel
import com.example.zoointro.viewmodel.VenueListViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class VenueListFragment : Fragment() {

    private lateinit var viewBinding: FragmentVenueListBinding
    private val venueListViewModel: VenueListViewModel by viewModel()
    private val mainViewModel: MainViewModel by sharedViewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_venue_list, container, false
        )
        viewBinding.lifecycleOwner = this
        initView()
        return viewBinding.root
    }

    private fun initView() {
        mainViewModel.updateActionBarTitle(resources.getString(R.string.home_title))

        val venueAdapter = VenueAdapter(object : ItemClick<VenueEntity> {
            override fun onClicked(view: View, venueEntity: VenueEntity) {
                //跳轉至場館資訊頁
                val bundle = Bundle()
                bundle.putParcelable(VenueEntityKey, venueEntity)
                Navigation.findNavController(view)
                    .navigate(R.id.action_venueListFragment_to_userInfoFragment, bundle)
            }
        })

        viewBinding.venueList.apply {
            layoutManager = LinearLayoutManager(requireContext())
            addItemDecoration(
                DividerItemDecoration(
                    viewBinding.venueList.context,
                    (viewBinding.venueList.layoutManager as LinearLayoutManager).orientation
                )
            )
            this.adapter = venueAdapter
        }

        lifecycleScope.launchWhenCreated {
            venueListViewModel.getVenueInfo().observe(viewLifecycleOwner, Observer { result ->
                when (result) {
                    is ResponseResult.Success -> {
                        venueAdapter.submitList(result.data.result.results)
                    }
                    is ResponseResult.GenericError -> {
                        showToast(result.catchException)
                    }
                    is ResponseResult.Failure -> {
                        result.catchException?.let { showToast(it) }
                    }
                    is ResponseResult.Loading -> {
                        viewBinding.progressBar.visibility = View.VISIBLE
                    }
                    is ResponseResult.Complete -> {
                        viewBinding.progressBar.visibility = View.GONE
                    }
                }
            })
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getNetworkLiveData().observe(viewLifecycleOwner, Observer { available ->
            if (!available) {
                Toast.makeText(activity, R.string.network_not_available, Toast.LENGTH_SHORT).show()
            }
        })
    }
}