package com.roshan.sample.presentation.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.roshan.sample.core.BaseViewModel
import com.roshan.sample.core.UiState
import com.roshan.sample.domain.usecase.GetProductListUseCase
import com.roshan.sample.presentation.state.ProductListState
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach


class ProductListVewModel(productListUseCase: GetProductListUseCase) : BaseViewModel() {

    private val _productList = mutableStateOf(ProductListState())
    val productList: State<ProductListState> get() = _productList

    init {
        productListUseCase.invoke().onEach {
            when (it) {
                is UiState.Loading -> {
                    _productList.value = ProductListState(isLoading = true)
                }

                is UiState.Success -> {
                    _productList.value = ProductListState(data = it.data)
                }

                is UiState.Error -> {
                    _productList.value = ProductListState(error = it.message.toString())
                }
            }
        }.launchIn(viewModelScope)
    }

}