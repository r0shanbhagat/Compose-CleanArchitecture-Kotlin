package com.roshan.sample.presentation.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.roshan.sample.core.UiState
import com.roshan.sample.domain.usecase.GetProductListUseCase
import com.roshan.sample.presentation.state.ProductListState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class ProductListVewModel @Inject constructor(private val productListUseCase: GetProductListUseCase) :
    ViewModel() {

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