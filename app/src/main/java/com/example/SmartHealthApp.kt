package com.example

import android.app.Application
import com.example.data.datasource.DemoDataSeeder
import com.example.data.local.AppDatabase
import com.example.data.repository.SmartHealthRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class SmartHealthApp : Application() {

    lateinit var database: AppDatabase
        private set

    lateinit var repository: SmartHealthRepository
        private set

    private val applicationScope = CoroutineScope(SupervisorJob() + Dispatchers.Main)

    override fun onCreate() {
        super.onCreate()
        instance = this
        database = AppDatabase.getDatabase(this)
        repository = SmartHealthRepository(database.smartHealthDao(), this)

        // Prepopulate demo hospital and clinical data
        applicationScope.launch {
            DemoDataSeeder.seedDatabaseIfEmpty(database.smartHealthDao())
        }
    }

    companion object {
        lateinit var instance: SmartHealthApp
            private set
    }
}
