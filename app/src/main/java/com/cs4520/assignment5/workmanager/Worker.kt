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
        val json = RetrofitClient.instance.fetchDataSuspend();
        val products = processData(json)
        DatabaseProvider.setContext(context)
        val dao = DatabaseProvider.getDatabase()?.productDao()
        dao?.clearAndInsert(products)
        return Result.success()
    }
}