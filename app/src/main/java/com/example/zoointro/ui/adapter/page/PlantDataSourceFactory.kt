package com.example.zoointro.ui.adapter.page

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.example.zoointro.data.PlantEntity
import com.example.zoointro.repository.ApiRepository


class PlantDataSourceFactory(private val apiRepository: ApiRepository) :
    DataSource.Factory<Int, PlantEntity>() {
    val sourceLiveData : MutableLiveData<PlantPageKeyDataSource> = MutableLiveData()
    private var dataSource: PlantPageKeyDataSource? = null
    private lateinit var location: String

    override fun create(): DataSource<Int, PlantEntity> {
        dataSource = PlantPageKeyDataSource(apiRepository, location)
        sourceLiveData.postValue(dataSource)
        return dataSource!!
    }

    fun setLocation(location: String) {
        this.location = location
    }

    fun clearDataSource() {
        if (dataSource != null)
            dataSource?.invalidate()
    }
}