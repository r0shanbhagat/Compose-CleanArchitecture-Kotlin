package com.roshan.sample.domain.usecase

import com.roshan.sample.core.UiState
import com.roshan.sample.domain.model.ProductDetail
import com.roshan.sample.domain.repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class GetProductDetailUseCase(private val repositoryImpl: Repository) {

    operator fun invoke(id: String): Flow<UiState<ProductDetail>> = flow {
        emit(UiState.Loading())
        try {
            emit(UiState.Success(data = repositoryImpl.getProductDetail(id)))
        } catch (e: Exception) {
            emit(UiState.Error(message = e.message.toString()))
        }
    }.flowOn(Dispatchers.IO)

}