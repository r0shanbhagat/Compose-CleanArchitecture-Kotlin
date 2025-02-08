package com.roshan.sample.domain.usecase

import com.roshan.sample.core.UiState
import com.roshan.sample.data.respository.RepositoryImpl
import com.roshan.sample.domain.model.ProductItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetProductListUseCase @Inject constructor(private val repositoryImpl: RepositoryImpl) {

    operator fun invoke(): Flow<UiState<List<ProductItem>>> = flow {
        emit(UiState.Loading())
        try {
            emit(UiState.Success(data = repositoryImpl.getProductList()))
        } catch (e: Exception) {
            emit(UiState.Error(message = e.message.toString()))
        }
    }.flowOn(Dispatchers.IO)

}