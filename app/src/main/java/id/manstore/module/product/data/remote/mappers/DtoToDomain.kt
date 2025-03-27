package id.manstore.module.product.data.remote.mappers

import id.manstore.module.product.data.remote.dto.ProductDto
import id.manstore.module.product.data.remote.dto.RatingDto
import id.manstore.module.product.domain.model.Product
import id.manstore.module.product.domain.model.Rating

internal fun ProductDto.toDomain(): Product {
    return Product(
        category = category,
        description = description,
        id = id,
        image = image,
        price = price,
        rating = ratingDto?.toDomain(),
        title = title
    )
}

internal fun RatingDto.toDomain(): Rating {
    return Rating(
        count = count,
        rate = rate
    )
}