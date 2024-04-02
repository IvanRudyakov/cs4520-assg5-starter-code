package com.cs4520.assignment5

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.navigation.compose.rememberNavController
import androidx.work.WorkerFactory
import com.cs4520.assignment5.database.DatabaseProvider
import com.cs4520.assignment5.workmanager.WorkManagerSingleton

class MainActivity : ComponentActivity() {

    private val viewModel: ProductListViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DatabaseProvider.setContext(this)
        WorkManagerSingleton.initialize(this)
        setContent {
            AppNavHost(viewModel)
        }
    }
}