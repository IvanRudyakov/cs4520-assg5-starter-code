package com.cs4520.assignment5.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.cs4520.assignment5.Product


@Dao
interface ProductDao {
    @Query("SELECT * FROM products")
    suspend fun getAll(): List<ProductEntity?>?

    @Insert
    suspend fun insertAll(product: List<ProductEntity>)

    @Query("DELETE FROM products")
    suspend fun clearProducts()

    suspend fun clearAndInsert(products: List<Product>) {
        clearProducts();
        val entities = products.map {
            val entity = ProductEntity();
            entity.name = it.name;
            entity.type = it.type;
            entity.price = it.price;
            entity.expiryDate = it.expiryDate;
            entity
        };
        insertAll(entities);
    }
}