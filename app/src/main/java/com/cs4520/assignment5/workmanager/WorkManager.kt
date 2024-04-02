package com.cs4520.assignment5.workmanager

import android.content.Context
import androidx.work.Configuration
import androidx.work.WorkManager
import androidx.work.WorkerFactory

object WorkManagerSingleton {
    private var workManager: WorkManager? = null

    fun initialize(context: Context) {
        this.workManager = WorkManager.getInstance(context)
    }

    fun getInstance(): WorkManager {
        if (workManager == null) {
            throw IllegalStateException("WorkManagerSingleton must be initialized first")
        }
        return workManager!!
    }
}

fun queuePeriodicWorker() {

}