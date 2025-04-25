package com.example.shop.data

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.util.Log
import com.example.shop.utils.DatabaseManager

class ProductDAO(context: Context) {

    val databaseManager = DatabaseManager(context)

    fun getContentValues(product: Product): ContentValues{
       return ContentValues().apply {
            put(Product.COLUMN_NAME_TITLE, product.title)
            put(Product.COLUMN_NAME_IMAGE, product.image)
        }
    }

    fun getEntityFromCursor(cursor: Cursor): Product{
        val id = cursor.getInt(cursor.getColumnIndexOrThrow(Product.COLUMN_NAME_ID))
        val title = cursor.getString(cursor.getColumnIndexOrThrow(Product.COLUMN_NAME_TITLE))
        val image = cursor.getString(cursor.getColumnIndexOrThrow(Product.COLUMN_NAME_IMAGE))
        return Product(id, title, image)
    }

    fun insertAll(productList: List<Product>){
        productList.forEach{
            insert(it)
        }
    }

    fun insert(product: Product) {
        // Gets the data repository in write mode
        val db = databaseManager.writableDatabase

        // Create a new map of values, where column names are the keys
        val values = getContentValues(product)

        try {
            // Insert the new row, returning the primary key value of the new row
            val newRowId = db.insert(Product.TABLE_NAME, null, values)

            Log.i("DATABASE", "Inserted Product with id: $newRowId")
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            db.close()
        }
    }

    fun update(product: Product) {
        // Gets the data repository in write mode
        val db = databaseManager.writableDatabase

        // Create a new map of values, where column names are the keys
       val values = getContentValues(product)

        try {
            val updatedRows = db.update(Product.TABLE_NAME, values, "${Product.COLUMN_NAME_ID} = ${product.id}", null)

            Log.i("DATABASE", "Updated Product with id: ${product.id}")
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            db.close()
        }
    }

    fun delete(product: Product) {
        val db = databaseManager.writableDatabase

        try {
            val deletedRows = db.delete(Product.TABLE_NAME, "${Product.COLUMN_NAME_ID} = ${product.id}", null)

            Log.i("DATABASE", "Deleted Product with id: ${product.id}")
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            db.close()
        }
    }

    fun deleteAll() {
        val db = databaseManager.writableDatabase

        try {
            val deletedRows = db.delete(Product.TABLE_NAME, "", null)

            Log.i("DATABASE", "Deleted $deletedRows products")
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            db.close()
        }
    }


    fun findById(id: Long): Product? {
        val db = databaseManager.readableDatabase

        val projection = Product.TABLE_COLUMNS

        val selection = "${Product.COLUMN_NAME_ID} = $id"

        var product: Product? = null

        try {
            val cursor = db.query(
                Product.TABLE_NAME,   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                selection,              // The columns for the WHERE clause
                null,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                null               // The sort order
            )

            if (cursor.moveToNext()) {
               product = getEntityFromCursor(cursor)

            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            db.close()
        }
        return product
    }

    fun findAll(): List<Product> {
        val db = databaseManager.readableDatabase

        val projection =  Product.TABLE_COLUMNS

        var productList: MutableList<Product> = mutableListOf()

        try {
            val cursor = db.query(
                Product.TABLE_NAME,   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                null,              // The columns for the WHERE clause
                null,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                null              // The sort order
            )
            while (cursor.moveToNext()) {
                val product = getEntityFromCursor(cursor)

                productList.add(product)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            db.close()
        }

        return productList
    }

    fun findAllByName(query: String): List<Product> {
        val db = databaseManager.readableDatabase

        val projection =  Product.TABLE_COLUMNS

        var productList: MutableList<Product> = mutableListOf()

        try {
            val cursor = db.query(
                Product.TABLE_NAME,   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                "${Product.COLUMN_NAME_TITLE} LIKE '%$query%'",   // The columns for the WHERE clause
                null,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                null              // The sort order
            )
            while (cursor.moveToNext()) {
                val product = getEntityFromCursor(cursor)

                productList.add(product)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            db.close()
        }

        return productList
    }
}



/*
fun findAllByCategory(category: Category): List<Product> {
val db = databaseManager.readableDatabase

val projection =  Product.TABLE_COLUMNS

val selection = "${Product.COLUMN_NAME_CATEGORY} = ${category.id}"

var ProductList: MutableList<Product> = mutableListOf()

try {
    val cursor = db.query(
        Product.TABLE_NAME,   // The table to query
        projection,             // The array of columns to return (pass null to get all)
        selection,              // The columns for the WHERE clause
        null,          // The values for the WHERE clause
        null,                   // don't group the rows
        null,                   // don't filter by row groups
        Product.COLUMN_NAME_DONE               // The sort order
    )

    while (cursor.moveToNext()) {
        val id = cursor.getLong(cursor.getColumnIndexOrThrow(Product.COLUMN_NAME_ID))
        val title = cursor.getString(cursor.getColumnIndexOrThrow(Product.COLUMN_NAME_TITLE))
        val done = cursor.getInt(cursor.getColumnIndexOrThrow(Product.COLUMN_NAME_DONE)) != 0
        val categoryId = cursor.getLong(cursor.getColumnIndexOrThrow(Product.COLUMN_NAME_CATEGORY))
        val category = categoryDAO.findById(categoryId)!!
        val Product = Product(id, title, done, category)

        ProductList.add(Product)
    }
} catch (e: Exception) {
    e.printStackTrace()
} finally {
    db.close()
}

return ProductList
}
*/
