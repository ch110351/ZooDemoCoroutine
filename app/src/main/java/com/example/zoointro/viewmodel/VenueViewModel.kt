package com.example.zoointro.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.zoointro.common.Constant.Companion.PAGE_OFFSET
import com.example.zoointro.data.PlantEntity
import com.example.zoointro.data.ResponseResult
import com.example.zoointro.ui.adapter.page.PlantDataSourceFactory
import com.example.zoointro.ui.adapter.page.PlantPageKeyDataSource

class VenueViewModel(private val plantDataSourceFactory: PlantDataSourceFactory) : ViewModel() {

    lateinit var pagedList: LiveData<PagedList<PlantEntity>>

    init {
        initPlantListFactory()
    }

    /**
     * init paged list
     */
    private fun initPlantListFactory() {
        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(true)
            .setInitialLoadSizeHint(PAGE_OFFSET)
            .setPageSize(PAGE_OFFSET)
            .setPrefetchDistance(5)
            .build()
        pagedList = LivePagedListBuilder(plantDataSourceFactory, config)
            .build()
    }

    fun clearDataSource() {
        plantDataSourceFactory.clearDataSource()
    }

    fun setLocation(location: String) {
        plantDataSourceFactory.setLocation(location)
    }

    fun getState(): LiveData<ResponseResult<Nothing>> =
        Transformations.switchMap<PlantPageKeyDataSource, ResponseResult<Nothing>>(
            plantDataSourceFactory.sourceLiveData,
            PlantPageKeyDataSource::state
        )

}