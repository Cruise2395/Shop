package com.example.shop.data

data class ProductResponse(
    val products: List<Product>
)
data class Product(
    val id: Int,
    val title: String,
    val image: String
    ) {
    companion object {
        const val TABLE_NAME = "Products"
        const val COLUMN_NAME_ID = "id"
        const val COLUMN_NAME_TITLE = "title"
        const val COLUMN_NAME_IMAGE = "image"

        val TABLE_COLUMNS = arrayOf(
            COLUMN_NAME_ID,
            COLUMN_NAME_TITLE,
            COLUMN_NAME_IMAGE
        )
    }

}
