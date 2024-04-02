package com.cs4520.assignment5.workmanager

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.cs4520.assignment5.api.RetrofitClient
import com.cs4520.assignment5.database.DatabaseProvider
import com.cs4520.assignment5.processData

class RefreshData(val context: Context, workerParams: WorkerParameters) : CoroutineWorker(context, workerParams) {
    override suspend fun doWork(): Result {
        Log.d("APIIIIIIII", "plzz")
        val json = RetrofitClient.instance.fetchDataSuspend();
        Log.d("APIIIIIIII", "plzz")
        val products = processData(json)
        Log.d("APIIIIIIII", products.size.toString())
        DatabaseProvider.setContext(context)
        val dao = DatabaseProvider.getDatabase()?.productDao()
        dao?.clearAndInsert(products)
        val out = dao?.getAll()
        Log.d("APIIIIIIII", out?.size.toString())
        return Result.success()
    }
}