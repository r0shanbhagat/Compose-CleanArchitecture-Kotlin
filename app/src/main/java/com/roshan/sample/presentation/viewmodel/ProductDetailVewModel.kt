package com.roshan.sample.presentation.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.roshan.sample.core.BaseViewModel
import com.roshan.sample.core.UiState
import com.roshan.sample.domain.usecase.GetProductDetailUseCase
import com.roshan.sample.presentation.state.ProductDetailState
import com.roshan.sample.presentation.state.StateEvent
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class ProductDetailVewModel(private val productDetailUseCase: GetProductDetailUseCase) :
    BaseViewModel() {


    private val _productDetail = mutableStateOf(ProductDetailState())
    val productDetail: State<ProductDetailState> get() = _productDetail


    /**
     * Set state intent
     *
     * @param mainStateEvent
     */
    internal fun setStateIntent(mainStateEvent: StateEvent) {
        when (mainStateEvent) {
            is StateEvent.ProductDetails -> {
                getProductDetailAPi(mainStateEvent.productId)
            }

            is StateEvent.None -> {
                //TODO will work on New flow
            }
        }
    }

    private fun getProductDetailAPi(id: String) {
        productDetailUseCase.invoke(id).onEach {
            when (it) {
                is UiState.Loading -> {
                    _productDetail.value = ProductDetailState(isLoading = true)
                }

                is UiState.Success -> {
                    _productDetail.value = ProductDetailState(data = it.data)
                }

                is UiState.Error -> {
                    _productDetail.value = ProductDetailState(error = it.message.toString())
                }
            }
        }.launchIn(viewModelScope)
    }

}