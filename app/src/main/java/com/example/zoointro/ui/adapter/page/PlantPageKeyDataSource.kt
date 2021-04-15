package com.example.zoointro.ui.adapter.page

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.example.zoointro.common.Constant.Companion.PAGE_LIMIT
import com.example.zoointro.common.Constant.Companion.PAGE_OFFSET
import com.example.zoointro.data.PlantEntity
import com.example.zoointro.data.ResponseResult
import com.example.zoointro.repository.ApiRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch


class PlantPageKeyDataSource(
    private val apiRepository: ApiRepository,
    private val location: String
) :
    PageKeyedDataSource<Int, PlantEntity>() {

    private val dataSourceJob = SupervisorJob()
    private val scope = CoroutineScope(Dispatchers.Main + dataSourceJob)
    var state: MutableLiveData<ResponseResult<Nothing>> = MutableLiveData()

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, PlantEntity>
    ) {
        scope.launch {
            updateState(ResponseResult.Loading)
            when (val response =
                apiRepository.getPlantInfo(location, PAGE_LIMIT, 0)) {
                is ResponseResult.Success -> {
                    updateState(ResponseResult.Complete)
                    callback.onResult(response.data.result.results, null, PAGE_OFFSET)
                }
                is ResponseResult.GenericError -> {
                    updateState(ResponseResult.GenericError(response.code))
                }
                is ResponseResult.Failure ->
                    updateState(ResponseResult.Failure(response.error))
            }

        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, PlantEntity>) {
        scope.launch {
            when (val response =
                apiRepository.getPlantInfo(location, PAGE_LIMIT, params.key)) {
                is ResponseResult.Success -> {
                    updateState(ResponseResult.Complete)
                    callback.onResult(response.data.result.results, params.key + PAGE_OFFSET)
                }
                is ResponseResult.GenericError -> {
                    updateState(ResponseResult.GenericError(response.code))
                }
                is ResponseResult.Failure ->
                    updateState(ResponseResult.Failure(response.error))
            }

        }
    }

    override fun loadBefore(
        params: LoadParams<Int>,
        callback: LoadCallback<Int, PlantEntity>
    ) {

    }

    /**
     * Update state
     */
    private fun updateState(state: ResponseResult<Nothing>) {
        this.state.postValue(state)
    }
}
