package com.roshan.sample.domain

import com.roshan.sample.domain.model.ProductDetail
import com.roshan.sample.domain.model.ProductItem
import com.roshan.sample.domain.model.ProductListDTO


fun ProductListDTO.toProductList(): ProductItem {
    return ProductItem(
        id = this.id,
        image = this.image,
        title = this.title,
        description = this.description
    )
}

fun ProductListDTO.toProductDetail(): ProductDetail {
    return ProductDetail(
        category = this.category,
        description = this.description,
        id = this.id,
        image = this.image,
        price = this.price,
        title = this.title
    )
}



