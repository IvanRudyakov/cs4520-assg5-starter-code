package com.cs4520.assignment5

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkRequest
import com.cs4520.assignment5.Product
import com.cs4520.assignment5.api.RetrofitClient
import com.cs4520.assignment5.database.DatabaseProvider
import com.cs4520.assignment5.database.ProductEntity
import com.cs4520.assignment5.workmanager.RefreshData
import com.cs4520.assignment5.workmanager.WorkManagerSingleton
import com.google.gson.Gson
import com.google.gson.JsonElement
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.TimeUnit

class ProductListViewModel : ViewModel() {

    private val _isLoading = MutableStateFlow<Boolean>(true)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _productList = MutableStateFlow<List<Product>>(listOf())
    val productList: StateFlow<List<Product>> = _productList

    private val _errors = MutableStateFlow<String?>(null)
    val errors: StateFlow<String?> = _errors

    fun refresh() {
        val call = RetrofitClient.instance.fetchData();
        _isLoading.value = true;
        queueRefreshRequest()
        call.enqueue(object : Callback<JsonElement> {
            override fun onResponse(call: Call<JsonElement>, response: Response<JsonElement>) {
                if (response.isSuccessful) {
                    val data = response.body()
                    _isLoading.value = false;
                    val products = processData(data)
                    if (products.size == 0) {
                        Log.d("APIIIIIIII", "0 prods")
                        _productList.value = listOf()
                        _errors.value = "No Products Available"
                    } else {
                        Log.d("APIIIIIIII", "Some prods")
                        //setdb(products)
                        _productList.value = products
                        _errors.value = null;
                    }
                } else {
                    _isLoading.value = false;
                    Log.d("APIIIIIIII", "Unknown Error")
                    _errors.value = "Unknown Error";
                    _productList.value = listOf();
                }
            }

            override fun onFailure(call: Call<JsonElement>, t: Throwable) {
                _isLoading.value = false;

                // Assuming user is not connected to internet.
                setProductsFromDb();
            }
        })
    }

    private fun setProductsFromDb() {
        viewModelScope.launch {
            val products = DatabaseProvider.getDatabase()?.productDao()?.getAll();
            if (products != null && products.size > 0) {
                _productList.value = products.map {
                        p -> Product(p!!.name, p.type, p.expiryDate, p.price)
                }
                _errors.value = null;
            }
            else {
                _errors.value = "Not connected to internet";
            }
        }
    }

    private fun setdb(products: List<Product>) {
        val dao = DatabaseProvider.getDatabase()?.productDao()
        if (dao != null) {
            viewModelScope.launch {
                dao.clearAndInsert(products)
            }
        }
    }

    private fun queueRefreshRequest() {
        val myWorkRequest: WorkRequest = OneTimeWorkRequestBuilder<RefreshData>()
            .setInitialDelay(4, TimeUnit.SECONDS)
            .build()
        val workManager = WorkManagerSingleton.getInstance()
        workManager.enqueue(myWorkRequest)
        Log.d("APIIIIIIII", "sent")
    }


}