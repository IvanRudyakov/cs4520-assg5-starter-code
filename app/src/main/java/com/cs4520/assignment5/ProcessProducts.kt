package com.cs4520.assignment5

import com.cs4520.assignment5.database.ProductEntity
import com.google.gson.Gson
import com.google.gson.JsonElement
import com.google.gson.reflect.TypeToken

fun processData(json: JsonElement?): List<Product> {
    val listType = object : TypeToken<List<Product>>() {}.type
    val products: List<Product> = Gson().fromJson(json, listType)
    return filterProducts(products)
}
fun filterProducts(products: List<Product>): List<Product> {
    val outProds: MutableList<Product> = mutableListOf();

    val productNamesSeen: MutableSet<String> = mutableSetOf();
    for (p in products) {
        if (
            (p.type == "Equipment" || p.type == "Food") &&
            p.name != null &&
            p.price != null &&
            (p.type == "Equipment" || p.expiryDate != null) &&
            !productNamesSeen.contains(p.name)
        ){
            productNamesSeen.add(p.name)
            outProds.add(p)
        }
    }

    return outProds
}